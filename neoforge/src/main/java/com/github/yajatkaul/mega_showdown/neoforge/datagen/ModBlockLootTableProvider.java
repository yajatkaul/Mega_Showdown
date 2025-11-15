package com.github.yajatkaul.mega_showdown.neoforge.datagen;

import com.cobblemon.mod.common.CobblemonItems;
import com.github.yajatkaul.mega_showdown.block.MegaShowdownBlocks;
import com.github.yajatkaul.mega_showdown.block.custom.MaxMushroomBlock;
import com.github.yajatkaul.mega_showdown.block.custom.ReassemblyUnitBlock;
import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(MegaShowdownBlocks.POWER_SPOT.get());
        add(MegaShowdownBlocks.MAX_MUSHROOM.get(),
                createMaxMushroomDrops(MegaShowdownBlocks.MAX_MUSHROOM.get(), MegaShowdownBlocks.MAX_MUSHROOM.get().asItem()));
        dropSelf(MegaShowdownBlocks.GRACIDEA_FLOWER.get());

        add(MegaShowdownBlocks.POTTED_GRACIDEA.get(),
                createPotFlowerItemTable(MegaShowdownBlocks.GRACIDEA_FLOWER.get())
        );

        add(MegaShowdownBlocks.WISHING_STAR_CRYSTAL.get(), block ->
                createSingleItemTableWithSilkTouch(block, MegaShowdownItems.WISHING_STAR.get()));

        add(MegaShowdownBlocks.REASSEMBLY_UNIT.get(), block ->
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .when(ExplosionCondition.survivesExplosion())
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(ReassemblyUnitBlock.HALF, DoubleBlockHalf.LOWER)))
                                .add(LootItem.lootTableItem(MegaShowdownBlocks.REASSEMBLY_UNIT.get()))
                        )
        );

        dropSelf(MegaShowdownBlocks.DORMANT_CRYSTAL.get());
        add(MegaShowdownBlocks.KEYSTONE_ORE.get(), block ->
                createSingleItemTableWithSilkTouch(block, MegaShowdownItems.KEYSTONE.get()));

        add(MegaShowdownBlocks.MEGA_STONE_CRYSTAL.get(), block ->
                createSingleItemTableWithSilkTouch(block, MegaShowdownItems.MEGA_STONE.get()));

        add(MegaShowdownBlocks.MEGA_METEORID_DAWN_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.DAWN_STONE));

        add(MegaShowdownBlocks.MEGA_METEORID_DUSK_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.DUSK_STONE));

        add(MegaShowdownBlocks.MEGA_METEORID_FIRE_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.FIRE_STONE));

        add(MegaShowdownBlocks.MEGA_METEORID_ICE_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.ICE_STONE));

        add(MegaShowdownBlocks.MEGA_METEORID_LEAF_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.LEAF_STONE));

        add(MegaShowdownBlocks.MEGA_METEORID_MOON_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.MOON_STONE));

        add(MegaShowdownBlocks.MEGA_METEORID_SHINY_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.SHINY_STONE));

        add(MegaShowdownBlocks.MEGA_METEORID_SUN_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.SUN_STONE));

        add(MegaShowdownBlocks.MEGA_METEORID_THUNDER_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.THUNDER_STONE));

        add(MegaShowdownBlocks.MEGA_METEORID_WATER_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.WATER_STONE));


        dropSelf(MegaShowdownBlocks.MEGA_METEOROID_BLOCK.get());
        dropSelf(MegaShowdownBlocks.MEGA_METEOROID_RADIATED_BLOCK.get());

        dropSelf(MegaShowdownBlocks.DEOXYS_METEORITE.get());

        dropSelf(MegaShowdownBlocks.ROTOM_WASHING_MACHINE.get());
        dropSelf(MegaShowdownBlocks.ROTOM_FAN.get());
        dropSelf(MegaShowdownBlocks.ROTOM_FRIDGE.get());
        dropSelf(MegaShowdownBlocks.ROTOM_MOW.get());
        dropSelf(MegaShowdownBlocks.ROTOM_OVEN.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        List<Block> blocks = new ArrayList<>();
        for (RegistrySupplier<Block> supplier : MegaShowdownBlocks.BLOCKS) {
            blocks.add(supplier.get());
        }
        return blocks;
    }

    private LootTable.Builder createMaxMushroomDrops(Block block, Item item) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(item)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(MaxMushroomBlock.AGE, 2)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F))))
                )
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(item)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(MaxMushroomBlock.AGE, 3)))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3.0F))))
                );
    }
}