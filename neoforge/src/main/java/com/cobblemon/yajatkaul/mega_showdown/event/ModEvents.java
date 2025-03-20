package com.cobblemon.yajatkaul.mega_showdown.event;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.battles.BattleRegistry;
import com.cobblemon.mod.common.platform.events.ServerTickEvent;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.ZMoves;
import com.cobblemon.yajatkaul.mega_showdown.mixin.LootPoolAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ExplorationMapFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import top.theillusivec4.curios.api.event.CurioCanUnequipEvent;

import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = MegaShowdown.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {
    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation lootTableId = event.getName();
        ResourceLocation desertPyramidLootTable = ResourceLocation.fromNamespaceAndPath("minecraft", "archaeology/desert_pyramid");
        ResourceLocation desertWellLootTable = ResourceLocation.fromNamespaceAndPath("minecraft", "archaeology/desert_well");

        ResourceLocation ruinWarmLootTable = ResourceLocation.fromNamespaceAndPath("minecraft", "archaeology/ocean_warm_cold");
        ResourceLocation ruinColdLootTable = ResourceLocation.fromNamespaceAndPath("minecraft", "archaeology/ocean_ruin_cold");

        ResourceLocation trialChamberLootTable = ResourceLocation.fromNamespaceAndPath("minecraft", "chests/trial_chambers/reward_ominous_unique");

        ResourceLocation luna_ruins = ResourceLocation.fromNamespaceAndPath("cobblemon", "ruins/common/luna_henge_ruins");
        ResourceLocation mossy_ruins = ResourceLocation.fromNamespaceAndPath("cobblemon", "ruins/common/mossy_oubliette_ruins");

        ResourceLocation ancient_city = ResourceLocation.fromNamespaceAndPath("minecraft", "chests/ancient_city");

        LootTable table = event.getTable();
        LootPool mainPool = table.getPool("main");

        if (desertPyramidLootTable.equals(lootTableId) || desertWellLootTable.equals(lootTableId)) {
            table = event.getTable();
            mainPool = table.getPool("main");

            if (mainPool != null && !mainPool.isFrozen()) {
                // Cast to accessor interface
                LootPoolAccessor poolAccessor = (LootPoolAccessor) mainPool;

                // Create new builder with existing properties
                LootPool.Builder newPoolBuilder = LootPool.lootPool()
                        .setRolls(mainPool.getRolls())
                        .setBonusRolls(mainPool.getBonusRolls());

                // Add all existing entries using the accessor
                poolAccessor.getEntries().forEach(entry ->
                        newPoolBuilder.add(new LootPoolEntryContainer.Builder() {
                            @Override
                            protected LootPoolEntryContainer.Builder getThis() {
                                return null;
                            }

                            @Override
                            public LootPoolEntryContainer build() {
                                return entry;
                            }
                        })
                );

                // Add your new item
                newPoolBuilder.add(LootItem.lootTableItem(MegaStones.RED_ORB)
                        .setWeight(1));

                // Preserve the name
                if (mainPool.getName() != null) {
                    newPoolBuilder.name(mainPool.getName());
                }

                // Replace the old pool with the new one
                table.removePool("main");
                table.addPool(newPoolBuilder.build());
            }
        }else if (ruinColdLootTable.equals(lootTableId) || ruinWarmLootTable.equals(lootTableId)) {

            if (mainPool != null && !mainPool.isFrozen()) {
                // Cast to accessor interface
                LootPoolAccessor poolAccessor = (LootPoolAccessor) mainPool;

                // Create new builder with existing properties
                LootPool.Builder newPoolBuilder = LootPool.lootPool()
                        .setRolls(mainPool.getRolls())
                        .setBonusRolls(mainPool.getBonusRolls());

                // Add all existing entries using the accessor
                poolAccessor.getEntries().forEach(entry ->
                        newPoolBuilder.add(new LootPoolEntryContainer.Builder() {
                            @Override
                            protected LootPoolEntryContainer.Builder getThis() {
                                return null;
                            }

                            @Override
                            public LootPoolEntryContainer build() {
                                return entry;
                            }
                        })
                );

                // Add your new item
                newPoolBuilder.add(LootItem.lootTableItem(MegaStones.BLUE_ORB)
                        .setWeight(1));

                // Preserve the name
                if (mainPool.getName() != null) {
                    newPoolBuilder.name(mainPool.getName());
                }

                // Replace the old pool with the new one
                table.removePool("main");
                table.addPool(newPoolBuilder.build());
            }
        }else if (trialChamberLootTable.equals(lootTableId)) {
            if (mainPool != null && !mainPool.isFrozen()) {
                // Cast to accessor interface
                LootPoolAccessor poolAccessor = (LootPoolAccessor) mainPool;

                // Create new builder with existing properties
                LootPool.Builder newPoolBuilder = LootPool.lootPool()
                        .setRolls(mainPool.getRolls())
                        .setBonusRolls(mainPool.getBonusRolls());

                // Add all existing entries using the accessor
                poolAccessor.getEntries().forEach(entry ->
                        newPoolBuilder.add(new LootPoolEntryContainer.Builder() {
                            @Override
                            protected LootPoolEntryContainer.Builder getThis() {
                                return null;
                            }

                            @Override
                            public LootPoolEntryContainer build() {
                                return entry;
                            }
                        })
                );

                // Add your new item
                newPoolBuilder.add(LootItem.lootTableItem(ZMoves.BLANK_Z).setWeight(2));

                // Preserve the name
                if (mainPool.getName() != null) {
                    newPoolBuilder.name(mainPool.getName());
                }

                // Replace the old pool with the new one
                table.removePool("main");
                table.addPool(newPoolBuilder.build());
            }
        } else if (luna_ruins.equals(lootTableId)) {
            if (mainPool != null && !mainPool.isFrozen()) {
                LootPoolAccessor poolAccessor = (LootPoolAccessor) mainPool;

                LootPool.Builder newPoolBuilder = LootPool.lootPool()
                        .setRolls(mainPool.getRolls())
                        .setBonusRolls(mainPool.getBonusRolls());

                poolAccessor.getEntries().forEach(entry ->
                        newPoolBuilder.add(new LootPoolEntryContainer.Builder() {
                            @Override
                            protected LootPoolEntryContainer.Builder getThis() {
                                return null;
                            }

                            @Override
                            public LootPoolEntryContainer build() {
                                return entry;
                            }
                        })
                );

                newPoolBuilder.add(LootItem.lootTableItem(ModItems.RUSTED_SWORD).setWeight(6));

                if (mainPool.getName() != null) {
                    newPoolBuilder.name(mainPool.getName());
                }

                table.removePool("main");
                table.addPool(newPoolBuilder.build());
            }
        }else if (mossy_ruins.equals(lootTableId)) {
            if (mainPool != null && !mainPool.isFrozen()) {
                LootPoolAccessor poolAccessor = (LootPoolAccessor) mainPool;

                LootPool.Builder newPoolBuilder = LootPool.lootPool()
                        .setRolls(mainPool.getRolls())
                        .setBonusRolls(mainPool.getBonusRolls());

                poolAccessor.getEntries().forEach(entry ->
                        newPoolBuilder.add(new LootPoolEntryContainer.Builder() {
                            @Override
                            protected LootPoolEntryContainer.Builder getThis() {
                                return null;
                            }

                            @Override
                            public LootPoolEntryContainer build() {
                                return entry;
                            }
                        })
                );

                newPoolBuilder.add(LootItem.lootTableItem(ModItems.RUSTED_SHIELD).setWeight(6));

                if (mainPool.getName() != null) {
                    newPoolBuilder.name(mainPool.getName());
                }

                table.removePool("main");
                table.addPool(newPoolBuilder.build());
            }
        }else if (ancient_city.equals(lootTableId)) {
            mainPool = table.getPool("pool0");
            if (mainPool != null && !mainPool.isFrozen()) {
                LootPoolAccessor poolAccessor = (LootPoolAccessor) mainPool;
                LootPool.Builder newPoolBuilder = LootPool.lootPool()
                        .setRolls(mainPool.getRolls())
                        .setBonusRolls(mainPool.getBonusRolls());

                poolAccessor.getEntries().forEach(entry ->
                        newPoolBuilder.add(new LootPoolEntryContainer.Builder() {
                            @Override
                            protected LootPoolEntryContainer.Builder getThis() {
                                return null;
                            }

                            @Override
                            public LootPoolEntryContainer build() {
                                return entry;
                            }
                        })
                );

                newPoolBuilder.add(LootItem.lootTableItem(ModItems.PRISON_BOTTLE).setWeight(3));

                if (mainPool.getName() != null) {
                    newPoolBuilder.name(mainPool.getName());
                }

                table.removePool("pool0");
                table.addPool(newPoolBuilder.build());
            }
        }
    }

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event){
        if(event.getType() == VillagerProfession.CARTOGRAPHER){
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // get(3) = villager level required
            trades.get(3).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(MegaStones.MEGA_STONE, 1),
                    createExplorerMap(entity.level(), entity.getOnPos(), entity),
                    1, 6, 0.05f
            ));

        }
    }

    private static ItemStack createExplorerMap(Level level, BlockPos pos, Entity entity) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return ItemStack.EMPTY;
        }
        // Define the TagKey for your custom structure "megaroid"
        TagKey<Structure> megaroidStructureTag = TagKey.create(
                Registries.STRUCTURE,
                ResourceLocation.fromNamespaceAndPath("mega_showdown", "megaroid")
        );

        // Correctly create the explorer map using the TagKey
        LootItemFunction mapFunction = new ExplorationMapFunction.Builder()
                .setDestination(megaroidStructureTag) // Use TagKey instead of Holder
                .setZoom((byte) 2) // Zoom level of the map
                .setSearchRadius(1000) // How far to search for the structure
                .setMapDecoration(MapDecorationTypes.RED_X) // Icon marking the location
                .setSkipKnownStructures(true) // Skip already found structures
                .build(); // Generate the map

        ItemStack mapStack = new ItemStack(Items.MAP);

        // Create a proper LootContext with all required parameters
        LootParams lootParams = new LootParams.Builder(serverLevel)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                .withParameter(LootContextParams.THIS_ENTITY, entity)
                .withParameter(LootContextParams.DAMAGE_SOURCE, entity.level().damageSources().generic())
                .create(LootContextParamSets.ENTITY);

        LootContext lootContext = new LootContext.Builder(lootParams).create(Optional.empty());

        // Apply the function to the map
        mapStack = mapFunction.apply(mapStack, lootContext);

        mapStack.set(DataComponents.CUSTOM_NAME, Component.literal("Lost map"));
        List<Component> loreLines = List.of(
                Component.literal("This map leads you to the lost stone")
        );
        mapStack.set(DataComponents.LORE, new ItemLore(loreLines));

        return mapStack;
    }

    @SubscribeEvent
    public static void onCurioChange(CurioCanUnequipEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PokemonBattle battle = BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(player);

            if(battle != null && event.getStack().is(TeraMoves.TERA_ORB)){
                event.setUnequipResult(TriState.FALSE);
            }
        }
    }

    @SubscribeEvent
    private static void onServerJoin(PlayerEvent.PlayerLoggedInEvent playerLoggedInEvent) {
        if(Config.battleModeOnly){
            if(playerLoggedInEvent.getEntity() instanceof ServerPlayer player){
                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

                for (Pokemon pokemon : playerPartyStore) {
                    if(Config.battleModeOnly){
                        new FlagSpeciesFeature("mega", false).apply(pokemon);
                        new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                        new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                    }
                    new StringSpeciesFeature("schooling_form", "solo").apply(pokemon);
                    new FlagSpeciesFeature("embody_aspect", false).apply(pokemon);
                    new StringSpeciesFeature("stance_forme", "shield").apply(pokemon);
                    new StringSpeciesFeature("forecast_form", "normal").apply(pokemon);
                    new StringSpeciesFeature("meteor_shield", "meteor").apply(pokemon);
                    new StringSpeciesFeature("disguise_form", "disguised").apply(pokemon);
                    if(pokemon.getSpecies().getName().equals("Greninja") && pokemon.getAspects().contains("ash")){
                        new StringSpeciesFeature("battle_bond", "bond").apply(pokemon);
                    }
                }

                if(Config.battleModeOnly){
                    player.setData(DataManage.MEGA_DATA, false);
                }
            }
        }
    }

    @SubscribeEvent
    private static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event){
        if(Config.battleModeOnly){
            if(event.getEntity() instanceof ServerPlayer player){
                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

                for (Pokemon pokemon : playerPartyStore) {
                    if(Config.battleModeOnly){
                        new FlagSpeciesFeature("mega", false).apply(pokemon);
                        new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                        new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                    }
                    new StringSpeciesFeature("schooling_form", "solo").apply(pokemon);
                    new FlagSpeciesFeature("embody_aspect", false).apply(pokemon);
                    new StringSpeciesFeature("stance_forme", "shield").apply(pokemon);
                    new StringSpeciesFeature("disguise_form", "disguised").apply(pokemon);
                    new StringSpeciesFeature("forecast_form", "normal").apply(pokemon);
                    new StringSpeciesFeature("meteor_shield", "meteor").apply(pokemon);
                    if(pokemon.getSpecies().getName().equals("Greninja") && pokemon.getAspects().contains("ash")){
                        new StringSpeciesFeature("battle_bond", "bond").apply(pokemon);
                    }
                }

                if(Config.battleModeOnly){
                    player.setData(DataManage.MEGA_DATA, false);
                }
            }
        }
    }
}