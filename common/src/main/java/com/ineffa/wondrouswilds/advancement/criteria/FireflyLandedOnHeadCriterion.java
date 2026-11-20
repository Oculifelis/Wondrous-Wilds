package com.ineffa.wondrouswilds.advancement.criteria;

import com.google.gson.JsonObject;
import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.entities.FireflyEntity;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.loot.LootContext;

public class FireflyLandedOnHeadCriterion extends SimpleCriterionTrigger<FireflyLandedOnHeadCriterion.Conditions> {

    private static final ResourceLocation ID = new ResourceLocation(WondrousWilds.MOD_ID, "firefly_landed_on_head");

    @Override
    protected Conditions createInstance(JsonObject obj, ContextAwarePredicate playerPredicate, DeserializationContext predicateDeserializer) {
        ContextAwarePredicate fireflyPredicate = EntityPredicate.fromJson(obj, "firefly", predicateDeserializer);
        return new Conditions(playerPredicate, fireflyPredicate);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player, FireflyEntity firefly) {
        LootContext lootContext = EntityPredicate.createContext(player, firefly);
        this.trigger(player, conditions -> conditions.matches(lootContext));
    }

    public static class Conditions extends AbstractCriterionTriggerInstance {
        private final ContextAwarePredicate fireflyPredicate;

        public Conditions(ContextAwarePredicate player, ContextAwarePredicate fireflyPredicate) {
            super(ID, player);
            this.fireflyPredicate = fireflyPredicate;
        }

        public static Conditions any() {
            return new Conditions(ContextAwarePredicate.ANY, ContextAwarePredicate.ANY);
        }

        public static Conditions predicate(ContextAwarePredicate fireflyPredicate) {
            return new Conditions(ContextAwarePredicate.ANY, fireflyPredicate);
        }

        public boolean matches(LootContext context) {
            return this.fireflyPredicate.matches(context);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext predicateSerializer) {
            JsonObject jsonObject = super.serializeToJson(predicateSerializer);
            jsonObject.add("firefly", this.fireflyPredicate.toJson(predicateSerializer));
            return jsonObject;
        }
    }
}
