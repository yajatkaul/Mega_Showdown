package com.cobblemon.yajatkaul.mega_showdown.utility;

import com.cobblemon.yajatkaul.mega_showdown.datapack.DatapackRegister;
import com.cobblemon.yajatkaul.mega_showdown.datapack.DatapackRegistriesLoader;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.*;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Utils {
    public static final Set<String> MEGA_POKEMONS = new HashSet<>();
    public static final Set<String> GMAX_SPECIES = new HashSet<>();
    public static Registry<KeyItemData> keyItemsRegistry;
    public static Registry<BattleFormChange> battleFormRegistry;
    public static Registry<FusionData> fusionRegistry;
    public static Registry<GmaxData> gmaxRegistry;
    public static Registry<HeldItemData> heldItemsRegistry;
    public static Registry<MegaData> megaRegistry;
    public static Registry<ShowdownItemData> showdownItemRegistry;

    public static void addGmaxToMap() {
        GMAX_SPECIES.add("Venusaur");
        GMAX_SPECIES.add("Charizard");
        GMAX_SPECIES.add("Blastoise");
        GMAX_SPECIES.add("Butterfree");
        GMAX_SPECIES.add("Pikachu");
        GMAX_SPECIES.add("Meowth");
        GMAX_SPECIES.add("Machamp");
        GMAX_SPECIES.add("Gengar");
        GMAX_SPECIES.add("Kingler");
        GMAX_SPECIES.add("Lapras");
        GMAX_SPECIES.add("Eevee");
        GMAX_SPECIES.add("Snorlax");
        GMAX_SPECIES.add("Garbodor");
        GMAX_SPECIES.add("Melmetal");
        GMAX_SPECIES.add("Rillaboom");
        GMAX_SPECIES.add("Cinderace");
        GMAX_SPECIES.add("Inteleon");
        GMAX_SPECIES.add("Corviknight");
        GMAX_SPECIES.add("Orbeetle");
        GMAX_SPECIES.add("Drednaw");
        GMAX_SPECIES.add("Coalossal");
        GMAX_SPECIES.add("Flapple");
        GMAX_SPECIES.add("Appletun");
        GMAX_SPECIES.add("Sandaconda");
        GMAX_SPECIES.add("Toxtricity");
        GMAX_SPECIES.add("Centiskorch");
        GMAX_SPECIES.add("Hatterene");
        GMAX_SPECIES.add("Grimmsnarl");
        GMAX_SPECIES.add("Alcremie");
        GMAX_SPECIES.add("Copperajah");
        GMAX_SPECIES.add("Duraludon");
    }

    public static void addMegaList() {
        Collections.addAll(MEGA_POKEMONS,
                "Venusaur", "Charizard", "Blastoise", "Alakazam", "Gengar", "Kangaskhan", "Pinsir",
                "Gyarados", "Aerodactyl", "Mewtwo", "Ampharos", "Scizor", "Heracross", "Houndoom",
                "Tyranitar", "Blaziken", "Gardevoir", "Mawile", "Aggron", "Medicham", "Manectric",
                "Banette", "Absol", "Latias", "Latios", "Garchomp", "Lucario", "Abomasnow",
                "Beedrill", "Pidgeot", "Slowbro", "Steelix", "Sceptile", "Swampert", "Sableye",
                "Sharpedo", "Camerupt", "Altaria", "Glalie", "Salamence", "Metagross", "Lopunny",
                "Gallade", "Audino", "Diancie"
        );
    }

    public static void registryLoader(RegistryAccess registryAccess) {
        keyItemsRegistry = registryAccess.registryOrThrow(DatapackRegister.KEY_ITEM_REGISTRY_KEY);
        battleFormRegistry = registryAccess.registryOrThrow(DatapackRegister.BATTLE_FORM_REGISTRY_KEY);
        fusionRegistry = registryAccess.registryOrThrow(DatapackRegister.FUSION_REGISTRY_KEY);
        gmaxRegistry = registryAccess.registryOrThrow(DatapackRegister.GMAX_REGISTRY_KEY);
        heldItemsRegistry = registryAccess.registryOrThrow(DatapackRegister.HELD_ITEM_REGISTRY_KEY);
        megaRegistry = registryAccess.registryOrThrow(DatapackRegister.MEGA_REGISTRY_KEY);
        showdownItemRegistry = registryAccess.registryOrThrow(DatapackRegister.SHOWDOWN_ITEM_REGISTRY_KEY);

        DatapackRegistriesLoader.registerCustomShowdown();
    }
}
