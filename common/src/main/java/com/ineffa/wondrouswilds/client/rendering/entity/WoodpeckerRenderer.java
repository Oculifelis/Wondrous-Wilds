package com.ineffa.wondrouswilds.client.rendering.entity;

import com.geckolib.cache.model.GeoBone;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.layer.builtin.ItemInHandGeoLayer;
import com.geckolib.util.RenderUtil;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.List;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class WoodpeckerRenderer extends GeoEntityRenderer<WoodpeckerEntity, LivingEntityRenderState> {

    public static final String HELD_ITEM_BONE_NAME = "heldItem";

    public WoodpeckerRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new WoodpeckerModel());

        this.withRenderLayer(new ItemInHandGeoLayer<WoodpeckerEntity, Void, LivingEntityRenderState>(ctx, this, HELD_ITEM_BONE_NAME, null) {
            @Override
            protected List<RenderData> getRelevantBones(WoodpeckerEntity woodpecker, @Nullable Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
                ItemStack stack = woodpecker.getMainHandItem();
                if (stack.isEmpty()) return List.of();
                ItemDisplayContext context = ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
                return List.of(RenderData.item(HELD_ITEM_BONE_NAME, context, RenderUtil.createRenderStateForItem(stack, this.itemModelResolver, context, woodpecker)));
            }

            @Override
            protected void submitItemStackRender(PoseStack poseStack, GeoBone bone, ItemStackRenderState stackState, ItemDisplayContext displayContext, LivingEntityRenderState renderState, SubmitNodeCollector renderTasks, int packedLight) {
                poseStack.rotate(Axis.XP.rotationDegrees(-90.0F));
                poseStack.translate(0.0D, 0.0D, -0.035D);
                poseStack.scale(0.5F, 0.5F, 0.5F);

                super.submitItemStackRender(poseStack, bone, stackState, displayContext, renderState, renderTasks, packedLight);
            }
        });

        this.shadowRadius = 0.225F;
    }

    // The original set noCulling on the woodpecker.
    @Override
    protected boolean affectedByCulling(WoodpeckerEntity entity) {
        return false;
    }

    @Override
    public @Nullable RenderType getRenderType(LivingEntityRenderState renderState, Identifier texture) {
        if (renderState.isInvisible)
            return super.getRenderType(renderState, texture);
        return RenderTypes.entityTranslucent(texture);
    }
}
