package com.ineffa.wondrouswilds.blocks;

import com.ineffa.wondrouswilds.blocks.entity.NestBoxBlockEntity;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public abstract class NestBoxBlock extends InhabitableNestBlock {

    public NestBoxBlock(Properties settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NestBoxBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return world.isClientSide() ? null : createTickerHelper(type, WondrousWildsBlocks.BlockEntities.NEST_BOX, NestBoxBlockEntity::serverTick);
    }

    // 1.21: the container block entity copies the item's custom name component on placement by itself,
    // so the original's setPlacedBy name copy is gone.

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide() && player.isCreative() && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && world.getBlockEntity(pos) instanceof NestBoxBlockEntity nestBox) {
            ItemStack itemStack = new ItemStack(this);
            if (nestBox.hasInhabitants()) {
                CompoundTag nbtCompound = new CompoundTag();

                nbtCompound.put(NestBoxBlockEntity.INHABITANTS_KEY, nestBox.getInhabitantsNbt());
                BlockItem.setBlockEntityData(itemStack, WondrousWildsBlocks.BlockEntities.NEST_BOX, nbtCompound);

                // 1.21: the original also copied this tag into BlockStateTag, which held no block properties and did nothing. Item block state is a property map now, so that copy is gone.

                ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                itemEntity.setDefaultPickUpDelay();
                world.addFreshEntity(itemEntity);
            }
        }

        return super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.is(newState.getBlock())) return;

        if (world.getBlockEntity(pos) instanceof Container inventory) {
            Containers.dropContents(world, pos, inventory);
            world.updateNeighbourForOutputSignal(pos, this);
        }

        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (world.isClientSide()) return InteractionResult.SUCCESS;

        if (world.getBlockEntity(pos) instanceof NestBoxBlockEntity nestBox) player.openMenu(nestBox);

        return InteractionResult.CONSUME;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos));
    }
}
