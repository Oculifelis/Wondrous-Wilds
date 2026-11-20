package com.ineffa.wondrouswilds;

import com.ineffa.wondrouswilds.config.WondrousWildsConfig;
import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.ineffa.wondrouswilds.mixin.MobEntityAccessor;
import com.ineffa.wondrouswilds.registry.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Fox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Loader-neutral entry point. Each loader calls {@link #init()} before it registers the entries collected in WondrousWildsRegistration. */
public class WondrousWilds {

	public static final String MOD_ID = "wondrouswilds";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static WondrousWildsConfig config;

	private static boolean initialized = false;

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static void init() {
		if (initialized) return;
		initialized = true;

		LOGGER.info("Wondrous Wilds initializing!");

		AutoConfig.register(WondrousWildsConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(WondrousWildsConfig.class).getConfig();

		WondrousWildsSounds.initialize();

		WondrousWildsEntities.initialize();
		WondrousWildsBlocks.initialize();
		WondrousWildsItems.initialize();

		WondrousWildsFeatures.initialize();

		WondrousWildsScreenHandlers.initialize();

		WondrousWildsAdvancementCriteria.initialize();

		LOGGER.info("Wondrous Wilds initialized and ready!");
	}

	/** Called by each loader whenever an entity joins a server level. */
	public static void hookEntityCreation(Entity entityBeingCreated) {
		if (entityBeingCreated instanceof Fox fox)
			((MobEntityAccessor) fox).getTargetSelector().addGoal(7, new NearestAttackableTargetGoal<>(fox, WoodpeckerEntity.class, 20, true, true, entity -> !((WoodpeckerEntity) entity).isInFlight()));
	}
}
