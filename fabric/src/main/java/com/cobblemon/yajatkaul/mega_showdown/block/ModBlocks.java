package com.cobblemon.yajatkaul.mega_showdown.block;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.*;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.joml.Vector3f;

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
            new KeyStoneBlock(AbstractBlock.Settings.create()
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
            new CrystalBlock(4, 3, AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.DIAMOND_BLUE)
                    .luminance((state) -> 15)
                    .nonOpaque()
                    .pistonBehavior(PistonBehavior.IGNORE)
                    .sounds(BlockSoundGroup.AMETHYST_CLUSTER)) {
                private static final VoxelShape SHAPE_UP = Block.createCuboidShape(5, 0, 4, 11, 4, 11);      // floor
                private static final VoxelShape SHAPE_DOWN = Block.createCuboidShape(5, 12, 5, 11, 16, 12);  // ceiling
                private static final VoxelShape SHAPE_NORTH = Block.createCuboidShape(5, 4, 12, 11, 11, 15); // north wall
                private static final VoxelShape SHAPE_SOUTH = Block.createCuboidShape(5, 5, 0, 11, 12, 4);   // south wall
                private static final VoxelShape SHAPE_WEST = Block.createCuboidShape(12, 4, 5, 16, 11, 11);  // west wall
                private static final VoxelShape SHAPE_EAST = Block.createCuboidShape(0, 4, 5, 4, 11, 11);    // east wall


                @Override
                protected BlockRenderType getRenderType(BlockState state) {
                    return BlockRenderType.MODEL;
                }

                @Override
                protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
                    return switch (state.get(FACING)) {
                        case DOWN -> SHAPE_DOWN;
                        case NORTH -> SHAPE_NORTH;
                        case SOUTH -> SHAPE_SOUTH;
                        case WEST -> SHAPE_WEST;
                        case EAST -> SHAPE_EAST;
                        default -> SHAPE_UP; // case UP
                    };
                }
            });

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
            new ReassemblyUnitBlock(AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .nonOpaque()
                    .pistonBehavior(PistonBehavior.PUSH_ONLY)
                    .sounds(BlockSoundGroup.METAL)));

    public static final Block WISHING_STAR_CRYSTAL = registerBlock("wishing_star_crystal",
            new CrystalBlock(4, 3,
                    AbstractBlock.Settings.create()
                            .strength(1.5f)
                            .sounds(BlockSoundGroup.STONE)
                            .nonOpaque()
                            .requiresTool()
                            .pistonBehavior(PistonBehavior.IGNORE)
                            .luminance((state) -> 15)) {
                private static final VoxelShape SHAPE =
                        Block.createCuboidShape(2, 0, 2, 14, 9, 14);

                @Override
                protected BlockRenderType getRenderType(BlockState state) {
                    return BlockRenderType.MODEL;
                }

                @Override
                protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
                    return SHAPE;
                }

                @Override
                public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
                    // Get block center coordinates
                    double x = pos.getX() + 0.5D;
                    double y = pos.getY() + 0.5D;
                    double z = pos.getZ() + 0.5D;

                    // Spawn particles around the block
                    for (int i = 0; i < 3; i++) { // Spawn 3 particles per tick
                        double offsetX = (random.nextDouble() - 0.5D) * 0.5D;
                        double offsetY = (random.nextDouble() - 0.5D) * 0.5D;
                        double offsetZ = (random.nextDouble() - 0.5D) * 0.5D;

                        world.addParticle(
                                new DustParticleEffect(new Vector3f(1.0f, 0.0f, 0.0f), 0.5f),
                                x + offsetX,
                                y + offsetY,
                                z + offsetZ,
                                0.0D,
                                0.0D,
                                0.0D
                        );
                    }
                }
            });

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

    public static void registerBlocks() {

    }
}
