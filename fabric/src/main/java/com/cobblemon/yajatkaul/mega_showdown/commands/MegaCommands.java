package com.cobblemon.yajatkaul.mega_showdown.commands;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
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
            dispatcher.register(CommandManager.literal("msdopreset")
                    .requires(source -> source.hasPermissionLevel(2)) // Requires OP level 2
                    .executes(context -> executeReset(context.getSource().getPlayer())) // No args = self
                    .then(CommandManager.argument("player", EntityArgumentType.player()) // Add player argument
                            .executes(context -> executeReset(EntityArgumentType.getPlayer(context, "player")))
                    ));
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("msdresetlock")
                .requires(source -> source.hasPermissionLevel(0)).executes(context -> executeResetCommon(context.getSource().getPlayer()))));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("msdresetmega")
                    .requires(source -> source.hasPermissionLevel(0)).executes(context -> executeResetMega(context.getSource().getPlayer())));
        });
    }

    private static int executeReset(ServerPlayerEntity player) {
        player.removeAttached(DataManage.MEGA_DATA);
        player.removeAttached(DataManage.MEGA_POKEMON);
        player.removeAttached(DataManage.PRIMAL_DATA);
        player.removeAttached(DataManage.PRIMAL_POKEMON);
        player.sendMessage(Text.translatable("message.mega_showdown.reset_completed"));
        return 1;
    }

    private static int executeResetCommon(PlayerEntity player) {
        player.removeAttached(DataManage.MEGA_DATA);
        player.removeAttached(DataManage.MEGA_POKEMON);
        player.removeAttached(DataManage.PRIMAL_DATA);
        player.removeAttached(DataManage.PRIMAL_POKEMON);

        PCStore storge = Cobblemon.INSTANCE.getStorage().getPC((ServerPlayerEntity) player);
        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);

        for (Pokemon pokemon: storge){
            if(pokemon.getAspects().contains("mega") || pokemon.getAspects().contains("mega_y") || pokemon.getAspects().contains("mega_x")){
                new StringSpeciesFeature("mega_evolution", "none").apply(pokemon);
            }
            if(pokemon.getAspects().contains("primal")){
                new StringSpeciesFeature("reversion_state", "standard").apply(pokemon);
            }
        }

        for (Pokemon pokemon: party){
            if(pokemon.getAspects().contains("mega") || pokemon.getAspects().contains("mega_y") || pokemon.getAspects().contains("mega_x")){
                new StringSpeciesFeature("mega_evolution", "none").apply(pokemon);
            }
            if(pokemon.getAspects().contains("primal")){
                new StringSpeciesFeature("reversion_state", "standard").apply(pokemon);
            }
        }

        player.sendMessage(Text.translatable("message.mega_showdown.reset_completed"));
        return 1;
    }

    private static int executeResetMega(PlayerEntity player) {
        player.removeAttached(DataManage.MEGA_DATA);
        player.removeAttached(DataManage.MEGA_POKEMON);

        PCStore storge = Cobblemon.INSTANCE.getStorage().getPC((ServerPlayerEntity) player);
        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);

        for (Pokemon pokemon: storge){
            if(pokemon.getAspects().contains("mega") || pokemon.getAspects().contains("mega_y") || pokemon.getAspects().contains("mega_x")){
                new StringSpeciesFeature("mega_evolution", "none").apply(pokemon);
            }
        }

        for (Pokemon pokemon: party){
            if(pokemon.getAspects().contains("mega") || pokemon.getAspects().contains("mega_y") || pokemon.getAspects().contains("mega_x")){
                new StringSpeciesFeature("mega_evolution", "none").apply(pokemon);
            }
        }

        player.sendMessage(Text.translatable("message.mega_showdown.reset_completed"));
        return 1;
    }
}