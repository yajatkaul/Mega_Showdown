package com.cobblemon.yajatkaul.mega_showdown.creativeTab;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.item.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MegaShowdown.MOD_ID);

    public static final Supplier<CreativeModeTab> MEGA_TAB = CREATIVE_MODE_TAB.register("mega_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MEGA_BRACELET.get()))
                    .title(Component.translatable("creativeTab.mega_showdown.mega_showdown_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        // Stones
                        output.accept(MegaStones.MEGA_STONE);
                        output.accept(MegaStones.ABOMASITE);
                        output.accept(MegaStones.ABSOLITE);
                        output.accept(MegaStones.AERODACTYLITE);
                        output.accept(MegaStones.AGGRONITE);
                        output.accept(MegaStones.ALAKAZITE);
                        output.accept(MegaStones.ALTARIANITE);
                        output.accept(MegaStones.AMPHAROSITE);
                        output.accept(MegaStones.AUDINITE);
                        output.accept(MegaStones.BANETTITE);
                        output.accept(MegaStones.BEEDRILLITE);
                        output.accept(MegaStones.BLASTOISINITE);
                        output.accept(MegaStones.BLAZIKENITE);
                        output.accept(MegaStones.CAMERUPTITE);
                        output.accept(MegaStones.CHARIZARDITE_X);
                        output.accept(MegaStones.CHARIZARDITE_Y);
                        output.accept(MegaStones.DIANCITE);
                        output.accept(MegaStones.GALLADITE);
                        output.accept(MegaStones.GARCHOMPITE);
                        output.accept(MegaStones.GARDEVOIRITE);
                        output.accept(MegaStones.GENGARITE);
                        output.accept(MegaStones.GLALITITE);
                        output.accept(MegaStones.GYARADOSITE);
                        output.accept(MegaStones.HERACRONITE);
                        output.accept(MegaStones.HOUNDOOMINITE);
                        output.accept(MegaStones.KANGASKHANITE);
                        output.accept(MegaStones.LATIASITE);
                        output.accept(MegaStones.LATIOSITE);
                        output.accept(MegaStones.LOPUNNITE);
                        output.accept(MegaStones.LUCARIONITE);
                        output.accept(MegaStones.MANECTITE);
                        output.accept(MegaStones.MAWILITE);
                        output.accept(MegaStones.MEDICHAMITE);
                        output.accept(MegaStones.METAGROSSITE);
                        output.accept(MegaStones.MEWTWONITE_Y);
                        output.accept(MegaStones.MEWTWONITE_X);
                        output.accept(MegaStones.PIDGEOTITE);
                        output.accept(MegaStones.PINSIRITE);
                        output.accept(MegaStones.SABLENITE);
                        output.accept(MegaStones.SALAMENCITE);
                        output.accept(MegaStones.SCIZORITE);
                        output.accept(MegaStones.SHARPEDONITE);
                        output.accept(MegaStones.SCEPTILITE);
                        output.accept(MegaStones.SLOWBRONITE);
                        output.accept(MegaStones.STEELIXITE);
                        output.accept(MegaStones.SWAMPERTITE);
                        output.accept(MegaStones.TYRANITARITE);
                        output.accept(MegaStones.VENUSAURITE);

                        output.accept(MegaStones.KEYSTONE);

                        // Device
                        output.accept(ModItems.MEGA_BRACELET);
                        output.accept(ModItems.MEGA_RED_BRACELET);
                        output.accept(ModItems.MEGA_YELLOW_BRACELET);
                        output.accept(ModItems.MEGA_PINK_BRACELET);
                        output.accept(ModItems.MEGA_GREEN_BRACELET);
                        output.accept(ModItems.MEGA_BLUE_BRACELET);
                        output.accept(ModItems.MEGA_BLACK_BRACELET);
                        output.accept(ModItems.MEGA_RING);
                        output.accept(ModItems.LYSANDRE_RING);
                        output.accept(ModItems.BRENDAN_MEGA_CUFF);
                        output.accept(ModItems.MAXIE_GLASSES);
                        output.accept(ModItems.ARCHIE_ANCHOR);
                        output.accept(ModItems.KORRINA_GLOVE);
                        output.accept(ModItems.MAY_BRACELET);
                        output.accept(ModItems.LISIA_MEGA_TIARA);

                        output.accept(MegaOres.KEYSTONE_ORE);

                        output.accept(MegaOres.MEGA_STONE_CRYSTAL);

                        output.accept(ModBlocks.MEGA_METEOROID_BLOCK);
                        output.accept(ModBlocks.MEGA_EVO_BLOCK);
                        output.accept(ModBlocks.MEGA_EVO_BRICK);
                        output.accept(ModBlocks.CHISELED_MEGA_EVO_BRICK);
                        output.accept(ModBlocks.CHISELED_MEGA_EVO_BLOCK);
                        output.accept(ModBlocks.POLISHED_MEGA_EVO_BLOCK);

                        output.accept(ModBlocks.KEYSTONE_BLOCK);

                        output.accept(MegaOres.MEGA_METEORID_DAWN_ORE);
                        output.accept(MegaOres.MEGA_METEORID_DUSK_ORE);
                        output.accept(MegaOres.MEGA_METEORID_FIRE_ORE);
                        output.accept(MegaOres.MEGA_METEORID_ICE_ORE);
                        output.accept(MegaOres.MEGA_METEORID_LEAF_ORE);
                        output.accept(MegaOres.MEGA_METEORID_MOON_ORE);
                        output.accept(MegaOres.MEGA_METEORID_SHINY_ORE);
                        output.accept(MegaOres.MEGA_METEORID_SUN_ORE);
                        output.accept(MegaOres.MEGA_METEORID_THUNDER_ORE);
                        output.accept(MegaOres.MEGA_METEORID_WATER_ORE);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> Z_MOVES_TAB = CREATIVE_MODE_TAB.register("z_moves_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ZCrystals.Z_RING_POWER.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "mega_showdown_tab"))
                    .title(Component.translatable("creativeTab.mega_showdown.z_moves_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ZCrystals.Z_RING);
                        output.accept(ZCrystals.Z_RING_BLACK);
                        output.accept(ZCrystals.Z_RING_BLUE);
                        output.accept(ZCrystals.Z_RING_GREEN);
                        output.accept(ZCrystals.Z_RING_PINK);
                        output.accept(ZCrystals.Z_RING_YELLOW);
                        output.accept(ZCrystals.Z_RING_RED);
                        output.accept(ZCrystals.OLIVIAS_Z_RING);
                        output.accept(ZCrystals.HAPUS_Z_RING);
                        output.accept(ZCrystals.Z_RING_POWER);
                        output.accept(ZCrystals.OLIVIA_Z_POWER_RING);
                        output.accept(ZCrystals.GLADION_Z_POWER_RING);
                        output.accept(ZCrystals.NANU_Z_POWER_RING);
                        output.accept(ZCrystals.HAPU_Z_POWER_RING);
                        output.accept(ZCrystals.ROCKET_Z_POWER_RING);
                        output.accept(ZCrystals.ALORAICHIUM_Z);
                        output.accept(ZCrystals.BLANK_Z);
                        output.accept(ZCrystals.BUGINIUM_Z);
                        output.accept(ZCrystals.DARKINIUM_Z);
                        output.accept(ZCrystals.DECIDIUM_Z);
                        output.accept(ZCrystals.DRAGONIUM_Z);
                        output.accept(ZCrystals.EEVIUM_Z);
                        output.accept(ZCrystals.ELECTRIUM_Z);
                        output.accept(ZCrystals.FAIRIUM_Z);
                        output.accept(ZCrystals.FIGHTINIUM_Z);
                        output.accept(ZCrystals.FIRIUM_Z);
                        output.accept(ZCrystals.FLYINIUM_Z);
                        output.accept(ZCrystals.GHOSTIUM_Z);
                        output.accept(ZCrystals.GRASSIUM_Z);
                        output.accept(ZCrystals.GROUNDIUM_Z);
                        output.accept(ZCrystals.ICIUM_Z);
                        output.accept(ZCrystals.INCINIUM_Z);
                        output.accept(ZCrystals.KOMMONIUM_Z);
                        output.accept(ZCrystals.LUNALIUM_Z);
                        output.accept(ZCrystals.LYCANIUM_Z);
                        output.accept(ZCrystals.MARSHADIUM_Z);
                        output.accept(ZCrystals.MEWNIUM_Z);
                        output.accept(ZCrystals.MIMIKIUM_Z);
                        output.accept(ZCrystals.NORMALIUM_Z);
                        output.accept(ZCrystals.PIKANIUM_Z);
                        output.accept(ZCrystals.PIKASHUNIUM_Z);
                        output.accept(ZCrystals.POISONIUM_Z);
                        output.accept(ZCrystals.PRIMARIUM_Z);
                        output.accept(ZCrystals.PSYCHIUM_Z);
                        output.accept(ZCrystals.ROCKIUM_Z);
                        output.accept(ZCrystals.SNORLIUM_Z);
                        output.accept(ZCrystals.SOLGANIUM_Z);
                        output.accept(ZCrystals.STEELIUM_Z);
                        output.accept(ZCrystals.TAPUNIUM_Z);
                        output.accept(ZCrystals.ULTRANECROZIUM_Z);
                        output.accept(ZCrystals.WATERIUM_Z);
                        output.accept(ZCrystals.SPARKLING_STONE_LIGHT);
                        output.accept(ZCrystals.SPARKLING_STONE_DARK);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> TERA_TAB = CREATIVE_MODE_TAB.register("tera_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(TeraMoves.TERA_ORB.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "z_moves_tab"))
                    .title(Component.translatable("creativeTab.mega_showdown.tera_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(TeraMoves.TERA_ORB);
                        output.accept(TeraMoves.BUG_TERA_SHARD);
                        output.accept(TeraMoves.DARK_TERA_SHARD);
                        output.accept(TeraMoves.DRAGON_TERA_SHARD);
                        output.accept(TeraMoves.ELECTRIC_TERA_SHARD);
                        output.accept(TeraMoves.FAIRY_TERA_SHARD);
                        output.accept(TeraMoves.FIGHTING_TERA_SHARD);
                        output.accept(TeraMoves.FIRE_TERA_SHARD);
                        output.accept(TeraMoves.FLYING_TERA_SHARD);
                        output.accept(TeraMoves.GHOST_TERA_SHARD);
                        output.accept(TeraMoves.GRASS_TERA_SHARD);
                        output.accept(TeraMoves.GROUND_TERA_SHARD);
                        output.accept(TeraMoves.ICE_TERA_SHARD);
                        output.accept(TeraMoves.NORMAL_TERA_SHARD);
                        output.accept(TeraMoves.POISON_TERA_SHARD);
                        output.accept(TeraMoves.PSYCHIC_TERA_SHARD);
                        output.accept(TeraMoves.ROCK_TERA_SHARD);
                        output.accept(TeraMoves.STEEL_TERA_SHARD);
                        output.accept(TeraMoves.STELLAR_TERA_SHARD);
                        output.accept(TeraMoves.WATER_TERA_SHARD);
                        output.accept(KeyItems.LIKOS_PENDANT);
                        output.accept(ModBlocks.DORMANT_CRYSTAL);

                    })
                    .build());

    public static final Supplier<CreativeModeTab> FORMS_TAB = CREATIVE_MODE_TAB.register("forms_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(FormeChangeItems.DNA_SPLICER.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "tera_tab"))
                    .title(Component.translatable("creativeTab.mega_showdown.forms_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(FormeChangeItems.N_LUNARIZER);
                        output.accept(FormeChangeItems.N_SOLARIZER);
                        output.accept(FormeChangeItems.DNA_SPLICER);
                        output.accept(MegaStones.BLUE_ORB);
                        output.accept(MegaStones.RED_ORB);
                        output.accept(FormeChangeItems.RUSTED_SWORD);
                        output.accept(FormeChangeItems.RUSTED_SHIELD);
                        output.accept(FormeChangeItems.PRISON_BOTTLE);
                        output.accept(FormeChangeItems.REINS_OF_UNITY);
                        output.accept(FormeChangeItems.GRACIDEA_FLOWER);
                        output.accept(ModItems.SCROLL_OF_WATERS);
                        output.accept(ModItems.SCROLL_OF_DARKNESS);
                        output.accept(FormeChangeItems.WELLSPRING_MASK);
                        output.accept(FormeChangeItems.CORNERSTONE_MASK);
                        output.accept(FormeChangeItems.HEARTHFLAME_MASK);
                        output.accept(FormeChangeItems.STAR_CORE);
                        output.accept(FormeChangeItems.GRISEOUS_CORE);
                        output.accept(FormeChangeItems.LUSTROUS_GLOBE);
                        output.accept(FormeChangeItems.ADAMANT_CRYSTAL);
                        output.accept(FormeChangeItems.ASH_CAP);
                        output.accept(FormeChangeItems.PIKA_CASE);
                        output.accept(RotomFormes.WASHINGUNIT);
                        output.accept(RotomFormes.FANUNIT);
                        output.accept(RotomFormes.MOWUNIT);
                        output.accept(RotomFormes.FRIDGEUNIT);
                        output.accept(RotomFormes.OVENUNIT);
                        output.accept(RotomFormes.ROTOM_CATALOGUE);

                        output.accept(FormeChangeItems.FLAME_PLATE);
                        output.accept(FormeChangeItems.SPLASH_PLATE);
                        output.accept(FormeChangeItems.ZAP_PLATE);
                        output.accept(FormeChangeItems.MEADOW_PLATE);
                        output.accept(FormeChangeItems.ICICLE_PLATE);
                        output.accept(FormeChangeItems.FIST_PLATE);
                        output.accept(FormeChangeItems.TOXIC_PLATE);
                        output.accept(FormeChangeItems.EARTH_PLATE);
                        output.accept(FormeChangeItems.SKY_PLATE);
                        output.accept(FormeChangeItems.MIND_PLATE);
                        output.accept(FormeChangeItems.INSECT_PLATE);
                        output.accept(FormeChangeItems.STONE_PLATE);
                        output.accept(FormeChangeItems.SPOOKY_PLATE);
                        output.accept(FormeChangeItems.DRACO_PLATE);
                        output.accept(FormeChangeItems.DREAD_PLATE);
                        output.accept(FormeChangeItems.IRON_PLATE);
                        output.accept(FormeChangeItems.PIXIE_PLATE);

                        output.accept(FormeChangeItems.BUG_MEMORY);
                        output.accept(FormeChangeItems.DARK_MEMORY);
                        output.accept(FormeChangeItems.DRAGON_MEMORY);
                        output.accept(FormeChangeItems.ELECTRIC_MEMORY);
                        output.accept(FormeChangeItems.FAIRY_MEMORY);
                        output.accept(FormeChangeItems.FIGHTING_MEMORY);
                        output.accept(FormeChangeItems.FIRE_MEMORY);
                        output.accept(FormeChangeItems.FLYING_MEMORY);
                        output.accept(FormeChangeItems.GHOST_MEMORY);
                        output.accept(FormeChangeItems.GRASS_MEMORY);
                        output.accept(FormeChangeItems.GROUND_MEMORY);
                        output.accept(FormeChangeItems.ICE_MEMORY);
                        output.accept(FormeChangeItems.POISON_MEMORY);
                        output.accept(FormeChangeItems.PSYCHIC_MEMORY);
                        output.accept(FormeChangeItems.ROCK_MEMORY);
                        output.accept(FormeChangeItems.STEEL_MEMORY);
                        output.accept(FormeChangeItems.WATER_MEMORY);

                        output.accept(FormeChangeItems.BURN_DRIVE);
                        output.accept(FormeChangeItems.DOUSE_DRIVE);
                        output.accept(FormeChangeItems.CHILL_DRIVE);
                        output.accept(FormeChangeItems.SHOCK_DRIVE);

                        output.accept(FormeChangeItems.RED_NECTAR);
                        output.accept(FormeChangeItems.PINK_NECTAR);
                        output.accept(FormeChangeItems.YELLOW_NECTAR);
                        output.accept(FormeChangeItems.PURPLE_NECTAR);

                        output.accept(FormeChangeItems.DEOXYS_METEORITE);

                        output.accept(FormeChangeItems.REVEAL_GLASS);
                        output.accept(FormeChangeItems.ZYGARDE_CORE);
                        output.accept(FormeChangeItems.ZYGARDE_CELL);
                        output.accept(FormeChangeItems.ZYGARDE_CUBE);
                        output.accept(ModBlocks.REASSEMBLY_UNIT);

                        output.accept(FormeChangeItems.HEART_TRIM);
                        output.accept(FormeChangeItems.STAR_TRIM);
                        output.accept(FormeChangeItems.DIAMOND_TRIM);
                        output.accept(FormeChangeItems.DEBUTANTE_TRIM);
                        output.accept(FormeChangeItems.MATRON_TRIM);
                        output.accept(FormeChangeItems.DANDY_TRIM);
                        output.accept(FormeChangeItems.LA_REINE_TRIM);
                        output.accept(FormeChangeItems.KABUKI_TRIM);
                        output.accept(FormeChangeItems.PHARAOH_TRIM);
                        output.accept(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> COMPI_TAB = CREATIVE_MODE_TAB.register("compi_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(CompiItems.BOOSTER_ENERGY.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "forms_tab"))
                    .title(Component.translatable("creativeTab.mega_showdown.compi_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(CompiItems.BOOSTER_ENERGY);
                        output.accept(CompiItems.LEGEND_PLATE);
                        output.accept(CompiItems.ADAMANT_ORB);
                        output.accept(CompiItems.GRISEOUS_ORB);
                        output.accept(CompiItems.LUSTROUS_ORB);
                        output.accept(CompiItems.CLEAR_AMULET);
                        output.accept(CompiItems.LAGGING_TAIL);
                        output.accept(CompiItems.ADRENALINEORB);
                        output.accept(CompiItems.SOULDEW);
                        output.accept(CompiItems.GRIPCLAW);
                        output.accept(CompiItems.LUMINOUS_MOSS);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> MEGA_SHOWDOWN_TAB = CREATIVE_MODE_TAB.register("mega_showdown",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.OMNI_RING.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "compi_tab"))
                    .title(Component.translatable("creativeTab.mega_showdown.key_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.PEDESTAL);
                        output.accept(ModItems.OMNI_RING);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> DYNAMAX_TAB = CREATIVE_MODE_TAB.register("dynamax_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(DynamaxItems.DYNAMAX_BAND.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "key_tab"))
                    .title(Component.translatable("creativeTab.mega_showdown.dynamax_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(DynamaxItems.DYNAMAX_BAND);
                        output.accept(DynamaxItems.DYNAMAX_CANDY);
                        output.accept(DynamaxItems.WISHING_STAR);
                        output.accept(DynamaxItems.MAX_HONEY);
                        output.accept(DynamaxItems.MAX_MUSHROOM);
                        output.accept(DynamaxItems.MAX_SOUP);
                        output.accept(DynamaxItems.SWEET_MAX_SOUP);
                        output.accept(ModBlocks.POWER_SPOT);
                        output.accept(ModBlocks.WISHING_STAR_CRYSTAL);
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
