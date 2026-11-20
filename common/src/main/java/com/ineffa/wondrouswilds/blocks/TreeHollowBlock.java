package com.ineffa.wondrouswilds.blocks;

import com.ineffa.wondrouswilds.blocks.entity.TreeHollowBlockEntity;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class TreeHollowBlock extends InhabitableNestBlock {


    public TreeHollowBlock(Properties settings) {
        super(settings);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TreeHollowBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return world.isClientSide() ? null : createTickerHelper(type, WondrousWildsBlocks.BlockEntities.TREE_HOLLOW, TreeHollowBlockEntity::serverTick);
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide() && player.isCreative() && world instanceof ServerLevel serverLevel && serverLevel.getGameRules().get(GameRules.BLOCK_DROPS) && world.getBlockEntity(pos) instanceof TreeHollowBlockEntity treeHollow) {
            ItemStack itemStack = new ItemStack(this);
            if (treeHollow.hasInhabitants()) {
                TagValueOutput output = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, world.registryAccess());
                treeHollow.writeInhabitants(output);
                BlockItem.setBlockEntityData(itemStack, WondrousWildsBlocks.BlockEntities.TREE_HOLLOW, output);

                // 1.21: the original also copied this tag into BlockStateTag, which held no block properties and did nothing. Item block state is a property map now, so that copy is gone.

                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                itemEntity.setDefaultPickUpDelay();
                world.addFreshEntity(itemEntity);
            }
        }

        return super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos, Direction direction) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof TreeHollowBlockEntity treeHollow && treeHollow.hasInhabitants()) return 15;

        return 0;
    }
}
