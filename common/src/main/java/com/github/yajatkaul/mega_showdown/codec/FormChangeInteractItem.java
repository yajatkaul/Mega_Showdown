package com.github.yajatkaul.mega_showdown.codec;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.gimmick.codec.AspectSetCodec;
import com.github.yajatkaul.mega_showdown.utils.Effect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record FormChangeInteractItem(
        AspectSetCodec aspect_conditions,
        List<String> pokemons,
        Effect effects,
        int consume
) {
    public static final Codec<FormChangeInteractItem> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            AspectSetCodec.CODEC.fieldOf("aspect_conditions").forGetter(FormChangeInteractItem::aspect_conditions),
            Codec.STRING.listOf().fieldOf("pokemons").forGetter(FormChangeInteractItem::pokemons),
            Effect.EFFECT_MAP_CODEC.optionalFieldOf("effects", Effect.empty()).forGetter(FormChangeInteractItem::effects),
            Codec.INT.optionalFieldOf("consume", 0).forGetter(FormChangeInteractItem::consume)
    ).apply(instance, FormChangeInteractItem::new));

    public InteractionResult interactLivingEntity(Player player, LivingEntity livingEntity, ItemStack stack) {
        if (player.level().isClientSide || player.isCrouching()) {
            return InteractionResult.PASS;
        }

        if (livingEntity instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();

            if (!pokemons.contains(pokemon.getSpecies().getName()) || pokemonEntity.isBattling() || pokemonEntity.getTethering() != null || pokemon.getPersistentData().contains("form_changing")) {
                return InteractionResult.PASS;
            }

            if (aspect_conditions.validate_apply(pokemon)) {
                effects.applyEffects(pokemon, aspect_conditions.apply_aspects(), null);
                stack.consume(consume, livingEntity);
                return InteractionResult.SUCCESS;
            } else if (aspect_conditions.validate_revert(pokemon)) {
                effects.applyEffects(pokemon, aspect_conditions.revert_aspects(), null);
                stack.consume(consume, livingEntity);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}
