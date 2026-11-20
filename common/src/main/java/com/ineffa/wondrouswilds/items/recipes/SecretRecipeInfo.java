package com.ineffa.wondrouswilds.items.recipes;

import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;

/** Implemented on NormalCraftingRecipe by a mixin, to read its protected info fields. */
public interface SecretRecipeInfo {
    Recipe.CommonInfo wondrouswilds$commonInfo();

    CraftingRecipe.CraftingBookInfo wondrouswilds$bookInfo();
}
