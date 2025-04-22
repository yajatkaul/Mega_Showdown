package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.MaxMushroomBlock;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.ReassemblyUnitBlock;
import com.cobblemon.yajatkaul.mega_showdown.item.DynamaxItems;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
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

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        add(MegaOres.KEYSTONE_ORE.get(), block ->
                createSingleItemTableWithSilkTouch(block, MegaStones.KEYSTONE.get()));

        add(MegaOres.MEGA_STONE_CRYSTAL.get(), block ->
                createSingleItemTableWithSilkTouch(block, MegaStones.MEGA_STONE.get()));

        dropSelf(ModBlocks.MEGA_METEOROID_BLOCK.get());
        dropSelf(ModBlocks.MEGA_EVO_BLOCK.get());
        dropSelf(ModBlocks.KEYSTONE_BLOCK.get());
        dropSelf(ModBlocks.CHISELED_MEGA_EVO_BRICK.get());
        dropSelf(ModBlocks.CHISELED_MEGA_EVO_BLOCK.get());
        dropSelf(ModBlocks.MEGA_EVO_BRICK.get());

        dropSelf(ModBlocks.POLISHED_MEGA_EVO_BLOCK.get());

        add(MegaOres.MEGA_METEORID_DAWN_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.DAWN_STONE));

        add(MegaOres.MEGA_METEORID_DUSK_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.DUSK_STONE));

        add(MegaOres.MEGA_METEORID_FIRE_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.FIRE_STONE));

        add(MegaOres.MEGA_METEORID_ICE_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.ICE_STONE));

        add(MegaOres.MEGA_METEORID_LEAF_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.LEAF_STONE));

        add(MegaOres.MEGA_METEORID_MOON_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.MOON_STONE));

        add(MegaOres.MEGA_METEORID_SHINY_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.SHINY_STONE));

        add(MegaOres.MEGA_METEORID_SUN_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.SUN_STONE));

        add(MegaOres.MEGA_METEORID_THUNDER_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.THUNDER_STONE));

        add(MegaOres.MEGA_METEORID_WATER_ORE.get(),
                block -> createOreDrop(block, CobblemonItems.WATER_STONE));

        dropSelf(ModBlocks.DEOXYS_METEORITE.get());

        dropSelf(ModBlocks.POWER_SPOT.get());

        add(ModBlocks.MAX_MUSHROOM.get(),
                createMaxMushroomDrops(ModBlocks.MAX_MUSHROOM.get(), DynamaxItems.MAX_MUSHROOM.get()));

        dropSelf(ModBlocks.GRACIDEA_FLOWER.get());

        add(ModBlocks.POTTED_GRACIDEA.get(),
                createPotFlowerItemTable(ModBlocks.GRACIDEA_FLOWER.get())
        );

        dropSelf(ModBlocks.PEDESTAL.get());

        add(ModBlocks.WISHING_STAR_CRYSTAL.get(), block ->
                createSingleItemTableWithSilkTouch(block, DynamaxItems.WISHING_STAR.get()));

        add(ModBlocks.REASSEMBLY_UNIT.get(), block ->
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .when(ExplosionCondition.survivesExplosion())
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(ReassemblyUnitBlock.HALF, DoubleBlockHalf.LOWER)))
                                .add(LootItem.lootTableItem(ModBlocks.REASSEMBLY_UNIT.get()))
                        )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
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
