package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.items.recipes.SecretRecipe;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * The original injected at the head of RecipeHolder#setRecipeUsed, an interface default method. Forge 1.20.1's Mixin
 * can't inject into interfaces, so this checks the same thing at the crafting grid's only call (the player inventory grid
 * goes through it too). Secret recipes are crafting recipes, so no other caller can see one.
 */
@Mixin(CraftingMenu.class)
public abstract class RecipeUnlockerMixin {

    @Redirect(method = "slotChangedCraftingGrid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ResultContainer;setRecipeUsed(Lnet/minecraft/world/level/Level;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/crafting/Recipe;)Z"))
    private static boolean restrictCraftingSecrets(ResultContainer result, Level world, ServerPlayer player, Recipe<?> recipe) {
        if (recipe instanceof SecretRecipe && !player.getRecipeBook().contains(recipe)) return false;
        return result.setRecipeUsed(world, player, recipe);
    }
}
