package com.cobblemon.yajatkaul.mega_showdown.datapack.handler;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.dataAttachments.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.dataAttachments.PokeHandler;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.FusionData;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.KeyItemData;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemHandler {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 1000; // 1 sec

    public static boolean Possible(ServerPlayer player) {
        UUID playerId = player.getUUID();
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId) && currentTime < cooldowns.get(playerId)) {
            player.displayClientMessage(Component.translatable("message.mega_showdown.not_so_fast")
                    .withColor(0xFF0000), true);
            return false;
        }

        // Apply cooldown
        cooldowns.put(playerId, currentTime + COOLDOWN_TIME);
        return true;
    }

    public static boolean useItem(Player player, Level level, InteractionHand hand, ItemStack itemStack) {
        if (level.isClientSide || player.isCrouching()) {
            return false;
        }

        if (!itemStack.isEmpty()) {
            for (FusionData fusion : Utils.fusionRegistry) {
                Item item = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(fusion.item_id()));
                if (HandlerUtils.itemValidator(item, fusion.custom_model_data(), itemStack)) {
                    EntityHitResult entityHit = HandlerUtils.getEntityLookingAt(player, 4.5f);
                    if (entityHit == null) {
                        PokeHandler pokeHandler = itemStack.getOrDefault(DataManage.POKEMON_STORAGE, null);
                        Pokemon currentValue;
                        if (pokeHandler == null) {
                            currentValue = null;
                        } else {
                            currentValue = pokeHandler.getPokemon();
                        }
                        if (currentValue != null) {
                            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);
                            playerPartyStore.add(currentValue);
                            itemStack.set(DataComponents.CUSTOM_NAME, Component.translatable(fusion.item_name()));
                            itemStack.remove(DataManage.POKEMON_STORAGE);
                        }
                        return false;
                    }
                    Entity context = entityHit.getEntity();

                    if (!(context instanceof PokemonEntity pk)) {
                        return false;
                    }

                    Pokemon pokemon = pk.getPokemon();
                    if (pokemon.getOwnerPlayer() != player || pokemon.getEntity() == null || pk.isBattling() || pk.getEntityData().get(PokemonEntity.getEVOLUTION_STARTED())) {
                        return false;
                    }

                    PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);
                    PokeHandler pokeHandler = itemStack.getOrDefault(DataManage.POKEMON_STORAGE, null);
                    Pokemon currentValue;

                    if (pokeHandler == null) {
                        currentValue = null;
                    } else {
                        currentValue = pokeHandler.getPokemon();
                    }

                    if (fusion.fusion_mons().contains(pokemon.getSpecies().getName()) && !HandlerUtils.listCheck(fusion.fusion_blacklist_aspects(), pokemon.getAspects(), true)) {
                        for (List<String> condition : fusion.revert_if()) {
                            if (pokemon.getAspects().containsAll(condition)) {
                                Pokemon pokemonSave = Pokemon.Companion.loadFromNBT(player.level().registryAccess(), pokemon.getPersistentData().getCompound("fusion_pokemon"));
                                playerPartyStore.add(pokemonSave);
                                pokemon.getPersistentData().remove("fusion_forme");
                                HandlerUtils.applyEffects(fusion.effects(), pokemon.getEntity(), fusion.revert_aspects(), false);
                                return true;
                            }
                        }

                        if (currentValue != null) {
                            if (fusion.fuse_if().isEmpty()) {
                                CompoundTag otherPokemonNbt = currentValue.saveToNBT(player.level().registryAccess(), new CompoundTag());
                                pokemon.getPersistentData().put("fusion_pokemon", otherPokemonNbt);
                                itemStack.remove(DataManage.POKEMON_STORAGE);
                                itemStack.set(DataComponents.CUSTOM_NAME, Component.translatable(fusion.item_name()));

                                HandlerUtils.applyEffects(fusion.effects(), pokemon.getEntity(), fusion.fusion_aspects(), true);
                                return true;
                            }
                            for (List<String> condition : fusion.fuse_if()) {
                                if (pokemon.getAspects().containsAll(condition)) {
                                    CompoundTag otherPokemonNbt = currentValue.saveToNBT(player.level().registryAccess(), new CompoundTag());
                                    pokemon.getPersistentData().put("fusion_pokemon", otherPokemonNbt);
                                    itemStack.remove(DataManage.POKEMON_STORAGE);
                                    itemStack.set(DataComponents.CUSTOM_NAME, Component.translatable(fusion.item_name()));

                                    HandlerUtils.applyEffects(fusion.effects(), pokemon.getEntity(), fusion.fusion_aspects(), true);
                                    return true;
                                }
                            }
                        }
                    } else if (fusion.fuser_mons().contains(pokemon.getSpecies().getName()) && !HandlerUtils.listCheck(fusion.fuser_blacklist_aspects(), pokemon.getAspects(), true) && pokemon.getEntity().getTethering() == null) {
                        if (currentValue == null) {
                            if (fusion.fuser_fuse_if().isEmpty()) {
                                itemStack.set(DataManage.POKEMON_STORAGE, new PokeHandler(pokemon));
                                itemStack.set(DataComponents.CUSTOM_NAME, Component.translatable(fusion.item_name() + ".charged"));
                                playerPartyStore.remove(pokemon);
                            }
                            for (List<String> condition : fusion.fuser_fuse_if()) {
                                if (pokemon.getAspects().containsAll(condition)) {
                                    itemStack.set(DataManage.POKEMON_STORAGE, new PokeHandler(pokemon));
                                    playerPartyStore.remove(pokemon);
                                    itemStack.set(DataComponents.CUSTOM_NAME, Component.translatable(fusion.item_name() + ".charged"));
                                    break;
                                }
                            }
                        }
                    }
                    player.setItemInHand(hand, itemStack);
                    return false;
                }
            }

            for (KeyItemData keyItem : Utils.keyItemsRegistry) {
                Item item = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(keyItem.item_id()));
                if (HandlerUtils.itemValidator(item, keyItem.custom_model_data(), itemStack)) {
                    EntityHitResult entityHit = HandlerUtils.getEntityLookingAt(player, 4.5f);
                    if (entityHit == null) {
                        return false;
                    }
                    Entity context = entityHit.getEntity();

                    if (!(context instanceof PokemonEntity pk)) {
                        return false;
                    }

                    Pokemon pokemon = pk.getPokemon();
                    if (pokemon.getOwnerPlayer() != player || pokemon.getEntity() == null || pk.isBattling() || pk.getEntityData().get(PokemonEntity.getEVOLUTION_STARTED())) {
                        return false;
                    }

                    if (keyItem.pokemons().contains(pokemon.getSpecies().getName()) && !HandlerUtils.listCheck(keyItem.blacklist_aspects(), pokemon.getAspects(), true)) {
                        if (keyItem.toggle_aspects().isEmpty()) {
                            for (List<String> condition : keyItem.revert_if()) {
                                if (pokemon.getAspects().containsAll(condition)) {
                                    if (Possible((ServerPlayer) player)) {
                                        itemStack.consume(keyItem.consume(), player);
                                        HandlerUtils.applyEffects(keyItem.effects(), pokemon.getEntity(), keyItem.revert_aspects(), false);
                                        if (!keyItem.tradable_form()) {
                                            pokemon.setTradeable(true);
                                        }
                                    }
                                    return true;
                                }
                            }

                            if (keyItem.apply_if().isEmpty()) {
                                if (Possible((ServerPlayer) player)) {
                                    itemStack.consume(keyItem.consume(), player);
                                    HandlerUtils.applyEffects(keyItem.effects(), pokemon.getEntity(), keyItem.apply_aspects(), true);
                                    if (!keyItem.tradable_form()) {
                                        pokemon.setTradeable(false);
                                    }
                                }
                                return true;
                            }
                            for (List<String> condition : keyItem.apply_if()) {
                                if (pokemon.getAspects().containsAll(condition)) {
                                    if (Possible((ServerPlayer) player)) {
                                        itemStack.consume(keyItem.consume(), player);
                                        HandlerUtils.applyEffects(keyItem.effects(), pokemon.getEntity(), keyItem.apply_aspects(), true);
                                        if (!keyItem.tradable_form()) {
                                            pokemon.setTradeable(false);
                                        }
                                    }
                                    return true;
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
                                if (Possible((ServerPlayer) player)) {
                                    if (keyItem.apply_if().isEmpty()) {
                                        itemStack.consume(keyItem.consume(), player);
                                        HandlerUtils.applyEffects(keyItem.effects(), pokemon.getEntity(), keyItem.toggle_aspects().get(index), true);
                                        return true;
                                    }
                                    for (List<String> condition : keyItem.apply_if()) {
                                        if (pokemon.getAspects().containsAll(condition)) {
                                            itemStack.consume(keyItem.consume(), player);
                                            HandlerUtils.applyEffects(keyItem.effects(), pokemon.getEntity(), keyItem.toggle_aspects().get(index), true);
                                            return true;
                                        }
                                    }

                                    if (!keyItem.tradable_form()) {
                                        pokemon.setTradeable(false);
                                    }
                                }
                                return true;
                            }
                        }
                        return false;
                    }
                }
            }
        }

        return false;
    }
}
