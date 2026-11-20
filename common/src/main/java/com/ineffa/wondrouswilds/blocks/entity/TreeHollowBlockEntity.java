package com.ineffa.wondrouswilds.blocks.entity;

import com.google.common.collect.Lists;
import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TreeHollowBlockEntity extends BlockEntity implements InhabitableNestBlockEntity {

    private final List<Inhabitant> inhabitants = Lists.newArrayList();

    public TreeHollowBlockEntity(BlockPos pos, BlockState state) {
        super(WondrousWildsBlocks.BlockEntities.TREE_HOLLOW, pos, state);
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, TreeHollowBlockEntity treeHollow) {
        tickInhabitants(world, pos, state, treeHollow.getInhabitants());
    }

    private static void tickInhabitants(Level world, BlockPos pos, BlockState state, List<Inhabitant> inhabitants) {
        boolean released = false;

        Iterator<Inhabitant> iterator = inhabitants.iterator();
        while (iterator.hasNext()) {
            Inhabitant inhabitant = iterator.next();
            if (inhabitant.ticksInNest > inhabitant.minOccupationTicks) {
                if (InhabitableNestBlockEntity.tryReleasingInhabitant(world, pos, state, InhabitantReleaseState.RELEASE, inhabitant, null)) {
                    released = true;
                    iterator.remove();
                }
            }
            ++inhabitant.ticksInNest;
        }

        if (released) setChanged(world, pos, state);
    }

    @Override
    protected void saveAdditional(ValueOutput nbt) {
        super.saveAdditional(nbt);

        this.writeInhabitants(nbt);
    }

    @Override
    protected void loadAdditional(ValueInput nbt) {
        super.loadAdditional(nbt);

        this.getInhabitants().clear();

        this.readInhabitants(nbt);
    }

    @Override
    public List<Inhabitant> getInhabitants() {
        return this.inhabitants;
    }

    @Override
    public Level getNestWorld() {
        return this.getLevel();
    }

    @Override
    public BlockPos getNestPos() {
        return this.getBlockPos();
    }

    @Override
    public BlockState getNestCachedState() {
        return this.getBlockState();
    }

    @Override
    public void markNestDirty() {
        this.setChanged();
    }
}
