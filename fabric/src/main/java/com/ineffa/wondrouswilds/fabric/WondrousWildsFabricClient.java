package com.ineffa.wondrouswilds.fabric;

import com.ineffa.wondrouswilds.client.network.ClientPacketHandlers;
import com.ineffa.wondrouswilds.client.rendering.WondrousWildsColorProviders;
import com.ineffa.wondrouswilds.client.rendering.entity.FireflyRenderer;
import com.ineffa.wondrouswilds.client.rendering.entity.WoodpeckerRenderer;
import com.ineffa.wondrouswilds.client.screen.ingame.NestBoxScreen;
import com.ineffa.wondrouswilds.networking.WondrousWildsPayload;
import com.ineffa.wondrouswilds.registry.WondrousWildsEntities;
import com.ineffa.wondrouswilds.registry.WondrousWildsScreenHandlers;
import java.util.List;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screens.MenuScreens;

public class WondrousWildsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(WondrousWildsEntities.FIREFLY, FireflyRenderer::new);
        EntityRendererRegistry.register(WondrousWildsEntities.WOODPECKER, WoodpeckerRenderer::new);

        // Cutout layers come from the textures on 26.x. Leaf item tints live in assets/wondrouswilds/items.
        WondrousWildsColorProviders.leafColors().forEach((block, color) -> BlockColorRegistry.register(List.of(state -> color), block));

        MenuScreens.register(WondrousWildsScreenHandlers.NEST_BOX, NestBoxScreen::new);

        ClientPlayNetworking.registerGlobalReceiver(WondrousWildsPayload.TYPE, (payload, context) -> ClientPacketHandlers.handle(payload.packetId(), payload.buffer()));
    }
}
