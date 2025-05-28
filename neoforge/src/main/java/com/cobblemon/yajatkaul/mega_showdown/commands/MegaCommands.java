package com.cobblemon.yajatkaul.mega_showdown.commands;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.pc.PCStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.*;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
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
        event.getDispatcher().register(Commands.literal("msdopreset")
                .requires(source -> source.hasPermission(2)) // Requires OP permission level (2)
                .executes(context -> executeReset(context.getSource().getPlayer())) // Self execution
                .then(Commands.argument("player", EntityArgument.player()) // Add player argument
                        .executes(context -> executeReset(EntityArgument.getPlayer(context, "player"))))); // Execute on specified player

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
                                .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("item", StringArgumentType.word())
                                                .suggests((context, builder) -> {
                                                    for (String item : VALID_ITEMS) {
                                                        builder.suggest(item);
                                                    }
                                                    return builder.buildFuture();
                                                })
                                                // No count
                                                .executes(context -> {
                                                    ServerPlayer player = EntityArgument.getPlayer(context, "player");
                                                    String item = StringArgumentType.getString(context, "item");
                                                    return executeGive(player, item, 1);
                                                })
                                                // With count
                                                .then(Commands.argument("count", IntegerArgumentType.integer(1))
                                                        .executes(context -> {
                                                            ServerPlayer player = EntityArgument.getPlayer(context, "player");
                                                            String item = StringArgumentType.getString(context, "item");
                                                            int count = IntegerArgumentType.getInteger(context, "count");
                                                            return executeGive(player, item, count);
                                                        })
                                                )
                                        )
                                )
                        )
        );
    }

    private static int executeGive(ServerPlayer player, String item, int count) {
        //MEGA
        for (MegaData pokemon : Utils.megaRegistry) {
            if (pokemon.msd_id().equals(item)) {
                item = pokemon.item_id();
                if (VALID_ITEMS.contains(item)) {
                    player.sendSystemMessage(Component.literal("Invalid item: " + item).withStyle(style -> style.withColor(ChatFormatting.RED)));
                    return 0;
                }
                String[] itemId = item.split(":");
                ResourceLocation msdItemId = ResourceLocation.fromNamespaceAndPath(itemId[0], itemId[1]);
                Item msdItem = BuiltInRegistries.ITEM.get(msdItemId);
                ItemStack stack = new ItemStack(msdItem, count);
                stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(pokemon.custom_model_data()));
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable(pokemon.item_name()));
                List<Component> lore = new ArrayList<>();
                for (String line : pokemon.item_description()) {
                    lore.add(Component.translatable(line));
                }
                stack.set(DataComponents.LORE, new ItemLore(lore));
                player.addItem(stack);
                player.sendSystemMessage(Component.literal("You received: " + item).withStyle(style -> style.withColor(ChatFormatting.GREEN)));

                return 1;
            }
        }

        //HELD ITEMS
        for (HeldItemData items : Utils.heldItemsRegistry) {
            if (items.msd_id().equals(item)) {
                item = items.item_id();
                if (VALID_ITEMS.contains(item)) {
                    player.sendSystemMessage(Component.literal("Invalid item: " + item).withStyle(style -> style.withColor(ChatFormatting.RED)));
                    return 0;
                }
                String[] itemId = item.split(":");
                ResourceLocation msdItemId = ResourceLocation.fromNamespaceAndPath(itemId[0], itemId[1]);
                Item msdItem = BuiltInRegistries.ITEM.get(msdItemId);
                ItemStack stack = new ItemStack(msdItem, count);
                stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(items.custom_model_data()));
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable(items.item_name()));
                List<Component> lore = new ArrayList<>();
                for (String line : items.item_description()) {
                    lore.add(Component.translatable(line));
                }
                stack.set(DataComponents.LORE, new ItemLore(lore));
                player.addItem(stack);
                player.sendSystemMessage(Component.literal("You received: " + item).withStyle(style -> style.withColor(ChatFormatting.GREEN)));

                return 1;
            }
        }

        //FORME CHANGE
        for (FormChangeData items : Utils.formChangeRegistry) {
            if (items.msd_id().equals(item)) {
                item = items.item_id();
                if (VALID_ITEMS.contains(item)) {
                    player.sendSystemMessage(Component.literal("Invalid item: " + item).withStyle(style -> style.withColor(ChatFormatting.RED)));
                    return 0;
                }
                String[] itemId = item.split(":");
                ResourceLocation msdItemId = ResourceLocation.fromNamespaceAndPath(itemId[0], itemId[1]);
                Item msdItem = BuiltInRegistries.ITEM.get(msdItemId);
                ItemStack stack = new ItemStack(msdItem, count);
                stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(items.custom_model_data()));
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable(items.item_name()));
                List<Component> lore = new ArrayList<>();
                for (String line : items.item_description()) {
                    lore.add(Component.translatable(line));
                }
                stack.set(DataComponents.LORE, new ItemLore(lore));
                player.addItem(stack);
                player.sendSystemMessage(Component.literal("You received: " + item).withStyle(style -> style.withColor(ChatFormatting.GREEN)));

                return 1;
            }
        }

        //FUSIONS
        for (FusionData fusion : Utils.fusionRegistry) {
            if (fusion.msd_id().equals(item)) {
                item = fusion.item_id();
                if (VALID_ITEMS.contains(item)) {
                    player.sendSystemMessage(Component.literal("Invalid item: " + item).withStyle(style -> style.withColor(ChatFormatting.RED)));
                    return 0;
                }
                String[] itemId = item.split(":");
                ResourceLocation msdItemId = ResourceLocation.fromNamespaceAndPath(itemId[0], itemId[1]);
                Item msdItem = BuiltInRegistries.ITEM.get(msdItemId);
                ItemStack stack = new ItemStack(msdItem, count);
                stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(fusion.custom_model_data()));
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable(fusion.item_name()));
                List<Component> lore = new ArrayList<>();
                for (String line : fusion.item_description()) {
                    lore.add(Component.translatable(line));
                }
                stack.set(DataComponents.LORE, new ItemLore(lore));
                player.addItem(stack);
                player.sendSystemMessage(Component.literal("You received: " + item).withStyle(style -> style.withColor(ChatFormatting.GREEN)));

                return 1;
            }
        }

        //KEY ITEMS
        for (KeyItemData keyItems : Utils.keyItemsRegistry) {
            if (keyItems.msd_id().equals(item)) {
                item = keyItems.item_id();
                if (VALID_ITEMS.contains(item)) {
                    player.sendSystemMessage(Component.literal("Invalid item: " + item).withStyle(style -> style.withColor(ChatFormatting.RED)));
                    return 0;
                }
                String[] itemId = item.split(":");
                ResourceLocation msdItemId = ResourceLocation.fromNamespaceAndPath(itemId[0], itemId[1]);
                Item msdItem = BuiltInRegistries.ITEM.get(msdItemId);
                ItemStack stack = new ItemStack(msdItem, count);
                stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(keyItems.custom_model_data()));
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable(keyItems.item_name()));
                List<Component> lore = new ArrayList<>();
                for (String line : keyItems.item_description()) {
                    lore.add(Component.translatable(line));
                }
                stack.set(DataComponents.LORE, new ItemLore(lore));
                player.addItem(stack);
                player.sendSystemMessage(Component.literal("You received: " + item).withStyle(style -> style.withColor(ChatFormatting.GREEN)));

                return 1;
            }
        }
        return 0;
    }

    private static int reloadCustomConfig(CommandSourceStack source) {
        source.getServer().reloadableRegistries();
        Utils.registryLoader(source.registryAccess());

        return 1;
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
        player.removeData(DataManage.MEGA_DATA);
        player.removeData(DataManage.MEGA_POKEMON);

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
