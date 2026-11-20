package com.ineffa.wondrouswilds.mixin;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(at = @At("TAIL"), method = "handleLogin")
    // Port: the client field moved to ClientCommonPacketListenerImpl, so the instance is read directly.
    private void sendIncompatibilityWarning(ClientboundLoginPacket packet, CallbackInfo callback) {
        if (WondrousWilds.config.clientSettings.showShadersWarning && Minecraft.getInstance().player != null) {
            // Port: Oculus is the Forge build of Iris, so it gets the same warning.
            if (Services.PLATFORM.isModLoaded("iris") || Services.PLATFORM.isModLoaded("optifabric") || Services.PLATFORM.isModLoaded("oculus")) {
                Minecraft.getInstance().player.sendSystemMessage(Component.translatable("mod." + WondrousWilds.MOD_ID + ".warning.shaders").withStyle(ChatFormatting.RED));
            }
        }
    }
}
