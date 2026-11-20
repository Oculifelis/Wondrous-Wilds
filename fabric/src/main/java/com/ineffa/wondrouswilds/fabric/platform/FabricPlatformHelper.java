package com.ineffa.wondrouswilds.fabric.platform;

import com.ineffa.wondrouswilds.networking.WondrousWildsNetwork;
import com.ineffa.wondrouswilds.platform.IPlatformHelper;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
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
        for (ServerPlayer player : PlayerLookup.tracking(entity)) {
            FriendlyByteBuf buf = PacketByteBufs.create();
            packet.writer().accept(buf);
            ServerPlayNetworking.send(player, WondrousWildsNetwork.createChannelId(packet.id()), buf);
        }
    }
}
