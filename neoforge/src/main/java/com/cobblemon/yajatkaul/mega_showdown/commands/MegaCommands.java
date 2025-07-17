package com.cobblemon.yajatkaul.mega_showdown.commands;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.pc.PCStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.*;
import com.cobblemon.yajatkaul.mega_showdown.temp.TestCommand;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.ItemLore;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.ArrayList;
import java.util.List;

public class MegaCommands {
    public static final List<String> VALID_ITEMS = new ArrayList<>();

    public static void register(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("msdresetlock")
                .requires(source -> source.hasPermission(0)) // Requires no OP permission level (0)
                .executes(context -> executeResetCommon(context.getSource().getPlayer()))); // Self execution

        event.getDispatcher().register(Commands.literal("msdresetmega")
                .requires(source -> source.hasPermission(0)) // Requires no OP permission level (0)
                .executes(context -> executeResetMega(context.getSource().getPlayer()))); // Self execution

        event.getDispatcher().register(
                Commands.literal("msd")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("give")
                                .then(Commands.argument("player", net.minecraft.commands.arguments.EntityArgument.player())
                                        .then(Commands.argument("itemtype", StringArgumentType.word())
                                                .suggests((context, builder) -> SharedSuggestionProvider.suggest(
                                                        List.of("mega_stone", "held_item", "showdown_item", "fusion_item", "key_item"),
                                                        builder
                                                ))
                                                .then(Commands.argument("item", StringArgumentType.word())
                                                        .suggests((context, builder) -> {
                                                            String type = StringArgumentType.getString(context, "itemtype");
                                                            switch (type) {
                                                                case "mega_stone" ->
                                                                        Utils.megaRegistry.forEach(m -> builder.suggest(m.msd_id()));
                                                                case "held_item" ->
                                                                        Utils.heldItemsRegistry.forEach(i -> builder.suggest(i.msd_id()));
                                                                case "showdown_item" ->
                                                                        Utils.showdownItemRegistry.forEach(i -> builder.suggest(i.msd_id()));
                                                                case "fusion_item" ->
                                                                        Utils.fusionRegistry.forEach(f -> builder.suggest(f.msd_id()));
                                                                case "key_item" ->
                                                                        Utils.keyItemsRegistry.forEach(k -> builder.suggest(k.msd_id()));
                                                            }
                                                            return builder.buildFuture();
                                                        })
                                                        .executes(context -> {
                                                            ServerPlayer player = net.minecraft.commands.arguments.EntityArgument.getPlayer(context, "player");
                                                            String itemtype = StringArgumentType.getString(context, "itemtype");
                                                            String item = StringArgumentType.getString(context, "item");
                                                            return executeGive(player, itemtype, item, 1);
                                                        })
                                                        .then(Commands.argument("count", IntegerArgumentType.integer(1))
                                                                .executes(context -> {
                                                                    ServerPlayer player = net.minecraft.commands.arguments.EntityArgument.getPlayer(context, "player");
                                                                    String itemtype = StringArgumentType.getString(context, "itemtype");
                                                                    String item = StringArgumentType.getString(context, "item");
                                                                    int count = IntegerArgumentType.getInteger(context, "count");
                                                                    return executeGive(player, itemtype, item, count);
                                                                })
                                                        )
                                                )
                                        )
                                )
                        )
        );

        TestCommand.INSTANCE.register(event.getDispatcher());
    }

    private static int executeGive(ServerPlayer player, String itemtype, String item, int count) {
        switch (itemtype) {
            case "mega_stone" -> {
                for (MegaData pokemon : Utils.megaRegistry) {
                    if (pokemon.msd_id().equals(item)) {
                        return giveItem(player, pokemon.item_id(), pokemon.item_name(), pokemon.item_description(), pokemon.custom_model_data(), count);
                    }
                }
            }
            case "held_item" -> {
                for (HeldItemData held : Utils.heldItemsRegistry) {
                    if (held.msd_id().equals(item)) {
                        return giveItem(player, held.item_id(), held.item_name(), held.item_description(), held.custom_model_data(), count);
                    }
                }
            }
            case "showdown_item" -> {
                for (ShowdownItemData sd : Utils.showdownItemRegistry) {
                    if (sd.msd_id().equals(item)) {
                        return giveItem(player, sd.item_id(), sd.item_name(), sd.item_description(), sd.custom_model_data(), count);
                    }
                }
            }
            case "fusion_item" -> {
                for (FusionData fusion : Utils.fusionRegistry) {
                    if (fusion.msd_id().equals(item)) {
                        return giveItem(player, fusion.item_id(), fusion.item_name(), fusion.item_description(), fusion.custom_model_data(), count);
                    }
                }
            }
            case "key_item" -> {
                for (KeyItemData key : Utils.keyItemsRegistry) {
                    if (key.msd_id().equals(item)) {
                        return giveItem(player, key.item_id(), key.item_name(), key.item_description(), key.custom_model_data(), count);
                    }
                }
            }
            default -> {
                player.sendSystemMessage(Component.literal("Unknown item type: " + itemtype).withStyle(ChatFormatting.RED));
                return 0;
            }
        }

        player.sendSystemMessage(Component.literal("Item not found: " + item).withStyle(ChatFormatting.RED));
        return 0;
    }


    private static int giveItem(ServerPlayer player, String itemIdStr, String itemName, List<String> description, int modelData, int count) {
        ResourceLocation id = ResourceLocation.tryParse(itemIdStr);
        Item item = BuiltInRegistries.ITEM.get(id);
        ItemStack stack = new ItemStack(item, count);

        stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(modelData));
        stack.set(DataComponents.CUSTOM_NAME, Component.translatable(itemName));

        List<Component> lore = new ArrayList<>();
        for (String line : description) {
            lore.add(Component.translatable(line));
        }
        stack.set(DataComponents.LORE, new ItemLore(lore));

        player.addItem(stack);
        player.sendSystemMessage(Component.literal("You received: " + itemIdStr).withStyle(ChatFormatting.GREEN));
        return 1;
    }

    private static int executeResetCommon(Player player) {
        PCStore storge = Cobblemon.INSTANCE.getStorage().getPC((ServerPlayer) player);
        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);

        for (Pokemon pokemon : storge) {
            if (pokemon.getAspects().contains("mega") || pokemon.getAspects().contains("mega_y") || pokemon.getAspects().contains("mega_x")) {
                new StringSpeciesFeature("mega_evolution", "none").apply(pokemon);
            }
            if (pokemon.getAspects().contains("primal")) {
                new StringSpeciesFeature("reversion_state", "standard").apply(pokemon);
            }
        }

        for (Pokemon pokemon : party) {
            if (pokemon.getAspects().contains("mega") || pokemon.getAspects().contains("mega_y") || pokemon.getAspects().contains("mega_x")) {
                new StringSpeciesFeature("mega_evolution", "none").apply(pokemon);
            }
            if (pokemon.getAspects().contains("primal")) {
                new StringSpeciesFeature("reversion_state", "standard").apply(pokemon);
            }
        }

        // Send success message to the command source
        player.sendSystemMessage(Component.translatable("message.mega_showdown.reset_completed"));
        return 1;
    }

    private static int executeResetMega(Player player) {
        PCStore storge = Cobblemon.INSTANCE.getStorage().getPC((ServerPlayer) player);
        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);

        for (Pokemon pokemon : storge) {
            if (pokemon.getAspects().contains("mega") || pokemon.getAspects().contains("mega_y") || pokemon.getAspects().contains("mega_x")) {
                new StringSpeciesFeature("mega_evolution", "none").apply(pokemon);
            }
        }

        for (Pokemon pokemon : party) {
            if (pokemon.getAspects().contains("mega") || pokemon.getAspects().contains("mega_y") || pokemon.getAspects().contains("mega_x")) {
                new StringSpeciesFeature("mega_evolution", "none").apply(pokemon);
            }
        }

        return 1;
    }
}
