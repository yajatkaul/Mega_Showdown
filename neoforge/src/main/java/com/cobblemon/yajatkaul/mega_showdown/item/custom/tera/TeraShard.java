package com.cobblemon.yajatkaul.mega_showdown.item.custom.tera;

import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class TeraShard extends Item {
    private final TeraType teraType;
    public TeraShard(Properties arg, TeraType teraType) {
        super(arg);
        this.teraType = teraType;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack arg, Player player, @NotNull LivingEntity context, InteractionHand hand) {
        if (player.level().isClientSide || player.isCrouching()) {
            return InteractionResult.PASS;
        }

        if (context instanceof PokemonEntity pk) {
            Pokemon pokemon = pk.getPokemon();
            if (pokemon.getEntity() == null || pokemon.getEntity().level().isClientSide || pokemon.getEntity().isBattling()) {
                return InteractionResult.PASS;
            }

            if (pokemon.getSpecies().getName().equals("Ogerpon")
                    || pokemon.getSpecies().getName().equals("Terapagos")) {
                return InteractionResult.PASS;
            }

            final int required_shards = MegaShowdownConfig.teraShardRequired;

            if (pokemon.getOwnerPlayer() == player && arg.getCount() == required_shards) {
                if(pokemon.getTeraType() == teraType){
                    player.displayClientMessage(Component.translatable("message.mega_showdown.same_tera")
                            .withColor(0xFF0000), true);
                    return InteractionResult.PASS;
                }
                arg.shrink(required_shards);
                if (teraType != TeraMoves.STELLAR_TERA_SHARD.get()) {
                    particleEffect(pokemon.getEntity());
                    pokemon.setTeraType(teraType);
                    AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "tera/change_tera");
                } else {
                    particleEffect(pokemon.getEntity());
                    pokemon.setTeraType(teraType);
                    AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "tera/change_tera");
                    AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "tera/change_tera_stellar");
                }
            } else if (pokemon.getOwnerPlayer() == player && arg.getCount() != required_shards) {
                player.displayClientMessage(Component.translatable("message.mega_showdown.tera_requirements", required_shards)
                        .withColor(0xFF0000), true);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private void particleEffect(LivingEntity context) {
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();
            double entityDepth = entityWidth; // Usually same as width for most mobs

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 1.2; // Adjust this for more spread
            double adjustedWidth = entityWidth * scaleFactor;
            double adjustedHeight = entityHeight * scaleFactor;
            double adjustedDepth = entityDepth * scaleFactor;

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.AMETHYST_BLOCK_CHIME, // Change this if needed
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
