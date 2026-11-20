package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.items.LovifierItem;
import com.ineffa.wondrouswilds.items.ScrollOfSecretsItem;
import com.ineffa.wondrouswilds.items.recipes.ShapedSecretRecipe;
import com.ineffa.wondrouswilds.items.recipes.ShapelessSecretRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import java.util.ArrayList;
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

    public static final Item WOODPECKER_CREST_FEATHER = registerItem("woodpecker_crest_feather", new Item(new Item.Properties()));

    //public static final ScrollOfSecretsItem SCROLL_OF_SECRETS_NEST_BOX = registerScrollOfSecretsItem("scroll_of_secrets_nest_box", BIRCH_NEST_BOX);

    public static final Item FIREFLY_SPAWN_EGG = registerItem("firefly_spawn_egg", new SpawnEggItem(WondrousWildsEntities.FIREFLY, 2563094, 14876540, new Item.Properties()));
    public static final Item WOODPECKER_SPAWN_EGG = registerItem("woodpecker_spawn_egg", new SpawnEggItem(WondrousWildsEntities.WOODPECKER, 2761271, 16740713, new Item.Properties()));

    public static final Item LOVIFIER = registerItem("lovifier", new LovifierItem(new Item.Properties().stacksTo(1)));

    public static final CreativeModeTab WONDROUS_WILDS_ITEM_GROUP = WondrousWildsRegistration.add(Registries.CREATIVE_MODE_TAB, "wondrous_wilds", CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .title(Component.translatable("itemGroup.wondrouswilds.wondrous_wilds"))
            .icon(() -> new ItemStack(WondrousWildsItems.WOODPECKER_CREST_FEATHER))
            .displayItems((parameters, output) -> TAB_ITEMS.forEach(output::accept))
            .build());

    private static Item registerItem(String name, Item item) {
        TAB_ITEMS.add(item);
        return WondrousWildsRegistration.add(Registries.ITEM, name, item);
    }

    private static BlockItem registerBlockItem(Block block) {
        return (BlockItem) registerItem(WondrousWildsRegistration.nameOf(block), new BlockItem(block, new Item.Properties()));
    }

    private static ScrollOfSecretsItem registerScrollOfSecretsItem(String name, Item... itemsToUnlock) {
        return (ScrollOfSecretsItem) registerItem(name, new ScrollOfSecretsItem(new Item.Properties().stacksTo(1), itemsToUnlock));
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

        Trades.addWandererTrade(false, 16, PURPLE_VIOLET, 2, 1);
        Trades.addWandererTrade(false, 16, PINK_VIOLET, 2, 1);
        Trades.addWandererTrade(false, 16, RED_VIOLET, 2, 1);
        Trades.addWandererTrade(false, 16, WHITE_VIOLET, 2, 1);

        Trades.addWandererTrade(false, 12, SMALL_POLYPORE, 3, 1);
        Trades.addWandererTrade(false, 8, BIG_POLYPORE, 1, 1);

        //Trades.addWandererTrade(true, 1, SCROLL_OF_SECRETS_NEST_BOX, 1, 8, WOODPECKER_CREST_FEATHER, 10);

        RecipeSerializers.initialize();
    }

    private static void addCompostable(float chance, ItemLike item) {
        ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
    }

    public static final class Trades {
        public static final List<VillagerTrades.ItemListing> COMMON_WANDERER_TRADES = new ArrayList<>();
        public static final List<VillagerTrades.ItemListing> RARE_WANDERER_TRADES = new ArrayList<>();

        public static void addWandererTrade(boolean rare, int maxUses, ItemLike itemToSell, int amountToSell, int emeraldCost) {
            addWandererTrade(rare, maxUses, itemToSell, amountToSell, emeraldCost, null, 0);
        }

        public static void addWandererTrade(boolean rare, int maxUses, ItemLike itemToSell, int amountToSell, int emeraldCost, ItemLike bonusItemToBuy, int bonusItemToBuyCost) {
            addWandererTrade(rare, maxUses, itemToSell, amountToSell, Items.EMERALD, emeraldCost, bonusItemToBuy, bonusItemToBuyCost);
        }

        public static void addWandererTrade(boolean rare, int maxUses, ItemLike itemToSell, int amountToSell, ItemLike firstItemToBuy, int firstItemToBuyCost, ItemLike secondItemToBuy, int secondItemToBuyCost) {
            List<VillagerTrades.ItemListing> listToAddTo = rare ? RARE_WANDERER_TRADES : COMMON_WANDERER_TRADES;

            listToAddTo.add((entity, random) -> new MerchantOffer(new net.minecraft.world.item.trading.ItemCost(firstItemToBuy, firstItemToBuyCost), java.util.Optional.of(new net.minecraft.world.item.trading.ItemCost(secondItemToBuy, secondItemToBuyCost)), new ItemStack(itemToSell, amountToSell), maxUses, 1, 1.0F));
        }

        // Each loader adds these lists to the wandering trader (Fabric: TradeOfferHelper level 1 / 2, Forge: WandererTradesEvent).
    }

    public static final class RecipeSerializers {
        public static final RecipeSerializer<ShapedSecretRecipe> SECRET_SHAPED = registerRecipeSerializer("crafting_shaped_secret", new ShapedSecretRecipe.Serializer());
        public static final RecipeSerializer<ShapelessSecretRecipe> SECRET_SHAPELESS = registerRecipeSerializer("crafting_shapeless_secret", new ShapelessSecretRecipe.Serializer());

        private static <S extends RecipeSerializer<T>, T extends Recipe<?>> S registerRecipeSerializer(String name, S serializer) {
            return WondrousWildsRegistration.add(Registries.RECIPE_SERIALIZER, name, serializer);
        }

        public static void initialize() {}
    }
}
