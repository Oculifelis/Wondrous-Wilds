package com.ineffa.wondrouswilds.client.rendering.entity;

import com.geckolib.loading.math.MolangQueries;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import com.ineffa.wondrouswilds.WondrousWilds;
import com.ineffa.wondrouswilds.entities.FireflyEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class FireflyModel extends GeoModel<FireflyEntity> {
    private static final Identifier TEXTURE = WondrousWilds.id("textures/entity/firefly/firefly.png");
    private static final Identifier MODEL = WondrousWilds.id("firefly");
    private static final Identifier ANIMATION = WondrousWilds.id("firefly");

    static {
        // Molang actor variables are global, so other entities keep the stock limb swing values.
        MolangQueries.<Object>setActorVariable(MolangQueries.LIMB_SWING, actor -> {
            if (actor.animatable() instanceof FireflyEntity firefly)
                return firefly.walkAnimation.position(actor.partialTick()) * 1.25D;
            return actor.animatable() instanceof LivingEntity living ? living.walkAnimation.position(actor.partialTick()) : 0.0D;
        });
        MolangQueries.<Object>setActorVariable(MolangQueries.LIMB_SWING_AMOUNT, actor -> {
            if (actor.animatable() instanceof FireflyEntity firefly) {
                float amount = firefly.walkAnimation.speed(actor.partialTick());
                return amount + (0.9F * Mth.clamp(amount * 10.0F, 0.0F, 1.0F));
            }
            return actor.animatable() instanceof LivingEntity living ? living.walkAnimation.speed(actor.partialTick()) : 0.0D;
        });
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public Identifier getAnimationResource(FireflyEntity entity) {
        return ANIMATION;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURE;
    }
}
