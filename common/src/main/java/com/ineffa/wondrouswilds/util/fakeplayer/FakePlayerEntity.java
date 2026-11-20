package com.ineffa.wondrouswilds.util.fakeplayer;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class FakePlayerEntity extends Player {

    public FakePlayerEntity(Level world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Override
    public void tick() {}

    @Override
    public void rideTick() {}

    @Override
    protected void tickEffects() {}

    @Override
    protected void checkAutoSpinAttack(AABB a, AABB b) {}

    @Override
    protected void serverAiStep() {}

    @Override
    protected void processPortalCooldown() {}


    @Override
    protected void updateUsingItem(ItemStack stack) {}

    @Override
    protected void onBelowWorld() {}

    @Override
    protected void updateSwingTime() {}

    @Override
    protected void pushEntities() {}

    @Override
    public void aiStep() {}

    @Override
    public void baseTick() {}

    @Override
    public void checkBelowWorld() {}

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
