package com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax

import com.cobblemon.mod.common.CobblemonSounds
import com.cobblemon.mod.common.advancement.CobblemonCriteria
import com.cobblemon.mod.common.advancement.criterion.PokemonInteractContext
import com.cobblemon.mod.common.api.battles.model.PokemonBattle
import com.cobblemon.mod.common.api.battles.model.actor.BattleActor
import com.cobblemon.mod.common.api.callback.PartySelectCallbacks
import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.events.pokemon.healing.PokemonHealedEvent
import com.cobblemon.mod.common.api.item.HealingSource
import com.cobblemon.mod.common.api.text.red
import com.cobblemon.mod.common.battles.BagItemActionResponse
import com.cobblemon.mod.common.battles.BattleRegistry
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon
import com.cobblemon.mod.common.item.CobblemonItem
import com.cobblemon.mod.common.item.battle.BagItem
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.util.battleLang
import com.cobblemon.mod.common.util.isHeld
import com.cobblemon.mod.common.util.isInBattle
import com.cobblemon.mod.common.util.party
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class MaxHoney : CobblemonItem(Properties()), HealingSource {
    val bagItem = object : BagItem {
        override val itemName = "item.mega_showdown.max_honey"
        override val returnItem = Items.AIR

        override fun canUse(battle: PokemonBattle, target: BattlePokemon): Boolean {
            return target.health <= 0
        }

        override fun getShowdownInput(
            actor: BattleActor,
            battlePokemon: BattlePokemon,
            data: String?
        ): String {
            return "max_honey"
        }
    }

    override fun use(world: Level, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        if (world !is ServerLevel) {
            return InteractionResultHolder.success(user.getItemInHand(hand))
        } else {
            val player = user as ServerPlayer
            val stack = user.getItemInHand(hand)
            val battle = BattleRegistry.getBattleByParticipatingPlayer(player)
            if (battle != null) {
                val actor = battle.getActor(player)!!
                val battlePokemon = actor.pokemonList
                if (!actor.canFitForcedAction()) {
                    player.sendSystemMessage(battleLang("bagitem.cannot").red(), true)
                    return InteractionResultHolder.consume(stack)
                } else {
                    val turn = battle.turn
                    PartySelectCallbacks.createBattleSelect(
                        player = player,
                        pokemon = battlePokemon,
                        canSelect = { bagItem.canUse(battle, it) }
                    ) { bp ->
                        if (actor.canFitForcedAction() && bp.health <= 0 && battle.turn == turn && stack.isHeld(player)) {
                            player.playSound(CobblemonSounds.ITEM_USE, 1F, 1F)
                            actor.forceChoose(
                                BagItemActionResponse(
                                    bagItem = bagItem,
                                    target = bp,
                                    data = bp.uuid.toString()
                                )
                            )
                            val stackName = BuiltInRegistries.ITEM.getKey(stack.item)
                            if (!player.isCreative) {
                                stack.shrink(1)
                            }
                            CobblemonCriteria.POKEMON_INTERACT.trigger(
                                player,
                                PokemonInteractContext(bp.effectedPokemon.species.resourceIdentifier, stackName)
                            )
                        }
                    }
                }
            } else {
                val pokemon = player.party().toList()
                PartySelectCallbacks.createFromPokemon(
                    player = player,
                    pokemon = pokemon,
                    canSelect = Pokemon::isFainted
                ) { pk ->
                    if (pk.isFainted() && !player.isInBattle() && stack.isHeld(player)) {
                        var amount = pk.maxHealth
                        CobblemonEvents.POKEMON_HEALED.postThen(
                            PokemonHealedEvent(pk, amount, this),
                            { return@createFromPokemon }) { event ->
                            amount = event.amount
                        }
                        pk.currentHealth = amount
                        val stackName = BuiltInRegistries.ITEM.getKey(stack.item)
                        if (!player.isCreative) {
                            stack.shrink(1)
                        }
                        CobblemonCriteria.POKEMON_INTERACT.trigger(
                            player,
                            PokemonInteractContext(pk.species.resourceIdentifier, stackName)
                        )
                    }
                }
            }
            return InteractionResultHolder.success(stack)
        }
    }

    override fun appendHoverText(
        arg: ItemStack,
        arg2: TooltipContext,
        tooltipComponents: MutableList<Component>,
        arg3: TooltipFlag
    ) {
        tooltipComponents.add(Component.translatable("tooltip.mega_showdown.max_honey.tooltip"))
        super.appendHoverText(arg, arg2, tooltipComponents, arg3)
    }
}