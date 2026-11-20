package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.advancement.criteria.FireflyLandedOnHeadCriterion;
import com.ineffa.wondrouswilds.advancement.criteria.GaveWoodpeckerItemCriterion;
import net.minecraft.core.registries.Registries;

public class WondrousWildsAdvancementCriteria {

    public static final FireflyLandedOnHeadCriterion FIREFLY_LANDED_ON_HEAD = WondrousWildsRegistration.add(Registries.TRIGGER_TYPE, "firefly_landed_on_head", new FireflyLandedOnHeadCriterion());
    public static final GaveWoodpeckerItemCriterion GAVE_WOODPECKER_ITEM = WondrousWildsRegistration.add(Registries.TRIGGER_TYPE, "gave_woodpecker_item", new GaveWoodpeckerItemCriterion());

    public static void initialize() {}
}
