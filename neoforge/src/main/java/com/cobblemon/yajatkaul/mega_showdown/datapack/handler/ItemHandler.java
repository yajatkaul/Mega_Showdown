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

import java.util.*;

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
                        return false;
                    } else {
                        PokeHandler currentValue = itemStack.getOrDefault(DataManage.POKEMON_STORAGE, null);
                        if (currentValue != null) {
                            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);
                            playerPartyStore.add(currentValue.getPokemon());
                        }
                    }
                    Entity context = entityHit.getEntity();

                    if (!(context instanceof PokemonEntity pk)) {
                        return false;
                    }

                    Pokemon pokemon = pk.getPokemon();
                    if (pokemon.getOwnerPlayer() != player || pokemon.getEntity() == null || pk.isBattling()) {
                        return false;
                    }

                    PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);
                    PokeHandler pokeHandler = itemStack.getOrDefault(DataManage.POKEMON_STORAGE, null);

                    if (HandlerUtils.checkEnabled(fusion, pokemon) && fusion.fusion_mons().contains(pokemon.getSpecies().getName())) {
                        Pokemon pokemonSave = Pokemon.Companion.loadFromNBT(player.level().registryAccess(), pokemon.getPersistentData().getCompound("fusion_pokemon"));
                        playerPartyStore.add(pokemonSave);
                        pokemon.getPersistentData().remove("fusion_forme");
                    } else if (fusion.fuser_mons().contains(pokemon.getSpecies().getName())) {
                        itemStack.set(DataManage.POKEMON_STORAGE, new PokeHandler(pokemon));
                        playerPartyStore.remove(pokemon);
                    } else if (fusion.fusion_mons().contains(pokemon.getSpecies().getName()) && !HandlerUtils.checkEnabled(fusion, pokemon)) {
                        if(pokeHandler != null){
                            Pokemon currentValue = pokeHandler.getPokemon();
                            CompoundTag otherPokemonNbt = currentValue.saveToNBT(player.level().registryAccess(), new CompoundTag());
                            pokemon.getPersistentData().put("fusion_pokemon", otherPokemonNbt);
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
                    if (pokemon.getOwnerPlayer() != player || pokemon.getEntity() == null || pk.isBattling()) {
                        return false;
                    }

                    if (keyItem.pokemons().contains(pokemon.getSpecies().getName())) {
                        if (keyItem.toggle_aspects().isEmpty()) {
                            if (pokemon.getAspects().containsAll(keyItem.apply_if())) {
                                if (Possible((ServerPlayer) player)) {
                                    HandlerUtils.applyAspects(keyItem.apply_aspects(), pokemon);
                                    if (!keyItem.tradable_form()) {
                                        pokemon.setTradeable(false);
                                    }
                                }
                                return true;
                            } else if (pokemon.getAspects().containsAll(keyItem.revert_if())) {
                                if (Possible((ServerPlayer) player)) {
                                    HandlerUtils.applyAspects(keyItem.revert_aspects(), pokemon);
                                    if (!keyItem.tradable_form()) {
                                        pokemon.setTradeable(true);
                                    }
                                }
                                return true;
                            }
                        } else {
                            int index = 0;
                            int size = keyItem.toggle_cycle().size();
                            for (List<String> toggleCycle : keyItem.toggle_cycle()) {
                                if (pokemon.getAspects().containsAll(toggleCycle)) {
                                    index += 1;
                                    break;
                                }
                                index += 1;
                            }

                            if (index != -1 && index < keyItem.toggle_aspects().size()) {
                                if (index == size) {
                                    index = 0;
                                }
                                if (Possible((ServerPlayer) player)) {
                                    HandlerUtils.applyAspects(keyItem.toggle_aspects().get(index), pokemon);
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
