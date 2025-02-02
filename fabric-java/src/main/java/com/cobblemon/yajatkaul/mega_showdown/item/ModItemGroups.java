package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
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
                    .displayName(Text.translatable("itemgroup.mega_showdown.mega_showdown_tab"))
                    .entries(((displayContext, entries) -> {
                        entries.add(ModItems.ABOMASITE);
                        entries.add(ModItems.ABSOLITE);
                        entries.add(ModItems.AERODACTYLITE);
                        entries.add(ModItems.AGGRONITE);
                        entries.add(ModItems.ALAKAZITE);
                        entries.add(ModItems.ALTARIANITE);
                        entries.add(ModItems.AMPHAROSITE);
                        entries.add(ModItems.AUDINITE);
                        entries.add(ModItems.BANETTITE);
                        entries.add(ModItems.BEEDRILLITE);
                        entries.add(ModItems.BLASTOISINITE);
                        entries.add(ModItems.BLAZIKENITE);
                        entries.add(ModItems.CAMERUPTITE);
                        entries.add(ModItems.CHARIZARDITE_X);
                        entries.add(ModItems.CHARIZARDITE_Y);
                        entries.add(ModItems.DIANCITE);
                        entries.add(ModItems.GALLADITE);
                        entries.add(ModItems.GARCHOMPITE);
                        entries.add(ModItems.GARDEVOIRITE);
                        entries.add(ModItems.GENGARITE);
                        entries.add(ModItems.GLALITITE);
                        entries.add(ModItems.GYARADOSITE);
                        entries.add(ModItems.HERACRONITE);
                        entries.add(ModItems.HOUNDOOMINITE);
                        entries.add(ModItems.KANGASKHANITE);
                        entries.add(ModItems.LATIASITE);
                        entries.add(ModItems.LATIOSITE);
                        entries.add(ModItems.LOPUNNITE);
                        entries.add(ModItems.LUCARIONITE);
                        entries.add(ModItems.MANECTITE);
                        entries.add(ModItems.MAWILITE);
                        entries.add(ModItems.MEDICHAMITE);
                        entries.add(ModItems.METAGROSSITE);
                        entries.add(ModItems.MEWTWONITE_Y);
                        entries.add(ModItems.MEWTWONITE_X);
                        entries.add(ModItems.PIDGEOTITE);
                        entries.add(ModItems.PINSIRITE);
                        entries.add(ModItems.SABLENITE);
                        entries.add(ModItems.SALAMENCITE);
                        entries.add(ModItems.SCIZORITE);
                        entries.add(ModItems.SHARPEDONITE);
                        entries.add(ModItems.SCEPTILITE);
                        entries.add(ModItems.SLOWBRONITE);
                        entries.add(ModItems.STEELIXITE);
                        entries.add(ModItems.SWAMPERTITE);
                        entries.add(ModItems.TYRANITARITE);
                        entries.add(ModItems.VENUSAURITE);

                        entries.add(ModItems.KEYSTONE);

                        entries.add(ModItems.MEGA_BRACELET);

                        entries.add(ModBlocks.ABOMASITE_ORE);
                        entries.add(ModBlocks.ABSOLITE_ORE);
                        entries.add(ModBlocks.AERODACTYLITE_ORE);
                        entries.add(ModBlocks.AGGRONITE_ORE);
                        entries.add(ModBlocks.ALAKAZITE_ORE);
                        entries.add(ModBlocks.ALTARIANITE_ORE);
                        entries.add(ModBlocks.AMPHAROSITE_ORE);
                        entries.add(ModBlocks.AUDINITE_ORE);
                        entries.add(ModBlocks.BANETTITE_ORE);
                        entries.add(ModBlocks.BEEDRILLITE_ORE);
                        entries.add(ModBlocks.BLASTOISINITE_ORE);
                        entries.add(ModBlocks.BLAZIKENITE_ORE);
                        entries.add(ModBlocks.CAMERUPTITE_ORE);
                        entries.add(ModBlocks.CHARIZARDITE_X_ORE);
                        entries.add(ModBlocks.CHARIZARDITE_Y_ORE);
                        entries.add(ModBlocks.DIANCITE_ORE);
                        entries.add(ModBlocks.GALLADITE_ORE);
                        entries.add(ModBlocks.GARCHOMPITE_ORE);
                        entries.add(ModBlocks.GARDEVOIRITE_ORE);
                        entries.add(ModBlocks.GENGARITE_ORE);
                        entries.add(ModBlocks.GLALITITE_ORE);
                        entries.add(ModBlocks.GYARADOSITE_ORE);
                        entries.add(ModBlocks.HERACRONITE_ORE);
                        entries.add(ModBlocks.HOUNDOOMINITE_ORE);
                        entries.add(ModBlocks.KANGASKHANITE_ORE);
                        entries.add(ModBlocks.LATIASITE_ORE);
                        entries.add(ModBlocks.LATIOSITE_ORE);
                        entries.add(ModBlocks.LOPUNNITE_ORE);
                        entries.add(ModBlocks.LUCARIONITE_ORE);
                        entries.add(ModBlocks.MANECTITE_ORE);
                        entries.add(ModBlocks.MAWILITE_ORE);
                        entries.add(ModBlocks.MEDICHAMITE_ORE);
                        entries.add(ModBlocks.METAGROSSITE_ORE);
                        entries.add(ModBlocks.MEWTWONITE_X_ORE);
                        entries.add(ModBlocks.MEWTWONITE_Y_ORE);
                        entries.add(ModBlocks.PIDGEOTITE_ORE);
                        entries.add(ModBlocks.PINSIRITE_ORE);
                        entries.add(ModBlocks.SABLENITE_ORE);
                        entries.add(ModBlocks.SALAMENCITE_ORE);
                        entries.add(ModBlocks.SCEPTILITE_ORE);
                        entries.add(ModBlocks.SCIZORITE_ORE);
                        entries.add(ModBlocks.SHARPEDONITE_ORE);
                        entries.add(ModBlocks.SLOWBRONITE_ORE);
                        entries.add(ModBlocks.STEELIXITE_ORE);
                        entries.add(ModBlocks.SWAMPERTITE_ORE);
                        entries.add(ModBlocks.TYRANITARITE_ORE);
                        entries.add(ModBlocks.VENUSAURITE_ORE);

                        entries.add(ModBlocks.KEYSTONE_ORE);

                        entries.add(ModBlocks.MEGA_METEOROID_BLOCK);
                        entries.add(ModItems.MEGA_STONE_CRYSTAL_ITEM);
                    }))
                    .build());

    public static void registerItemGroups(){

    }
}
