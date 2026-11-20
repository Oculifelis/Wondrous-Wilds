package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.blocks.entity.InhabitableNestBlockEntity;
import com.ineffa.wondrouswilds.entities.FlyingAndWalkingAnimalEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import com.ineffa.wondrouswilds.entities.BlockNester;

public class FindOrReturnToBlockNestGoal extends MoveToBlockGoal {

    private final BlockNester nester;
    private final Mob nesterEntity;

    private int nextCheckDelay = 40;
    private boolean shouldStop = false;

    private boolean lookingForNest = false;
    private int ticksTryingToClaimNest;

    public FindOrReturnToBlockNestGoal(BlockNester nester, double speed, int range, int maxYDifference) {
        super((PathfinderMob) nester, speed, range, maxYDifference);

        this.nester = nester;
        this.nesterEntity = (Mob) nester;
    }

    @Override
    public boolean canUse() {
        if (this.nextCheckDelay > 0) {
            --this.nextCheckDelay;
            return false;
        }
        else this.nextCheckDelay = 40;

        if (!(this.nester.shouldReturnToNest() || (this.nester.shouldFindNest() && this.nester.getCannotInhabitNestTicks() <= 0))) return false;

        this.lookingForNest = this.nester.shouldFindNest();

        return this.findNearestBlock();
    }

    @Override
    public void start() {
        super.start();

        this.shouldStop = false;

        this.ticksTryingToClaimNest = 0;

        if (this.nesterEntity instanceof FlyingAndWalkingAnimalEntity flyingEntity && !flyingEntity.isInFlight()) flyingEntity.setFlying(true);
    }

    @Override
    public boolean canContinueToUse() {
        return !this.shouldStop && this.isValidTarget(this.nesterEntity.level(), this.blockPos);
    }

    @Override
    public void stop() {
        super.stop();

        this.nextCheckDelay = 40;

        if (!(this.nesterEntity.level().getBlockEntity(this.getMoveToTarget()) instanceof InhabitableNestBlockEntity nestBlock)) {
            this.nester.clearNestPos();
            return;
        }

        if (this.isReachedTarget()) {
            if (!nestBlock.tryAddingInhabitant(this.nester)) {
                this.nester.setCannotInhabitNestTicks(this.nester.getMinTicksOutOfNest());
                this.nester.clearNestPos();
            }
        }
    }

    @Override
    public void tick() {
        if (!this.lookingForNest) {
            if (this.nesterEntity.tickCount % 400 == 0 && !this.nester.shouldReturnToNest()) {
                this.shouldStop = true;
                return;
            }
        }
        else if (this.ticksTryingToClaimNest++ >= 400) {
            this.nester.setCannotInhabitNestTicks(this.nester.getMinTicksOutOfNest());
            this.shouldStop = true;
            return;
        }

        super.tick();

        if (this.isReachedTarget()) this.shouldStop = true;
    }

    @Override
    protected boolean isValidTarget(LevelReader world, BlockPos pos) {
        if (!this.lookingForNest) return true;

        return world.getBlockEntity(pos) instanceof InhabitableNestBlockEntity;
    }

    @Override
    protected BlockPos getMoveToTarget() {
        return this.blockPos;
    }

    @Override
    protected boolean findNearestBlock() {
        if (!this.lookingForNest) {
            this.blockPos = this.nester.getNestPos();
            return true;
        }

        return super.findNearestBlock();
    }

    @Override
    public double acceptedDistance() {
        return 1.5D;
    }
}
