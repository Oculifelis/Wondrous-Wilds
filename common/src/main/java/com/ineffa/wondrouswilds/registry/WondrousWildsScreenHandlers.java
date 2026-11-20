package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.screen.NestBoxScreenHandler;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public final class WondrousWildsScreenHandlers {

    public static final MenuType<NestBoxScreenHandler> NEST_BOX = registerScreenHandler("nest_box", NestBoxScreenHandler::new);

    private static <T extends AbstractContainerMenu> MenuType<T> registerScreenHandler(String name, MenuType.MenuSupplier<T> factory) {
        return WondrousWildsRegistration.add(Registries.MENU, name, new MenuType<>(factory, FeatureFlags.VANILLA_SET));
    }

    public static void initialize() {}
}
