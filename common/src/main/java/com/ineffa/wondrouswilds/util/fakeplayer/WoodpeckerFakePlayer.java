package com.ineffa.wondrouswilds.util.fakeplayer;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class WoodpeckerFakePlayer extends FakePlayerEntity {

    private final WoodpeckerEntity woodpecker;
    private final WoodpeckerFakeAbilities woodpeckerAbilities;

    public WoodpeckerFakePlayer(WoodpeckerEntity woodpecker) {
        super(woodpecker.level(), woodpecker.blockPosition(), woodpecker.getYRot(), new GameProfile(woodpecker.getUUID(), woodpecker.getName().getString()));

        this.woodpecker = woodpecker;
        this.woodpeckerAbilities = new WoodpeckerFakeAbilities(woodpecker);

        this.copyPosition(woodpecker);

        this.setPermanentlyInvulnerable(woodpecker.isInvulnerable());
    }

    @Override
    public Abilities getAbilities() {
        return this.woodpeckerAbilities;
    }

    @Override
    public boolean canEat(boolean ignoreHunger) {
        return true;
    }

    @Override
    public void setItemInHand(InteractionHand hand, ItemStack stack) {
        if (hand == InteractionHand.MAIN_HAND) this.woodpecker.setItemSlot(EquipmentSlot.MAINHAND, stack);

        else if (hand == InteractionHand.OFF_HAND) {
            if (this.woodpecker.level() instanceof ServerLevel serverLevel) this.woodpecker.spawnAtLocation(serverLevel, stack);
        }

        else super.setItemInHand(hand, stack);
    }

    @Override
    public ItemStack getItemInHand(InteractionHand hand) {
        return this.woodpecker.getItemInHand(hand);
    }

    @Nullable
    @Override
    public ItemEntity spawnAtLocation(ServerLevel level, ItemStack stack) {
        return this.woodpecker.spawnAtLocation(level, stack);
    }

    @Nullable
    @Override
    public ItemEntity spawnAtLocation(ServerLevel level, ItemStack stack, float yOffset) {
        return this.woodpecker.spawnAtLocation(level, stack, yOffset);
    }

    @Nullable
    @Override
    public ItemEntity drop(ItemStack stack, boolean throwRandomly, net.minecraft.util.Prediction prediction) {
        if (stack.isEmpty()) return null;

        if (this.woodpecker.level().isClientSide()) this.woodpecker.swing(InteractionHand.MAIN_HAND, net.minecraft.world.item.component.SwingAnimation.DEFAULT, false);

        ItemEntity itemEntity = new ItemEntity(this.woodpecker.level(), this.woodpecker.getX(), this.woodpecker.getEyeY() - 0.3D, this.woodpecker.getZ(), stack);
        itemEntity.setPickUpDelay(40);

        // 26.3 drops no longer say whether the thrower is kept, so it always is, as vanilla players do.
        itemEntity.setThrower(this.woodpecker);

        if (throwRandomly) {
            float f = this.woodpecker.getRandom().nextFloat() * 0.5F;
            float g = this.woodpecker.getRandom().nextFloat() * ((float) Math.PI * 2.0F);
            itemEntity.setDeltaMovement(-Mth.sin(g) * f, 0.2F, Mth.cos(g) * f);
        }
        else {
            float g = Mth.sin(this.woodpecker.getXRot() * ((float) Math.PI / 180.0F));
            float h = Mth.cos(this.woodpecker.getXRot() * ((float) Math.PI / 180.0F));
            float i = Mth.sin(this.woodpecker.getYRot() * ((float) Math.PI / 180.0F));
            float j = Mth.cos(this.woodpecker.getYRot() * ((float) Math.PI / 180.0F));
            float k = this.woodpecker.getRandom().nextFloat() * ((float) Math.PI * 2.0F);
            float l = 0.02F * this.woodpecker.getRandom().nextFloat();
            float f = 0.3F;
            itemEntity.setDeltaMovement((double) (-i * h * f) + Math.cos(k) * (double) l, -g * f + 0.1F + (this.woodpecker.getRandom().nextFloat() - this.woodpecker.getRandom().nextFloat()) * 0.1F, (double) (j * h * f) + Math.sin(k) * (double) l);
        }

        this.woodpecker.level().addFreshEntity(itemEntity);
        return itemEntity;
    }

    @Override
    public boolean addItem(ItemStack stack) {
        if (this.woodpecker.getMainHandItem().isEmpty()) {
            this.setItemInHand(InteractionHand.MAIN_HAND, stack);
            return true;
        }

        return false;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        this.woodpecker.setItemSlot(slot, stack);
    }

    @Override
    public void onEquipItem(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack) {
        this.woodpecker.onEquipItem(slot, oldStack, newStack);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return this.woodpecker.getItemBySlot(slot);
    }

    @Override
    public ItemStack getOffhandItem() {
        return this.woodpecker.getOffhandItem();
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return this.woodpecker.getPickResult();
    }
}
