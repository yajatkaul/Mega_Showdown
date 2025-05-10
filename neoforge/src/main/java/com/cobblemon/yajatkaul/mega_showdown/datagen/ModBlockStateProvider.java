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
        blockWithItem(MegaOres.KEYSTONE_ORE);

        blockWithItem(ModBlocks.MEGA_METEOROID_BLOCK);
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

        blockWithItem(ModBlocks.DEOXYS_METEORITE);
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock){
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockWithTopBottom(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(),
                models().cubeBottomTop(deferredBlock.getId().getPath(),
                        ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "block/" + deferredBlock.getId().getPath() + "_side"),
                        ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "block/" + deferredBlock.getId().getPath() + "_top"),
                        ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "block/" + deferredBlock.getId().getPath() + "_top")));
    }
}
