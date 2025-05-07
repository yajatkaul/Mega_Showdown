package com.cobblemon.yajatkaul.mega_showdown.commands;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.pc.PCStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class MegaCommands {
    public static void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("msdopreset")
                .requires(source -> source.hasPermission(2)) // Requires OP permission level (2)
                .executes(context -> executeReset(context.getSource().getPlayer())) // Self execution
                .then(Commands.argument("player", EntityArgument.player()) // Add player argument
                        .executes(context -> executeReset(EntityArgument.getPlayer(context, "player"))))); // Execute on specified player

        event.getDispatcher().register(Commands.literal("msdresetlock")
                .requires(source -> source.hasPermission(0)) // Requires no OP permission level (0)
                .executes(context -> executeResetCommon(context.getSource().getPlayer()))); // Self execution
    }

    private static int executeReset(Player player) {
        player.removeData(DataManage.MEGA_DATA);
        player.removeData(DataManage.MEGA_POKEMON);
        player.removeData(DataManage.PRIMAL_DATA);
        player.removeData(DataManage.PRIMAL_POKEMON);

        // Send success message to the command source
        player.sendSystemMessage(Component.translatable("message.mega_showdown.reset_completed"));
        return 1;
    }

    private static int executeResetCommon(Player player) {
        player.removeData(DataManage.MEGA_DATA);
        player.removeData(DataManage.MEGA_POKEMON);
        player.removeData(DataManage.PRIMAL_DATA);
        player.removeData(DataManage.PRIMAL_POKEMON);

        PCStore storge = Cobblemon.INSTANCE.getStorage().getPC((ServerPlayer) player);
        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);

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

        // Send success message to the command source
        player.sendSystemMessage(Component.translatable("message.mega_showdown.reset_completed"));
        return 1;
    }
}
