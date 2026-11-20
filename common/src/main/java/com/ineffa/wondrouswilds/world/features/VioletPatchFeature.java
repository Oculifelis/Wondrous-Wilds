package com.ineffa.wondrouswilds.world.features;

import com.ineffa.wondrouswilds.blocks.VioletBlock;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;

public record VioletPatchFeature(Holder<BlockStateProvider> violetProvider) implements Feature {

    public static final MapCodec<VioletPatchFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("violet_provider").forGetter(VioletPatchFeature::violetProvider)
    ).apply(instance, VioletPatchFeature::new));

    @Override
    public MapCodec<VioletPatchFeature> codec() {
        return CODEC;
    }

    @Override
    public boolean place(WorldGenLevel world, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {

        int horizontalSpread = 12 + 1;
        int verticalSpread = 2 + 1;

        int i = 0;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (int l = 0; l < 280; ++l) {
            mutablePos.setWithOffset(origin, random.nextInt(horizontalSpread) - random.nextInt(horizontalSpread), random.nextInt(verticalSpread) - random.nextInt(verticalSpread), random.nextInt(horizontalSpread) - random.nextInt(horizontalSpread));

            if (world.isEmptyBlock(mutablePos)) {
                BlockState violetState = this.violetProvider.value().getState(world, random, mutablePos);

                if (!violetState.canSurvive(world, mutablePos)) continue;

                if (violetState.getBlock() instanceof VioletBlock) violetState = violetState.setValue(VioletBlock.VIOLETS, random.nextIntBetweenInclusive(VioletBlock.MIN_VIOLETS, VioletBlock.MAX_VIOLETS));
                else continue;

                world.setBlock(mutablePos, violetState, Block.UPDATE_CLIENTS);
            }

            ++i;
        }

        return i > 0;
    }
}
