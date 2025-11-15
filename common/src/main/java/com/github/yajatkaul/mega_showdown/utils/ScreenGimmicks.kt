package com.github.yajatkaul.mega_showdown.utils

import com.cobblemon.mod.common.api.gui.blitk
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.resources.ResourceLocation

object ScreenGimmicks {
    fun gimmikInfo(
        matrices: PoseStack,
        texture: ResourceLocation,
        x: Number,
        y: Number,
        width: Number,
        height: Number,
        scale: Float
    ) {
        blitk(
            matrixStack = matrices,
            texture = texture,
            x = x,
            y = y,
            width = width,
            height = height,
            scale = scale
        )
    }

    fun gimmikInfo(
        matrices: PoseStack,
        texture: ResourceLocation,
        x: Number,
        y: Number,
        width: Number,
        height: Number
    ) {
        blitk(
            matrixStack = matrices,
            texture = texture,
            x = x,
            y = y,
            width = width,
            height = height,
        )
    }
}