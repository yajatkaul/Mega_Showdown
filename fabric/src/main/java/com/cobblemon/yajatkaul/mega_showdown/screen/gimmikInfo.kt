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
