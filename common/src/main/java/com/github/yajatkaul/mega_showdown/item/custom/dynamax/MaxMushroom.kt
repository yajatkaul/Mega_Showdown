package com.github.yajatkaul.mega_showdown.item.custom.dynamax

import com.cobblemon.mod.common.CobblemonSounds
import com.cobblemon.mod.common.advancement.CobblemonCriteria
import com.cobblemon.mod.common.advancement.criterion.PokemonInteractContext
import com.cobblemon.mod.common.api.battles.model.PokemonBattle
import com.cobblemon.mod.common.api.battles.model.actor.BattleActor
import com.cobblemon.mod.common.api.callback.PartySelectCallbacks
import com.cobblemon.mod.common.api.item.HealingSource
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.cobblemon.mod.common.api.text.red
import com.cobblemon.mod.common.battles.BagItemActionResponse
import com.cobblemon.mod.common.battles.BattleRegistry
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon
import com.cobblemon.mod.common.item.battle.BagItem
import com.cobblemon.mod.common.util.battleLang
import com.cobblemon.mod.common.util.isHeld
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block

open class MaxMushroom(
    block: Block,
    settings: Properties
) : BlockItem(block, settings), HealingSource {

    private val bagItem = object : BagItem {
        override val itemName = "item.mega_showdown.max_mushroom"
        override val returnItem = Items.AIR

        override fun canUse(
            stack: ItemStack,
            battle: PokemonBattle,
            target: BattlePokemon
        ): Boolean {
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

    override fun use(world: Level, user: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        if (world !is ServerLevel) {
            return InteractionResultHolder.pass(user.getItemInHand(hand))
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
                        canSelect = { bagItem.canUse(stack, battle, it) }
                    ) { bp ->
                        if (actor.canFitForcedAction() && bp.health > 0 && battle.turn == turn && stack.isHeld(player)) {
                            player.playSound(CobblemonSounds.ITEM_USE, 1F, 1F)
                            actor.forceChoose(
                                BagItemActionResponse(
                                    bagItem = bagItem,
                                    target = bp,
                                    data = bp.uuid.toString()
                                )
                            )
                            val stackName = BuiltInRegistries.ITEM.getKey(stack.item)
                            stack.consume(1, player);
                            CobblemonCriteria.POKEMON_INTERACT.trigger(
                                player,
                                PokemonInteractContext(bp.effectedPokemon.species.resourceIdentifier, stackName)
                            )
                        }
                    }
                    return InteractionResultHolder.success(stack)
                }
            }
            return super.use(world, user, hand)
        }
    }

    override fun useOn(arg: UseOnContext): InteractionResult {
        val player = arg.player
        val stack = arg.itemInHand

        if (player is ServerPlayer) {
            val battle = BattleRegistry.getBattleByParticipatingPlayer(player)
            if (battle != null) {
                val actor = battle.getActor(player)!!
                val battlePokemon = actor.pokemonList
                if (!actor.canFitForcedAction()) {
                    player.sendSystemMessage(battleLang("bagitem.cannot").red(), false)
                    return InteractionResult.PASS
                } else {
                    val turn = battle.turn
                    PartySelectCallbacks.createBattleSelect(
                        player = player,
                        pokemon = battlePokemon,
                        canSelect = { bagItem.canUse(stack, battle, it) }
                    ) { bp ->
                        if (actor.canFitForcedAction() && bp.health > 0 && battle.turn == turn && stack.isHeld(player)) {
                            player.playSound(CobblemonSounds.ITEM_USE, 1F, 1F)
                            actor.forceChoose(
                                BagItemActionResponse(
                                    bagItem = bagItem,
                                    target = bp,
                                    data = bp.uuid.toString()
                                )
                            )
                            val stackName = BuiltInRegistries.ITEM.getKey(stack.item)
                            stack.consume(1, player);
                            CobblemonCriteria.POKEMON_INTERACT.trigger(
                                player,
                                PokemonInteractContext(bp.effectedPokemon.species.resourceIdentifier, stackName)
                            )
                        }
                    }
                    return InteractionResult.SUCCESS
                }
            }
        }
        return super.useOn(arg)
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