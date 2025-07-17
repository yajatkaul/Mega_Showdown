package com.cobblemon.yajatkaul.mega_showdown.utility.backporting

import com.cobblemon.mod.common.api.gui.blitk
import com.cobblemon.mod.common.client.battle.SingleActionRequest
import com.cobblemon.mod.common.util.cobblemonResource
import net.minecraft.client.gui.DrawContext

class BattleShiftButton(val x: Float, val y: Float, val request: SingleActionRequest) {
    companion object {
        const val WIDTH = 72
        const val HEIGHT = 34
        const val SCALE = 0.5F
        val baseTexture = cobblemonResource("textures/gui/battle/triple_battle_shift.png")

    }

    val enabled: Boolean
        get() = request.activePokemon.getFormat().battleType.slotsPerActor == 3 && (request.activePokemon.getPNX()[2] == 'a' || request.activePokemon.getPNX()[2] == 'c')

    fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        if(!enabled)
            return
        blitk(
            matrixStack = context.matrices,
            texture = baseTexture,
            x = x * 2,
            y = y * 2,
            height = HEIGHT,
            width = WIDTH,
            vOffset = if (isHovered(mouseX.toDouble(), mouseY.toDouble())) HEIGHT else 0,
            textureHeight = HEIGHT * 2,
            scale = SCALE
        )

    }

    fun isHovered(mouseX: Double, mouseY: Double) = mouseX.toFloat() in (x..(x + (WIDTH * SCALE))) && mouseY.toFloat() in (y..(y + (HEIGHT * SCALE)))
}
