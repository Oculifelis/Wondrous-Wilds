package com.ineffa.wondrouswilds.networking;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.platform.IPlatformHelper;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

/** One payload type carries every server-to-client packet: the packet's index in S2C_PACKETS and its raw bytes. */
public record WondrousWildsPayload(int index, byte[] payload) implements CustomPacketPayload {

    public static final Type<WondrousWildsPayload> TYPE = new Type<>(WondrousWilds.id("main"));

    public static final StreamCodec<FriendlyByteBuf, WondrousWildsPayload> CODEC = StreamCodec.of(
            (buf, p) -> {
                buf.writeVarInt(p.index());
                buf.writeByteArray(p.payload());
            },
            buf -> new WondrousWildsPayload(buf.readVarInt(), buf.readByteArray()));

    public static WondrousWildsPayload of(IPlatformHelper.ModPacket packet) {
        int index = WondrousWildsNetwork.S2C_PACKETS.indexOf(packet.id());
        if (index < 0) throw new IllegalArgumentException("Unknown packet " + packet.id());

        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        packet.writer().accept(buf);
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        return new WondrousWildsPayload(index, bytes);
    }

    /** Client side only. The common handler queues its own work on the client thread. */
    public void handleClient() {
        WondrousWildsNetwork.handleClient(WondrousWildsNetwork.S2C_PACKETS.get(this.index), new FriendlyByteBuf(Unpooled.wrappedBuffer(this.payload)));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
