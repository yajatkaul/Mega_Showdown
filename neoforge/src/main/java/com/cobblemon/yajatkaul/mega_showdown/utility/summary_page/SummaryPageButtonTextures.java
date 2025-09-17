package com.cobblemon.yajatkaul.mega_showdown.utility.summary_page;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.resources.ResourceLocation;

public interface SummaryPageButtonTextures {
    ResourceLocation NEXT_PAGE = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "textures/gui/summary/next_feature_page.png");
    ResourceLocation NEXT_PAGE_HOVER = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "textures/gui/summary/next_feature_page_hover.png");
    ResourceLocation PREV_PAGE = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "textures/gui/summary/prev_feature_page.png");
    ResourceLocation PREV_PAGE_HOVER = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "textures/gui/summary/prev_feature_page_hover.png");

    int DIMENSIONS = 6;
}