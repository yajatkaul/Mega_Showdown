package com.cobblemon.yajatkaul.mega_showdown.item.custom.tera;

import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
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

public class TeraShard extends Item {
    private final TeraType teraType;

    public TeraShard(Settings settings, TeraType teraType) {
        super(settings);
        this.teraType = teraType;
    }

    public static void particleEffect(LivingEntity context) {
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
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, // Change this if needed
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

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity context, Hand hand) {
        if (player.getWorld().isClient || player.isCrawling()) {
            return ActionResult.PASS;
        }

        if (context instanceof PokemonEntity pk) {
            Pokemon pokemon = pk.getPokemon();
            if (pokemon.getEntity() == null || pokemon.getEntity().getWorld().isClient || pokemon.getEntity().isBattling()) {
                return ActionResult.PASS;
            }

            if (pokemon.getSpecies().getName().equals("Ogerpon")
                    || pokemon.getSpecies().getName().equals("Terapagos")) {
                return ActionResult.PASS;
            }

            final int required_shards = MegaShowdownConfig.teraShardRequired.get();

            if (pokemon.getOwnerPlayer() == player && stack.getCount() == required_shards) {
                if(pokemon.getTeraType() == teraType){
                    player.sendMessage(
                            Text.translatable("message.mega_showdown.same_tera").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                            true
                    );
                    return ActionResult.PASS;
                }
                stack.decrementUnlessCreative(MegaShowdownConfig.teraShardRequired.get(), player);
                if (teraType != TeraTypes.getSTELLAR()) {
                    particleEffect(pokemon.getEntity());
                    pokemon.setTeraType(teraType);
                    AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "tera/change_tera");
                } else {
                    particleEffect(pokemon.getEntity());
                    pokemon.setTeraType(teraType);
                    AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "tera/change_tera");
                    AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "tera/change_tera_stellar");
                }
            } else if (pokemon.getOwnerPlayer() == player && stack.getCount() != required_shards) {
                player.sendMessage(
                        Text.translatable("message.mega_showdown.tera_requirements", required_shards).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
            }

            return ActionResult.SUCCESS;
        }

        return super.useOnEntity(stack, player, context, hand);
    }
}
