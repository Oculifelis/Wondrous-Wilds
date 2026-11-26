package com.ineffa.wondrouswilds.networking;

import com.ineffa.wondrouswilds.WondrousWilds;
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
}
