package com.cobblemon.yajatkaul.mega_showdown.commands;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.pc.PCStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.*;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class MegaCommands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("msdresetlock")
                .requires(source -> source.hasPermissionLevel(0)).executes(context -> executeResetCommon(context.getSource().getPlayer()))));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("msdresetmega")
                .requires(source -> source.hasPermissionLevel(0)).executes(context -> executeResetMega(context.getSource().getPlayer()))));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("msd")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(CommandManager.literal("give")
                            .then(CommandManager.argument("player", EntityArgumentType.player())
                                    .then(CommandManager.argument("itemtype", StringArgumentType.word())
                                            .suggests((context, builder) -> CommandSource.suggestMatching(
                                                    List.of("mega_stone", "held_item", "showdown_item", "fusion_item", "key_item"),
                                                    builder
                                            ))
                                            .then(CommandManager.argument("item", StringArgumentType.word())
                                                    .suggests((context, builder) -> {
                                                        String type = StringArgumentType.getString(context, "itemtype");
                                                        switch (type) {
                                                            case "mega_stone" -> Utils.megaRegistry.forEach(m -> builder.suggest(m.msd_id()));
                                                            case "held_item" -> Utils.heldItemsRegistry.forEach(i -> builder.suggest(i.msd_id()));
                                                            case "showdown_item" -> Utils.showdownItemRegistry.forEach(i -> builder.suggest(i.msd_id()));
                                                            case "fusion_item" -> Utils.fusionRegistry.forEach(f -> builder.suggest(f.msd_id()));
                                                            case "key_item" -> Utils.keyItemsRegistry.forEach(k -> builder.suggest(k.msd_id()));
                                                        }
                                                        return builder.buildFuture();
                                                    })
                                                    // Without count
                                                    .executes(context -> {
                                                        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                                                        String itemtype = StringArgumentType.getString(context, "itemtype");
                                                        String item = StringArgumentType.getString(context, "item");
                                                        return executeGive(player, itemtype, item, 1);
                                                    })
                                                    // With count
                                                    .then(CommandManager.argument("count", IntegerArgumentType.integer(1))
                                                            .executes(context -> {
                                                                ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
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
        });
    }

    private static int executeGive(ServerPlayerEntity player, String itemtype, String item, int count) {
        switch (itemtype) {
            case "mega_stone" -> {
                for (MegaData pokemon : Utils.megaRegistry) {
                    if (pokemon.msd_id().equals(item)) return giveItem(player, pokemon.item_id(), pokemon.item_name(), pokemon.item_description(), pokemon.custom_model_data(), count);
                }
            }
            case "held_item" -> {
                for (HeldItemData held : Utils.heldItemsRegistry) {
                    if (held.msd_id().equals(item)) return giveItem(player, held.item_id(), held.item_name(), held.item_description(), held.custom_model_data(), count);
                }
            }
            case "showdown_item" -> {
                for (ShowdownItemData sd : Utils.showdownItemRegistry) {
                    if (sd.msd_id().equals(item)) return giveItem(player, sd.item_id(), sd.item_name(), sd.item_description(), sd.custom_model_data(), count);
                }
            }
            case "fusion_item" -> {
                for (FusionData fusion : Utils.fusionRegistry) {
                    if (fusion.msd_id().equals(item)) return giveItem(player, fusion.item_id(), fusion.item_name(), fusion.item_description(), fusion.custom_model_data(), count);
                }
            }
            case "key_item" -> {
                for (KeyItemData key : Utils.keyItemsRegistry) {
                    if (key.msd_id().equals(item)) return giveItem(player, key.item_id(), key.item_name(), key.item_description(), key.custom_model_data(), count);
                }
            }
            default -> {
                player.sendMessage(Text.literal("Unknown item type: " + itemtype).formatted(Formatting.RED), false);
                return 0;
            }
        }

        player.sendMessage(Text.literal("Item not found: " + item).formatted(Formatting.RED), false);
        return 0;
    }

    private static int giveItem(ServerPlayerEntity player, String itemIdStr, String itemName, List<String> description, int modelData, int count) {
        Identifier id = Identifier.tryParse(itemIdStr);
        Item item = Registries.ITEM.get(id);
        ItemStack stack = new ItemStack(item, count);

        stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(modelData));
        stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(itemName));

        List<Text> lore = new ArrayList<>();
        for (String line : description) lore.add(Text.translatable(line));
        stack.set(DataComponentTypes.LORE, new LoreComponent(lore));

        player.giveItemStack(stack);
        player.sendMessage(Text.literal("You received: " + itemIdStr).formatted(Formatting.GREEN), false);
        return 1;
    }

    private static int executeResetCommon(PlayerEntity player) {
        PCStore storge = Cobblemon.INSTANCE.getStorage().getPC((ServerPlayerEntity) player);
        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);

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

        player.sendMessage(Text.translatable("message.mega_showdown.reset_completed"));
        return 1;
    }

    private static int executeResetMega(PlayerEntity player) {
        PCStore storge = Cobblemon.INSTANCE.getStorage().getPC((ServerPlayerEntity) player);
        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);

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