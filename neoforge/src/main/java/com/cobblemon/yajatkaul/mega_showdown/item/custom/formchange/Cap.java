package com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Cap extends Item {
    public Cap(Properties arg) {
        super(arg);
    }

    public static void playFormeChangeAnimation(LivingEntity context) {
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.AMETHYST_BLOCK_CHIME, // Change this if needed
                    SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (10 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverLevel.sendParticles(
                        ParticleTypes.HEART, // Change this to any particle type
                        entityPos.x + xOffset,
                        entityPos.y + yOffset,
                        entityPos.z + zOffset,
                        1, // One particle per call for better spread
                        0, 0, 0, // No movement velocity
                        0.1 // Slight motion
                );
            }
        }
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
    public InteractionResult interactLivingEntity(ItemStack arg, Player player, LivingEntity context, InteractionHand arg4) {
        if (context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == player && !pk.isBattling() && !player.isCrouching()) {
            if (pk.getPokemon().getSpecies().getName().equals("Pikachu") && !pk.getPokemon().getAspects().contains("partner-cap")
                    && pk.getAspects().stream().noneMatch(black_list::contains)) {
                if (pk.getFriendship() < 200) {
                    player.displayClientMessage(Component.translatable("message.mega_showdown.friendship_requirement")
                            .withColor(0xFF0000), true);
                    return InteractionResult.PASS;
                }
                playFormeChangeAnimation(context);
                new StringSpeciesFeature("league_cap", "partner").apply(pk);
                AdvancementHelper.grantAdvancement((ServerPlayer) player, "bond/ash_cap_bond");
                arg.consume(1, player);
            } else if (pk.getPokemon().getSpecies().getName().equals("Greninja") && !pk.getPokemon().getAspects().contains("bond")) {
                if (pk.getFriendship() < 200) {
                    player.displayClientMessage(Component.translatable("message.mega_showdown.friendship_requirement")
                            .withColor(0xFF0000), true);
                    return InteractionResult.PASS;
                }
                playFormeChangeAnimation(context);
                new StringSpeciesFeature("battle_bond", "bond").apply(pk);
                AdvancementHelper.grantAdvancement((ServerPlayer) player, "bond/ash_cap_bond");
                arg.consume(1, player);
            }
        }

        return super.interactLivingEntity(arg, player, context, arg4);
    }
}
