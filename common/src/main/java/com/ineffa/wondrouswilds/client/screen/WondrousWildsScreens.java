package com.ineffa.wondrouswilds.client.screen;

import com.ineffa.wondrouswilds.client.screen.ingame.NestBoxScreen;
import com.ineffa.wondrouswilds.registry.WondrousWildsScreenHandlers;
import net.minecraft.client.gui.screens.MenuScreens;

public final class WondrousWildsScreens {

    public static void register() {
        MenuScreens.register(WondrousWildsScreenHandlers.NEST_BOX, NestBoxScreen::new);
    }
}
