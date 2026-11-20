package com.ineffa.wondrouswilds.client.rendering.entity;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.model.GeoModel;

public class WoodpeckerModel extends GeoModel<WoodpeckerEntity> {
    private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(WondrousWilds.MOD_ID, "geo/woodpecker.geo.json");
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(WondrousWilds.MOD_ID, "textures/entity/woodpecker.png");
    private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath(WondrousWilds.MOD_ID, "animations/woodpecker.animation.json");

    @Override
    public ResourceLocation getModelResource(WoodpeckerEntity entity) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(WoodpeckerEntity entity) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(WoodpeckerEntity entity) {
        return ANIMATION;
    }

    @Override
    public void applyMolangQueries(AnimationState<WoodpeckerEntity> animationState, double animTime) {
        super.applyMolangQueries(animationState, animTime);
        WoodpeckerEntity entity = animationState.getAnimatable();


        boolean flying = entity.isInFlight();

        float delta = animationState.getPartialTick();

        float headPitch = Mth.lerp(delta, entity.xRotO, entity.getXRot());
        float f = Mth.rotLerp(delta, entity.yBodyRotO, entity.getVisualRotationYInDegrees());
        float f1 = Mth.rotLerp(delta, entity.yHeadRotO, entity.getYHeadRot());
        float headYaw = f1 - f;

        MathParser.setVariable("query.head_pitch", () -> headPitch);
        MathParser.setVariable("query.head_yaw", () -> headYaw);

        float swing = entity.walkAnimation.position(delta);
        float swingAmount = entity.walkAnimation.speed(delta);
        float extraSwing = flying ? 0.0F : 0.5F * Mth.clamp(swingAmount * 10.0F, 0.0F, 1.0F);

        MathParser.setVariable("query.swing", () -> swing * 0.15D);
        MathParser.setVariable("query.swing_amount", () -> Mth.clamp(swingAmount + extraSwing, 0.0D, flying ? 1.0D : 1.25D));

        float flapAngle = Mth.lerp(delta, entity.prevFlapAngle, entity.flapAngle);

        MathParser.setVariable("query.flap_angle", () -> flapAngle);
    }
}
