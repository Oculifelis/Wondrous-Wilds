package com.ineffa.wondrouswilds.forge.network;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.networking.WondrousWildsNetwork;
import com.ineffa.wondrouswilds.platform.IPlatformHelper;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;
import java.util.function.Supplier;

/** One channel carries every server-to-client packet; each message holds the packet's index and its raw payload. */
public final class ForgeNetwork {

    private static final String PROTOCOL = "1";

    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(WondrousWilds.id("main"), () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);

    private static boolean registered = false;

    private ForgeNetwork() {}

    public static void register() {
        if (registered) return;
        registered = true;

        CHANNEL.registerMessage(0, Message.class, Message::encode, Message::decode, Message::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static void sendToTracking(Entity entity, IPlatformHelper.ModPacket packet) {
        int index = WondrousWildsNetwork.S2C_PACKETS.indexOf(packet.id());
        if (index < 0) throw new IllegalArgumentException("Unknown packet " + packet.id());

        FriendlyByteBuf payload = new FriendlyByteBuf(Unpooled.buffer());
        packet.writer().accept(payload);
        byte[] bytes = new byte[payload.readableBytes()];
        payload.readBytes(bytes);

        CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new Message(index, bytes));
    }

    record Message(int index, byte[] payload) {
        void encode(FriendlyByteBuf buf) {
            buf.writeVarInt(this.index);
            buf.writeByteArray(this.payload);
        }

        static Message decode(FriendlyByteBuf buf) {
            return new Message(buf.readVarInt(), buf.readByteArray());
        }

        void handle(Supplier<NetworkEvent.Context> context) {
            // The common handler queues its own work on the client thread.
            WondrousWildsNetwork.handleClient(WondrousWildsNetwork.S2C_PACKETS.get(this.index), new FriendlyByteBuf(Unpooled.wrappedBuffer(this.payload)));
            context.get().setPacketHandled(true);
        }
    }
}
