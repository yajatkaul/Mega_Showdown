package com.github.yajatkaul.mega_showdown.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ShowdownItem(
        String msd_id,
        String showdown_item_id
) {
    public static final Codec<ShowdownItem> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("msd_id").forGetter(ShowdownItem::msd_id),
            Codec.STRING.fieldOf("showdown_item_id").forGetter(ShowdownItem::showdown_item_id)
    ).apply(instance, ShowdownItem::new));
}