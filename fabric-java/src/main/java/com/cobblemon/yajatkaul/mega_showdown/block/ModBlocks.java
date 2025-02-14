package com.cobblemon.yajatkaul.mega_showdown.block;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.MegaCrystalBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {

    public static final Block KEYSTONE_ORE = registerBlock("keystone_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(6, 9), AbstractBlock.Settings.create()
                    .strength(3f)
                    .mapColor(MapColor.PURPLE)
                    .requiresTool()
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK)));

    public static final Block MEGA_EVO_BRICK = registerBlock("mega_evo_brick",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .mapColor(MapColor.PURPLE)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_MEGA_EVO_BRICK = registerBlock("chiseled_mega_evo_brick",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .mapColor(MapColor.PURPLE)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block CHISELED_MEGA_EVO_BLOCK = registerBlock("chiseled_mega_evo_block",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .mapColor(MapColor.PURPLE)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block POLISHED_MEGA_EVO_BLOCK = registerBlock("polished_mega_evo_block",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .mapColor(MapColor.PURPLE)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block KEYSTONE_BLOCK = registerBlock("keystone_block",
            new Block(AbstractBlock.Settings.create()
                    .strength(4f)
                    .mapColor(MapColor.PURPLE)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block ABOMASITE_ORE = registerBlock("abomasite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block ABSOLITE_ORE = registerBlock("absolite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block AERODACTYLITE_ORE = registerBlock("aerodactylite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block AGGRONITE_ORE = registerBlock("aggronite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block ALAKAZITE_ORE = registerBlock("alakazite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block ALTARIANITE_ORE = registerBlock("altarianite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block AMPHAROSITE_ORE = registerBlock("ampharosite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block AUDINITE_ORE = registerBlock("audinite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block BANETTITE_ORE = registerBlock("banettite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block BEEDRILLITE_ORE = registerBlock("beedrillite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block BLASTOISINITE_ORE = registerBlock("blastoisinite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block BLAZIKENITE_ORE = registerBlock("blazikenite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block CAMERUPTITE_ORE = registerBlock("cameruptite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block CHARIZARDITE_X_ORE = registerBlock("charizardite_x_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block CHARIZARDITE_Y_ORE = registerBlock("charizardite_y_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block DIANCITE_ORE = registerBlock("diancite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block GALLADITE_ORE = registerBlock("galladite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block GARCHOMPITE_ORE = registerBlock("garchompite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block GARDEVOIRITE_ORE = registerBlock("gardevoirite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block GENGARITE_ORE = registerBlock("gengarite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block GLALITITE_ORE = registerBlock("glalitite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block GYARADOSITE_ORE = registerBlock("gyaradosite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block HERACRONITE_ORE = registerBlock("heracronite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block HOUNDOOMINITE_ORE = registerBlock("houndoominite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block KANGASKHANITE_ORE = registerBlock("kangaskhanite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block LATIASITE_ORE = registerBlock("latiasite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block LATIOSITE_ORE = registerBlock("latiosite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block LOPUNNITE_ORE = registerBlock("lopunnite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block LUCARIONITE_ORE = registerBlock("lucarionite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MANECTITE_ORE = registerBlock("manectite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MAWILITE_ORE = registerBlock("mawilite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MEDICHAMITE_ORE = registerBlock("medichamite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block METAGROSSITE_ORE = registerBlock("metagrossite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MEWTWONITE_X_ORE = registerBlock("mewtwonite_x_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MEWTWONITE_Y_ORE = registerBlock("mewtwonite_y_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block PIDGEOTITE_ORE = registerBlock("pidgeotite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block PINSIRITE_ORE = registerBlock("pinsirite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block SABLENITE_ORE = registerBlock("sablenite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block SALAMENCITE_ORE = registerBlock("salamencite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block SCEPTILITE_ORE = registerBlock("sceptilite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block SCIZORITE_ORE = registerBlock("scizorite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block SHARPEDONITE_ORE = registerBlock("sharpedonite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block SLOWBRONITE_ORE = registerBlock("slowbronite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block STEELIXITE_ORE = registerBlock("steelixite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block SWAMPERTITE_ORE = registerBlock("swampertite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block TYRANITARITE_ORE = registerBlock("tyranitarite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block VENUSAURITE_ORE = registerBlock("venusaurite_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(3, 6), AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MEGA_METEOROID_BLOCK = registerBlock("mega_meteorid_block",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MEGA_EVO_BLOCK = registerBlock("mega_evo_block",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MEGA_STONE_CRYSTAL = Registry.register(Registries.BLOCK,
            Identifier.of(MegaShowdown.MOD_ID, "mega_stone_crystal"),
            new MegaCrystalBlock(4, 3,
                    AbstractBlock.Settings.create()
                            .strength(1.5f)
                            .sounds(BlockSoundGroup.MEDIUM_AMETHYST_BUD)
                            .nonOpaque()
                            .requiresTool()
                            .luminance((state) -> 15)));

    public static final Block MEGA_METEORID_DAWN_ORE = registerBlock("mega_meteorid_dawn_ore",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MEGA_METEORID_DUSK_ORE = registerBlock("mega_meteorid_dusk_ore",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MEGA_METEORID_FIRE_ORE = registerBlock("mega_meteorid_fire_ore",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MEGA_METEORID_ICE_ORE = registerBlock("mega_meteorid_ice_ore",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MEGA_METEORID_LEAF_ORE = registerBlock("mega_meteorid_leaf_ore",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MEGA_METEORID_MOON_ORE = registerBlock("mega_meteorid_moon_ore",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MEGA_METEORID_SHINY_ORE = registerBlock("mega_meteorid_shiny_ore",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MEGA_METEORID_SUN_ORE = registerBlock("mega_meteorid_sun_ore",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MEGA_METEORID_THUNDER_ORE = registerBlock("mega_meteorid_thunder_ore",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MEGA_METEORID_WATER_ORE = registerBlock("mega_meteorid_water_ore",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));


    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MegaShowdown.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, Identifier.of(MegaShowdown.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks(){

    }
}
