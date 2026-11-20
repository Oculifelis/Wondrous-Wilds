package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.FireflyEntity;
import com.ineffa.wondrouswilds.registry.WondrousWildsAdvancementCriteria;
import java.util.EnumSet;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;

public class FireflyLandOnEntityGoal extends Goal {

    private final FireflyEntity firefly;
    private final Level world;
    private final Class<? extends LivingEntity> classToTarget;

    private LivingEntity entityToLandOn;

    private final TargetingConditions LAND_ON_PREDICATE = TargetingConditions.forNonCombat().range(8.0D).selector((entity, level) -> this.canEntityBeLandedOn(entity));

    public FireflyLandOnEntityGoal(FireflyEntity firefly, Class<? extends LivingEntity> classToTarget) {
        this.firefly = firefly;
        this.world = firefly.level();
        this.classToTarget = classToTarget;

        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!this.firefly.canSearchForEntityToLandOn()) return false;

        if (this.firefly.getRandom().nextInt(400) != 0) return false;

        return this.findEntityToLandOn();
    }

    @Override
    public void start() {
        if (!this.firefly.isInFlight()) this.firefly.setFlying(true);
    }

    @Override
    public boolean canContinueToUse() {
        return this.firefly.canSearchForEntityToLandOn() && this.canEntityBeLandedOn(this.entityToLandOn) && this.firefly.hasLineOfSight(this.entityToLandOn) && this.firefly.distanceTo(this.entityToLandOn) <= 16.0F;
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void tick() {
        this.firefly.getNavigation().moveTo(this.entityToLandOn, 1.0D);

        if (this.firefly.distanceTo(this.entityToLandOn) <= 1.0F) {
            this.firefly.setLandOnEntityCooldown(2400);

            this.firefly.setFlying(false);

            this.firefly.startRiding(this.entityToLandOn);

            if (this.world instanceof ServerLevel serverWorld && this.entityToLandOn instanceof ServerPlayer playerToLandOn) {
                WondrousWildsAdvancementCriteria.FIREFLY_LANDED_ON_HEAD.trigger(playerToLandOn, this.firefly);

                for (ServerPlayer player : serverWorld.players()) player.connection.send(new ClientboundSetPassengersPacket(playerToLandOn));
            }
        }
    }

    private boolean findEntityToLandOn() {
        return (this.entityToLandOn = getServerLevel(this.firefly).getNearestEntity(this.classToTarget, LAND_ON_PREDICATE, this.firefly, this.firefly.getX(), this.firefly.getY(), this.firefly.getZ(), this.firefly.getBoundingBox().inflate(8.0D))) != null;
    }

    private boolean canEntityBeLandedOn(LivingEntity entity) {
        return entity.getClass() != this.firefly.getClass() && !entity.isVehicle();
    }
}
