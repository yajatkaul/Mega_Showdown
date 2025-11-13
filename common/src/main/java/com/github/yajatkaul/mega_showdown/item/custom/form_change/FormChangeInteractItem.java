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
    private final List<String> form_apply_order;
    private final List<List<String>> form_aspect_apply_order;
    private final List<String> pokemons;
    private final List<Effect> effects;

    public FormChangeInteractItem(Properties properties,
                                  List<String> fusion_apply_order,
                                  List<List<String>> fusion_aspect_apply_order,
                                  List<String> pokemons,
                                  List<Effect> effects
    ) {
        super(properties);
        this.form_apply_order = fusion_apply_order;
        this.form_aspect_apply_order = fusion_aspect_apply_order;
        this.pokemons = pokemons;
        this.effects = effects;
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
                effects.getFirst().applyEffects(pokemonEntity, form_aspect_apply_order.getFirst(), null);
            } else {
                effects.get(currentIndex + 1).applyEffects(pokemonEntity, form_aspect_apply_order.get(currentIndex + 1), null);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
