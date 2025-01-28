package com.cobblemon.mdks.fabric

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.util.cobblemonResource
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import net.minecraft.commands.CommandSourceStack
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style

/**
 * With Kotlin, the Entrypoint can be defined in numerous ways. This is showcased on Fabrics' Github:
 * https://github.com/FabricMC/fabric-language-kotlin#entrypoint-samples
 */
object FabricMod : ModInitializer {

    override fun onInitialize() {

        CommandRegistrationCallback.EVENT.register { dispatcher, registryAccess, environment ->

            literal<CommandSourceStack>("test").executes { context ->

                // We can utilise Kotlin methods in Cobblemon to avoid having to write "ResourceLocation.of("cobblemon:x")
                // every time
                val species = PokemonSpecies.getByIdentifier(cobblemonResource("bidoof"))!!

                context.source.sendSystemMessage(
                    Component.literal("Got Species: ")
                        .withStyle(Style.EMPTY.withColor(0x03e3fc))
                        .append(species.translatedName)
                )

                0
            }.register(dispatcher)
        }

    }

}

// We can write extension functions to reduce nesting in our command logic if we wanted to
fun LiteralArgumentBuilder<CommandSourceStack>.register(dispatcher: CommandDispatcher<CommandSourceStack>) {
    dispatcher.register(this)
}