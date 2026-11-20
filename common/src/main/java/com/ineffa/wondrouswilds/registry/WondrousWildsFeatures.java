package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.world.features.FallenLogFeature;
import com.ineffa.wondrouswilds.world.features.VioletPatchFeature;
import com.ineffa.wondrouswilds.world.features.configs.FallenLogFeatureConfig;
import com.ineffa.wondrouswilds.world.features.configs.VioletPatchFeatureConfig;
import com.ineffa.wondrouswilds.world.features.trees.decorators.CobwebTreeDecorator;
import com.ineffa.wondrouswilds.world.features.trees.decorators.HangingBeeNestTreeDecorator;
import com.ineffa.wondrouswilds.world.features.trees.decorators.PolyporeTreeDecorator;
import com.ineffa.wondrouswilds.world.features.trees.decorators.TreeHollowTreeDecorator;
import com.ineffa.wondrouswilds.world.features.trees.foliage.FancyBirchFoliagePlacer;
import com.ineffa.wondrouswilds.world.features.trees.trunks.StraightBranchingTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Objects;

/**
 * Feature, trunk placer, foliage placer and decorator types. The configured and placed features the original built in
 * code are datapack JSON under data/wondrouswilds/worldgen (written by tools/gen_worldgen.py from the original values).
 */
public class WondrousWildsFeatures {

    public static final class Trees {

        public static final class TrunkPlacers {
            public static final TrunkPlacerType<StraightBranchingTrunkPlacer> STRAIGHT_BRANCHING_TRUNK = WondrousWildsRegistration.add(Registries.TRUNK_PLACER_TYPE, "straight_branching_trunk_placer", new TrunkPlacerType<>(StraightBranchingTrunkPlacer.CODEC));
        }

        public static final class FoliagePlacers {
            public static final FoliagePlacerType<FancyBirchFoliagePlacer> FANCY_BIRCH = WondrousWildsRegistration.add(Registries.FOLIAGE_PLACER_TYPE, "fancy_birch_foliage_placer", new FoliagePlacerType<>(FancyBirchFoliagePlacer.CODEC));
        }

        public static final class Decorators {
            public static final TreeDecoratorType<TreeHollowTreeDecorator> TREE_HOLLOW_TYPE = registerDecorator("tree_hollow", new TreeDecoratorType<>(TreeHollowTreeDecorator.CODEC));
            public static final TreeDecoratorType<HangingBeeNestTreeDecorator> HANGING_BEE_NEST_TYPE = registerDecorator("hanging_bee_nest", new TreeDecoratorType<>(HangingBeeNestTreeDecorator.CODEC));
            public static final TreeDecoratorType<PolyporeTreeDecorator> POLYPORE_TYPE = registerDecorator("polypores", new TreeDecoratorType<>(PolyporeTreeDecorator.CODEC));
            public static final TreeDecoratorType<CobwebTreeDecorator> COBWEB_TYPE = registerDecorator("cobwebs", new TreeDecoratorType<>(CobwebTreeDecorator.CODEC));

            public static final TreeDecorator TREE_HOLLOW = TreeHollowTreeDecorator.INSTANCE;
            public static final TreeDecorator HANGING_BEE_NEST = HangingBeeNestTreeDecorator.INSTANCE;
            public static final TreeDecorator POLYPORES = PolyporeTreeDecorator.INSTANCE;
            public static final TreeDecorator COBWEBS = CobwebTreeDecorator.INSTANCE;

            private static <T extends TreeDecorator> TreeDecoratorType<T> registerDecorator(String name, TreeDecoratorType<T> type) {
                return WondrousWildsRegistration.add(Registries.TREE_DECORATOR_TYPE, name, type);
            }
        }

        public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_BIRCH_CONFIGURED = configured("fancy_birch");
        public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_BIRCH_WITH_BEES_CONFIGURED = configured("fancy_birch_with_bees");
        public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_FANCY_BIRCH_CONFIGURED = configured("yellow_fancy_birch");
        public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_FANCY_BIRCH_WITH_BEES_CONFIGURED = configured("yellow_fancy_birch_with_bees");
        public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_FANCY_BIRCH_CONFIGURED = configured("orange_fancy_birch");
        public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_FANCY_BIRCH_WITH_BEES_CONFIGURED = configured("orange_fancy_birch_with_bees");
        public static final ResourceKey<ConfiguredFeature<?, ?>> RED_FANCY_BIRCH_CONFIGURED = configured("red_fancy_birch");
        public static final ResourceKey<ConfiguredFeature<?, ?>> RED_FANCY_BIRCH_WITH_BEES_CONFIGURED = configured("red_fancy_birch_with_bees");

        public static final ResourceKey<PlacedFeature> BIRCH_FOREST_TREES_PLACED = placed("birch_forest_trees");
        public static final ResourceKey<PlacedFeature> OLD_GROWTH_BIRCH_FOREST_TREES_PLACED = placed("old_growth_birch_forest_trees");
    }

    public static final Feature<FallenLogFeatureConfig> FALLEN_LOG = WondrousWildsRegistration.add(Registries.FEATURE, "fallen_log", new FallenLogFeature());
    public static final Feature<VioletPatchFeatureConfig> VIOLET_PATCH = WondrousWildsRegistration.add(Registries.FEATURE, "violet_patch", new VioletPatchFeature());

    public static final ResourceKey<PlacedFeature> FALLEN_BIRCH_LOG_PLACED = placed("fallen_birch_log");
    public static final ResourceKey<PlacedFeature> LILY_OF_THE_VALLEY_PATCH_PLACED = placed("lily_of_the_valley_patch");
    public static final ResourceKey<PlacedFeature> BIRCH_FOREST_TALL_FLOWERS_PLACED = placed("birch_forest_tall_flowers");
    public static final ResourceKey<PlacedFeature> PURPLE_VIOLETS_PLACED = placed("purple_violets");
    public static final ResourceKey<PlacedFeature> PINK_VIOLETS_PLACED = placed("pink_violets");
    public static final ResourceKey<PlacedFeature> RED_VIOLETS_PLACED = placed("red_violets");
    public static final ResourceKey<PlacedFeature> WHITE_VIOLETS_PLACED = placed("white_violets");
    public static final ResourceKey<PlacedFeature> BIRCH_FOREST_GRASS_PATCH_PLACED = placed("birch_forest_grass_patch");
    /** The original registered this one under the vanilla namespace (PlacementUtils.register). */
    public static final ResourceKey<PlacedFeature> BIRCH_FOREST_TALL_GRASS_PATCH_PLACED = placed("birch_forest_tall_grass_patch");
    public static final ResourceKey<PlacedFeature> BIRCH_FOREST_ROCK_PLACED = placed("birch_forest_rock");

    private static ResourceKey<ConfiguredFeature<?, ?>> configured(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, WondrousWilds.id(name));
    }

    private static ResourceKey<PlacedFeature> placed(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, WondrousWilds.id(name));
    }

    /** Loads the nested holders too; their static fields register the placer and decorator types. */
    public static void initialize() {
        Objects.requireNonNull(Trees.TrunkPlacers.STRAIGHT_BRANCHING_TRUNK);
        Objects.requireNonNull(Trees.FoliagePlacers.FANCY_BIRCH);
        Objects.requireNonNull(Trees.Decorators.TREE_HOLLOW_TYPE);
    }
}
