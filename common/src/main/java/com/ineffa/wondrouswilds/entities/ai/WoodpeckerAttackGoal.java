package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class WoodpeckerAttackGoal extends MeleeAttackGoal {

    private final WoodpeckerEntity woodpecker;

    public WoodpeckerAttackGoal(WoodpeckerEntity woodpecker, double speed, boolean pauseWhenMobIdle) {
        super(woodpecker, speed, pauseWhenMobIdle);

        this.woodpecker = woodpecker;
    }

    @Override
    public void start() {
        super.start();

        if (!this.woodpecker.isInFlight()) this.woodpecker.setFlying(true);
    }

    @Override
    public void stop() {
        super.stop();

        if (this.woodpecker.isPecking()) this.woodpecker.stopPecking(true);
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        // 1.21 dropped Mob#getPerceivedTargetDistanceSquareForMeleeAttack; it was the plain squared distance unless the target rode a vehicle.
        double squaredDistance = this.mob.distanceToSqr(target);
        double maxDistance = this.getAttackReachSqr(target);
        if (squaredDistance <= maxDistance) {
            if (!this.woodpecker.isPecking() && this.isTimeToAttack()) {
                this.resetAttackCooldown();

                this.woodpecker.startPeckChain(1);
            }
        }

        if (this.woodpecker.isPecking() && squaredDistance <= this.getBaseAttackReachSqr(target)) this.woodpecker.setDeltaMovement(this.woodpecker.getDeltaMovement().scale(0.9D));
    }

    /** The old melee reach formula, which newer versions replaced with a box check. */
    private double getBaseAttackReachSqr(LivingEntity entity) {
        return this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + entity.getBbWidth();
    }

    protected double getAttackReachSqr(LivingEntity entity) {
        return this.getBaseAttackReachSqr(entity) * 3.0D;
    }
}
