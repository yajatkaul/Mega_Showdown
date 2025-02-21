package com.cobblemon.yajatkaul.mega_showdown.battle;

import com.cobblemon.mod.common.client.gui.battle.BattleGUI;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.MegaBraceletItem;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.EvoPacketBattle;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.Window;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class ButtonLogic {
    public static void megaEvoButton(MinecraftClient minecraftClient, Screen screen, int j, int j1) {
        if (screen instanceof BattleGUI) {
            PlayerEntity clientPlayer = minecraftClient.player;

            Screen window = minecraftClient.currentScreen;
            int guiScale = MinecraftClient.getInstance().options.getGuiScale().getValue();

            if(window == null){
                return;
            }
            int screenWidth = window.width;
            int screenHeight = window.height;

// Calculate relative positions (as percentages of screen size)
            double relativeX;  // 5.5% from left
            double relativeY;  // 96.6% from top

// Calculate responsive size (you can adjust these ratios)
            int buttonWidth;  // 5% of screen width
            int buttonHeight;

            ButtonTextures buttonTextures = new ButtonTextures(
                    Identifier.of(MegaShowdown.MOD_ID, "mega_btn"),
                    Identifier.of(MegaShowdown.MOD_ID, "mega_btn"),
                    Identifier.of(MegaShowdown.MOD_ID, "mega_btn_hover"),
                    Identifier.of(MegaShowdown.MOD_ID, "mega_btn")
            );

            switch (guiScale){
                case 1:
                    relativeX = 0.030;  // 3% from left
                    relativeY = 0.977;  // 97.7% from top
                    // Calculate responsive size (you can adjust these ratios)
                    buttonWidth = (int)(screenWidth * 0.01);  // 1% of screen width
                    buttonHeight = (int)(screenHeight * 0.02);
                    break;
                case 2:
                    relativeX = 0.048;
                    relativeY = 0.948;
                    // Calculate responsive size (you can adjust these ratios)
                    buttonWidth = (int)(screenWidth * 0.028);  // 5% of screen width
                    buttonHeight = (int)(screenHeight * 0.05);
                    break;
                case 3:
                    relativeX = 0.070;
                    relativeY = 0.920;
                    // Calculate responsive size (you can adjust these ratios)
                    buttonWidth = (int)(screenWidth * 0.040);  // 5% of screen width
                    buttonHeight = (int)(screenHeight * 0.075);
                    break;
                case 4:
                    relativeX = 0.085;
                    relativeY = 0.910;
                    // Calculate responsive size (you can adjust these ratios)
                    buttonWidth = (int)(screenWidth * 0.040);  // 5% of screen width
                    buttonHeight = (int)(screenHeight * 0.075);
                    break;
                default:
                    relativeX = 0.048;  // 5.5% from left
                    relativeY = 0.948;  // 96.6% from top
                    // Calculate responsive size (you can adjust these ratios)
                    buttonWidth = (int)(screenWidth * 0.028);  // 5% of screen width
                    buttonHeight = (int)(screenHeight * 0.05);
                    break;
            }

            // Calculate actual position
            int xPos = (int)(screenWidth * relativeX);
            int yPos = (int)(screenHeight * relativeY);

            TexturedButtonWidget texturedButtonWidget = new TexturedButtonWidget(
                    xPos,
                    yPos,
                    buttonWidth,
                    buttonHeight,
                    buttonTextures,
                    buttonWidget -> {
                        EvoPacketBattle.send();
                    });

            boolean hasMegaItem = TrinketsApi.getTrinketComponent(clientPlayer).map(trinkets ->
                    trinkets.isEquipped(item -> item.getItem() instanceof MegaBraceletItem)).orElse(false);

            //Battle mode only
            if((ShowdownConfig.battleModeOnly.get() || ShowdownConfig.battleMode.get()) && clientPlayer != null &&
                    (clientPlayer.getOffHandStack().getItem() instanceof MegaBraceletItem || hasMegaItem)){
                Screens.getButtons(screen).add(texturedButtonWidget);
            }
        }
    }
}
