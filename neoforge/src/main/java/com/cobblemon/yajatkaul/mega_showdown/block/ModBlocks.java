package com.cobblemon.yajatkaul.mega_showdown.block;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.MegaCrystalBlock;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MegaShowdown.MOD_ID);

    public static final DeferredBlock<Block> KEYSTONE_ORE = registerBlock("keystone_ore",
            () -> new DropExperienceBlock(UniformInt.of(6,9),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> KEYSTONE_BLOCK = registerBlock("keystone_block",
            () -> new Block(
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> ABOMASITE_ORE = registerBlock("abomasite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> ABSOLITE_ORE = registerBlock("absolite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> AERODACTYLITE_ORE = registerBlock("aerodactylite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> AGGRONITE_ORE = registerBlock("aggronite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> ALAKAZITE_ORE = registerBlock("alakazite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> ALTARIANITE_ORE = registerBlock("altarianite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> AMPHAROSITE_ORE = registerBlock("ampharosite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> AUDINITE_ORE = registerBlock("audinite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> BANETTITE_ORE = registerBlock("banettite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> BEEDRILLITE_ORE = registerBlock("beedrillite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> BLASTOISINITE_ORE = registerBlock("blastoisinite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> BLAZIKENITE_ORE = registerBlock("blazikenite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> CAMERUPTITE_ORE = registerBlock("cameruptite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> CHARIZARDITE_X_ORE = registerBlock("charizardite_x_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> CHARIZARDITE_Y_ORE = registerBlock("charizardite_y_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> DIANCITE_ORE = registerBlock("diancite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> GALLADITE_ORE = registerBlock("galladite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> GARCHOMPITE_ORE = registerBlock("garchompite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> GARDEVOIRITE_ORE = registerBlock("gardevoirite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> GENGARITE_ORE = registerBlock("gengarite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> GLALITITE_ORE = registerBlock("glalitite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> GYARADOSITE_ORE = registerBlock("gyaradosite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> HERACRONITE_ORE = registerBlock("heracronite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> HOUNDOOMINITE_ORE = registerBlock("houndoominite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> KANGASKHANITE_ORE = registerBlock("kangaskhanite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> LATIASITE_ORE = registerBlock("latiasite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> LATIOSITE_ORE = registerBlock("latiosite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> LOPUNNITE_ORE = registerBlock("lopunnite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> LUCARIONITE_ORE = registerBlock("lucarionite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> MANECTITE_ORE = registerBlock("manectite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> MAWILITE_ORE = registerBlock("mawilite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> MEDICHAMITE_ORE = registerBlock("medichamite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> METAGROSSITE_ORE = registerBlock("metagrossite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> MEWTWONITE_X_ORE = registerBlock("mewtwonite_x_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> MEWTWONITE_Y_ORE = registerBlock("mewtwonite_y_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> PIDGEOTITE_ORE = registerBlock("pidgeotite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> PINSIRITE_ORE = registerBlock("pinsirite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> SABLENITE_ORE = registerBlock("sablenite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> SALAMENCITE_ORE = registerBlock("salamencite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> SCEPTILITE_ORE = registerBlock("sceptilite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> SCIZORITE_ORE = registerBlock("scizorite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> SHARPEDONITE_ORE = registerBlock("sharpedonite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> SLOWBRONITE_ORE = registerBlock("slowbronite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> STEELIXITE_ORE = registerBlock("steelixite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> SWAMPERTITE_ORE = registerBlock("swampertite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> TYRANITARITE_ORE = registerBlock("tyranitarite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<Block> VENUSAURITE_ORE = registerBlock("venusaurite_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,6),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.AMETHYST)));


    public static final DeferredBlock<Block> MEGA_METEOROID_BLOCK = registerBlock("mega_meteorid_block",
            () -> new Block(BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> MEGA_EVO_BLOCK = registerBlock("mega_evo_block",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .requiresCorrectToolForDrops()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<AmethystClusterBlock> MEGA_STONE_CRYSTAL = registerBlock("mega_stone_crystal",
            () -> new MegaCrystalBlock(4, 3,
                    BlockBehaviour.Properties.of()
                            .strength(1.5f)
                            .sound(SoundType.MEDIUM_AMETHYST_BUD)
                            .noOcclusion()
                            .requiresCorrectToolForDrops()
                            .lightLevel((state) -> 15)));

    public static final DeferredBlock<Block> MEGA_METEORID_WATER_ORE = registerBlock("mega_meteorid_water_ore",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> MEGA_METEORID_DAWN_ORE = registerBlock("mega_meteorid_dawn_ore",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> MEGA_METEORID_DUSK_ORE = registerBlock("mega_meteorid_dusk_ore",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> MEGA_METEORID_FIRE_ORE = registerBlock("mega_meteorid_fire_ore",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> MEGA_METEORID_ICE_ORE = registerBlock("mega_meteorid_ice_ore",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> MEGA_METEORID_LEAF_ORE = registerBlock("mega_meteorid_leaf_ore",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> MEGA_METEORID_MOON_ORE = registerBlock("mega_meteorid_moon_ore",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> MEGA_METEORID_SHINY_ORE = registerBlock("mega_meteorid_shiny_ore",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> MEGA_METEORID_SUN_ORE = registerBlock("mega_meteorid_sun_ore",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> MEGA_METEORID_THUNDER_ORE = registerBlock("mega_meteorid_thunder_ore",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    //decor
    public static final DeferredBlock<Block> MEGA_EVO_BRICK = registerBlock("mega_evo_brick",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> CHISELED_MEGA_EVO_BRICK = registerBlock("chiseled_mega_evo_brick",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> CHISELED_MEGA_EVO_BLOCK = registerBlock("chiseled_mega_evo_block",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> POLISHED_MEGA_EVO_BLOCK = registerBlock("polished_mega_evo_block",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return  toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
