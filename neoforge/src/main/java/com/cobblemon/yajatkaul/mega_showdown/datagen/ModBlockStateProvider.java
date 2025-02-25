package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper){
       super(output, MegaShowdown.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels(){
        blockWithItem(MegaOres.ABOMASITE_ORE);
        blockWithItem(MegaOres.ABSOLITE_ORE);
        blockWithItem(MegaOres.AERODACTYLITE_ORE);
        blockWithItem(MegaOres.AGGRONITE_ORE);
        blockWithItem(MegaOres.ALAKAZITE_ORE);
        blockWithItem(MegaOres.ALTARIANITE_ORE);
        blockWithItem(MegaOres.AMPHAROSITE_ORE);
        blockWithItem(MegaOres.AUDINITE_ORE);
        blockWithItem(MegaOres.BANETTITE_ORE);
        blockWithItem(MegaOres.BEEDRILLITE_ORE);
        blockWithItem(MegaOres.BLASTOISINITE_ORE);
        blockWithItem(MegaOres.BLAZIKENITE_ORE);
        blockWithItem(MegaOres.CAMERUPTITE_ORE);
        blockWithItem(MegaOres.CHARIZARDITE_X_ORE);
        blockWithItem(MegaOres.CHARIZARDITE_Y_ORE);
        blockWithItem(MegaOres.DIANCITE_ORE);
        blockWithItem(MegaOres.GALLADITE_ORE);
        blockWithItem(MegaOres.GARCHOMPITE_ORE);
        blockWithItem(MegaOres.GARDEVOIRITE_ORE);
        blockWithItem(MegaOres.GENGARITE_ORE);
        blockWithItem(MegaOres.GLALITITE_ORE);
        blockWithItem(MegaOres.GYARADOSITE_ORE);
        blockWithItem(MegaOres.HERACRONITE_ORE);
        blockWithItem(MegaOres.HOUNDOOMINITE_ORE);
        blockWithItem(MegaOres.KANGASKHANITE_ORE);
        blockWithItem(MegaOres.LATIASITE_ORE);
        blockWithItem(MegaOres.LATIOSITE_ORE);
        blockWithItem(MegaOres.LOPUNNITE_ORE);
        blockWithItem(MegaOres.LUCARIONITE_ORE);
        blockWithItem(MegaOres.MANECTITE_ORE);
        blockWithItem(MegaOres.MAWILITE_ORE);
        blockWithItem(MegaOres.MEDICHAMITE_ORE);
        blockWithItem(MegaOres.METAGROSSITE_ORE);
        blockWithItem(MegaOres.MEWTWONITE_X_ORE);
        blockWithItem(MegaOres.MEWTWONITE_Y_ORE);
        blockWithItem(MegaOres.PIDGEOTITE_ORE);
        blockWithItem(MegaOres.PINSIRITE_ORE);
        blockWithItem(MegaOres.SABLENITE_ORE);
        blockWithItem(MegaOres.SALAMENCITE_ORE);
        blockWithItem(MegaOres.SCEPTILITE_ORE);
        blockWithItem(MegaOres.SCIZORITE_ORE);
        blockWithItem(MegaOres.SHARPEDONITE_ORE);
        blockWithItem(MegaOres.SLOWBRONITE_ORE);
        blockWithItem(MegaOres.STEELIXITE_ORE);
        blockWithItem(MegaOres.SWAMPERTITE_ORE);
        blockWithItem(MegaOres.TYRANITARITE_ORE);
        blockWithItem(MegaOres.VENUSAURITE_ORE);

        blockWithItem(MegaOres.KEYSTONE_ORE);

        blockWithItem(ModBlocks.MEGA_METEOROID_BLOCK);
        blockWithItem(ModBlocks.KEYSTONE_BLOCK);
        blockWithItem(ModBlocks.MEGA_EVO_BLOCK);

        //Custom stones
        blockWithItem(MegaOres.MEGA_METEORID_DAWN_ORE);
        blockWithItem(MegaOres.MEGA_METEORID_DUSK_ORE);
        blockWithItem(MegaOres.MEGA_METEORID_FIRE_ORE);
        blockWithItem(MegaOres.MEGA_METEORID_ICE_ORE);
        blockWithItem(MegaOres.MEGA_METEORID_LEAF_ORE);
        blockWithItem(MegaOres.MEGA_METEORID_MOON_ORE);
        blockWithItem(MegaOres.MEGA_METEORID_SHINY_ORE);
        blockWithItem(MegaOres.MEGA_METEORID_SUN_ORE);
        blockWithItem(MegaOres.MEGA_METEORID_THUNDER_ORE);
        blockWithItem(MegaOres.MEGA_METEORID_WATER_ORE);
        blockWithTopBottom(ModBlocks.CHISELED_MEGA_EVO_BLOCK);
        blockWithItem(ModBlocks.CHISELED_MEGA_EVO_BRICK);
        blockWithItem(ModBlocks.MEGA_EVO_BRICK);
        blockWithItem(ModBlocks.POLISHED_MEGA_EVO_BLOCK);

    }

    private void blockWithItem(DeferredBlock<?> deferredBlock){
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(MegaShowdown.MOD_ID + ":block/" + deferredBlock.getId().getPath()));
    }

    //For crystals
    private void blockWithItemCutOut(DeferredBlock<?> deferredBlock) {
        directionalBlock(deferredBlock.get(),
                models().cross(deferredBlock.getId().getPath(),
                        blockTexture(deferredBlock.get())).renderType("cutout"));
        itemModels().withExistingParent(deferredBlock.getId().getPath(), mcLoc("item/generated"))
                .texture("layer0", "block/" + deferredBlock.getId().getPath());
    }

    private void blockWithTopBottom(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(),
                models().cubeBottomTop(deferredBlock.getId().getPath(),
                        ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "block/" + deferredBlock.getId().getPath() + "_side"),
                        ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "block/" + deferredBlock.getId().getPath() + "_top"),
                        ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "block/" + deferredBlock.getId().getPath() + "_top")));
    }
}
