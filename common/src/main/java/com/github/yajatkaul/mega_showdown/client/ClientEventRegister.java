package com.github.yajatkaul.mega_showdown.client;

public class ClientEventRegister {
    public static void register() {
        CobbleClientEvents.register();
        ClientMinecraftRegister.register();
    }
}
