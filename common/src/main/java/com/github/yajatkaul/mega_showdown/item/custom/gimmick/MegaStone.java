package com.github.yajatkaul.mega_showdown.item.custom.gimmick;

import com.github.yajatkaul.mega_showdown.components.MegaShowdownDataComponents;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipItem;
import net.minecraft.resources.ResourceLocation;

public class MegaStone extends ToolTipItem {
    public MegaStone(Properties properties,
                     ResourceLocation resourceLocation
    ) {
        super(properties);
        properties.component(MegaShowdownDataComponents.REGISTRY_TYPE_COMPONENT.get(), "mega");
        properties.component(MegaShowdownDataComponents.RESOURCE_LOCATION_COMPONENT.get(), resourceLocation);
    }
}
