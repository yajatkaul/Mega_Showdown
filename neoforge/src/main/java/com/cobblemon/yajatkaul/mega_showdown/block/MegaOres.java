package com.cobblemon.yajatkaul.mega_showdown.block;

import com.cobblemon.yajatkaul.mega_showdown.block.custom.MegaCrystalBlock;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Supplier;


public class MegaOres {
    public static final DeferredBlock<Block> KEYSTONE_ORE = registerBlock("keystone_ore",
            () -> new DropExperienceBlock(UniformInt.of(6,9),
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .mapColor(MapColor.COLOR_PURPLE)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.AMETHYST)));

    public static final DeferredBlock<AmethystClusterBlock> MEGA_STONE_CRYSTAL = registerBlock("mega_stone_crystal",
            () -> new MegaCrystalBlock(4, 3,
                    BlockBehaviour.Properties.of()
                            .strength(1.5f)
                            .sound(SoundType.MEDIUM_AMETHYST_BUD)
                            .noOcclusion()
                            .requiresCorrectToolForDrops()
                            .pushReaction(PushReaction.PUSH_ONLY)
                            .lightLevel((state) -> 15)){
                public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 13, 14);

                @Override
                protected VoxelShape getShape(BlockState arg, BlockGetter arg2, BlockPos arg3, CollisionContext arg4) {
                    return SHAPE;
                }
            });

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


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = ModBlocks.BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return  toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(){
    }
}
