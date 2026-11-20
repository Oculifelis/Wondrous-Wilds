package com.ineffa.wondrouswilds.networking;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.networking.packets.s2c.WoodpeckerDrillPacket;
import com.ineffa.wondrouswilds.networking.packets.s2c.WoodpeckerInteractWithBlockPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public final class WondrousWildsNetwork {

    public static final String WOODPECKER_DRILL = "woodpecker_drill";
    public static final String WOODPECKER_INTERACT_WITH_BLOCK = "woodpecker_interact_with_block";

    /** Every server-to-client packet, in a fixed order (Forge uses the index as the discriminator). */
    public static final List<String> S2C_PACKETS = List.of(WOODPECKER_DRILL, WOODPECKER_INTERACT_WITH_BLOCK);

    public static ResourceLocation createChannelId(String name) {
        return WondrousWilds.id(name);
    }

    /** Client side only. Reads the payload at once and queues the work on the client thread. */
    public static void handleClient(String id, FriendlyByteBuf buf) {
        switch (id) {
            case WOODPECKER_DRILL -> WoodpeckerDrillPacket.receive(buf);
            case WOODPECKER_INTERACT_WITH_BLOCK -> WoodpeckerInteractWithBlockPacket.receive(buf);
            default -> WondrousWilds.LOGGER.warn("Unknown packet {}", id);
        }
    }
}
