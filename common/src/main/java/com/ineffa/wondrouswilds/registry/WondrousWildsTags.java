package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class WondrousWildsTags {

    public static class BlockTags {
        public static final TagKey<Block> VIOLETS = createBlockTag("violets");
        public static final TagKey<Block> WOODPECKERS_INTERACT_WITH = createBlockTag("woodpeckers_interact_with");

        public static final TagKey<Block> FIREFLIES_SPAWNABLE_ON = createBlockTag("fireflies_spawnable_on");
        public static final TagKey<Block> FIREFLIES_HIDE_IN = createBlockTag("fireflies_hide_in");
    }

    public static class ItemTags {
        public static final TagKey<Item> VIOLETS = createItemTag("violets");
    }

    public static class BiomeTags {
        public static final TagKey<Biome> SPAWNS_FIREFLIES_ON_SURFACE = createBiomeTag("spawns_fireflies_on_surface");
        public static final TagKey<Biome> SPAWNS_FIREFLIES_ON_SURFACE_ONLY_IN_RAIN = createBiomeTag("spawns_fireflies_on_surface_only_in_rain");
        public static final TagKey<Biome> SPAWNS_FIREFLIES_UNDERGROUND = createBiomeTag("spawns_fireflies_underground");
    }

    private static TagKey<Block> createBlockTag(String name) {
        return TagKey.create(Registries.BLOCK, WondrousWilds.id(name));
    }

    private static TagKey<Item> createItemTag(String name) {
        return TagKey.create(Registries.ITEM, WondrousWilds.id(name));
    }

    private static TagKey<Biome> createBiomeTag(String name) {
        return TagKey.create(Registries.BIOME, WondrousWilds.id(name));
    }
}
