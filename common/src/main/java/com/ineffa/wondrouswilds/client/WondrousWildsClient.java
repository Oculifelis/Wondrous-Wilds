package com.ineffa.wondrouswilds.client;

import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import net.minecraft.world.level.block.Block;

import java.util.List;

/** Client data shared by both loaders. Each loader's client entry point registers renderers, render layers, colours and screens from here. */
public final class WondrousWildsClient {

    private WondrousWildsClient() {}

    public static List<Block> cutoutBlocks() {
        return List.of(
                WondrousWildsBlocks.SMALL_POLYPORE, WondrousWildsBlocks.BIG_POLYPORE,
                WondrousWildsBlocks.PURPLE_VIOLET, WondrousWildsBlocks.PINK_VIOLET, WondrousWildsBlocks.RED_VIOLET, WondrousWildsBlocks.WHITE_VIOLET,
                WondrousWildsBlocks.POTTED_PURPLE_VIOLET, WondrousWildsBlocks.POTTED_PINK_VIOLET, WondrousWildsBlocks.POTTED_RED_VIOLET, WondrousWildsBlocks.POTTED_WHITE_VIOLET);
    }

    public static List<Block> cutoutMippedBlocks() {
        return List.of(WondrousWildsBlocks.YELLOW_BIRCH_LEAVES, WondrousWildsBlocks.ORANGE_BIRCH_LEAVES, WondrousWildsBlocks.RED_BIRCH_LEAVES);
    }
}
