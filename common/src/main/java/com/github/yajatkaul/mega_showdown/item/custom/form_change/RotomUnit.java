package com.github.yajatkaul.mega_showdown.item.custom.form_change;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.github.yajatkaul.mega_showdown.advancement.AdvancementHelper;
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
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RotomUnit extends BlockItem {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 2000; // 2 sec
    private static final List<String> rotomAspects = List.of(
            "heat-appliance",
            "wash-appliance",
            "mow-appliance",
            "frost-appliance",
            "fan-appliance"
    );
    private final String form;

    public RotomUnit(Block arg, Properties arg2, String form) {
        super(arg, arg2);
        this.form = form;
    }

    public static boolean possible(ServerPlayer player) {
        UUID playerId = player.getUUID();
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId) && currentTime < cooldowns.get(playerId)) {
            player.displayClientMessage(Component.translatable("message.mega_showdown.not_so_fast")
                    .withColor(0xFF0000), true);
            return false;
        }

        // Apply cooldown
        cooldowns.put(playerId, currentTime + COOLDOWN_TIME);
        return true;
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
            int particleCount = (int) (100 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverLevel.sendParticles(
                        ParticleTypes.END_ROD, // Change this to any particle type
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

    @Override
    public InteractionResult interactLivingEntity(ItemStack arg, Player player, LivingEntity context, InteractionHand arg4) {
        if (!player.level().isClientSide && context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == player && !pk.isBattling() && !player.isCrouching()) {
            if (pk.getPokemon().getSpecies().getName().equals("Rotom")) {
                if (!possible((ServerPlayer) player)) {
                    return InteractionResult.PASS;
                }

                if (pk.getAspects().stream().anyMatch(rotomAspects::contains)) {
                    return InteractionResult.PASS;
                }

                playFormeChangeAnimation(pk);

                new StringSpeciesFeature("appliance", form).apply(pk);
                arg.consume(1, player);
                AdvancementHelper.grantAdvancement((ServerPlayer) player, "rotom/rotom_form_change");
                return InteractionResult.SUCCESS;
            }
        }

        return super.interactLivingEntity(arg, player, context, arg4);
    }
}
