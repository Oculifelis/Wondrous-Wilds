package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.items.recipes.SecretRecipe;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeHolder.class)
public interface RecipeUnlockerMixin {

    @Inject(at = @At("HEAD"), method = "setRecipeUsed(Lnet/minecraft/world/level/Level;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/crafting/Recipe;)Z", cancellable = true)
    private void restrictCraftingSecrets(Level world, ServerPlayer player, Recipe<?> recipe, CallbackInfoReturnable<Boolean> callback) {
        if (recipe instanceof SecretRecipe && !player.getRecipeBook().contains(recipe)) callback.setReturnValue(false);
    }
}
