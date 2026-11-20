package com.ineffa.wondrouswilds.items;

import net.minecraft.core.registries.BuiltInRegistries;
import com.ineffa.wondrouswilds.WondrousWilds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ScrollOfSecretsItem extends Item {

    protected final Set<Item> itemsToUnlock = new HashSet<>();
    protected final Map<Item, ResourceLocation> itemIds = new HashMap<>();

    public ScrollOfSecretsItem(Properties settings, Item... itemsToUnlock) {
        super(settings);

        for (Item item : itemsToUnlock) {
            if (!this.itemsToUnlock.add(item)) continue;
            this.itemIds.put(item, BuiltInRegistries.ITEM.getKey(item));
        }
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.RARE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);

        if (!world.isClientSide()) {
            boolean hasAllRecipes = true;
            ArrayList<Recipe<?>> recipesToUnlock = new ArrayList<>();

            RecipeManager recipeManager = Objects.requireNonNull(world.getServer()).getRecipeManager();
            for (Item itemToUnlock : this.itemsToUnlock) {
                ResourceLocation itemToUnlockId = this.itemIds.get(itemToUnlock);

                if (user instanceof ServerPlayer serverPlayer && serverPlayer.getRecipeBook().contains(itemToUnlockId)) continue;
                else if (hasAllRecipes) hasAllRecipes = false;

                Optional<? extends Recipe<?>> recipe = recipeManager.byKey(itemToUnlockId);
                if (recipe.isPresent()) recipesToUnlock.add(recipe.get());

                else WondrousWilds.LOGGER.error("Scroll of Secrets failed to find recipe for " + itemToUnlockId.toString());
            }

            if (hasAllRecipes) return InteractionResultHolder.fail(itemStack);

            if (!user.getAbilities().instabuild) user.setItemInHand(hand, ItemStack.EMPTY);

            user.awardStat(Stats.ITEM_USED.get(this));

            world.levelEvent(LevelEvent.SOUND_PAGE_TURN, user.blockPosition(), 0);

            user.awardRecipes(recipesToUnlock);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        for (Item item : this.itemsToUnlock) tooltip.add(item.getDescription().copy().withStyle(ChatFormatting.GRAY));

        super.appendHoverText(stack, world, tooltip, context);
    }
}
