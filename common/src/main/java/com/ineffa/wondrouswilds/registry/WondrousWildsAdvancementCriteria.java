package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.advancement.criteria.FireflyLandedOnHeadCriterion;
import com.ineffa.wondrouswilds.advancement.criteria.GaveWoodpeckerItemCriterion;
import net.minecraft.advancements.CriteriaTriggers;

public class WondrousWildsAdvancementCriteria {

    public static final FireflyLandedOnHeadCriterion FIREFLY_LANDED_ON_HEAD = CriteriaTriggers.register(new FireflyLandedOnHeadCriterion());
    public static final GaveWoodpeckerItemCriterion GAVE_WOODPECKER_ITEM = CriteriaTriggers.register(new GaveWoodpeckerItemCriterion());

    public static void initialize() {}
}
