package com.ineffa.wondrouswilds.entities.ai;

import com.ineffa.wondrouswilds.entities.WoodpeckerEntity;
import net.minecraft.world.entity.ai.control.LookControl;

public class WoodpeckerLookControl extends LookControl {

    private final WoodpeckerEntity woodpecker;

    public WoodpeckerLookControl(WoodpeckerEntity entity) {
        super(entity);

        this.woodpecker = entity;
    }

    @Override
    protected boolean resetXRotOnTick() {
        return !this.woodpecker.isPecking();
    }
}
