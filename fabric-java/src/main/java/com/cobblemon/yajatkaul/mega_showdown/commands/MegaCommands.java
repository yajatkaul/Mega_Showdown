package com.cobblemon.yajatkaul.mega_showdown.commands;

import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
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
    }

    private static int executeReset(ServerPlayerEntity player) {
        player.removeAttached(DataManage.MEGA_DATA);
        player.removeAttached(DataManage.MEGA_POKEMON);
        player.sendMessage(Text.literal("Reset completed!"));
        return 1;
    }
}