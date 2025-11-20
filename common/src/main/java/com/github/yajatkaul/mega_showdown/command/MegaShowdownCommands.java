package com.github.yajatkaul.mega_showdown.command;

import com.github.yajatkaul.mega_showdown.components.MegaShowdownDataComponents;
import com.github.yajatkaul.mega_showdown.datapack.MegaShowdownDatapackRegister;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class MegaShowdownCommands {
    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection environment) {
        dispatcher.register(literal("msd")
                .then(literal("apply")
                        .requires(req -> req.hasPermission(4))
                        .then(argument("type", StringArgumentType.string())
                                .suggests(((cxt, builder) -> {
                                    builder.suggest("solo_fusion");
                                    builder.suggest("mega");
                                    builder.suggest("showdown_item");
                                    builder.suggest("held_form_change");
                                    builder.suggest("du_fusion");
                                    builder.suggest("form_change_interact");
                                    builder.suggest("form_change_toggle_interact");
                                    return builder.buildFuture();
                                }))
                                .then(argument("resource_id", StringArgumentType.greedyString())
                                        .executes(MegaShowdownCommands::applyComponent)
                                        .suggests(((cxt, builder) -> {
                                            String type = StringArgumentType.getString(cxt, "type");
                                            switch (type) {
                                                case "solo_fusion":
                                                    for (ResourceLocation location : MegaShowdownDatapackRegister.SOLO_FUSION_REGISTRY.keySet()) {
                                                        builder.suggest(String.valueOf(location));
                                                    }
                                                case "mega":
                                                    for (ResourceLocation location : MegaShowdownDatapackRegister.MEGA_REGISTRY.keySet()) {
                                                        builder.suggest(String.valueOf(location));
                                                    }
                                                case "held_form_change":
                                                    for (ResourceLocation location : MegaShowdownDatapackRegister.HELD_ITEM_FORM_CHANGE_REGISTRY.keySet()) {
                                                        builder.suggest(String.valueOf(location));
                                                    }
                                                case "du_fusion":
                                                    for (ResourceLocation location : MegaShowdownDatapackRegister.DU_FUSION_REGISTRY.keySet()) {
                                                        builder.suggest(String.valueOf(location));
                                                    }
                                                case "form_change_interact":
                                                    for (ResourceLocation location : MegaShowdownDatapackRegister.FORM_CHANGE_INTERACT_REGISTRY.keySet()) {
                                                        builder.suggest(String.valueOf(location));
                                                    }
                                                case "form_change_toggle_interact":
                                                    for (ResourceLocation location : MegaShowdownDatapackRegister.FORM_CHANGE_TOGGLE_INTERACT_REGISTRY.keySet()) {
                                                        builder.suggest(String.valueOf(location));
                                                    }
                                                case "showdown_item":
                                                    for (ResourceLocation location : MegaShowdownDatapackRegister.SHOWDOWN_ITEM_REGISTRY.keySet()) {
                                                        builder.suggest(String.valueOf(location));
                                                    }
                                            }
                                            return builder.buildFuture();
                                        })))))
        );
    }

    private static int applyComponent(CommandContext<CommandSourceStack> context) {
        String type = StringArgumentType.getString(context, "type");
        String resourceId = StringArgumentType.getString(context, "resource_id");

        ServerPlayer player = context.getSource().getPlayer();

        if (player == null) {
            return 1;
        }

        ItemStack stack = player.getMainHandItem();

        stack.set(MegaShowdownDataComponents.REGISTRY_TYPE_COMPONENT.get(), type);
        stack.set(MegaShowdownDataComponents.RESOURCE_LOCATION_COMPONENT.get(), ResourceLocation.tryParse(resourceId));

        return 0;
    }
}
