package com.github.yajatkaul.mega_showdown.render.accessories;

import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;

public class AccessoriesRegisterRenderer {
    public static void register() {
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.MEGA_BRACELET.get(), LowOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.MEGA_RED_BRACELET.get(), LowOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.MEGA_YELLOW_BRACELET.get(), LowOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.MEGA_PINK_BRACELET.get(), LowOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.MEGA_GREEN_BRACELET.get(), LowOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.MEGA_BLUE_BRACELET.get(), LowOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.MEGA_BLACK_BRACELET.get(), LowOffHandRenderer::new);

        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.MAY_BRACELET.get(), LowOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.MEGA_RING.get(), LowOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.LYSANDRE_RING.get(), LowOffHandRenderer::new);

        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.BRENDAN_MEGA_CUFF.get(), LowOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.KORRINA_GLOVE.get(), LowOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.MAXIE_GLASSES.get(), HeadRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.ARCHIE_ANCHOR.get(), ChestRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.LISIA_MEGA_TIARA.get(), HeadRenderer::new);

        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.Z_RING.get(), HighOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.Z_RING_BLACK.get(), HighOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.Z_RING_YELLOW.get(), HighOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.Z_RING_GREEN.get(), HighOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.Z_RING_BLUE.get(), HighOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.Z_RING_PINK.get(), HighOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.Z_RING_RED.get(), HighOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.OLIVIAS_Z_RING.get(), HighOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.HAPUS_Z_RING.get(), HighOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.Z_RING_POWER.get(), HighOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.OLIVIA_Z_POWER_RING.get(), HighOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.HAPU_Z_POWER_RING.get(), HighOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.ROCKET_Z_POWER_RING.get(), HighOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.GLADION_Z_POWER_RING.get(), HighOffHandRenderer::new);
        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.NANU_Z_POWER_RING.get(), HighOffHandRenderer::new);

        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.TERA_ORB.get(), BeltRenderer::new);

        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.DYNAMAX_BAND.get(), HandRenderer::new);

        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.OMNI_RING.get(), HandRenderer::new);

        AccessoriesRendererRegistry.registerRenderer(MegaShowdownItems.LIKOS_PENDANT.get(), ChestRenderer::new);
    }
}
