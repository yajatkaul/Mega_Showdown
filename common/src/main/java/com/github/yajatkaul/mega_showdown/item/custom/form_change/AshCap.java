package com.github.yajatkaul.mega_showdown.item.custom.form_change;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.github.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.utils.ParticlesList;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AshCap extends Item {
    public AshCap(Properties arg) {
        super(arg);
    }

    private final List<String> black_list = List.of(
            "cosplay",
            "belle",
            "libre",
            "phd",
            "pop_star",
            "rock_star"
    );

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity context, InteractionHand hand) {
        if (player.level().isClientSide) {
            return InteractionResult.PASS;
        }

        if (context instanceof PokemonEntity pokemonEntity) {
            if (pokemonEntity.getPokemon().getOwnerPlayer() != player || pokemonEntity.isBattling() || player.isCrouching()) {
                return InteractionResult.PASS;
            }

            if (pokemonEntity.getPokemon().getSpecies().getName().equals("Pikachu") && !pokemonEntity.getPokemon().getAspects().contains("partner-cap")
                    && pokemonEntity.getAspects().stream().noneMatch(black_list::contains)) {
                if (pokemonEntity.getFriendship() < MegaShowdownConfig.minBondingRequired) {
                    player.displayClientMessage(Component.translatable("message.mega_showdown.friendship_requirement")
                            .withStyle(ChatFormatting.RED), true);
                    return InteractionResult.PASS;
                }

                ParticlesList.heartAnim.applyEffects(pokemonEntity, List.of("league_cap=partner"), null);
                AdvancementHelper.grantAdvancement((ServerPlayer) player, "bond/ash_cap_bond");
                stack.consume(1, player);
                return InteractionResult.SUCCESS;
            } else if (pokemonEntity.getPokemon().getSpecies().getName().equals("Greninja") && !pokemonEntity.getPokemon().getAspects().contains("bond")) {
                if (pokemonEntity.getFriendship() < MegaShowdownConfig.minBondingRequired) {
                    player.displayClientMessage(Component.translatable("message.mega_showdown.friendship_requirement")
                            .withStyle(ChatFormatting.RED), true);
                    return InteractionResult.PASS;
                }
                ParticlesList.heartAnim.applyEffects(pokemonEntity, List.of("battle_bond=bond"), null);
                AdvancementHelper.grantAdvancement((ServerPlayer) player, "bond/ash_cap_bond");
                stack.consume(1, player);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}
