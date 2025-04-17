package com.cobblemon.yajatkaul.mega_showdown.block;

import com.cobblemon.yajatkaul.mega_showdown.block.custom.MegaCrystalBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import static com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks.registerBlock;

public class MegaOres {
    public static final Block KEYSTONE_ORE = registerBlock("keystone_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(6, 9), AbstractBlock.Settings.create()
                    .strength(3f)
                    .mapColor(MapColor.PURPLE)
                    .requiresTool()
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK)));

    public static final Block MEGA_STONE_CRYSTAL = registerBlock("mega_stone_crystal",
            new MegaCrystalBlock(4, 3,
                    AbstractBlock.Settings.create()
                            .strength(1.5f)
                            .sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD)
                            .nonOpaque()
                            .requiresTool()
                            .pistonBehavior(PistonBehavior.PUSH_ONLY)
                            .luminance((state) -> 15)));

    public static void registerBlocks(){

    }
}
