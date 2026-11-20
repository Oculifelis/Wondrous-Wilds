package com.ineffa.wondrouswilds.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BigPolyporeBlock extends LadderBlock {

    private static final VoxelShape NORTH_SHAPE = Block.box(1.0D, 7.0D, 4.0D, 15.0D, 9.0D, 16.0D);
    private static final VoxelShape SOUTH_SHAPE = Block.box(1.0D, 7.0D, 0.0D, 15.0D, 9.0D, 12.0D);
    private static final VoxelShape EAST_SHAPE = Block.box(0.0D, 7.0D, 1.0D, 12.0D, 9.0D, 15.0D);
    private static final VoxelShape WEST_SHAPE = Block.box(4.0D, 7.0D, 1.0D, 16.0D, 9.0D, 15.0D);

    public BigPolyporeBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            default -> NORTH_AABB;
            case SOUTH -> SOUTH_AABB;
            case EAST -> EAST_AABB;
            case WEST -> WEST_AABB;
        };
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
        return false;
    }
}
