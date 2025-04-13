package com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax

import com.cobblemon.mod.common.api.Priority
import com.cobblemon.mod.common.api.battles.model.PokemonBattle
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.cobblemon.mod.common.battles.BagItems
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon
import com.cobblemon.mod.common.item.CobblemonItem
import com.cobblemon.mod.common.item.battle.BagItem
import com.cobblemon.mod.common.item.battle.SimpleBagItemLike
import net.minecraft.network.chat.Component
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.TooltipFlag

class MaxMushroom : CobblemonItem(Properties()), SimpleBagItemLike {

    override val bagItem = object : BagItem {
        override val itemName = "item.mega_showdown.max_mushroom"
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
            return "max_mushroom ${Stats.ATTACK.showdownId} ${Stats.DEFENCE.showdownId} ${Stats.SPECIAL_ATTACK.showdownId} ${Stats.SPECIAL_DEFENCE.showdownId} ${Stats.SPEED.showdownId} 1"
        }
    }

    init {
        BagItems.bagItems.add(Priority.NORMAL, this)
    }

    override fun appendHoverText(
        arg: ItemStack,
        arg2: TooltipContext,
        tooltipComponents: MutableList<Component>,
        arg3: TooltipFlag
    ) {
        tooltipComponents.add(Component.translatable("tooltip.mega_showdown.max_mushroom.tooltip"))
        super.appendHoverText(arg, arg2, tooltipComponents, arg3)
    }
}