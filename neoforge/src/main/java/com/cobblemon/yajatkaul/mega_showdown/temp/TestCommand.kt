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
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.server.level.ServerPlayer
import java.io.File
import java.io.PrintWriter
import com.cobblemon.mod.common.CobblemonEntities
import com.cobblemon.mod.common.CobblemonNetwork.sendPacket
import com.cobblemon.mod.common.api.abilities.Abilities
import com.cobblemon.mod.common.api.text.green
import com.cobblemon.mod.common.api.text.red
import com.cobblemon.mod.common.entity.npc.NPCBattleActor
import com.cobblemon.mod.common.net.messages.client.trade.TradeStartedPacket
import com.mojang.brigadier.Command
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.AABB

@Suppress("unused")
object TestCommand {

    fun register(dispatcher: CommandDispatcher<CommandSourceStack>) {
        val command = Commands.literal("multitry")
            .requires { it.hasPermission(4) }
            .executes(::execute)
        dispatcher.register(command)
    }

    @Suppress("SameReturnValue")
    private fun execute(context: CommandContext<CommandSourceStack>): Int {
        if (context.source.entity !is ServerPlayer) {
            return Command.SINGLE_SUCCESS
        }

        try {
            val player = context.source.player as ServerPlayer
            val npcs = player.level().getEntities(player, player.boundingBox.inflate(20.0)) { it is NPCEntity }
                .map { NPCBattleActorTemp(it as NPCEntity, it.getPartyForChallenge(listOf(player))!!, 0) }
                .sortedBy { player.distanceToSqr(it.entity) }
            val plyr = player.level().getEntities(player, player.boundingBox.inflate(20.0)) { it is Player }

            val playerTeam = player.party().toBattleTeam()
            val playerActor = PlayerBattleActor(player.uuid, playerTeam)
            //val player2 = plyr[0] as ServerPlayerEntity
            //val playerTeam2 = player2.party().toBattleTeam()
            //val playerActor2 = PlayerBattleActor(plyr[0].uuid, playerTeam2)
            //
            BattleRegistry.startBattle(
                battleFormat = BattleFormat.GEN_9_DOUBLES,
                side1 = BattleSide(playerActor),
                side2 = BattleSide(npcs[1])
            ).ifSuccessful {
                it.battlePartyStores.add(player.party())
            }
        } catch (e: Exception) {
            Cobblemon.LOGGER.error(e)
        }
        return Command.SINGLE_SUCCESS
    }

    var trade: ActiveTrade? = null
    var lastDebugId = 0

    private fun testClosestBattle(context: CommandContext<CommandSourceStack>) {
        val player = context.source.playerOrException
        val cloneTeam = player.party().toBattleTeam(true)
        cloneTeam.forEach { it.effectedPokemon.level = 100 }
        val scanBox = AABB.ofSize(player.position(), 9.0, 9.0, 9.0)
        val results = player.level().getEntities(CobblemonEntities.POKEMON, scanBox) { entityPokemon -> entityPokemon.pokemon.isWild() }
        val pokemonEntity = results.firstOrNull()
        if (pokemonEntity == null) {
            context.source.sendFailure(Component.literal("Cannot find any wild Pokémon in a 9x9x9 area"))
            return
        }
        BattleRegistry.startBattle(
            BattleFormat.GEN_9_SINGLES,
            BattleSide(PlayerBattleActor(player.uuid, cloneTeam)),
            BattleSide(PokemonBattleActor(pokemonEntity.pokemon.uuid, BattlePokemon(pokemonEntity.pokemon), Cobblemon.config.defaultFleeDistance))
        )
    }

    private fun testTrade(playerEntity: ServerPlayer) {
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
        playerEntity.sendPacket(TradeStartedPacket(trade.player2.uuid, trade.player2.name.copy(), trade.player2.party.mapNullPreserving(TradeStartedPacket::TradeablePokemon)))

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

        val currentDebugId = 0 // Change this number to some other number and hot reload when you want the later code block to run once.

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
                val posY = cols[index+1].toFloat()
                val posZ = cols[index+2].toFloat()
                val rotX = cols[index+3].toFloat()
                val rotY = cols[index+4].toFloat()
                val rotZ = cols[index+5].toFloat()

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

//    private fun testParticles(context: CommandContext<ServerCommandSource>) {
//        val file = File("particle.particle.json")
//        val effect = SnowstormParticleReader.loadEffect(GsonBuilder().create().fromJson<JsonObject>(file.readText()))
//
//        val player = context.source.entity as ServerPlayerEntity
//        val position = player.pos
//        val pkt = SpawnSnowstormParticlePacket(effect, position, 0F, 0F)
//        player.sendPacket(pkt)
//    }

//    private fun extractMovesData() {
//        val ctx = GraalShowdown.context
//        ctx.eval("js", """
//                const ShowdownMoves = require('pokemon-showdown/data/moves');
//            """.trimIndent())
//        val moves = ctx.getBindings("js").getMember("ShowdownMoves").getMember("Moves")
//        val gson = GsonBuilder().setPrettyPrinting().create()
//        for (moveName in moves.memberKeys) {
//            try {
//                val value = moves.getMember(moveName)
//                val obj = JsonObject()
//                obj.addProperty("name", moveName)
//                obj.addProperty("type", value.getMember("type").asString())
//                obj.addProperty("damageCategory", value.getMember("category").asString())
//                obj.addProperty("target", value.getMember("target").asString())
//                obj.addProperty("power", value.getMember("basePower").asInt())
//                obj.addProperty("accuracy", value.getMember("accuracy").let { if (it.isBoolean) -1F else it.asFloat() })
//                obj.addProperty("pp", value.getMember("pp").asInt())
//                obj.addProperty("priority", value.getMember("priority").asInt())
//                if (value.hasMember("secondary")) {
//                    val secondary = value.getMember("secondary")
//                    if (secondary.hasMember("chance")) {
//                        obj.addProperty("effectChance", secondary.getMember("chance").asInt())
//                    }
//                }
//                val file = File("outputmoves").also { it.mkdir() }
//                val pw = PrintWriter(File(file, "$moveName.json"))
//                pw.write(gson.toJson(obj))
//                pw.close()
//            } catch (e: Exception) {
//                println("Issue when converting $moveName")
//                e.printStackTrace()
//            }
//        }
//    }
//
//    private fun extractAbilitiesData() {
//        val ctx = GraalShowdown.context
//        ctx.eval("js", """
//            const ShowdownAbilities = require('pokemon-showdown/data/abilities');
//        """.trimIndent())
//        val abilities = ctx.getBindings("js").getMember("ShowdownAbilities").getMember("Abilities")
//        val gson = GsonBuilder().setPrettyPrinting().create()
//        for (abilityName in abilities.memberKeys) {
//            try {
//                val obj = JsonObject()
//                obj.addProperty("name", abilityName)
//                obj.addProperty("displayName", "cobblemon.ability.$abilityName")
//                obj.addProperty("description", "cobblemon.ability.$abilityName.desc")
//                val file = File("outputabilities").also { it.mkdir() }
//                val pw = PrintWriter(File(file, "$abilityName.json"))
//                pw.write(gson.toJson(obj))
//                pw.close()
//            } catch (e: Exception) {
//                println("Issue when converting $abilityName")
//                e.printStackTrace()
//            }
//        }
//    }

    private fun testAbilitiesBetweenEvolution(context: CommandContext<CommandSourceStack>) {
        val results = Component.literal("Ability test results (Assumed default assets)")
            .append(Component.literal("\n"))
            .append(this.testHiddenAbilityThroughoutEvolutions())
            .append(Component.literal("\n"))
            .append(this.testMiddleStageSingleAbility())
            .append(Component.literal("\n"))
            .append(this.testForcedAbility())
            .append(Component.literal("\n"))
            .append(this.testIllegalAbilityNonForced())
            .append(Component.literal("\n"))
            .append(this.testAbilityCapsule())
            .append(Component.literal("\n"))
            .append(this.testAbilityPatch())
        context.source.sendSystemMessage(results)
    }

    private fun testHiddenAbilityThroughoutEvolutions(): Component {
        // Hidden ability test, Dragonite HA differs from Dratini/Dragonair we need to ensure he keeps that ability until the end
        // Skip Dratini cause same HA irrelevant for this test
        val pokemon = PokemonProperties.parse("dragonair level=${Cobblemon.config.maxPokemonLevel} hiddenability=true").create()
        val dragonite = pokemon.evolutions.firstOrNull() ?: return Component.literal("✖ Failed to find Dragonair » Dragonite evolution").red()
        dragonite.evolutionMethod(pokemon)
        val failed = pokemon.ability.index != 0 || pokemon.ability.priority != Priority.LOW || pokemon.ability.forced
        val symbol = if (failed) "✖" else "✔"
        val result = Component.literal(" $symbol Dratini line final Ability(name=${pokemon.ability.name}, priority=${pokemon.ability.priority}, index=${pokemon.ability.index}, forced=${pokemon.ability.forced})")
        return if (failed) result.red() else result.green()
    }

    private fun testMiddleStageSingleAbility(): Component {
        val pokemon = PokemonProperties.parse("scatterbug level=${Cobblemon.config.maxPokemonLevel} ability=compoundeyes").create()
        val spewpa = pokemon.evolutions.firstOrNull() ?: return Component.literal("✖ Failed to find Scatterbug » Spewpa evolution").red()
        spewpa.evolutionMethod(pokemon)
        val vivillon = pokemon.evolutions.firstOrNull() ?: return Component.literal("✖ Failed to find Spewpa » Vivillon evolution").red()
        vivillon.evolutionMethod(pokemon)
        val failed = pokemon.ability.index != 1 || pokemon.ability.priority != Priority.LOWEST || pokemon.ability.forced
        val symbol = if (failed) "✖" else "✔"
        val result = Component.literal(" $symbol Scatterbug line final Ability(name=${pokemon.ability.name}, priority=${pokemon.ability.priority}, index=${pokemon.ability.index}, forced=${pokemon.ability.forced})")
        return if (failed) result.red() else result.green()
    }

    private fun testForcedAbility(): Component {
        val pokemon = PokemonProperties.parse("magikarp level=${Cobblemon.config.maxPokemonLevel} ability=adaptability").create()
        val gyarados = pokemon.evolutions.firstOrNull() ?: return Component.literal("✖ Failed to find Magikarp » Gyarados evolution").red()
        gyarados.evolutionMethod(pokemon)
        val failed = !pokemon.ability.forced || pokemon.ability.template.name != "adaptability"
        val symbol = if (failed) "✖" else "✔"
        val result = Component.literal(" $symbol Magikarp line forced Ability(name=${pokemon.ability.name}, priority=${pokemon.ability.priority}, index=${pokemon.ability.index}, forced=${pokemon.ability.forced})")
        return if (failed) result.red() else result.green()
    }

    private fun testIllegalAbilityNonForced(): Component {
        val pokemon = PokemonProperties.parse("rattata").create()
        pokemon.updateAbility(Abilities.getOrException("adaptability").create(false))
        val failed = !pokemon.ability.forced
        val symbol = if (failed) "✖" else "✔"
        val result = Component.literal(" $symbol Rattata illegal non-forced (name=${pokemon.ability.name}, priority=${pokemon.ability.priority}, index=${pokemon.ability.index}, forced=${pokemon.ability.forced})")
        return if (failed) result.red() else result.green()
    }

    private fun testAbilityCapsule(): Component {
        val pokemon = PokemonProperties.parse("rattata").create()
        val failed = !AbilityChanger.COMMON_ABILITY.performChange(pokemon)
        val symbol = if (failed) "✖" else "✔"
        val result = Component.literal(" $symbol Rattata capsule Ability(name=${pokemon.ability.name}, priority=${pokemon.ability.priority}, index=${pokemon.ability.index}, forced=${pokemon.ability.forced})")
        return if (failed) result.red() else result.green()
    }

    private fun testAbilityPatch(): Component {
        val pokemon = PokemonProperties.parse("magikarp ha=true").create()
        // It shouldn't change
        val failed = AbilityChanger.HIDDEN_ABILITY.performChange(pokemon)
        val symbol = if (failed) "✖" else "✔"
        val result = Component.literal(" $symbol Magikarp patch Ability(name=${pokemon.ability.name}, priority=${pokemon.ability.priority}, index=${pokemon.ability.index}, forced=${pokemon.ability.forced})")
        return if (failed) result.red() else result.green()
    }

    private fun testCodecOutput(context: CommandContext<CommandSourceStack>) {
        val pokemon = context.source.playerOrException.party().get(0) ?: Pokemon()
        pokemon.nickname = pokemon.species.translatedName
            .withStyle {
                it.withColor(pokemon.form.primaryType.hue)
                    .withBold(true)
            }
        val jsonElement = Pokemon.CODEC.encodeStart(JsonOps.INSTANCE, pokemon).orThrow
        context.source.sendSystemMessage(Component.literal(jsonElement.toString()))
    }

}
