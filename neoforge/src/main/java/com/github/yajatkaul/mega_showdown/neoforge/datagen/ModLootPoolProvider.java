package com.github.yajatkaul.mega_showdown.neoforge.datagen;

import com.cobblemon.mod.common.CobblemonItems;
import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class ModLootPoolProvider implements LootTableSubProvider {
    @Override
    public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(
                ResourceKey.create(Registries.LOOT_TABLE,
                        ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "sets/any_showdown_held_item")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(1.0f, 0.0f))
                                .add(LootItem.lootTableItem(CobblemonItems.CLEAR_AMULET)
                                        .setWeight(1))
                                .add(LootItem.lootTableItem(CobblemonItems.LAGGING_TAIL)
                                        .setWeight(1))
                                .add(LootItem.lootTableItem(CobblemonItems.GRIP_CLAW)
                                        .setWeight(1))
                                .add(LootItem.lootTableItem(MegaShowdownItems.SOUL_DEW.get())
                                        .setWeight(1))
                                .add(LootItem.lootTableItem(MegaShowdownItems.ADRENALINE_ORB.get())
                                        .setWeight(1))
                                .add(LootItem.lootTableItem(CobblemonItems.LUMINOUS_MOSS)
                                        .setWeight(1))
                        )
        );

        output.accept(
                ResourceKey.create(Registries.LOOT_TABLE,
                        ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "wishing_weald/wishing_weald")),
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1))
                                        .setBonusRolls(ConstantValue.exactly(0))
                                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(10))
                                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(1))
                                        .add(LootItem.lootTableItem(CobblemonItems.RELIC_COIN).setWeight(25))
                                        .add(LootItem.lootTableItem(Items.COPPER_INGOT).setWeight(10))
                                        .add(NestedLootTable.lootTableReference(
                                                        ResourceKey.create(Registries.LOOT_TABLE,
                                                                ResourceLocation.fromNamespaceAndPath("cobblemon", "sets/any_type_gem"))
                                                )
                                                .setWeight(5))
                        ).setRandomSequence(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "wishing_weald/wishing_weald"))
        );

        output.accept(
                ResourceKey.create(Registries.LOOT_TABLE,
                        ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "wishing_weald/wishing_weald_chest")),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(1.0f, 2.0f))
                                .add(LootItem.lootTableItem(MegaShowdownItems.ZYGARDE_CELL.get())
                                        .setWeight(1))
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0.0f, 1.0f))
                                .setBonusRolls(ConstantValue.exactly(0.0f))
                                .add(NestedLootTable.lootTableReference(
                                                ResourceKey.create(Registries.LOOT_TABLE,
                                                        ResourceLocation.fromNamespaceAndPath("cobblemon", "sets/any_type_gem"))
                                        )
                                        .setWeight(1))
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0.0f, 1.0f))
                                .setBonusRolls(ConstantValue.exactly(0.0f))
                                .add(NestedLootTable.lootTableReference(
                                                ResourceKey.create(Registries.LOOT_TABLE,
                                                        ResourceLocation.fromNamespaceAndPath("cobblemon", "sets/any_evo_stone"))
                                        )
                                        .setWeight(1))
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(1.0f, 1.0f))
                                .setBonusRolls(ConstantValue.exactly(0.0f))
                                .add(NestedLootTable.lootTableReference(
                                                ResourceKey.create(Registries.LOOT_TABLE,
                                                        ResourceLocation.fromNamespaceAndPath("cobblemon", "sets/any_exp_candy"))
                                        )
                                        .setWeight(1))
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0.0f, 1.0f))
                                .setBonusRolls(ConstantValue.exactly(0.0f))
                                .add(NestedLootTable.lootTableReference(
                                                ResourceKey.create(Registries.LOOT_TABLE,
                                                        ResourceLocation.fromNamespaceAndPath("cobblemon", "sets/any_showdown_held_item"))
                                        )
                                        .setWeight(1))
                        )
        );
    }
}
