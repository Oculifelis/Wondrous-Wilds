package com.ineffa.wondrouswilds.items.recipes;

import com.ineffa.wondrouswilds.mixin.ShapedRecipeAccessor;
import com.ineffa.wondrouswilds.registry.WondrousWildsItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

public class ShapedSecretRecipe extends ShapedRecipe implements SecretRecipe {

    public ShapedSecretRecipe(Recipe.CommonInfo commonInfo, CraftingRecipe.CraftingBookInfo bookInfo, ShapedRecipePattern pattern, ItemStackTemplate result) {
        super(commonInfo, bookInfo, pattern, result);
    }

    private static ShapedSecretRecipe of(ShapedRecipe recipe) {
        ShapedRecipeAccessor access = (ShapedRecipeAccessor) recipe;
        return new ShapedSecretRecipe(((SecretRecipeInfo) recipe).wondrouswilds$commonInfo(), ((SecretRecipeInfo) recipe).wondrouswilds$bookInfo(), access.wondrouswilds$getPattern(), access.wondrouswilds$getResult());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public RecipeSerializer<ShapedRecipe> getSerializer() {
        return (RecipeSerializer) WondrousWildsItems.RecipeSerializers.SECRET_SHAPED;
    }

    /** Same format as the vanilla shaped recipe, read through the vanilla codecs. */
    public static final MapCodec<ShapedSecretRecipe> CODEC = ShapedRecipe.MAP_CODEC.xmap(ShapedSecretRecipe::of, r -> r);
    public static final StreamCodec<RegistryFriendlyByteBuf, ShapedSecretRecipe> STREAM_CODEC = ShapedRecipe.STREAM_CODEC.map(ShapedSecretRecipe::of, r -> r);
}
