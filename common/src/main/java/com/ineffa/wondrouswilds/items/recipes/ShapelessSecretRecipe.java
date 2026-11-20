package com.ineffa.wondrouswilds.items.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
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
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class ShapelessSecretRecipe extends ShapelessRecipe implements SecretRecipe {

    public ShapelessSecretRecipe(ResourceLocation id, String group, CraftingBookCategory category, ItemStack output, NonNullList<Ingredient> input) {
        super(id, group, category, output, input);
    }

    public static class Serializer implements RecipeSerializer<ShapelessSecretRecipe> {

        @Override
        public ShapelessSecretRecipe fromJson(ResourceLocation id, JsonObject json) {
            NonNullList<Ingredient> defaultedList = com.ineffa.wondrouswilds.items.recipes.ShapelessSecretRecipe.Serializer.getIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));

            if (defaultedList.isEmpty()) throw new JsonParseException("No ingredients for shapeless secret recipe");
            if (defaultedList.size() > 9) throw new JsonParseException("Too many ingredients for shapeless secret recipe");

            String string = GsonHelper.getAsString(json, "group", "");
            CraftingBookCategory category = CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", null), CraftingBookCategory.MISC);
            ItemStack itemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new ShapelessSecretRecipe(id, string, category, itemStack, defaultedList);
        }

        @Override
        public ShapelessSecretRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            // Deviation: the original read the group last but wrote it first, which broke the packet. Read in write order.
            String group = buf.readUtf();
            CraftingBookCategory category = buf.readEnum(CraftingBookCategory.class);
            NonNullList<Ingredient> defaultedList = NonNullList.withSize(buf.readVarInt(), Ingredient.EMPTY);
            for (int j = 0; j < defaultedList.size(); ++j) defaultedList.set(j, Ingredient.fromNetwork(buf));

            ItemStack itemStack = buf.readItem();
            return new ShapelessSecretRecipe(id, group, category, itemStack, defaultedList);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ShapelessSecretRecipe recipe) {
            buf.writeUtf(recipe.getGroup());
            buf.writeEnum(recipe.category());
            buf.writeVarInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) ingredient.toNetwork(buf);

            buf.writeItem(recipe.getResultItem(RegistryAccess.EMPTY));
        }

        private static NonNullList<Ingredient> getIngredients(JsonArray json) {
            NonNullList<Ingredient> defaultedList = NonNullList.create();
            for (int i = 0; i < json.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if (ingredient.isEmpty()) continue;
                defaultedList.add(ingredient);
            }
            return defaultedList;
        }
    }
}
