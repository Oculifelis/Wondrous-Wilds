package com.ineffa.wondrouswilds.entities;

import net.minecraft.world.phys.Vec3;
import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;
import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.entities.ai.FireflyHideGoal;
import com.ineffa.wondrouswilds.entities.ai.FireflyLandOnEntityGoal;
import com.ineffa.wondrouswilds.entities.ai.FireflyWanderFlyingGoal;
import com.ineffa.wondrouswilds.entities.ai.FireflyWanderLandGoal;
import com.ineffa.wondrouswilds.registry.WondrousWildsTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.golem.SnowGolem;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class FireflyEntity extends FlyingAndWalkingAnimalEntity implements GeoEntity {

    private static final EntityDataAccessor<Integer> LAND_ON_ENTITY_COOLDOWN = SynchedEntityData.defineId(FireflyEntity.class, EntityDataSerializers.INT);

    public FireflyEntity(EntityType<? extends FireflyEntity> entityType, Level world) {
        super(entityType, world);

        this.setPathfindingMalus(PathType.FIRE_IN_NEIGHBOR, -1.0f);
        this.setPathfindingMalus(PathType.WATER, -1.0f);
        this.setPathfindingMalus(PathType.WATER_BORDER, 16.0f);
        this.setPathfindingMalus(PathType.COCOA, -1.0f);
        this.setPathfindingMalus(PathType.FENCE, -1.0f);

        // noCulling is gone; FireflyRenderer#affectedByCulling returns false instead.
    }

    public static boolean canFireflySpawn(EntityType<FireflyEntity> entityType, ServerLevelAccessor world, EntitySpawnReason spawnReason, BlockPos spawnAttemptPos, RandomSource random) {
        if (!world.getBlockState(spawnAttemptPos.below()).is(WondrousWildsTags.BlockTags.FIREFLIES_SPAWNABLE_ON) || !FireflyEntity.checkMobSpawnRules(entityType, world, spawnReason, spawnAttemptPos, random)) return false;

        Holder<Biome> biome = world.getBiome(spawnAttemptPos);
        int skylightLevel = world.getBrightness(LightLayer.SKY, spawnAttemptPos);

        // Spawn immediately if the spawn position is underground and the biome allows underground spawning
        if (skylightLevel <= 0 && biome.is(WondrousWildsTags.BiomeTags.SPAWNS_FIREFLIES_UNDERGROUND)) return true;

        ServerLevel serverWorld = world.getLevel();

        // Otherwise, cancel if it is not raining and the biome requires it
        if (biome.is(WondrousWildsTags.BiomeTags.SPAWNS_FIREFLIES_ON_SURFACE_ONLY_IN_RAIN)) {
            if (!serverWorld.isRaining()) return false;
        }
        // Otherwise, cancel if the biome does not allow surface spawning at all
        else if (!biome.is(WondrousWildsTags.BiomeTags.SPAWNS_FIREFLIES_ON_SURFACE)) return false;

        // Finally, spawn if basic surface spawning conditions are met
        return serverWorld.isDarkOutside() && skylightLevel >= 6 && world.getBrightness(LightLayer.BLOCK, spawnAttemptPos) <= 0;
    }

    // Removes the light level restriction set by AnimalEntity
    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader world) {
        return 0.0F;
    }

    public static AttributeSupplier.Builder createFireflyAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.FLYING_SPEED, 0.3D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);

        builder.define(LAND_ON_ENTITY_COOLDOWN, 0);
    }

    @Override
    public void readAdditionalSaveData(ValueInput nbt) {
        super.readAdditionalSaveData(nbt);

        this.setLandOnEntityCooldown(nbt.getIntOr("LandOnEntityCooldown", 0));
    }

    @Override
    public void addAdditionalSaveData(ValueOutput nbt) {
        super.addAdditionalSaveData(nbt);

        nbt.putInt("LandOnEntityCooldown", this.getLandOnEntityCooldown());
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        if (this.getRandom().nextBoolean() && !this.isInFlight()) this.setFlying(true);

        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    public int getLandOnEntityCooldown() {
        return this.entityData.get(LAND_ON_ENTITY_COOLDOWN);
    }

    public void setLandOnEntityCooldown(int ticks) {
        this.entityData.set(LAND_ON_ENTITY_COOLDOWN, ticks);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new FireflyHideGoal(this, 1.0D, 16, 16));
        if (WondrousWilds.config.mobSettings.firefliesLandOnMobs) this.goalSelector.addGoal(3, new FireflyLandOnEntityGoal(this, LivingEntity.class));
        this.goalSelector.addGoal(4, new FireflyWanderLandGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new FireflyWanderFlyingGoal(this));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isPassenger()) {
            if (!this.level().isClientSide() && (this.getRandom().nextInt(600) == 0 || this.shouldHide())) this.stopRiding();

            if (this.getVehicle() != null) this.setYHeadRot(this.getVehicle().getYHeadRot());
        }
        else if (this.getLandOnEntityCooldown() > 0) this.setLandOnEntityCooldown(this.getLandOnEntityCooldown() - 1);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        if (super.hurtServer(level, source, amount)) {
            if (this.isPassenger()) this.stopRiding();

            else if (!this.isInFlight()) this.setFlying(true);

            return true;
        }
        else return false;
    }

    // The original's ARTHROPOD mob type is the minecraft:arthropod entity tag now (data/minecraft/tags/entity_type).

    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        return true;
    }

    public boolean canWander() {
        return !this.isPassenger();
    }

    public boolean canSearchForEntityToLandOn() {
        return this.getLandOnEntityCooldown() <= 0 && !this.isPassenger() && !this.shouldHide();
    }

    public boolean shouldHide() {
        return this.level().isBrightOutside() && this.level().getBrightness(LightLayer.SKY, this.blockPosition()) >= 6;
    }

    @Override
    public void stopRiding() {
        if (!this.isInFlight()) this.setFlying(true);

        Entity vehicle = this.getVehicle();

        super.stopRiding();

        if (!this.level().isClientSide()) {
            if (vehicle instanceof Player) {
                ServerLevel serverWorld = (ServerLevel) this.level();
                for (ServerPlayer player : serverWorld.players()) player.connection.send(new ClientboundSetPassengersPacket(vehicle));
            }
        }
    }

    /**
     * The original's riding offsets were tuned for the old rider formula (vehicle height * 0.75 + rider offset).
     * Newer versions place riders at the vehicle's passenger attachment point, so the old height is rebuilt here.
     */
    @Override
    public Vec3 getVehicleAttachmentPoint(Entity vehicle) {
        double attach = vehicle.getPassengerRidingPosition(this).y - vehicle.getY();
        return new Vec3(0.0D, attach - vehicle.getBbHeight() * 0.75D - this.getLegacyRidingOffset(), 0.0D);
    }

    private double getLegacyRidingOffset() {
        double offset = 0.0D;

        if (this.isPassenger()) {
            EntityType<?> vehicleType = Objects.requireNonNull(this.getVehicle()).getType();

            if (vehicleType == EntityType.ZOMBIE_VILLAGER || vehicleType == EntityType.ENDERMAN) offset = 0.6D;
            else if (vehicleType == EntityType.PLAYER || vehicleType == EntityType.ZOMBIE || vehicleType == EntityType.SKELETON || vehicleType == EntityType.VILLAGER || vehicleType == EntityType.WANDERING_TRADER || vehicleType == EntityType.PILLAGER || vehicleType == EntityType.VINDICATOR || vehicleType == EntityType.EVOKER || vehicleType == EntityType.ILLUSIONER) offset = 0.5D;

            else if (vehicleType == EntityType.CREEPER || vehicleType == EntityType.SPIDER || vehicleType == EntityType.COW || vehicleType == EntityType.CHICKEN) offset = 0.3D;
            else if (vehicleType == EntityType.SHEEP || vehicleType == EntityType.PIG) offset = 0.2D;
            else if (vehicleType == EntityType.WITCH) offset = 1.0D;
            else if (vehicleType == EntityType.ALLAY) offset = 0.1D;
            else if (vehicleType == EntityType.IRON_GOLEM) offset = 0.65D;

            else if (vehicleType == EntityType.SNOW_GOLEM) {
                offset = 0.45D;

                if (!((SnowGolem) this.getVehicle()).hasPumpkin()) offset -= 0.175D;
            }

            else if (vehicleType == EntityType.ARMOR_STAND) {
                offset = 0.4D;

                if (!((ArmorStand) this.getVehicle()).getItemBySlot(EquipmentSlot.HEAD).isEmpty()) offset += 0.1D;
            }

            if (this.getVehicle().isShiftKeyDown()) offset -= 0.15D;
        }

        return offset;
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        return null;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {}

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar animationData) {
        AnimationController<FireflyEntity> molangAnimationController = new AnimationController<>("molangAnimationController", 2, this::molangAnimationPredicate);
        AnimationController<FireflyEntity> antennaMolangController = new AnimationController<>("antennaMolangController", 2, this::antennaMolangPredicate);

        animationData.add(molangAnimationController);
        animationData.add(antennaMolangController);
    }

    private <E extends GeoEntity> PlayState molangAnimationPredicate(AnimationTest<E> event) {
        if (this.isInFlight())
            event.setAnimation(RawAnimation.begin().thenLoop("flyingMolang"));

        else
            event.setAnimation(RawAnimation.begin().thenLoop("groundedMolang"));

        return PlayState.CONTINUE;
    }

    private <E extends GeoEntity> PlayState antennaMolangPredicate(AnimationTest<E> event) {
        event.setAnimation(RawAnimation.begin().thenLoop("antennaeMolang"));

        return PlayState.CONTINUE;
    }
}
