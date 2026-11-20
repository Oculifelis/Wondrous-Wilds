package com.ineffa.wondrouswilds.platform;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.function.Consumer;

public interface IPlatformHelper {
    String getPlatformName();

    boolean isModLoaded(String modId);

    /** Sends a server-to-client packet to every player tracking the entity. */
    void sendToTracking(Entity entity, ModPacket packet);

    /** The packet's payload is written by {@code writer}; {@code id} names one of the packets in WondrousWildsNetwork. */
    record ModPacket(String id, Consumer<FriendlyByteBuf> writer) {}
}
