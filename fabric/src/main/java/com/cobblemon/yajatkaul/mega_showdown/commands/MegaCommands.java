package com.cobblemon.yajatkaul.mega_showdown.commands;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.pc.PCStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class MegaCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("megareset")
                    .requires(source -> source.hasPermissionLevel(2)) // Requires OP level 2
                    .executes(context -> executeReset(context.getSource().getPlayer())) // No args = self
                    .then(CommandManager.argument("player", EntityArgumentType.player()) // Add player argument
                            .executes(context -> executeReset(EntityArgumentType.getPlayer(context, "player")))
                    ));
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("megaresetcommon")
                    .requires(source -> source.hasPermissionLevel(0)).executes(context -> executeResetCommon(context.getSource().getPlayer())));
        });
    }

    private static int executeReset(ServerPlayerEntity player) {
        player.removeAttached(DataManage.MEGA_DATA);
        player.removeAttached(DataManage.MEGA_POKEMON);
        player.sendMessage(Text.literal("Reset completed!"));
        return 1;
    }

    private static int executeResetCommon(PlayerEntity player) {
        player.removeAttached(DataManage.MEGA_DATA);
        player.removeAttached(DataManage.MEGA_POKEMON);

        PCStore storge = Cobblemon.INSTANCE.getStorage().getPC((ServerPlayerEntity) player);
        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);

        for (Pokemon pokemon: storge){
            new FlagSpeciesFeature("mega", false).apply(pokemon);
            new FlagSpeciesFeature("mega-x", false).apply(pokemon);
            new FlagSpeciesFeature("mega-y", false).apply(pokemon);
        }

        for (Pokemon pokemon: party){
            new FlagSpeciesFeature("mega", false).apply(pokemon);
            new FlagSpeciesFeature("mega-x", false).apply(pokemon);
            new FlagSpeciesFeature("mega-y", false).apply(pokemon);
        }

        // Send success message to the command source
        player.sendMessage(Text.literal("Reset completed!"));
        return 1;
    }
}