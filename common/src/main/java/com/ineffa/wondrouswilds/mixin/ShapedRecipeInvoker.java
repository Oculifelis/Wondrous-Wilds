package com.ineffa.wondrouswilds.mixin;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;

@Mixin(ShapedRecipe.class)
public interface ShapedRecipeInvoker {

    @Invoker("dissolvePattern")
    static NonNullList<Ingredient> createPatternMatrix(String[] pattern, Map<String, Ingredient> symbols, int width, int height) {
        throw new AssertionError();
    }

    @Invoker("shrink")
    static String[] removePadding(String ... pattern) {
        throw new AssertionError();
    }

    @Invoker("patternFromJson")
    static String[] getPattern(JsonArray json) {
        throw new AssertionError();
    }

    @Invoker("keyFromJson")
    static Map<String, Ingredient> readSymbols(JsonObject json) {
        throw new AssertionError();
    }
}
