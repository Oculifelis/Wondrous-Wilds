package com.ineffa.wondrouswilds.util.fakeplayer;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.phys.BlockHitResult;

public class WoodpeckerItemUsageContext extends UseOnContext {

    public WoodpeckerItemUsageContext(WoodpeckerEntity woodpecker, WoodpeckerFakePlayer fakePlayer, ItemStack itemStack, BlockHitResult hit) {
        super(woodpecker.level(), fakePlayer, InteractionHand.MAIN_HAND, itemStack, hit);
    }
}
