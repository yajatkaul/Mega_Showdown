package com.cobblemon.yajatkaul.mega_showdown.battle;

import com.cobblemon.mod.common.client.gui.battle.BattleGUI;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.MegaEvo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.cobblemon.yajatkaul.mega_showdown.battle.BattleHandling.*;

public class ButtonLogic {
    public static void megaEvoButton(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof BattleGUI) {
            Player clientPlayer = Minecraft.getInstance().player;

            Button.OnPress onPressAction = button -> {
                event.removeListener(button);
                PacketDistributor.sendToServer(new MegaEvo("packet", 1));
            };

            Screen screen = Minecraft.getInstance().screen;
            assert screen != null;
            int screenWidth = screen.width;
            int screenHeight = screen.height;

// Calculate relative positions (as percentages of screen size)
            double relativeX = 0.048;  // 5.5% from left
            double relativeY = 0.948;  // 96.6% from top

// Calculate actual position
            int xPos = (int)(screenWidth * relativeX);
            int yPos = (int)(screenHeight * relativeY);

// Calculate responsive size (you can adjust these ratios)
            int buttonWidth = (int)(screenWidth * 0.028);  // 5% of screen width
            int buttonHeight = (int)(screenHeight * 0.05);

            ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "mega_btn");
            ResourceLocation texture_hover = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "mega_btn_hover");

            WidgetSprites buttonTexture = new WidgetSprites(texture,texture,texture_hover,texture);
            ImageButton button = new ImageButton(xPos,yPos, buttonWidth, buttonHeight, buttonTexture,onPressAction );

            if(Config.battleMode && clientPlayer != null && clientPlayer.getOffhandItem().is(ModItems.MEGA_BRACELET.asItem())){
                if(!clicked || Config.multipleMegas){
                    event.addListener(button);
                }
                else{
                    event.removeListener(button);
                }
            }
        }
    }
}
