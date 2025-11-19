package com.github.yajatkaul.mega_showdown.item.custom.gimmick;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipItem;
import net.minecraft.resources.ResourceLocation;

public class MegaStone extends ToolTipItem {
    private final ResourceLocation mega_stone;

    public MegaStone(Properties properties, String mega_stone) {
        super(properties);
        this.mega_stone = ResourceLocation.tryParse(MegaShowdown.MOD_ID + ":" + mega_stone);
    }

    public ResourceLocation getMegaStone() {
        return mega_stone;
    }
}
