package com.cobblemon.yajatkaul.mega_showdown.block;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.MegaStoneCrystal;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class MegaOres {
    public static final Block KEYSTONE_ORE = registerBlock("keystone_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(6, 9), AbstractBlock.Settings.create()
                    .strength(3f)
                    .mapColor(MapColor.PURPLE)
                    .requiresTool()
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK)));

    public static final Block MEGA_STONE_CRYSTAL = registerBlock("mega_stone_crystal",
            new MegaStoneCrystal(4, 3, AbstractBlock.Settings.create(), true));

    public static final Block MEGA_METEORID_DAWN_ORE = registerMeteoroidOre("mega_meteorid_dawn_ore");
    public static final Block MEGA_METEORID_DUSK_ORE = registerMeteoroidOre("mega_meteorid_dusk_ore");
    public static final Block MEGA_METEORID_FIRE_ORE = registerMeteoroidOre("mega_meteorid_fire_ore");
    public static final Block MEGA_METEORID_ICE_ORE = registerMeteoroidOre("mega_meteorid_ice_ore");
    public static final Block MEGA_METEORID_LEAF_ORE = registerMeteoroidOre("mega_meteorid_leaf_ore");
    public static final Block MEGA_METEORID_MOON_ORE = registerMeteoroidOre("mega_meteorid_moon_ore");
    public static final Block MEGA_METEORID_SHINY_ORE = registerMeteoroidOre("mega_meteorid_shiny_ore");
    public static final Block MEGA_METEORID_SUN_ORE = registerMeteoroidOre("mega_meteorid_sun_ore");
    public static final Block MEGA_METEORID_THUNDER_ORE = registerMeteoroidOre("mega_meteorid_thunder_ore");
    public static final Block MEGA_METEORID_WATER_ORE = registerMeteoroidOre("mega_meteorid_water_ore");

    private static Block registerMeteoroidOre(String name) {
        return registerBlock(name, new ExperienceDroppingBlock(
                UniformIntProvider.create(3, 6),
                AbstractBlock.Settings.create()
                        .strength(3f)
                        .mapColor(MapColor.PURPLE)
                        .requiresTool()
                        .sounds(BlockSoundGroup.STONE)
        ));
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MegaShowdown.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(MegaShowdown.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    public static void registerBlocks() {

    }
}
