package com.cobblemon.yajatkaul.mega_showdown.event;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.datapack.handler.ItemHandler;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils.EventUtils;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.mixin.accessors.LootPoolAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ExplorationMapFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = MegaShowdown.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {
    @SubscribeEvent
    private static void addCustomTrades(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.CARTOGRAPHER) {
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

        mapStack.set(DataComponents.CUSTOM_NAME, Component.translatable("message.mega_showdown.lost_map"));
        List<Component> loreLines = List.of(
                Component.translatable("message.mega_showdown.lost_map.desc")
        );
        mapStack.set(DataComponents.LORE, new ItemLore(loreLines));

        return mapStack;
    }

    @SubscribeEvent
    private static void onServerJoin(PlayerEvent.PlayerLoggedInEvent playerLoggedInEvent) {
        if (!playerLoggedInEvent.getEntity().level().isClientSide) {
            ServerPlayer player = (ServerPlayer) playerLoggedInEvent.getEntity();

            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

            for (Pokemon pokemon : playerPartyStore) {
                EventUtils.revertFormesEnd(pokemon);
            }
        }
    }

    @SubscribeEvent
    private static void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation lootTableId = event.getName();
        ResourceLocation cobblemonLunaHengeRuinLootTable = ResourceLocation.fromNamespaceAndPath("cobblemon", "ruins/common/luna_henge_ruins");

        LootTable table = event.getTable();
        LootPool mainPool = table.getPool("main");

        if (cobblemonLunaHengeRuinLootTable.equals(lootTableId)) {
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

                newPoolBuilder.add(LootItem.lootTableItem(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE)
                        .setWeight(5));

                if (mainPool.getName() != null) {
                    newPoolBuilder.name(mainPool.getName());
                }

                table.removePool("main");
                table.addPool(newPoolBuilder.build());
            }
        }
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack itemStack = event.getItemStack();
        Level level = event.getLevel();

        boolean consumed = ItemHandler.useItem(player, level, hand, itemStack);

        if (consumed) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Level level = event.getLevel();

        boolean consumed = useOnEntity(player, level, event.getTarget());

        if (consumed) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }

    private static boolean useOnEntity(Player player, Level level, Entity entity) {
        if (level.isClientSide) {
            return false;
        }

        if (entity instanceof PokemonEntity pk) {
            if (pk.getAspects().contains("core-percent") && !player.getMainHandItem().is(FormeChangeItems.ZYGARDE_CUBE) && !player.getOffhandItem().is(FormeChangeItems.ZYGARDE_CUBE)) {
                player.addItem(new ItemStack(FormeChangeItems.ZYGARDE_CORE.get()));
                if (pk.getPokemon().getOwnerPlayer() == player) {
                    PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);
                    playerPartyStore.remove(pk.getPokemon());
                } else {
                    entity.discard();
                }
                return true;
            }
        }

        return false;
    }
}