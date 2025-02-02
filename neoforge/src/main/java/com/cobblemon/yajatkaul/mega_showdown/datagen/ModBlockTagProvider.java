package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
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
                .add(ModBlocks.ABOMASITE_ORE.get(),
                        ModBlocks.ABSOLITE_ORE.get(),
                        ModBlocks.AERODACTYLITE_ORE.get(),
                        ModBlocks.AGGRONITE_ORE.get(),
                        ModBlocks.ALAKAZITE_ORE.get(),
                        ModBlocks.ALTARIANITE_ORE.get(),
                        ModBlocks.AMPHAROSITE_ORE.get(),
                        ModBlocks.AUDINITE_ORE.get(),
                        ModBlocks.BANETTITE_ORE.get(),
                        ModBlocks.BEEDRILLITE_ORE.get(),
                        ModBlocks.BLASTOISINITE_ORE.get(),
                        ModBlocks.BLAZIKENITE_ORE.get(),
                        ModBlocks.CAMERUPTITE_ORE.get(),
                        ModBlocks.CHARIZARDITE_X_ORE.get(),
                        ModBlocks.CHARIZARDITE_Y_ORE.get(),
                        ModBlocks.DIANCITE_ORE.get(),
                        ModBlocks.GALLADITE_ORE.get(),
                        ModBlocks.GARCHOMPITE_ORE.get(),
                        ModBlocks.GARDEVOIRITE_ORE.get(),
                        ModBlocks.GENGARITE_ORE.get(),
                        ModBlocks.GLALITITE_ORE.get(),
                        ModBlocks.GYARADOSITE_ORE.get(),
                        ModBlocks.HERACRONITE_ORE.get(),
                        ModBlocks.HOUNDOOMINITE_ORE.get(),
                        ModBlocks.KANGASKHANITE_ORE.get(),
                        ModBlocks.LATIASITE_ORE.get(),
                        ModBlocks.LATIOSITE_ORE.get(),
                        ModBlocks.LOPUNNITE_ORE.get(),
                        ModBlocks.LUCARIONITE_ORE.get(),
                        ModBlocks.MANECTITE_ORE.get(),
                        ModBlocks.MAWILITE_ORE.get(),
                        ModBlocks.MEDICHAMITE_ORE.get(),
                        ModBlocks.METAGROSSITE_ORE.get(),
                        ModBlocks.MEWTWONITE_X_ORE.get(),
                        ModBlocks.MEWTWONITE_Y_ORE.get(),
                        ModBlocks.PIDGEOTITE_ORE.get(),
                        ModBlocks.PINSIRITE_ORE.get(),
                        ModBlocks.SABLENITE_ORE.get(),
                        ModBlocks.SALAMENCITE_ORE.get(),
                        ModBlocks.SCEPTILITE_ORE.get(),
                        ModBlocks.SCIZORITE_ORE.get(),
                        ModBlocks.SHARPEDONITE_ORE.get(),
                        ModBlocks.SLOWBRONITE_ORE.get(),
                        ModBlocks.STEELIXITE_ORE.get(),
                        ModBlocks.SWAMPERTITE_ORE.get(),
                        ModBlocks.TYRANITARITE_ORE.get(),
                        ModBlocks.VENUSAURITE_ORE.get(),
                        ModBlocks.KEYSTONE_ORE.get(),
                        ModBlocks.MEGA_STONE_CRYSTAL.get(),
                        ModBlocks.MEGA_METEOROID_BLOCK.get(),
                        ModBlocks.MEGA_METEORID_DAWN_ORE.get(),
                        ModBlocks.MEGA_METEORID_DUSK_ORE.get(),
                        ModBlocks.MEGA_METEORID_FIRE_ORE.get(),
                        ModBlocks.MEGA_METEORID_ICE_ORE.get(),
                        ModBlocks.MEGA_METEORID_LEAF_ORE.get(),
                        ModBlocks.MEGA_METEORID_MOON_ORE.get(),
                        ModBlocks.MEGA_METEORID_SHINY_ORE.get(),
                        ModBlocks.MEGA_METEORID_SUN_ORE.get(),
                        ModBlocks.MEGA_METEORID_THUNDER_ORE.get(),
                        ModBlocks.MEGA_METEORID_WATER_ORE.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.ABOMASITE_ORE.get(),
                        ModBlocks.ABSOLITE_ORE.get(),
                        ModBlocks.AERODACTYLITE_ORE.get(),
                        ModBlocks.AGGRONITE_ORE.get(),
                        ModBlocks.ALAKAZITE_ORE.get(),
                        ModBlocks.ALTARIANITE_ORE.get(),
                        ModBlocks.AMPHAROSITE_ORE.get(),
                        ModBlocks.AUDINITE_ORE.get(),
                        ModBlocks.BANETTITE_ORE.get(),
                        ModBlocks.BEEDRILLITE_ORE.get(),
                        ModBlocks.BLASTOISINITE_ORE.get(),
                        ModBlocks.BLAZIKENITE_ORE.get(),
                        ModBlocks.CAMERUPTITE_ORE.get(),
                        ModBlocks.CHARIZARDITE_X_ORE.get(),
                        ModBlocks.CHARIZARDITE_Y_ORE.get(),
                        ModBlocks.DIANCITE_ORE.get(),
                        ModBlocks.GALLADITE_ORE.get(),
                        ModBlocks.GARCHOMPITE_ORE.get(),
                        ModBlocks.GARDEVOIRITE_ORE.get(),
                        ModBlocks.GENGARITE_ORE.get(),
                        ModBlocks.GLALITITE_ORE.get(),
                        ModBlocks.GYARADOSITE_ORE.get(),
                        ModBlocks.HERACRONITE_ORE.get(),
                        ModBlocks.HOUNDOOMINITE_ORE.get(),
                        ModBlocks.KANGASKHANITE_ORE.get(),
                        ModBlocks.LATIASITE_ORE.get(),
                        ModBlocks.LATIOSITE_ORE.get(),
                        ModBlocks.LOPUNNITE_ORE.get(),
                        ModBlocks.LUCARIONITE_ORE.get(),
                        ModBlocks.MANECTITE_ORE.get(),
                        ModBlocks.MAWILITE_ORE.get(),
                        ModBlocks.MEDICHAMITE_ORE.get(),
                        ModBlocks.METAGROSSITE_ORE.get(),
                        ModBlocks.MEWTWONITE_X_ORE.get(),
                        ModBlocks.MEWTWONITE_Y_ORE.get(),
                        ModBlocks.PIDGEOTITE_ORE.get(),
                        ModBlocks.PINSIRITE_ORE.get(),
                        ModBlocks.SABLENITE_ORE.get(),
                        ModBlocks.SALAMENCITE_ORE.get(),
                        ModBlocks.SCEPTILITE_ORE.get(),
                        ModBlocks.SCIZORITE_ORE.get(),
                        ModBlocks.SHARPEDONITE_ORE.get(),
                        ModBlocks.SLOWBRONITE_ORE.get(),
                        ModBlocks.STEELIXITE_ORE.get(),
                        ModBlocks.SWAMPERTITE_ORE.get(),
                        ModBlocks.TYRANITARITE_ORE.get(),
                        ModBlocks.VENUSAURITE_ORE.get(),
                        ModBlocks.MEGA_STONE_CRYSTAL.get(),
                        ModBlocks.KEYSTONE_ORE.get()
                );

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.MEGA_METEOROID_BLOCK.get(),
                        ModBlocks.MEGA_METEORID_DAWN_ORE.get(),
                        ModBlocks.MEGA_METEORID_DUSK_ORE.get(),
                        ModBlocks.MEGA_METEORID_FIRE_ORE.get(),
                        ModBlocks.MEGA_METEORID_ICE_ORE.get(),
                        ModBlocks.MEGA_METEORID_LEAF_ORE.get(),
                        ModBlocks.MEGA_METEORID_MOON_ORE.get(),
                        ModBlocks.MEGA_METEORID_SHINY_ORE.get(),
                        ModBlocks.MEGA_METEORID_SUN_ORE.get(),
                        ModBlocks.MEGA_METEORID_THUNDER_ORE.get(),
                        ModBlocks.MEGA_METEORID_WATER_ORE.get());
    }
}
