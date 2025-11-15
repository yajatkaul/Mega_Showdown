package com.github.yajatkaul.mega_showdown.cobblemon.battle.button

import com.cobblemon.mod.common.api.gui.blitk
import com.cobblemon.mod.common.client.battle.SingleActionRequest
import com.cobblemon.mod.common.util.cobblemonResource
import net.minecraft.client.gui.GuiGraphics

class BattleShiftButton(val x: Float, val y: Float, val request: SingleActionRequest) {
    companion object {
        const val WIDTH = 72
        const val HEIGHT = 34
        const val SCALE = 0.5F
        val baseTexture = cobblemonResource("textures/gui/battle/triple_battle_shift.png")
    }

    val enabled: Boolean
        get() = request.activePokemon.getFormat().battleType.slotsPerActor == 3
                && (request.activePokemon.getPNX()[2] == 'a' || request.activePokemon.getPNX()[2] == 'c')

    fun render(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        if (!enabled)
            return
        blitk(
            matrixStack = context.pose(),
            texture = baseTexture,
            x = x * 6,
            y = y * 2,
            height = HEIGHT,
            width = WIDTH,
            vOffset = if (isHovered(mouseX.toDouble(), mouseY.toDouble())) HEIGHT else 0,
            textureHeight = HEIGHT * 2,
            scale = SCALE
        )

    }

    fun isHovered(mouseX: Double, mouseY: Double): Boolean {
        val scaledX = x * 3
        val scaledY = y
        val scaledWidth = WIDTH * SCALE
        val scaledHeight = HEIGHT * SCALE

        return mouseX.toFloat() in (scaledX..(scaledX + scaledWidth)) &&
                mouseY.toFloat() in (scaledY..(scaledY + scaledHeight))
    }
}
