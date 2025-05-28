package com.cobblemon.yajatkaul.mega_showdown.megaevo;

import com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.UltraPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Controls {
    public static final String KEY_CATEGORY = "key.categories.mega_showdown";
    public static final String KEY_MEGA = "key.mega_showdown.mega_evo";
    public static final String KEY_ULTRA = "key.mega_showdown.ultra_key";

    public static KeyBinding megaKey;
    public static KeyBinding ultraKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (megaKey.wasPressed()) {
                EvoPacket.send();
            } else if (ultraKey.wasPressed()) {
                UltraPacket.send();
            }
        });
    }

    public static void register() {
        megaKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_MEGA,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                KEY_CATEGORY
        ));

        ultraKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_ULTRA,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                KEY_CATEGORY
        ));

        registerKeyInputs();
    }
}
