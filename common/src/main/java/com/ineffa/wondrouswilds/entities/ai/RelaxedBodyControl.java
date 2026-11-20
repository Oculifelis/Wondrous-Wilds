package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.FireflyEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class RelaxedBodyControl extends BodyRotationControl {

    protected final Mob entity;
    protected static final float BODY_KEEP_UP_THRESHOLD = 15.0F;
    protected float lastHeadYaw;
    protected int bodyAdjustTicks;

    public RelaxedBodyControl(Mob entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    public void clientTick() {
        if (this.isMoving()) {
            this.entity.yBodyRot = this.entity.getYRot();
            this.rotateHeadIfNecessary();
            this.lastHeadYaw = this.entity.yHeadRot;
            this.bodyAdjustTicks = 0;
            return;
        }
        if (this.notCarryingMobPassengers()) {
            if (Math.abs(this.entity.yHeadRot - this.lastHeadYaw) > BODY_KEEP_UP_THRESHOLD) {
                this.bodyAdjustTicks = 0;
                this.lastHeadYaw = this.entity.yHeadRot;
                this.rotateBodyIfNecessary();
            }
            else if (this.shouldSlowlyAdjustBody()) {
                ++this.bodyAdjustTicks;
                if (this.bodyAdjustTicks > 10) this.rotateHeadTowardsFront();

                this.entity.setYRot(this.entity.getVisualRotationYInDegrees()); // Fixes desync present in vanilla body controller
            }
        }
    }

    private void rotateBodyIfNecessary() {
        this.entity.yBodyRot = Mth.rotateIfNecessary(this.entity.yBodyRot, this.entity.yHeadRot, this.entity.getMaxHeadYRot());
    }

    private void rotateHeadIfNecessary() {
        this.entity.yHeadRot = Mth.rotateIfNecessary(this.entity.yHeadRot, this.entity.yBodyRot, this.entity.getMaxHeadYRot());
    }

    private boolean notCarryingMobPassengers() {
        Entity firstPassenger = this.entity.getFirstPassenger();

        if (firstPassenger instanceof FireflyEntity) return true;

        return !(firstPassenger instanceof Mob);
    }

    private boolean isMoving() {
        double e;
        double d = this.entity.getX() - this.entity.xo;
        return d * d + (e = this.entity.getZ() - this.entity.zo) * e > 2.500000277905201E-7;
    }

    private void rotateHeadTowardsFront() {
        int i = this.bodyAdjustTicks - 10;
        float f = Mth.clamp((float)i / 10.0f, 0.0f, 1.0f);
        float g = (float)this.entity.getMaxHeadYRot() * (1.0f - f);
        this.entity.yBodyRot = Mth.rotateIfNecessary(this.entity.yBodyRot, this.entity.yHeadRot, g);
    }

    protected boolean shouldSlowlyAdjustBody() {
        return false;
    }
}
