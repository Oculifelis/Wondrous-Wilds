package com.ineffa.wondrouswilds.client.rendering.entity;

import com.geckolib.constant.DataTickets;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.builtin.TextureLayerGeoLayer;
import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.client.rendering.WondrousWildsRenderTypes;
import com.ineffa.wondrouswilds.entities.FireflyEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import org.jspecify.annotations.Nullable;

public class FireflyRenderer extends GeoEntityRenderer<FireflyEntity, LivingEntityRenderState> {

    public FireflyRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new FireflyModel());
        this.withRenderLayer(new FireflyGlowLayer(this));
        this.shadowRadius = 0.2F;
    }

    @Override
    public @Nullable RenderType getRenderType(LivingEntityRenderState renderState, Identifier texture) {
        if (renderState.isInvisible)
            return super.getRenderType(renderState, texture);
        return RenderTypes.entityTranslucent(texture);
    }

    // The original set noCulling so the glow never popped out at screen edges.
    @Override
    protected boolean affectedByCulling(FireflyEntity entity) {
        return false;
    }

    private static class FireflyGlowLayer extends TextureLayerGeoLayer<FireflyEntity, Void, LivingEntityRenderState> {
        private static final Identifier GLOW_TEXTURE = WondrousWilds.id("textures/entity/firefly/firefly_glow.png");
        private static final DataTicket<Float> GLOW_ALPHA = DataTicket.create(WondrousWilds.MOD_ID + "_firefly_glow_alpha", Float.class);

        FireflyGlowLayer(GeoRenderer<FireflyEntity, Void, LivingEntityRenderState> renderer) {
            super(renderer, GLOW_TEXTURE, WondrousWildsRenderTypes::getTranslucentGlow);
        }

        @Override
        public void addRenderData(FireflyEntity entity, @Nullable Void relatedObject, LivingEntityRenderState renderState, float partialTick) {
            float ageInTicks = entity.tickCount + partialTick;
            renderState.addGeckolibData(GLOW_ALPHA, Mth.clamp((Mth.cos(ageInTicks * 0.1F) * 2.0F) - 1.0F, 0.0F, 1.0F));
        }

        @Override
        public void submitRenderTask(RenderPassInfo<LivingEntityRenderState> renderPassInfo, SubmitNodeCollector renderTasks) {
            LivingEntityRenderState state = renderPassInfo.renderState();
            int color = state.getOrDefaultGeckolibData(DataTickets.RENDER_COLOR, 0xFFFFFFFF);
            int light = state.getOrDefaultGeckolibData(DataTickets.PACKED_LIGHT, WondrousWildsRenderTypes.FULL_BRIGHT);
            float alpha = state.getOrDefaultGeckolibData(GLOW_ALPHA, 0.0F);

            state.addGeckolibData(DataTickets.RENDER_COLOR, ARGB.colorFromFloat(alpha, 1.0F, 1.0F, 1.0F));
            state.addGeckolibData(DataTickets.PACKED_LIGHT, WondrousWildsRenderTypes.FULL_BRIGHT);
            super.submitRenderTask(renderPassInfo, renderTasks);
            state.addGeckolibData(DataTickets.RENDER_COLOR, color);
            state.addGeckolibData(DataTickets.PACKED_LIGHT, light);
        }
    }
}
