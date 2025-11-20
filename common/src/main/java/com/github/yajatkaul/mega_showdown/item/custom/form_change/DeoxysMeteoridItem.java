package com.github.yajatkaul.mega_showdown.item.custom.form_change;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.codec.Effect;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipBlockItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DeoxysMeteoridItem extends ToolTipBlockItem {
    private final List<String> form_apply_order = List.of("normal-forme", "attack-forme", "speed-forme", "defense-forme");
    private final List<List<String>> form_aspect_apply_order = List.of(
            List.of("meteorite_forme=normal"),
            List.of("meteorite_forme=attack"),
            List.of("meteorite_forme=speed"),
            List.of("meteorite_forme=defense")
    );

    public DeoxysMeteoridItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if (player.level().isClientSide || player.isCrouching()) {
            return InteractionResult.PASS;
        }

        if (livingEntity instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();

            if (!pokemon.getSpecies().getName().equals("Deoxys") || pokemonEntity.isBattling() || pokemonEntity.getTethering() != null || pokemon.getPersistentData().contains("form_changing")) {
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
                Effect.getEffect("mega_showdown:deoxys_effect").applyEffects(pokemon, form_aspect_apply_order.getFirst(), null);
            } else {
                Effect.getEffect("mega_showdown:deoxys_effect").applyEffects(pokemon, form_aspect_apply_order.get(currentIndex + 1), null);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
