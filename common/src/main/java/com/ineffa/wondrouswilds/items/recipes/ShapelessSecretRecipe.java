package com.ineffa.wondrouswilds.items.recipes;

import com.ineffa.wondrouswilds.mixin.ShapelessRecipeAccessor;
import com.ineffa.wondrouswilds.registry.WondrousWildsItems;
import com.mojang.serialization.MapCodec;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class ShapelessSecretRecipe extends ShapelessRecipe implements SecretRecipe {

    public ShapelessSecretRecipe(Recipe.CommonInfo commonInfo, CraftingRecipe.CraftingBookInfo bookInfo, ItemStackTemplate result, List<Ingredient> ingredients) {
        super(commonInfo, bookInfo, result, ingredients);
    }

    private static ShapelessSecretRecipe of(ShapelessRecipe recipe) {
        ShapelessRecipeAccessor access = (ShapelessRecipeAccessor) recipe;
        return new ShapelessSecretRecipe(((SecretRecipeInfo) recipe).wondrouswilds$commonInfo(), ((SecretRecipeInfo) recipe).wondrouswilds$bookInfo(), access.wondrouswilds$getResult(), access.wondrouswilds$getIngredients());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public RecipeSerializer<ShapelessRecipe> getSerializer() {
        return (RecipeSerializer) WondrousWildsItems.RecipeSerializers.SECRET_SHAPELESS;
    }

    /** Same format as the vanilla shapeless recipe, read through the vanilla codecs. */
    public static final MapCodec<ShapelessSecretRecipe> CODEC = ShapelessRecipe.MAP_CODEC.xmap(ShapelessSecretRecipe::of, r -> r);
    public static final StreamCodec<RegistryFriendlyByteBuf, ShapelessSecretRecipe> STREAM_CODEC = ShapelessRecipe.STREAM_CODEC.map(ShapelessSecretRecipe::of, r -> r);
}
