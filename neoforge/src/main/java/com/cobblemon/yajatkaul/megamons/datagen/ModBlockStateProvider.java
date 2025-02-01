package com.cobblemon.yajatkaul.megamons.datagen;

import com.cobblemon.yajatkaul.megamons.MegaShowdown;
import com.cobblemon.yajatkaul.megamons.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper){
       super(output, MegaShowdown.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels(){
        blockWithItem(ModBlocks.ABOMASITE_ORE);
        blockWithItem(ModBlocks.ABSOLITE_ORE);
        blockWithItem(ModBlocks.AERODACTYLITE_ORE);
        blockWithItem(ModBlocks.AGGRONITE_ORE);
        blockWithItem(ModBlocks.ALAKAZITE_ORE);
        blockWithItem(ModBlocks.ALTARIANITE_ORE);
        blockWithItem(ModBlocks.AMPHAROSITE_ORE);
        blockWithItem(ModBlocks.AUDINITE_ORE);
        blockWithItem(ModBlocks.BANETTITE_ORE);
        blockWithItem(ModBlocks.BEEDRILLITE_ORE);
        blockWithItem(ModBlocks.BLASTOISINITE_ORE);
        blockWithItem(ModBlocks.BLAZIKENITE_ORE);
        blockWithItem(ModBlocks.CAMERUPTITE_ORE);
        blockWithItem(ModBlocks.CHARIZARDITE_X_ORE);
        blockWithItem(ModBlocks.CHARIZARDITE_Y_ORE);
        blockWithItem(ModBlocks.DIANCITE_ORE);
        blockWithItem(ModBlocks.GALLADITE_ORE);
        blockWithItem(ModBlocks.GARCHOMPITE_ORE);
        blockWithItem(ModBlocks.GARDEVOIRITE_ORE);
        blockWithItem(ModBlocks.GENGARITE_ORE);
        blockWithItem(ModBlocks.GLALITITE_ORE);
        blockWithItem(ModBlocks.GYARADOSITE_ORE);
        blockWithItem(ModBlocks.HERACRONITE_ORE);
        blockWithItem(ModBlocks.HOUNDOOMINITE_ORE);
        blockWithItem(ModBlocks.KANGASKHANITE_ORE);
        blockWithItem(ModBlocks.LATIASITE_ORE);
        blockWithItem(ModBlocks.LATIOSITE_ORE);
        blockWithItem(ModBlocks.LOPUNNITE_ORE);
        blockWithItem(ModBlocks.LUCARIONITE_ORE);
        blockWithItem(ModBlocks.MANECTITE_ORE);
        blockWithItem(ModBlocks.MAWILITE_ORE);
        blockWithItem(ModBlocks.MEDICHAMITE_ORE);
        blockWithItem(ModBlocks.METAGROSSITE_ORE);
        blockWithItem(ModBlocks.MEWTWONITE_X_ORE);
        blockWithItem(ModBlocks.MEWTWONITE_Y_ORE);
        blockWithItem(ModBlocks.PIDGEOTITE_ORE);
        blockWithItem(ModBlocks.PINSIRITE_ORE);
        blockWithItem(ModBlocks.SABLENITE_ORE);
        blockWithItem(ModBlocks.SALAMENCITE_ORE);
        blockWithItem(ModBlocks.SCEPTILITE_ORE);
        blockWithItem(ModBlocks.SCIZORITE_ORE);
        blockWithItem(ModBlocks.SHARPEDONITE_ORE);
        blockWithItem(ModBlocks.SLOWBRONITE_ORE);
        blockWithItem(ModBlocks.STEELIXITE_ORE);
        blockWithItem(ModBlocks.SWAMPERTITE_ORE);
        blockWithItem(ModBlocks.TYRANITARITE_ORE);
        blockWithItem(ModBlocks.VENUSAURITE_ORE);

        blockWithItem(ModBlocks.KEYSTONE_ORE);

        blockWithItem(ModBlocks.MEGA_METEOROID_BLOCK);

        //Testing
        //blockWithItemCutOut(ModBlocks.MEGA_STONE_CRYSTAL);
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock){
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockWithItemCutOut(DeferredBlock<?> deferredBlock) {
        directionalBlock(deferredBlock.get(),
                models().cross(BuiltInRegistries.BLOCK.getKey(deferredBlock.get()).getPath(),
                        blockTexture(deferredBlock.get())).renderType("cutout"));

    }
}
