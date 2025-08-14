package com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class Cap extends Item {

    public Cap(Settings settings) {
        super(settings);
    }

    public static void playFormeChangeAnimation(LivingEntity context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, // Yarn mapping for BEACON_ACTIVATE
                    SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (10 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverWorld.spawnParticles(
                        ParticleTypes.HEART, // Same particle type
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
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        if (entity instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == user && !pk.isBattling()
        && pk.getAspects().stream().noneMatch(black_list::contains)) {
            if (pk.getPokemon().getSpecies().getName().equals("Pikachu") && !pk.getPokemon().getAspects().contains("partner-cap")) {
                if (pk.getFriendship() < 200) {
                    user.sendMessage(
                            Text.translatable("message.mega_showdown.friendship_requirement").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                            true
                    );
                    return ActionResult.PASS;
                }
                playFormeChangeAnimation(entity);
                new StringSpeciesFeature("league_cap", "partner").apply(pk);
                AdvancementHelper.grantAdvancement((ServerPlayerEntity) user, "bond/ash_cap_bond");
                stack.decrementUnlessCreative(1, user);
            } else if (pk.getPokemon().getSpecies().getName().equals("Greninja") && !pk.getPokemon().getAspects().contains("bond")) {
                if (pk.getFriendship() < 200) {
                    user.sendMessage(
                            Text.translatable("message.mega_showdown.friendship_requirement").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                            true
                    );
                    return ActionResult.PASS;
                }
                playFormeChangeAnimation(entity);
                new StringSpeciesFeature("battle_bond", "bond").apply(pk);
                AdvancementHelper.grantAdvancement((ServerPlayerEntity) user, "bond/ash_cap_bond");
                stack.decrementUnlessCreative(1, user);
            }
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

}
