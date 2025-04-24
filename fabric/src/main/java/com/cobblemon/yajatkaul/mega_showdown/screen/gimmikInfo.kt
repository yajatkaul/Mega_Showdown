package com.cobblemon.yajatkaul.mega_showdown.screen

import com.cobblemon.mod.common.api.gui.blitk
import com.cobblemon.mod.common.client.render.drawScaledText
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.MutableText
import net.minecraft.util.Identifier

fun gimmikInfo(
    matrices: MatrixStack,
    texture: Identifier,
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
    matrices: MatrixStack,
    texture: Identifier,
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
    context: DrawContext,
    font: Identifier,
    x: Number,
    y: Number,
    name: MutableText,
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
    context: DrawContext,
    x: Number,
    y: Number,
    name: MutableText,
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
    context: MatrixStack,
    color: Identifier,
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

