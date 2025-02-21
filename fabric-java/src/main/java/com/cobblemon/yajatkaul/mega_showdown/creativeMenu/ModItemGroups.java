package com.cobblemon.yajatkaul.mega_showdown.creativeMenu;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.block.PokemonStones;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
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

    public static void registerItemGroups(){

    }
}
