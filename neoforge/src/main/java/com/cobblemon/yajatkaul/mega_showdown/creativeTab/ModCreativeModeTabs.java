package com.cobblemon.yajatkaul.mega_showdown.creativeTab;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.ZMoves;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MegaShowdown.MOD_ID);

    public static final Supplier<CreativeModeTab> MEGA_SHOWDOWN_TAB = CREATIVE_MODE_TAB.register("mega_showdown_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(MegaStones.KEYSTONE.get()))
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

                        // Blocks
                        output.accept(MegaOres.ABOMASITE_ORE);
                        output.accept(MegaOres.ABSOLITE_ORE);
                        output.accept(MegaOres.AERODACTYLITE_ORE);
                        output.accept(MegaOres.AGGRONITE_ORE);
                        output.accept(MegaOres.ALAKAZITE_ORE);
                        output.accept(MegaOres.ALTARIANITE_ORE);
                        output.accept(MegaOres.AMPHAROSITE_ORE);
                        output.accept(MegaOres.AUDINITE_ORE);
                        output.accept(MegaOres.BANETTITE_ORE);
                        output.accept(MegaOres.BEEDRILLITE_ORE);
                        output.accept(MegaOres.BLASTOISINITE_ORE);
                        output.accept(MegaOres.BLAZIKENITE_ORE);
                        output.accept(MegaOres.CAMERUPTITE_ORE);
                        output.accept(MegaOres.CHARIZARDITE_X_ORE);
                        output.accept(MegaOres.CHARIZARDITE_Y_ORE);
                        output.accept(MegaOres.DIANCITE_ORE);
                        output.accept(MegaOres.GALLADITE_ORE);
                        output.accept(MegaOres.GARCHOMPITE_ORE);
                        output.accept(MegaOres.GARDEVOIRITE_ORE);
                        output.accept(MegaOres.GENGARITE_ORE);
                        output.accept(MegaOres.GLALITITE_ORE);
                        output.accept(MegaOres.GYARADOSITE_ORE);
                        output.accept(MegaOres.HERACRONITE_ORE);
                        output.accept(MegaOres.HOUNDOOMINITE_ORE);
                        output.accept(MegaOres.KANGASKHANITE_ORE);
                        output.accept(MegaOres.LATIASITE_ORE);
                        output.accept(MegaOres.LATIOSITE_ORE);
                        output.accept(MegaOres.LOPUNNITE_ORE);
                        output.accept(MegaOres.LUCARIONITE_ORE);
                        output.accept(MegaOres.MANECTITE_ORE);
                        output.accept(MegaOres.MAWILITE_ORE);
                        output.accept(MegaOres.MEDICHAMITE_ORE);
                        output.accept(MegaOres.METAGROSSITE_ORE);
                        output.accept(MegaOres.MEWTWONITE_X_ORE);
                        output.accept(MegaOres.MEWTWONITE_Y_ORE);
                        output.accept(MegaOres.PIDGEOTITE_ORE);
                        output.accept(MegaOres.PINSIRITE_ORE);
                        output.accept(MegaOres.SABLENITE_ORE);
                        output.accept(MegaOres.SALAMENCITE_ORE);
                        output.accept(MegaOres.SCEPTILITE_ORE);
                        output.accept(MegaOres.SCIZORITE_ORE);
                        output.accept(MegaOres.SHARPEDONITE_ORE);
                        output.accept(MegaOres.SLOWBRONITE_ORE);
                        output.accept(MegaOres.STEELIXITE_ORE);
                        output.accept(MegaOres.SWAMPERTITE_ORE);
                        output.accept(MegaOres.TYRANITARITE_ORE);
                        output.accept(MegaOres.VENUSAURITE_ORE);

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

                        output.accept(MegaStones.BLUE_ORB);
                        output.accept(MegaStones.RED_ORB);

                        output.accept(ModItems.N_LUNARIZER);
                        output.accept(ModItems.N_SOLARIZER);
                    })
                    .build());

    public static final Supplier<CreativeModeTab> Z_MOVES_TAB = CREATIVE_MODE_TAB.register("z_moves_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ZMoves.BLANK_Z.get()))
                    .title(Component.translatable("creativeTab.mega_showdown.z_moves_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ZMoves.Z_RING);
                        output.accept(ZMoves.ALORAICHIUM_Z);
                        output.accept(ZMoves.BLANK_Z);
                        output.accept(ZMoves.BUGINIUM_Z);
                        output.accept(ZMoves.DARKINIUM_Z);
                        output.accept(ZMoves.DECIDIUM_Z);
                        output.accept(ZMoves.DRAGONIUM_Z);
                        output.accept(ZMoves.EEVIUM_Z);
                        output.accept(ZMoves.ELECTRIUM_Z);
                        output.accept(ZMoves.FAIRIUM_Z);
                        output.accept(ZMoves.FIGHTINIUM_Z);
                        output.accept(ZMoves.FIRIUM_Z);
                        output.accept(ZMoves.FLYINIUM_Z);
                        output.accept(ZMoves.GHOSTIUM_Z);
                        output.accept(ZMoves.GRASSIUM_Z);
                        output.accept(ZMoves.GROUNDIUM_Z);
                        output.accept(ZMoves.ICIUM_Z);
                        output.accept(ZMoves.INCINIUM_Z);
                        output.accept(ZMoves.KOMMONIUM_Z);
                        output.accept(ZMoves.LUNALIUM_Z);
                        output.accept(ZMoves.LYCANIUM_Z);
                        output.accept(ZMoves.MARSHADIUM_Z);
                        output.accept(ZMoves.MEWNIUM_Z);
                        output.accept(ZMoves.MIMIKIUM_Z);
                        output.accept(ZMoves.NORMALIUM_Z);
                        output.accept(ZMoves.PIKANIUM_Z);
                        output.accept(ZMoves.PIKASHUNIUM_Z);
                        output.accept(ZMoves.POISONIUM_Z);
                        output.accept(ZMoves.PRIMARIUM_Z);
                        output.accept(ZMoves.PSYCHIUM_Z);
                        output.accept(ZMoves.ROCKIUM_Z);
                        output.accept(ZMoves.SNORLIUM_Z);
                        output.accept(ZMoves.SOLGANIUM_Z);
                        output.accept(ZMoves.STEELIUM_Z);
                        output.accept(ZMoves.TAPUNIUM_Z);
                        output.accept(ZMoves.ULTRANECROZIUM_Z);
                        output.accept(ZMoves.WATERIUM_Z);

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
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
