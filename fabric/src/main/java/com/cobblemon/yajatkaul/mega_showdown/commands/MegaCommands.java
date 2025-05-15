package com.cobblemon.yajatkaul.mega_showdown.commands;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.pc.PCStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownCustomsConfig;
import com.cobblemon.yajatkaul.mega_showdown.config.structure.*;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.configActions.ConfigResults;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.MSDCustomPacket;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class MegaCommands {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("msdopreset")
                .requires(source -> source.hasPermissionLevel(2)) // Requires OP level 2
                .executes(context -> executeReset(context.getSource().getPlayer())) // No args = self
                .then(CommandManager.argument("player", EntityArgumentType.player()) // Add player argument
                        .executes(context -> executeReset(EntityArgumentType.getPlayer(context, "player")))
                )));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("msdresetlock")
                .requires(source -> source.hasPermissionLevel(0)).executes(context -> executeResetCommon(context.getSource().getPlayer()))));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("msdresetmega")
                .requires(source -> source.hasPermissionLevel(0)).executes(context -> executeResetMega(context.getSource().getPlayer()))));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("msd")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(CommandManager.literal("reload")
                            .executes(context -> reloadCustomConfig(context.getSource()))
                    )
                    .then(CommandManager.literal("give")
                            .then(CommandManager.argument("player", EntityArgumentType.player())
                                    .then(CommandManager.argument("item", StringArgumentType.word())
                                            .suggests((context, builder) -> {
                                                for (String item : VALID_ITEMS) {
                                                    builder.suggest(item);
                                                }
                                                return builder.buildFuture();
                                            })
                                            // version with no count
                                            .executes(context -> {
                                                ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                                                String item = StringArgumentType.getString(context, "item");
                                                return executeGive(player, item, 1);
                                            })
                                            // version with count
                                            .then(CommandManager.argument("count", IntegerArgumentType.integer(1))
                                                    .executes(context -> {
                                                        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                                                        String item = StringArgumentType.getString(context, "item");
                                                        int count = IntegerArgumentType.getInteger(context, "count");
                                                        return executeGive(player, item, count);
                                                    })
                                            )
                                    )
                            )
                    )
            );
        });
    }

    public static final List<String> VALID_ITEMS = new ArrayList<>();

    private static int executeGive(ServerPlayerEntity player, String item, int count) {
        //MEGA
        for(MegaItem pokemon: ShowdownCustomsConfig.megaItems){
            if(pokemon.msd_id.equals(item)){
                item = pokemon.item_id;
                if (VALID_ITEMS.contains(item)) {
                    player.sendMessage(Text.literal("Invalid item: " + item).formatted(Formatting.RED), false);
                    return 0;
                }
                String[] itemId = item.split(":");
                Identifier msdItemId = Identifier.of(itemId[0], itemId[1]);
                Item msdItem = Registries.ITEM.get(msdItemId);
                ItemStack stack = new ItemStack(msdItem, count);
                stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(pokemon.custom_model_data));
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(pokemon.item_name));
                List<Text> lore = new ArrayList<>();
                for(String line: pokemon.item_description){
                    lore.add(Text.translatable(line));
                }
                stack.set(DataComponentTypes.LORE, new LoreComponent(lore));
                player.giveItemStack(stack);
                player.sendMessage(Text.literal("You received: " + item).formatted(Formatting.GREEN), false);

                return 1;
            }
        }

        //HELD ITEMS
        for(HeldItem items: ShowdownCustomsConfig.heldItems){
            if(items.msd_id.equals(item)){
                item = items.item_id;
                if (VALID_ITEMS.contains(item)) {
                    player.sendMessage(Text.literal("Invalid item: " + item).formatted(Formatting.RED), false);
                    return 0;
                }
                String[] itemId = item.split(":");
                Identifier msdItemId = Identifier.of(itemId[0], itemId[1]);
                Item msdItem = Registries.ITEM.get(msdItemId);
                ItemStack stack = new ItemStack(msdItem, count);
                stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(items.custom_model_data));
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(items.item_name));
                List<Text> lore = new ArrayList<>();
                for(String line: items.item_description){
                    lore.add(Text.translatable(line));
                }
                stack.set(DataComponentTypes.LORE, new LoreComponent(lore));
                player.giveItemStack(stack);
                player.sendMessage(Text.literal("You received: " + item).formatted(Formatting.GREEN), false);

                return 1;
            }
        }

        //FORME CHANGE
        for(FormeChange items: ShowdownCustomsConfig.formeChange){
            if(items.msd_id.equals(item)){
                item = items.item_id;
                if (VALID_ITEMS.contains(item)) {
                    player.sendMessage(Text.literal("Invalid item: " + item).formatted(Formatting.RED), false);
                    return 0;
                }
                String[] itemId = item.split(":");
                Identifier msdItemId = Identifier.of(itemId[0], itemId[1]);
                Item msdItem = Registries.ITEM.get(msdItemId);
                ItemStack stack = new ItemStack(msdItem, count);
                stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(items.custom_model_data));
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(items.item_name));
                List<Text> lore = new ArrayList<>();
                for(String line: items.item_description){
                    lore.add(Text.translatable(line));
                }
                stack.set(DataComponentTypes.LORE, new LoreComponent(lore));
                player.giveItemStack(stack);
                player.sendMessage(Text.literal("You received: " + item).formatted(Formatting.GREEN), false);

                return 1;
            }
        }

        //FUSIONS
        for(Fusion fusion: ShowdownCustomsConfig.fusionItems){
            if(fusion.msd_id.equals(item)){
                item = fusion.item_id;
                if (VALID_ITEMS.contains(item)) {
                    player.sendMessage(Text.literal("Invalid item: " + item).formatted(Formatting.RED), false);
                    return 0;
                }
                String[] itemId = item.split(":");
                Identifier msdItemId = Identifier.of(itemId[0], itemId[1]);
                Item msdItem = Registries.ITEM.get(msdItemId);
                ItemStack stack = new ItemStack(msdItem, count);
                stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(fusion.custom_model_data));
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(fusion.item_name));
                List<Text> lore = new ArrayList<>();
                for(String line: fusion.item_description){
                    lore.add(Text.translatable(line));
                }
                stack.set(DataComponentTypes.LORE, new LoreComponent(lore));
                player.giveItemStack(stack);
                player.sendMessage(Text.literal("You received: " + item).formatted(Formatting.GREEN), false);

                return 1;
            }
        }

        //KEY ITEMS
        for(KeyItems keyItems: ShowdownCustomsConfig.keyItems){
            if(keyItems.msd_id.equals(item)){
                item = keyItems.item_id;
                if (VALID_ITEMS.contains(item)) {
                    player.sendMessage(Text.literal("Invalid item: " + item).formatted(Formatting.RED), false);
                    return 0;
                }
                String[] itemId = item.split(":");
                Identifier msdItemId = Identifier.of(itemId[0], itemId[1]);
                Item msdItem = Registries.ITEM.get(msdItemId);
                ItemStack stack = new ItemStack(msdItem, count);
                stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(keyItems.custom_model_data));
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(keyItems.item_name));
                List<Text> lore = new ArrayList<>();
                for(String line: keyItems.item_description){
                    lore.add(Text.translatable(line));
                }
                stack.set(DataComponentTypes.LORE, new LoreComponent(lore));
                player.giveItemStack(stack);
                player.sendMessage(Text.literal("You received: " + item).formatted(Formatting.GREEN), false);

                return 1;
            }
        }
        return 0;
    }

    private static int reloadCustomConfig(ServerCommandSource source){
        ConfigResults.registerCustomShowdown();

        MinecraftServer server = source.getServer();
        Iterable<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();

        MSDCustomPacket packet = new MSDCustomPacket(
                ShowdownCustomsConfig.fusionItems,
                ShowdownCustomsConfig.formeChange,
                ShowdownCustomsConfig.heldItems,
                ShowdownCustomsConfig.megaItems,
                ShowdownCustomsConfig.gmax,
                ShowdownCustomsConfig.keyItems
        );

        for (ServerPlayerEntity player : players) {
            MSDCustomPacket.sendToClient(player, packet);
        }
        return 1;
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

        return 1;
    }
}