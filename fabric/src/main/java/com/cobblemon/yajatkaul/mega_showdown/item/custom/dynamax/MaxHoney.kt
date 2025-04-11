package com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax

import com.cobblemon.mod.common.api.Priority
import com.cobblemon.mod.common.api.battles.model.PokemonBattle
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.cobblemon.mod.common.battles.BagItems
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon
import com.cobblemon.mod.common.item.CobblemonItem
import com.cobblemon.mod.common.item.battle.BagItem
import com.cobblemon.mod.common.item.battle.SimpleBagItemLike
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.text.Text

class MaxHoney : CobblemonItem(Settings()), SimpleBagItemLike {
    override val bagItem = object : BagItem {
        override val itemName = "item.mega_showdown.max_honey"
        override val returnItem = Items.AIR

        override fun canUse(battle: PokemonBattle, target: BattlePokemon): Boolean {
            return target.health > 0
        }

        override fun getShowdownInput(
            actor: com.cobblemon.mod.common.api.battles.model.actor.BattleActor,
            battlePokemon: BattlePokemon,
            data: String?
        ): String {
            battlePokemon.effectedPokemon.incrementFriendship(1)
            return "max_honey ${Stats.ATTACK.showdownId} ${Stats.DEFENCE.showdownId} ${Stats.SPECIAL_ATTACK.showdownId} ${Stats.SPECIAL_DEFENCE.showdownId} ${Stats.SPEED.showdownId} 1"
        }
    }

    init {
        BagItems.bagItems.add(Priority.NORMAL, this)
    }

    override fun appendTooltip(
        stack: ItemStack,
        context: TooltipContext,
        tooltip: MutableList<Text>,
        type: TooltipType
    ) {
        tooltip.add(Text.translatable("tooltip.mega_showdown.max_honey.tooltip"))
        super.appendTooltip(stack, context, tooltip, type)
    }
}