package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

public class WondrousWildsSounds {

    public static final SoundEvent WOODPECKER_CHIRP = createSoundEvent("entity.woodpecker.chirp");
    public static final SoundEvent WOODPECKER_DRUM = createSoundEvent("entity.woodpecker.drum");

    private static SoundEvent createSoundEvent(String name) {
        return WondrousWildsRegistration.add(Registries.SOUND_EVENT, name, SoundEvent.createVariableRangeEvent(WondrousWilds.id(name)));
    }

    public static void initialize() {}
}
