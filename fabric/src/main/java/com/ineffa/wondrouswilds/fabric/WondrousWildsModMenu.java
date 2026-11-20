package com.ineffa.wondrouswilds.fabric;

import com.ineffa.wondrouswilds.config.WondrousWildsConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfigClient;

public class WondrousWildsModMenu implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfigClient.getConfigScreen(WondrousWildsConfig.class, parent).get();
    }
}
