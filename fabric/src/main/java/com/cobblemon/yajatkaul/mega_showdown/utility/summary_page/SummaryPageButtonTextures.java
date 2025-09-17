package com.cobblemon.yajatkaul.mega_showdown.utility.summary_page;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.util.Identifier;

public interface SummaryPageButtonTextures {
    Identifier NEXT_PAGE = Identifier.of(MegaShowdown.MOD_ID, "textures/gui/summary/next_feature_page.png");
    Identifier NEXT_PAGE_HOVER = Identifier.of(MegaShowdown.MOD_ID, "textures/gui/summary/next_feature_page_hover.png");
    Identifier PREV_PAGE = Identifier.of(MegaShowdown.MOD_ID, "textures/gui/summary/prev_feature_page.png");
    Identifier PREV_PAGE_HOVER = Identifier.of(MegaShowdown.MOD_ID, "textures/gui/summary/prev_feature_page_hover.png");

    int DIMENSIONS = 6;
}