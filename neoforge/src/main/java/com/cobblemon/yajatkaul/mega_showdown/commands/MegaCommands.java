package com.cobblemon.yajatkaul.mega_showdown.commands;

import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public class MegaCommands {
    public static void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("megareset")
                .requires(source -> source.hasPermission(2)) // Requires OP permission level (2)
                .executes(context -> executeReset(context.getSource().getPlayer())) // Self execution
                .then(Commands.argument("player", EntityArgument.player()) // Add player argument
                        .executes(context -> executeReset(EntityArgument.getPlayer(context, "player"))))); // Execute on specified player
    }

    private static int executeReset(Player player) {
        player.removeData(DataManage.MEGA_DATA);
        player.removeData(DataManage.MEGA_POKEMON);

        // Send success message to the command source
        player.sendSystemMessage(Component.literal("Reset completed!"));
        return 1;
    }
}
