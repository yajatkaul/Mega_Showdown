package com.github.yajatkaul.mega_showdown.codec.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record PerspectivesCodec(
        ResourceLocation headLoc,
        ResourceLocation guiLoc,
        ResourceLocation groundLoc,
        ResourceLocation fixedLoc
) {
    public static final Codec<PerspectivesCodec> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("headLoc").forGetter(PerspectivesCodec::headLoc),
            ResourceLocation.CODEC.fieldOf("guiLoc").forGetter(PerspectivesCodec::guiLoc),
            ResourceLocation.CODEC.fieldOf("groundLoc").forGetter(PerspectivesCodec::groundLoc),
            ResourceLocation.CODEC.fieldOf("fixedLoc").forGetter(PerspectivesCodec::fixedLoc)
    ).apply(instance, PerspectivesCodec::new));
}
