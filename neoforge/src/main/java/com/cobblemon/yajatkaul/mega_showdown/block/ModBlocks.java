package com.cobblemon.yajatkaul.mega_showdown.block;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.*;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MegaShowdown.MOD_ID);

    public static final DeferredBlock<Block> KEYSTONE_BLOCK = registerBlock("keystone_block",
            () -> new KeyStoneBlock(
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.STONE)));

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

    public static final DeferredBlock<Block> DEOXYS_METEORITE = BLOCKS.register("deoxys_meteorite",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PINK)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> POWER_SPOT = registerBlockWithToolTip("power_spot",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .lightLevel((state) -> 15)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)) {
                public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 4, 14);

                @Override
                protected VoxelShape getShape(BlockState arg, BlockGetter arg2, BlockPos arg3, CollisionContext arg4) {
                    return SHAPE;
                }
            });

    public static final DeferredBlock<Block> MAX_MUSHROOM = BLOCKS.register("max_mushroom", () ->
            new MaxMushroomBlock(BlockBehaviour
                    .Properties.of()
                    .noCollission()
                    .sound(SoundType.FLOWERING_AZALEA)));

    public static final DeferredBlock<Block> GRACIDEA_FLOWER = BLOCKS.register("gracidea_flower", GracideaBlock::new);

    public static final DeferredBlock<Block> POTTED_GRACIDEA = BLOCKS.register("potted_gracidea",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GRACIDEA_FLOWER,
                    BlockBehaviour.Properties.of().noOcclusion().instabreak()));

    public static final DeferredBlock<Block> PEDESTAL = registerBlock("pedestal",
            () -> new PedestalBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(2)));

    public static final DeferredBlock<Block> REASSEMBLY_UNIT = registerBlock("reassembly_unit",
            () -> new ReassemblyUnitBlock(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()
                    .sound(SoundType.METAL)));

    public static final DeferredBlock<AmethystClusterBlock> WISHING_STAR_CRYSTAL = registerBlock("wishing_star_crystal",
            () -> new CrystalBlock(4, 3,
                    BlockBehaviour.Properties.of()
                            .strength(1.5f)
                            .sound(SoundType.MEDIUM_AMETHYST_BUD)
                            .noOcclusion()
                            .requiresCorrectToolForDrops()
                            .pushReaction(PushReaction.PUSH_ONLY)
                            .lightLevel((state) -> 15)) {
                public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 9, 14);

                @Override
                protected VoxelShape getShape(BlockState arg, BlockGetter arg2, BlockPos arg3, CollisionContext arg4) {
                    return SHAPE;
                }

                @Override
                public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
                    // Get block center coordinates
                    double x = pos.getX() + 0.5D;
                    double y = pos.getY() + 0.5D;
                    double z = pos.getZ() + 0.5D;

                    // Spawn particles around the block
                    for (int i = 0; i < 3; i++) { // Spawn 3 particles per tick
                        // Add some random offset to particle position
                        double offsetX = (random.nextDouble() - 0.5D) * 0.5D;
                        double offsetY = (random.nextDouble() - 0.5D) * 0.5D;
                        double offsetZ = (random.nextDouble() - 0.5D) * 0.5D;

                        level.addParticle(
                                new DustParticleOptions(
                                        new Vector3f(1.0f, 0.0f, 0.0f), // From color
                                        0.5f
                                ),
                                x + offsetX, // X position
                                y + offsetY, // Y position
                                z + offsetZ, // Z position
                                0.0D, // X velocity
                                0.0D, // Y velocity
                                0.0D  // Z velocity
                        );
                    }
                }
            });

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerBlockWithToolTip(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItemWithToolTip(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> void registerBlockItemWithToolTip(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()) {
            @Override
            public void appendHoverText(ItemStack arg, TooltipContext arg2, List<Component> list, TooltipFlag arg3) {
                list.add(Component.translatable("tooltip.mega_showdown." + name + ".tooltip"));
                super.appendHoverText(arg, arg2, list, arg3);
            }
        });
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
