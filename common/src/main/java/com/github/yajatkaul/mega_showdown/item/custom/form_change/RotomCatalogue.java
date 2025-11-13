package com.github.yajatkaul.mega_showdown.item.custom.form_change;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipItem;
import com.github.yajatkaul.mega_showdown.utils.ParticlesList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RotomCatalogue extends ToolTipItem {
    private final List<String> form_apply_order = List.of(
            "heat-appliance",
            "fan-appliance",
            "mow-appliance",
            "frost-appliance",
            "wash-appliance",
            "none-appliance"
    );
    private final List<String> form_aspect_apply_order = List.of(
            "appliance=heat",
            "appliance=fan",
            "appliance=mow",
            "appliance=frost",
            "appliance=wash",
            "appliance=none"
    );

    public RotomCatalogue(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if (player.level().isClientSide || player.isCrouching()) {
            return InteractionResult.PASS;
        }

        if (livingEntity instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();

            if (!pokemon.getSpecies().getName().equals("Rotom") || pokemonEntity.isBattling() || pokemonEntity.getTethering() != null || pokemon.getPersistentData().contains("form_changing")) {
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
                currentIndex = 5;
            }

            if (currentIndex + 1 > form_apply_order.size() - 1) {
                ParticlesList.defaultParticles.applyEffects(pokemonEntity, List.of(form_aspect_apply_order.getFirst()), null);
            } else {
                ParticlesList.defaultParticles.applyEffects(pokemonEntity, List.of(form_aspect_apply_order.get(currentIndex + 1)), null);
            }
            AdvancementHelper.grantAdvancement((ServerPlayer) player, "rotom/rotom_form_change");

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
