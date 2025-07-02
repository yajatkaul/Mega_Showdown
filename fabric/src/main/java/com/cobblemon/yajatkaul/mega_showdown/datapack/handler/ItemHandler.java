package com.cobblemon.yajatkaul.mega_showdown.datapack.handler;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.dataAttachments.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.FusionData;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.KeyItemData;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
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

import java.util.*;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class ItemHandler {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 1000; // 1 sec

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
            CustomModelDataComponent nbt = itemStack.get(DataComponentTypes.CUSTOM_MODEL_DATA);

            for (FusionData fusion : Utils.fusionRegistry) {
                Item item = Registries.ITEM.get(Identifier.tryParse(fusion.item_id()));
                if (itemStack.isOf(item) && ((nbt != null && fusion.custom_model_data() == nbt.value()) || fusion.custom_model_data() == 0)) {
                    EntityHitResult entityHit = HandlerUtils.getEntityLookingAt(player, 4.5);
                    if (entityHit == null) {
                        return TypedActionResult.pass(itemStack);
                    }
                    Entity context = entityHit.getEntity();

                    if (!(context instanceof PokemonEntity pk)) {
                        return TypedActionResult.pass(itemStack);
                    }

                    Pokemon pokemon = pk.getPokemon();
                    if (pokemon.getOwnerPlayer() != player || pokemon.getEntity() == null || pk.isBattling()) {
                        return TypedActionResult.pass(itemStack);
                    }

                    PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);
                    Pokemon currentValue = itemStack.getOrDefault(DataManage.POKEMON_STORAGE, null);

                    if (HandlerUtils.checkEnabled(fusion, pokemon) && fusion.fusion_mon().contains(pokemon.getSpecies().getName())) {
                        if (!fusion.required_aspects_fusion_mon().isEmpty()) {
                            List<String> aspectList = new ArrayList<>();
                            for (String aspects : fusion.required_aspects_fusion_mon()) {
                                String[] aspectDiv = aspects.split("=");
                                if (aspectDiv[1].equals("true") || aspectDiv[1].equals("false")) {
                                    aspectList.add(aspectDiv[0]);
                                } else {
                                    aspectList.add(aspectDiv[1]);
                                }
                            }

                            boolean allMatch = true;
                            for (String requiredAspect : aspectList) {
                                boolean matched = false;
                                for (String pokemonAspect : pokemon.getAspects()) {
                                    if (pokemonAspect.startsWith(requiredAspect)) {
                                        matched = true;
                                        break;
                                    }
                                }
                                if (!matched) {
                                    allMatch = false;
                                    break;
                                }
                            }

                            if (!allMatch) {
                                return TypedActionResult.pass(itemStack);
                            }
                        }

                        if (itemStack.get(DataManage.POKEMON_STORAGE) != null) {
                            player.sendMessage(
                                    Text.translatable("message.mega_showdown.already_fused").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                                    true
                            );
                            return TypedActionResult.success(itemStack);
                        }

                        HandlerUtils.particleEffect(pk, fusion.effects(), false);

                        for (String aspects : fusion.default_aspects()) {
                            String[] aspectsDiv = aspects.split("=");
                            if (aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")) {
                                new FlagSpeciesFeature(aspectsDiv[0], Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                            } else {
                                new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                            }
                        }
                        if (!fusion.tradable_form()) {
                            setTradable(pokemon, true);
                        }

                        Pokemon pokemon1 = Pokemon.Companion.loadFromNBT(player.getWorld().getRegistryManager(), pokemon.getPersistentData().getCompound("fusion_pokemon"));
                        playerPartyStore.add(pokemon1);
                        pokemon.getPersistentData().remove("fusion_forme");

                        itemStack.set(DataManage.POKEMON_STORAGE, null);
                        itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(fusion.item_name() + ".inactive"));
                        return TypedActionResult.success(itemStack);
                    } else if (currentValue != null && fusion.fusion_mon().contains(pokemon.getSpecies().getName())) {
                        if (!fusion.required_aspects_fusion_mon().isEmpty()) {
                            List<String> aspectList = new ArrayList<>();
                            for (String aspects : fusion.required_aspects_fusion_mon()) {
                                String[] aspectDiv = aspects.split("=");
                                if (aspectDiv[1].equals("true") || aspectDiv[1].equals("false")) {
                                    aspectList.add(aspectDiv[0]);
                                } else {
                                    aspectList.add(aspectDiv[1]);
                                }
                            }

                            boolean allMatch = true;
                            for (String requiredAspect : aspectList) {
                                boolean matched = false;
                                for (String pokemonAspect : pokemon.getAspects()) {
                                    if (pokemonAspect.startsWith(requiredAspect)) {
                                        matched = true;
                                        break;
                                    }
                                }
                                if (!matched) {
                                    allMatch = false;
                                    break;
                                }
                            }

                            if (!allMatch) {
                                return TypedActionResult.pass(itemStack);
                            }
                        }

                        for (String aspects : fusion.fusion_aspects()) {
                            String[] aspectsDiv = aspects.split("=");
                            if (aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")) {
                                new FlagSpeciesFeature(aspectsDiv[0], Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                            } else {
                                new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                            }
                        }
                        HandlerUtils.particleEffect(pk, fusion.effects(), true);

                        if (!fusion.tradable_form()) {
                            setTradable(pokemon, false);
                        }

                        NbtCompound otherPokemonNbt = currentValue.saveToNBT(player.getWorld().getRegistryManager(), new NbtCompound());
                        pokemon.getPersistentData().put("fusion_pokemon", otherPokemonNbt);

                        itemStack.set(DataManage.POKEMON_STORAGE, null);
                        itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(fusion.item_name() + ".inactive"));
                        return TypedActionResult.success(itemStack);
                    } else if (currentValue == null && fusion.fuse_with_mon().contains(pokemon.getSpecies().getName())) {
                        if (!fusion.required_aspects_fuse_with_mon().isEmpty()) {
                            List<String> aspectList = new ArrayList<>();
                            for (String aspects : fusion.required_aspects_fuse_with_mon()) {
                                String[] aspectDiv = aspects.split("=");
                                if (aspectDiv[1].equals("true") || aspectDiv[1].equals("false")) {
                                    aspectList.add(aspectDiv[0]);
                                } else {
                                    aspectList.add(aspectDiv[1]);
                                }
                            }

                            boolean allMatch = true;
                            for (String requiredAspect : aspectList) {
                                boolean matched = false;
                                for (String pokemonAspect : pokemon.getAspects()) {
                                    if (pokemonAspect.startsWith(requiredAspect)) {
                                        matched = true;
                                        break;
                                    }
                                }
                                if (!matched) {
                                    allMatch = false;
                                    break;
                                }
                            }

                            if (!allMatch) {
                                return TypedActionResult.pass(itemStack);
                            }
                        }

                        itemStack.set(DataManage.POKEMON_STORAGE, pk.getPokemon());
                        playerPartyStore.remove(pk.getPokemon());
                        itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(fusion.item_name() + ".charged"));
                        return TypedActionResult.success(itemStack);
                    }

                    player.setStackInHand(hand, itemStack);

                }
            }

            for (KeyItemData keyItems : Utils.keyItemsRegistry) {
                Item item = Registries.ITEM.get(Identifier.tryParse(keyItems.item_id()));
                if (itemStack.isOf(item) && ((nbt != null && keyItems.custom_model_data() == nbt.value()) || keyItems.custom_model_data() == 0)) {
                    EntityHitResult entityHit = HandlerUtils.getEntityLookingAt(player, 4.5);
                    if (entityHit == null) {
                        return TypedActionResult.pass(itemStack);
                    }
                    Entity context = entityHit.getEntity();

                    if (!(context instanceof PokemonEntity pk)) {
                        return TypedActionResult.pass(itemStack);
                    }

                    Pokemon pokemon = pk.getPokemon();
                    if (pokemon.getOwnerPlayer() != player || pokemon.getEntity() == null || pk.isBattling()) {
                        return TypedActionResult.pass(itemStack);
                    }

                    if (keyItems.pokemons().contains(pokemon.getSpecies().getName())) {
                        if (!keyItems.required_aspects().isEmpty()) {
                            List<String> aspectList = new ArrayList<>();
                            for (String aspects : keyItems.required_aspects()) {
                                String[] aspectDiv = aspects.split("=");
                                if (aspectDiv[1].equals("true") || aspectDiv[1].equals("false")) {
                                    aspectList.add(aspectDiv[0]);
                                } else {
                                    aspectList.add(aspectDiv[1]);
                                }
                            }
                            boolean allMatch = true;
                            for (String requiredAspect : aspectList) {
                                boolean matched = false;
                                for (String pokemonAspect : pokemon.getAspects()) {
                                    if (pokemonAspect.startsWith(requiredAspect)) {
                                        matched = true;
                                        break;
                                    }
                                }
                                if (!matched) {
                                    allMatch = false;
                                    break;
                                }
                            }

                            if (!allMatch) {
                                return TypedActionResult.pass(itemStack);
                            }
                        }

                        if (!Possible((ServerPlayerEntity) player)) {
                            return TypedActionResult.pass(itemStack);
                        }

                        if (keyItems.toggle_aspects().isEmpty()) {
                            List<String> aspectList = new ArrayList<>();
                            for (String aspects : keyItems.aspects()) {
                                String[] aspectDiv = aspects.split("=");
                                if (aspectDiv[1].equals("true") || aspectDiv[1].equals("false")) {
                                    aspectList.add(aspectDiv[0]);
                                } else {
                                    aspectList.add(aspectDiv[1]);
                                }
                            }

                            boolean allMatch = true;
                            for (String requiredAspect : aspectList) {
                                boolean matched = false;
                                for (String pokemonAspect : pokemon.getAspects()) {
                                    if (pokemonAspect.startsWith(requiredAspect)) {
                                        matched = true;
                                        break;
                                    }
                                }
                                if (!matched) {
                                    allMatch = false;
                                    break;
                                }
                            }

                            if (allMatch) {
                                for (String aspects : keyItems.default_aspects()) {
                                    String[] aspectsDiv = aspects.split("=");
                                    if (aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")) {
                                        new FlagSpeciesFeature(aspectsDiv[0], Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                                    } else {
                                        new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                                    }
                                    if (!keyItems.tradable_form()) {
                                        setTradable(pokemon, true);
                                    }
                                }
                                if (keyItems.consume()) {
                                    itemStack.decrement(1);
                                }
                                HandlerUtils.particleEffect(pk, keyItems.effects(), false);
                            } else {
                                for (String aspects : keyItems.aspects()) {
                                    String[] aspectsDiv = aspects.split("=");
                                    if (aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")) {
                                        new FlagSpeciesFeature(aspectsDiv[0], Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                                    } else {
                                        new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                                    }
                                    if (!keyItems.tradable_form()) {
                                        setTradable(pokemon, false);
                                    }
                                }
                                HandlerUtils.particleEffect(pk, keyItems.effects(), true);
                                if (keyItems.consume()) {
                                    itemStack.decrement(1);
                                }
                            }
                        } else {
                            int currentIndex = -1;

                            List<String> currentAspects = pokemon.getAspects().stream()
                                    .map(String::toLowerCase)
                                    .toList();

                            for (int i = 0; i < keyItems.toggle_aspects().size(); i++) {
                                List<String> sublist = keyItems.toggle_aspects().get(i);
                                for (String aspect : sublist) {
                                    String value = aspect.split("=")[1].toLowerCase();

                                    for (String current : currentAspects) {
                                        if (current.contains(value) || value.contains(current)) {
                                            currentIndex = i;
                                            break;
                                        }
                                    }
                                    if (currentIndex != -1) break;
                                }
                                if (currentIndex != -1) break;
                            }

                            int nextIndex = (currentIndex + 1) % keyItems.toggle_aspects().size();
                            List<String> nextAspects = keyItems.toggle_aspects().get(nextIndex);

                            for (String aspect : nextAspects) {
                                String[] parts = aspect.split("=");
                                String key = parts[0];
                                String value = parts[1];

                                if (value.equals("true") || value.equals("false")) {
                                    new FlagSpeciesFeature(key, Boolean.parseBoolean(value)).apply(pokemon);
                                } else {
                                    new StringSpeciesFeature(key, value).apply(pokemon);
                                }
                            }

                            HandlerUtils.particleEffect(pk, keyItems.effects(), true);
                            setTradable(pokemon, !keyItems.tradable_form());
                            if (keyItems.consume()) {
                                itemStack.decrement(1);
                            }
                        }
                    }
                }
            }
        }

        return TypedActionResult.pass(itemStack);
    }
}
