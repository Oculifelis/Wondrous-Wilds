package com.ineffa.wondrouswilds.fabric.platform;

import com.ineffa.wondrouswilds.networking.WondrousWildsPayload;
import com.ineffa.wondrouswilds.platform.IPlatformHelper;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public void sendToTracking(Entity entity, ModPacket packet) {
        WondrousWildsPayload payload = WondrousWildsPayload.of(packet);
        for (ServerPlayer player : PlayerLookup.tracking(entity))
            ServerPlayNetworking.send(player, payload);
    }
}
