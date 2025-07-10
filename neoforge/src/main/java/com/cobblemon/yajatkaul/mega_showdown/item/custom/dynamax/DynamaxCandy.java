package com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.item.abstracts.MSDPokemonSelectingItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DynamaxCandy extends MSDPokemonSelectingItem {
    public DynamaxCandy(Properties arg) {
        super(arg);
    }

    private void particleEffect(LivingEntity context, SimpleParticleType particleType) {
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 0.5; // Adjust this for more spread
            double adjustedWidth = entityWidth * scaleFactor;
            double adjustedHeight = entityHeight * scaleFactor;
            double adjustedDepth = entityWidth * scaleFactor;

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
                        particleType,
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
    public void appendHoverText(ItemStack arg, Item.TooltipContext arg2, List<Component> tooltipComponents, TooltipFlag arg3) {
        tooltipComponents.add(Component.translatable("tooltip.mega_showdown.dynamax_candy.tooltip"));
        super.appendHoverText(arg, arg2, tooltipComponents, arg3);
    }

    @Nullable
    @Override
    public InteractionResultHolder<ItemStack> applyToPokemon(@NotNull ServerPlayer player, @NotNull ItemStack itemStack, @NotNull Pokemon pokemon) {
        if (pokemon.getEntity() == null || pokemon.getEntity().level().isClientSide || pokemon.getEntity().isBattling()) {
            return InteractionResultHolder.pass(itemStack);
        }

        if (pokemon.getOwnerPlayer() == player && pokemon.getDmaxLevel() < 10) {
            pokemon.setDmaxLevel(pokemon.getDmaxLevel() + 1);
            if (pokemon.getDmaxLevel() == 10) {
                AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "dynamax/dynamax_candy_max");
            }
            if (pokemon.getSpecies().getName().equals("Calyrex")) {
                particleEffect(pokemon.getEntity(), ParticleTypes.SOUL_FIRE_FLAME);
            } else {
                particleEffect(pokemon.getEntity(), ParticleTypes.FLAME);
            }
            itemStack.consume(1, player);

            return InteractionResultHolder.success(itemStack);
        } else if (pokemon.getDmaxLevel() >= Cobblemon.config.getMaxDynamaxLevel() && pokemon.getOwnerPlayer() == player) {
            player.displayClientMessage(Component.translatable("message.mega_showdown.dmax_level_cap")
                    .withColor(0xFF0000), true);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public boolean canUseOnPokemon(@NotNull Pokemon pokemon) {
        return pokemon.getDmaxLevel() < Cobblemon.config.getMaxDynamaxLevel();
    }
}
