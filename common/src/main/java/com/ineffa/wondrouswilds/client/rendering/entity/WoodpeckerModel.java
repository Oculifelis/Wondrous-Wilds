package com.ineffa.wondrouswilds.client.rendering.entity;

import com.geckolib.loading.math.MolangQueries;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class WoodpeckerModel extends GeoModel<WoodpeckerEntity> {
    private static final Identifier MODEL = WondrousWilds.id("woodpecker");
    private static final Identifier TEXTURE = WondrousWilds.id("textures/entity/woodpecker.png");
    private static final Identifier ANIMATION = WondrousWilds.id("woodpecker");

    static {
        MolangQueries.<Object>setActorVariable("query.head_pitch", actor -> actor.animatable() instanceof WoodpeckerEntity entity
                ? Mth.lerp(actor.partialTick(), entity.xRotO, entity.getXRot()) : 0.0D);
        MolangQueries.<Object>setActorVariable("query.head_yaw", actor -> {
            if (!(actor.animatable() instanceof WoodpeckerEntity entity)) return 0.0D;
            float delta = actor.partialTick();
            float body = Mth.rotLerp(delta, entity.yBodyRotO, entity.getVisualRotationYInDegrees());
            float head = Mth.rotLerp(delta, entity.yHeadRotO, entity.getYHeadRot());
            return head - body;
        });
        MolangQueries.<Object>setActorVariable("query.swing", actor -> actor.animatable() instanceof WoodpeckerEntity entity
                ? entity.walkAnimation.position(actor.partialTick()) * 0.15D : 0.0D);
        MolangQueries.<Object>setActorVariable("query.swing_amount", actor -> {
            if (!(actor.animatable() instanceof WoodpeckerEntity entity)) return 0.0D;
            boolean flying = entity.isInFlight();
            float swingAmount = entity.walkAnimation.speed(actor.partialTick());
            float extraSwing = flying ? 0.0F : 0.5F * Mth.clamp(swingAmount * 10.0F, 0.0F, 1.0F);
            return Mth.clamp(swingAmount + extraSwing, 0.0D, flying ? 1.0D : 1.25D);
        });
        MolangQueries.<Object>setActorVariable("query.flap_angle", actor -> actor.animatable() instanceof WoodpeckerEntity entity
                ? Mth.lerp(actor.partialTick(), entity.prevFlapAngle, entity.flapAngle) : 0.0D);
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(WoodpeckerEntity entity) {
        return ANIMATION;
    }
}
