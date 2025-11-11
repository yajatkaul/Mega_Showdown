package com.github.yajatkaul.mega_showdown.block;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.block.custom.CustomHitBoxBlock;
import com.github.yajatkaul.mega_showdown.block.custom.MaxMushroomBlock;
import com.github.yajatkaul.mega_showdown.creative.MegaShowdownTabs;
import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import com.github.yajatkaul.mega_showdown.item.custom.dynamax.MaxMushroom;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.List;
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

    public static final RegistrySupplier<Block> MAX_MUSHROOM = registerMaxMushroom("max_mushroom", () ->
            new MaxMushroomBlock(BlockBehaviour.Properties.of().noCollission().sound(SoundType.FLOWERING_AZALEA)));

    private static RegistrySupplier<Block> registerBlock(String name, Supplier<Block> block) {
        RegistrySupplier<Block> blockSupplier = BLOCKS.register(name, block);
        MegaShowdownItems.ITEMS.register(name, () -> new BlockItem(blockSupplier.get(), new Item.Properties()));
        return blockSupplier;
    }

    private static RegistrySupplier<Block> registerMaxMushroom(String name, Supplier<Block> block) {
        RegistrySupplier<Block> blockSupplier = BLOCKS.register(name, block);
        MegaShowdownItems.ITEMS.register(name, () -> new MaxMushroom(blockSupplier.get(), new Item.Properties().arch$tab(MegaShowdownTabs.DYNAMAX_TAB)));
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
