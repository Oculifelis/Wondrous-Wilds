"""Writes the worldgen JSON (configured + placed features, biome modifiers) from the values in the original
WondrousWildsFeatures / WondrousWilds.upgradeBirchForests (1.19.2-1.1.6). Re-run instead of hand-editing.
Usage: python tools/gen_worldgen.py <resources dir> <common|forge|neoforge>"""
import json, os, sys

RES = sys.argv[1]
WHAT = sys.argv[2] if len(sys.argv) > 2 else "common"
M = "wondrouswilds"


def write(rel, obj):
    p = os.path.join(RES, rel)
    os.makedirs(os.path.dirname(p), exist_ok=True)
    with open(p, "w", encoding="utf-8", newline="\n") as f:
        json.dump(obj, f, indent=2)
        f.write("\n")


def state(name):
    return {"type": "minecraft:simple_state_provider", "state": {"Name": name}}


def heightmap(h):
    return {"type": "minecraft:heightmap", "heightmap": h}


SQUARE = {"type": "minecraft:in_square"}
BIOME = {"type": "minecraft:biome"}
MOTION = heightmap("MOTION_BLOCKING")


def rarity(n):
    return {"type": "minecraft:rarity_filter", "chance": n}


def survive(sapling="minecraft:birch_sapling"):
    return {"type": "minecraft:block_predicate_filter",
            "predicate": {"type": "minecraft:would_survive", "state": {"Name": sapling, "Properties": {"stage": "0"}}}}


def count_extra(base, chance, extra):
    # PlacementUtils.countExtra: weights (int)(1/chance) - 1 and 1
    return {"type": "minecraft:count", "count": {"type": "minecraft:weighted_list", "distribution": [
        {"data": base, "weight": int(round(1.0 / chance)) - 1}, {"data": base + extra, "weight": 1}]}}


def tree_placement(mod):
    # VegetationPlacements.treePlacement(modifier, sapling)
    return [mod, SQUARE, {"type": "minecraft:surface_water_depth_filter", "max_water_depth": 0},
            heightmap("OCEAN_FLOOR"), BIOME, survive()]


def fancy_birch(leaves, bees, decorators):
    return {"type": "minecraft:tree", "config": {
        "dirt_provider": state("minecraft:dirt"),
        "force_dirt": False,
        "ignore_vines": True,
        "minimum_size": {"type": "minecraft:two_layers_feature_size", "limit": 1, "lower_size": 0, "upper_size": 2},
        "trunk_provider": state("minecraft:birch_log"),
        "trunk_placer": {"type": f"{M}:straight_branching_trunk_placer", "base_height": 10, "height_rand_a": 10,
                         "height_rand_b": 0, "min_branches": 1 if bees else 0, "max_branches": 3,
                         "min_branch_length": 1, "max_branch_length": 1},
        "foliage_provider": state(leaves),
        "foliage_placer": {"type": f"{M}:fancy_birch_foliage_placer", "radius": 3, "offset": 0},
        "decorators": [{"type": f"{M}:{d}"} for d in decorators]}}


def common():
    cf, pf = f"data/{M}/worldgen/configured_feature/", f"data/{M}/worldgen/placed_feature/"
    for colour, leaves in [("", "minecraft:birch_leaves"), ("yellow_", f"{M}:yellow_birch_leaves"),
                           ("orange_", f"{M}:orange_birch_leaves"), ("red_", f"{M}:red_birch_leaves")]:
        for suffix, bees, decos in [("", False, ["polypores", "cobwebs"]),
                                    # .decorators(List.of(TREE_HOLLOW)) replaces the list: tree hollow only
                                    ("_with_woodpeckers", False, ["tree_hollow"]),
                                    ("_with_bees", True, ["hanging_bee_nest", "polypores", "cobwebs"])]:
            n = f"{colour}fancy_birch{suffix}"
            write(cf + n + ".json", fancy_birch(leaves, bees, decos))
            write(pf + n + ".json", {"feature": f"{M}:{n}", "placement": [survive()]})
    # TreeFeatures.superBirch: createStraightBlobTree(BIRCH_LOG, BIRCH_LEAVES, 5, 2, 6, 2).ignoreVines(), no decorators
    write(cf + "tall_birch.json", {"type": "minecraft:tree", "config": {
        "dirt_provider": state("minecraft:dirt"), "force_dirt": False, "ignore_vines": True,
        "minimum_size": {"type": "minecraft:two_layers_feature_size", "limit": 1, "lower_size": 0, "upper_size": 1},
        "trunk_provider": state("minecraft:birch_log"),
        "trunk_placer": {"type": "minecraft:straight_trunk_placer", "base_height": 5, "height_rand_a": 2, "height_rand_b": 6},
        "foliage_provider": state("minecraft:birch_leaves"),
        "foliage_placer": {"type": "minecraft:blob_foliage_placer", "radius": 2, "offset": 0, "height": 3},
        "decorators": []}})
    write(pf + "tall_birch.json", {"feature": f"{M}:tall_birch", "placement": [survive()]})

    write(cf + "birch_forest_trees.json", {"type": "minecraft:random_selector", "config": {"features": [
        {"feature": f"{M}:fancy_birch", "chance": 0.85},
        {"feature": f"{M}:fancy_birch_with_woodpeckers", "chance": 0.1},
        {"feature": f"{M}:fancy_birch_with_bees", "chance": 0.075}], "default": "minecraft:birch_checked"}})
    write(pf + "birch_forest_trees.json", {"feature": f"{M}:birch_forest_trees",
                                           "placement": tree_placement(count_extra(5, 0.1, 1))})

    feats = []
    for suffix, ch in [("", 0.5), ("_with_woodpeckers", 0.04), ("_with_bees", 0.03)]:
        for colour in ["", "yellow_", "orange_", "red_"]:
            feats.append({"feature": f"{M}:{colour}fancy_birch{suffix}", "chance": ch})
    write(cf + "old_growth_birch_forest_trees.json", {"type": "minecraft:random_selector",
                                                     "config": {"features": feats, "default": f"{M}:tall_birch"}})
    write(pf + "old_growth_birch_forest_trees.json", {"feature": f"{M}:old_growth_birch_forest_trees",
                                                      "placement": tree_placement(count_extra(10, 0.1, 1))})

    write(cf + "fallen_birch_log.json", {"type": f"{M}:fallen_log", "config": {
        "log_provider": state(f"{M}:hollow_dead_birch_log"), "min_length": 3, "max_length": 8}})
    write(pf + "fallen_birch_log.json", {"feature": f"{M}:fallen_birch_log", "placement": tree_placement(rarity(12))})

    # The original registered this one in the minecraft namespace; here it is under the mod's namespace.
    write(cf + "lily_of_the_valley_patch.json", {"type": "minecraft:flower", "config": {
        "tries": 64, "xz_spread": 6, "y_spread": 2, "feature": {"feature": {"type": "minecraft:simple_block", "config": {
            "to_place": state("minecraft:lily_of_the_valley")}}, "placement": []}}})
    write(pf + "lily_of_the_valley_patch.json", {"feature": f"{M}:lily_of_the_valley_patch",
                                                 "placement": [rarity(10), SQUARE, MOTION, BIOME]})

    write(pf + "birch_forest_tall_flowers.json", {"feature": "minecraft:forest_flowers", "placement": [
        rarity(3), SQUARE, MOTION,
        {"type": "minecraft:count", "count": {"type": "minecraft:clamped", "value": {
            "min_inclusive": 0, "max_inclusive": 1,
            "source": {"type": "minecraft:uniform", "value": {"min_inclusive": -3, "max_inclusive": 1}}}}},
        BIOME]})

    for c in ["purple", "pink", "red", "white"]:
        write(cf + f"{c}_violets.json", {"type": f"{M}:violet_patch", "config": {"violet_provider": state(f"{M}:{c}_violet")}})
        write(pf + f"{c}_violets.json", {"feature": f"{M}:{c}_violets", "placement": [rarity(8), SQUARE, MOTION, BIOME]})

    # VegetationPlacements.worldSurfaceSquaredWithCount(6)
    write(pf + "birch_forest_grass_patch.json", {"feature": "minecraft:patch_grass", "placement": [
        {"type": "minecraft:count", "count": 6}, SQUARE, heightmap("WORLD_SURFACE_WG"), BIOME]})
    # The original registered this placed feature in the minecraft namespace; here it is under the mod's namespace.
    write(pf + "birch_forest_tall_grass_patch.json", {"feature": "minecraft:patch_tall_grass",
                                                      "placement": [rarity(20), SQUARE, MOTION, BIOME]})
    write(pf + "birch_forest_rock.json", {"feature": "minecraft:forest_rock",
                                          "placement": [rarity(5), SQUARE, MOTION, BIOME]})


# What upgradeBirchForests() does, as data the loaders read.
ALL_BIRCH = ["minecraft:birch_forest", "minecraft:old_growth_birch_forest"]
REMOVE_ALL = ["minecraft:patch_grass_forest", "minecraft:forest_flowers"]
ADD_VEG_BEFORE = ["birch_forest_grass_patch", "birch_forest_tall_flowers"]
ADD_VEG = ["fallen_birch_log", "birch_forest_tall_grass_patch",
           "purple_violets", "pink_violets", "red_violets", "white_violets", "lily_of_the_valley_patch"]


def forge_like(ns):
    # Forge/NeoForge run every remove modifier before any add modifier. Each biome gets one add list in the
    # order the original's Fabric phases produced: REPLACEMENTS (grass, flowers, trees), then ADDITIONS.
    d = f"data/{M}/{ns}/biome_modifier/"
    write(d + "birch_forest_remove.json", {"type": f"{ns}:remove_features", "biomes": "minecraft:birch_forest",
                                           "features": REMOVE_ALL + ["minecraft:trees_birch"], "steps": ["vegetal_decoration"]})
    write(d + "old_growth_birch_forest_remove.json", {"type": f"{ns}:remove_features",
                                                      "biomes": "minecraft:old_growth_birch_forest",
                                                      "features": REMOVE_ALL + ["minecraft:birch_tall"],
                                                      "steps": ["vegetal_decoration"]})
    for biome, trees in [("birch_forest", "birch_forest_trees"), ("old_growth_birch_forest", "old_growth_birch_forest_trees")]:
        write(d + f"{biome}_vegetation.json", {"type": f"{ns}:add_features", "biomes": f"minecraft:{biome}",
                                               "features": [f"{M}:{n}" for n in ADD_VEG_BEFORE + [trees] + ADD_VEG],
                                               "step": "vegetal_decoration"})
    write(d + "birch_forest_rock.json", {"type": f"{ns}:add_features", "biomes": ALL_BIRCH,
                                         "features": f"{M}:birch_forest_rock", "step": "local_modifications"})
    write(d + "birch_forest_foxes.json", {"type": f"{ns}:add_spawns", "biomes": ALL_BIRCH,
                                          "spawners": {"type": "minecraft:fox", "weight": 6, "count": {"type": "minecraft:uniform", "max_inclusive": 4, "min_inclusive": 2}}})
    # Grass colour 12232267 on old growth birch forest: a biome modifier type registered by the mod.
    write(d + "old_growth_birch_forest_grass_color.json", {"type": f"{M}:grass_color",
                                                           "biomes": "minecraft:old_growth_birch_forest", "color": 12232267})


if WHAT == "common":
    common()
else:
    forge_like(WHAT)
