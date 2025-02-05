package com.cobblemon.yajatkaul.mega_showdown.battle;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.events.battles.BattleFaintedEvent;
import com.cobblemon.mod.common.api.events.battles.BattleFledEvent;
import com.cobblemon.mod.common.api.events.battles.BattleStartedPostEvent;
import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.battles.ShowdownActionResponse;
import com.cobblemon.mod.common.battles.ShowdownActionResponseType;
import com.cobblemon.mod.common.battles.ShowdownMoveset;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.client.gui.battle.BattleGUI;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.showdown.ShowdownUtils;
import kotlin.Unit;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.Window;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class BattleHandling {
    public static PokemonBattle battle = null;

    public static Unit getBattleInfo(BattleStartedPostEvent battleStartedPostEvent) {
        battle = battleStartedPostEvent.getBattle();
        return Unit.INSTANCE;
    }

    public static void megaEvoButton(MinecraftClient minecraftClient, Screen screen, int j, int j1) {
        if (screen instanceof BattleGUI gui) {
            PlayerEntity clientPlayer = MinecraftClient.getInstance().player;

            Window window = MinecraftClient.getInstance().getWindow();
            int screenWidth = window.getScaledWidth();
            int screenHeight = window.getScaledHeight();

// Calculate relative positions (as percentages of screen size)
            double relativeX = 0.15;  // 15% from left
            double relativeY = 0.88;  // 70% from top

// Calculate actual position
            int xPos = (int)(screenWidth * relativeX);
            int yPos = (int)(screenHeight * relativeY);

// Calculate responsive size (you can adjust these ratios)
            int buttonWidth = (int)(screenWidth * 0.05);  // 5% of screen width
            int buttonHeight = (int)(screenHeight * 0.095);

            ButtonWidget button = ButtonWidget.builder(Text.literal("M"), buttonWidget -> {
                if(battle == null){
                    return;
                }
                battle.getPlayers().forEach(serverPlayer -> {
                    if (serverPlayer.getUuid().equals(clientPlayer.getUuid())) {
                        for (BattlePokemon battlePokemon : battle.getActor(clientPlayer.getUuid()).getPokemonList()) {
                            if (battlePokemon.getOriginalPokemon().getEntity() == null ||
                                    battlePokemon.getOriginalPokemon().getEntity().getWorld().isClient) {
                                continue; // Skip to the next iteration
                            }

                            Pokemon pokemon = battlePokemon.getOriginalPokemon();
                            Species species = ShowdownUtils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());

                            if (pokemon.getEntity().isBattling() && species == pokemon.getSpecies() &&
                                    (!serverPlayer.getAttached(DataManage.MEGA_DATA) || Config.getInstance().multipleMegas)) {

                                if(Config.getInstance().megaTurns){
                                    List<ShowdownActionResponse> action = Collections.singletonList(new ShowdownActionResponse(ShowdownActionResponseType.FORCE_PASS) {
                                        @NotNull
                                        @Override
                                        public String toShowdownString(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveset) {
                                            return "pass"; // Or whatever the correct format for passing is in your Showdown implementation
                                        }

                                        @Override
                                        public boolean isValid(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveset, boolean forceSwitch) {
                                            return true; // This action should be valid when we want to force a pass
                                        }
                                    });

                                    battle.getActor(serverPlayer).setActionResponses(action);
                                }

                                if (species == ShowdownUtils.getSpecies("charizard")) {
                                    if (pokemon.heldItem().isOf(ModItems.CHARIZARDITE_X)) {
                                        serverPlayer.setAttached(DataManage.MEGA_DATA, true);
                                        serverPlayer.setAttached(DataManage.MEGA_POKEMON, pokemon);

                                        new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                                        new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                                        break;
                                    } else if (pokemon.heldItem().isOf(ModItems.CHARIZARDITE_Y)) {
                                        serverPlayer.setAttached(DataManage.MEGA_DATA, true);
                                        serverPlayer.setAttached(DataManage.MEGA_POKEMON, pokemon);

                                        new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                                        new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                                        break;
                                    }
                                } else if (species == ShowdownUtils.getSpecies("mewtwo")) {
                                    if (pokemon.heldItem().isOf(ModItems.MEWTWONITE_X)) {
                                        serverPlayer.setAttached(DataManage.MEGA_DATA, true);
                                        serverPlayer.setAttached(DataManage.MEGA_POKEMON, pokemon);

                                        new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                                        new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                                        break;
                                    } else if (pokemon.heldItem().isOf(ModItems.MEWTWONITE_Y)) {
                                        serverPlayer.setAttached(DataManage.MEGA_DATA, true);
                                        serverPlayer.setAttached(DataManage.MEGA_POKEMON, pokemon);

                                        new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                                        new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                                        break;
                                    }
                                } else {
                                    serverPlayer.setAttached(DataManage.MEGA_DATA, true);
                                    serverPlayer.setAttached(DataManage.MEGA_POKEMON, pokemon);

                                    new FlagSpeciesFeature("mega", true).apply(pokemon);
                                    break;
                                }
                            } else if (pokemon.getSpecies() == ShowdownUtils.getSpecies("rayquaza")) {
                                boolean found = false;
                                for (int i = 0; i < 4; i++) {
                                    if (pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")) {
                                        serverPlayer.setAttached(DataManage.MEGA_POKEMON, pokemon);
                                        serverPlayer.setAttached(DataManage.MEGA_DATA, true);

                                        if(Config.getInstance().megaTurns){
                                            List<ShowdownActionResponse> action = Collections.singletonList(new ShowdownActionResponse(ShowdownActionResponseType.FORCE_PASS) {
                                                @NotNull
                                                @Override
                                                public String toShowdownString(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveset) {
                                                    return "pass"; // Or whatever the correct format for passing is in your Showdown implementation
                                                }

                                                @Override
                                                public boolean isValid(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveset, boolean forceSwitch) {
                                                    return true; // This action should be valid when we want to force a pass
                                                }
                                            });

                                            battle.getActor(serverPlayer).setActionResponses(action);
                                        }

                                        new FlagSpeciesFeature("mega", true).apply(pokemon);
                                        found = true;
                                        break;
                                    }
                                }
                                if(!found){
                                    serverPlayer.sendMessage(
                                            Text.literal("Rayquaza doesn't have dragonascent").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                                            true
                                    );
                                }
                            } else if (species == pokemon.getSpecies() && serverPlayer.getAttached(DataManage.MEGA_DATA)) {
                                serverPlayer.sendMessage(
                                        Text.literal("You can only have one mega at a time").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                                        true
                                );
                            } else {
                                serverPlayer.sendMessage(
                                        Text.literal("Don't have the correct stone").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                                        true
                                );
                            }
                        }
                    }
                });
            }).position(xPos,yPos).size(buttonWidth,buttonHeight).build();


            if(clientPlayer != null && clientPlayer.getOffHandStack().isOf(ModItems.MEGA_BRACELET.asItem())){
                Screens.getButtons(screen).add(button);
            }
        }
    }

    public static Unit getBattleEndInfo(BattleVictoryEvent battleVictoryEvent) {
        PlayerEntity clientPlayer = MinecraftClient.getInstance().player;

        battle.getPlayers().forEach(serverPlayer -> {
            if (serverPlayer.getUuid().equals(clientPlayer.getUuid())) {
                for (BattlePokemon battlePokemon : battle.getActor(clientPlayer.getUuid()).getPokemonList()) {
                    if (battlePokemon.getOriginalPokemon().getEntity() == null ||
                            battlePokemon.getOriginalPokemon().getEntity().getWorld().isClient) {
                        continue;
                    }

                    Pokemon pokemon = battlePokemon.getOriginalPokemon();

                    List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

                    for (String key : megaKeys) {
                        FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));

                        FlagSpeciesFeature feature = featureProvider.get(pokemon);
                        if(feature != null){
                            boolean enabled = featureProvider.get(pokemon).getEnabled();

                            if (enabled && feature.getName().equals("mega")) {
                                serverPlayer.setAttached(DataManage.MEGA_DATA, false);
                                serverPlayer.setAttached(DataManage.MEGA_POKEMON, new Pokemon());

                                new FlagSpeciesFeature("mega", false).apply(pokemon);

                            }else if(enabled && feature.getName().equals("mega-x")){
                                serverPlayer.setAttached(DataManage.MEGA_DATA, false);
                                serverPlayer.setAttached(DataManage.MEGA_POKEMON, new Pokemon());

                                new FlagSpeciesFeature("mega-x", false).apply(pokemon);

                            } else if (enabled && feature.getName().equals("mega-y")) {
                                serverPlayer.setAttached(DataManage.MEGA_DATA, false);
                                serverPlayer.setAttached(DataManage.MEGA_POKEMON, new Pokemon());

                                new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                            }
                        }
                    }
                }
            }
        });

        battle = null;
        return Unit.INSTANCE;
    }

    public static Unit devolveFainted(BattleFaintedEvent battleFaintedEvent) {
        Pokemon pokemon = battleFaintedEvent.getKilled().getOriginalPokemon();
        ServerPlayerEntity serverPlayer = battleFaintedEvent.getKilled().getOriginalPokemon().getOwnerPlayer();

        List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

        if(serverPlayer == null || serverPlayer.getWorld().isClient){
            return Unit.INSTANCE;
        }
        for (String key : megaKeys) {
            FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));

            FlagSpeciesFeature feature = featureProvider.get(pokemon);
            if(feature != null){
                boolean enabled = featureProvider.get(pokemon).getEnabled();

                if (enabled && feature.getName().equals("mega")) {
                    serverPlayer.setAttached(DataManage.MEGA_DATA, false);
                    serverPlayer.setAttached(DataManage.MEGA_POKEMON, new Pokemon());

                    new FlagSpeciesFeature("mega", false).apply(pokemon);

                }else if(enabled && feature.getName().equals("mega-x")){
                    serverPlayer.setAttached(DataManage.MEGA_DATA, false);
                    serverPlayer.setAttached(DataManage.MEGA_POKEMON, new Pokemon());

                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);

                } else if (enabled && feature.getName().equals("mega-y")) {
                    serverPlayer.setAttached(DataManage.MEGA_DATA, false);
                    serverPlayer.setAttached(DataManage.MEGA_POKEMON, new Pokemon());

                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                }
            }
        }

        return Unit.INSTANCE;
    }

    public static Unit deVolveFlee(BattleFledEvent battleFledEvent) {
        PlayerEntity clientPlayer = MinecraftClient.getInstance().player;

        battleFledEvent.getBattle().getPlayers().forEach(serverPlayer -> {
            if (serverPlayer.getUuid().equals(clientPlayer.getUuid())) {
                for (BattlePokemon battlePokemon : battleFledEvent.getBattle().getActor(clientPlayer.getUuid()).getPokemonList()) {
                    if (battlePokemon.getOriginalPokemon().getEntity() == null ||
                            battlePokemon.getOriginalPokemon().getEntity().getWorld().isClient) {
                        continue;
                    }

                    Pokemon pokemon = battlePokemon.getOriginalPokemon();

                    List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

                    for (String key : megaKeys) {
                        FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));

                        FlagSpeciesFeature feature = featureProvider.get(pokemon);
                        if(feature != null){
                            boolean enabled = featureProvider.get(pokemon).getEnabled();

                            if (enabled && feature.getName().equals("mega")) {
                                serverPlayer.setAttached(DataManage.MEGA_DATA, false);
                                serverPlayer.setAttached(DataManage.MEGA_POKEMON, new Pokemon());

                                new FlagSpeciesFeature("mega", false).apply(pokemon);

                            }else if(enabled && feature.getName().equals("mega-x")){
                                serverPlayer.setAttached(DataManage.MEGA_DATA, false);
                                serverPlayer.setAttached(DataManage.MEGA_POKEMON, new Pokemon());

                                new FlagSpeciesFeature("mega-x", false).apply(pokemon);

                            } else if (enabled && feature.getName().equals("mega-y")) {
                                serverPlayer.setAttached(DataManage.MEGA_DATA, false);
                                serverPlayer.setAttached(DataManage.MEGA_POKEMON, new Pokemon());

                                new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                            }
                        }
                    }
                }
            }
        });
        battle = null;
        return Unit.INSTANCE;
    }
}