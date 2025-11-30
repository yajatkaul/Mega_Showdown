package com.github.yajatkaul.mega_showdown;

import com.github.yajatkaul.mega_showdown.client.ClientEventRegister;
import com.github.yajatkaul.mega_showdown.networking.client.MegaShowdownNetworkHandlerClient;
import com.github.yajatkaul.mega_showdown.render.accessories.AccessoriesRegisterRenderer;

public class MegaShowdownClient {
    public static void init() {
        AccessoriesRegisterRenderer.register();
        MegaShowdownNetworkHandlerClient.register();

        ClientEventRegister.register();
    }
}
