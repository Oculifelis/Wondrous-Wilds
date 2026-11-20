package com.ineffa.wondrouswilds.forge.platform;

import com.ineffa.wondrouswilds.forge.network.ForgeNetwork;
import com.ineffa.wondrouswilds.platform.IPlatformHelper;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.ModList;

public class ForgePlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public void sendToTracking(Entity entity, ModPacket packet) {
        ForgeNetwork.sendToTracking(entity, packet);
    }
}
