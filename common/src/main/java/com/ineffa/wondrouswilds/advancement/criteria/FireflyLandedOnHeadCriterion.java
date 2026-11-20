package com.ineffa.wondrouswilds.advancement.criteria;

import com.ineffa.wondrouswilds.entities.FireflyEntity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.Optional;

public class FireflyLandedOnHeadCriterion extends SimpleCriterionTrigger<FireflyLandedOnHeadCriterion.Conditions> {

    @Override
    public Codec<Conditions> codec() {
        return Conditions.CODEC;
    }

    public void trigger(ServerPlayer player, FireflyEntity firefly) {
        LootContext lootContext = EntityPredicate.createContext(player, firefly);
        this.trigger(player, conditions -> conditions.matches(lootContext));
    }

    public record Conditions(Optional<Holder<LootItemCondition>> player, Optional<Holder<LootItemCondition>> firefly) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                LootItemCondition.CODEC.optionalFieldOf("player").forGetter(Conditions::player),
                LootItemCondition.CODEC.optionalFieldOf("firefly").forGetter(Conditions::firefly)
        ).apply(instance, Conditions::new));

        public static Conditions any() {
            return new Conditions(Optional.empty(), Optional.empty());
        }

        public static Conditions predicate(Holder<LootItemCondition> fireflyPredicate) {
            return new Conditions(Optional.empty(), Optional.of(fireflyPredicate));
        }

        public boolean matches(LootContext context) {
            return this.firefly.isEmpty() || this.firefly.get().value().test(context);
        }
    }
}
