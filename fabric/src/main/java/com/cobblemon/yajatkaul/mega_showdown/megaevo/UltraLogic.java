package com.cobblemon.yajatkaul.mega_showdown.megaevo;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.item.ZMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.MegaBraceletItem;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.ZRingItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class UltraLogic {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 2000; // 2 sec

    public static boolean Possible(ServerPlayerEntity player) {
        UUID playerId = player.getUuid();
        long currentTime = System.currentTimeMillis();

        boolean hasUltraItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                trinkets.isEquipped(item -> item.getItem() instanceof ZRingItem)).orElse(false);

        boolean hasOffhandUltraItem = player.getOffHandStack().getItem() instanceof ZRingItem;
        boolean hasMainhandUltraItem = player.getMainHandStack().getItem() instanceof ZRingItem;

        if (!hasUltraItemTrinkets && !hasOffhandUltraItem && !hasMainhandUltraItem) {
            return false;
        }

        if (cooldowns.containsKey(playerId) && currentTime < cooldowns.get(playerId)) {
            player.sendMessage(
                    Text.literal("Not so fast!").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return false;
        }

        // Apply cooldown
        cooldowns.put(playerId, currentTime + COOLDOWN_TIME);
        return true;
    }

    public static void ultraTransform(ServerPlayerEntity player){
        double range = 5.0;

        Vec3d startPos = player.getEyePos();
        Vec3d lookVec = player.getRotationVector();
        Vec3d endPos = startPos.add(lookVec.multiply(range));

        EntityHitResult entityHit = ProjectileUtil.raycast(
                player,
                startPos,
                endPos,
                player.getBoundingBox().stretch(lookVec.multiply(range)).expand(1.0),
                entity -> !entity.isSpectator() && entity.canHit(),
                range * range
        );

        if(entityHit == null){
            return;
        }

        if(entityHit.getEntity() instanceof PokemonEntity pk) {
            Pokemon pokemon = pk.getPokemon();
            if(pokemon.getEntity() == null || pokemon.getEntity().getWorld().isClient || pokemon.getEntity().isBattling()){
                return;
            }

            if(pokemon.getSpecies().getName().equals("Necrozma") && pokemon.heldItem().isOf(ZMoves.ULTRANECROZIUM_Z)
                    && (pokemon.getForcedAspects().contains("dawn-fusion")
                    || pokemon.getForcedAspects().contains("dusk-fusion"))){
                if(!Possible(player)){
                    return;
                }

                FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of("ultra"));
                FlagSpeciesFeature feature = featureProvider.get(pokemon);

                if(feature != null){
                    boolean enabled = featureProvider.get(pokemon).getEnabled();

                    if(enabled) {
                        new FlagSpeciesFeature("ultra", false).apply(pokemon);
                        setTradable(pokemon, true);
                        ultraAnimation(pokemon.getEntity());
                        return;
                    }
                }

                new FlagSpeciesFeature("ultra", true).apply(pokemon);
                setTradable(pokemon, false);
                AdvancementHelper.grantAdvancement(player, "ultra_necrom");
                ultraAnimation(pokemon.getEntity());
            }
        }
    }

    public static void ultraAnimation(LivingEntity context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();
            double entityDepth = entityWidth; // Usually same as width for most mobs

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 1.2; // Adjust this for more spread
            double adjustedWidth = entityWidth * scaleFactor;
            double adjustedHeight = entityHeight * scaleFactor;
            double adjustedDepth = entityDepth * scaleFactor;

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_BEACON_ACTIVATE, // Change this if needed
                    SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (175 * adjustedWidth * adjustedHeight); // Scale particle amount

            for (int i = 0; i < particleCount; i++) {
                double xOffset = (Math.random() - 0.5) * adjustedWidth; // Random X within slightly expanded bounding box
                double yOffset = Math.random() * adjustedHeight; // Random Y within slightly expanded bounding box
                double zOffset = (Math.random() - 0.5) * adjustedDepth; // Random Z within slightly expanded bounding box

                serverWorld.spawnParticles(
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
