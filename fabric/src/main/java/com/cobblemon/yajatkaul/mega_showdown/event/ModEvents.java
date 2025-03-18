package com.cobblemon.yajatkaul.mega_showdown.event;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.item.ZMoves;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
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
import net.minecraft.server.network.ServerPlayerEntity;
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

            Identifier trialChamberLootTable = Identifier.of("minecraft", "chests/trial_chambers/reward_ominous_unique");

            if(trialChamberLootTable.equals(key.getValue())){
                tableBuilder.modifyPools(poolBuilder -> {
                    poolBuilder.with(ItemEntry.builder(ZMoves.BLANK_Z).weight(2));
                });
            }

            Identifier luna_ruins = Identifier.of("cobblemon", "ruins/common/luna_henge_ruins");
            Identifier mossy_ruins = Identifier.of("cobblemon", "ruins/common/mossy_oubliette_ruins");

            if(luna_ruins.equals(key.getValue())){
                tableBuilder.modifyPools(poolBuilder -> {
                    poolBuilder.with(ItemEntry.builder(ModItems.RUSTED_SWORD).weight(6));
                });
            }

            if(mossy_ruins.equals(key.getValue())){
                tableBuilder.modifyPools(poolBuilder -> {
                    poolBuilder.with(ItemEntry.builder(ModItems.RUSTED_SHIELD).weight(6));
                });
            }

            Identifier ancient_city = Identifier.of("minecraft", "chests/ancient_city");

            if(ancient_city.equals(key.getValue())){
                final boolean[] canEdit = {true};
                tableBuilder.modifyPools(poolBuilder -> {
                    if(canEdit[0]){
                        poolBuilder.with(ItemEntry.builder(ModItems.PRISON_BOTTLE).weight(1));
                        canEdit[0] = false;
                    }
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

        ServerPlayConnectionEvents.JOIN.register((handler, sender, serverJoin) -> {
            ServerPlayerEntity player = handler.player;
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

            for (Pokemon pokemon : playerPartyStore) {
                new FlagSpeciesFeature("mega", false).apply(pokemon);
                new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                new FlagSpeciesFeature("embody_aspect", false).apply(pokemon);
            }

            player.setAttached(DataManage.MEGA_DATA, false);
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(newPlayer);

            if(alive){
                oldPlayer.removeAttached(DataManage.MEGA_DATA);
            }else{
                newPlayer.removeAttached(DataManage.MEGA_DATA);
            }

            for (Pokemon pokemon : playerPartyStore) {
                new FlagSpeciesFeature("mega", false).apply(pokemon);
                new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                new FlagSpeciesFeature("embody_aspect", false).apply(pokemon);
            }
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
