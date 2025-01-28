package com.cobblemon.yajatkaul.megamons;

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.pokemon.Species;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

import static net.minecraft.commands.Commands.literal;

public final class ExampleCommandRegistry {

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection environment) {
        dispatcher.register(literal("test").executes(ctx -> {
            Species species = PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocation.tryParse("cobblemon:bidoof"));
            ctx.getSource().sendSystemMessage(
                    Component.literal("Got species: ")
                            .withStyle(Style.EMPTY.withColor(0x03e3fc))
                            .append(species.getTranslatedName())
            );

            return 0;
        }));
    }

}
