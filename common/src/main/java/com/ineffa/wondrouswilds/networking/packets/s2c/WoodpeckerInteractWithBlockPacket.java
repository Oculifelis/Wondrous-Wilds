package com.ineffa.wondrouswilds.networking.packets.s2c;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.ineffa.wondrouswilds.networking.WondrousWildsNetwork;
import com.ineffa.wondrouswilds.util.fakeplayer.WoodpeckerFakePlayer;
import com.ineffa.wondrouswilds.util.fakeplayer.WoodpeckerItemUsageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public final class WoodpeckerInteractWithBlockPacket {

    public static final Identifier ID = WondrousWildsNetwork.createChannelId(WondrousWildsNetwork.WOODPECKER_INTERACT_WITH_BLOCK);

    public static void write(FriendlyByteBuf buf, int entityId, BlockHitResult hitResult, boolean useItem) {
        buf.writeVarInt(entityId);
        BlockHitResult.STREAM_CODEC.encode(buf, hitResult);
        buf.writeBoolean(useItem);
    }

    public static void receive(FriendlyByteBuf buf) {
        int entityId = buf.readVarInt();
        BlockHitResult hitResult = BlockHitResult.STREAM_CODEC.decode(buf);
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
