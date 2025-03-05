package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.item.ZMoves;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ZRingItem extends Item {
    public ZRingItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            ItemStack stack = user.getStackInHand(hand);

            // Get the player's Trinket component
            Optional<TrinketComponent> trinkets = TrinketsApi.getTrinketComponent(user);

            if (trinkets.isPresent()) {
                TrinketComponent trinketComponent = trinkets.get();

                // Get all Trinket slots under the "hand" slot group
                Map<String, TrinketInventory> handSlots = trinketComponent.getInventory().get("hand");

                if (handSlots != null) {
                    // Get the "bracelet" slot inside "hand" (hand/evowear)
                    TrinketInventory braceletInventory = handSlots.get("z_slot");

                    if (braceletInventory != null) {
                        // Find an empty slot in "hand/bracelet"
                        for (int i = 0; i < braceletInventory.size(); i++) {
                            if (braceletInventory.getStack(i).isEmpty()) {
                                // Equip the item in the hand/bracelet slot
                                braceletInventory.setStack(i, stack.copy());
                                user.setStackInHand(hand, ItemStack.EMPTY); // Remove from hand

                                world.playSound(null, user.getX(), user.getY(), user.getZ(),
                                        SoundEvents.ITEM_ARMOR_EQUIP_GENERIC.value(),
                                        SoundCategory.PLAYERS, 1.0f, 1.0f);

                                return TypedActionResult.success(stack, world.isClient);
                            }
                        }
                    }
                }
            }
        }

        return super.use(world, user, hand);
    }

    @Override
    public ActionResult useOnEntity(ItemStack arg, PlayerEntity player, LivingEntity context, Hand hand) {
        if (player.getWorld().isClient){
            return ActionResult.PASS;
        }

        if(context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == player){
            Pokemon pokemon = pk.getPokemon();
            if(pokemon.getEntity() == null || pokemon.getEntity().getWorld().isClient || pokemon.getEntity().isBattling()){
                return ActionResult.PASS;
            }

            if(pokemon.getSpecies().getName().equals("Necrozma") && pokemon.heldItem().isOf(ZMoves.ULTRANECROZIUM_Z)
                    && (pokemon.getForcedAspects().contains("dawn-fusion")
                    || pokemon.getForcedAspects().contains("dusk-fusion"))){
                FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of("ultra"));
                FlagSpeciesFeature feature = featureProvider.get(pokemon);

                if(feature != null){
                    boolean enabled = featureProvider.get(pokemon).getEnabled();

                    if(enabled) {
                        new FlagSpeciesFeature("ultra", false).apply(pokemon);
                        ultraAnimation(pokemon.getEntity());
                        return ActionResult.SUCCESS;
                    }
                }

                new FlagSpeciesFeature("ultra", true).apply(pokemon);
                ultraAnimation(pokemon.getEntity());
                return ActionResult.SUCCESS;
            }
        }

        return super.useOnEntity(arg, player, context, hand);
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

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.mega_showdown.z-ring.tooltip"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
