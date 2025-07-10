package com.cobblemon.yajatkaul.mega_showdown.block;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.*;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.zygarde.ReassemblyUnitBlock;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.List;

public class ModBlocks {
    public static final Block MEGA_EVO_BRICK = registerMeteoridBlock("mega_evo_brick");

    public static final Block CHISELED_MEGA_EVO_BRICK = registerMeteoridBlock("chiseled_mega_evo_brick");

    public static final Block CHISELED_MEGA_EVO_BLOCK = registerMeteoridBlock("chiseled_mega_evo_block");

    public static final Block POLISHED_MEGA_EVO_BLOCK = registerMeteoridBlock("polished_mega_evo_block");

    public static final Block KEYSTONE_BLOCK = registerBlock("keystone_block",
            new KeyStoneBlock(AbstractBlock.Settings.create()
                    .strength(4f)
                    .mapColor(MapColor.PURPLE)
                    .requiresTool()
                    .sounds(BlockSoundGroup.STONE)));

    public static final Block MEGA_METEOROID_BLOCK = registerMeteoridBlock("mega_meteorid_block");

    public static final Block MEGA_EVO_BLOCK = registerMeteoridBlock("mega_evo_block");

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
                    .luminance((state) -> 15)
                    .sounds(BlockSoundGroup.STONE)) {
                private static final VoxelShape SHAPE =
                        Block.createCuboidShape(2, 0, 2, 14, 4, 14);

                @Override
                protected BlockRenderType getRenderType(BlockState state) {
                    return BlockRenderType.MODEL;
                }

                @Override
                protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
                    return SHAPE;
                }
            });

    public static final Block DORMANT_CRYSTAL = registerBlockWithToolTip("dormant_crystal",
            new DormantCrystal(4, 3, AbstractBlock.Settings.create()));

    public static final Block MAX_MUSHROOM = Registry.register(
            Registries.BLOCK,
            Identifier.of(MegaShowdown.MOD_ID, "max_mushroom"),
            new MaxMushroomBlock(AbstractBlock
                    .Settings.create()
                    .noCollision()
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

    public static final Block PEDESTAL = registerBlock("pedestal",
            new PedestalBlock(AbstractBlock.Settings.create().nonOpaque().strength(2).requiresTool()));

    public static final Block REASSEMBLY_UNIT = registerBlock("reassembly_unit",
            new ReassemblyUnitBlock(AbstractBlock.Settings.create().strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .nonOpaque()
                    .pistonBehavior(PistonBehavior.PUSH_ONLY)
                    .sounds(BlockSoundGroup.METAL)));

    public static final Block WISHING_STAR_CRYSTAL = registerBlock("wishing_star_crystal",
            new WishingStarCrystal(4, 3, AbstractBlock.Settings.create()));

    public static final Block ROTOM_WASHING_MACHINE = registerRotomBlock("rotom_washing_machine", "wash");
    public static final Block ROTOM_FAN = registerRotomBlock("rotom_fan", "fan");
    public static final Block ROTOM_MOW = registerRotomBlock("rotom_mow", "mow");
    public static final Block ROTOM_FRIDGE = registerRotomBlock("rotom_fridge", "frost");
    public static final Block ROTOM_OVEN = registerRotomBlock("rotom_oven", "heat");

    private static Block registerRotomBlock(String name, String form) {
        return registerBlockWithoutItem(name, new RotomUnit(AbstractBlock.Settings.create()
                .strength(2f)
                .requiresTool()
                .mapColor(MapColor.ORANGE)
                .sounds(BlockSoundGroup.STONE), form
        ));
    }

    private static Block registerMeteoridBlock(String name) {
        return registerBlock(name, new Block(
                AbstractBlock.Settings.create()
                        .strength(3f)
                        .mapColor(MapColor.PURPLE)
                        .requiresTool()
                        .sounds(BlockSoundGroup.STONE)
        ));
    }

    public static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MegaShowdown.MOD_ID, name), block);
    }

    public static Block registerBlockWithToolTip(String name, Block block) {
        registerBlockItemWithToolTip(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(MegaShowdown.MOD_ID, name), block);
    }

    private static void registerBlockItemWithToolTip(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(MegaShowdown.MOD_ID, name), new BlockItem(block, new Item.Settings()) {
            @Override
            public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
                tooltip.add(Text.translatable("tooltip.mega_showdown." + name + ".tooltip"));
                super.appendTooltip(stack, context, tooltip, type);
            }
        });
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(MegaShowdown.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    public static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(MegaShowdown.MOD_ID, name), block);
    }

    public static void registerBlocks() {

    }
}
