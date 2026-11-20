package com.ineffa.wondrouswilds.client.rendering.entity;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class WoodpeckerRenderer extends GeoEntityRenderer<WoodpeckerEntity> {

    public static final String HELD_ITEM_BONE_NAME = "heldItem";

    public WoodpeckerRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new WoodpeckerModel());

        this.addRenderLayer(new BlockAndItemGeoLayer<>(this, (bone, woodpecker) -> bone.getName().equals(HELD_ITEM_BONE_NAME) ? woodpecker.getMainHandItem() : null, (bone, woodpecker) -> null) {
            @Override
            protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, WoodpeckerEntity woodpecker) {
                return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
            }

            @Override
            protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, WoodpeckerEntity woodpecker, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
                poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
                poseStack.translate(0.0D, 0.0D, -0.035D);
                poseStack.scale(0.5F, 0.5F, 0.5F);

                super.renderStackForBone(poseStack, bone, stack, woodpecker, bufferSource, partialTick, packedLight, packedOverlay);
            }
        });

        this.shadowRadius = 0.225F;
    }

    @Override
    public RenderType getRenderType(WoodpeckerEntity entity, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(texture);
    }
}
