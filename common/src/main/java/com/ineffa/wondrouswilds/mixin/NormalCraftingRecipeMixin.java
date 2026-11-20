package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.items.recipes.SecretRecipeInfo;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.NormalCraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NormalCraftingRecipe.class)
public abstract class NormalCraftingRecipeMixin implements SecretRecipeInfo {
    @Shadow @Final protected Recipe.CommonInfo commonInfo;
    @Shadow @Final protected CraftingRecipe.CraftingBookInfo bookInfo;

    @Override
    public Recipe.CommonInfo wondrouswilds$commonInfo() {
        return this.commonInfo;
    }

    @Override
    public CraftingRecipe.CraftingBookInfo wondrouswilds$bookInfo() {
        return this.bookInfo;
    }
}
