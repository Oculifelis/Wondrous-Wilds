package com.ineffa.wondrouswilds.client.rendering;

import com.ineffa.wondrouswilds.registry.WondrousWildsBlocks;
import net.minecraft.world.level.block.Block;

import java.util.LinkedHashMap;
import java.util.Map;

/** Fixed tints of the coloured birch leaves. Each loader registers these for the blocks and their items. */
public final class WondrousWildsColorProviders {

    private WondrousWildsColorProviders() {}

    public static Map<Block, Integer> leafColors() {
        Map<Block, Integer> map = new LinkedHashMap<>();
        map.put(WondrousWildsBlocks.YELLOW_BIRCH_LEAVES, getYellowBirchLeavesColor());
        map.put(WondrousWildsBlocks.ORANGE_BIRCH_LEAVES, getOrangeBirchLeavesColor());
        map.put(WondrousWildsBlocks.RED_BIRCH_LEAVES, getRedBirchLeavesColor());
        return map;
    }

    public static int getYellowBirchLeavesColor() {
        return 14924323;
    }

    public static int getOrangeBirchLeavesColor() {
        return 15304240;
    }

    public static int getRedBirchLeavesColor() {
        return 16204080;
    }
}
