package com.cobblemon.yajatkaul.megamons.item;

import com.cobblemon.yajatkaul.megamons.MegaShowdown;
import com.cobblemon.yajatkaul.megamons.block.ModBlocks;
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
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CHARIZARDITE_X.get()))
                    .title(Component.translatable("creativeTab.mega_showdown.megamons_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        // Stones
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

                        // Device
                        output.accept(ModItems.MEGA_BRACELET);

                        // Utils
                        output.accept(ModItems.DNASPLICERS);
                        output.accept(ModItems.DOUSEDRIVE);
                        output.accept(ModItems.DRACOPLATE);
                        output.accept(ModItems.DRAGONMEMORY);
                        output.accept(ModItems.DREADPLATE);
                        output.accept(ModItems.EARTHPLATE);
                        output.accept(ModItems.ELECTRICMEMORY);
                        output.accept(ModItems.DARKMEMORY);
                        output.accept(ModItems.CORNERSTONEMASK);
                        output.accept(ModItems.BLUEORB);
                        output.accept(ModItems.BUGMEMORY);
                        output.accept(ModItems.BURNDRIVE);
                        output.accept(ModItems.CHILLDRIVE);
                        output.accept(ModItems.ADAMANTORB);

                        // Blocks
                        output.accept(ModBlocks.CHARIZARDITE_X_ORE);
                        output.accept(ModBlocks.MEGA_METEOROID_BLOCK);
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
