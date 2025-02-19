package com.cobblemon.yajatkaul.mega_showdown.battle;

import com.cobblemon.mod.common.client.gui.battle.BattleGUI;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.MegaBraceletItem;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.MegaEvoBattle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import top.theillusivec4.curios.api.CuriosApi;


public class ButtonLogic {
    public static void megaEvoButton(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof BattleGUI) {
            Player clientPlayer = Minecraft.getInstance().player;

            Button.OnPress onPressAction = button -> {
                event.removeListener(button);
                PacketDistributor.sendToServer(new MegaEvoBattle("mega_evo_battle"));
            };

            Screen screen = Minecraft.getInstance().screen;

            if(screen == null){
                return;
            }

            int screenWidth = screen.width;
            int screenHeight = screen.height;

            int guiScale = Minecraft.getInstance().options.guiScale().get();
// Calculate relative positions (as percentages of screen size)
            double relativeX;  // 5.5% from left
            double relativeY;  // 96.6% from top

            // Calculate responsive size (you can adjust these ratios)
            int buttonWidth = (int)(screenWidth * 0.028);  // 5% of screen width
            int buttonHeight = (int)(screenHeight * 0.05);

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

            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "mega_btn");
            ResourceLocation texture_hover = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "mega_btn_hover");

            WidgetSprites buttonTexture = new WidgetSprites(texture,texture,texture_hover,texture);
            ImageButton button = new ImageButton(xPos,yPos, buttonWidth, buttonHeight, buttonTexture,onPressAction );

            boolean hasMegaItem = CuriosApi.getCuriosInventory(clientPlayer).map(inventory -> inventory.isEquipped(stack -> stack.getItem() instanceof MegaBraceletItem)).orElse(false);

            if((Config.battleModeOnly || Config.battleMode) && clientPlayer != null && (clientPlayer.getOffhandItem().getItem() instanceof
                    MegaBraceletItem || hasMegaItem)){
                event.addListener(button);
            }
        }
    }
}
