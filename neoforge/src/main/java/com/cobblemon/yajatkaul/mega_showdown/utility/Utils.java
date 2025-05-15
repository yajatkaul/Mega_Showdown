package com.cobblemon.yajatkaul.mega_showdown.utility;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.helditem.CobblemonHeldItemManager;
import com.cobblemon.yajatkaul.mega_showdown.config.Config;
import com.cobblemon.yajatkaul.mega_showdown.item.*;
import net.minecraft.world.item.Item;

import java.util.*;

public class Utils {
    public static final Map<Item, String> MEGA_STONE_IDS = new HashMap<>();

    public static void loadMegaStoneIds() {
        MEGA_STONE_IDS.put(MegaStones.VENUSAURITE.asItem(), "Venusaur");
        MEGA_STONE_IDS.put(MegaStones.CHARIZARDITE_X.asItem(), "Charizard");
        MEGA_STONE_IDS.put(MegaStones.CHARIZARDITE_Y.asItem(), "Charizard");
        MEGA_STONE_IDS.put(MegaStones.BLASTOISINITE.asItem(), "Blastoise");
        MEGA_STONE_IDS.put(MegaStones.ALAKAZITE.asItem(), "Alakazam");
        MEGA_STONE_IDS.put(MegaStones.GENGARITE.asItem(), "Gengar");
        MEGA_STONE_IDS.put(MegaStones.KANGASKHANITE.asItem(), "Kangaskhan");
        MEGA_STONE_IDS.put(MegaStones.PINSIRITE.asItem(), "Pinsir");
        MEGA_STONE_IDS.put(MegaStones.GYARADOSITE.asItem(), "Gyarados");
        MEGA_STONE_IDS.put(MegaStones.AERODACTYLITE.asItem(), "Aerodactyl");
        MEGA_STONE_IDS.put(MegaStones.MEWTWONITE_X.asItem(), "Mewtwo");
        MEGA_STONE_IDS.put(MegaStones.MEWTWONITE_Y.asItem(), "Mewtwo");
        MEGA_STONE_IDS.put(MegaStones.AMPHAROSITE.asItem(), "Ampharos");
        MEGA_STONE_IDS.put(MegaStones.SCIZORITE.asItem(), "Scizor");
        MEGA_STONE_IDS.put(MegaStones.HERACRONITE.asItem(), "Heracross");
        MEGA_STONE_IDS.put(MegaStones.HOUNDOOMINITE.asItem(), "Houndoom");
        MEGA_STONE_IDS.put(MegaStones.TYRANITARITE.asItem(), "Tyranitar");
        MEGA_STONE_IDS.put(MegaStones.BLAZIKENITE.asItem(), "Blaziken");
        MEGA_STONE_IDS.put(MegaStones.GARDEVOIRITE.asItem(), "Gardevoir");
        MEGA_STONE_IDS.put(MegaStones.MAWILITE.asItem(), "Mawile");
        MEGA_STONE_IDS.put(MegaStones.AGGRONITE.asItem(), "Aggron");
        MEGA_STONE_IDS.put(MegaStones.MEDICHAMITE.asItem(), "Medicham");
        MEGA_STONE_IDS.put(MegaStones.MANECTITE.asItem(), "Manectric");
        MEGA_STONE_IDS.put(MegaStones.BANETTITE.asItem(), "Banette");
        MEGA_STONE_IDS.put(MegaStones.ABSOLITE.asItem(), "Absol");
        MEGA_STONE_IDS.put(MegaStones.LATIASITE.asItem(), "Latias");
        MEGA_STONE_IDS.put(MegaStones.LATIOSITE.asItem(), "Latios");
        MEGA_STONE_IDS.put(MegaStones.GARCHOMPITE.asItem(), "Garchomp");
        MEGA_STONE_IDS.put(MegaStones.LUCARIONITE.asItem(), "Lucario");
        MEGA_STONE_IDS.put(MegaStones.ABOMASITE.asItem(), "Abomasnow");
        MEGA_STONE_IDS.put(MegaStones.BEEDRILLITE.asItem(), "Beedrill");
        MEGA_STONE_IDS.put(MegaStones.PIDGEOTITE.asItem(), "Pidgeot");
        MEGA_STONE_IDS.put(MegaStones.SLOWBRONITE.asItem(), "Slowbro");
        MEGA_STONE_IDS.put(MegaStones.STEELIXITE.asItem(), "Steelix");
        MEGA_STONE_IDS.put(MegaStones.SCEPTILITE.asItem(), "Sceptile");
        MEGA_STONE_IDS.put(MegaStones.SWAMPERTITE.asItem(), "Swampert");
        MEGA_STONE_IDS.put(MegaStones.SABLENITE.asItem(), "Sableye");
        MEGA_STONE_IDS.put(MegaStones.SHARPEDONITE.asItem(), "Sharpedo");
        MEGA_STONE_IDS.put(MegaStones.CAMERUPTITE.asItem(), "Camerupt");
        MEGA_STONE_IDS.put(MegaStones.ALTARIANITE.asItem(), "Altaria");
        MEGA_STONE_IDS.put(MegaStones.GLALITITE.asItem(), "Glalie");
        MEGA_STONE_IDS.put(MegaStones.SALAMENCITE.asItem(), "Salamence");
        MEGA_STONE_IDS.put(MegaStones.METAGROSSITE.asItem(), "Metagross");
        MEGA_STONE_IDS.put(MegaStones.LOPUNNITE.asItem(), "Lopunny");
        MEGA_STONE_IDS.put(MegaStones.GALLADITE.asItem(), "Gallade");
        MEGA_STONE_IDS.put(MegaStones.AUDINITE.asItem(), "Audino");
        MEGA_STONE_IDS.put(MegaStones.DIANCITE.asItem(), "Diancie");
    }

    public static final Set<String> MEGA_POKEMONS = new HashSet<>();;
    public static final Set<String> GMAX_SPECIES = new HashSet<>();

    public static void addGmaxToMap(){
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

    public static void megaStonesRegister(){
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

    public static void zMovesRegister(){
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.ALORAICHIUM_Z.asItem(), "aloraichiumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.BLANK_Z.asItem(), "blankz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.BUGINIUM_Z.asItem(), "buginiumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.DARKINIUM_Z.asItem(), "darkiniumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.DECIDIUM_Z.asItem(), "decidiumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.DRAGONIUM_Z.asItem(), "dragoniumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.EEVIUM_Z.asItem(), "eeviumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.ELECTRIUM_Z.asItem(), "electriumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.FAIRIUM_Z.asItem(), "fairiumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.FIGHTINIUM_Z.asItem(), "fightiniumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.FIRIUM_Z.asItem(), "firiumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.FLYINIUM_Z.asItem(), "flyiniumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.GHOSTIUM_Z.asItem(), "ghostiumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.GRASSIUM_Z.asItem(), "grassiumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.GROUNDIUM_Z.asItem(), "groundiumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.ICIUM_Z.asItem(), "iciumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.INCINIUM_Z.asItem(), "inciniumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.KOMMONIUM_Z.asItem(), "kommoniumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.LUNALIUM_Z.asItem(), "lunaliumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.LYCANIUM_Z.asItem(), "lycaniumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.MARSHADIUM_Z.asItem(), "marshadiumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.MEWNIUM_Z.asItem(), "mewniumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.MIMIKIUM_Z.asItem(), "mimikiumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.NORMALIUM_Z.asItem(), "normaliumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.PIKANIUM_Z.asItem(), "pikaniumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.PIKASHUNIUM_Z.asItem(), "pikashuniumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.POISONIUM_Z.asItem(), "poisoniumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.PRIMARIUM_Z.asItem(), "primariumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.PSYCHIUM_Z.asItem(), "psychiumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.ROCKIUM_Z.asItem(), "rockiumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.SNORLIUM_Z.asItem(), "snorliumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.SOLGANIUM_Z.asItem(), "solganiumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.STEELIUM_Z.asItem(), "steeliumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.TAPUNIUM_Z.asItem(), "tapuniumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.ULTRANECROZIUM_Z.asItem(), "ultranecroziumz");
        CobblemonHeldItemManager.INSTANCE.registerRemap(ZCrystals.WATERIUM_Z.asItem(), "wateriumz");
    }

    public static void formeChangeheldItems(){
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.GRISEOUS_CORE.get(), "griseouscore");

        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.LUSTROUS_GLOBE.get(), "lustrousglobe");

        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.ADAMANT_CRYSTAL.get(), "adamantcrystal");

        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.BURN_DRIVE.get(), "burndrive");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.CHILL_DRIVE.get(), "chilldrive");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.DOUSE_DRIVE.get(), "dousedrive");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.SHOCK_DRIVE.get(), "shockdrive");

        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.CORNERSTONE_MASK.get(), "cornerstonemask");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.WELLSPRING_MASK.get(), "wellspringmask");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.HEARTHFLAME_MASK.get(), "hearthflamemask");
    }

    public static void registerRemapping(){
        megaStonesRegister();
        zMovesRegister();
        loadMegaStoneIds();
        heldItems();
        platesRegister();
        memoriesRegister();
        formeChangeheldItems();
    }

    public static void heldItems(){
        CobblemonHeldItemManager.INSTANCE.registerRemap(CompiItems.BOOSTER_ENERGY.get(), "boosterenergy");

        CobblemonHeldItemManager.INSTANCE.registerRemap(CompiItems.LEGEND_PLATE.get(), "legendplate");

        CobblemonHeldItemManager.INSTANCE.registerRemap(CompiItems.CLEAR_AMULET.get(), "clearamulet");
        CobblemonHeldItemManager.INSTANCE.registerRemap(CompiItems.LAGGING_TAIL.get(), "laggingtail");

        CobblemonHeldItemManager.INSTANCE.registerRemap(CompiItems.ADAMANT_ORB.get(), "adamantorb");
        CobblemonHeldItemManager.INSTANCE.registerRemap(CompiItems.GRISEOUS_ORB.get(), "griseousorb");
        CobblemonHeldItemManager.INSTANCE.registerRemap(CompiItems.LUSTROUS_ORB.get(), "lustrousorb");

        CobblemonHeldItemManager.INSTANCE.registerRemap(CompiItems.ADRENALINEORB.get(), "adrenalineorb");
        CobblemonHeldItemManager.INSTANCE.registerRemap(CompiItems.SOULDEW.get(), "souldew");
        CobblemonHeldItemManager.INSTANCE.registerRemap(CompiItems.GRIPCLAW.get(), "gripclaw");

        CobblemonHeldItemManager.INSTANCE.registerRemap(CompiItems.LUMINOUS_MOSS.get(), "luminousmoss");
    }

    public static void platesRegister(){
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.FLAME_PLATE.get(), "flameplate");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.DRACO_PLATE.get(), "dracoplate");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.DREAD_PLATE.get(), "dreadplate");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.EARTH_PLATE.get(), "earthplate");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.FIST_PLATE.get(), "fistplate");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.ICICLE_PLATE.get(), "icicleplate");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.INSECT_PLATE.get(), "insectplate");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.IRON_PLATE.get(), "ironplate");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.MEADOW_PLATE.get(), "meadowplate");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.MIND_PLATE.get(), "mindplate");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.PIXIE_PLATE.get(), "pixieplate");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.SKY_PLATE.get(), "skyplate");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.SPLASH_PLATE.get(), "splashplate");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.SPOOKY_PLATE.get(), "spookyplate");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.STONE_PLATE.get(), "stoneplate");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.TOXIC_PLATE.get(), "toxicplate");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.ZAP_PLATE.get(), "zapplate");
    }

    public static void memoriesRegister(){
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.BUG_MEMORY.get(), "bugmemory");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.DARK_MEMORY.get(), "darkmemory");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.DRAGON_MEMORY.get(), "dragonmemory");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.ELECTRIC_MEMORY.get(), "electricmemory");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.FAIRY_MEMORY.get(), "fairymemory");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.FIGHTING_MEMORY.get(), "fightingmemory");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.FIRE_MEMORY.get(), "firememory");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.FLYING_MEMORY.get(), "flyingmemory");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.GHOST_MEMORY.get(), "ghostmemory");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.GRASS_MEMORY.get(), "grassmemory");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.GROUND_MEMORY.get(), "groundmemory");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.ICE_MEMORY.get(), "icememory");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.POISON_MEMORY.get(), "poisonmemory");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.PSYCHIC_MEMORY.get(), "psychicmemory");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.ROCK_MEMORY.get(), "rockmemory");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.STEEL_MEMORY.get(), "steelmemory");
        CobblemonHeldItemManager.INSTANCE.registerRemap(FormeChangeItems.WATER_MEMORY.get(), "watermemory");
    }

    public static void setTradable(Pokemon pokemon, boolean allow){
        if(!Config.tradeForm){
            pokemon.setTradeable(allow);
        }
    }
}
