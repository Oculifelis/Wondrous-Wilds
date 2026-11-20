package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.phys.Vec3;

public class WoodpeckerWanderLandGoal extends WaterAvoidingRandomStrollGoal {

    private final WoodpeckerEntity woodpecker;

    public WoodpeckerWanderLandGoal(WoodpeckerEntity woodpeckerEntity, double speed) {
        super(woodpeckerEntity, speed);

        this.woodpecker = woodpeckerEntity;
    }

    @Override
    public boolean canUse() {
        if (this.woodpecker.isInFlight() || !this.woodpecker.canWander()) return false;

        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !this.woodpecker.isInFlight() && this.woodpecker.canWander() && super.canContinueToUse();
    }

    @Override
    public void stop() {
        super.stop();

        if (this.woodpecker.getRandom().nextBoolean()) this.woodpecker.setFlying(true);
    }

    @Nullable
    @Override
    protected Vec3 getPosition() {
        boolean moveTowardsNest = this.woodpecker.hasNestPos() && !Objects.requireNonNull(this.woodpecker.getNestPos()).closerToCenterThan(this.woodpecker.position(), this.woodpecker.getWanderRadiusFromNest());

        if (moveTowardsNest) {
            Vec3 direction = Vec3.atCenterOf(this.woodpecker.getNestPos()).subtract(this.woodpecker.position()).normalize();
            return AirAndWaterRandomPos.getPos(this.woodpecker, 10, 7, -2, direction.x, direction.z, 1.5707963705062866D);
        }

        return super.getPosition();
    }
}
