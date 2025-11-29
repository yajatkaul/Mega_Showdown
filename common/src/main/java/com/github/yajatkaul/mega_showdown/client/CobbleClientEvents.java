package com.github.yajatkaul.mega_showdown.client;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.events.pokemon.interaction.PokemonInteractionGUICreationEvent;
import com.cobblemon.mod.common.client.CobblemonClient;
import com.cobblemon.mod.common.client.gui.interact.wheel.InteractWheelOption;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.networking.server.packet.MegaEvoPacket;
import com.github.yajatkaul.mega_showdown.networking.server.packet.UltraBurstPacket;
import dev.architectury.networking.NetworkManager;
import kotlin.Unit;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

import java.util.Objects;

public class CobbleClientEvents {
    public static void register() {
        CobblemonEvents.POKEMON_INTERACTION_GUI_CREATION.subscribe(Priority.NORMAL, CobbleClientEvents::addGimmickButtons);
    }

    private static void addGimmickButtons(PokemonInteractionGUICreationEvent event) {
        Pokemon pokemon = CobblemonClient.INSTANCE.getStorage().getParty().getSlots().stream()
                .filter(Objects::nonNull)
                .filter(slot -> slot.getEntity() != null && slot.getEntity().getUUID().equals(event.getPokemonID()))
                .findFirst()
                .orElse(null);

        if (pokemon == null) return;

        if (MegaShowdownConfig.outSideMega && WheelDataClient.shouldMega) {
            InteractWheelOption wheelOption = new InteractWheelOption(
                    ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "textures/gui/interact/mega_evolve_wheel.png"),
                    null,
                    WheelDataClient.canMega,
                    "mega_showdown.ui.mega_evolve",
                    () -> new Vector3f(1),
                    () -> {
                        if (WheelDataClient.canMega) {
                            NetworkManager.sendToServer(new MegaEvoPacket(event.getPokemonID()));
                            closeGUI();
                        }
                        return Unit.INSTANCE;
                    });
            event.addFillingOption(wheelOption);
        }
        if (MegaShowdownConfig.outSideUltraBurst && WheelDataClient.shouldUltra) {
            InteractWheelOption wheelOption = new InteractWheelOption(
                    ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "textures/gui/interact/ultra_burst_wheel.png"),
                    null,
                    WheelDataClient.canUltra,
                    "mega_showdown.ui.ultra_burst",
                    () -> new Vector3f(1),
                    () -> {
                        if (WheelDataClient.canUltra) {
                            NetworkManager.sendToServer(new UltraBurstPacket(event.getPokemonID()));
                            closeGUI();
                        }
                        return Unit.INSTANCE;
                    });
            event.addFillingOption(wheelOption);
        }
    }

    private static void closeGUI() {
        Minecraft.getInstance().setScreen(null);
    }
}
