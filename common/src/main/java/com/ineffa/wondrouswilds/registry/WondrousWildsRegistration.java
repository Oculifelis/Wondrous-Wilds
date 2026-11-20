package com.ineffa.wondrouswilds.registry;

import com.ineffa.wondrouswilds.WondrousWilds;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Every registry object of the mod, in creation order. The common registry classes create their objects as plain
 * static fields and list them here; each loader then hands the list to its own registration system.
 */
public final class WondrousWildsRegistration {

    public record Entry(ResourceKey<? extends Registry<?>> registry, Identifier id, Object value) {}

    private static final List<Entry> ENTRIES = new ArrayList<>();

    private WondrousWildsRegistration() {}

    public static <T, V extends T> V add(ResourceKey<? extends Registry<T>> registry, String name, V value) {
        ENTRIES.add(new Entry(registry, WondrousWilds.id(name), value));
        return value;
    }

    /** The registry path an object was added under. */
    public static String nameOf(Object value) {
        for (Entry e : ENTRIES) if (e.value() == value) return e.id().getPath();
        throw new IllegalArgumentException("Not registered: " + value);
    }

    public static List<Entry> entries() {
        return Collections.unmodifiableList(ENTRIES);
    }
}
