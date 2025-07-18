package com.cobblemon.yajatkaul.mega_showdown.utility.backporting

import com.cobblemon.mod.common.CobblemonSounds
import com.cobblemon.mod.common.api.gui.blitk
import com.cobblemon.mod.common.api.moves.MoveTemplate
import com.cobblemon.mod.common.api.text.bold
import com.cobblemon.mod.common.api.text.font
import com.cobblemon.mod.common.api.text.text
import com.cobblemon.mod.common.battles.InBattleGimmickMove
import com.cobblemon.mod.common.battles.InBattleMove
import com.cobblemon.mod.common.battles.MoveActionResponse
import com.cobblemon.mod.common.client.CobblemonClient
import com.cobblemon.mod.common.client.CobblemonResources
import com.cobblemon.mod.common.client.battle.ActiveClientBattlePokemon
import com.cobblemon.mod.common.client.battle.SingleActionRequest
import com.cobblemon.mod.common.client.gui.battle.BattleGUI
import com.cobblemon.mod.common.client.gui.battle.BattleOverlay
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleActionSelection
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleBackButton
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleMoveSelection
import com.cobblemon.mod.common.client.gui.drawProfilePokemon
import com.cobblemon.mod.common.client.render.drawScaledText
import com.cobblemon.mod.common.client.render.models.blockbench.FloatingState
import com.cobblemon.mod.common.pokemon.Gender
import com.cobblemon.mod.common.util.battleLang
import com.cobblemon.mod.common.util.cobblemonResource
import com.cobblemon.mod.common.util.lang
import com.cobblemon.mod.common.util.math.fromEulerXYZDegrees
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.client.sounds.SoundManager
import org.joml.Quaternionf
import org.joml.Vector3f
import kotlin.math.floor
import kotlin.math.sin

class BattleTargetSelection(
    battleGUI: BattleGUI,
    request: SingleActionRequest,
    private val move: InBattleMove,
    private val gimmickID: String?,
    gimmickMove: InBattleGimmickMove?
) : BattleActionSelection(
    battleGUI = battleGUI,
    request = request,
    x = 0,
    y = run {
        val mc = Minecraft.getInstance()
        val screenHeight = mc.window.guiScaledHeight
        if (screenHeight > 304) screenHeight / 2 - BACKGROUND_HEIGHT / 2
        else screenHeight - (BACKGROUND_HEIGHT + 78)
    },
    width = 100,
    height = 100,
    battleLang("ui.select_move")
) {
    companion object {
        const val TARGET_WIDTH = 93
        const val TARGET_HEIGHT = 33
        const val MOVE_VERTICAL_SPACING = 1F
        const val MOVE_HORIZONTAL_SPACING = 2F
        const val BACKGROUND_HEIGHT = 148

        val underlayTexture = cobblemonResource("textures/gui/battle/selection_underlay.png")
        val targetTileTexture = cobblemonResource("textures/gui/battle/target_select.png")
        val targetTileTextureDisabled = cobblemonResource("textures/gui/battle/target_select_disabled.png")
        val battleRoleUpper = cobblemonResource("textures/gui/battle/battle_info_role.png")
        val battleRoleLower = cobblemonResource("textures/gui/battle/target_select_role.png")
        val battleArrowUp = cobblemonResource("textures/gui/battle/arrow_pointer_up.png")
        val battleArrowDown = cobblemonResource("textures/gui/battle/arrow_pointer_down.png")
        val battleArrowLeft = cobblemonResource("textures/gui/battle/arrow_pointer_left.png")
        val battleArrowRight = cobblemonResource("textures/gui/battle/arrow_pointer_right.png")

        enum class ArrowDirection {
            UP,
            DOWN,
            LEFT,
            RIGHT,
        }
    }

    val targets = request.activePokemon.getAllActivePokemon()
    val targetType =
        if (gimmickID != null && gimmickMove != null && gimmickID != "terastal") gimmickMove.target else move.target
    val backButton = BattleBackButton(x + 9F, Minecraft.getInstance().window.guiScaledHeight - 22F)
    val selectableTargetList = targetType.targetList(request.activePokemon)
    val multiTargetList =
        if (selectableTargetList == null) request.activePokemon.getMultiTargetList(targetType) else null

    val baseTiles = targets.mapIndexed { index, target ->
        val isAlly = target.isAllied(request.activePokemon)
        val teamSize = request.activePokemon.getSidePokemon().count()
        val fieldPos = if (isAlly) index % teamSize else teamSize - 1 - (index % teamSize)
        val verticalAligned = false
        val x: Float
        val y: Float
        val arrowDirection: ArrowDirection
        if (verticalAligned) {
            x =
                this.x + (Minecraft.getInstance().window.guiScaledWidth / 2) + if (isAlly) -(TARGET_WIDTH + MOVE_HORIZONTAL_SPACING) else MOVE_HORIZONTAL_SPACING
            y =
                this.y + (5 + BACKGROUND_HEIGHT - (TARGET_HEIGHT + MOVE_VERTICAL_SPACING) * teamSize) / 2 + (TARGET_HEIGHT + MOVE_VERTICAL_SPACING) * fieldPos
            arrowDirection = if (isAlly) ArrowDirection.RIGHT else ArrowDirection.LEFT
        } else {
            x =
                this.x + Minecraft.getInstance().window.guiScaledWidth / 2 - (TARGET_WIDTH + MOVE_HORIZONTAL_SPACING) * teamSize / 2 + MOVE_HORIZONTAL_SPACING + fieldPos * TARGET_WIDTH
            y = this.y + 44 + (if (isAlly) TARGET_HEIGHT + MOVE_VERTICAL_SPACING else 0F)
            arrowDirection = when (fieldPos) {
                0 -> ArrowDirection.RIGHT
                teamSize - 1 -> ArrowDirection.LEFT
                else -> if (isAlly) ArrowDirection.UP else ArrowDirection.DOWN
            }
        }
        TargetTile(this, target, x, y, arrowDirection)
    }
    var targetTiles = baseTiles

    open inner class TargetTile(
        val targetSelection: BattleTargetSelection,
        val target: ActiveClientBattlePokemon,
        val x: Float,
        val y: Float,
        val arrowDirection: ArrowDirection
    ) {
        var moveTemplate = MoveTemplate.dummy(target.battlePokemon?.displayName.toString())
        private val responseTarget = selectableTargetList?.firstOrNull { it.getPNX() == target.getPNX() }?.getPNX()
        private val isMultiTarget = multiTargetList?.firstOrNull { it.getPNX() == target.getPNX() } != null
        private val isCurrentPokemon = request.activePokemon.battlePokemon?.uuid == target.battlePokemon!!.uuid

        open val response: MoveActionResponse
            get() = MoveActionResponse(
                targetSelection.move.id,
                responseTarget,
                targetSelection.gimmickID
            )
        val state = FloatingState()

        open val selectable: Boolean get() = isMultiTarget || (responseTarget != null)
        val hue = target.getHue()
        val rgb = if ((target.battlePokemon?.hpValue ?: 0F) > 0)
            Triple(
                ((hue shr 16) and 0b11111111) / 255F,
                ((hue shr 8) and 0b11111111) / 255F,
                (hue and 0b11111111) / 255F
            )
        else Triple(0.5f, 0.5f, 0.5f)

        val arrowTexture = when (arrowDirection) {
            ArrowDirection.LEFT -> battleArrowLeft
            ArrowDirection.RIGHT -> battleArrowRight
            ArrowDirection.UP -> battleArrowUp
            ArrowDirection.DOWN -> battleArrowDown
        }

        val arrowPosition = when (arrowDirection) {
            ArrowDirection.LEFT -> Pair(this.x + TARGET_WIDTH + 3F, this.y + 13)
            ArrowDirection.RIGHT -> Pair(this.x - 7F, this.y + 13F)
            ArrowDirection.UP -> Pair(this.x + TARGET_WIDTH / 2 - 4F, this.y + TARGET_HEIGHT + 3)
            ArrowDirection.DOWN -> Pair(this.x + TARGET_WIDTH / 2 - 4F, this.y - 7)
        }

        fun render(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
            val passedSeconds = CobblemonClient.battleOverlay.passedSeconds // This syncs the role throbbers
            val selectConditionOpacity = targetSelection.opacity * if (!selectable) 0.5F else 1F
            val matrices = context.pose()
            val battlePokemon = target.battlePokemon ?: return
            val isHovered = isHovered(mouseX.toDouble(), mouseY.toDouble())

            // Render Base tile texture
            blitk(
                matrixStack = matrices,
                texture = if (selectable) targetTileTexture else targetTileTextureDisabled,
                x = x,
                y = y,
                width = TARGET_WIDTH,
                height = TARGET_HEIGHT,
                vOffset = if (selectable && isHovered) TARGET_HEIGHT else 0,
                textureHeight = if (selectable) TARGET_HEIGHT * 2 else TARGET_HEIGHT
            )

            // Target Selection Arrows
            if (selectable && isHovered) {
                val offset = (sin(passedSeconds * 2 * Math.PI) + 1).toFloat()
                val pos = when (arrowDirection) {
                    ArrowDirection.LEFT -> Pair(arrowPosition.first - offset, arrowPosition.second)
                    ArrowDirection.RIGHT -> Pair(arrowPosition.first + offset, arrowPosition.second)
                    ArrowDirection.UP -> Pair(arrowPosition.first, arrowPosition.second - offset)
                    ArrowDirection.DOWN -> Pair(arrowPosition.first, arrowPosition.second + offset)
                }
                blitk(
                    matrixStack = matrices,
                    texture = arrowTexture,
                    x = pos.first * 2,
                    y = pos.second * 2,
                    scale = 0.5F,
                    height = if (arrowDirection == ArrowDirection.LEFT || arrowDirection == ArrowDirection.RIGHT) 17 else 10,
                    width = if (arrowDirection == ArrowDirection.LEFT || arrowDirection == ArrowDirection.RIGHT) 10 else 17
                )
            }

            // Target name
            if (battlePokemon.hpValue > 0) {
                // Render Pokémon
                matrices.pushPose()
                matrices.translate(x + TARGET_WIDTH - (25 / 2.0) - 2, y + 5.0, 0.0)
                matrices.scale(2.5F, 2.5F, 1F)
                // Grab aspects for right variant
                state.currentAspects = battlePokemon.state.currentAspects
                drawProfilePokemon(
                    species = battlePokemon.species.resourceIdentifier,
                    matrixStack = matrices,
                    rotation = Quaternionf().fromEulerXYZDegrees(Vector3f(13F, 35F, 0F)),
                    state = state,
                    scale = 4.5F,
                    partialTicks = delta
                )
                matrices.popPose()

                // Battle Role Upper
                val stage = floor((passedSeconds / BattleOverlay.ROLE_CYCLE_SECONDS % 1) * 5)
                if (!isCurrentPokemon || stage != 4.0) {
                    blitk(
                        matrixStack = matrices,
                        texture = battleRoleUpper,
                        x = x + 6,
                        y = y + 1,
                        width = 27,
                        height = 3,
                        textureHeight = 12,
                        vOffset = if (isCurrentPokemon) stage * 3 else 9,
                        red = rgb.first,
                        green = rgb.second,
                        blue = rgb.third
                    )
                }

                // Target Level
                drawScaledText(
                    context = context,
                    font = CobblemonResources.DEFAULT_LARGE,
                    text = lang("ui.lv").bold(),
                    x = x + 5,
                    y = y + 8,
                    opacity = selectConditionOpacity,
                    shadow = true
                )

                drawScaledText(
                    context = context,
                    font = CobblemonResources.DEFAULT_LARGE,
                    text = battlePokemon.level.toString().text().bold(),
                    x = x + 5 + 13,
                    y = y + 8,
                    opacity = selectConditionOpacity,
                    shadow = true
                )

                val displayText = battlePokemon.displayName.bold()
                // Pokémon Display Name
                drawScaledText(
                    context = context,
                    font = CobblemonResources.DEFAULT_LARGE,
                    text = displayText,
                    x = x + 5,
                    y = y + 15,
                    opacity = selectConditionOpacity,
                    shadow = true
                )
                // Gender
                val gender = battlePokemon.gender
                val pokemonDisplayNameWidth =
                    Minecraft.getInstance().font.width(displayText.font(CobblemonResources.DEFAULT_LARGE))
                if (gender != Gender.GENDERLESS) {
                    val isMale = gender == Gender.MALE
                    val textSymbol = if (isMale) "♂".text().bold() else "♀".text().bold()
                    drawScaledText(
                        context = context,
                        font = CobblemonResources.DEFAULT_LARGE,
                        text = textSymbol,
                        x = x + 6 + pokemonDisplayNameWidth,
                        y = y + 15,
                        colour = if (isMale) 0x32CBFF else 0xFC5454,
                        opacity = selectConditionOpacity,
                        shadow = true
                    )
                }

                // Lower Battle Role
                blitk(
                    matrixStack = matrices,
                    texture = battleRoleLower,
                    x = x + 4,
                    y = y + 29,
                    width = 82,
                    height = 3,
                    red = rgb.first,
                    green = rgb.second,
                    blue = rgb.third
                )

                // Actor Display Name
                drawScaledText(
                    context = context,
                    text = target.actor.displayName,
                    x = x + 3,
                    y = y + 26,
                    scale = 0.5F,
                    shadow = true,
                    opacity = selectConditionOpacity
                )
            }
        }

        fun isHovered(mouseX: Double, mouseY: Double): Boolean {
            if (isMultiTarget) {
                return targetTiles.firstOrNull { it.selectable && mouseX >= it.x && mouseX <= it.x + TARGET_WIDTH && mouseY >= it.y && mouseY <= it.y + TARGET_HEIGHT } != null
            }
            return mouseX >= x && mouseX <= x + TARGET_WIDTH && mouseY >= y && mouseY <= y + TARGET_HEIGHT
        }

        fun onClick() {
            if (!selectable) return
            targetSelection.playDownSound(Minecraft.getInstance().soundManager)
            targetSelection.battleGUI.selectAction(targetSelection.request, response)
        }
    }

    override fun renderWidget(context: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {

        // Draw Background
        blitk(
            matrixStack = context.pose(),
            texture = underlayTexture,
            x = x,
            y = y,
            width = Minecraft.getInstance().window.guiScaledWidth,
            height = BACKGROUND_HEIGHT
        )

        // Draw Title Text
        val text = lang("battle.select_target")
        val width = Minecraft.getInstance().font.width(text)
        drawScaledText(
            context = context,
            text = text,
            x = (Minecraft.getInstance().window.guiScaledWidth - width) / 2,
            y = y + if (request.activePokemon.getSidePokemon().count() == 2) 25 else 16,
            scale = 1F,
            shadow = true
        )

        targetTiles.forEach {
            it.render(context, mouseX, mouseY, delta)
        }
        backButton.render(context, mouseX, mouseY, delta)
    }

    override fun mousePrimaryClicked(pMouseX: Double, pMouseY: Double): Boolean {
        val target = targetTiles.find { it.isHovered(pMouseX, pMouseY) }
        if (target != null) {
            target.onClick()
            return true
        } else if (backButton.isHovered(pMouseX, pMouseY)) {
            playDownSound(Minecraft.getInstance().soundManager)
            battleGUI.changeActionSelection(BattleMoveSelection(battleGUI, request))
        }
        return false
    }

    override fun playDownSound(soundManager: SoundManager) {
        soundManager.play(SimpleSoundInstance.forUI(CobblemonSounds.GUI_CLICK, 1.0f))
    }
}
