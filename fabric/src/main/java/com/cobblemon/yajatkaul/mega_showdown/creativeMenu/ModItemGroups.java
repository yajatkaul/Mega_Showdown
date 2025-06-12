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
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.MEGA_BRACELET))
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
                        entries.add(ModItems.LISIA_MEGA_TIARA);

                        entries.add(MegaOres.KEYSTONE_ORE);
                        entries.add(ModBlocks.KEYSTONE_BLOCK);

                        entries.add(ModBlocks.MEGA_METEOROID_BLOCK);
                        entries.add(ModBlocks.MEGA_EVO_BLOCK);
                        entries.add(ModBlocks.MEGA_EVO_BRICK);
                        entries.add(ModBlocks.CHISELED_MEGA_EVO_BRICK);
                        entries.add(ModBlocks.CHISELED_MEGA_EVO_BLOCK);
                        entries.add(ModBlocks.POLISHED_MEGA_EVO_BLOCK);

                        entries.add(MegaOres.MEGA_STONE_CRYSTAL);

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
            FabricItemGroup.builder().icon(() -> new ItemStack(ZCrystals.Z_RING_POWER))
                    .displayName(Text.translatable("creativeTab.mega_showdown.z_moves_tab"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ZCrystals.Z_RING);
                        entries.add(ZCrystals.Z_RING_BLACK);
                        entries.add(ZCrystals.Z_RING_BLUE);
                        entries.add(ZCrystals.Z_RING_GREEN);
                        entries.add(ZCrystals.Z_RING_PINK);
                        entries.add(ZCrystals.Z_RING_YELLOW);
                        entries.add(ZCrystals.Z_RING_RED);
                        entries.add(ZCrystals.OLIVIAS_Z_RING);
                        entries.add(ZCrystals.HAPUS_Z_RING);
                        entries.add(ZCrystals.Z_RING_POWER);
                        entries.add(ZCrystals.OLIVIA_Z_POWER_RING);
                        entries.add(ZCrystals.HAPU_Z_POWER_RING);
                        entries.add(ZCrystals.ROCKET_Z_POWER_RING);
                        entries.add(ZCrystals.GLADION_Z_POWER_RING);
                        entries.add(ZCrystals.NANU_Z_POWER_RING);
                        entries.add(ZCrystals.ALORAICHIUM_Z);
                        entries.add(ZCrystals.BLANK_Z);
                        entries.add(ZCrystals.BUGINIUM_Z);
                        entries.add(ZCrystals.DARKINIUM_Z);
                        entries.add(ZCrystals.DECIDIUM_Z);
                        entries.add(ZCrystals.DRAGONIUM_Z);
                        entries.add(ZCrystals.EEVIVIUM_Z);
                        entries.add(ZCrystals.ELECTRIUM_Z);
                        entries.add(ZCrystals.FAIRIUM_Z);
                        entries.add(ZCrystals.FIGHTINIUM_Z);
                        entries.add(ZCrystals.FIRIUM_Z);
                        entries.add(ZCrystals.FLYINIUM_Z);
                        entries.add(ZCrystals.GHOSTIUM_Z);
                        entries.add(ZCrystals.GRASSIUM_Z);
                        entries.add(ZCrystals.GROUNDIUM_Z);
                        entries.add(ZCrystals.ICIUM_Z);
                        entries.add(ZCrystals.INCINIUM_Z);
                        entries.add(ZCrystals.KOMMONIUM_Z);
                        entries.add(ZCrystals.LUNALIUM_Z);
                        entries.add(ZCrystals.LYCANIUM_Z);
                        entries.add(ZCrystals.MARSHADIUM_Z);
                        entries.add(ZCrystals.MEWNIUM_Z);
                        entries.add(ZCrystals.MIMIKIUM_Z);
                        entries.add(ZCrystals.NORMALIUM_Z);
                        entries.add(ZCrystals.PIKANIUM_Z);
                        entries.add(ZCrystals.PIKASHUNIUM_Z);
                        entries.add(ZCrystals.POISONIUM_Z);
                        entries.add(ZCrystals.PRIMARIUM_Z);
                        entries.add(ZCrystals.PSYCHIUM_Z);
                        entries.add(ZCrystals.ROCKIUM_Z);
                        entries.add(ZCrystals.SNORLIUM_Z);
                        entries.add(ZCrystals.SOLGANIUM_Z);
                        entries.add(ZCrystals.STEELIUM_Z);
                        entries.add(ZCrystals.TAPUNIUM_Z);
                        entries.add(ZCrystals.ULTRANECROZIUM_Z);
                        entries.add(ZCrystals.WATERIUM_Z);
                        entries.add(ZCrystals.SPARKLING_STONE_LIGHT);
                        entries.add(ZCrystals.SPARKLING_STONE_DARK);
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
                        entries.add(KeyItems.LIKOS_PENDANT);
                        entries.add(ModBlocks.DORMANT_CRYSTAL);
                    }))
                    .build());

    public static final ItemGroup FORMS_TAB = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(MegaShowdown.MOD_ID, "forms_tab"),
            FabricItemGroup.builder().icon(() -> new ItemStack(FormeChangeItems.DNA_SPLICER))
                    .displayName(Text.translatable("creativeTab.mega_showdown.forms_tab"))
                    .entries(((displayContext, entries) -> {
                        entries.add(FormeChangeItems.N_LUNARIZER);
                        entries.add(FormeChangeItems.N_SOLARIZER);
                        entries.add(FormeChangeItems.DNA_SPLICER);
                        entries.add(MegaStones.BLUE_ORB);
                        entries.add(MegaStones.RED_ORB);
                        entries.add(FormeChangeItems.RUSTED_SWORD);
                        entries.add(FormeChangeItems.RUSTED_SHIELD);
                        entries.add(FormeChangeItems.PRISON_BOTTLE);
                        entries.add(FormeChangeItems.REINS_OF_UNITY);
                        entries.add(FormeChangeItems.GRACIDEA_FLOWER);
                        entries.add(ModItems.SCROLL_OF_DARKNESS);
                        entries.add(ModItems.SCROLL_OF_WATERS);
                        entries.add(FormeChangeItems.WELLSPRING_MASK);
                        entries.add(FormeChangeItems.CORNERSTONE_MASK);
                        entries.add(FormeChangeItems.HEARTHFLAME_MASK);
                        entries.add(FormeChangeItems.STAR_CORE);
                        entries.add(FormeChangeItems.GRISEOUS_CORE);
                        entries.add(FormeChangeItems.ADAMANT_CRYSTAL);
                        entries.add(FormeChangeItems.LUSTROUS_GLOBE);
                        entries.add(FormeChangeItems.ASH_CAP);
                        entries.add(RotomFormes.FAN);
                        entries.add(RotomFormes.FRIDGEUNIT);
                        entries.add(RotomFormes.MOWUNIT);
                        entries.add(RotomFormes.OVENUNIT);
                        entries.add(RotomFormes.WASHUNIT);
                        entries.add(RotomFormes.ROTOM_CATALOGUE);

                        entries.add(FormeChangeItems.FLAME_PLATE);
                        entries.add(FormeChangeItems.SPLASH_PLATE);
                        entries.add(FormeChangeItems.ZAP_PLATE);
                        entries.add(FormeChangeItems.MEADOW_PLATE);
                        entries.add(FormeChangeItems.ICICLE_PLATE);
                        entries.add(FormeChangeItems.FIST_PLATE);
                        entries.add(FormeChangeItems.TOXIC_PLATE);
                        entries.add(FormeChangeItems.EARTH_PLATE);
                        entries.add(FormeChangeItems.SKY_PLATE);
                        entries.add(FormeChangeItems.MIND_PLATE);
                        entries.add(FormeChangeItems.INSECT_PLATE);
                        entries.add(FormeChangeItems.STONE_PLATE);
                        entries.add(FormeChangeItems.SPOOKY_PLATE);
                        entries.add(FormeChangeItems.DRACO_PLATE);
                        entries.add(FormeChangeItems.DREAD_PLATE);
                        entries.add(FormeChangeItems.IRON_PLATE);
                        entries.add(FormeChangeItems.PIXIE_PLATE);

                        entries.add(FormeChangeItems.BUG_MEMORY);
                        entries.add(FormeChangeItems.DARK_MEMORY);
                        entries.add(FormeChangeItems.DRAGON_MEMORY);
                        entries.add(FormeChangeItems.ELECTRIC_MEMORY);
                        entries.add(FormeChangeItems.FAIRY_MEMORY);
                        entries.add(FormeChangeItems.FIGHTING_MEMORY);
                        entries.add(FormeChangeItems.FIRE_MEMORY);
                        entries.add(FormeChangeItems.FLYING_MEMORY);
                        entries.add(FormeChangeItems.GHOST_MEMORY);
                        entries.add(FormeChangeItems.GRASS_MEMORY);
                        entries.add(FormeChangeItems.GROUND_MEMORY);
                        entries.add(FormeChangeItems.ICE_MEMORY);
                        entries.add(FormeChangeItems.POISON_MEMORY);
                        entries.add(FormeChangeItems.PSYCHIC_MEMORY);
                        entries.add(FormeChangeItems.ROCK_MEMORY);
                        entries.add(FormeChangeItems.STEEL_MEMORY);
                        entries.add(FormeChangeItems.WATER_MEMORY);

                        entries.add(FormeChangeItems.BURN_DRIVE);
                        entries.add(FormeChangeItems.DOUSE_DRIVE);
                        entries.add(FormeChangeItems.CHILL_DRIVE);
                        entries.add(FormeChangeItems.SHOCK_DRIVE);

                        entries.add(FormeChangeItems.PINK_NECTAR);
                        entries.add(FormeChangeItems.PURPLE_NECTAR);
                        entries.add(FormeChangeItems.YELLOW_DRIVE);
                        entries.add(FormeChangeItems.RED_NECTAR);

                        entries.add(FormeChangeItems.DEOXYS_METEORITE);

                        entries.add(FormeChangeItems.REVEAL_GLASS);
                        entries.add(FormeChangeItems.ZYGARDE_CORE);
                        entries.add(FormeChangeItems.ZYGARDE_CELL);
                        entries.add(FormeChangeItems.ZYGARDE_CUBE);
                        entries.add(ModBlocks.REASSEMBLY_UNIT);

                        entries.add(FormeChangeItems.HEART_TRIM);
                        entries.add(FormeChangeItems.STAR_TRIM);
                        entries.add(FormeChangeItems.DIAMOND_TRIM);
                        entries.add(FormeChangeItems.DEBUTANTE_TRIM);
                        entries.add(FormeChangeItems.MATRON_TRIM);
                        entries.add(FormeChangeItems.DANDY_TRIM);
                        entries.add(FormeChangeItems.LA_REINE_TRIM);
                        entries.add(FormeChangeItems.KABUKI_TRIM);
                        entries.add(FormeChangeItems.PHARAOH_TRIM);
                        entries.add(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE);
                    }))
                    .build());

    public static final ItemGroup COMPI_TAB = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(MegaShowdown.MOD_ID, "compi_tab"),
            FabricItemGroup.builder().icon(() -> new ItemStack(CompiItems.BOOSTER_ENERGY))
                    .displayName(Text.translatable("creativeTab.mega_showdown.compi_tab"))
                    .entries(((displayContext, entries) -> {
                        entries.add(CompiItems.BOOSTER_ENERGY);
                        entries.add(CompiItems.LEGEND_PLATE);
                        entries.add(CompiItems.ADAMANT_ORB);
                        entries.add(CompiItems.GRISEOUS_ORB);
                        entries.add(CompiItems.LUSTROUS_ORB);
                        entries.add(CompiItems.CLEAR_AMULET);
                        entries.add(CompiItems.LAGGING_TAIL);
                        entries.add(CompiItems.ADRENALINEORB);
                        entries.add(CompiItems.SOULDEW);
                        entries.add(CompiItems.GRIPCLAW);
                        entries.add(CompiItems.LUMINOUS_MOSS);
                    }))
                    .build());

    public static final ItemGroup KEY_TAB = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(MegaShowdown.MOD_ID, "key_tab"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.PEDESTAL))
                    .displayName(Text.translatable("creativeTab.mega_showdown.key_tab"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModBlocks.PEDESTAL);
                    }))
                    .build());

    public static final ItemGroup DYNAMAX_TAB = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(MegaShowdown.MOD_ID, "dynamax_tab"),
            FabricItemGroup.builder().icon(() -> new ItemStack(DynamaxItems.DYNAMAX_BAND))
                    .displayName(Text.translatable("creativeTab.mega_showdown.dynamax_tab"))
                    .entries(((displayContext, entries) -> {
                        entries.add(DynamaxItems.DYNAMAX_BAND);
                        entries.add(DynamaxItems.DYNAMAX_CANDY);
                        entries.add(DynamaxItems.WISHING_STAR);
                        entries.add(DynamaxItems.MAX_HONEY);
                        entries.add(DynamaxItems.MAX_MUSHROOM);
                        entries.add(DynamaxItems.MAX_SOUP);
                        entries.add(DynamaxItems.SWEET_MAX_SOUP);
                        entries.add(ModBlocks.POWER_SPOT);
                        entries.add(ModBlocks.WISHING_STAR_CRYSTAL);
                    }))
                    .build());

    public static void registerItemGroups() {

    }
}
