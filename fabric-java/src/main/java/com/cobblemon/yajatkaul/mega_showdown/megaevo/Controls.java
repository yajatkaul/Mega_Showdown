package com.cobblemon.yajatkaul.mega_showdown.megaevo;

import com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Controls {
    public static final String KEY_CATEGORY = "key.categories.mega_showdown";
    public static final String KEY_MEGA = "key.mega_showdown.mega_evo";

    public static KeyBinding megaKey;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(megaKey.wasPressed()) {
                EvoPacket.send();
            }
        });
    }

    public static void register(){
        megaKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_MEGA,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                KEY_CATEGORY
        ));

        registerKeyInputs();
    }
}
