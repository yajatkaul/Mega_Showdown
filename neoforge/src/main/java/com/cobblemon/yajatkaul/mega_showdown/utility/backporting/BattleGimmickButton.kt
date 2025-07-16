package com.cobblemon.yajatkaul.mega_showdown.utility.backporting

import com.cobblemon.mod.common.api.gui.blitk
import com.cobblemon.mod.common.api.moves.MoveTemplate
import com.cobblemon.mod.common.api.moves.Moves
import com.cobblemon.mod.common.battles.*
import com.cobblemon.mod.common.battles.ShowdownMoveset.Gimmick
import com.cobblemon.mod.common.client.CobblemonClient
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleMoveSelection
import com.cobblemon.mod.common.util.cobblemonResource
import com.cobblemon.mod.common.util.math.toRGB
import com.cobblemon.yajatkaul.mega_showdown.utility.backporting.BattleGimmickButton.GimmickTile
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.sounds.SoundEvents

/**
 * Button for toggling gimmicks during a battle.
 *
 * @param gimmick The [ShowdownMoveset.Gimmick] tied to this button.
 * @property tiles The [GimmickTile]s to render when this button is [toggled].
 *
 * @author Segfault Guy
 * @since July 8th, 2023
 */
abstract class BattleGimmickButton(val gimmick: Gimmick, val x: Float, val y: Float) {

    companion object {
        const val WIDTH = 36
        const val HEIGHT = 34
        const val SCALE = 0.5F
        const val XOFF = WIDTH * SCALE
        const val YOFF = HEIGHT * SCALE
        const val SPACING = 26

        /** Factory for creating an instance of [BattleGimmickButton] based on [ShowdownMoveset.Gimmick]. */
        fun create(gimmick: Gimmick, moveSelection: BattleMoveSelection, x: Float, y: Float): BattleGimmickButton {
            return when (gimmick) {
                Gimmick.ULTRA_BURST ->
                    UltraBurstButton(moveSelection, x, y)

                Gimmick.Z_POWER ->
                    ZPowerButton(moveSelection, x, y)

                Gimmick.DYNAMAX ->
                    DynamaxButton(moveSelection, x, y)

                else ->
                    object : BattleGimmickButton(gimmick, x, y) {
                        override var tiles: List<BattleMoveSelection.MoveTile> = moveSelection.baseTiles.map { tile ->
                            GimmickTile(gimmick, moveSelection, tile.move, tile.x, tile.y)
                        }
                    }
            }
        }
    }

    abstract val tiles: List<BattleMoveSelection.MoveTile>
    var toggled = false
    private val sfx = SimpleSoundInstance.forUI(SoundEvents.ANVIL_LAND, 1.0F)
    private val texture = gimmick.id
    val selectable: Boolean
        get() {
            val battle = CobblemonClient.battle ?: return false
            return (battle as? ClientBattleExtension)?.pendingGimmick(gimmick) ?: false
        }

    fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        blitk(
            matrixStack = graphics.pose(),
            texture = cobblemonResource("textures/gui/battle/battle_gimmick_${texture}.png"),
            x = x * 2,
            y = y * 2,
            height = HEIGHT,
            width = WIDTH,
            vOffset = if ((toggled || isHovered(mouseX.toDouble(), mouseY.toDouble()) && selectable)) HEIGHT else 0,
            textureHeight = HEIGHT * 2,
            scale = SCALE,
            alpha = if (selectable) 1.0F else 0.5F
        )
    }

    fun isHovered(mouseX: Double, mouseY: Double) =
        mouseX >= x && mouseX <= x + XOFF && mouseY >= y && mouseY <= y + YOFF

    fun toggle(): Boolean {
        if (!selectable)
            return toggled
        toggled = !toggled
        Minecraft.getInstance().soundManager.play(sfx)
        return toggled
    }

    /**
     * Tile for an [InBattleMove] when a [BattleGimmickButton] is toggled. Triggers a gimmick when executed.
     *
     * @param moveSelection The [BattleMoveSelection] subscreen this tile is rendered on.
     * @param move The [InBattleMove] this tile is rendered for. May or may not have an associated [InBattleGimmickMove].
     * @property gimmick The [ShowdownMoveset.Gimmick] that is triggered.
     *
     * @author Segfault Guy
     * @since July 15th, 2023
     */
    open class GimmickTile(
        private val gimmick: Gimmick,
        moveSelection: BattleMoveSelection,
        move: InBattleMove,
        x: Float,
        y: Float
    ) : BattleMoveSelection.MoveTile(moveSelection, move, x, y) {

        // if there isn't a compatible gimmick for this move, the rendered moveTemplate will default to the base template
        init {
            gimmickMoveTemplate?.let {
                moveTemplate = it
                rgb = it.elementalType.hue.toRGB()
            }

            if (gimmick == Gimmick.TERASTALLIZATION || gimmick == Gimmick.MEGA_EVOLUTION) {
                moveTemplate = Moves.getByNameOrDummy(move.id)
                rgb = moveTemplate.getEffectiveElementalType(pokemon).hue.toRGB()
            }
        }

        protected val gimmickMove = move.gimmickMove

        // showdown already translates the base move id to the gimmick variant
        override val response: MoveActionResponse
            get() = MoveActionResponse(move.id, targetPnx, gimmick.id)
        override val targetList: List<Targetable>?
            get() = if (gimmickMove != null) gimmickMove.target.targetList(moveSelection.request.activePokemon) else super.targetList
        override val selectable: Boolean
            get() = if (gimmickMove != null) !gimmickMove.disabled else super.selectable

        /**
         * Couple reasons for making a unique template for gimmick moves:
         * 1. Z versions of status moves aren't registered as distinct moves, so can't get a template by name or num
         * 2. Damaging moves are registered as physical and it's confusing since the actual damageCategory is inherited from the base move
         */
        private val gimmickMoveTemplate: MoveTemplate?
            get() {
                val SHOWDOWN_ID_SANITIZE_REGEX = Regex("[^a-z0-9]+")

                val gimmickMoveID = move.gimmickMove?.move
                    ?.lowercase()
                    ?.replace(SHOWDOWN_ID_SANITIZE_REGEX, "") ?: return null
                val gimmickTemplate = Moves.getByName(gimmickMoveID)
                return MoveTemplate(
                    name = gimmickMoveID,
                    num = gimmickTemplate?.num ?: -1,
                    elementalType = gimmickTemplate?.elementalType ?: moveTemplate.elementalType,
                    damageCategory = moveTemplate.damageCategory,
                    power = gimmickTemplate?.power ?: moveTemplate.power,
                    target = gimmickTemplate?.target ?: moveTemplate.target,
                    accuracy = gimmickTemplate?.accuracy ?: moveTemplate.accuracy,
                    pp = gimmickTemplate?.pp ?: moveTemplate.pp,
                    priority = gimmickTemplate?.priority ?: moveTemplate.priority,
                    critRatio = gimmickTemplate?.critRatio ?: moveTemplate.critRatio,
                    effectChances = gimmickTemplate?.effectChances ?: moveTemplate.effectChances,
                    actionEffect = null
                )
            }
    }
}

/**
 * Button for toggling Z-Power variations of compatible moves.
 *
 * @property tiles The Z-Moves for this moveset.
 *
 * @author Segfault Guy
 * @since July 15th, 2023
 */
class ZPowerButton(moveSelection: BattleMoveSelection, x: Float, y: Float) :
    BattleGimmickButton(Gimmick.Z_POWER, x, y) {

    override var tiles: List<BattleMoveSelection.MoveTile> = moveSelection.baseTiles.map { tile ->
        ZPowerTile(moveSelection, tile.move, tile.x, tile.y)
    }

    /**
     * Tile for Z-Power variation of a move.
     *
     * @param move The base move.
     * @property gimmickMove The respective Z-Move (if it exists).
     *
     * @author Segfault Guy
     * @since July 16th, 2023
     */
    class ZPowerTile(
        moveSelection: BattleMoveSelection,
        move: InBattleMove,
        x: Float,
        y: Float
    ) : GimmickTile(Gimmick.Z_POWER, moveSelection, move, x, y) {
        override val selectable: Boolean
            get() = gimmickMove != null && !gimmickMove.disabled
    }
}

/**
 * Button for toggling Max variations of moves.
 *
 * @property tiles The Max Moves for this moveset.
 *
 * @author Segfault Guy
 * @since July 27th, 2023
 */
class DynamaxButton(moveSelection: BattleMoveSelection, x: Float, y: Float) :
    BattleGimmickButton(Gimmick.DYNAMAX, x, y) {

    override var tiles: List<BattleMoveSelection.MoveTile> = moveSelection.baseTiles.map { tile ->
        DynamaxTile(moveSelection, tile.move, tile.x, tile.y)
    }

    /**
     * Tile for Max variation of a move.
     *
     * @param move The base move.
     * @property gimmickMove The respective Max Move.
     *
     * @author Segfault Guy
     * @since July 27th, 2023
     */
    class DynamaxTile(
        moveSelection: BattleMoveSelection,
        move: InBattleMove,
        x: Float,
        y: Float
    ) : GimmickTile(Gimmick.DYNAMAX, moveSelection, move, x, y) {
        override val selectable: Boolean
            get() = gimmickMove != null && !gimmickMove.disabled
    }
}

/**
 * Button for toggling Ultra Burst.
 *
 *
 * @author YajatKaul
 * @since July 16th, 2025
 */
class UltraBurstButton(moveSelection: BattleMoveSelection, x: Float, y: Float) :
    BattleGimmickButton(Gimmick.ULTRA_BURST, x, y) {

    override var tiles: List<BattleMoveSelection.MoveTile> = moveSelection.baseTiles.map { tile ->
        UltraBurstTile(moveSelection, tile.move, tile.x, tile.y)
    }

    /**
     *
     * @param move The base move.
     *
     * @author YajatKaul
     * @since July 16th, 2025
     */
    class UltraBurstTile(
        moveSelection: BattleMoveSelection,
        move: InBattleMove,
        x: Float,
        y: Float
    ) : GimmickTile(Gimmick.ULTRA_BURST, moveSelection, move, x, y)
}