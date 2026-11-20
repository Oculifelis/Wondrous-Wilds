package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.FireflyEntity;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.phys.Vec3;

public class FireflyWanderFlyingGoal extends Goal {

    private final FireflyEntity firefly;

    public FireflyWanderFlyingGoal(FireflyEntity fireflyEntity) {
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));

        this.firefly = fireflyEntity;
    }

    @Override
    public boolean canUse() {
        if (!this.firefly.isInFlight() || !this.firefly.canWander()) return false;

        return this.firefly.getNavigation().isDone() && this.firefly.getRandom().nextInt(10) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return this.firefly.isInFlight() && this.firefly.getNavigation().isInProgress();
    }

    @Override
    public void start() {
        Vec3 vec3d = this.getRandomLocation();
        if (vec3d != null) this.firefly.getNavigation().moveTo(this.firefly.getNavigation().createPath(BlockPos.containing(vec3d), 1), 1.0D);
    }

    @Override
    public void stop() {
        if (!this.firefly.wantsToLand()) {
            if (this.firefly.getRandom().nextInt(10) == 0) this.firefly.setWantsToLand(true);
        }
        else this.firefly.setFlying(false);
    }

    @Nullable
    private Vec3 getRandomLocation() {
        Vec3 vec3d = this.firefly.getViewVector(0.0F);
        Vec3 vec3d3 = HoverRandomPos.getPos(this.firefly, 8, 8, vec3d.x, vec3d.z, 1.5707964F, this.firefly.wantsToLand() ? 1 : 6, 1);

        if (vec3d3 != null) return vec3d3;

        return AirAndWaterRandomPos.getPos(this.firefly, 16, 8, -2, vec3d.x, vec3d.z, 1.5707963705062866D);
    }
}
