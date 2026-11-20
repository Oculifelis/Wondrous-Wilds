package com.ineffa.wondrouswilds.neoforge.world;

import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.registry.WondrousWildsEntities;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

/** Adds the firefly spawn to the listed biomes when the config allows natural firefly spawns. */
public record FireflySpawnsModifier(HolderSet<Biome> biomes) implements BiomeModifier {

    public static final MapCodec<FireflySpawnsModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(FireflySpawnsModifier::biomes)
    ).apply(instance, FireflySpawnsModifier::new));

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.ADD || !this.biomes.contains(biome)) return;
        if (!WondrousWilds.config.mobSettings.firefliesSpawnNaturally) return;

        builder.getMobSpawnSettings().addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(WondrousWildsEntities.FIREFLY,
                WondrousWildsEntities.FIREFLY_SPAWN_WEIGHT, WondrousWildsEntities.FIREFLY_SPAWN_MIN, WondrousWildsEntities.FIREFLY_SPAWN_MAX));
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
