package com.ineffa.wondrouswilds.advancement.criteria;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import com.ineffa.wondrouswilds.WondrousWilds;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class GaveWoodpeckerItemCriterion extends SimpleCriterionTrigger<GaveWoodpeckerItemCriterion.Conditions> {

    private static final ResourceLocation ID = new ResourceLocation(WondrousWilds.MOD_ID, "gave_woodpecker_item");

    @Override
    protected Conditions createInstance(JsonObject obj, ContextAwarePredicate playerPredicate, DeserializationContext predicateDeserializer) {
        return new Conditions(playerPredicate, ItemPredicate.fromJson(obj.get("item")));
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, conditions -> conditions.matches(stack));
    }

    public static class Conditions extends AbstractCriterionTriggerInstance {
        private final ItemPredicate itemPredicate;

        public Conditions(ContextAwarePredicate player, ItemPredicate itemPredicate) {
            super(ID, player);
            this.itemPredicate = itemPredicate;
        }

        public static Conditions any() {
            return new Conditions(ContextAwarePredicate.ANY, ItemPredicate.ANY);
        }

        public static Conditions predicate(ItemPredicate predicate) {
            return new Conditions(ContextAwarePredicate.ANY, predicate);
        }

        public static Conditions item(ItemLike item) {
            return new Conditions(ContextAwarePredicate.ANY, new ItemPredicate(null, ImmutableSet.of(item.asItem()), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, EnchantmentPredicate.NONE, EnchantmentPredicate.NONE, null, NbtPredicate.ANY));
        }

        public boolean matches(ItemStack stack) {
            return this.itemPredicate.matches(stack);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext predicateSerializer) {
            JsonObject jsonObject = super.serializeToJson(predicateSerializer);
            jsonObject.add("item", this.itemPredicate.serializeToJson());
            return jsonObject;
        }
    }
}
