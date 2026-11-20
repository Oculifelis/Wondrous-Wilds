package com.ineffa.wondrouswilds.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class BirchNestBoxBlock extends NestBoxBlock {

    private static final VoxelShape ROOF_SHAPE = Block.box(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_BOX_SHAPE = Block.box(0.0D, 0.0D, 2.0D, 16.0D, 14.0D, 16.0D);
    private static final VoxelShape SOUTH_BOX_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 14.0D);
    private static final VoxelShape EAST_BOX_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 14.0D, 14.0D, 16.0D);
    private static final VoxelShape WEST_BOX_SHAPE = Block.box(2.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    private static final VoxelShape NORTH_SHAPE = Shapes.or(ROOF_SHAPE, NORTH_BOX_SHAPE);
    private static final VoxelShape SOUTH_SHAPE = Shapes.or(ROOF_SHAPE, SOUTH_BOX_SHAPE);
    private static final VoxelShape EAST_SHAPE = Shapes.or(ROOF_SHAPE, EAST_BOX_SHAPE);
    private static final VoxelShape WEST_SHAPE = Shapes.or(ROOF_SHAPE, WEST_BOX_SHAPE);


    public BirchNestBoxBlock(Properties settings) {
        super(settings);
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            default -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
        };
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }
}
