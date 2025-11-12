package com.github.yajatkaul.mega_showdown.block;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.block.custom.CustomHitBoxBlock;
import com.github.yajatkaul.mega_showdown.block.custom.MaxMushroomBlock;
import com.github.yajatkaul.mega_showdown.block.custom.ReassemblyUnitBlock;
import com.github.yajatkaul.mega_showdown.creative.MegaShowdownTabs;
import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import com.github.yajatkaul.mega_showdown.item.custom.dynamax.MaxMushroom;
import com.github.yajatkaul.mega_showdown.item.custom.form_change.Gracedia;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class MegaShowdownBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(MegaShowdown.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> POWER_SPOT = registerBlockWithToolTip("power_spot",
            () -> new CustomHitBoxBlock(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_RED)
                    .lightLevel((state) -> 15)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE),
                    Block.box(2, 0, 2, 14, 4, 14)),
            MegaShowdownTabs.DYNAMAX_TAB
    );

    public static final RegistrySupplier<Block> MAX_MUSHROOM = registerBlockWithBlockItems(
            "max_mushroom",
            () -> new MaxMushroomBlock(BlockBehaviour.Properties.of()
                    .noCollission()
                    .sound(SoundType.FLOWERING_AZALEA)
            ),
            block -> new MaxMushroom(block.get(), new Item.Properties())
    );

    public static final RegistrySupplier<Block> GRACIDEA_FLOWER = registerBlockWithBlockItems("gracidea_flower",
            () -> new FlowerBlock(MobEffects.HEAL, 8, BlockBehaviour.Properties.of()
                    .noCollission()
                    .noOcclusion()
                    .lightLevel((state) -> 15)
                    .instabreak()
                    .sound(SoundType.GRASS)),
            (block) -> new Gracedia(block.get(), new Item.Properties().arch$tab(MegaShowdownTabs.FORM_TAB)));

    public static final RegistrySupplier<Block> POTTED_GRACIDEA = registerFlowerPlotBlock("potted_gracidea", GRACIDEA_FLOWER.get());

    public static final RegistrySupplier<Block> REASSEMBLY_UNIT = registerBlockWithToolTip(
            "reassembly_unit",
            () -> new ReassemblyUnitBlock(BlockBehaviour.Properties.of()),
            MegaShowdownTabs.FORM_TAB
    );

    private static RegistrySupplier<Block> registerFlowerPlotBlock(String name, Block block) {
        return BLOCKS.register(name, () -> new FlowerPotBlock(block,
                BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ALLIUM)
                        .noOcclusion()
                        .instabreak()
        ));
    }

    private static RegistrySupplier<Block> registerBlockWithBlockItems(String name,
                                                               Supplier<Block> block,
                                                               Function<RegistrySupplier<Block>, Item> itemFactory
    ) {
        RegistrySupplier<Block> blockSupplier = BLOCKS.register(name, block);
        MegaShowdownItems.ITEMS.register(name, () -> itemFactory.apply(blockSupplier));
        return blockSupplier;
    }

    private static RegistrySupplier<Block> registerBlock(String name, Supplier<Block> block) {
        RegistrySupplier<Block> blockSupplier = BLOCKS.register(name, block);
        MegaShowdownItems.ITEMS.register(name, () -> new BlockItem(blockSupplier.get(), new Item.Properties()));
        return blockSupplier;
    }

    private static RegistrySupplier<Block> registerBlockWithToolTip(String name, Supplier<Block> block, DeferredSupplier<CreativeModeTab> tab) {
        RegistrySupplier<Block> blockSupplier = BLOCKS.register(name, block);
        MegaShowdownItems.ITEMS.register(name, () -> new BlockItem(blockSupplier.get(), new Item.Properties().arch$tab(tab)) {
            @Override
            public void appendHoverText(ItemStack arg, TooltipContext arg2, List<Component> list, TooltipFlag arg3) {
                list.add(Component.translatable("tooltip.mega_showdown." + name + ".tooltip"));
                super.appendHoverText(arg, arg2, list, arg3);
            }
        });
        return blockSupplier;
    }

    public static void register() {
        BLOCKS.register();
    }
}
