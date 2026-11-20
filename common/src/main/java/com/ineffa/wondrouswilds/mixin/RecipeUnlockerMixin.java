package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.items.recipes.SecretRecipe;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeCraftingHolder.class)
public interface RecipeUnlockerMixin {

    @Inject(at = @At("HEAD"), method = "setRecipeUsed(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/crafting/RecipeHolder;)Z", cancellable = true)
    private void restrictCraftingSecrets(ServerPlayer player, RecipeHolder<?> recipe, CallbackInfoReturnable<Boolean> callback) {
        if (recipe.value() instanceof SecretRecipe && !player.getRecipeBook().contains(recipe.id())) callback.setReturnValue(false);
    }
}
