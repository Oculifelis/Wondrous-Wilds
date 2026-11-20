package com.ineffa.wondrouswilds.util;

import com.google.common.collect.ImmutableMap;
import com.ineffa.wondrouswilds.blocks.TreeHollowBlock;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WondrousWildsUtils {

    public static final Direction[] HORIZONTAL_DIRECTIONS = Arrays.stream(Direction.values()).filter((direction) -> direction.getAxis().isHorizontal()).toArray(Direction[]::new);

    public static final Map<Block, TreeHollowBlock> TREE_HOLLOW_MAP = new ImmutableMap.Builder<Block, TreeHollowBlock>()
            .put(Blocks.OAK_LOG, WondrousWildsBlocks.OAK_TREE_HOLLOW)
            .put(Blocks.SPRUCE_LOG, WondrousWildsBlocks.SPRUCE_TREE_HOLLOW)
            .put(Blocks.BIRCH_LOG, WondrousWildsBlocks.BIRCH_TREE_HOLLOW)
            .put(Blocks.JUNGLE_LOG, WondrousWildsBlocks.JUNGLE_TREE_HOLLOW)
            .put(Blocks.ACACIA_LOG, WondrousWildsBlocks.ACACIA_TREE_HOLLOW)
            .put(Blocks.DARK_OAK_LOG, WondrousWildsBlocks.DARK_OAK_TREE_HOLLOW)
            .put(Blocks.MANGROVE_LOG, WondrousWildsBlocks.MANGROVE_TREE_HOLLOW)
            .build();

    public static Set<BlockPos> getCenteredCuboid(BlockPos center, int horizontalRadius) {
        return getCenteredCuboid(center, horizontalRadius, 0);
    }

    public static Set<BlockPos> getCenteredCuboid(BlockPos center, int horizontalRadius, int verticalRadius) {
        Set<BlockPos> positions = new HashSet<>();

        for (int y = -verticalRadius; y <= verticalRadius; ++y) {
            for (int x = -horizontalRadius; x <= horizontalRadius; ++x) {
                for (int z = -horizontalRadius; z <= horizontalRadius; ++z) {
                    BlockPos pos = center.offset(x, y, z);
                    positions.add(pos);
                }
            }
        }

        return positions;
    }

    public static Set<BlockPos> getEdges(BlockPos center, int edgeDistance, int edgeRadius) {
        Set<BlockPos> positions = new HashSet<>();

        for (Direction direction : HORIZONTAL_DIRECTIONS) {
            BlockPos offsetPos = center.relative(direction, edgeDistance);

            positions.add(offsetPos);

            if (edgeRadius < 1) continue;

            for (int distance = 1; distance <= edgeRadius; ++distance) {
                positions.add(offsetPos.relative(direction.getClockWise(), distance));
                positions.add(offsetPos.relative(direction.getCounterClockWise(), distance));
            }
        }

        return positions;
    }

    public static boolean isPosAtWorldOrigin(BlockPos pos) {
        BlockPos origin = BlockPos.ZERO;
        return pos.getX() == origin.getX() && pos.getY() == origin.getY() && pos.getZ() == origin.getZ();
    }

    public static boolean canEntitySeeBlock(LivingEntity entity, BlockPos posToCheck, boolean ignoreFluids) {
        Level world = entity.level();

        BlockState lookState = world.getBlockState(posToCheck);
        if (lookState == null) return false;

        VoxelShape shape = lookState.getShape(world, posToCheck);
        if (shape == null || shape.isEmpty()) return false;

        AABB blockBoundingBox = shape.bounds();
        if (blockBoundingBox == null) return false;

        Vec3 raycastStart = new Vec3(entity.getX(), entity.getEyeY(), entity.getZ());
        Vec3 raycastEnd = blockBoundingBox.getCenter().add(posToCheck.getX(), posToCheck.getY(), posToCheck.getZ());
        return world.clip(new ClipContext(raycastStart, raycastEnd, ClipContext.Block.COLLIDER, ignoreFluids ? ClipContext.Fluid.NONE : ClipContext.Fluid.ANY, entity)).getBlockPos().equals(posToCheck);
    }
}
