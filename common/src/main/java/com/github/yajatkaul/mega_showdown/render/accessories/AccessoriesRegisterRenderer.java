package com.github.yajatkaul.mega_showdown.render.accessories;

import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;

public class AccessoriesRegisterRenderer {
    public static void register() {
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.MEGA_BRACELET.get(), LowOffHandRenderer::new);

        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.LISIA_MEGA_TIARA.get(), HeadRenderer::new);

        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.Z_RING.get(), HighOffHandRenderer::new);

        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.TERA_ORB.get(), BeltRenderer::new);

        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.DYNAMAX_BAND.get(), HandRenderer::new);

        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.OMNI_RING.get(), HandRenderer::new);

        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.LIKOS_PENDANT.get(), ChestRenderer::new);
    }
}
