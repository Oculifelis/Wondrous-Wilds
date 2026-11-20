package com.ineffa.wondrouswilds.blocks.entity;

import net.minecraft.world.entity.EntitySpawnRequest;
import net.minecraft.core.registries.BuiltInRegistries;
import com.ineffa.wondrouswilds.blocks.InhabitableNestBlock;
import com.ineffa.wondrouswilds.entities.FlyingAndWalkingAnimalEntity;
import com.ineffa.wondrouswilds.entities.BlockNester;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.ineffa.wondrouswilds.registry.WondrousWildsItems;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import static com.ineffa.wondrouswilds.registry.WondrousWildsEntities.DEFAULT_NESTER_CAPACITY_WEIGHTS;

public interface InhabitableNestBlockEntity {

    String INHABITANTS_KEY = "Inhabitants";
    String IS_FRESH_KEY = "IsFresh";
    String ENTITY_DATA_KEY = "EntityData";
    String CAPACITY_WEIGHT_KEY = "CapacityWeight";
    String TICKS_IN_NEST_KEY = "TicksInNest";
    String MIN_OCCUPATION_TICKS_KEY = "MinOccupationTicks";

    List<String> IRRELEVANT_INHABITANT_NBT_KEYS = Arrays.asList("CannotEnterNestTicks", "Air", "DeathTime", "FallDistance", "FallFlying", "Fire", "HurtByTimestamp", "HurtTime", "Motion", "OnGround", "PortalCooldown", "Pos", "Rotation", "Passengers", "Leash", "UUID");

    List<Inhabitant> getInhabitants();

    static void removeIrrelevantNbt(CompoundTag nbtCompound) {
        for (String key : IRRELEVANT_INHABITANT_NBT_KEYS) nbtCompound.remove(key);
    }

    // Inhabitants stay a raw tag list, as in the original, stored through a codec on the value output.
    default void writeInhabitants(ValueOutput out) {
        ValueOutput.TypedOutputList<CompoundTag> list = out.list(INHABITANTS_KEY, CompoundTag.CODEC);
        ListTag nbtList = this.getInhabitantsNbt();
        for (int i = 0; i < nbtList.size(); ++i) list.add(nbtList.getCompoundOrEmpty(i));
    }

    default void readInhabitants(ValueInput in) {
        for (CompoundTag nbtCompound : in.listOrEmpty(INHABITANTS_KEY, CompoundTag.CODEC)) {

            Inhabitant inhabitant = new Inhabitant(nbtCompound.getBooleanOr(IS_FRESH_KEY, false), nbtCompound.getCompoundOrEmpty(ENTITY_DATA_KEY), nbtCompound.getIntOr(CAPACITY_WEIGHT_KEY, 0), nbtCompound.getIntOr(MIN_OCCUPATION_TICKS_KEY, 0), nbtCompound.getIntOr(TICKS_IN_NEST_KEY, 0));
            this.getInhabitants().add(inhabitant);
        }
    }

    default ListTag getInhabitantsNbt() {
        ListTag nbtList = new ListTag();
        for (Inhabitant inhabitant : this.getInhabitants()) {
            CompoundTag nbt = new CompoundTag();

            nbt.putBoolean(IS_FRESH_KEY, inhabitant.isFresh);
            nbt.put(ENTITY_DATA_KEY, inhabitant.entityData.copy());
            nbt.putInt(CAPACITY_WEIGHT_KEY, inhabitant.capacityWeight);
            nbt.putInt(MIN_OCCUPATION_TICKS_KEY, inhabitant.minOccupationTicks);
            nbt.putInt(TICKS_IN_NEST_KEY, inhabitant.ticksInNest);

            nbtList.add(nbt);
        }
        return nbtList;
    }

    /*default int getInhabitantCount() {
        return this.getInhabitantList(.size();
    }*/

    default boolean hasInhabitants() {
        return !this.getInhabitants().isEmpty();
    }

    default int getMaxCapacity() {
        return 100;
    }

    default int getUsedCapacity() {
        int usedCapacity = 0;

        for (Inhabitant inhabitant : this.getInhabitants()) usedCapacity += inhabitant.capacityWeight;

        return usedCapacity;
    }

    default int getRemainingCapacity() {
        return this.getMaxCapacity() - this.getUsedCapacity();
    }

    default boolean isCapacityFilled() {
        return this.getUsedCapacity() >= this.getMaxCapacity();
    }

    default boolean entityMatchesInhabitants(Mob entity) {
        String nesterId = EntityType.getKey(entity.getType()).toString();

        for (Inhabitant inhabitant : this.getInhabitants()) {
            String inhabitantId = inhabitant.entityData.getStringOr(Entity.TAG_ID, "");

            if (!Objects.equals(inhabitantId, nesterId)) return false;
        }

        return true;
    }

    default boolean canFitNester(BlockNester nester) {
        return nester.getNestCapacityWeight() <= this.getRemainingCapacity();
    }

    default boolean tryAddingInhabitant(BlockNester nester) {
        if (!(nester instanceof Mob nesterEntity)) return false;

        if ((this.isCapacityFilled() || !this.canFitNester(nester)) || !this.entityMatchesInhabitants(nesterEntity)) {
            if (nesterEntity instanceof Animal animal && animal.isBaby() && this.entityMatchesInhabitants(animal)) return false;

            this.alertInhabitants(nesterEntity, InhabitantReleaseState.ALERT);
            return false;
        }

        nesterEntity.stopRiding();
        nesterEntity.ejectPassengers();

        this.addInhabitant(nester);

        Level world = this.getNestWorld();
        if (world != null) {
            BlockPos pos = this.getNestPos();

            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(nesterEntity, this.getNestCachedState()));
        }

        nesterEntity.discard();

        this.markNestDirty();

        return true;
    }

    default void addInhabitant(BlockNester inhabitant) {
        if (!(inhabitant instanceof Mob entity)) return;

        TagValueOutput output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, entity.registryAccess());
        entity.save(output);
        CompoundTag entityData = output.buildResult();

        this.getInhabitants().add(new Inhabitant(false, entityData, inhabitant.getNestCapacityWeight(), 0, inhabitant.getMinTicksInNest()));
    }

    default void addFreshInhabitant(EntityType<?> entityType) {
        if (!DEFAULT_NESTER_CAPACITY_WEIGHTS.containsKey(entityType)) return;

        CompoundTag entityData = new CompoundTag();
        entityData.putString("id", BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
        entityData.store("NestPos", BlockPos.CODEC, this.getNestPos());

        this.getInhabitants().add(new Inhabitant(true, entityData, DEFAULT_NESTER_CAPACITY_WEIGHTS.get(entityType), 0, 600));
    }

    default void alertInhabitants(@Nullable LivingEntity attacker, InhabitantReleaseState releaseState) {
        this.alertInhabitants(attacker, Objects.requireNonNull(this.getNestWorld()).getBlockState(this.getNestPos()), releaseState);
    }

    default void alertInhabitants(@Nullable LivingEntity attacker, BlockState state, InhabitantReleaseState releaseState) {
        List<BlockNester> inhabitants = this.tryReleasingInhabitants(state, releaseState);

        if (attacker != null) {
            for (BlockNester inhabitant : inhabitants) {
                if (!inhabitant.defendsNest() || !(inhabitant instanceof Mob inhabitantEntity) || (inhabitant instanceof WoodpeckerEntity woodpecker && woodpecker.isTame())) continue;

                if (attacker.position().distanceTo(inhabitantEntity.position()) > inhabitantEntity.getAttributeValue(Attributes.FOLLOW_RANGE)) continue;

                inhabitantEntity.setTarget(attacker);

                //inhabitant.setCannotEnterNestTicks(inhabitant.getMinTicksOutOfNest());
            }
        }
    }

    default List<BlockNester> tryReleasingInhabitants(BlockState state, InhabitantReleaseState releaseState) {
        ArrayList<BlockNester> releasedInhabitants = new ArrayList<>();

        this.getInhabitants().removeIf(inhabitant -> tryReleasingInhabitant(Objects.requireNonNull(this.getNestWorld()), this.getNestPos(), state, releaseState, inhabitant, releasedInhabitants));

        if (!releasedInhabitants.isEmpty()) this.markNestDirty();

        return releasedInhabitants;
    }

    static boolean tryReleasingInhabitant(Level world, BlockPos nestPos, BlockState state, InhabitantReleaseState releaseState, Inhabitant inhabitant, @Nullable List<BlockNester> inhabitantGetter) {
        if ((world.isDarkOutside() || world.isRaining()) && releaseState == InhabitantReleaseState.RELEASE) return false;

        CompoundTag nbtCompound = inhabitant.entityData.copy();
        removeIrrelevantNbt(nbtCompound);
        nbtCompound.store("NestPos", BlockPos.CODEC, nestPos);

        Direction frontDirection = state.getValue(InhabitableNestBlock.FACING);
        BlockPos frontPos = nestPos.relative(frontDirection);

        boolean hasSpaceToRelease = world.getBlockState(frontPos).getCollisionShape(world, frontPos).isEmpty();

        if (!hasSpaceToRelease && releaseState != InhabitantReleaseState.EMERGENCY) return false;

        Mob inhabitantEntity = (Mob) EntityType.loadEntityRecursive(nbtCompound, world, new EntitySpawnRequest(EntitySpawnReason.LOAD, false), e -> e);
        if (inhabitantEntity != null) {
            double d0 = !hasSpaceToRelease ? 0.0D : 0.55D + (double) (inhabitantEntity.getBbWidth() / 2.0F);
            double d1 = (double) nestPos.getX() + 0.5D + d0 * (double) frontDirection.getStepX();
            double d2 = (double) nestPos.getY() + 0.5D - (double) (inhabitantEntity.getBbHeight() / 2.0F);
            double d3 = (double) nestPos.getZ() + 0.5D + d0 * (double) frontDirection.getStepZ();
            inhabitantEntity.snapTo(d1, d2, d3, inhabitantEntity.getYRot(), inhabitantEntity.getXRot());

            if (inhabitantEntity instanceof Animal) ageInhabitant(inhabitant.ticksInNest, (Animal) inhabitantEntity);

            if (inhabitant.isFresh) {
                if (world instanceof ServerLevelAccessor serverWorldAccess) inhabitantEntity.finalizeSpawn(serverWorldAccess, serverWorldAccess.getCurrentDifficultyAt(inhabitantEntity.blockPosition()), EntitySpawnReason.CHUNK_GENERATION, null);

                if (inhabitantEntity instanceof FlyingAndWalkingAnimalEntity flyingEntity) flyingEntity.setFlying(true);
            }

            if (inhabitantEntity instanceof WoodpeckerEntity woodpecker && woodpecker.isTame()) {
                if (woodpecker.getMainHandItem().isEmpty() && world.getBlockEntity(nestPos) instanceof NestBoxBlockEntity nestBox && !nestBox.isEmpty()) {
                    int slotToTakeFrom = 0;
                    ItemStack nestBoxItem = nestBox.getItem(slotToTakeFrom);
                    if (!nestBoxItem.isEmpty()) {
                        woodpecker.setItemInHand(InteractionHand.MAIN_HAND, nestBoxItem.copy());
                        nestBox.removeItemNoUpdate(slotToTakeFrom);
                    }
                }
            }

            if (inhabitantGetter != null) inhabitantGetter.add((BlockNester) inhabitantEntity);

            world.playSound(null, nestPos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.gameEvent(GameEvent.BLOCK_CHANGE, nestPos, GameEvent.Context.of(inhabitantEntity, world.getBlockState(nestPos)));

            if (world.addFreshEntity(inhabitantEntity)) {
                if (releaseState == InhabitantReleaseState.RELEASE && inhabitantEntity instanceof WoodpeckerEntity woodpecker && woodpecker.isTame() && woodpecker.getRandom().nextInt(10) == 0 && world instanceof net.minecraft.server.level.ServerLevel serverLevel) woodpecker.spawnAtLocation(serverLevel, WondrousWildsItems.WOODPECKER_CREST_FEATHER);

                return true;
            }
            else return false;
        }
        return false;
    }

    static void ageInhabitant(int ticks, Animal inhabitant) {
        int i = inhabitant.getAge();
        if (i < 0) inhabitant.setAge(Math.min(0, i + ticks));
        else if (i > 0) inhabitant.setAge(Math.max(0, i - ticks));

        inhabitant.setInLoveTime(Math.max(0, inhabitant.getInLoveTime() - ticks));
    }

    Level getNestWorld();

    BlockPos getNestPos();

    BlockState getNestCachedState();

    void markNestDirty();

    class Inhabitant {
        final boolean isFresh;
        final CompoundTag entityData;
        final int capacityWeight;
        final int minOccupationTicks;
        int ticksInNest;

        protected Inhabitant(boolean isFresh, CompoundTag entityData, int capacityWeight, int ticksInNest, int minOccupationTicks) {
            removeIrrelevantNbt(entityData);

            this.isFresh = isFresh;
            this.entityData = entityData;
            this.capacityWeight = capacityWeight;
            this.ticksInNest = ticksInNest;
            this.minOccupationTicks = minOccupationTicks;
        }
    }

    enum InhabitantReleaseState {
        RELEASE,
        ALERT,
        EMERGENCY
    }
}
