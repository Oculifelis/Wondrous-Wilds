package com.ineffa.wondrouswilds.forge;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.config.WondrousWildsConfig;
import com.ineffa.wondrouswilds.entities.FireflyEntity;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.ineffa.wondrouswilds.forge.network.ForgeNetwork;
import com.ineffa.wondrouswilds.forge.world.FireflySpawnsModifier;
import com.ineffa.wondrouswilds.forge.world.GrassColorModifier;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import com.ineffa.wondrouswilds.registry.WondrousWildsEntities;
import com.ineffa.wondrouswilds.registry.WondrousWildsItems;
import com.ineffa.wondrouswilds.registry.WondrousWildsRegistration;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod(WondrousWilds.MOD_ID)
public class WondrousWildsForge {

    public WondrousWildsForge() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        modBus.addListener(this::onRegister);
        modBus.addListener(this::onAttributes);
        modBus.addListener(this::onSpawnPlacements);
        modBus.addListener(this::onCommonSetup);

        MinecraftForge.EVENT_BUS.addListener(this::onEntityJoin);
        MinecraftForge.EVENT_BUS.addListener(this::onWandererTrades);
        MinecraftForge.EVENT_BUS.addListener(this::onToolModification);

        if (FMLEnvironment.dist.isClient()) {
            WondrousWildsForgeClient.init(modBus);
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> AutoConfig.getConfigScreen(WondrousWildsConfig.class, parent).get()));
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void onRegister(RegisterEvent event) {
        // Blocks and items can only be created while Forge has the registries unfrozen, so the common objects are built on the first event.
        WondrousWilds.init();

        if (event.getRegistryKey().equals(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS)) {
            event.register(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, WondrousWilds.id("grass_color"), () -> GrassColorModifier.CODEC);
            event.register(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, WondrousWilds.id("firefly_spawns"), () -> FireflySpawnsModifier.CODEC);
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

    private void onSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(WondrousWildsEntities.FIREFLY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, FireflyEntity::canFireflySpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(WondrousWildsEntities.WOODPECKER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        ForgeNetwork.register();
    }

    private void onEntityJoin(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide())
            WondrousWilds.hookEntityCreation(event.getEntity());
    }

    private void onWandererTrades(WandererTradesEvent event) {
        event.getGenericTrades().addAll(WondrousWildsItems.Trades.COMMON_WANDERER_TRADES);
        event.getRareTrades().addAll(WondrousWildsItems.Trades.RARE_WANDERER_TRADES);
    }

    private void onToolModification(BlockEvent.BlockToolModificationEvent event) {
        if (event.getToolAction() != ToolActions.AXE_STRIP) return;

        BlockState state = event.getState();
        Block stripped = WondrousWildsBlocks.HOLLOW_STRIPPABLES.get(state.getBlock());
        if (stripped != null)
            event.setFinalState(stripped.withPropertiesOf(state));
    }
}
