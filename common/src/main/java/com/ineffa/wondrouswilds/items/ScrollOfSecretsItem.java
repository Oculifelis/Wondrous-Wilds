package com.ineffa.wondrouswilds.items;

import net.minecraft.core.registries.BuiltInRegistries;
import com.ineffa.wondrouswilds.WondrousWilds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.crafting.Recipe;
import java.util.function.Consumer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ScrollOfSecretsItem extends Item {

    protected final Set<Item> itemsToUnlock = new HashSet<>();
    protected final Map<Item, ResourceKey<Recipe<?>>> itemIds = new HashMap<>();

    public ScrollOfSecretsItem(Properties settings, Item... itemsToUnlock) {
        // 1.21: rarity is an item component now, set here instead of a getRarity override.
        super(settings.rarity(Rarity.RARE));

        for (Item item : itemsToUnlock) {
            if (!this.itemsToUnlock.add(item)) continue;
            this.itemIds.put(item, ResourceKey.create(Registries.RECIPE, BuiltInRegistries.ITEM.getKey(item)));
        }
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);

        if (!world.isClientSide()) {
            boolean hasAllRecipes = true;
            ArrayList<RecipeHolder<?>> recipesToUnlock = new ArrayList<>();

            RecipeManager recipeManager = ((net.minecraft.server.level.ServerLevel) world).recipeAccess();
            for (Item itemToUnlock : this.itemsToUnlock) {
                ResourceKey<Recipe<?>> itemToUnlockId = this.itemIds.get(itemToUnlock);

                if (user instanceof ServerPlayer serverPlayer && serverPlayer.getRecipeBook().contains(itemToUnlockId)) continue;
                else if (hasAllRecipes) hasAllRecipes = false;

                Optional<RecipeHolder<?>> recipe = recipeManager.byKey(itemToUnlockId);
                if (recipe.isPresent()) recipesToUnlock.add(recipe.get());

                else WondrousWilds.LOGGER.error("Scroll of Secrets failed to find recipe for " + itemToUnlockId.identifier().toString());
            }

            if (hasAllRecipes) return InteractionResult.FAIL;

            if (!user.getAbilities().instabuild) user.setItemInHand(hand, ItemStack.EMPTY);

            user.awardStat(Stats.ITEM_USED.get(this));

            world.levelEvent(LevelEvent.SOUND_PAGE_TURN, user.blockPosition(), 0);

            user.awardRecipes(recipesToUnlock);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext world, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag context) {
        for (Item item : this.itemsToUnlock) tooltip.accept(item.getName(item.getDefaultInstance()).copy().withStyle(ChatFormatting.GRAY));

        super.appendHoverText(stack, world, display, tooltip, context);
    }
}
