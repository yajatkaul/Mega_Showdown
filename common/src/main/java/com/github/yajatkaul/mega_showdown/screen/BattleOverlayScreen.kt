package com.github.yajatkaul.mega_showdown.screen

import com.cobblemon.mod.common.api.gui.blitk
import com.cobblemon.mod.common.client.CobblemonClient
import com.cobblemon.mod.common.util.cobblemonResource
import com.github.yajatkaul.mega_showdown.MegaShowdown
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics

object BattleOverlayScreen {
    val pokeball = cobblemonResource("textures/gui/battle/pokeball.png")

    fun render(context: GuiGraphics) {
        val battle = CobblemonClient.battle ?: return
        val playerUUID = Minecraft.getInstance().player?.uuid ?: return
        val side1 = if (battle.side1.actors.any { it.uuid == playerUUID }) battle.side1 else battle.side2
        val side2 = if (side1 == battle.side1) battle.side2 else battle.side1

        side2.actors.forEach { MegaShowdown.LOGGER.info(it.pokemon.size.toString()) }

        blitk(
            matrixStack = context.pose(),
            texture = pokeball,
            y = 23,
            x = 23,
            width = 16,
            height = 16
        )
    }
}