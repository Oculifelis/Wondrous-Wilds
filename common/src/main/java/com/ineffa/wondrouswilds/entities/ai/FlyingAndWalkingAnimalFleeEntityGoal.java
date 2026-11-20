package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.FlyingAndWalkingAnimalEntity;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.phys.Vec3;

public class FlyingAndWalkingAnimalFleeEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {

    private final FlyingAndWalkingAnimalEntity animal;
    private final TargetingConditions fleeFromPredicate;
    private final double slowSpeed, fastSpeed;
    private Vec3 targetPos;

    public FlyingAndWalkingAnimalFleeEntityGoal(FlyingAndWalkingAnimalEntity fleeingAnimal, Class<T> classToFleeFrom, float fleeDistance, double fleeSlowSpeed, double fleeFastSpeed) {
        this(fleeingAnimal, classToFleeFrom, fleeDistance, fleeSlowSpeed, fleeFastSpeed, livingEntity -> true);
    }

    public FlyingAndWalkingAnimalFleeEntityGoal(FlyingAndWalkingAnimalEntity fleeingAnimal, Class<T> classToFleeFrom, float fleeDistance, double fleeSlowSpeed, double fleeFastSpeed, Predicate<LivingEntity> inclusionSelector) {
        super(fleeingAnimal, classToFleeFrom, fleeDistance, fleeSlowSpeed, fleeFastSpeed, inclusionSelector);

        this.animal = fleeingAnimal;
        this.slowSpeed = fleeSlowSpeed;
        this.fastSpeed = fleeFastSpeed;

        this.fleeFromPredicate = TargetingConditions.forCombat().range(fleeDistance).selector(inclusionSelector);
    }

    @Override
    public boolean canUse() {
        this.toAvoid = this.animal.level().getNearestEntity(this.animal.level().getEntitiesOfClass(this.avoidClass, this.animal.getBoundingBox().inflate(this.maxDist, this.maxDist, this.maxDist), livingEntity -> true), this.fleeFromPredicate, this.animal, this.animal.getX(), this.animal.getY(), this.animal.getZ());
        if (this.toAvoid == null) return false;

        Vec3 direction = Vec3.atCenterOf(this.toAvoid.blockPosition()).subtract(this.animal.position()).normalize().reverse();

        Vec3 fleePos;

        Vec3 groundLocation = HoverRandomPos.getPos(this.animal, 24, 12, direction.x, direction.z, 1.5707964F, 12, 1);
        if (groundLocation != null) fleePos = groundLocation;

        else fleePos = AirAndWaterRandomPos.getPos(this.animal, 24, 12, -2, direction.x, direction.z, 1.5707963705062866D);

        if (fleePos == null) return false;

        this.targetPos = fleePos;
        return true;
    }

    @Override
    public void start() {
        if (!this.animal.isInFlight()) this.animal.setFlying(true);
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.canUse();
    }

    @Override
    public void tick() {
        double speed = this.mob.distanceToSqr(this.toAvoid) < 49.0D ? this.fastSpeed : this.slowSpeed;
        this.animal.getNavigation().moveTo(this.targetPos.x(), this.targetPos.y(), this.targetPos.z(), speed);
    }
}
