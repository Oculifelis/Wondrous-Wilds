package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.FireflyEntity;
import com.ineffa.wondrouswilds.registry.WondrousWildsTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;

public class FireflyHideGoal extends MoveToBlockGoal {

    public FireflyHideGoal(FireflyEntity mob, double speed, int range, int maxYDifference) {
        super(mob, speed, range, maxYDifference);
    }

    @Override
    public boolean canUse() {
        if (!((FireflyEntity) this.mob).shouldHide()) return false;

        return this.findNearestBlock();
    }

    @Override
    public boolean canContinueToUse() {
        return this.isValidTarget(this.mob.level(), this.blockPos);
    }

    @Override
    public void tick() {
        if (this.isReachedTarget()) {
            BlockPos pos = this.getMoveToTarget();
            this.mob.level().levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(this.mob.level().getBlockState(pos)));

            this.mob.discard();
        }

        if (!((FireflyEntity) this.mob).isInFlight()) {
            ((FireflyEntity) this.mob).setFlying(true);
        }

        super.tick();
    }

    @Override
    protected boolean isValidTarget(LevelReader world, BlockPos pos) {
        return world.getBlockState(pos).is(WondrousWildsTags.BlockTags.FIREFLIES_HIDE_IN);
    }

    @Override
    protected BlockPos getMoveToTarget() {
        return this.blockPos;
    }
}
