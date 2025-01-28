package com.cobblemon.mdks.fabric;

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.pokemon.Species;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

import static net.minecraft.commands.Commands.literal;

public class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("test").executes(context -> {
                Species species = PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocation.tryParse("cobblemon:bidoof"));
                context.getSource().sendSystemMessage(
                        Component.literal("Got species: ")
                                .withStyle(Style.EMPTY.withColor(0x03e3fc))
                                .append(species.getTranslatedName())
                );

                return 0;
            }));
        });
    }
}
