package com.ineffa.wondrouswilds.fabric;

import net.minecraft.world.entity.EntityTypes;
import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.networking.WondrousWildsPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
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
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
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

        PayloadTypeRegistry.clientboundPlay().register(WondrousWildsPayload.TYPE, WondrousWildsPayload.CODEC);

        for (WondrousWildsRegistration.Entry entry : WondrousWildsRegistration.entries()) {
            Registry registry = BuiltInRegistries.REGISTRY.getValue(entry.registry().identifier());
            Registry.register(registry, entry.id(), entry.value());
        }

        FabricDefaultAttributeRegistry.register(WondrousWildsEntities.FIREFLY, FireflyEntity.createFireflyAttributes());
        FabricDefaultAttributeRegistry.register(WondrousWildsEntities.WOODPECKER, WoodpeckerEntity.createWoodpeckerAttributes());
        SpawnPlacements.register(WondrousWildsEntities.FIREFLY, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, FireflyEntity::canFireflySpawn);
        SpawnPlacements.register(WondrousWildsEntities.WOODPECKER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, Animal::checkAnimalSpawnRules);

        if (WondrousWilds.config.mobSettings.firefliesSpawnNaturally)
            BiomeModifications.addSpawn(context -> context.hasTag(ConventionalBiomeTags.IS_OVERWORLD), MobCategory.WATER_AMBIENT, WondrousWildsEntities.FIREFLY,
                    WondrousWildsEntities.FIREFLY_SPAWN_WEIGHT, WondrousWildsEntities.FIREFLY_SPAWN_MIN, WondrousWildsEntities.FIREFLY_SPAWN_MAX);

        // 26.3 Fabric API has no strippable registry and vanilla stripping is one shared data file, so axes strip hollow logs here.
        UseBlockCallback.EVENT.register(WondrousWildsFabric::stripHollowLog);


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

            context.getMobSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityTypes.FOX, net.minecraft.util.valueproviders.UniformInt.of(2, 4)), 6);
        });

        birchForestModifier.add(ModificationPhase.REPLACEMENTS, BIRCH_FOREST, context -> {
            context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_BIRCH);
            context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WondrousWildsFeatures.Trees.BIRCH_FOREST_TREES_PLACED);
        });

        birchForestModifier.add(ModificationPhase.REPLACEMENTS, OLD_GROWTH_BIRCH_FOREST, context -> {
            context.getGenerationSettings().removeFeature(VegetationPlacements.BIRCH_TALL);
            context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WondrousWildsFeatures.Trees.OLD_GROWTH_BIRCH_FOREST_TREES_PLACED);

            context.getEffects().setGrassColorOverride(12232267);
        });
    }

    private static net.minecraft.world.InteractionResult stripHollowLog(net.minecraft.world.entity.player.Player player, net.minecraft.world.level.Level level, net.minecraft.world.InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit) {
        net.minecraft.world.item.ItemStack stack = player.getItemInHand(hand);
        if (player.isSpectator() || !stack.is(net.minecraft.tags.ItemTags.AXES)) return net.minecraft.world.InteractionResult.PASS;
        net.minecraft.core.BlockPos pos = hit.getBlockPos();
        net.minecraft.world.level.block.state.BlockState state = level.getBlockState(pos);
        net.minecraft.world.level.block.Block stripped = WondrousWildsBlocks.HOLLOW_STRIPPABLES.get(state.getBlock());
        if (stripped == null) return net.minecraft.world.InteractionResult.PASS;
        net.minecraft.world.level.block.state.BlockState newState = stripped.withPropertiesOf(state);
        level.playSound(player, pos, net.minecraft.sounds.SoundEvents.AXE_STRIP.value(), net.minecraft.sounds.SoundSource.BLOCKS, 1.0F, 1.0F);
        if (!level.isClientSide()) {
            level.setBlock(pos, newState, net.minecraft.world.level.block.Block.UPDATE_ALL_IMMEDIATE);
            level.gameEvent(net.minecraft.world.level.gameevent.GameEvent.BLOCK_CHANGE, pos, net.minecraft.world.level.gameevent.GameEvent.Context.of(player, newState));
            stack.hurtAndBreak(1, player, hand);
        }
        return net.minecraft.world.InteractionResult.SUCCESS;
    }
}
