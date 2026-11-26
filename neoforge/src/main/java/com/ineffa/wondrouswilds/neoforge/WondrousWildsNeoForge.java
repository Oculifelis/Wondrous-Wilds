package com.ineffa.wondrouswilds.neoforge;

import com.ineffa.wondrouswilds.client.network.ClientPacketHandlers;
import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.config.WondrousWildsConfig;
import com.ineffa.wondrouswilds.entities.FireflyEntity;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.ineffa.wondrouswilds.networking.WondrousWildsPayload;
import com.ineffa.wondrouswilds.neoforge.world.FireflySpawnsModifier;
import com.ineffa.wondrouswilds.neoforge.world.GrassColorModifier;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import com.ineffa.wondrouswilds.registry.WondrousWildsEntities;
import com.ineffa.wondrouswilds.registry.WondrousWildsItems;
import com.ineffa.wondrouswilds.registry.WondrousWildsRegistration;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.AutoConfigClient;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(WondrousWilds.MOD_ID)
public class WondrousWildsNeoForge {

    public WondrousWildsNeoForge(IEventBus modBus, ModContainer container) {
        modBus.addListener(this::onRegister);
        modBus.addListener(this::onAttributes);
        modBus.addListener(this::onSpawnPlacements);
        modBus.addListener(this::onPayloads);

        NeoForge.EVENT_BUS.addListener(this::onEntityJoin);

        if (FMLEnvironment.getDist().isClient()) {
            WondrousWildsNeoForgeClient.init(modBus);
            container.registerExtensionPoint(IConfigScreenFactory.class,
                    (IConfigScreenFactory) (mod, parent) -> AutoConfigClient.getConfigScreen(WondrousWildsConfig.class, parent).get());
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void onRegister(RegisterEvent event) {
        // Blocks and items can only be created while the registries are unfrozen, so the common objects are built on the first event.
        WondrousWilds.init();

        if (event.getRegistryKey().equals(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS)) {
            event.register(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, WondrousWilds.id("grass_color"), () -> GrassColorModifier.CODEC);
            event.register(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, WondrousWilds.id("firefly_spawns"), () -> FireflySpawnsModifier.CODEC);
        }

        for (WondrousWildsRegistration.Entry entry : WondrousWildsRegistration.entries()) {
            if (entry.registry().equals(event.getRegistryKey())) {
                Object value = entry.value();
                event.register((net.minecraft.resources.ResourceKey) entry.registry(), entry.id(), () -> value);
            }
        }
    }

    private void onAttributes(EntityAttributeCreationEvent event) {
        event.put(WondrousWildsEntities.FIREFLY, FireflyEntity.createFireflyAttributes().build());
        event.put(WondrousWildsEntities.WOODPECKER, WoodpeckerEntity.createWoodpeckerAttributes().build());
    }

    private void onSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(WondrousWildsEntities.FIREFLY, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, FireflyEntity::canFireflySpawn, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(WondrousWildsEntities.WOODPECKER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    private void onPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar("1").playToClient(WondrousWildsPayload.TYPE, WondrousWildsPayload.CODEC, (payload, context) -> {
            // playToClient handlers only run on the client, so the client-only class is never loaded on a server.
            ClientPacketHandlers.handle(payload.packetId(), payload.buffer());
        });
    }

    private void onEntityJoin(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide())
            WondrousWilds.hookEntityCreation(event.getEntity());
    }

}
