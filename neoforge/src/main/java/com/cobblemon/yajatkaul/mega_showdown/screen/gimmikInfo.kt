package com.cobblemon.yajatkaul.mega_showdown.screen

import com.cobblemon.mod.common.api.gui.blitk
import com.cobblemon.mod.common.client.render.drawScaledText
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceLocation

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
