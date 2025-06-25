package com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.item.PokemonSelectingItem;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.item.impl.MSDPokemonSelectingItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DynamaxCandy extends MSDPokemonSelectingItem {
    public DynamaxCandy(Settings settings) {
        super(settings);
    }

    public static void particleEffect(LivingEntity context, SimpleParticleType particleType) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();
            double entityDepth = entityWidth; // Usually same as width for most mobs

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 0.5; // Adjust this for more spread
            double adjustedWidth = entityWidth * scaleFactor;
            double adjustedHeight = entityHeight * scaleFactor;
            double adjustedDepth = entityDepth * scaleFactor;

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
        if (pokemon.getEntity() == null || pokemon.getEntity().getWorld().isClient || pokemon.getEntity().isBattling()) {
            return TypedActionResult.pass(itemStack);
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
            itemStack.decrementUnlessCreative(1, player);

            return TypedActionResult.success(itemStack);
        } else if (pokemon.getDmaxLevel() >= 10 && pokemon.getOwnerPlayer() == player) {
            player.sendMessage(
                    Text.translatable("message.mega_showdown.dmax_level_cap").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
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
    public boolean canUseOnPokemon (@NotNull Pokemon pokemon) {
        return pokemon.getDmaxLevel() < Cobblemon.config.getMaxDynamaxLevel();
    }
}
