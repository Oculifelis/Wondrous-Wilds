package com.ineffa.wondrouswilds.client.rendering.entity;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.client.rendering.WondrousWildsRenderTypes;
import com.ineffa.wondrouswilds.entities.FireflyEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class FireflyRenderer extends GeoEntityRenderer<FireflyEntity> {

    public FireflyRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new FireflyModel());
        this.addRenderLayer(new FireflyGlowLayer(this));
        this.shadowRadius = 0.2F;
    }

    @Override
    public RenderType getRenderType(FireflyEntity entity, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }

    private static class FireflyGlowLayer extends GeoRenderLayer<FireflyEntity> {
        private static final ResourceLocation GLOW_TEXTURE = new ResourceLocation(WondrousWilds.MOD_ID, "textures/entity/firefly/firefly_glow.png");

        public FireflyGlowLayer(GeoRenderer<FireflyEntity> renderer) {
            super(renderer);
        }

        @Override
        public void render(PoseStack poseStack, FireflyEntity entity, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
            RenderType glowType = WondrousWildsRenderTypes.getTranslucentGlow(GLOW_TEXTURE);
            float ageInTicks = entity.tickCount + partialTick;
            float alpha = Mth.clamp((Mth.cos(ageInTicks * 0.1F) * 2.0F) - 1.0F, 0.0F, 1.0F);

            this.getRenderer().reRender(bakedModel, poseStack, bufferSource, entity, glowType, bufferSource.getBuffer(glowType), partialTick, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, alpha);
        }
    }
}
