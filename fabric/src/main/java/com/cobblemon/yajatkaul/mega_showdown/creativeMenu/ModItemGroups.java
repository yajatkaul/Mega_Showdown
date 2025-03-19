package com.cobblemon.yajatkaul.mega_showdown.creativeMenu;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.block.PokemonStones;
import com.cobblemon.yajatkaul.mega_showdown.item.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup MEGA_SHOWDOWN_TAB = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(MegaShowdown.MOD_ID, "mega_showdown_tab"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.KEYSTONE))
                    .displayName(Text.translatable("creativeTab.mega_showdown.mega_showdown_tab"))
                    .entries(((displayContext, entries) -> {
                        entries.add(MegaStones.MEGA_STONE);
                        entries.add(MegaStones.ABOMASITE);
                        entries.add(MegaStones.ABSOLITE);
                        entries.add(MegaStones.AERODACTYLITE);
                        entries.add(MegaStones.AGGRONITE);
                        entries.add(MegaStones.ALAKAZITE);
                        entries.add(MegaStones.ALTARIANITE);
                        entries.add(MegaStones.AMPHAROSITE);
                        entries.add(MegaStones.AUDINITE);
                        entries.add(MegaStones.BANETTITE);
                        entries.add(MegaStones.BEEDRILLITE);
                        entries.add(MegaStones.BLASTOISINITE);
                        entries.add(MegaStones.BLAZIKENITE);
                        entries.add(MegaStones.CAMERUPTITE);
                        entries.add(MegaStones.CHARIZARDITE_X);
                        entries.add(MegaStones.CHARIZARDITE_Y);
                        entries.add(MegaStones.DIANCITE);
                        entries.add(MegaStones.GALLADITE);
                        entries.add(MegaStones.GARCHOMPITE);
                        entries.add(MegaStones.GARDEVOIRITE);
                        entries.add(MegaStones.GENGARITE);
                        entries.add(MegaStones.GLALITITE);
                        entries.add(MegaStones.GYARADOSITE);
                        entries.add(MegaStones.HERACRONITE);
                        entries.add(MegaStones.HOUNDOOMINITE);
                        entries.add(MegaStones.KANGASKHANITE);
                        entries.add(MegaStones.LATIASITE);
                        entries.add(MegaStones.LATIOSITE);
                        entries.add(MegaStones.LOPUNNITE);
                        entries.add(MegaStones.LUCARIONITE);
                        entries.add(MegaStones.MANECTITE);
                        entries.add(MegaStones.MAWILITE);
                        entries.add(MegaStones.MEDICHAMITE);
                        entries.add(MegaStones.METAGROSSITE);
                        entries.add(MegaStones.MEWTWONITE_Y);
                        entries.add(MegaStones.MEWTWONITE_X);
                        entries.add(MegaStones.PIDGEOTITE);
                        entries.add(MegaStones.PINSIRITE);
                        entries.add(MegaStones.SABLENITE);
                        entries.add(MegaStones.SALAMENCITE);
                        entries.add(MegaStones.SCIZORITE);
                        entries.add(MegaStones.SHARPEDONITE);
                        entries.add(MegaStones.SCEPTILITE);
                        entries.add(MegaStones.SLOWBRONITE);
                        entries.add(MegaStones.STEELIXITE);
                        entries.add(MegaStones.SWAMPERTITE);
                        entries.add(MegaStones.TYRANITARITE);
                        entries.add(MegaStones.VENUSAURITE);

                        entries.add(ModItems.KEYSTONE);

                        entries.add(ModItems.MEGA_BRACELET);
                        entries.add(ModItems.MEGA_RED_BRACELET);
                        entries.add(ModItems.MEGA_YELLOW_BRACELET);
                        entries.add(ModItems.MEGA_PINK_BRACELET);
                        entries.add(ModItems.MEGA_GREEN_BRACELET);
                        entries.add(ModItems.MEGA_BLUE_BRACELET);
                        entries.add(ModItems.MEGA_BLACK_BRACELET);
                        entries.add(ModItems.MEGA_RING);
                        entries.add(ModItems.BRENDAN_MEGA_CUFF);
                        entries.add(ModItems.LYSANDRE_RING);
                        entries.add(ModItems.KORRINA_GLOVE);
                        entries.add(ModItems.MAXIE_GLASSES);
                        entries.add(ModItems.ARCHIE_ANCHOR);
                        entries.add(ModItems.MAY_BRACELET);

                        entries.add(MegaOres.ABOMASITE_ORE);
                        entries.add(MegaOres.ABSOLITE_ORE);
                        entries.add(MegaOres.AERODACTYLITE_ORE);
                        entries.add(MegaOres.AGGRONITE_ORE);
                        entries.add(MegaOres.ALAKAZITE_ORE);
                        entries.add(MegaOres.ALTARIANITE_ORE);
                        entries.add(MegaOres.AMPHAROSITE_ORE);
                        entries.add(MegaOres.AUDINITE_ORE);
                        entries.add(MegaOres.BANETTITE_ORE);
                        entries.add(MegaOres.BEEDRILLITE_ORE);
                        entries.add(MegaOres.BLASTOISINITE_ORE);
                        entries.add(MegaOres.BLAZIKENITE_ORE);
                        entries.add(MegaOres.CAMERUPTITE_ORE);
                        entries.add(MegaOres.CHARIZARDITE_X_ORE);
                        entries.add(MegaOres.CHARIZARDITE_Y_ORE);
                        entries.add(MegaOres.DIANCITE_ORE);
                        entries.add(MegaOres.GALLADITE_ORE);
                        entries.add(MegaOres.GARCHOMPITE_ORE);
                        entries.add(MegaOres.GARDEVOIRITE_ORE);
                        entries.add(MegaOres.GENGARITE_ORE);
                        entries.add(MegaOres.GLALITITE_ORE);
                        entries.add(MegaOres.GYARADOSITE_ORE);
                        entries.add(MegaOres.HERACRONITE_ORE);
                        entries.add(MegaOres.HOUNDOOMINITE_ORE);
                        entries.add(MegaOres.KANGASKHANITE_ORE);
                        entries.add(MegaOres.LATIASITE_ORE);
                        entries.add(MegaOres.LATIOSITE_ORE);
                        entries.add(MegaOres.LOPUNNITE_ORE);
                        entries.add(MegaOres.LUCARIONITE_ORE);
                        entries.add(MegaOres.MANECTITE_ORE);
                        entries.add(MegaOres.MAWILITE_ORE);
                        entries.add(MegaOres.MEDICHAMITE_ORE);
                        entries.add(MegaOres.METAGROSSITE_ORE);
                        entries.add(MegaOres.MEWTWONITE_X_ORE);
                        entries.add(MegaOres.MEWTWONITE_Y_ORE);
                        entries.add(MegaOres.PIDGEOTITE_ORE);
                        entries.add(MegaOres.PINSIRITE_ORE);
                        entries.add(MegaOres.SABLENITE_ORE);
                        entries.add(MegaOres.SALAMENCITE_ORE);
                        entries.add(MegaOres.SCEPTILITE_ORE);
                        entries.add(MegaOres.SCIZORITE_ORE);
                        entries.add(MegaOres.SHARPEDONITE_ORE);
                        entries.add(MegaOres.SLOWBRONITE_ORE);
                        entries.add(MegaOres.STEELIXITE_ORE);
                        entries.add(MegaOres.SWAMPERTITE_ORE);
                        entries.add(MegaOres.TYRANITARITE_ORE);
                        entries.add(MegaOres.VENUSAURITE_ORE);

                        entries.add(MegaOres.KEYSTONE_ORE);
                        entries.add(ModBlocks.KEYSTONE_BLOCK);

                        entries.add(ModBlocks.MEGA_METEOROID_BLOCK);
                        entries.add(ModBlocks.MEGA_EVO_BLOCK);
                        entries.add(ModBlocks.MEGA_EVO_BRICK);
                        entries.add(ModBlocks.CHISELED_MEGA_EVO_BRICK);
                        entries.add(ModBlocks.CHISELED_MEGA_EVO_BLOCK);
                        entries.add(ModBlocks.POLISHED_MEGA_EVO_BLOCK);

                        entries.add(ModItems.MEGA_STONE_CRYSTAL_ITEM);

                        entries.add(PokemonStones.MEGA_METEORID_DAWN_ORE);
                        entries.add(PokemonStones.MEGA_METEORID_DUSK_ORE);
                        entries.add(PokemonStones.MEGA_METEORID_FIRE_ORE);
                        entries.add(PokemonStones.MEGA_METEORID_ICE_ORE);
                        entries.add(PokemonStones.MEGA_METEORID_LEAF_ORE);
                        entries.add(PokemonStones.MEGA_METEORID_MOON_ORE);
                        entries.add(PokemonStones.MEGA_METEORID_SHINY_ORE);
                        entries.add(PokemonStones.MEGA_METEORID_SUN_ORE);
                        entries.add(PokemonStones.MEGA_METEORID_THUNDER_ORE);
                        entries.add(PokemonStones.MEGA_METEORID_WATER_ORE);
                    }))
                    .build());

    public static final ItemGroup Z_MOVES_TAB = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(MegaShowdown.MOD_ID, "z_moves_tab"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ZMoves.BLANK_Z))
                    .displayName(Text.translatable("creativeTab.mega_showdown.z_moves_tab"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ZMoves.Z_RING);
                        entries.add(ZMoves.Z_RING_BLACK);
                        entries.add(ZMoves.Z_RING_POWER);
                        entries.add(ZMoves.ALORAICHIUM_Z);
                        entries.add(ZMoves.BLANK_Z);
                        entries.add(ZMoves.BUGINIUM_Z);
                        entries.add(ZMoves.DARKINIUM_Z);
                        entries.add(ZMoves.DECIDIUM_Z);
                        entries.add(ZMoves.DRAGONIUM_Z);
                        entries.add(ZMoves.EEVIVIUM_Z);
                        entries.add(ZMoves.ELECTRIUM_Z);
                        entries.add(ZMoves.FAIRIUM_Z);
                        entries.add(ZMoves.FIGHTINIUM_Z);
                        entries.add(ZMoves.FIRIUM_Z);
                        entries.add(ZMoves.FLYINIUM_Z);
                        entries.add(ZMoves.GHOSTIUM_Z);
                        entries.add(ZMoves.GRASSIUM_Z);
                        entries.add(ZMoves.GROUNDIUM_Z);
                        entries.add(ZMoves.ICIUM_Z);
                        entries.add(ZMoves.INCINIUM_Z);
                        entries.add(ZMoves.KOMMONIUM_Z);
                        entries.add(ZMoves.LUNALIUM_Z);
                        entries.add(ZMoves.LYCANIUM_Z);
                        entries.add(ZMoves.MARSHADIUM_Z);
                        entries.add(ZMoves.MEWNIUM_Z);
                        entries.add(ZMoves.MIMIKIUM_Z);
                        entries.add(ZMoves.NORMALIUM_Z);
                        entries.add(ZMoves.PIKANIUM_Z);
                        entries.add(ZMoves.PIKASHUNIUM_Z);
                        entries.add(ZMoves.POISONIUM_Z);
                        entries.add(ZMoves.PRIMARIUM_Z);
                        entries.add(ZMoves.PSYCHIUM_Z);
                        entries.add(ZMoves.ROCKIUM_Z);
                        entries.add(ZMoves.SNORLIUM_Z);
                        entries.add(ZMoves.SOLGANIUM_Z);
                        entries.add(ZMoves.STEELIUM_Z);
                        entries.add(ZMoves.TAPUNIUM_Z);
                        entries.add(ZMoves.ULTRANECROZIUM_Z);
                        entries.add(ZMoves.WATERIUM_Z);
                    }))
                    .build());

    public static final ItemGroup TERA_TAB = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(MegaShowdown.MOD_ID, "tera_tab"),
            FabricItemGroup.builder().icon(() -> new ItemStack(TeraMoves.TERA_ORB))
                    .displayName(Text.translatable("creativeTab.mega_showdown.tera_tab"))
                    .entries(((displayContext, entries) -> {
                        entries.add(TeraMoves.TERA_ORB);
                        entries.add(TeraMoves.BUG_TERA_SHARD);
                        entries.add(TeraMoves.DARK_TERA_SHARD);
                        entries.add(TeraMoves.DRAGON_TERA_SHARD);
                        entries.add(TeraMoves.ELECTRIC_TERA_SHARD);
                        entries.add(TeraMoves.FAIRY_TERA_SHARD);
                        entries.add(TeraMoves.FIGHTING_TERA_SHARD);
                        entries.add(TeraMoves.FIRE_TERA_SHARD);
                        entries.add(TeraMoves.FLYING_TERA_SHARD);
                        entries.add(TeraMoves.GHOST_TERA_SHARD);
                        entries.add(TeraMoves.GRASS_TERA_SHARD);
                        entries.add(TeraMoves.GROUND_TERA_SHARD);
                        entries.add(TeraMoves.ICE_TERA_SHARD);
                        entries.add(TeraMoves.NORMAL_TERA_SHARD);
                        entries.add(TeraMoves.POISON_TERA_SHARD);
                        entries.add(TeraMoves.PSYCHIC_TERA_SHARD);
                        entries.add(TeraMoves.ROCK_TERA_SHARD);
                        entries.add(TeraMoves.STEEL_TERA_SHARD);
                        entries.add(TeraMoves.STELLAR_TERA_SHARD);
                        entries.add(TeraMoves.WATER_TERA_SHARD);
                    }))
                    .build());

    public static final ItemGroup FORMS_TAB = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(MegaShowdown.MOD_ID, "forms_tab"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.DNA_SPLICER))
                    .displayName(Text.translatable("creativeTab.mega_showdown.forms_tab"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModItems.N_LUNARIZER);
                        entries.add(ModItems.N_SOLARIZER);
                        entries.add(ModItems.DNA_SPLICER);
                        entries.add(MegaStones.BLUE_ORB);
                        entries.add(MegaStones.RED_ORB);
                        entries.add(ModItems.RUSTED_SWORD);
                        entries.add(ModItems.RUSTED_SHIELD);
                        entries.add(ModItems.PRISON_BOTTLE);
                        entries.add(ModItems.REINS_OF_UNITY);
                        entries.add(ModItems.GRACIDEA_FLOWER);
                        entries.add(ModItems.SCROLL_OF_DARKNESS);
                        entries.add(ModItems.SCROLL_OF_WATERS);
                        entries.add(ModItems.WELLSPRING_MASK);
                        entries.add(ModItems.CORNERSTONE_MASK);
                        entries.add(ModItems.HEARTHFLAME_MASK);
                        entries.add(ModItems.STAR_CORE);
                        entries.add(ModItems.GRISEOUS_ORB);
                        entries.add(ModItems.ADAMANT_ORB);
                        entries.add(ModItems.LUSTROUS_GLOBE);
                        entries.add(ModItems.ASH_CAP);
                        entries.add(RotomFormes.FAN);
                        entries.add(RotomFormes.FRIDGEUNIT);
                        entries.add(RotomFormes.MOWUNIT);
                        entries.add(RotomFormes.OVENUNIT);
                        entries.add(RotomFormes.WASHUNIT);
                        entries.add(RotomFormes.ROTOM_CATALOGUE);

                        entries.add(ModItems.FLAME_PLATE);
                        entries.add(ModItems.SPLASH_PLATE);
                        entries.add(ModItems.ZAP_PLATE);
                        entries.add(ModItems.MEADOW_PLATE);
                        entries.add(ModItems.ICICLE_PLATE);
                        entries.add(ModItems.FIST_PLATE);
                        entries.add(ModItems.TOXIC_PLATE);
                        entries.add(ModItems.EARTH_PLATE);
                        entries.add(ModItems.SKY_PLATE);
                        entries.add(ModItems.MIND_PLATE);
                        entries.add(ModItems.INSECT_PLATE);
                        entries.add(ModItems.STONE_PLATE);
                        entries.add(ModItems.SPOOKY_PLATE);
                        entries.add(ModItems.DRACO_PLATE);
                        entries.add(ModItems.DREAD_PLATE);
                        entries.add(ModItems.IRON_PLATE);
                        entries.add(ModItems.PIXIE_PLATE);

                        entries.add(ModItems.BUG_MEMORY);
                        entries.add(ModItems.DARK_MEMORY);
                        entries.add(ModItems.DRAGON_MEMORY);
                        entries.add(ModItems.ELECTRIC_MEMORY);
                        entries.add(ModItems.FAIRY_MEMORY);
                        entries.add(ModItems.FIGHTING_MEMORY);
                        entries.add(ModItems.FIRE_MEMORY);
                        entries.add(ModItems.FLYING_MEMORY);
                        entries.add(ModItems.GHOST_MEMORY);
                        entries.add(ModItems.GRASS_MEMORY);
                        entries.add(ModItems.GROUND_MEMORY);
                        entries.add(ModItems.ICE_MEMORY);
                        entries.add(ModItems.POISON_MEMORY);
                        entries.add(ModItems.PSYCHIC_MEMORY);
                        entries.add(ModItems.ROCK_MEMORY);
                        entries.add(ModItems.STEEL_MEMORY);
                        entries.add(ModItems.WATER_MEMORY);
                    }))
                    .build());

    public static void registerItemGroups(){

    }
}
