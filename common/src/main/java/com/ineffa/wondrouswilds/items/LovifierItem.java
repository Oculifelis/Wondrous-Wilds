package com.ineffa.wondrouswilds.items;

import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class LovifierItem extends Item {

    public LovifierItem(Properties settings) {
        // 1.21: rarity is an item component now, set here instead of a getRarity override.
        super(settings.rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext world, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, display, tooltip, context);

        tooltip.accept(Component.translatable(this.getDescriptionId() + ".desc").withStyle(ChatFormatting.GRAY));
    }
}
