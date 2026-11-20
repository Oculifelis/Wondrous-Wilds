package com.ineffa.wondrouswilds.registry;

import com.google.common.collect.ImmutableMap;
import com.ineffa.wondrouswilds.blocks.*;
import com.ineffa.wondrouswilds.blocks.entity.NestBoxBlockEntity;
import com.ineffa.wondrouswilds.blocks.entity.TreeHollowBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.Map;

public class WondrousWildsBlocks {

    public static final Block PURPLE_VIOLET = registerBlock("purple_violet", new VioletBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final Block PINK_VIOLET = registerBlock("pink_violet", new VioletBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final Block RED_VIOLET = registerBlock("red_violet", new VioletBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final Block WHITE_VIOLET = registerBlock("white_violet", new VioletBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));
    public static final Block POTTED_PURPLE_VIOLET = registerBlock("potted_purple_violet", new FlowerPotBlock(PURPLE_VIOLET, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).instabreak().noOcclusion()));
    public static final Block POTTED_PINK_VIOLET = registerBlock("potted_pink_violet", new FlowerPotBlock(PINK_VIOLET, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).instabreak().noOcclusion()));
    public static final Block POTTED_RED_VIOLET = registerBlock("potted_red_violet", new FlowerPotBlock(RED_VIOLET, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).instabreak().noOcclusion()));
    public static final Block POTTED_WHITE_VIOLET = registerBlock("potted_white_violet", new FlowerPotBlock(WHITE_VIOLET, BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).instabreak().noOcclusion()));

    public static final Block SMALL_POLYPORE = registerBlock("small_polypore", new SmallPolyporeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS).noOcclusion().instabreak().noCollission()));
    public static final Block BIG_POLYPORE = registerBlock("big_polypore", new BigPolyporeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).pushReaction(PushReaction.DESTROY).sound(SoundType.GRASS).noOcclusion().instabreak()));

    public static final Block YELLOW_BIRCH_LEAVES = registerBlock("yellow_birch_leaves", new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_LEAVES)));
    public static final Block ORANGE_BIRCH_LEAVES = registerBlock("orange_birch_leaves", new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_LEAVES)));
    public static final Block RED_BIRCH_LEAVES = registerBlock("red_birch_leaves", new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_LEAVES)));

    public static final Block DEAD_OAK_LOG = registerBlock("dead_oak_log", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final Block DEAD_SPRUCE_LOG = registerBlock("dead_spruce_log", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_LOG)));
    public static final Block DEAD_BIRCH_LOG = registerBlock("dead_birch_log", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_LOG)));
    public static final Block DEAD_JUNGLE_LOG = registerBlock("dead_jungle_log", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_LOG)));
    public static final Block DEAD_ACACIA_LOG = registerBlock("dead_acacia_log", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_LOG)));
    public static final Block DEAD_DARK_OAK_LOG = registerBlock("dead_dark_oak_log", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_LOG)));
    public static final Block DEAD_MANGROVE_LOG = registerBlock("dead_mangrove_log", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.MANGROVE_LOG)));
    public static final Block DEAD_CRIMSON_STEM = registerBlock("dead_crimson_stem", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_STEM)));
    public static final Block DEAD_WARPED_STEM = registerBlock("dead_warped_stem", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_STEM)));

    public static final Block DEAD_OAK_WOOD = registerBlock("dead_oak_wood", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
    public static final Block DEAD_SPRUCE_WOOD = registerBlock("dead_spruce_wood", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_WOOD)));
    public static final Block DEAD_BIRCH_WOOD = registerBlock("dead_birch_wood", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_WOOD)));
    public static final Block DEAD_JUNGLE_WOOD = registerBlock("dead_jungle_wood", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_WOOD)));
    public static final Block DEAD_ACACIA_WOOD = registerBlock("dead_acacia_wood", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_WOOD)));
    public static final Block DEAD_DARK_OAK_WOOD = registerBlock("dead_dark_oak_wood", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_WOOD)));
    public static final Block DEAD_MANGROVE_WOOD = registerBlock("dead_mangrove_wood", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.MANGROVE_WOOD)));
    public static final Block DEAD_CRIMSON_HYPHAE = registerBlock("dead_crimson_hyphae", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_HYPHAE)));
    public static final Block DEAD_WARPED_HYPHAE = registerBlock("dead_warped_hyphae", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_HYPHAE)));

    public static final Block HOLLOW_OAK_LOG = registerBlock("hollow_oak_log", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).noOcclusion()));
    public static final Block HOLLOW_SPRUCE_LOG = registerBlock("hollow_spruce_log", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_LOG).noOcclusion()));
    public static final Block HOLLOW_BIRCH_LOG = registerBlock("hollow_birch_log", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_LOG).noOcclusion()));
    public static final Block HOLLOW_JUNGLE_LOG = registerBlock("hollow_jungle_log", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.JUNGLE_LOG).noOcclusion()));
    public static final Block HOLLOW_ACACIA_LOG = registerBlock("hollow_acacia_log", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.ACACIA_LOG).noOcclusion()));
    public static final Block HOLLOW_DARK_OAK_LOG = registerBlock("hollow_dark_oak_log", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.DARK_OAK_LOG).noOcclusion()));
    public static final Block HOLLOW_MANGROVE_LOG = registerBlock("hollow_mangrove_log", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.MANGROVE_LOG).noOcclusion()));
    public static final Block HOLLOW_CRIMSON_STEM = registerBlock("hollow_crimson_stem", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_STEM).noOcclusion()));
    public static final Block HOLLOW_WARPED_STEM = registerBlock("hollow_warped_stem", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_STEM).noOcclusion()));

    public static final Block HOLLOW_DEAD_OAK_LOG = registerBlock("hollow_dead_oak_log", new HollowLogBlock(BlockBehaviour.Properties.copy(DEAD_OAK_LOG).noOcclusion()));
    public static final Block HOLLOW_DEAD_SPRUCE_LOG = registerBlock("hollow_dead_spruce_log", new HollowLogBlock(BlockBehaviour.Properties.copy(DEAD_SPRUCE_LOG).noOcclusion()));
    public static final Block HOLLOW_DEAD_BIRCH_LOG = registerBlock("hollow_dead_birch_log", new HollowLogBlock(BlockBehaviour.Properties.copy(DEAD_BIRCH_LOG).noOcclusion()));
    public static final Block HOLLOW_DEAD_JUNGLE_LOG = registerBlock("hollow_dead_jungle_log", new HollowLogBlock(BlockBehaviour.Properties.copy(DEAD_JUNGLE_LOG).noOcclusion()));
    public static final Block HOLLOW_DEAD_ACACIA_LOG = registerBlock("hollow_dead_acacia_log", new HollowLogBlock(BlockBehaviour.Properties.copy(DEAD_ACACIA_LOG).noOcclusion()));
    public static final Block HOLLOW_DEAD_DARK_OAK_LOG = registerBlock("hollow_dead_dark_oak_log", new HollowLogBlock(BlockBehaviour.Properties.copy(DEAD_DARK_OAK_LOG).noOcclusion()));
    public static final Block HOLLOW_DEAD_MANGROVE_LOG = registerBlock("hollow_dead_mangrove_log", new HollowLogBlock(BlockBehaviour.Properties.copy(DEAD_MANGROVE_LOG).noOcclusion()));
    public static final Block HOLLOW_DEAD_CRIMSON_STEM = registerBlock("hollow_dead_crimson_stem", new HollowLogBlock(BlockBehaviour.Properties.copy(DEAD_CRIMSON_STEM).noOcclusion()));
    public static final Block HOLLOW_DEAD_WARPED_STEM = registerBlock("hollow_dead_warped_stem", new HollowLogBlock(BlockBehaviour.Properties.copy(DEAD_WARPED_STEM).noOcclusion()));

    public static final Block HOLLOW_STRIPPED_OAK_LOG = registerBlock("hollow_stripped_oak_log", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).noOcclusion()));
    public static final Block HOLLOW_STRIPPED_SPRUCE_LOG = registerBlock("hollow_stripped_spruce_log", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_SPRUCE_LOG).noOcclusion()));
    public static final Block HOLLOW_STRIPPED_BIRCH_LOG = registerBlock("hollow_stripped_birch_log", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_BIRCH_LOG).noOcclusion()));
    public static final Block HOLLOW_STRIPPED_JUNGLE_LOG = registerBlock("hollow_stripped_jungle_log", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_JUNGLE_LOG).noOcclusion()));
    public static final Block HOLLOW_STRIPPED_ACACIA_LOG = registerBlock("hollow_stripped_acacia_log", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_ACACIA_LOG).noOcclusion()));
    public static final Block HOLLOW_STRIPPED_DARK_OAK_LOG = registerBlock("hollow_stripped_dark_oak_log", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_DARK_OAK_LOG).noOcclusion()));
    public static final Block HOLLOW_STRIPPED_MANGROVE_LOG = registerBlock("hollow_stripped_mangrove_log", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_MANGROVE_LOG).noOcclusion()));
    public static final Block HOLLOW_STRIPPED_CRIMSON_STEM = registerBlock("hollow_stripped_crimson_stem", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_CRIMSON_STEM).noOcclusion()));
    public static final Block HOLLOW_STRIPPED_WARPED_STEM = registerBlock("hollow_stripped_warped_stem", new HollowLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_WARPED_STEM).noOcclusion()));

    public static final TreeHollowBlock OAK_TREE_HOLLOW = (TreeHollowBlock) registerBlock("oak_tree_hollow", new TreeHollowBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(2.0f).sound(SoundType.WOOD)));
    public static final TreeHollowBlock SPRUCE_TREE_HOLLOW = (TreeHollowBlock) registerBlock("spruce_tree_hollow", new TreeHollowBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PODZOL).instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(2.0f).sound(SoundType.WOOD)));
    public static final TreeHollowBlock BIRCH_TREE_HOLLOW = (TreeHollowBlock) registerBlock("birch_tree_hollow", new TreeHollowBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(2.0f).sound(SoundType.WOOD)));
    public static final TreeHollowBlock JUNGLE_TREE_HOLLOW = (TreeHollowBlock) registerBlock("jungle_tree_hollow", new TreeHollowBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(2.0f).sound(SoundType.WOOD)));
    public static final TreeHollowBlock ACACIA_TREE_HOLLOW = (TreeHollowBlock) registerBlock("acacia_tree_hollow", new TreeHollowBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(2.0f).sound(SoundType.WOOD)));
    public static final TreeHollowBlock DARK_OAK_TREE_HOLLOW = (TreeHollowBlock) registerBlock("dark_oak_tree_hollow", new TreeHollowBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(2.0f).sound(SoundType.WOOD)));
    public static final TreeHollowBlock MANGROVE_TREE_HOLLOW = (TreeHollowBlock) registerBlock("mangrove_tree_hollow", new TreeHollowBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).instrument(NoteBlockInstrument.BASS).ignitedByLava().strength(2.0f).sound(SoundType.WOOD)));

    public static final NestBoxBlock BIRCH_NEST_BOX = (NestBoxBlock) registerBlock("birch_nest_box", new BirchNestBoxBlock(BlockBehaviour.Properties.copy(Blocks.BIRCH_PLANKS).noOcclusion()));

    public static final class BlockEntities {
        public static final BlockEntityType<TreeHollowBlockEntity> TREE_HOLLOW = registerBlockEntity("tree_hollow", BlockEntityType.Builder.of(TreeHollowBlockEntity::new,
                OAK_TREE_HOLLOW, SPRUCE_TREE_HOLLOW, BIRCH_TREE_HOLLOW, JUNGLE_TREE_HOLLOW, ACACIA_TREE_HOLLOW, DARK_OAK_TREE_HOLLOW, MANGROVE_TREE_HOLLOW
        ).build(null));

        public static final BlockEntityType<NestBoxBlockEntity> NEST_BOX = registerBlockEntity("nest_box", BlockEntityType.Builder.of(NestBoxBlockEntity::new, BIRCH_NEST_BOX).build(null));

        private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, BlockEntityType<T> blockEntityType) {
            return WondrousWildsRegistration.add(Registries.BLOCK_ENTITY_TYPE, name, blockEntityType);
        }

        public static void init() {}
    }

    private static Block registerBlock(String name, Block block) {
        return WondrousWildsRegistration.add(Registries.BLOCK, name, block);
    }

    /** Hollow log to its stripped hollow log, fed to each loader's axe-stripping hook. */
    public static final Map<Block, Block> HOLLOW_STRIPPABLES = new ImmutableMap.Builder<Block, Block>()
            .put(HOLLOW_OAK_LOG, HOLLOW_STRIPPED_OAK_LOG)
            .put(HOLLOW_SPRUCE_LOG, HOLLOW_STRIPPED_SPRUCE_LOG)
            .put(HOLLOW_BIRCH_LOG, HOLLOW_STRIPPED_BIRCH_LOG)
            .put(HOLLOW_JUNGLE_LOG, HOLLOW_STRIPPED_JUNGLE_LOG)
            .put(HOLLOW_ACACIA_LOG, HOLLOW_STRIPPED_ACACIA_LOG)
            .put(HOLLOW_DARK_OAK_LOG, HOLLOW_STRIPPED_DARK_OAK_LOG)
            .put(HOLLOW_MANGROVE_LOG, HOLLOW_STRIPPED_MANGROVE_LOG)
            .put(HOLLOW_CRIMSON_STEM, HOLLOW_STRIPPED_CRIMSON_STEM)
            .put(HOLLOW_WARPED_STEM, HOLLOW_STRIPPED_WARPED_STEM)
            .build();

    public static void initialize() {
        BlockEntities.init();
    }
}
