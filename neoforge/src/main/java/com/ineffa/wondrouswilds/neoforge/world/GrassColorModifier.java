package com.ineffa.wondrouswilds.neoforge.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

/** Sets the grass colour of the listed biomes (the old growth birch forest tint of the original). */
public record GrassColorModifier(HolderSet<Biome> biomes, int color) implements BiomeModifier {

    public static final MapCodec<GrassColorModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(GrassColorModifier::biomes),
            Codec.INT.fieldOf("color").forGetter(GrassColorModifier::color)
    ).apply(instance, GrassColorModifier::new));

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING && this.biomes.contains(biome))
            builder.getSpecialEffects().grassColorOverride(this.color);
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
