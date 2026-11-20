package com.ineffa.wondrouswilds.fabric;

import com.ineffa.wondrouswilds.config.WondrousWildsConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;

public class WondrousWildsModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(WondrousWildsConfig.class, parent).get();
    }
}
