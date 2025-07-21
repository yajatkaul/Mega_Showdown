package com.cobblemon.yajatkaul.mega_showdown.temp

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.Priority
import com.cobblemon.mod.common.api.item.ability.AbilityChanger
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.api.scheduling.ServerTaskTracker
import com.cobblemon.mod.common.api.scheduling.taskBuilder
import com.cobblemon.mod.common.battles.BattleFormat
import com.cobblemon.mod.common.battles.BattleRegistry
import com.cobblemon.mod.common.battles.BattleSide
import com.cobblemon.mod.common.battles.actor.PlayerBattleActor
import com.cobblemon.mod.common.battles.actor.PokemonBattleActor
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon
import com.cobblemon.mod.common.entity.npc.NPCEntity
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.trade.ActiveTrade
import com.cobblemon.mod.common.trade.DummyTradeParticipant
import com.cobblemon.mod.common.trade.PlayerTradeParticipant
import com.cobblemon.mod.common.util.party
import com.cobblemon.mod.common.util.toPokemon
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import com.mojang.serialization.JsonOps
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.util.math.Box
import java.io.File
import java.io.PrintWriter

object TestCommand {

    @JvmStatic
    fun register(dispatcher: CommandDispatcher<ServerCommandSource>) {
        val command = CommandManager.literal("multitry")
            .requires { it.hasPermissionLevel(4) } // Fabric permission level check
            .executes(this::execute)
        dispatcher.register(command)
    }

    @Suppress("SameReturnValue")
    private fun execute(context: CommandContext<ServerCommandSource>): Int {
        if (context.source.player !is ServerPlayerEntity) {
            return com.mojang.brigadier.Command.SINGLE_SUCCESS
        }

        try {
            val player = context.source.player as ServerPlayerEntity
            val npcs = player.world.getOtherEntities(player, player.boundingBox.expand(20.0)) { it is NPCEntity }
                .map { NPCBattleActorTemp(it as NPCEntity, it.getPartyForChallenge(listOf(player))!!, 0) }
                .sortedBy { player.squaredDistanceTo(it.entity) }
            val plyr = player.world.getOtherEntities(player, player.boundingBox.expand(20.0)) { it is PlayerEntity }

            val playerTeam = player.party().toBattleTeam()
            val playerActor = PlayerBattleActor(player.uuid, playerTeam)
            //val player2 = plyr[0] as ServerPlayerEntity
            //val playerTeam2 = player2.party().toBattleTeam()
            //val playerActor2 = PlayerBattleActor(plyr[0].uuid, playerTeam2)

            BattleRegistry.startBattle(
                battleFormat = BattleFormat.GEN_9_MULTI,
                side1 = BattleSide(playerActor, npcs[2]),
                side2 = BattleSide(npcs[1], npcs[0]),
            ).ifSuccessful {
                it.battlePartyStores.add(player.party())
            }
        } catch (e: Exception) {
            Cobblemon.LOGGER.error("Error in testcommand", e)
        }
        return com.mojang.brigadier.Command.SINGLE_SUCCESS
    }

    var trade: ActiveTrade? = null
    var lastDebugId = 0

    private fun testClosestBattle(context: CommandContext<ServerCommandSource>) {
        val player = context.source.playerOrThrow
        val cloneTeam = player.party().toBattleTeam(true)
        cloneTeam.forEach { it.effectedPokemon.level = 100 }
        val scanBox = Box.of(player.pos, 9.0, 9.0, 9.0)
        val results = player.world.getOtherEntities(
            null,
            scanBox
        ) { entityPokemon -> entityPokemon is PokemonEntity && entityPokemon.pokemon.isWild() }
        var pokemonEntity = results.firstOrNull()
        if (pokemonEntity == null) {
            context.source.sendError(Text.literal("Cannot find any wild Pokémon in a 9x9x9 area"))
            return
        }
        pokemonEntity = pokemonEntity as PokemonEntity
        BattleRegistry.startBattle(
            BattleFormat.GEN_9_SINGLES,
            BattleSide(PlayerBattleActor(player.uuid, cloneTeam)),
            BattleSide(
                PokemonBattleActor(
                    pokemonEntity.pokemon.uuid,
                    BattlePokemon(pokemonEntity.pokemon),
                    Cobblemon.config.defaultFleeDistance
                )
            )
        )
    }

    private fun testTrade(playerEntity: ServerPlayerEntity) {
        val trade = ActiveTrade(
            player1 = PlayerTradeParticipant(playerEntity),
            player2 = DummyTradeParticipant(
                pokemonList = mutableListOf(
                    "pikachu level=30 shiny".toPokemon(),
                    "machop level=15".toPokemon()
                )
            )
        )
        this.trade = trade
        // Fabric networking: Requires a custom channel for TradeStartedPacket
        // Example: ServerPlayNetworking.send(playerEntity, TRADE_STARTED_CHANNEL, TradeStartedPacket(...).toBuffer())
        // For now, comment out as it needs Cobblemon's Fabric networking implementation
        /*
        playerEntity.sendPacket(TradeStartedPacket(trade.player2.uuid, trade.player2.name.copy(), trade.player2.party.mapNullPreserving(TradeStartedPacket::TradeablePokemon)))
        */

        taskBuilder()
            .interval(0.5F) // Run every half second
            .execute { task ->
                if (this.trade != trade) {
                    task.expire()
                    return@execute
                }

                testUpdate()
            }
            .tracker(ServerTaskTracker)
            .iterations(Int.MAX_VALUE)
            .build()
    }

    @Suppress("UNUSED_VARIABLE")
    private fun testUpdate() {
        val trade = this.trade ?: return
        val dummy = trade.player2 as DummyTradeParticipant

        val currentDebugId =
            0 // Change this number to some other number and hot reload when you want the later code block to run once.

        if (lastDebugId != currentDebugId) {
            // Some code here, when hotswapped, will immediately run.
            // This is a trick so that if you want to fiddle with the GUI, then you want the dummy participant to do something,
            // you can update the code here and the 'currentDebugId' value and this will run once.

            // Something

            this.lastDebugId = currentDebugId
        }
    }

    fun readBerryDataFromCSV() {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val csv = File("scripty/berries.csv").readLines()
        val iterator = csv.iterator()
        iterator.next() // Skip heading
        iterator.next() // Skip sub-heading thing
        for (line in iterator) {
            val cols = line.split(",")
            val berryName = cols[1].lowercase() + "_berry"
            val json = gson.fromJson(File("scripty/old/$berryName.json").reader(), JsonObject::class.java)
            val growthPoints = mutableListOf<JsonObject>()
            var index = 7
            while (true) {
                if (cols.size <= index || cols[index].isBlank()) {
                    break
                }

                val posX = cols[index].toFloat()
                val posY = cols[index + 1].toFloat()
                val posZ = cols[index + 2].toFloat()
                val rotX = cols[index + 3].toFloat()
                val rotY = cols[index + 4].toFloat()
                val rotZ = cols[index + 5].toFloat()

                val position = JsonObject()
                position.addProperty("x", posX)
                position.addProperty("y", posY)
                position.addProperty("z", posZ)
                val rotation = JsonObject()
                rotation.addProperty("x", rotX)
                rotation.addProperty("y", rotY)
                rotation.addProperty("z", rotZ)

                val obj = JsonObject()
                obj.add("position", position)
                obj.add("rotation", rotation)
                growthPoints.add(obj)
                index += 6
            }

            val arr = json.getAsJsonArray("growthPoints")
            arr.removeAll { true }
            for (point in growthPoints) {
                arr.add(point)
            }

            val new = File("scripty/new/$berryName.json")
            val pw = PrintWriter(new)
            gson.toJson(json, pw)
            pw.flush()
            pw.close()
        }
    }

    private fun testAbilitiesBetweenEvolution(context: CommandContext<ServerCommandSource>) {
        val results = Text.literal("Ability test results (Assumed default assets)")
            .append(Text.literal("\n"))
            .append(this.testHiddenAbilityThroughoutEvolutions())
            .append(Text.literal("\n"))
            .append(this.testMiddleStageSingleAbility())
            .append(Text.literal("\n"))
            .append(this.testForcedAbility())
            .append(Text.literal("\n"))
            .append(Text.literal("\n"))
            .append(this.testAbilityCapsule())
            .append(Text.literal("\n"))
            .append(this.testAbilityPatch())
        context.source.sendMessage(results)
    }

    private fun testHiddenAbilityThroughoutEvolutions(): Text {
        val pokemon =
            PokemonProperties.parse("dragonair level=${Cobblemon.config.maxPokemonLevel} hiddenability=true").create()
        val dragonite =
            pokemon.evolutions.firstOrNull() ?: return Text.literal("✖ Failed to find Dragonair » Dragonite evolution")
                .styled { it.withColor(0xFF0000) }
        dragonite.evolutionMethod(pokemon)
        val failed = pokemon.ability.index != 0 || pokemon.ability.priority != Priority.LOW || pokemon.ability.forced
        val symbol = if (failed) "✖" else "✔"
        val result =
            Text.literal(" $symbol Dratini line final Ability(name=${pokemon.ability.name}, priority=${pokemon.ability.priority}, index=${pokemon.ability.index}, forced=${pokemon.ability.forced})")
        return if (failed) result.styled { it.withColor(0xFF0000) } else result.styled { it.withColor(0x00FF00) }
    }

    private fun testMiddleStageSingleAbility(): Text {
        val pokemon =
            PokemonProperties.parse("scatterbug level=${Cobblemon.config.maxPokemonLevel} ability=compoundeyes")
                .create()
        val spewpa =
            pokemon.evolutions.firstOrNull() ?: return Text.literal("✖ Failed to find Scatterbug » Spewpa evolution")
                .styled { it.withColor(0xFF0000) }
        spewpa.evolutionMethod(pokemon)
        val vivillon =
            pokemon.evolutions.firstOrNull() ?: return Text.literal("✖ Failed to find Spewpa » Vivillon evolution")
                .styled { it.withColor(0xFF0000) }
        vivillon.evolutionMethod(pokemon)
        val failed = pokemon.ability.index != 1 || pokemon.ability.priority != Priority.LOWEST || pokemon.ability.forced
        val symbol = if (failed) "✖" else "✔"
        val result =
            Text.literal(" $symbol Scatterbug line final Ability(name=${pokemon.ability.name}, priority=${pokemon.ability.priority}, index=${pokemon.ability.index}, forced=${pokemon.ability.forced})")
        return if (failed) result.styled { it.withColor(0xFF0000) } else result.styled { it.withColor(0x00FF00) }
    }

    private fun testForcedAbility(): Text {
        val pokemon =
            PokemonProperties.parse("magikarp level=${Cobblemon.config.maxPokemonLevel} ability=adaptability").create()
        val gyarados =
            pokemon.evolutions.firstOrNull() ?: return Text.literal("✖ Failed to find Magikarp » Gyarados evolution")
                .styled { it.withColor(0xFF0000) }
        gyarados.evolutionMethod(pokemon)
        val failed = !pokemon.ability.forced || pokemon.ability.template.name != "adaptability"
        val symbol = if (failed) "✖" else "✔"
        val result =
            Text.literal(" $symbol Magikarp line forced Ability(name=${pokemon.ability.name}, priority=${pokemon.ability.priority}, index=${pokemon.ability.index}, forced=${pokemon.ability.forced})")
        return if (failed) result.styled { it.withColor(0xFF0000) } else result.styled { it.withColor(0x00FF00) }
    }

    private fun testAbilityCapsule(): Text {
        val pokemon = PokemonProperties.parse("rattata").create()
        val failed = !AbilityChanger.COMMON_ABILITY.performChange(pokemon)
        val symbol = if (failed) "✖" else "✔"
        val result =
            Text.literal(" $symbol Rattata capsule Ability(name=${pokemon.ability.name}, priority=${pokemon.ability.priority}, index=${pokemon.ability.index}, forced=${pokemon.ability.forced})")
        return if (failed) result.styled { it.withColor(0xFF0000) } else result.styled { it.withColor(0x00FF00) }
    }

    private fun testAbilityPatch(): Text {
        val pokemon = PokemonProperties.parse("magikarp ha=true").create()
        val failed = AbilityChanger.HIDDEN_ABILITY.performChange(pokemon)
        val symbol = if (failed) "✖" else "✔"
        val result =
            Text.literal(" $symbol Magikarp patch Ability(name=${pokemon.ability.name}, priority=${pokemon.ability.priority}, index=${pokemon.ability.index}, forced=${pokemon.ability.forced})")
        return if (failed) result.styled { it.withColor(0xFF0000) } else result.styled { it.withColor(0x00FF00) }
    }

    private fun testCodecOutput(context: CommandContext<ServerCommandSource>) {
        val pokemon = context.source.playerOrThrow.party().get(0) ?: Pokemon()
        pokemon.nickname = pokemon.species.translatedName
            .styled {
                it.withColor(pokemon.form.primaryType.hue)
                    .withBold(true)
            }
        val jsonElement = Pokemon.CODEC.encodeStart(JsonOps.INSTANCE, pokemon).orThrow
        context.source.sendMessage(Text.literal(jsonElement.toString()))
    }
}