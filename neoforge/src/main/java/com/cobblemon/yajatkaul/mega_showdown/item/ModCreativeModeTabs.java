package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
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
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.KEYSTONE.get()))
                    .title(Component.translatable("creativeTab.mega_showdown.mega_showdown_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        // Stones
                        output.accept(ModItems.MEGA_STONE);
                        output.accept(ModItems.ABOMASITE);
                        output.accept(ModItems.ABSOLITE);
                        output.accept(ModItems.AERODACTYLITE);
                        output.accept(ModItems.AGGRONITE);
                        output.accept(ModItems.ALAKAZITE);
                        output.accept(ModItems.ALTARIANITE);
                        output.accept(ModItems.AMPHAROSITE);
                        output.accept(ModItems.AUDINITE);
                        output.accept(ModItems.BANETTITE);
                        output.accept(ModItems.BEEDRILLITE);
                        output.accept(ModItems.BLASTOISINITE);
                        output.accept(ModItems.BLAZIKENITE);
                        output.accept(ModItems.CAMERUPTITE);
                        output.accept(ModItems.CHARIZARDITE_X);
                        output.accept(ModItems.CHARIZARDITE_Y);
                        output.accept(ModItems.DIANCITE);
                        output.accept(ModItems.GALLADITE);
                        output.accept(ModItems.GARCHOMPITE);
                        output.accept(ModItems.GARDEVOIRITE);
                        output.accept(ModItems.GENGARITE);
                        output.accept(ModItems.GLALITITE);
                        output.accept(ModItems.GYARADOSITE);
                        output.accept(ModItems.HERACRONITE);
                        output.accept(ModItems.HOUNDOOMINITE);
                        output.accept(ModItems.KANGASKHANITE);
                        output.accept(ModItems.LATIASITE);
                        output.accept(ModItems.LATIOSITE);
                        output.accept(ModItems.LOPUNNITE);
                        output.accept(ModItems.LUCARIONITE);
                        output.accept(ModItems.MANECTITE);
                        output.accept(ModItems.MAWILITE);
                        output.accept(ModItems.MEDICHAMITE);
                        output.accept(ModItems.METAGROSSITE);
                        output.accept(ModItems.MEWTWONITE_Y);
                        output.accept(ModItems.MEWTWONITE_X);
                        output.accept(ModItems.PIDGEOTITE);
                        output.accept(ModItems.PINSIRITE);
                        output.accept(ModItems.SABLENITE);
                        output.accept(ModItems.SALAMENCITE);
                        output.accept(ModItems.SCIZORITE);
                        output.accept(ModItems.SHARPEDONITE);
                        output.accept(ModItems.SCEPTILITE);
                        output.accept(ModItems.SLOWBRONITE);
                        output.accept(ModItems.STEELIXITE);
                        output.accept(ModItems.SWAMPERTITE);
                        output.accept(ModItems.TYRANITARITE);
                        output.accept(ModItems.VENUSAURITE);

                        output.accept(ModItems.KEYSTONE);

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

                        // Utils
//                        output.accept(ModItems.DNASPLICERS);
//                        output.accept(ModItems.DOUSEDRIVE);
//                        output.accept(ModItems.DRACOPLATE);
//                        output.accept(ModItems.DRAGONMEMORY);
//                        output.accept(ModItems.DREADPLATE);
//                        output.accept(ModItems.EARTHPLATE);
//                        output.accept(ModItems.ELECTRICMEMORY);
//                        output.accept(ModItems.DARKMEMORY);
//                        output.accept(ModItems.CORNERSTONEMASK);
//                        output.accept(ModItems.BLUEORB);
//                        output.accept(ModItems.BUGMEMORY);
//                        output.accept(ModItems.BURNDRIVE);
//                        output.accept(ModItems.CHILLDRIVE);
//                        output.accept(ModItems.ADAMANTORB);

                        // Blocks
                        output.accept(ModBlocks.ABOMASITE_ORE);
                        output.accept(ModBlocks.ABSOLITE_ORE);
                        output.accept(ModBlocks.AERODACTYLITE_ORE);
                        output.accept(ModBlocks.AGGRONITE_ORE);
                        output.accept(ModBlocks.ALAKAZITE_ORE);
                        output.accept(ModBlocks.ALTARIANITE_ORE);
                        output.accept(ModBlocks.AMPHAROSITE_ORE);
                        output.accept(ModBlocks.AUDINITE_ORE);
                        output.accept(ModBlocks.BANETTITE_ORE);
                        output.accept(ModBlocks.BEEDRILLITE_ORE);
                        output.accept(ModBlocks.BLASTOISINITE_ORE);
                        output.accept(ModBlocks.BLAZIKENITE_ORE);
                        output.accept(ModBlocks.CAMERUPTITE_ORE);
                        output.accept(ModBlocks.CHARIZARDITE_X_ORE);
                        output.accept(ModBlocks.CHARIZARDITE_Y_ORE);
                        output.accept(ModBlocks.DIANCITE_ORE);
                        output.accept(ModBlocks.GALLADITE_ORE);
                        output.accept(ModBlocks.GARCHOMPITE_ORE);
                        output.accept(ModBlocks.GARDEVOIRITE_ORE);
                        output.accept(ModBlocks.GENGARITE_ORE);
                        output.accept(ModBlocks.GLALITITE_ORE);
                        output.accept(ModBlocks.GYARADOSITE_ORE);
                        output.accept(ModBlocks.HERACRONITE_ORE);
                        output.accept(ModBlocks.HOUNDOOMINITE_ORE);
                        output.accept(ModBlocks.KANGASKHANITE_ORE);
                        output.accept(ModBlocks.LATIASITE_ORE);
                        output.accept(ModBlocks.LATIOSITE_ORE);
                        output.accept(ModBlocks.LOPUNNITE_ORE);
                        output.accept(ModBlocks.LUCARIONITE_ORE);
                        output.accept(ModBlocks.MANECTITE_ORE);
                        output.accept(ModBlocks.MAWILITE_ORE);
                        output.accept(ModBlocks.MEDICHAMITE_ORE);
                        output.accept(ModBlocks.METAGROSSITE_ORE);
                        output.accept(ModBlocks.MEWTWONITE_X_ORE);
                        output.accept(ModBlocks.MEWTWONITE_Y_ORE);
                        output.accept(ModBlocks.PIDGEOTITE_ORE);
                        output.accept(ModBlocks.PINSIRITE_ORE);
                        output.accept(ModBlocks.SABLENITE_ORE);
                        output.accept(ModBlocks.SALAMENCITE_ORE);
                        output.accept(ModBlocks.SCEPTILITE_ORE);
                        output.accept(ModBlocks.SCIZORITE_ORE);
                        output.accept(ModBlocks.SHARPEDONITE_ORE);
                        output.accept(ModBlocks.SLOWBRONITE_ORE);
                        output.accept(ModBlocks.STEELIXITE_ORE);
                        output.accept(ModBlocks.SWAMPERTITE_ORE);
                        output.accept(ModBlocks.TYRANITARITE_ORE);
                        output.accept(ModBlocks.VENUSAURITE_ORE);

                        output.accept(ModBlocks.KEYSTONE_ORE);

                        output.accept(ModBlocks.MEGA_STONE_CRYSTAL);

                        output.accept(ModBlocks.MEGA_METEOROID_BLOCK);
                        output.accept(ModBlocks.MEGA_EVO_BLOCK);
                        output.accept(ModBlocks.MEGA_EVO_BRICK);
                        output.accept(ModBlocks.CHISELED_MEGA_EVO_BRICK);
                        output.accept(ModBlocks.CHISELED_MEGA_EVO_BLOCK);
                        output.accept(ModBlocks.POLISHED_MEGA_EVO_BLOCK);

                        output.accept(ModBlocks.KEYSTONE_BLOCK);

                        output.accept(ModBlocks.MEGA_METEORID_DAWN_ORE);
                        output.accept(ModBlocks.MEGA_METEORID_DUSK_ORE);
                        output.accept(ModBlocks.MEGA_METEORID_FIRE_ORE);
                        output.accept(ModBlocks.MEGA_METEORID_ICE_ORE);
                        output.accept(ModBlocks.MEGA_METEORID_LEAF_ORE);
                        output.accept(ModBlocks.MEGA_METEORID_MOON_ORE);
                        output.accept(ModBlocks.MEGA_METEORID_SHINY_ORE);
                        output.accept(ModBlocks.MEGA_METEORID_SUN_ORE);
                        output.accept(ModBlocks.MEGA_METEORID_THUNDER_ORE);
                        output.accept(ModBlocks.MEGA_METEORID_WATER_ORE);
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
