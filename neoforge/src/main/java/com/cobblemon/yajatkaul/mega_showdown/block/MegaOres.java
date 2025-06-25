package com.cobblemon.yajatkaul.mega_showdown.block;

import com.cobblemon.yajatkaul.mega_showdown.block.custom.MegaStoneCrystal;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Supplier;


public class MegaOres {
    public static final DeferredBlock<Block> KEYSTONE_ORE = registerBlock("keystone_ore",
            () -> new DropExperienceBlock(UniformInt.of(6, 9),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<AmethystClusterBlock> MEGA_STONE_CRYSTAL = registerBlock("mega_stone_crystal",
            () -> new MegaStoneCrystal(4, 3, BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> MEGA_METEORID_WATER_ORE = registerMeteoroidOre("mega_meteorid_water_ore");

    public static final DeferredBlock<Block> MEGA_METEORID_DAWN_ORE = registerMeteoroidOre("mega_meteorid_dawn_ore");

    public static final DeferredBlock<Block> MEGA_METEORID_DUSK_ORE = registerMeteoroidOre("mega_meteorid_dusk_ore");

    public static final DeferredBlock<Block> MEGA_METEORID_FIRE_ORE = registerMeteoroidOre("mega_meteorid_fire_ore");

    public static final DeferredBlock<Block> MEGA_METEORID_ICE_ORE = registerMeteoroidOre("mega_meteorid_ice_ore");

    public static final DeferredBlock<Block> MEGA_METEORID_LEAF_ORE = registerMeteoroidOre("mega_meteorid_leaf_ore");

    public static final DeferredBlock<Block> MEGA_METEORID_MOON_ORE = registerMeteoroidOre("mega_meteorid_moon_ore");

    public static final DeferredBlock<Block> MEGA_METEORID_SHINY_ORE = registerMeteoroidOre("mega_meteorid_shiny_ore");

    public static final DeferredBlock<Block> MEGA_METEORID_SUN_ORE = registerMeteoroidOre("mega_meteorid_sun_ore");

    public static final DeferredBlock<Block> MEGA_METEORID_THUNDER_ORE = registerMeteoroidOre("mega_meteorid_thunder_ore");

    private static DeferredBlock<Block> registerMeteoroidOre(String name) {
        return registerBlock(name, () -> new DropExperienceBlock(
                UniformInt.of(3, 6),
                BlockBehaviour.Properties.of()
                        .strength(3f)
                        .mapColor(MapColor.COLOR_PURPLE)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.STONE)));
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = ModBlocks.BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register() {
    }
}
