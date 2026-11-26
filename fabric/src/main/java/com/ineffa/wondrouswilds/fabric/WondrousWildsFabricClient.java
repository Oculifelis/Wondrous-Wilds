package com.ineffa.wondrouswilds.fabric;

import com.ineffa.wondrouswilds.client.network.ClientPacketHandlers;
import com.ineffa.wondrouswilds.client.WondrousWildsClient;
import com.ineffa.wondrouswilds.client.rendering.WondrousWildsColorProviders;
import com.ineffa.wondrouswilds.client.rendering.entity.FireflyRenderer;
import com.ineffa.wondrouswilds.client.rendering.entity.WoodpeckerRenderer;
import com.ineffa.wondrouswilds.client.screen.ingame.NestBoxScreen;
import com.ineffa.wondrouswilds.registry.WondrousWildsScreenHandlers;
import net.minecraft.client.gui.screens.MenuScreens;
import com.ineffa.wondrouswilds.networking.WondrousWildsPayload;
import com.ineffa.wondrouswilds.registry.WondrousWildsEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;

public class WondrousWildsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(WondrousWildsEntities.FIREFLY, FireflyRenderer::new);
        EntityRendererRegistry.register(WondrousWildsEntities.WOODPECKER, WoodpeckerRenderer::new);

        WondrousWildsClient.cutoutBlocks().forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutout()));
        WondrousWildsClient.cutoutMippedBlocks().forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutoutMipped()));

        WondrousWildsColorProviders.leafColors().forEach((block, color) -> {
            ColorProviderRegistry.BLOCK.register((state, level, pos, tintIndex) -> color, block);
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> color, block.asItem());
        });

        MenuScreens.register(WondrousWildsScreenHandlers.NEST_BOX, NestBoxScreen::new);

        ClientPlayNetworking.registerGlobalReceiver(WondrousWildsPayload.TYPE, (payload, context) -> ClientPacketHandlers.handle(payload.packetId(), payload.buffer()));
    }
}
