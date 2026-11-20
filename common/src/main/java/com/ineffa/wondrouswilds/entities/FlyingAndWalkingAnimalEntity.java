package com.ineffa.wondrouswilds.entities;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import com.ineffa.wondrouswilds.entities.ai.BetterFlyNavigation;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public abstract class FlyingAndWalkingAnimalEntity extends Animal {

    public static final String IS_FLYING_KEY = "IsFlying";
    public static final String WANTS_TO_LAND_KEY = "WantsToLand";

    private static final EntityDataAccessor<Boolean> IS_FLYING = SynchedEntityData.defineId(FlyingAndWalkingAnimalEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WANTS_TO_LAND = SynchedEntityData.defineId(FlyingAndWalkingAnimalEntity.class, EntityDataSerializers.BOOLEAN);

    private final FlyingMoveControl airMoveControl;
    private final MoveControl landMoveControl;

    private final BetterFlyNavigation flyNavigation;
    private final GroundPathNavigation landNavigation;

    public FlyingAndWalkingAnimalEntity(EntityType<? extends FlyingAndWalkingAnimalEntity> entityType, Level world) {
        super(entityType, world);

        BetterFlyNavigation flyNavigation = new BetterFlyNavigation(this, world);
        flyNavigation.setCanOpenDoors(false);
        flyNavigation.getNodeEvaluator().setCanPassDoors(true);
        flyNavigation.setCanFloat(false);
        this.flyNavigation = flyNavigation;
        this.landNavigation = new GroundPathNavigation(this, world);

        this.airMoveControl = new FlyingMoveControl(this, 20, true);
        this.landMoveControl = new MoveControl(this);
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        if (this.isInFlight()) {
            BetterFlyNavigation flyNavigation = new BetterFlyNavigation(this, world);
            flyNavigation.setCanOpenDoors(false);
            flyNavigation.getNodeEvaluator().setCanPassDoors(true);
            flyNavigation.setCanFloat(false);

            return flyNavigation;
        }

        return new GroundPathNavigation(this, world);
    }

    /** Quirk kept: the original never chose a breeding item, so it kept the old default (wheat). */
    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.WHEAT);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);

        builder.define(IS_FLYING, false);
        builder.define(WANTS_TO_LAND, false);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput nbt) {
        super.addAdditionalSaveData(nbt);

        nbt.putBoolean(IS_FLYING_KEY, this.isInFlight());
        nbt.putBoolean(WANTS_TO_LAND_KEY, this.wantsToLand());
    }

    @Override
    public void readAdditionalSaveData(ValueInput nbt) {
        super.readAdditionalSaveData(nbt);

        boolean isFlying = nbt.getBooleanOr(IS_FLYING_KEY, false);
        if (this.isInFlight() != isFlying) this.setFlying(isFlying);

        this.setWantsToLand(nbt.getBooleanOr(WANTS_TO_LAND_KEY, false));
    }

    public boolean isInFlight() {
        return this.entityData.get(IS_FLYING);
    }

    public void setIsFlying(boolean isFlying) {
        this.entityData.set(IS_FLYING, isFlying);
    }

    public void setFlying(boolean flying) {
        this.setIsFlying(flying);

        if (!flying) {
            this.setNoGravity(false);
            this.setWantsToLand(false);
        }

        this.moveControl = flying ? this.airMoveControl : this.landMoveControl;
        this.navigation = flying ? this.flyNavigation : this.landNavigation;
    }

    public boolean wantsToLand() {
        return this.entityData.get(WANTS_TO_LAND);
    }

    public void setWantsToLand(boolean wantsToLand) {
        this.entityData.set(WANTS_TO_LAND, wantsToLand);
    }

    public boolean isFlying() {
        return !this.onGround();
    }

    @Override
    protected boolean isFlapping() {
        return this.isInFlight();
    }
}
