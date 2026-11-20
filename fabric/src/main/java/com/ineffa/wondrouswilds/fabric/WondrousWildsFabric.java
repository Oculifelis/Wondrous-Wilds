package com.ineffa.wondrouswilds.fabric;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.entities.FireflyEntity;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import com.ineffa.wondrouswilds.registry.WondrousWildsEntities;
import com.ineffa.wondrouswilds.registry.WondrousWildsFeatures;
import com.ineffa.wondrouswilds.registry.WondrousWildsItems;
import com.ineffa.wondrouswilds.registry.WondrousWildsRegistration;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Predicate;

public class WondrousWildsFabric implements ModInitializer {

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void onInitialize() {
        WondrousWilds.init();

        for (WondrousWildsRegistration.Entry entry : WondrousWildsRegistration.entries()) {
            Registry registry = BuiltInRegistries.REGISTRY.get(entry.registry().location());
            Registry.register(registry, entry.id(), entry.value());
        }

        FabricDefaultAttributeRegistry.register(WondrousWildsEntities.FIREFLY, FireflyEntity.createFireflyAttributes());
        FabricDefaultAttributeRegistry.register(WondrousWildsEntities.WOODPECKER, WoodpeckerEntity.createWoodpeckerAttributes());
        SpawnPlacements.register(WondrousWildsEntities.FIREFLY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, FireflyEntity::canFireflySpawn);
        SpawnPlacements.register(WondrousWildsEntities.WOODPECKER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Animal::checkAnimalSpawnRules);

        if (WondrousWilds.config.mobSettings.firefliesSpawnNaturally)
            BiomeModifications.addSpawn(context -> context.hasTag(ConventionalBiomeTags.IN_OVERWORLD), MobCategory.WATER_AMBIENT, WondrousWildsEntities.FIREFLY,
                    WondrousWildsEntities.FIREFLY_SPAWN_WEIGHT, WondrousWildsEntities.FIREFLY_SPAWN_MIN, WondrousWildsEntities.FIREFLY_SPAWN_MAX);

        WondrousWildsBlocks.HOLLOW_STRIPPABLES.forEach(StrippableBlockRegistry::register);

        if (!WondrousWildsItems.Trades.COMMON_WANDERER_TRADES.isEmpty())
            TradeOfferHelper.registerWanderingTraderOffers(1, factories -> factories.addAll(WondrousWildsItems.Trades.COMMON_WANDERER_TRADES));
        if (!WondrousWildsItems.Trades.RARE_WANDERER_TRADES.isEmpty())
            TradeOfferHelper.registerWanderingTraderOffers(2, factories -> factories.addAll(WondrousWildsItems.Trades.RARE_WANDERER_TRADES));

        upgradeBirchForests();

        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> WondrousWilds.hookEntityCreation(entity));
    }

    private static void upgradeBirchForests() {
        BiomeModification birchForestModifier = BiomeModifications.create(WondrousWilds.id("birch_forest_modifier"));

        final Predicate<BiomeSelectionContext> BIRCH_FOREST = BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST);
        final Predicate<BiomeSelectionContext> OLD_GROWTH_BIRCH_FOREST = BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_BIRCH_FOREST);
        final Predicate<BiomeSelectionContext> ALL_BIRCH_FORESTS = BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST);

        birchForestModifier.add(ModificationPhase.REPLACEMENTS, ALL_BIRCH_FORESTS, context -> {
            context.getGenerationSettings().removeFeature(VegetationPlacements.PATCH_GRASS_FOREST);
            context.getGenerationSettings().removeFeature(VegetationPlacements.FOREST_FLOWERS);

            context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WondrousWildsFeatures.BIRCH_FOREST_GRASS_PATCH_PLACED);
            context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WondrousWildsFeatures.BIRCH_FOREST_TALL_FLOWERS_PLACED);
        });

        birchForestModifier.add(ModificationPhase.ADDITIONS, ALL_BIRCH_FORESTS, context -> {
            context.getGenerationSettings().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WondrousWildsFeatures.BIRCH_FOREST_ROCK_PLACED);

            context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WondrousWildsFeatures.FALLEN_BIRCH_LOG_PLACED);
            context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WondrousWildsFeatures.BIRCH_FOREST_TALL_GRASS_PATCH_PLACED);

            context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WondrousWildsFeatures.PURPLE_VIOLETS_PLACED);
            context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WondrousWildsFeatures.PINK_VIOLETS_PLACED);
            context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WondrousWildsFeatures.RED_VIOLETS_PLACED);
            context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WondrousWildsFeatures.WHITE_VIOLETS_PLACED);

            context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WondrousWildsFeatures.LILY_OF_THE_VALLEY_PATCH_PLACED);

            context.getSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 6, 2, 4));
        });

        birchForestModifier.add(ModificationPhase.REPLACEMENTS, BIRCH_FOREST, context -> {
            context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_BIRCH);
            context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WondrousWildsFeatures.Trees.BIRCH_FOREST_TREES_PLACED);
        });

        birchForestModifier.add(ModificationPhase.REPLACEMENTS, OLD_GROWTH_BIRCH_FOREST, context -> {
            context.getGenerationSettings().removeFeature(VegetationPlacements.BIRCH_TALL);
            context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WondrousWildsFeatures.Trees.OLD_GROWTH_BIRCH_FOREST_TREES_PLACED);

            context.getEffects().setGrassColor(12232267);
        });
    }
}
