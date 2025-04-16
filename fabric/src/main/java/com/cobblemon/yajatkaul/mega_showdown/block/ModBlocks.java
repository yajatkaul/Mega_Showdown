package com.cobblemon.yajatkaul.mega_showdown.block;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.GracideaBlock;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.MaxMushroomBlock;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModBlocks {
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

    public static final Block DEOXYS_METEORITE = Registry.register(Registries.BLOCK, Identifier.of(MegaShowdown.MOD_ID, "deoxys_meteorite"),
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PINK)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block POWER_SPOT = registerBlockWithToolTip("power_spot",
            new Block(AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.PURPLE)
                    .luminance((state) -> 20)
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MAX_MUSHROOM = Registry.register(
            Registries.BLOCK,
            Identifier.of(MegaShowdown.MOD_ID, "max_mushroom"),
            new MaxMushroomBlock(AbstractBlock
                    .Settings.create()
                    .sounds(BlockSoundGroup.FLOWERING_AZALEA)));

    public static final Block GRACIDEA_FLOWER = Registry.register(
            Registries.BLOCK,
            Identifier.of(MegaShowdown.MOD_ID, "gracidea_flower"),
            new GracideaBlock());

    public static final Block POTTED_GRACIDEA = Registry.register(
            Registries.BLOCK,
            Identifier.of(MegaShowdown.MOD_ID, "potted_gracidea"),
            new FlowerPotBlock(GRACIDEA_FLOWER, AbstractBlock.Settings.copy(Blocks.POTTED_ALLIUM).nonOpaque())
    );

    public static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MegaShowdown.MOD_ID, name), block);
    }

    public static Block registerBlockWithToolTip(String name, Block block){
        registerBlockItemWithToolTip(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MegaShowdown.MOD_ID, name), block);
    }

    private static void registerBlockItemWithToolTip(String name, Block block){
        Registry.register(Registries.ITEM, Identifier.of(MegaShowdown.MOD_ID, name), new BlockItem(block, new Item.Settings()){
            @Override
            public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
                tooltip.add(Text.translatable("tooltip.mega_showdown."  + name + ".tooltip"));
                super.appendTooltip(stack, context, tooltip, type);
            }
        });
    }

    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, Identifier.of(MegaShowdown.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    public static void registerBlocks(){

    }
}
