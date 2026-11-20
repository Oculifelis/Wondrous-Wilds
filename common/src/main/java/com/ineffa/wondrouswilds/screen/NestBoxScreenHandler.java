package com.ineffa.wondrouswilds.screen;

import com.ineffa.wondrouswilds.registry.WondrousWildsScreenHandlers;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class NestBoxScreenHandler extends AbstractContainerMenu {

    private final Container inventory;

    public NestBoxScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(1));
    }

    public NestBoxScreenHandler(int syncId, Inventory playerInventory, Container inventory) {
        super(WondrousWildsScreenHandlers.NEST_BOX, syncId);

        checkContainerSize(inventory, 1);

        this.inventory = inventory;

        inventory.startOpen(playerInventory.player);

        // Container inventory
        this.addSlot(new Slot(inventory, 0, 80, 35));

        // Player inventory
        int i;
        int j;
        for (i = 0; i < 3; ++i)
            for (j = 0; j < 9; ++j) this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

        // Player hotbar
        for (i = 0; i < 9; ++i) this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        this.inventory.stopOpen(player);
    }
}
