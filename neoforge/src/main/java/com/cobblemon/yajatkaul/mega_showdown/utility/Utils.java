package com.cobblemon.yajatkaul.mega_showdown.utility;

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static final Map<Item, Species> MEGA_STONE_IDS = new HashMap<>();

    public static Species getSpecies(String name) {
        return PokemonSpecies.INSTANCE.getByName(name);
    }

    public static void loadMegaStoneIds() {
        MEGA_STONE_IDS.put(MegaStones.VENUSAURITE.asItem(), getSpecies("venusaur"));
        MEGA_STONE_IDS.put(MegaStones.CHARIZARDITE_X.asItem(), getSpecies("charizard"));
        MEGA_STONE_IDS.put(MegaStones.CHARIZARDITE_Y.asItem(), getSpecies("charizard"));
        MEGA_STONE_IDS.put(MegaStones.BLASTOISINITE.asItem(), getSpecies("blastoise"));
        MEGA_STONE_IDS.put(MegaStones.ALAKAZITE.asItem(), getSpecies("alakazam"));
        MEGA_STONE_IDS.put(MegaStones.GENGARITE.asItem(), getSpecies("gengar"));
        MEGA_STONE_IDS.put(MegaStones.KANGASKHANITE.asItem(), getSpecies("kangaskhan"));
        MEGA_STONE_IDS.put(MegaStones.PINSIRITE.asItem(), getSpecies("pinsir"));
        MEGA_STONE_IDS.put(MegaStones.GYARADOSITE.asItem(), getSpecies("gyarados"));
        MEGA_STONE_IDS.put(MegaStones.AERODACTYLITE.asItem(), getSpecies("aerodactyl"));
        MEGA_STONE_IDS.put(MegaStones.MEWTWONITE_X.asItem(), getSpecies("mewtwo"));
        MEGA_STONE_IDS.put(MegaStones.MEWTWONITE_Y.asItem(), getSpecies("mewtwo"));
        MEGA_STONE_IDS.put(MegaStones.AMPHAROSITE.asItem(), getSpecies("ampharos"));
        MEGA_STONE_IDS.put(MegaStones.SCIZORITE.asItem(), getSpecies("scizor"));
        MEGA_STONE_IDS.put(MegaStones.HERACRONITE.asItem(), getSpecies("heracross"));
        MEGA_STONE_IDS.put(MegaStones.HOUNDOOMINITE.asItem(), getSpecies("houndoom"));
        MEGA_STONE_IDS.put(MegaStones.TYRANITARITE.asItem(), getSpecies("tyranitar"));
        MEGA_STONE_IDS.put(MegaStones.BLAZIKENITE.asItem(), getSpecies("blaziken"));
        MEGA_STONE_IDS.put(MegaStones.GARDEVOIRITE.asItem(), getSpecies("gardevoir"));
        MEGA_STONE_IDS.put(MegaStones.MAWILITE.asItem(), getSpecies("mawile"));
        MEGA_STONE_IDS.put(MegaStones.AGGRONITE.asItem(), getSpecies("aggron"));
        MEGA_STONE_IDS.put(MegaStones.MEDICHAMITE.asItem(), getSpecies("medicham"));
        MEGA_STONE_IDS.put(MegaStones.MANECTITE.asItem(), getSpecies("manectric"));
        MEGA_STONE_IDS.put(MegaStones.BANETTITE.asItem(), getSpecies("banette"));
        MEGA_STONE_IDS.put(MegaStones.ABSOLITE.asItem(), getSpecies("absol"));
        MEGA_STONE_IDS.put(MegaStones.LATIASITE.asItem(), getSpecies("latias"));
        MEGA_STONE_IDS.put(MegaStones.LATIOSITE.asItem(), getSpecies("latios"));
        MEGA_STONE_IDS.put(MegaStones.GARCHOMPITE.asItem(), getSpecies("garchomp"));
        MEGA_STONE_IDS.put(MegaStones.LUCARIONITE.asItem(), getSpecies("lucario"));
        MEGA_STONE_IDS.put(MegaStones.ABOMASITE.asItem(), getSpecies("abomasnow"));
        MEGA_STONE_IDS.put(MegaStones.BEEDRILLITE.asItem(), getSpecies("beedrill"));
        MEGA_STONE_IDS.put(MegaStones.PIDGEOTITE.asItem(), getSpecies("pidgeot"));
        MEGA_STONE_IDS.put(MegaStones.SLOWBRONITE.asItem(), getSpecies("slowbro"));
        MEGA_STONE_IDS.put(MegaStones.STEELIXITE.asItem(), getSpecies("steelix"));
        MEGA_STONE_IDS.put(MegaStones.SCEPTILITE.asItem(), getSpecies("sceptile"));
        MEGA_STONE_IDS.put(MegaStones.SWAMPERTITE.asItem(), getSpecies("swampert"));
        MEGA_STONE_IDS.put(MegaStones.SABLENITE.asItem(), getSpecies("sableye"));
        MEGA_STONE_IDS.put(MegaStones.SHARPEDONITE.asItem(), getSpecies("sharpedo"));
        MEGA_STONE_IDS.put(MegaStones.CAMERUPTITE.asItem(), getSpecies("camerupt"));
        MEGA_STONE_IDS.put(MegaStones.ALTARIANITE.asItem(), getSpecies("altaria"));
        MEGA_STONE_IDS.put(MegaStones.GLALITITE.asItem(), getSpecies("glalie"));
        MEGA_STONE_IDS.put(MegaStones.SALAMENCITE.asItem(), getSpecies("salamence"));
        MEGA_STONE_IDS.put(MegaStones.METAGROSSITE.asItem(), getSpecies("metagross"));
        MEGA_STONE_IDS.put(MegaStones.LOPUNNITE.asItem(), getSpecies("lopunny"));
        MEGA_STONE_IDS.put(MegaStones.GALLADITE.asItem(), getSpecies("gallade"));
        MEGA_STONE_IDS.put(MegaStones.AUDINITE.asItem(), getSpecies("audino"));
        MEGA_STONE_IDS.put(MegaStones.DIANCITE.asItem(), getSpecies("diancie"));
    }
}
