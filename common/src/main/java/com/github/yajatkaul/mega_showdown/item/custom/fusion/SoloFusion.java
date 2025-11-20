package com.github.yajatkaul.mega_showdown.item.custom.fusion;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.codec.Effect;
import com.github.yajatkaul.mega_showdown.components.MegaShowdownDataComponents;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipItem;
import com.github.yajatkaul.mega_showdown.utils.PlayerUtils;
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

public class SoloFusion extends ToolTipItem {
    private final List<String> fusions;
    private final List<String> pokemons;
    private final List<String> mainPokemons;
    private final String namespace;
    private final String effectId;
    private final List<String> applyAspect;
    private final List<String> revertAspect;

    public SoloFusion(Properties properties,
                      List<String> fusions,
                      List<String> pokemons,
                      List<String> mainPokemons,
                      String effectId,
                      List<String> applyAspect,
                      List<String> revertAspect
    ) {
        super(properties);
        this.fusions = fusions;
        this.pokemons = pokemons;
        this.mainPokemons = mainPokemons;

        ResourceLocation id = BuiltInRegistries.ITEM.getKey(this);
        this.namespace = id.getNamespace();
        this.effectId = effectId;

        this.applyAspect = applyAspect;
        this.revertAspect = revertAspect;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
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

            boolean isMain = mainPokemons.contains(pokemon.getSpecies().getName());
            boolean isFusion = pokemons.contains(pokemon.getSpecies().getName());

            if (isMain && checkEnabled(pokemon)) {
                if (pokemonStored != null) {
                    player.displayClientMessage(Component.translatable("message.mega_showdown.already_fused")
                            .withStyle(ChatFormatting.RED), true);
                    return InteractionResultHolder.pass(stack);
                }

                Effect.getEffect(effectId).revertEffects(pokemon, revertAspect, null);

                pokemon.setTradeable(true);

                Pokemon pokemonInside =
                        Pokemon.Companion.loadFromNBT(
                                level.registryAccess(),
                                pokemon.getPersistentData().getCompound("fusion_pokemon")
                        );
                playerPartyStore.add(pokemonInside);
                pokemon.getPersistentData().remove("fusion_forme");

                stack.set(MegaShowdownDataComponents.NBT_COMPONENT.get(), null);
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown." + namespace + ".inactive"));
            } else if (pokemonStored != null && isMain) {
                Effect.getEffect(effectId).revertEffects(pokemon, applyAspect, null);
                pokemon.setTradeable(false);

                CompoundTag otherPokemonNbt = pokemonStored.saveToNBT(level.registryAccess(), new CompoundTag());
                pokemon.getPersistentData().put("fusion_pokemon", otherPokemonNbt);

                stack.set(MegaShowdownDataComponents.NBT_COMPONENT.get(), null);
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown." + namespace + ".inactive"));
            } else if (pokemonStored == null && isFusion) {
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
        return pokemon.getAspects().stream().anyMatch(fusions::contains);
    }

    @Override
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
