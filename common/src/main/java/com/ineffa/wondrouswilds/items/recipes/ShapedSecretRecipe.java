package com.ineffa.wondrouswilds.items.recipes;

import com.google.gson.JsonObject;
import com.ineffa.wondrouswilds.mixin.ShapedRecipeInvoker;
import java.util.Map;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class ShapedSecretRecipe extends ShapedRecipe implements SecretRecipe {

    public ShapedSecretRecipe(ResourceLocation id, String group, CraftingBookCategory category, int width, int height, NonNullList<Ingredient> input, ItemStack output) {
        super(id, group, category, width, height, input, output);
    }

    public static class Serializer implements RecipeSerializer<ShapedSecretRecipe> {

        @Override
        public ShapedSecretRecipe fromJson(ResourceLocation id, JsonObject json) {
            String string = GsonHelper.getAsString(json, "group", "");
            CraftingBookCategory category = CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", null), CraftingBookCategory.MISC);

            Map<String, Ingredient> map = ShapedRecipeInvoker.readSymbols(GsonHelper.getAsJsonObject(json, "key"));

            String[] strings = ShapedRecipeInvoker.removePadding(ShapedRecipeInvoker.getPattern(GsonHelper.getAsJsonArray(json, "pattern")));

            int i = strings[0].length();
            int j = strings.length;

            NonNullList<Ingredient> defaultedList = ShapedRecipeInvoker.createPatternMatrix(strings, map, i, j);

            ItemStack itemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            return new ShapedSecretRecipe(id, string, category, i, j, defaultedList, itemStack);
        }

        @Override
        public ShapedSecretRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int i = buf.readVarInt();
            int j = buf.readVarInt();
            String string = buf.readUtf();
            CraftingBookCategory category = buf.readEnum(CraftingBookCategory.class);

            NonNullList<Ingredient> defaultedList = NonNullList.withSize(i * j, Ingredient.EMPTY);
            for (int k = 0; k < defaultedList.size(); ++k) defaultedList.set(k, Ingredient.fromNetwork(buf));

            ItemStack itemStack = buf.readItem();

            return new ShapedSecretRecipe(id, string, category, i, j, defaultedList, itemStack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ShapedSecretRecipe recipe) {
            buf.writeVarInt(recipe.getWidth());
            buf.writeVarInt(recipe.getHeight());
            buf.writeUtf(recipe.getGroup());
            buf.writeEnum(recipe.category());

            for (Ingredient ingredient : recipe.getIngredients()) ingredient.toNetwork(buf);

            buf.writeItem(recipe.getResultItem(RegistryAccess.EMPTY));
        }
    }
}
