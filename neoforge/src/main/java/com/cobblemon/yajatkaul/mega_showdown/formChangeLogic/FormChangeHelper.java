package com.cobblemon.yajatkaul.mega_showdown.formChangeLogic;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.pc.PCStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FormChangeHelper {
    public static Set<String> mega_aspects = new HashSet<>(Set.of("mega", "mega_y", "mega_x"));

    public static boolean hasMega(ServerPlayer player) {
        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
        PCStore pcStore = Cobblemon.INSTANCE.getStorage().getPC(player);

        for (Pokemon pokemon : playerPartyStore) {
            if (pokemon.getAspects().stream().anyMatch(mega_aspects::contains)) {
                return true;
            }
        }

        for (Pokemon pokemon : pcStore) {
            if (pokemon.getAspects().stream().anyMatch(mega_aspects::contains)) {
                return true;
            }
        }

        return false;
    }

    public static boolean hasPrimal(ServerPlayer player) {
        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
        PCStore pcStore = Cobblemon.INSTANCE.getStorage().getPC(player);

        for (Pokemon pokemon : playerPartyStore) {
            if (pokemon.getAspects().contains("primal")) {
                return true;
            }
        }

        for (Pokemon pokemon : pcStore) {
            if (pokemon.getAspects().contains("primal")) {
                return true;
            }
        }

        return false;
    }
}
