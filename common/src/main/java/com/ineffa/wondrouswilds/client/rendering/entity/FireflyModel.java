package com.ineffa.wondrouswilds.client.rendering.entity;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.entities.FireflyEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.model.GeoModel;

public class FireflyModel extends GeoModel<FireflyEntity> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(WondrousWilds.MOD_ID, "textures/entity/firefly/firefly.png");
    private static final ResourceLocation MODEL = ResourceLocation.fromNamespaceAndPath(WondrousWilds.MOD_ID, "geo/firefly.geo.json");
    private static final ResourceLocation ANIMATION = ResourceLocation.fromNamespaceAndPath(WondrousWilds.MOD_ID, "animations/firefly.animation.json");

    @Override
    public ResourceLocation getModelResource(FireflyEntity entity) {
        return MODEL;
    }

    @Override
    public ResourceLocation getAnimationResource(FireflyEntity entity) {
        return ANIMATION;
    }

    @Override
    public ResourceLocation getTextureResource(FireflyEntity entity) {
        return TEXTURE;
    }

    @Override
    public void applyMolangQueries(AnimationState<FireflyEntity> animationState, double animTime) {
        super.applyMolangQueries(animationState, animTime);
        FireflyEntity fireflyEntity = animationState.getAnimatable();


        float delta = animationState.getPartialTick();
        float limbSwing = fireflyEntity.walkAnimation.position(delta);
        float limbSwingAmount = fireflyEntity.walkAnimation.speed(delta);

        MathParser.setVariable("query.limb_swing", () -> limbSwing * 1.25D);
        MathParser.setVariable("query.limb_swing_amount", () -> limbSwingAmount + (0.9F * Mth.clamp(limbSwingAmount * 10.0F, 0.0F, 1.0F)));
    }
}
