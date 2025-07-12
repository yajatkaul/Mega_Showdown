package com.cobblemon.yajatkaul.mega_showdown.formChangeLogic;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.jarjar.nio.util.Lazy;
import org.lwjgl.glfw.GLFW;

public class Controls {
    public static final Lazy<KeyMapping> MEGA_ITEM_KEY = Lazy.of(() -> new KeyMapping(
            "key.mega_showdown.mega_evo", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_K, // Default key is P
            "key.categories.mega_showdown" // Mapping will be in the misc category
    ));

    public static final Lazy<KeyMapping> ULTRA_KEY = Lazy.of(() -> new KeyMapping(
            "key.mega_showdown.ultra_key", // Will be localized using this translation key
            InputConstants.Type.KEYSYM, // Default mapping is on the keyboard
            GLFW.GLFW_KEY_L, // Default key is P
            "key.categories.mega_showdown" // Mapping will be in the misc category
    ));
}
