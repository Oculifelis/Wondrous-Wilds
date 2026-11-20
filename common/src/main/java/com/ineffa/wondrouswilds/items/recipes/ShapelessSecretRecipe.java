package com.ineffa.wondrouswilds.items.recipes;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class ShapelessSecretRecipe extends ShapelessRecipe implements SecretRecipe {

    public ShapelessSecretRecipe(String group, CraftingBookCategory category, ItemStack output, NonNullList<Ingredient> input) {
        super(group, category, output, input);
    }

    private static ShapelessSecretRecipe of(ShapelessRecipe recipe) {
        return new ShapelessSecretRecipe(recipe.getGroup(), recipe.category(), recipe.getResultItem(null), recipe.getIngredients());
    }

    /** Same format as the vanilla shapeless recipe, read through the vanilla codecs. */
    public static class Serializer implements RecipeSerializer<ShapelessSecretRecipe> {
        private static final MapCodec<ShapelessSecretRecipe> CODEC = RecipeSerializer.SHAPELESS_RECIPE.codec().xmap(ShapelessSecretRecipe::of, r -> r);
        private static final StreamCodec<RegistryFriendlyByteBuf, ShapelessSecretRecipe> STREAM_CODEC = RecipeSerializer.SHAPELESS_RECIPE.streamCodec().map(ShapelessSecretRecipe::of, r -> r);

        @Override
        public MapCodec<ShapelessSecretRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShapelessSecretRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
