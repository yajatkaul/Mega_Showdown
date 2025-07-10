package com.cobblemon.yajatkaul.mega_showdown.block;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.*;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.zygarde.ReassemblyUnitBlock;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

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

    public static final DeferredBlock<Block> MEGA_METEOROID_BLOCK = registerMeteoroidBlock("mega_meteorid_block");

    public static final DeferredBlock<Block> MEGA_EVO_BLOCK = registerMeteoroidBlock("mega_evo_block");

    public static final DeferredBlock<Block> MEGA_EVO_BRICK = registerMeteoroidBlock("mega_evo_brick");

    public static final DeferredBlock<Block> CHISELED_MEGA_EVO_BRICK = registerMeteoroidBlock("chiseled_mega_evo_brick");

    public static final DeferredBlock<Block> CHISELED_MEGA_EVO_BLOCK = registerMeteoroidBlock("chiseled_mega_evo_block");

    public static final DeferredBlock<Block> POLISHED_MEGA_EVO_BLOCK = registerMeteoroidBlock("polished_mega_evo_block");

    public static final DeferredBlock<Block> DEOXYS_METEORITE = BLOCKS.register("deoxys_meteorite",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PINK)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> POWER_SPOT = registerBlockWithToolTip("power_spot",
            () -> new PowerSpotBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> MAX_MUSHROOM = BLOCKS.register("max_mushroom", () ->
            new MaxMushroomBlock(BlockBehaviour.Properties.of().noCollission().sound(SoundType.FLOWERING_AZALEA)));

    public static final DeferredBlock<Block> GRACIDEA_FLOWER = BLOCKS.register("gracidea_flower", GracideaBlock::new);

    public static final DeferredBlock<Block> POTTED_GRACIDEA = BLOCKS.register("potted_gracidea",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GRACIDEA_FLOWER,
                    BlockBehaviour.Properties.of().noOcclusion().instabreak()));

    public static final DeferredBlock<Block> PEDESTAL = registerBlock("pedestal",
            () -> new PedestalBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(2)));

    public static final DeferredBlock<Block> REASSEMBLY_UNIT = registerBlock("reassembly_unit",
            () -> new ReassemblyUnitBlock(BlockBehaviour.Properties.of()));

    public static final DeferredBlock<AmethystClusterBlock> WISHING_STAR_CRYSTAL = registerBlock("wishing_star_crystal",
            () -> new WishingStarCrystal(4, 3, BlockBehaviour.Properties.of()));

    public static final DeferredBlock<AmethystClusterBlock> DORMANT_CRYSTAL = registerBlock("dormant_crystal",
            () -> new DormantCrystal(4, 3, BlockBehaviour.Properties.of()));

    public static final DeferredBlock<Block> ROTOM_WASHING_MACHINE = registerRotomBlock("rotom_washing_machine", "wash");
    public static final DeferredBlock<Block> ROTOM_FAN = registerRotomBlock("rotom_fan", "fan");
    public static final DeferredBlock<Block> ROTOM_MOW = registerRotomBlock("rotom_mow", "mow");
    public static final DeferredBlock<Block> ROTOM_FRIDGE = registerRotomBlock("rotom_fridge", "frost");
    public static final DeferredBlock<Block> ROTOM_OVEN = registerRotomBlock("rotom_oven", "heat");

    private static DeferredBlock<Block> registerMeteoroidBlock(String name) {
        return registerBlock(name, () -> new Block(BlockBehaviour
                .Properties.of()
                .strength(3f)
                .mapColor(MapColor.COLOR_PURPLE)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE)));
    }

    private static DeferredBlock<Block> registerRotomBlock(String name, String form) {
        return registerBlockWithoutItem(name, () ->
                new RotomUnit(BlockBehaviour.Properties.of()
                        .requiresCorrectToolForDrops()
                        .strength(2)
                        .mapColor(DyeColor.ORANGE)
                        .sound(SoundType.METAL), form));
    }

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

    private static <T extends Block> DeferredBlock<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
