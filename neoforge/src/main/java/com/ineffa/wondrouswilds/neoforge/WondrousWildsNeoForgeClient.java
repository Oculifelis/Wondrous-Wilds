package com.ineffa.wondrouswilds.neoforge;

import com.ineffa.wondrouswilds.client.WondrousWildsClient;
import com.ineffa.wondrouswilds.client.rendering.WondrousWildsColorProviders;
import com.ineffa.wondrouswilds.client.rendering.entity.FireflyRenderer;
import com.ineffa.wondrouswilds.client.rendering.entity.WoodpeckerRenderer;
import com.ineffa.wondrouswilds.client.screen.ingame.NestBoxScreen;
import com.ineffa.wondrouswilds.registry.WondrousWildsEntities;
import com.ineffa.wondrouswilds.registry.WondrousWildsItems;
import com.ineffa.wondrouswilds.registry.WondrousWildsScreenHandlers;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

final class WondrousWildsNeoForgeClient {

    private WondrousWildsNeoForgeClient() {}

    static void init(IEventBus modBus) {
        modBus.addListener(WondrousWildsNeoForgeClient::onRenderers);
        modBus.addListener(WondrousWildsNeoForgeClient::onBlockColors);
        modBus.addListener(WondrousWildsNeoForgeClient::onItemColors);
        modBus.addListener(WondrousWildsNeoForgeClient::onMenuScreens);
        modBus.addListener(WondrousWildsNeoForgeClient::onClientSetup);
    }

    private static void onRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(WondrousWildsEntities.FIREFLY, FireflyRenderer::new);
        event.registerEntityRenderer(WondrousWildsEntities.WOODPECKER, WoodpeckerRenderer::new);
    }

    private static void onBlockColors(RegisterColorHandlersEvent.Block event) {
        WondrousWildsColorProviders.leafColors().forEach((block, color) -> event.register((state, level, pos, tintIndex) -> color, block));
    }

    private static void onItemColors(RegisterColorHandlersEvent.Item event) {
        WondrousWildsColorProviders.leafColors().forEach((block, color) -> event.register((stack, tintIndex) -> color, block));

        for (SpawnEggItem egg : new SpawnEggItem[]{(SpawnEggItem) WondrousWildsItems.FIREFLY_SPAWN_EGG, (SpawnEggItem) WondrousWildsItems.WOODPECKER_SPAWN_EGG})
            event.register((stack, tintIndex) -> egg.getColor(tintIndex), egg);
    }

    private static void onMenuScreens(RegisterMenuScreensEvent event) {
        event.register(WondrousWildsScreenHandlers.NEST_BOX, NestBoxScreen::new);
    }

    @SuppressWarnings("deprecation")
    private static void onClientSetup(FMLClientSetupEvent event) {
        WondrousWildsClient.cutoutBlocks().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout()));
        WondrousWildsClient.cutoutMippedBlocks().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutoutMipped()));
    }
}
