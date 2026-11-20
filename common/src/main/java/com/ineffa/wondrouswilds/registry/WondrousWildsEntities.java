package com.ineffa.wondrouswilds.registry;

import com.google.common.collect.ImmutableMap;
import com.ineffa.wondrouswilds.entities.FireflyEntity;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import java.util.Map;

public class WondrousWildsEntities {

    // Attributes, spawn placements (ON_GROUND, MOTION_BLOCKING) and the firefly biome spawn are added by each loader.
    public static final EntityType<FireflyEntity> FIREFLY = WondrousWildsRegistration.add(Registries.ENTITY_TYPE, "firefly",
            EntityType.Builder.of(FireflyEntity::new, MobCategory.WATER_AMBIENT).sized(0.1875F, 0.25F).build("firefly"));

    public static final EntityType<WoodpeckerEntity> WOODPECKER = WondrousWildsRegistration.add(Registries.ENTITY_TYPE, "woodpecker",
            EntityType.Builder.of(WoodpeckerEntity::new, MobCategory.CREATURE).sized(0.3125F, 0.5F).build("woodpecker"));

    /** Firefly natural spawn from the original: weight 100, groups of 3 to 6, every overworld biome. */
    public static final int FIREFLY_SPAWN_WEIGHT = 100, FIREFLY_SPAWN_MIN = 3, FIREFLY_SPAWN_MAX = 6;

    public static void initialize() {}

    public static final Map<EntityType<?>, Integer> DEFAULT_NESTER_CAPACITY_WEIGHTS = new ImmutableMap.Builder<EntityType<?>, Integer>()
            .put(WOODPECKER, 55)
            .build();
}
