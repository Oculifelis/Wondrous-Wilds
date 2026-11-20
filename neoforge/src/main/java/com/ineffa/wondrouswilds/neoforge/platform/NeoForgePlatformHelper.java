package com.ineffa.wondrouswilds.neoforge.platform;

import com.ineffa.wondrouswilds.networking.WondrousWildsPayload;
import com.ineffa.wondrouswilds.platform.IPlatformHelper;
import net.minecraft.world.entity.Entity;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoForgePlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public void sendToTracking(Entity entity, ModPacket packet) {
        PacketDistributor.sendToPlayersTrackingEntity(entity, WondrousWildsPayload.of(packet));
    }
}
