package com.cobblemon.yajatkaul.mega_showdown.event;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents.EventUtils;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapDecorationTypes;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
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
                if(ShowdownConfig.battleModeOnly.get()){
                    new FlagSpeciesFeature("mega", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                }
                EventUtils.revertFormesEnd(pokemon);
            }

            if(ShowdownConfig.battleModeOnly.get()){
                player.setAttached(DataManage.MEGA_DATA, false);
            }
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(newPlayer);

            if(ShowdownConfig.battleModeOnly.get()){
                if(alive){
                    oldPlayer.removeAttached(DataManage.MEGA_DATA);
                }else{
                    newPlayer.removeAttached(DataManage.MEGA_DATA);
                }
            }

            for (Pokemon pokemon : playerPartyStore) {
                if(ShowdownConfig.battleModeOnly.get()){
                    new FlagSpeciesFeature("mega", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                }
                EventUtils.revertFormesEnd(pokemon);
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
