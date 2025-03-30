package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider
                               ,@Nullable ExistingFileHelper existingFileHelper){
        super(output, lookupProvider, MegaShowdown.MOD_ID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(MegaOres.ABOMASITE_ORE.get(),
                        MegaOres.ABSOLITE_ORE.get(),
                        MegaOres.AERODACTYLITE_ORE.get(),
                        MegaOres.AGGRONITE_ORE.get(),
                        MegaOres.ALAKAZITE_ORE.get(),
                        MegaOres.ALTARIANITE_ORE.get(),
                        MegaOres.AMPHAROSITE_ORE.get(),
                        MegaOres.AUDINITE_ORE.get(),
                        MegaOres.BANETTITE_ORE.get(),
                        MegaOres.BEEDRILLITE_ORE.get(),
                        MegaOres.BLASTOISINITE_ORE.get(),
                        MegaOres.BLAZIKENITE_ORE.get(),
                        MegaOres.CAMERUPTITE_ORE.get(),
                        MegaOres.CHARIZARDITE_X_ORE.get(),
                        MegaOres.CHARIZARDITE_Y_ORE.get(),
                        MegaOres.DIANCITE_ORE.get(),
                        MegaOres.GALLADITE_ORE.get(),
                        MegaOres.GARCHOMPITE_ORE.get(),
                        MegaOres.GARDEVOIRITE_ORE.get(),
                        MegaOres.GENGARITE_ORE.get(),
                        MegaOres.GLALITITE_ORE.get(),
                        MegaOres.GYARADOSITE_ORE.get(),
                        MegaOres.HERACRONITE_ORE.get(),
                        MegaOres.HOUNDOOMINITE_ORE.get(),
                        MegaOres.KANGASKHANITE_ORE.get(),
                        MegaOres.LATIASITE_ORE.get(),
                        MegaOres.LATIOSITE_ORE.get(),
                        MegaOres.LOPUNNITE_ORE.get(),
                        MegaOres.LUCARIONITE_ORE.get(),
                        MegaOres.MANECTITE_ORE.get(),
                        MegaOres.MAWILITE_ORE.get(),
                        MegaOres.MEDICHAMITE_ORE.get(),
                        MegaOres.METAGROSSITE_ORE.get(),
                        MegaOres.MEWTWONITE_X_ORE.get(),
                        MegaOres.MEWTWONITE_Y_ORE.get(),
                        MegaOres.PIDGEOTITE_ORE.get(),
                        MegaOres.PINSIRITE_ORE.get(),
                        MegaOres.SABLENITE_ORE.get(),
                        MegaOres.SALAMENCITE_ORE.get(),
                        MegaOres.SCEPTILITE_ORE.get(),
                        MegaOres.SCIZORITE_ORE.get(),
                        MegaOres.SHARPEDONITE_ORE.get(),
                        MegaOres.SLOWBRONITE_ORE.get(),
                        MegaOres.STEELIXITE_ORE.get(),
                        MegaOres.SWAMPERTITE_ORE.get(),
                        MegaOres.TYRANITARITE_ORE.get(),
                        MegaOres.VENUSAURITE_ORE.get(),
                        MegaOres.KEYSTONE_ORE.get(),

                        MegaOres.MEGA_STONE_CRYSTAL.get(),

                        ModBlocks.MEGA_METEOROID_BLOCK.get(),
                        ModBlocks.MEGA_EVO_BLOCK.get(),
                        ModBlocks.KEYSTONE_BLOCK.get(),

                        MegaOres.MEGA_METEORID_DAWN_ORE.get(),
                        MegaOres.MEGA_METEORID_DUSK_ORE.get(),
                        MegaOres.MEGA_METEORID_FIRE_ORE.get(),
                        MegaOres.MEGA_METEORID_ICE_ORE.get(),
                        MegaOres.MEGA_METEORID_LEAF_ORE.get(),
                        MegaOres.MEGA_METEORID_MOON_ORE.get(),
                        MegaOres.MEGA_METEORID_SHINY_ORE.get(),
                        MegaOres.MEGA_METEORID_SUN_ORE.get(),
                        MegaOres.MEGA_METEORID_THUNDER_ORE.get(),
                        MegaOres.MEGA_METEORID_WATER_ORE.get(),

                        ModBlocks.DEOXYS_METEORITE.get(),

                        //Decor
                        ModBlocks.CHISELED_MEGA_EVO_BRICK.get(),
                        ModBlocks.CHISELED_MEGA_EVO_BLOCK.get(),
                        ModBlocks.POLISHED_MEGA_EVO_BLOCK.get(),
                        ModBlocks.MEGA_EVO_BRICK.get()
                );

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(MegaOres.ABOMASITE_ORE.get(),
                        MegaOres.ABSOLITE_ORE.get(),
                        MegaOres.AERODACTYLITE_ORE.get(),
                        MegaOres.AGGRONITE_ORE.get(),
                        MegaOres.ALAKAZITE_ORE.get(),
                        MegaOres.ALTARIANITE_ORE.get(),
                        MegaOres.AMPHAROSITE_ORE.get(),
                        MegaOres.AUDINITE_ORE.get(),
                        MegaOres.BANETTITE_ORE.get(),
                        MegaOres.BEEDRILLITE_ORE.get(),
                        MegaOres.BLASTOISINITE_ORE.get(),
                        MegaOres.BLAZIKENITE_ORE.get(),
                        MegaOres.CAMERUPTITE_ORE.get(),
                        MegaOres.CHARIZARDITE_X_ORE.get(),
                        MegaOres.CHARIZARDITE_Y_ORE.get(),
                        MegaOres.DIANCITE_ORE.get(),
                        MegaOres.GALLADITE_ORE.get(),
                        MegaOres.GARCHOMPITE_ORE.get(),
                        MegaOres.GARDEVOIRITE_ORE.get(),
                        MegaOres.GENGARITE_ORE.get(),
                        MegaOres.GLALITITE_ORE.get(),
                        MegaOres.GYARADOSITE_ORE.get(),
                        MegaOres.HERACRONITE_ORE.get(),
                        MegaOres.HOUNDOOMINITE_ORE.get(),
                        MegaOres.KANGASKHANITE_ORE.get(),
                        MegaOres.LATIASITE_ORE.get(),
                        MegaOres.LATIOSITE_ORE.get(),
                        MegaOres.LOPUNNITE_ORE.get(),
                        MegaOres.LUCARIONITE_ORE.get(),
                        MegaOres.MANECTITE_ORE.get(),
                        MegaOres.MAWILITE_ORE.get(),
                        MegaOres.MEDICHAMITE_ORE.get(),
                        MegaOres.METAGROSSITE_ORE.get(),
                        MegaOres.MEWTWONITE_X_ORE.get(),
                        MegaOres.MEWTWONITE_Y_ORE.get(),
                        MegaOres.PIDGEOTITE_ORE.get(),
                        MegaOres.PINSIRITE_ORE.get(),
                        MegaOres.SABLENITE_ORE.get(),
                        MegaOres.SALAMENCITE_ORE.get(),
                        MegaOres.SCEPTILITE_ORE.get(),
                        MegaOres.SCIZORITE_ORE.get(),
                        MegaOres.SHARPEDONITE_ORE.get(),
                        MegaOres.SLOWBRONITE_ORE.get(),
                        MegaOres.STEELIXITE_ORE.get(),
                        MegaOres.SWAMPERTITE_ORE.get(),
                        MegaOres.TYRANITARITE_ORE.get(),
                        MegaOres.VENUSAURITE_ORE.get(),
                        MegaOres.MEGA_STONE_CRYSTAL.get(),
                        MegaOres.KEYSTONE_ORE.get(),
                        ModBlocks.KEYSTONE_BLOCK.get(),

                        ModBlocks.DEOXYS_METEORITE.get()
                );

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.MEGA_METEOROID_BLOCK.get(),
                        ModBlocks.MEGA_EVO_BLOCK.get(),
                        MegaOres.MEGA_METEORID_DAWN_ORE.get(),
                        MegaOres.MEGA_METEORID_DUSK_ORE.get(),
                        MegaOres.MEGA_METEORID_FIRE_ORE.get(),
                        MegaOres.MEGA_METEORID_ICE_ORE.get(),
                        MegaOres.MEGA_METEORID_LEAF_ORE.get(),
                        MegaOres.MEGA_METEORID_MOON_ORE.get(),
                        MegaOres.MEGA_METEORID_SHINY_ORE.get(),
                        MegaOres.MEGA_METEORID_SUN_ORE.get(),
                        MegaOres.MEGA_METEORID_THUNDER_ORE.get(),
                        MegaOres.MEGA_METEORID_WATER_ORE.get(),

                        //Decor
                        ModBlocks.CHISELED_MEGA_EVO_BRICK.get(),
                        ModBlocks.CHISELED_MEGA_EVO_BLOCK.get(),
                        ModBlocks.POLISHED_MEGA_EVO_BLOCK.get(),
                        ModBlocks.MEGA_EVO_BRICK.get()
                );
    }
}
