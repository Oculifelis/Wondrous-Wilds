package com.ineffa.wondrouswilds.networking.packets.s2c;

import com.ineffa.wondrouswilds.networking.WondrousWildsNetwork;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.BlockHitResult;

public final class WoodpeckerInteractWithBlockPacket {

    public static final Identifier ID = WondrousWildsNetwork.createChannelId(WondrousWildsNetwork.WOODPECKER_INTERACT_WITH_BLOCK);

    public static void write(FriendlyByteBuf buf, int entityId, BlockHitResult hitResult, boolean useItem) {
        buf.writeVarInt(entityId);
        BlockHitResult.STREAM_CODEC.encode(buf, hitResult);
        buf.writeBoolean(useItem);
    }
}
