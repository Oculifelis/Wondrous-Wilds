package com.ineffa.wondrouswilds.advancement.criteria;

import com.ineffa.wondrouswilds.entities.FireflyEntity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
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

    public record Conditions(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> firefly) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<Conditions> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Conditions::player),
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("firefly").forGetter(Conditions::firefly)
        ).apply(instance, Conditions::new));

        public static Conditions any() {
            return new Conditions(Optional.empty(), Optional.empty());
        }

        public static Conditions predicate(ContextAwarePredicate fireflyPredicate) {
            return new Conditions(Optional.empty(), Optional.of(fireflyPredicate));
        }

        public boolean matches(LootContext context) {
            return this.firefly.isEmpty() || this.firefly.get().matches(context);
        }
    }
}
