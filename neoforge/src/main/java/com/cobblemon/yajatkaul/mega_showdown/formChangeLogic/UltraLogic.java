package com.cobblemon.yajatkaul.mega_showdown.formChangeLogic;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.item.ZCrystals;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.zmove.ZRingItem;
import com.cobblemon.yajatkaul.mega_showdown.utility.ModTags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UltraLogic {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 2000; // 2 sec

    public static boolean Possible(ServerPlayer player) {
        UUID playerId = player.getUUID();
        long currentTime = System.currentTimeMillis();

        boolean hasUltraItemCurios = CuriosApi.getCuriosInventory(player)
                .map(inventory -> inventory.isEquipped(stack -> (stack.getItem() instanceof ZRingItem
                        || stack.is(ModTags.Items.Z_RINGS))))
                .orElse(false);

        boolean hasOffhandUltraItem = player.getOffhandItem().getItem() instanceof ZRingItem || player.getOffhandItem().is(ModTags.Items.Z_RINGS);
        boolean hasMainhandUltraItem = player.getMainHandItem().getItem() instanceof ZRingItem || player.getMainHandItem().is(ModTags.Items.Z_RINGS);

        if (!hasUltraItemCurios && !hasOffhandUltraItem && !hasMainhandUltraItem) {
            return false;
        }

        if (cooldowns.containsKey(playerId) && currentTime < cooldowns.get(playerId)) {
            player.displayClientMessage(Component.translatable("message.mega_showdown.not_so_fast")
                    .withColor(0xFF0000), true);
            return false;
        }

        // Apply cooldown
        cooldowns.put(playerId, currentTime + COOLDOWN_TIME);
        return true;
    }

    public static void ultraTransform(Player playerContext) {
        ServerPlayer player = (ServerPlayer) playerContext;

        double range = 5.0;

        // Check for entities first
        Vec3 startPos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 endPos = startPos.add(lookVec.multiply(range, range, range));

        AABB searchBox = new AABB(startPos, endPos).inflate(1.0);

        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                player.level(),
                player,
                startPos,
                endPos,
                searchBox,
                entity -> !entity.isSpectator() && entity.isPickable(),
                0.3f
        );

        if (entityHit == null || MegaShowdownConfig.battleModeOnly) {
            return;
        }

        if (entityHit.getEntity() instanceof PokemonEntity pk) {
            Pokemon pokemon = pk.getPokemon();

            if (pokemon.getEntity() == null || pokemon.getEntity().level().isClientSide || pokemon.getEntity().isBattling()) {
                return;
            }

            if (pokemon.getSpecies().getName().equals("Necrozma") && pokemon.heldItem().is(ZCrystals.ULTRANECROZIUM_Z) && checkFused(pokemon)) {
                if (!Possible(player)) {
                    return;
                }

                if (pokemon.getAspects().contains("ultra-fusion")) {
                    new StringSpeciesFeature("prism_fusion", pokemon.getPersistentData().getString("fusion_form")).apply(pokemon);
                    pokemon.getPersistentData().remove("fusion_form");
                    ultraAnimation(pokemon.getEntity());
                    return;
                }

                if (pokemon.getAspects().contains("dawn-fusion")) {
                    pokemon.getPersistentData().putString("fusion_form", "dawn");
                } else {
                    pokemon.getPersistentData().putString("fusion_form", "dusk");
                }

                new StringSpeciesFeature("prism_fusion", "ultra").apply(pokemon);
                pokemon.setTradeable(false);
                ultraAnimation(pokemon.getEntity());
            }
        }
    }

    private static boolean checkFused(Pokemon pokemon) {
        return pokemon.getAspects().contains("dusk-fusion") || pokemon.getAspects().contains("dawn-fusion") || pokemon.getAspects().contains("ultra-fusion");
    }

    public static void ultraAnimation(LivingEntity context) {
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 1.2; // Adjust this for more spread
            double adjustedWidth = entityWidth * scaleFactor;
            double adjustedHeight = entityHeight * scaleFactor;
            double adjustedDepth = entityWidth * scaleFactor;

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BEACON_ACTIVATE, // Change this if needed
                    SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (175 * adjustedWidth * adjustedHeight); // Scale particle amount

            for (int i = 0; i < particleCount; i++) {
                double xOffset = (Math.random() - 0.5) * adjustedWidth; // Random X within slightly expanded bounding box
                double yOffset = Math.random() * adjustedHeight; // Random Y within slightly expanded bounding box
                double zOffset = (Math.random() - 0.5) * adjustedDepth; // Random Z within slightly expanded bounding box

                serverLevel.sendParticles(
                        ParticleTypes.GLOW,
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
}
