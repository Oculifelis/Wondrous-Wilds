package com.ineffa.wondrouswilds.client.rendering.entity;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.model.GeoModel;

public class WoodpeckerModel extends GeoModel<WoodpeckerEntity> {
    private static final ResourceLocation MODEL = new ResourceLocation(WondrousWilds.MOD_ID, "geo/woodpecker.geo.json");
    private static final ResourceLocation TEXTURE = new ResourceLocation(WondrousWilds.MOD_ID, "textures/entity/woodpecker.png");
    private static final ResourceLocation ANIMATION = new ResourceLocation(WondrousWilds.MOD_ID, "animations/woodpecker.animation.json");

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
    public void applyMolangQueries(WoodpeckerEntity entity, double animTime) {
        super.applyMolangQueries(entity, animTime);

        MolangParser parser = MolangParser.INSTANCE;

        boolean flying = entity.isInFlight();

        float delta = Minecraft.getInstance().getFrameTime();

        float headPitch = Mth.lerp(delta, entity.xRotO, entity.getXRot());
        float f = Mth.rotLerp(delta, entity.yBodyRotO, entity.getVisualRotationYInDegrees());
        float f1 = Mth.rotLerp(delta, entity.yHeadRotO, entity.getYHeadRot());
        float headYaw = f1 - f;

        parser.setValue("query.head_pitch", () -> headPitch);
        parser.setValue("query.head_yaw", () -> headYaw);

        float swing = entity.walkAnimation.position(delta);
        float swingAmount = entity.walkAnimation.speed(delta);
        float extraSwing = flying ? 0.0F : 0.5F * Mth.clamp(swingAmount * 10.0F, 0.0F, 1.0F);

        parser.setValue("query.swing", () -> swing * 0.15D);
        parser.setValue("query.swing_amount", () -> Mth.clamp(swingAmount + extraSwing, 0.0D, flying ? 1.0D : 1.25D));

        float flapAngle = Mth.lerp(delta, entity.prevFlapAngle, entity.flapAngle);

        parser.setValue("query.flap_angle", () -> flapAngle);
    }
}
