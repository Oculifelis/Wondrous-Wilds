package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.items.LovifierItem;
import com.ineffa.wondrouswilds.items.ScrollOfSecretsItem;
import com.ineffa.wondrouswilds.items.recipes.ShapedSecretRecipe;
import com.ineffa.wondrouswilds.items.recipes.ShapelessSecretRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import com.ineffa.wondrouswilds.WondrousWilds;
import net.minecraft.resources.ResourceKey;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.List;

public class WondrousWildsItems {

    /** Every item, in the order the original's item group listed them. */
    private static final List<Item> TAB_ITEMS = new ArrayList<>();

    public static final BlockItem PURPLE_VIOLET = registerBlockItem(WondrousWildsBlocks.PURPLE_VIOLET);
    public static final BlockItem PINK_VIOLET = registerBlockItem(WondrousWildsBlocks.PINK_VIOLET);
    public static final BlockItem RED_VIOLET = registerBlockItem(WondrousWildsBlocks.RED_VIOLET);
    public static final BlockItem WHITE_VIOLET = registerBlockItem(WondrousWildsBlocks.WHITE_VIOLET);

    public static final BlockItem SMALL_POLYPORE = registerBlockItem(WondrousWildsBlocks.SMALL_POLYPORE);
    public static final BlockItem BIG_POLYPORE = registerBlockItem(WondrousWildsBlocks.BIG_POLYPORE);

    public static final BlockItem YELLOW_BIRCH_LEAVES = registerBlockItem(WondrousWildsBlocks.YELLOW_BIRCH_LEAVES);
    public static final BlockItem ORANGE_BIRCH_LEAVES = registerBlockItem(WondrousWildsBlocks.ORANGE_BIRCH_LEAVES);
    public static final BlockItem RED_BIRCH_LEAVES = registerBlockItem(WondrousWildsBlocks.RED_BIRCH_LEAVES);

    public static final BlockItem DEAD_OAK_LOG = registerBlockItem(WondrousWildsBlocks.DEAD_OAK_LOG);
    public static final BlockItem DEAD_SPRUCE_LOG = registerBlockItem(WondrousWildsBlocks.DEAD_SPRUCE_LOG);
    public static final BlockItem DEAD_BIRCH_LOG = registerBlockItem(WondrousWildsBlocks.DEAD_BIRCH_LOG);
    public static final BlockItem DEAD_JUNGLE_LOG = registerBlockItem(WondrousWildsBlocks.DEAD_JUNGLE_LOG);
    public static final BlockItem DEAD_ACACIA_LOG = registerBlockItem(WondrousWildsBlocks.DEAD_ACACIA_LOG);
    public static final BlockItem DEAD_DARK_OAK_LOG = registerBlockItem(WondrousWildsBlocks.DEAD_DARK_OAK_LOG);
    public static final BlockItem DEAD_MANGROVE_LOG = registerBlockItem(WondrousWildsBlocks.DEAD_MANGROVE_LOG);
    public static final BlockItem DEAD_CRIMSON_STEM = registerBlockItem(WondrousWildsBlocks.DEAD_CRIMSON_STEM);
    public static final BlockItem DEAD_WARPED_STEM = registerBlockItem(WondrousWildsBlocks.DEAD_WARPED_STEM);

    public static final BlockItem DEAD_OAK_WOOD = registerBlockItem(WondrousWildsBlocks.DEAD_OAK_WOOD);
    public static final BlockItem DEAD_SPRUCE_WOOD = registerBlockItem(WondrousWildsBlocks.DEAD_SPRUCE_WOOD);
    public static final BlockItem DEAD_BIRCH_WOOD = registerBlockItem(WondrousWildsBlocks.DEAD_BIRCH_WOOD);
    public static final BlockItem DEAD_JUNGLE_WOOD = registerBlockItem(WondrousWildsBlocks.DEAD_JUNGLE_WOOD);
    public static final BlockItem DEAD_ACACIA_WOOD = registerBlockItem(WondrousWildsBlocks.DEAD_ACACIA_WOOD);
    public static final BlockItem DEAD_DARK_OAK_WOOD = registerBlockItem(WondrousWildsBlocks.DEAD_DARK_OAK_WOOD);
    public static final BlockItem DEAD_MANGROVE_WOOD = registerBlockItem(WondrousWildsBlocks.DEAD_MANGROVE_WOOD);
    public static final BlockItem DEAD_CRIMSON_HYPHAE = registerBlockItem(WondrousWildsBlocks.DEAD_CRIMSON_HYPHAE);
    public static final BlockItem DEAD_WARPED_HYPHAE = registerBlockItem(WondrousWildsBlocks.DEAD_WARPED_HYPHAE);

    public static final BlockItem HOLLOW_OAK_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_OAK_LOG);
    public static final BlockItem HOLLOW_SPRUCE_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_SPRUCE_LOG);
    public static final BlockItem HOLLOW_BIRCH_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_BIRCH_LOG);
    public static final BlockItem HOLLOW_JUNGLE_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_JUNGLE_LOG);
    public static final BlockItem HOLLOW_ACACIA_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_ACACIA_LOG);
    public static final BlockItem HOLLOW_DARK_OAK_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_DARK_OAK_LOG);
    public static final BlockItem HOLLOW_MANGROVE_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_MANGROVE_LOG);
    public static final BlockItem HOLLOW_CRIMSON_STEM = registerBlockItem(WondrousWildsBlocks.HOLLOW_CRIMSON_STEM);
    public static final BlockItem HOLLOW_WARPED_STEM = registerBlockItem(WondrousWildsBlocks.HOLLOW_WARPED_STEM);

    public static final BlockItem HOLLOW_DEAD_OAK_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_DEAD_OAK_LOG);
    public static final BlockItem HOLLOW_DEAD_SPRUCE_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_DEAD_SPRUCE_LOG);
    public static final BlockItem HOLLOW_DEAD_BIRCH_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_DEAD_BIRCH_LOG);
    public static final BlockItem HOLLOW_DEAD_JUNGLE_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_DEAD_JUNGLE_LOG);
    public static final BlockItem HOLLOW_DEAD_ACACIA_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_DEAD_ACACIA_LOG);
    public static final BlockItem HOLLOW_DEAD_DARK_OAK_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_DEAD_DARK_OAK_LOG);
    public static final BlockItem HOLLOW_DEAD_MANGROVE_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_DEAD_MANGROVE_LOG);
    public static final BlockItem HOLLOW_DEAD_CRIMSON_STEM = registerBlockItem(WondrousWildsBlocks.HOLLOW_DEAD_CRIMSON_STEM);
    public static final BlockItem HOLLOW_DEAD_WARPED_STEM = registerBlockItem(WondrousWildsBlocks.HOLLOW_DEAD_WARPED_STEM);

    public static final BlockItem HOLLOW_STRIPPED_OAK_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_OAK_LOG);
    public static final BlockItem HOLLOW_STRIPPED_SPRUCE_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_SPRUCE_LOG);
    public static final BlockItem HOLLOW_STRIPPED_BIRCH_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_BIRCH_LOG);
    public static final BlockItem HOLLOW_STRIPPED_JUNGLE_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_JUNGLE_LOG);
    public static final BlockItem HOLLOW_STRIPPED_ACACIA_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_ACACIA_LOG);
    public static final BlockItem HOLLOW_STRIPPED_DARK_OAK_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_DARK_OAK_LOG);
    public static final BlockItem HOLLOW_STRIPPED_MANGROVE_LOG = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_MANGROVE_LOG);
    public static final BlockItem HOLLOW_STRIPPED_CRIMSON_STEM = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_CRIMSON_STEM);
    public static final BlockItem HOLLOW_STRIPPED_WARPED_STEM = registerBlockItem(WondrousWildsBlocks.HOLLOW_STRIPPED_WARPED_STEM);

    public static final BlockItem OAK_TREE_HOLLOW = registerBlockItem(WondrousWildsBlocks.OAK_TREE_HOLLOW);
    public static final BlockItem SPRUCE_TREE_HOLLOW = registerBlockItem(WondrousWildsBlocks.SPRUCE_TREE_HOLLOW);
    public static final BlockItem BIRCH_TREE_HOLLOW = registerBlockItem(WondrousWildsBlocks.BIRCH_TREE_HOLLOW);
    public static final BlockItem JUNGLE_TREE_HOLLOW = registerBlockItem(WondrousWildsBlocks.JUNGLE_TREE_HOLLOW);
    public static final BlockItem ACACIA_TREE_HOLLOW = registerBlockItem(WondrousWildsBlocks.ACACIA_TREE_HOLLOW);
    public static final BlockItem DARK_OAK_TREE_HOLLOW = registerBlockItem(WondrousWildsBlocks.DARK_OAK_TREE_HOLLOW);
    public static final BlockItem MANGROVE_TREE_HOLLOW = registerBlockItem(WondrousWildsBlocks.MANGROVE_TREE_HOLLOW);

    public static final BlockItem BIRCH_NEST_BOX = registerBlockItem(WondrousWildsBlocks.BIRCH_NEST_BOX);

    public static final Item WOODPECKER_CREST_FEATHER = registerItem("woodpecker_crest_feather", Item::new, new Item.Properties());

    //public static final ScrollOfSecretsItem SCROLL_OF_SECRETS_NEST_BOX = registerScrollOfSecretsItem("scroll_of_secrets_nest_box", BIRCH_NEST_BOX);

    public static final Item FIREFLY_SPAWN_EGG = registerItem("firefly_spawn_egg", SpawnEggItem::new, new Item.Properties().spawnEgg(WondrousWildsEntities.FIREFLY));
    public static final Item WOODPECKER_SPAWN_EGG = registerItem("woodpecker_spawn_egg", SpawnEggItem::new, new Item.Properties().spawnEgg(WondrousWildsEntities.WOODPECKER));

    public static final Item LOVIFIER = registerItem("lovifier", LovifierItem::new, new Item.Properties().stacksTo(1));

    public static final CreativeModeTab WONDROUS_WILDS_ITEM_GROUP = WondrousWildsRegistration.add(Registries.CREATIVE_MODE_TAB, "wondrous_wilds", CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .title(Component.translatable("itemGroup.wondrouswilds.wondrous_wilds"))
            .icon(() -> new ItemStack(WondrousWildsItems.WOODPECKER_CREST_FEATHER))
            .displayItems((parameters, output) -> TAB_ITEMS.forEach(output::accept))
            .build());

    private static Item registerItem(String name, Function<Item.Properties, ? extends Item> factory, Item.Properties properties) {
        Item item = factory.apply(properties.setId(ResourceKey.create(Registries.ITEM, WondrousWilds.id(name))));
        TAB_ITEMS.add(item);
        return WondrousWildsRegistration.add(Registries.ITEM, name, item);
    }

    private static BlockItem registerBlockItem(Block block) {
        return (BlockItem) registerItem(WondrousWildsRegistration.nameOf(block), p -> new BlockItem(block, p), new Item.Properties().useBlockDescriptionPrefix());
    }

    private static ScrollOfSecretsItem registerScrollOfSecretsItem(String name, Item... itemsToUnlock) {
        return (ScrollOfSecretsItem) registerItem(name, p -> new ScrollOfSecretsItem(p, itemsToUnlock), new Item.Properties().stacksTo(1));
    }

    public static void initialize() {
        addCompostable(0.65F, PURPLE_VIOLET);
        addCompostable(0.65F, PINK_VIOLET);
        addCompostable(0.65F, RED_VIOLET);
        addCompostable(0.65F, WHITE_VIOLET);

        addCompostable(0.3F, SMALL_POLYPORE);
        addCompostable(0.65F, BIG_POLYPORE);

        addCompostable(0.3F, YELLOW_BIRCH_LEAVES);
        addCompostable(0.3F, ORANGE_BIRCH_LEAVES);
        addCompostable(0.3F, RED_BIRCH_LEAVES);

        // Wandering trader offers are datapack files: data/wondrouswilds/villager_trade/wandering_trader.

        RecipeSerializers.initialize();
    }

    private static void addCompostable(float chance, ItemLike item) {
        ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
    }

    public static final class RecipeSerializers {
        public static final RecipeSerializer<ShapedSecretRecipe> SECRET_SHAPED = registerRecipeSerializer("crafting_shaped_secret", new RecipeSerializer<>(ShapedSecretRecipe.CODEC, ShapedSecretRecipe.STREAM_CODEC));
        public static final RecipeSerializer<ShapelessSecretRecipe> SECRET_SHAPELESS = registerRecipeSerializer("crafting_shapeless_secret", new RecipeSerializer<>(ShapelessSecretRecipe.CODEC, ShapelessSecretRecipe.STREAM_CODEC));

        private static <T extends Recipe<?>> RecipeSerializer<T> registerRecipeSerializer(String name, RecipeSerializer<T> serializer) {
            return WondrousWildsRegistration.add(Registries.RECIPE_SERIALIZER, name, serializer);
        }

        public static void initialize() {}
    }
}
