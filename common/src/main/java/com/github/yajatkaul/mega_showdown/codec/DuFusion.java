package com.github.yajatkaul.mega_showdown.codec;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.components.MegaShowdownDataComponents;
import com.github.yajatkaul.mega_showdown.gimmick.codec.AspectSetCodec;
import com.github.yajatkaul.mega_showdown.utils.ParticlesList;
import com.github.yajatkaul.mega_showdown.utils.PlayerUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;
import java.util.Optional;

public record DuFusion(
        List<String> fusions1,
        List<String> fusions2,
        List<String> pokemons1,
        AspectSetCodec pokemon_1_aspect_conditions,
        List<String> pokemons2,
        AspectSetCodec pokemon_2_aspect_conditions,
        List<String> mainPokemons,
        AspectSetCodec pokemon_main_aspect_conditions,
        Optional<ResourceLocation> effect1,
        Optional<ResourceLocation> effect2
) {
    public static final Codec<DuFusion> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.listOf().fieldOf("fusions1").forGetter(DuFusion::fusions1),
            Codec.STRING.listOf().fieldOf("fusions2").forGetter(DuFusion::fusions2),
            Codec.STRING.listOf().fieldOf("pokemons1").forGetter(DuFusion::pokemons1),
            AspectSetCodec.CODEC.fieldOf("pokemon_1_aspect_conditions").forGetter(DuFusion::pokemon_1_aspect_conditions),
            Codec.STRING.listOf().fieldOf("pokemons2").forGetter(DuFusion::pokemons2),
            AspectSetCodec.CODEC.fieldOf("pokemon_2_aspect_conditions").forGetter(DuFusion::pokemon_1_aspect_conditions),
            Codec.STRING.listOf().fieldOf("main_pokemons").forGetter(DuFusion::mainPokemons),
            AspectSetCodec.CODEC.fieldOf("pokemon_main_aspect_conditions").forGetter(DuFusion::pokemon_1_aspect_conditions),
            ResourceLocation.CODEC.optionalFieldOf("effect_for_fusion1").forGetter(DuFusion::effect1),
            ResourceLocation.CODEC.optionalFieldOf("effect_for_fusion2").forGetter(DuFusion::effect2)
    ).apply(instance, DuFusion::new));

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        String namespace = BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace();

        if (level.isClientSide) {
            return InteractionResultHolder.pass(stack);
        }

        EntityHitResult hitResult = PlayerUtils.getEntityLookingAt(player, 4.5f);
        Entity entity = null;
        if (hitResult != null) {
            entity = hitResult.getEntity();
        }

        CompoundTag compoundTag = stack.get(MegaShowdownDataComponents.NBT_COMPONENT.get());
        Pokemon pokemonStored = null;
        if (compoundTag != null) {
            pokemonStored = new Pokemon().loadFromNBT(level.registryAccess(), compoundTag);
        }

        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);

        if (entity instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();

            if (pokemonEntity.isBattling() || pokemon.getPersistentData().contains("form_changing") || pokemonEntity.getTethering() != null) {
                return InteractionResultHolder.pass(stack);
            }

            if (mainPokemons.contains(pokemon.getSpecies().getName()) && checkEnabled(pokemon)) {
                if (pokemonStored != null) {
                    player.displayClientMessage(Component.translatable("message.mega_showdown.already_fused")
                            .withStyle(ChatFormatting.RED), true);
                    return InteractionResultHolder.pass(stack);
                }

                Pokemon pokemonInside =
                        Pokemon.Companion.loadFromNBT(
                                level.registryAccess(),
                                pokemon.getPersistentData().getCompound("fusion_pokemon")
                        );
                playerPartyStore.add(pokemonInside);

                if (pokemons1.contains(pokemonInside.getSpecies().getName()) &&
                        pokemon_1_aspect_conditions.validate_revert(pokemon)) {
                    ParticlesList.getEffect(effect1.get()).revertEffects(pokemon, pokemon_1_aspect_conditions.revert_aspects(), null);
                } else if (pokemon_2_aspect_conditions.validate_revert(pokemon)) {
                    ParticlesList.getEffect(effect2.get()).revertEffects(pokemon, pokemon_2_aspect_conditions.revert_aspects(), null);
                } else {
                    return InteractionResultHolder.pass(stack);
                }

                pokemon.setTradeable(true);

                stack.set(MegaShowdownDataComponents.NBT_COMPONENT.get(), null);
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown." + namespace + ".inactive"));
            } else if (pokemonStored != null && mainPokemons.contains(pokemon.getSpecies().getName())) {
                pokemon.setTradeable(false);

                CompoundTag otherPokemonNbt = pokemonStored.saveToNBT(level.registryAccess(), new CompoundTag());
                pokemon.getPersistentData().put("fusion_pokemon", otherPokemonNbt);

                stack.set(MegaShowdownDataComponents.NBT_COMPONENT.get(), null);
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown." + namespace + ".inactive"));
                pokemon.setTradeable(false);

                if (pokemons1.contains(pokemonStored.getSpecies().getName()) &&
                        pokemon_1_aspect_conditions.validate_apply(pokemon)) {
                    ParticlesList.getEffect(effect1.get()).applyEffects(pokemon, pokemon_1_aspect_conditions.apply_aspects(), null);
                } else if (pokemon_2_aspect_conditions.validate_apply(pokemon)) {
                    ParticlesList.getEffect(effect1.get()).applyEffects(pokemon, pokemon_2_aspect_conditions.apply_aspects(), null);
                } else {
                    return InteractionResultHolder.pass(stack);
                }

            } else if (pokemonStored == null &&
                    pokemons1.contains(pokemon.getSpecies().getName()) ||
                    pokemons2.contains(pokemon.getSpecies().getName())
            ) {
                CompoundTag pokemonNBT = pokemon.saveToNBT(level.registryAccess(), new CompoundTag());
                stack.set(MegaShowdownDataComponents.NBT_COMPONENT.get(), pokemonNBT);
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown." + namespace + ".charged"));

                playerPartyStore.remove(pokemon);
            }
        } else if (pokemonStored != null) {
            playerPartyStore.add(pokemonStored);
            stack.set(MegaShowdownDataComponents.NBT_COMPONENT.get(), null);
            stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown." + namespace + ".inactive"));
        }

        return InteractionResultHolder.pass(stack);
    }

    private boolean checkEnabled(Pokemon pokemon) {
        return pokemon.getAspects().stream().anyMatch(fusions1::contains) || pokemon.getAspects().stream().anyMatch(fusions2::contains);
    }

    public void onDestroyed(ItemEntity itemEntity) {
        CompoundTag compoundTag = itemEntity.getItem().get(MegaShowdownDataComponents.NBT_COMPONENT.get());
        Pokemon pokemonStored = null;
        if (compoundTag != null) {
            pokemonStored = new Pokemon().loadFromNBT(itemEntity.level().registryAccess(), compoundTag);
        }
        if (pokemonStored != null) {
            if (itemEntity.getOwner() instanceof ServerPlayer player) {
                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
                playerPartyStore.add(pokemonStored);
            }
        }
    }
}