package com.github.yajatkaul.mega_showdown.item.custom.mega;

import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipItem;

public class MegaStone extends ToolTipItem {
    private final MegaGimmick megaGimmick;

    public MegaStone(Properties properties, MegaGimmick megaGimmick) {
        super(properties);
        this.megaGimmick = megaGimmick;
    }

    public MegaGimmick getMegaProps() {
        return megaGimmick;
    }
}
