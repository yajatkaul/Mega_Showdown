package com.cobblemon.yajatkaul.mega_showdown.temp

import com.cobblemon.mod.common.api.battles.model.actor.AIBattleActor
import com.cobblemon.mod.common.api.battles.model.actor.ActorType
import com.cobblemon.mod.common.api.battles.model.actor.BattleActor
import com.cobblemon.mod.common.api.battles.model.actor.EntityBackedBattleActor
import com.cobblemon.mod.common.api.net.NetworkPacket
import com.cobblemon.mod.common.api.storage.party.PartyStore
import com.cobblemon.mod.common.battles.ai.RandomBattleAI
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon
import com.cobblemon.mod.common.entity.npc.NPCEntity
import com.cobblemon.mod.common.net.messages.client.battle.BattleEndPacket
import com.cobblemon.mod.common.util.battleLang
import com.cobblemon.mod.common.util.chainFutures
import com.cobblemon.mod.common.util.effectiveName
import com.cobblemon.mod.common.util.update
import java.util.concurrent.CompletableFuture

class NPCBattleActorTemp(
    val npc: NPCEntity,
    pokemonList: List<BattlePokemon>,
    val skill: Int
) : AIBattleActor(
    gameId = npc.uuid,
    pokemonList = pokemonList.let { if (npc.npc.randomizePartyOrder) it.shuffled() else it },
    battleAI = RandomBattleAI()
), EntityBackedBattleActor<NPCEntity> {
    override val entity = npc
    override val type = ActorType.NPC
    override fun getName() = npc.effectiveName().copy()
    override fun nameOwned(name: String) = battleLang("owned_pokemon", this.getName(), name)
    override val initialPos = entity.position()

    constructor(
        npc: NPCEntity,
        party: PartyStore,
        skill: Int
    ) : this(
        npc,
        party.toBattleTeam(healPokemon = npc.npc.autoHealParty),
        skill
    )

    override fun sendUpdate(packet: NetworkPacket<*>) {
        super.sendUpdate(packet)
        if (packet is BattleEndPacket) {
            if (npc.isAlive) {
                val allEntities = pokemonList.mapNotNull { it.entity }.toMutableList()
                val finalFuture = CompletableFuture<Unit>()
                chainFutures(allEntities.map { pokemonEntity -> { pokemonEntity.recallWithAnimation() } }.iterator(), finalFuture)
                if (allEntities.isEmpty()) {
                    finalFuture.complete(Unit)
                }
                finalFuture.thenApply {
                    // Delay because losing animations can take a second
                    npc.after(seconds = 3F) {
                        entity.entityData.update(NPCEntity.BATTLE_IDS) { it - battle.battleId }
                    }
                }
            } else {
                entity.entityData.update(NPCEntity.BATTLE_IDS) { it - battle.battleId }
            }
        }
    }

    override fun win(otherWinners: List<BattleActor>, losers: List<BattleActor>) {
        super.win(otherWinners, losers)
        npc.playAnimation(NPCEntity.WIN_ANIMATION)
    }

    override fun lose(winners: List<BattleActor>, otherLosers: List<BattleActor>) {
        super.lose(winners, otherLosers)
        npc.playAnimation(NPCEntity.LOSE_ANIMATION)
//        winners.forEach {
//            rewards.forEach {
//                winner give reward :))
//            }
//        }
    }
}
