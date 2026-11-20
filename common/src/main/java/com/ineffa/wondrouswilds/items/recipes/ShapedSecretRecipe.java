package com.ineffa.wondrouswilds.items.recipes;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

public class ShapedSecretRecipe extends ShapedRecipe implements SecretRecipe {

    public ShapedSecretRecipe(String group, CraftingBookCategory category, ShapedRecipePattern pattern, ItemStack output, boolean showNotification) {
        super(group, category, pattern, output, showNotification);
    }

    private static ShapedSecretRecipe of(ShapedRecipe recipe) {
        return new ShapedSecretRecipe(recipe.getGroup(), recipe.category(), recipe.pattern, recipe.getResultItem(null), recipe.showNotification());
    }

    /** Same format as the vanilla shaped recipe, read through the vanilla codecs. */
    public static class Serializer implements RecipeSerializer<ShapedSecretRecipe> {
        private static final MapCodec<ShapedSecretRecipe> CODEC = RecipeSerializer.SHAPED_RECIPE.codec().xmap(ShapedSecretRecipe::of, r -> r);
        private static final StreamCodec<RegistryFriendlyByteBuf, ShapedSecretRecipe> STREAM_CODEC = RecipeSerializer.SHAPED_RECIPE.streamCodec().map(ShapedSecretRecipe::of, r -> r);

        @Override
        public MapCodec<ShapedSecretRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShapedSecretRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
