package com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax

import com.cobblemon.mod.common.api.Priority
import com.cobblemon.mod.common.api.battles.model.PokemonBattle
import com.cobblemon.mod.common.api.battles.model.actor.BattleActor
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.cobblemon.mod.common.battles.BagItems
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon
import com.cobblemon.mod.common.item.battle.BagItem
import com.cobblemon.mod.common.item.battle.SimpleBagItemLike
import net.minecraft.network.chat.Component
import net.minecraft.world.item.*
import net.minecraft.world.level.block.Block

class MaxMushroom(
    block: Block,
    settings: Properties
) : BlockItem(block, settings), SimpleBagItemLike {

    override val bagItem = object : BagItem {
        override val itemName = "item.mega_showdown.max_mushroom"
        override val returnItem = Items.AIR

        override fun canUse(battle: PokemonBattle, target: BattlePokemon): Boolean {
            return target.health > 0
        }

        override fun getShowdownInput(
            actor: BattleActor,
            battlePokemon: BattlePokemon,
            data: String?
        ): String {
            battlePokemon.effectedPokemon.incrementFriendship(1)
            return "max_mushroom ${Stats.ATTACK.showdownId} ${Stats.DEFENCE.showdownId} ${Stats.SPECIAL_ATTACK.showdownId} ${Stats.SPECIAL_DEFENCE.showdownId} ${Stats.SPEED.showdownId} 1"
        }
    }

    init {
        BagItems.bagItems.add(Priority.NORMAL, this)
    }

    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        tooltipComponents: MutableList<Component>,
        flag: TooltipFlag
    ) {
        tooltipComponents.add(Component.translatable("tooltip.mega_showdown.max_mushroom.tooltip"))
        super.appendHoverText(stack, context, tooltipComponents, flag)
    }
}