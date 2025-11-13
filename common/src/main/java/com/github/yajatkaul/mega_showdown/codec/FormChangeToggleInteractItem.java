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

public record FormChangeToggleInteractItem(
        List<String> form_apply_order,
        List<AspectSetCodec> aspect_conditions,
        List<List<String>> form_aspect_apply_order,
        List<String> pokemons,
        List<Effect> effects,
        int consume
) {
    public static final Codec<FormChangeToggleInteractItem> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.listOf().fieldOf("form_apply_order").forGetter(FormChangeToggleInteractItem::form_apply_order),
            AspectSetCodec.CODEC.listOf().fieldOf("aspect_conditions").forGetter(FormChangeToggleInteractItem::aspect_conditions),
            Codec.STRING.listOf().listOf().fieldOf("form_aspect_apply_order").forGetter(FormChangeToggleInteractItem::form_aspect_apply_order),
            Codec.STRING.listOf().fieldOf("pokemons").forGetter(FormChangeToggleInteractItem::pokemons),
            Effect.EFFECT_MAP_CODEC.listOf().fieldOf("effects").forGetter(FormChangeToggleInteractItem::effects),
            Codec.INT.optionalFieldOf("consume", 0).forGetter(FormChangeToggleInteractItem::consume)
    ).apply(instance, FormChangeToggleInteractItem::new));

    public InteractionResult interactLivingEntity(Player player, LivingEntity livingEntity, ItemStack stack) {
        if (player.level().isClientSide || player.isCrouching()) {
            return InteractionResult.PASS;
        }

        if (livingEntity instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();

            if (!pokemons.contains(pokemon.getSpecies().getName()) || pokemonEntity.isBattling() || pokemonEntity.getTethering() != null || pokemon.getPersistentData().contains("form_changing")) {
                return InteractionResult.PASS;
            }

            int currentIndex = -1;
            for (int i = 0; i < form_apply_order.size(); i++) {
                String form = form_apply_order.get(i);
                boolean hasAspect = pokemon.getAspects().stream().anyMatch(aspect -> aspect.equalsIgnoreCase(form));
                if (hasAspect) {
                    currentIndex = i;
                    break;
                }
            }

            if (currentIndex == -1) {
                return InteractionResult.PASS;
            }

            if (currentIndex + 1 > form_apply_order.size() - 1) {
                if (aspect_conditions.getFirst().validate_apply(pokemon)) {
                    effects.getFirst().applyEffects(pokemonEntity, form_aspect_apply_order.getFirst(), null);
                }
            } else {
                if (aspect_conditions.get(currentIndex + 1).validate_apply(pokemon)) {
                    effects.get(currentIndex + 1).applyEffects(pokemonEntity, form_aspect_apply_order.get(currentIndex + 1), null);
                }
            }
            stack.consume(consume, livingEntity);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
