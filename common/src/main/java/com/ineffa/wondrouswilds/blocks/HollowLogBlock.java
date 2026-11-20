package com.ineffa.wondrouswilds.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.Objects;

@SuppressWarnings("deprecation")
public class HollowLogBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {

    private static final VoxelShape VERTICAL_NORTH_WALL_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    private static final VoxelShape VERTICAL_SOUTH_WALL_SHAPE = Block.box(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape VERTICAL_EAST_WALL_SHAPE = Block.box(13.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D);
    private static final VoxelShape VERTICAL_WEST_WALL_SHAPE = Block.box(0.0D, 0.0D, 3.0D, 3.0D, 16.0D, 13.0D);

    private static final VoxelShape HORIZONTAL_TOP_SHAPE = Block.box(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape HORIZONTAL_BOTTOM_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);

    private static final VoxelShape X_AXIS_NORTH_WALL_SHAPE = Block.box(0.0D, 3.0D, 0.0D, 16.0D, 13.0D, 3.0D);
    private static final VoxelShape X_AXIS_SOUTH_WALL_SHAPE = Block.box(0.0D, 3.0D, 13.0D, 16.0D, 13.0D, 16.0D);

    private static final VoxelShape Z_AXIS_EAST_WALL_SHAPE = Block.box(13.0D, 3.0D, 0.0D, 16.0D, 13.0D, 16.0D);
    private static final VoxelShape Z_AXIS_WEST_WALL_SHAPE = Block.box(0.0D, 3.0D, 0.0D, 3.0D, 13.0D, 16.0D);

    private static final VoxelShape Y_AXIS_SHAPE = Shapes.or(VERTICAL_NORTH_WALL_SHAPE, VERTICAL_SOUTH_WALL_SHAPE, VERTICAL_EAST_WALL_SHAPE, VERTICAL_WEST_WALL_SHAPE);
    private static final VoxelShape X_AXIS_SHAPE = Shapes.or(HORIZONTAL_TOP_SHAPE, HORIZONTAL_BOTTOM_SHAPE, X_AXIS_NORTH_WALL_SHAPE, X_AXIS_SOUTH_WALL_SHAPE);
    private static final VoxelShape Z_AXIS_SHAPE = Shapes.or(HORIZONTAL_TOP_SHAPE, HORIZONTAL_BOTTOM_SHAPE, Z_AXIS_EAST_WALL_SHAPE, Z_AXIS_WEST_WALL_SHAPE);

    private static final VoxelShape RAYCAST_SHAPE = Shapes.block();

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public HollowLogBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return Objects.requireNonNull(super.getStateForPlacement(ctx)).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess ticks, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));

        return super.updateShape(state, world, ticks, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED)) return Fluids.WATER.getSource(false);

        return super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction.Axis axis = state.getValue(AXIS);

        if (axis == Direction.Axis.X) return X_AXIS_SHAPE;
        if (axis == Direction.Axis.Z) return Z_AXIS_SHAPE;

        return Y_AXIS_SHAPE;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return RAYCAST_SHAPE;
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }
}
