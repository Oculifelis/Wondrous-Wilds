package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.FlyingAndWalkingAnimalEntity;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.phys.Vec3;

public class FlyingAndWalkingAnimalEscapeDangerGoal extends PanicGoal {

    private final FlyingAndWalkingAnimalEntity animal;

    private final int horizontalFleeRange, verticalFleeRange;

    public FlyingAndWalkingAnimalEscapeDangerGoal(FlyingAndWalkingAnimalEntity mob, double speed, int horizontalFleeRange, int verticalFleeRange) {
        super(mob, speed);

        this.animal = mob;

        this.horizontalFleeRange = horizontalFleeRange;
        this.verticalFleeRange = verticalFleeRange;
    }

    @Override
    public void start() {
        super.start();

        if (!this.animal.isInFlight()) this.animal.setFlying(true);
    }

    @Override
    protected boolean findRandomPosition() {
        Vec3 target;

        Vec3 direction = this.mob.getViewVector(0.0F);

        Vec3 groundLocation = HoverRandomPos.getPos(this.mob, this.horizontalFleeRange, this.verticalFleeRange, direction.x, direction.z, 1.5707964F, 6, 1);
        if (groundLocation != null) target = groundLocation;

        else target = AirAndWaterRandomPos.getPos(this.mob, this.horizontalFleeRange, this.verticalFleeRange, -2, direction.x, direction.z, 1.5707963705062866D);

        if (target == null) return false;

        this.posX = target.x;
        this.posY = target.y;
        this.posZ = target.z;
        return true;
    }
}
