package com.ineffa.wondrouswilds.advancement.criteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;

public class GaveWoodpeckerItemCriterion extends SimpleCriterionTrigger<GaveWoodpeckerItemCriterion.Conditions> {

    @Override
    public Codec<Conditions> codec() {
        return Conditions.CODEC;
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, conditions -> conditions.matches(stack));
    }

    public record Conditions(Optional<Holder<LootItemCondition>> player, Optional<ItemPredicate> item) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                LootItemCondition.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
                ItemPredicate.CODEC.optionalFieldOf("item").forGetter(Conditions::item)
        ).apply(instance, Conditions::new));

        public static Conditions any() {
            return new Conditions(Optional.empty(), Optional.empty());
        }

        public static Conditions predicate(ItemPredicate predicate) {
            return new Conditions(Optional.empty(), Optional.of(predicate));
        }

        public static Conditions item(ItemLike item) {
            return predicate(ItemPredicate.Builder.item().of(net.minecraft.core.registries.BuiltInRegistries.ITEM, item).build());
        }

        public boolean matches(ItemStack stack) {
            return this.item.isEmpty() || this.item.get().test(stack);
        }
    }
}
