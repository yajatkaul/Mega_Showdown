package com.github.yajatkaul.mega_showdown.item.custom.form_change;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipItem;
import com.github.yajatkaul.mega_showdown.utils.Effect;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FormChangeInteractItem extends ToolTipItem {
    private final String form_aspect_name;
    private final String form_aspect_apply;
    private final List<String> pokemons;
    private final Effect effects;
    private final int consume;
    private final boolean revertable;
    private final String form_aspect_revert;

    public FormChangeInteractItem(Properties properties,
                                  String form_aspect_name,
                                  String form_aspect_apply,
                                  List<String> pokemons,
                                  Effect effects,
                                  int consume,
                                  boolean revertable,
                                  String form_aspect_revert
    ) {
        super(properties);
        this.form_aspect_name = form_aspect_name;
        this.form_aspect_apply = form_aspect_apply;
        this.form_aspect_revert = form_aspect_revert;
        this.pokemons = pokemons;
        this.effects = effects;
        this.consume = consume;
        this.revertable = revertable;
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if (player.level().isClientSide || player.isCrouching()) {
            return InteractionResult.PASS;
        }

        if (livingEntity instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();

            if (!pokemons.contains(pokemon.getSpecies().getName()) || pokemonEntity.isBattling() || pokemonEntity.getTethering() != null || pokemon.getPersistentData().contains("form_changing")) {
                return InteractionResult.PASS;
            }

            if (pokemon.getAspects().contains(form_aspect_name) && !revertable) {
                return InteractionResult.PASS;
            } else if (pokemon.getAspects().contains(form_aspect_name)) {
                effects.revertEffects(pokemonEntity, List.of(form_aspect_revert), null);
                itemStack.consume(consume, livingEntity);

                return InteractionResult.SUCCESS;
            }

            effects.applyEffects(pokemonEntity, List.of(form_aspect_apply), null);
            itemStack.consume(consume, livingEntity);

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
