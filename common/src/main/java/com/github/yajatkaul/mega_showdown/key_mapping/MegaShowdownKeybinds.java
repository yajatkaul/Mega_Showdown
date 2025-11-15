package com.github.yajatkaul.mega_showdown.key_mapping;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;

public class MegaShowdownKeybinds {
    public static final KeyMapping MEGA_KEY = new KeyMapping(
            "key.mega_showdown.mega_evo",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_L,
            "key.categories.mega_showdown"
    );

    public static final KeyMapping ULTRA_KEY = new KeyMapping(
            "key.mega_showdown.ultra_key",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_K,
            "key.categories.mega_showdown"
    );

    public static void register() {
        KeyMappingRegistry.register(MEGA_KEY);
        KeyMappingRegistry.register(ULTRA_KEY);
    }
}
