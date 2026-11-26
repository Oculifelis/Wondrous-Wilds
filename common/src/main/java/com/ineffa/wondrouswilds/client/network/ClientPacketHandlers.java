package com.ineffa.wondrouswilds.client.network;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.ineffa.wondrouswilds.networking.WondrousWildsNetwork;
import com.ineffa.wondrouswilds.util.fakeplayer.WoodpeckerFakePlayer;
import com.ineffa.wondrouswilds.util.fakeplayer.WoodpeckerItemUsageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

/** Client side only: reads each server-to-client packet and queues its work on the client thread. */
public final class ClientPacketHandlers {

    private ClientPacketHandlers() {}

    public static void handle(String id, FriendlyByteBuf buf) {
        switch (id) {
            case WondrousWildsNetwork.WOODPECKER_DRILL -> receiveDrill(buf);
            case WondrousWildsNetwork.WOODPECKER_INTERACT_WITH_BLOCK -> receiveInteractWithBlock(buf);
            default -> WondrousWilds.LOGGER.warn("Unknown packet {}", id);
        }
    }

    private static void receiveDrill(FriendlyByteBuf buf) {
        BlockPos drillPos = buf.readBlockPos();
        Direction drillSide = buf.readEnum(Direction.class);

        Minecraft client = Minecraft.getInstance();
        client.execute(() -> {
            for (int i = 0; i < 10; ++i) client.particleEngine.crack(drillPos, drillSide);
        });
    }

    private static void receiveInteractWithBlock(FriendlyByteBuf buf) {
        int entityId = buf.readVarInt();
        BlockHitResult hitResult = buf.readBlockHitResult();
        boolean useItem = buf.readBoolean();

        Minecraft client = Minecraft.getInstance();
        Level world = client.level;
        if (world == null) return;

        client.execute(() -> {
            Entity entity = world.getEntity(entityId);
            if (!(entity instanceof WoodpeckerEntity woodpecker)) return;

            WoodpeckerFakePlayer fakePlayer = new WoodpeckerFakePlayer(woodpecker);

            if (useItem) {
                ItemStack heldItem = woodpecker.getMainHandItem();
                if (!heldItem.isEmpty()) heldItem.useOn(new WoodpeckerItemUsageContext(woodpecker, fakePlayer, heldItem, hitResult));
            }
            else WoodpeckerEntity.useBlockAsFakePlayer(world.getBlockState(hitResult.getBlockPos()), world, fakePlayer, hitResult);
        });
    }
}
