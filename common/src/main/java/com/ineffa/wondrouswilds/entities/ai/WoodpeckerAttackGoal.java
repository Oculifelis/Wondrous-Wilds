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
    protected void checkAndPerformAttack(LivingEntity target, double squaredDistance) {
        double maxDistance = this.getAttackReachSqr(target);
        if (squaredDistance <= maxDistance) {
            if (!this.woodpecker.isPecking() && this.isTimeToAttack()) {
                this.resetAttackCooldown();

                this.woodpecker.startPeckChain(1);
            }
        }

        if (this.woodpecker.isPecking() && squaredDistance <= super.getAttackReachSqr(target)) this.woodpecker.setDeltaMovement(this.woodpecker.getDeltaMovement().scale(0.9D));
    }

    @Override
    protected double getAttackReachSqr(LivingEntity entity) {
        return super.getAttackReachSqr(entity) * 3.0D;
    }
}
