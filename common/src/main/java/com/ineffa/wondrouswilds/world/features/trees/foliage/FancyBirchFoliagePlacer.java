package com.ineffa.wondrouswilds.world.features.trees.foliage;

import com.mojang.serialization.MapCodec;
import com.ineffa.wondrouswilds.registry.WondrousWildsFeatures;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import static com.ineffa.wondrouswilds.util.WondrousWildsUtils.*;

public class FancyBirchFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<FancyBirchFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance -> FancyBirchFoliagePlacer.foliagePlacerParts(instance).apply(instance, FancyBirchFoliagePlacer::new));

    public FancyBirchFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return WondrousWildsFeatures.Trees.FoliagePlacers.FANCY_BIRCH;
    }

    @Override
    protected void createFoliage(WorldGenLevel world, FoliagePlacer.FoliageSetter replacer, RandomSource random, TreeFeature config, int trunkHeight, FoliageAttachment treeNode, int foliageHeight, int radius, int offset) {
        BlockPos origin = treeNode.pos();
        BlockPos.MutableBlockPos currentCenter = origin.mutable();

        Set<BlockPos> leaves = new HashSet<>();

        // Top layers
        boolean tallTop = random.nextInt(3) != 0;

        BlockPos tipTop = tallTop ? origin.above() : origin;
        leaves.add(tipTop); for (Direction direction : HORIZONTAL_DIRECTIONS) leaves.add(tipTop.relative(direction));

        if (tallTop) {
            leaves.addAll(getCenteredCuboid(origin, 1));

            if (random.nextBoolean()) for (Direction direction : HORIZONTAL_DIRECTIONS) leaves.add(origin.relative(direction, 2));
        }

        // Intermediate & central layers
        currentCenter.move(Direction.DOWN);

        int centralLayers = random.nextIntBetweenInclusive(2, 4);

        boolean finishedEdges = false;
        boolean shrinkCentralEdgeRadius = false;
        int nextCentralEdgeRadius = random.nextBoolean() ? 1 : 0;

        for (int layerCount = -1; layerCount <= centralLayers; ++layerCount) {
            boolean intermediate = layerCount == -1 || layerCount == centralLayers;

            leaves.addAll(getCenteredCuboid(currentCenter, intermediate ? 1 : 2));
            if (intermediate) leaves.addAll(getEdges(currentCenter, 2, random.nextBoolean() ? 1 : 0));
            else if (!finishedEdges) {
                boolean reachedMaxRadius = nextCentralEdgeRadius >= 2;
                boolean reachedMinRadius = nextCentralEdgeRadius <= 0;

                if (layerCount == 0 && random.nextBoolean()) {
                    currentCenter.move(Direction.DOWN);
                    continue;
                }

                leaves.addAll(getEdges(currentCenter, 3, nextCentralEdgeRadius));

                if (!shrinkCentralEdgeRadius && reachedMaxRadius) shrinkCentralEdgeRadius = true;

                if (shrinkCentralEdgeRadius) nextCentralEdgeRadius -= reachedMaxRadius && random.nextBoolean() ? 2 : 1;
                else nextCentralEdgeRadius += reachedMinRadius && random.nextBoolean() ? 2 : 1;

                if (layerCount > 1 && reachedMinRadius) finishedEdges = true;
            }

            currentCenter.move(Direction.DOWN);
        }

        // Bottom layer
        if (random.nextBoolean()) for (Direction direction : HORIZONTAL_DIRECTIONS) leaves.add(currentCenter.relative(direction));

        // Final placement
        for (BlockPos pos : leaves) FancyBirchFoliagePlacer.tryPlaceLeaf(world, replacer, random, config, pos);
    }

    @Override
    public int foliageHeight(RandomSource random, int trunkHeight, TreeFeature config) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return false;
    }
}
