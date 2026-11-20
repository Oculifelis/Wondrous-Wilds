package com.ineffa.wondrouswilds.networking.packets.s2c;

import com.ineffa.wondrouswilds.networking.WondrousWildsNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public final class WoodpeckerDrillPacket {

    public static final ResourceLocation ID = WondrousWildsNetwork.createChannelId(WondrousWildsNetwork.WOODPECKER_DRILL);

    public static void write(FriendlyByteBuf buf, BlockPos drillPos, Direction drillSide) {
        buf.writeBlockPos(drillPos);
        buf.writeEnum(drillSide);
    }

    public static void receive(FriendlyByteBuf buf) {
        BlockPos drillPos = buf.readBlockPos();
        Direction drillSide = buf.readEnum(Direction.class);

        Minecraft client = Minecraft.getInstance();
        client.execute(() -> {
            for (int i = 0; i < 10; ++i) client.particleEngine.crack(drillPos, drillSide);
        });
    }
}
