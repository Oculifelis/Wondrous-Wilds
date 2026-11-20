package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.phys.Vec3;

public class WoodpeckerWanderFlyingGoal extends Goal {

    private final WoodpeckerEntity woodpecker;

    public WoodpeckerWanderFlyingGoal(WoodpeckerEntity woodpeckerEntity) {
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));

        this.woodpecker = woodpeckerEntity;
    }

    @Override
    public boolean canUse() {
        if (!this.woodpecker.isInFlight() || !this.woodpecker.canWander()) return false;

        return this.woodpecker.getNavigation().isDone() && this.woodpecker.getRandom().nextInt(10) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return this.woodpecker.isInFlight() && this.woodpecker.canWander() && this.woodpecker.getNavigation().isInProgress();
    }

    @Override
    public void start() {
        Vec3 vec3d = this.getRandomLocation();
        if (vec3d != null) this.woodpecker.getNavigation().moveTo(this.woodpecker.getNavigation().createPath(BlockPos.containing(vec3d), 1), 1.0D);
    }

    @Override
    public void stop() {
        if (!this.woodpecker.wantsToLand()) {
            if (this.woodpecker.getRandom().nextInt(30) == 0) this.woodpecker.setWantsToLand(true);
        }
        else this.woodpecker.setFlying(false);
    }

    @Nullable
    private Vec3 getRandomLocation() {
        boolean moveTowardsNest = this.woodpecker.hasNestPos() && !Objects.requireNonNull(this.woodpecker.getNestPos()).closerToCenterThan(this.woodpecker.position(), this.woodpecker.getWanderRadiusFromNest());

        Vec3 direction = moveTowardsNest ? Vec3.atCenterOf(this.woodpecker.getNestPos()).subtract(this.woodpecker.position()).normalize() : this.woodpecker.getViewVector(0.0F);

        Vec3 groundLocation = HoverRandomPos.getPos(this.woodpecker, 16, 8, direction.x, direction.z, 1.5707964F, this.woodpecker.wantsToLand() ? 1 : 6, 1);
        if (groundLocation != null) return groundLocation;

        return AirAndWaterRandomPos.getPos(this.woodpecker, 16, 8, -2, direction.x, direction.z, 1.5707963705062866D);
    }
}
