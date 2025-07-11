package com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.item.abstracts.MSDPokemonSelectingItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DynamaxCandy extends MSDPokemonSelectingItem {
    public DynamaxCandy(Settings settings) {
        super(settings);
    }

    public static void particleEffect(PokemonEntity context, SimpleParticleType particleType) {
        if (context == null) return;
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 0.5; // Adjust this for more spread
            double adjustedWidth = entityWidth * scaleFactor;
            double adjustedHeight = entityHeight * scaleFactor;
            double adjustedDepth = entityWidth * scaleFactor;

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_FIRE_EXTINGUISH, // Change this if needed
                    SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (175 * adjustedWidth * adjustedHeight); // Scale particle amount

            for (int i = 0; i < particleCount; i++) {
                double xOffset = (Math.random() - 0.5) * adjustedWidth; // Random X within slightly expanded bounding box
                double yOffset = Math.random() * adjustedHeight; // Random Y within slightly expanded bounding box
                double zOffset = (Math.random() - 0.5) * adjustedDepth; // Random Z within slightly expanded bounding box

                serverWorld.spawnParticles(
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
    public @Nullable TypedActionResult<ItemStack> applyToPokemon(@NotNull ServerPlayerEntity player, @NotNull ItemStack itemStack, @NotNull Pokemon pokemon) {
        if (pokemon.getEntity() != null && pokemon.getEntity().isBattling()) {
            return TypedActionResult.pass(itemStack);
        }

        if (pokemon.getOwnerPlayer() == player && pokemon.getDmaxLevel() < 10) {
            pokemon.setDmaxLevel(pokemon.getDmaxLevel() + 1);

            player.sendMessage(
                    Text.translatable("message.mega_showdown.dmax_level_up", pokemon.getDisplayName(), pokemon.getDmaxLevel()).setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.GREEN))),
                    true
            );

            if (pokemon.getDmaxLevel() == 10) {
                AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "dynamax/dynamax_candy_max");
            }
            if (pokemon.getSpecies().getName().equals("Calyrex")) {
                particleEffect(pokemon.getEntity(), ParticleTypes.SOUL_FIRE_FLAME);
            } else {
                particleEffect(pokemon.getEntity(), ParticleTypes.FLAME);
            }
            itemStack.decrementUnlessCreative(1, player);

            return TypedActionResult.success(itemStack);
        } else if (pokemon.getDmaxLevel() >= 10 && pokemon.getOwnerPlayer() == player) {
            player.sendMessage(
                    Text.translatable("message.mega_showdown.dmax_level_cap").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED))),
                    true
            );
        }
        return TypedActionResult.pass(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.mega_showdown.dynamax_candy.tooltip"));
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public boolean canUseOnPokemon(@NotNull Pokemon pokemon) {
        return pokemon.getDmaxLevel() < Cobblemon.config.getMaxDynamaxLevel() && !pokemon.getSpecies().getDynamaxBlocked();
    }
}
