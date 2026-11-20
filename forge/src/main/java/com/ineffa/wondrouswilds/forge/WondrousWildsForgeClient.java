package com.ineffa.wondrouswilds.forge;

import com.ineffa.wondrouswilds.client.WondrousWildsClient;
import com.ineffa.wondrouswilds.client.rendering.WondrousWildsColorProviders;
import com.ineffa.wondrouswilds.client.rendering.entity.FireflyRenderer;
import com.ineffa.wondrouswilds.client.rendering.entity.WoodpeckerRenderer;
import com.ineffa.wondrouswilds.client.screen.WondrousWildsScreens;
import com.ineffa.wondrouswilds.registry.WondrousWildsEntities;
import com.ineffa.wondrouswilds.registry.WondrousWildsItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

final class WondrousWildsForgeClient {

    private WondrousWildsForgeClient() {}

    static void init(IEventBus modBus) {
        modBus.addListener(WondrousWildsForgeClient::onRenderers);
        modBus.addListener(WondrousWildsForgeClient::onBlockColors);
        modBus.addListener(WondrousWildsForgeClient::onItemColors);
        modBus.addListener(WondrousWildsForgeClient::onClientSetup);
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

    @SuppressWarnings("removal")
    private static void onClientSetup(FMLClientSetupEvent event) {
        WondrousWildsClient.cutoutBlocks().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout()));
        WondrousWildsClient.cutoutMippedBlocks().forEach(block -> ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutoutMipped()));

        event.enqueueWork(WondrousWildsScreens::register);
    }
}
