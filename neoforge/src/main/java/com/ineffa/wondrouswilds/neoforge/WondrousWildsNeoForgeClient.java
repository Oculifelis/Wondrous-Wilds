package com.ineffa.wondrouswilds.neoforge;

import com.ineffa.wondrouswilds.client.rendering.WondrousWildsColorProviders;
import com.ineffa.wondrouswilds.client.rendering.entity.FireflyRenderer;
import com.ineffa.wondrouswilds.client.rendering.entity.WoodpeckerRenderer;
import com.ineffa.wondrouswilds.client.screen.ingame.NestBoxScreen;
import com.ineffa.wondrouswilds.registry.WondrousWildsEntities;
import com.ineffa.wondrouswilds.registry.WondrousWildsScreenHandlers;
import java.util.List;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

final class WondrousWildsNeoForgeClient {

    private WondrousWildsNeoForgeClient() {}

    static void init(IEventBus modBus) {
        modBus.addListener(WondrousWildsNeoForgeClient::onRenderers);
        modBus.addListener(WondrousWildsNeoForgeClient::onBlockColors);
        modBus.addListener(WondrousWildsNeoForgeClient::onMenuScreens);
    }

    private static void onRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(WondrousWildsEntities.FIREFLY, FireflyRenderer::new);
        event.registerEntityRenderer(WondrousWildsEntities.WOODPECKER, WoodpeckerRenderer::new);
    }

    // Cutout layers come from the textures on 26.x. Leaf item tints live in assets/wondrouswilds/items.
    private static void onBlockColors(RegisterColorHandlersEvent.BlockTintSources event) {
        WondrousWildsColorProviders.leafColors().forEach((block, color) -> event.register(List.of(state -> color), block));
    }

    private static void onMenuScreens(RegisterMenuScreensEvent event) {
        event.register(WondrousWildsScreenHandlers.NEST_BOX, NestBoxScreen::new);
    }
}
