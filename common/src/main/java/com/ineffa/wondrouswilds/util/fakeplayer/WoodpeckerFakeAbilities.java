package com.ineffa.wondrouswilds.util.fakeplayer;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Abilities;

public class WoodpeckerFakeAbilities extends Abilities {

    public WoodpeckerFakeAbilities(WoodpeckerEntity woodpecker) {
        this.invulnerable = woodpecker.isInvulnerable();

        this.flying = woodpecker.isInFlight();
        this.mayfly = true;

        this.instabuild = false;
        this.mayBuild = true;
    }

    @Override
    public void addSaveData(CompoundTag nbt) {}

    @Override
    public void loadSaveData(CompoundTag nbt) {}
}
