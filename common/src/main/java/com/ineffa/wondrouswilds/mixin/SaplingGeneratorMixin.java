package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.registry.WondrousWildsFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AbstractTreeGrower.class)
public class SaplingGeneratorMixin {

    /**
     * <p> Allows fancy birches grown from birch saplings to be autumn colored when grown in an Old Growth Birch Forest biome.
     * <p> Unlike when generated naturally, each color has an equal chance to be selected.
     */
    @ModifyVariable(method = "growTree", at = @At("STORE"), ordinal = 0)
    private @Nullable ResourceKey<ConfiguredFeature<?, ?>> generateAutumnBirches(@Nullable ResourceKey<ConfiguredFeature<?, ?>> key, ServerLevel world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, RandomSource random) {
        boolean bees = key == WondrousWildsFeatures.Trees.FANCY_BIRCH_WITH_BEES_CONFIGURED;
        if (bees || key == WondrousWildsFeatures.Trees.FANCY_BIRCH_CONFIGURED) {
            if (world.getBiome(pos).is(Biomes.OLD_GROWTH_BIRCH_FOREST)) {
                key = switch (random.nextInt(4)) {
                    default -> key;
                    case 1 -> bees ? WondrousWildsFeatures.Trees.YELLOW_FANCY_BIRCH_WITH_BEES_CONFIGURED : WondrousWildsFeatures.Trees.YELLOW_FANCY_BIRCH_CONFIGURED;
                    case 2 -> bees ? WondrousWildsFeatures.Trees.ORANGE_FANCY_BIRCH_WITH_BEES_CONFIGURED : WondrousWildsFeatures.Trees.ORANGE_FANCY_BIRCH_CONFIGURED;
                    case 3 -> bees ? WondrousWildsFeatures.Trees.RED_FANCY_BIRCH_WITH_BEES_CONFIGURED : WondrousWildsFeatures.Trees.RED_FANCY_BIRCH_CONFIGURED;
                };
            }
        }
        return key;
    }
}
