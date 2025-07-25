package com.cobblemon.yajatkaul.mega_showdown.datapack.handler;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.dataAttachments.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.FusionData;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.KeyItemData;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemHandler {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 1; // 1 sec

    public static boolean Possible(ServerPlayerEntity player) {
        UUID playerId = player.getUuid();
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId) && currentTime < cooldowns.get(playerId)) {
            player.sendMessage(
                    Text.translatable("message.mega_showdown.not_so_fast").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return false;
        }

        // Apply cooldown
        cooldowns.put(playerId, currentTime + COOLDOWN_TIME);
        return true;
    }

    public static TypedActionResult<ItemStack> useItem(PlayerEntity player, World world, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (world.isClient || player.isCrawling()) {
            return TypedActionResult.pass(itemStack);
        }

        if (!itemStack.isEmpty()) {

            for (FusionData fusion : Utils.fusionRegistry) {
                Item item = Registries.ITEM.get(Identifier.tryParse(fusion.item_id()));
                if (HandlerUtils.itemValidator(item, fusion.custom_model_data(), itemStack, fusion.item_id())) {
                    EntityHitResult entityHit = HandlerUtils.getEntityLookingAt(player, 4.5);
                    if (entityHit == null) {
                        Pokemon currentValue = itemStack.getOrDefault(DataManage.POKEMON_STORAGE, null);
                        if (currentValue != null) {
                            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);
                            playerPartyStore.add(currentValue);
                            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(fusion.item_name()));
                            itemStack.remove(DataManage.POKEMON_STORAGE);
                        }
                        return TypedActionResult.pass(itemStack);
                    }
                    Entity context = entityHit.getEntity();

                    if (!(context instanceof PokemonEntity pk)) {
                        return TypedActionResult.pass(itemStack);
                    }

                    Pokemon pokemon = pk.getPokemon();
                    if (pokemon.getOwnerPlayer() != player || pokemon.getEntity() == null || pk.isBattling() || pk.getDataTracker().get(PokemonEntity.getEVOLUTION_STARTED())) {
                        return TypedActionResult.pass(itemStack);
                    }

                    PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);
                    Pokemon currentValue = itemStack.getOrDefault(DataManage.POKEMON_STORAGE, null);

                    if (fusion.fusion_mons().contains(pokemon.getSpecies().getName()) && !HandlerUtils.listCheck(fusion.fusion_blacklist_aspects(), pokemon.getAspects(), true)) {
                        for (List<String> condition : fusion.revert_if()) {
                            if (pokemon.getAspects().containsAll(condition)) {
                                Pokemon pokemonSave = Pokemon.Companion.loadFromNBT(player.getWorld().getRegistryManager(), pokemon.getPersistentData().getCompound("fusion_pokemon"));
                                playerPartyStore.add(pokemonSave);
                                pokemon.getPersistentData().remove("fusion_forme");
                                HandlerUtils.applyEffects(fusion.effects(), pokemon.getEntity(), fusion.revert_aspects(), false);
                                return TypedActionResult.success(itemStack);
                            }
                        }

                        if (currentValue != null) {
                            if (fusion.fuse_if().isEmpty()) {
                                NbtCompound otherPokemonNbt = currentValue.saveToNBT(player.getWorld().getRegistryManager(), new NbtCompound());
                                pokemon.getPersistentData().put("fusion_pokemon", otherPokemonNbt);
                                itemStack.remove(DataManage.POKEMON_STORAGE);
                                itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(fusion.item_name()));

                                HandlerUtils.applyEffects(fusion.effects(), pokemon.getEntity(), fusion.fusion_aspects(), true);
                                return TypedActionResult.success(itemStack);
                            }
                            for (List<String> condition : fusion.fuse_if()) {
                                if (pokemon.getAspects().containsAll(condition)) {
                                    NbtCompound otherPokemonNbt = currentValue.saveToNBT(player.getWorld().getRegistryManager(), new NbtCompound());
                                    pokemon.getPersistentData().put("fusion_pokemon", otherPokemonNbt);
                                    itemStack.remove(DataManage.POKEMON_STORAGE);
                                    itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(fusion.item_name()));

                                    HandlerUtils.applyEffects(fusion.effects(), pokemon.getEntity(), fusion.fusion_aspects(), true);
                                    return TypedActionResult.success(itemStack);
                                }
                            }
                        }
                    } else if (fusion.fuser_mons().contains(pokemon.getSpecies().getName()) && !HandlerUtils.listCheck(fusion.fusion_blacklist_aspects(), pokemon.getAspects(), true)) {
                        if (currentValue == null) {
                            if (fusion.fuser_fuse_if().isEmpty()) {
                                itemStack.set(DataManage.POKEMON_STORAGE, pokemon);
                                itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(fusion.item_name() + ".charged"));
                                playerPartyStore.remove(pokemon);
                            }
                            for (List<String> condition : fusion.fuser_fuse_if()) {
                                if (pokemon.getAspects().containsAll(condition)) {
                                    itemStack.set(DataManage.POKEMON_STORAGE, pokemon);
                                    playerPartyStore.remove(pokemon);
                                    itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(fusion.item_name() + ".charged"));
                                    break;
                                }
                            }
                        }
                    }
                    player.setStackInHand(hand, itemStack);
                    return TypedActionResult.pass(itemStack);
                }
            }

            for (KeyItemData keyItem : Utils.keyItemsRegistry) {
                Item item = Registries.ITEM.get(Identifier.tryParse(keyItem.item_id()));
                if (HandlerUtils.itemValidator(item, keyItem.custom_model_data(), itemStack, keyItem.item_id())) {
                    EntityHitResult entityHit = HandlerUtils.getEntityLookingAt(player, 4.5);
                    if (entityHit == null) {
                        return TypedActionResult.pass(itemStack);
                    }
                    Entity context = entityHit.getEntity();

                    if (!(context instanceof PokemonEntity pk)) {
                        return TypedActionResult.pass(itemStack);
                    }

                    Pokemon pokemon = pk.getPokemon();
                    if (pokemon.getOwnerPlayer() != player || pokemon.getEntity() == null || pk.isBattling() || pk.getDataTracker().get(PokemonEntity.getEVOLUTION_STARTED())) {
                        return TypedActionResult.pass(itemStack);
                    }

                    if (keyItem.pokemons().contains(pokemon.getSpecies().getName()) && !HandlerUtils.listCheck(keyItem.blacklist_aspects(), pokemon.getAspects(), true)) {
                        if (keyItem.toggle_aspects().isEmpty()) {
                            for (List<String> condition : keyItem.revert_if()) {
                                if (pokemon.getAspects().containsAll(condition)) {
                                    if (Possible((ServerPlayerEntity) player)) {
                                        itemStack.decrementUnlessCreative(keyItem.consume(), player);
                                        HandlerUtils.applyEffects(keyItem.effects(), pokemon.getEntity(), keyItem.revert_aspects(), false);
                                        if (!keyItem.tradable_form()) {
                                            pokemon.setTradeable(true);
                                        }
                                    }
                                    return TypedActionResult.success(itemStack);
                                }
                            }

                            if (keyItem.apply_if().isEmpty()) {
                                if (Possible((ServerPlayerEntity) player)) {
                                    itemStack.decrementUnlessCreative(keyItem.consume(), player);
                                    HandlerUtils.applyEffects(keyItem.effects(), pokemon.getEntity(), keyItem.apply_aspects(), true);
                                    if (!keyItem.tradable_form()) {
                                        pokemon.setTradeable(false);
                                    }
                                }
                                return TypedActionResult.success(itemStack);
                            }
                            for (List<String> condition : keyItem.apply_if()) {
                                if (pokemon.getAspects().containsAll(condition)) {
                                    if (Possible((ServerPlayerEntity) player)) {
                                        itemStack.decrementUnlessCreative(keyItem.consume(), player);
                                        HandlerUtils.applyEffects(keyItem.effects(), pokemon.getEntity(), keyItem.apply_aspects(), true);
                                        if (!keyItem.tradable_form()) {
                                            pokemon.setTradeable(false);
                                        }
                                    }
                                    return TypedActionResult.success(itemStack);
                                }
                            }
                        } else {
                            int index = -1;
                            int size = keyItem.toggle_cycle().size();

                            for (int i = 0; i < size; i++) {
                                if (pokemon.getAspects().containsAll(keyItem.toggle_cycle().get(i))) {
                                    index = (i + 1) % size;
                                    break;
                                }
                            }

                            if (index != -1 && index < keyItem.toggle_aspects().size()) {
                                if (index == size) {
                                    index = 0;
                                }
                                if (Possible((ServerPlayerEntity) player)) {
                                    if (keyItem.apply_if().isEmpty()) {
                                        itemStack.decrementUnlessCreative(keyItem.consume(), player);
                                        HandlerUtils.applyEffects(keyItem.effects(), pokemon.getEntity(), keyItem.toggle_aspects().get(index), true);
                                        return TypedActionResult.success(itemStack);
                                    }
                                    for (List<String> condition : keyItem.apply_if()) {
                                        if (pokemon.getAspects().containsAll(condition)) {
                                            itemStack.decrementUnlessCreative(keyItem.consume(), player);
                                            HandlerUtils.applyEffects(keyItem.effects(), pokemon.getEntity(), keyItem.toggle_aspects().get(index), true);
                                            return TypedActionResult.success(itemStack);
                                        }
                                    }

                                    if (!keyItem.tradable_form()) {
                                        pokemon.setTradeable(false);
                                    }
                                }
                                return TypedActionResult.success(itemStack);
                            }
                        }
                        return TypedActionResult.pass(itemStack);
                    }
                }
            }
        }

        return TypedActionResult.pass(itemStack);
    }
}

