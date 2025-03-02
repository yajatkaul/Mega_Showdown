package com.cobblemon.yajatkaul.mega_showdown.event;

import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.component.Component;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapDecorationType;
import net.minecraft.item.map.MapDecorationTypes;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ExplorationMapLootFunction;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structure;

import java.util.List;
import java.util.Optional;

public class ModEvents {
    public static void register(){
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            Identifier desertPyramidLootTable = Identifier.of("minecraft", "archaeology/desert_pyramid");
            Identifier desertWellLootTable = Identifier.of("minecraft", "archaeology/desert_well");

            if (desertPyramidLootTable.equals(key.getValue()) || desertWellLootTable.equals(key.getValue())) {
                tableBuilder.modifyPools(poolBuilder -> {
                    poolBuilder.with(ItemEntry.builder(MegaStones.RED_ORB).weight(1));
                });
            }

            Identifier ruinColdLootTable = Identifier.of("minecraft", "archaeology/ocean_ruin_cold");
            Identifier ruinWarmLootTable = Identifier.of("minecraft", "archaeology/ocean_ruin_warm");

            if (ruinColdLootTable.equals(key.getValue()) || ruinWarmLootTable.equals(key.getValue())) {
                tableBuilder.modifyPools(poolBuilder -> {
                    poolBuilder.with(ItemEntry.builder(MegaStones.BLUE_ORB).weight(1));
                });
            }
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CARTOGRAPHER, 3, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new TradedItem(MegaStones.MEGA_STONE, 1), // Assuming MegaStones.KEYSTONE is an Item
                    createExplorerMap(entity.getWorld(), entity.getBlockPos(), entity),
                    1, // Max uses
                    6, // Experience
                    0.05f // Price multiplier
            ));
        });
    }

    private static ItemStack createExplorerMap(World world, BlockPos pos, Entity entity) {
        if (!(world instanceof ServerWorld serverWorld)) {
            return ItemStack.EMPTY;
        }

        // Define the TagKey for your custom structure "megaroid"
        TagKey<Structure> megaroidStructureTag = TagKey.of(
                RegistryKeys.STRUCTURE,
                Identifier.of("mega_showdown", "megaroid")
        );


        // We need to use reflection to access the private methods
        // Instead, we'll use the constructor to create the object directly
        LootFunction mapFunction = ExplorationMapLootFunction.builder()
                .withDestination(megaroidStructureTag)  // Use searchStructure instead of destination
                .withZoom((byte) 2)                        // Zoom level of the map
                .searchRadius(1000)                     // How far to search for the structure
                .withDecoration(MapDecorationTypes.RED_X)    // Icon marking the location
                .withSkipExistingChunks(true)               // Skip already found structures
                .build();

        ItemStack mapStack = new ItemStack(Items.MAP);

        // Create a proper LootContext with all required parameters
        LootContextParameterSet lootParams = new LootContextParameterSet.Builder(serverWorld)
                .add(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
                .add(LootContextParameters.THIS_ENTITY, entity)
                .add(LootContextParameters.DAMAGE_SOURCE, entity.getWorld().getDamageSources().generic())
                .build(LootContextTypes.ENTITY);

        LootContext lootContext = new LootContext.Builder(lootParams).build(Optional.empty());

        // Apply the function to the map
        mapStack = mapFunction.apply(mapStack, lootContext);

        // Set the map name and lore
        mapStack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("Lost map"));
        List<Text> loreLines = List.of(
                Text.literal("This map leads you to the lost stone")
        );
        mapStack.set(DataComponentTypes.LORE, new LoreComponent(loreLines));

        return mapStack;
    }
}
