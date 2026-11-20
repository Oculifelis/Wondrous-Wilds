package com.ineffa.wondrouswilds.world.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;

public record FallenLogFeature(Holder<BlockStateProvider> logProvider, int minLength, int maxLength) implements Feature {
    private static final BlockState MOSS_CARPET_STATE = Blocks.MOSS_CARPET.defaultBlockState();

    public static final MapCodec<FallenLogFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("log_provider").forGetter(FallenLogFeature::logProvider),
            Codec.INT.fieldOf("min_length").orElse(3).forGetter(FallenLogFeature::minLength),
            Codec.INT.fieldOf("max_length").orElse(8).forGetter(FallenLogFeature::maxLength)
    ).apply(instance, FallenLogFeature::new));

    @Override
    public MapCodec<FallenLogFeature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {

        Set<BlockPos> logs = new HashSet<>();

        int logLimit = random.nextIntBetweenInclusive(minLength, maxLength);
        int nextPositiveOffsetDistance = 0;
        int nextNegativeOffsetDistance = 0;
        Direction nextOffsetDirection = Direction.from2DDataValue(random.nextInt(4));
        boolean oneDirectional = false;
        while (logs.size() < logLimit) {
            Direction.AxisDirection axisDirection = nextOffsetDirection.getAxisDirection();

            BlockPos tryLogPos = origin.relative(nextOffsetDirection, axisDirection == Direction.AxisDirection.POSITIVE ? nextPositiveOffsetDistance : nextNegativeOffsetDistance);

            if (!oneDirectional) nextOffsetDirection = nextOffsetDirection.getOpposite();

            if (!TreeFeature.validTreePos(world, tryLogPos) || !(world.isStateAtPosition(tryLogPos.below(), BlockState::isSolidRender))) {
                if (oneDirectional) break;

                oneDirectional = true;
                continue;
            }

            if (axisDirection == Direction.AxisDirection.POSITIVE) ++nextPositiveOffsetDistance;
            else if (axisDirection == Direction.AxisDirection.NEGATIVE) ++nextNegativeOffsetDistance;

            logs.add(tryLogPos);
        }

        if (logs.size() < minLength) return false;

        for (BlockPos pos : logs) {
            BlockState state = this.logProvider.value().getState(world, random, pos);
            if (state.getBlock() instanceof RotatedPillarBlock) state = state.setValue(RotatedPillarBlock.AXIS, nextOffsetDirection.getAxis());

            this.setBlock(world, pos, state);

            if (random.nextInt(3) == 0) {
                BlockPos tryMossCarpetPos = pos.above();
                if (world.isEmptyBlock(tryMossCarpetPos)) this.setBlock(world, tryMossCarpetPos, MOSS_CARPET_STATE);
            }
        }

        return true;
    }
}
