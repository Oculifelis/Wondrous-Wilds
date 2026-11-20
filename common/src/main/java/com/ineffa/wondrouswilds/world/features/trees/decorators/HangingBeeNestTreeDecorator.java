package com.ineffa.wondrouswilds.world.features.trees.decorators;

import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import com.ineffa.wondrouswilds.registry.WondrousWildsFeatures;
import com.mojang.serialization.Codec;
import java.util.*;
import java.util.stream.Collectors;
import net.minecraft.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import static com.ineffa.wondrouswilds.util.WondrousWildsUtils.HORIZONTAL_DIRECTIONS;

public class HangingBeeNestTreeDecorator extends TreeDecorator {

    public static final HangingBeeNestTreeDecorator INSTANCE = new HangingBeeNestTreeDecorator();
    public static final MapCodec<HangingBeeNestTreeDecorator> CODEC = MapCodec.unit(() -> INSTANCE);

    @Override
    protected TreeDecoratorType<?> type() {
        return WondrousWildsFeatures.Trees.Decorators.HANGING_BEE_NEST_TYPE;
    }

    @Override
    public void place(Context generator) {
        LevelSimulatedReader world = generator.level();
        RandomSource random = generator.random();

        List<BlockPos> horizontalLogs = generator.logs().stream().filter(pos -> world.isStateAtPosition(pos, state -> state.hasProperty(RotatedPillarBlock.AXIS) && state.getValue(RotatedPillarBlock.AXIS).isHorizontal())).collect(Collectors.toList());
        Collections.shuffle(horizontalLogs);

        for (BlockPos logPos : horizontalLogs) {
            BlockPos nestPos = logPos.below();
            if (generator.isAir(nestPos)) {
                Set<Direction> suitableFacingDirections = new HashSet<>();
                for (Direction direction : HORIZONTAL_DIRECTIONS) if (generator.isAir(nestPos.relative(direction))) suitableFacingDirections.add(direction);

                Direction nestFacingDirection = Util.getRandom(!suitableFacingDirections.isEmpty() ? suitableFacingDirections.toArray(Direction[]::new) : HORIZONTAL_DIRECTIONS, random);

                generator.setBlock(nestPos, Blocks.BEE_NEST.defaultBlockState().setValue(BeehiveBlock.FACING, nestFacingDirection));
                world.getBlockEntity(nestPos, BlockEntityTypes.BEEHIVE).ifPresent(blockEntity -> {
                    int beeCount = 2 + random.nextInt(2);
                    for (int i = 0; i < beeCount; ++i) {
                        // 1.21: same as vanilla's BeehiveDecorator, a bee occupant with a random time in the hive.
                        blockEntity.storeBee(BeehiveBlockEntity.Occupant.create(random.nextInt(599)));
                    }
                });

                return;
            }
        }
    }
}
