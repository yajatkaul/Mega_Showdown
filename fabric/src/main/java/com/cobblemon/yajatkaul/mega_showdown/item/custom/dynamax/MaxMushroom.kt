package com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax

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
import net.minecraft.block.Block
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.Items
import net.minecraft.item.tooltip.TooltipType
import net.minecraft.registry.Registries
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class MaxMushroom(
    block: Block,
    settings: Settings
) : BlockItem(block, settings), HealingSource {

    private val bagItem = object : BagItem {
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

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (world !is ServerWorld) {
            return TypedActionResult.pass(user.getStackInHand(hand))
        } else {
            val player = user as ServerPlayerEntity
            val stack = user.getStackInHand(hand)
            val battle = BattleRegistry.getBattleByParticipatingPlayer(player)
            if (battle != null) {
                val actor = battle.getActor(player)!!
                val battlePokemon = actor.pokemonList
                if (!actor.canFitForcedAction()) {
                    player.sendMessage(battleLang("bagitem.cannot").red(), false)
                    return TypedActionResult.consume(stack)
                } else {
                    val turn = battle.turn
                    PartySelectCallbacks.createBattleSelect(
                        player = player,
                        pokemon = battlePokemon,
                        canSelect = { bagItem.canUse(battle, it) }
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
                            val stackName = Registries.ITEM.getId(stack.item)
                            stack.decrementUnlessCreative(1, player);
                            CobblemonCriteria.POKEMON_INTERACT.trigger(
                                player,
                                PokemonInteractContext(bp.effectedPokemon.species.resourceIdentifier, stackName)
                            )
                        }
                    }
                    return TypedActionResult.success(stack)
                }
            }
            return super.use(world, user, hand)
        }
    }

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        val player = context.player
        val stack = context.stack

        if (player is ServerPlayerEntity) {
            val battle = BattleRegistry.getBattleByParticipatingPlayer(player)
            if (battle != null) {
                val actor = battle.getActor(player)!!
                val battlePokemon = actor.pokemonList
                if (!actor.canFitForcedAction()) {
                    player.sendMessage(battleLang("bagitem.cannot").red(), false)
                    return ActionResult.PASS
                } else {
                    val turn = battle.turn
                    PartySelectCallbacks.createBattleSelect(
                        player = player,
                        pokemon = battlePokemon,
                        canSelect = { bagItem.canUse(battle, it) }
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
                            val stackName = Registries.ITEM.getId(stack.item)
                            stack.decrementUnlessCreative(1, player);
                            CobblemonCriteria.POKEMON_INTERACT.trigger(
                                player,
                                PokemonInteractContext(bp.effectedPokemon.species.resourceIdentifier, stackName)
                            )
                        }
                    }
                    return ActionResult.SUCCESS
                }
            }
        }
        return super.useOnBlock(context)
    }


    override fun appendTooltip(
        stack: ItemStack,
        context: TooltipContext,
        tooltipComponents: MutableList<Text>,
        flag: TooltipType
    ) {
        tooltipComponents.add(Text.translatable("tooltip.mega_showdown.max_mushroom.tooltip"))
        super.appendTooltip(stack, context, tooltipComponents, flag)
    }
}