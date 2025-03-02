package com.cobblemon.yajatkaul.mega_showdown.utility;

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.mod.common.pokemon.helditem.CobblemonHeldItemManager;
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

    public static void registerRemapping(){
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.ABSOLITE.asItem(), "Absolite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.AGGRONITE.asItem(), "Aggronite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.ALAKAZITE.asItem(), "Alakazite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.AUDINITE.asItem(), "Audinite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.ABOMASITE.get(), "Abomasite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.ALTARIANITE.asItem(), "Altarianite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.AERODACTYLITE.get(), "Aerodactylite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.AMPHAROSITE.get(), "Ampharosite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.BANETTITE.get(), "Banettite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.BEEDRILLITE.get(), "Beedrillite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.BLASTOISINITE.get(), "Blastoisinite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.BLAZIKENITE.get(), "Blazikenite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.CAMERUPTITE.get(), "Cameruptite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.CHARIZARDITE_X.get(), "Charizardite X");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.CHARIZARDITE_Y.get(), "Charizardite Y");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.DIANCITE.get(), "Diancite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.GALLADITE.get(), "Galladite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.GARCHOMPITE.get(), "Garchompite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.GARDEVOIRITE.get(), "Gardevoirite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.GENGARITE.get(), "Gengarite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.GLALITITE.get(), "Glalitite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.GYARADOSITE.get(), "Gyaradosite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.HERACRONITE.get(), "Heracronite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.HOUNDOOMINITE.get(), "Houndoominite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.KANGASKHANITE.get(), "Kangaskhanite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.LATIASITE.get(), "Latiasite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.LATIOSITE.get(), "Latiosite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.LOPUNNITE.get(), "Lopunnite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.LUCARIONITE.get(), "Lucarionite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.MANECTITE.get(), "Manectite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.MAWILITE.get(), "Mawilite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.MEDICHAMITE.get(), "Medichamite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.METAGROSSITE.get(), "Metagrossite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.MEWTWONITE_X.get(), "Mewtwonite X");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.MEWTWONITE_Y.get(), "Mewtwonite Y");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.PIDGEOTITE.get(), "Pidgeotite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.PINSIRITE.get(), "Pinsirite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.SABLENITE.get(), "Sablenite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.SALAMENCITE.get(), "Salamencite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.SCEPTILITE.get(), "Sceptilite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.SCIZORITE.get(), "Scizorite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.SHARPEDONITE.get(), "Sharpedonite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.SLOWBRONITE.get(), "Slowbronite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.STEELIXITE.get(), "Steelixite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.SWAMPERTITE.get(), "Swampertite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.TYRANITARITE.get(), "Tyranitarite");
        CobblemonHeldItemManager.INSTANCE.registerRemap(MegaStones.VENUSAURITE.get(), "Venusaurite");
    }
}
