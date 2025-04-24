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


fun gimmikText(
    context: GuiGraphics,
    font: ResourceLocation,
    x: Number,
    y: Number,
    name: MutableComponent,
    centered: Boolean,
    shadow: Boolean
) {
    drawScaledText(
        context = context,
        font = font,
        text = name,
        x = x,
        y = y,
        centered = centered,
        shadow = shadow
    )
}

fun gimmikText(
    context: GuiGraphics,
    x: Number,
    y: Number,
    name: MutableComponent,
    scale: Float,
    centered: Boolean,
) {
    drawScaledText(
        context = context,
        text = name,
        x = x,
        y = y,
        scale = scale,
        centered = centered
    )
}

fun gimmikBar(
    context: PoseStack,
    color: ResourceLocation,
    x: Number,
    y: Number,
    height: Number,
    barWidth: Number,
    red: Number,
    green: Number,
    blue: Number
) {
    blitk(
        matrixStack = context,
        texture = color,
        x = x,
        y = y,
        height = height,
        width = barWidth,
        red = red,
        green = green,
        blue = blue,
    )
}
