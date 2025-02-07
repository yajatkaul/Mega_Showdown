package com.cobblemon.yajatkaul.mega_showdown.battle;

import com.cobblemon.mod.common.client.gui.battle.BattleGUI;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.Window;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import static com.cobblemon.yajatkaul.mega_showdown.battle.BattleHandling.*;

public class ButtonLogic {
    public static void megaEvoButton(MinecraftClient minecraftClient, Screen screen, int j, int j1) {
        if (screen instanceof BattleGUI) {
            PlayerEntity clientPlayer = minecraftClient.player;

            Window window = minecraftClient.getWindow();
            int screenWidth = window.getScaledWidth();
            int screenHeight = window.getScaledHeight();

// Calculate relative positions (as percentages of screen size)
            double relativeX = 0.048;  // 5.5% from left
            double relativeY = 0.948;  // 96.6% from top

// Calculate actual position
            int xPos = (int)(screenWidth * relativeX);
            int yPos = (int)(screenHeight * relativeY);

// Calculate responsive size (you can adjust these ratios)
            int buttonWidth = (int)(screenWidth * 0.028);  // 5% of screen width
            int buttonHeight = (int)(screenHeight * 0.05);

            ButtonTextures buttonTextures = new ButtonTextures(
                    Identifier.of(MegaShowdown.MOD_ID, "mega_btn"),
                    Identifier.of(MegaShowdown.MOD_ID, "mega_btn"),
                    Identifier.of(MegaShowdown.MOD_ID, "mega_btn_hover"),
                    Identifier.of(MegaShowdown.MOD_ID, "mega_btn")
            );

            TexturedButtonWidget texturedButtonWidget = new TexturedButtonWidget(
                    xPos,
                    yPos,
                    buttonWidth,
                    buttonHeight,
                    buttonTextures,
                    buttonWidget -> {
                        if(battle == null){
                            return;
                        }
                        battle.getPlayers().forEach(serverPlayer -> {
                            handleMegaEvolution(serverPlayer, battle, (TexturedButtonWidget) buttonWidget, screen);
                        });
                    });

            //Battle mode only
            if(Config.getInstance().battleModeOnly && clientPlayer != null && clientPlayer.getOffHandStack().isOf(ModItems.MEGA_BRACELET.asItem())){
                //Multiple Megas
                if(!clicked || Config.getInstance().multipleMegas){
                    Screens.getButtons(screen).add(texturedButtonWidget);
                }else{
                    Screens.getButtons(screen).remove(texturedButtonWidget);
                }
            }
        }
    }
}
