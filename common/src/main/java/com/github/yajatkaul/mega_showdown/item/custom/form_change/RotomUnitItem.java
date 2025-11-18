package com.github.yajatkaul.mega_showdown.item.custom.form_change;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipBlockItem;
import com.github.yajatkaul.mega_showdown.utils.ParticlesList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RotomUnitItem extends ToolTipBlockItem {
    private final String form;
    private static final List<String> rotomAspects = List.of(
            "heat-appliance",
            "wash-appliance",
            "mow-appliance",
            "frost-appliance",
            "fan-appliance"
    );

    public RotomUnitItem(Block block, Properties properties, String form) {
        super(block, properties);
        this.form = form;
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if (player.level().isClientSide || player.isCrouching()) {
            return InteractionResult.PASS;
        }

        if (livingEntity instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();
            if (!pokemon.getSpecies().getName().equals("Rotom") || pokemon.getAspects().stream().anyMatch(rotomAspects::contains)) {
                return InteractionResult.PASS;
            }

            ParticlesList.getEffect("mega_showdown:end_rod").applyEffects(pokemon, List.of(String.format("appliance=%s", this.form)), null);
            itemStack.consume(1, player);
            AdvancementHelper.grantAdvancement((ServerPlayer) player, "rotom/rotom_form_change");
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
