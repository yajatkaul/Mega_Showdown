package com.github.yajatkaul.mega_showdown.entitiy.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class TeraCrystalAnimation {
    public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(2.0F)
            .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.SCALE,
                    new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
                    new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.0F, 1.1F, 1.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();
}
