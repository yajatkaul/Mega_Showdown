package com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.dataAttachments.DataManage;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Unity extends Item {
    public Unity(Settings settings) {
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

    public static EntityHitResult getEntityLookingAt(PlayerEntity player, double distance) {
        Vec3d eyePos = player.getEyePos();
        Vec3d lookVec = player.getRotationVec(1.0F);
        Vec3d targetPos = eyePos.add(lookVec.multiply(distance));

        return ProjectileUtil.raycast(
                player,
                eyePos,
                targetPos,
                player.getBoundingBox().stretch(lookVec.multiply(distance)).expand(1.0, 1.0, 1.0),
                entity -> !entity.isSpectator() && entity.canHit() && entity instanceof LivingEntity,
                distance * distance
        );
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (world.isClient) {
            return TypedActionResult.fail(stack);
        }

        EntityHitResult hitResult = getEntityLookingAt(player, 4.5f);

        Pokemon currentValue = stack.get(DataManage.POKEMON_STORAGE);

        if (hitResult == null && currentValue != null) {
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);

            playerPartyStore.add(currentValue);
            stack.set(DataManage.POKEMON_STORAGE, null);
            stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.reins_of_unity.inactive"));
            player.setStackInHand(hand, stack);
            return TypedActionResult.consume(stack);
        } else if (hitResult != null && hitResult.getEntity() instanceof PokemonEntity pkmn) {
            Pokemon context = pkmn.getPokemon();

            if (player.isCrawling()) {
                return TypedActionResult.pass(stack);
            }

            if (!(context.getEntity() instanceof PokemonEntity pk)) {
                return TypedActionResult.pass(stack);
            }

            Pokemon pokemon = pk.getPokemon();
            if (pokemon.getOwnerPlayer() != player || pokemon.getEntity() == null) {
                return TypedActionResult.pass(stack);
            }

            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);

            if (pokemon.getSpecies().getName().equals("Calyrex") && checkEnabled(pokemon)) {
                if (stack.get(DataManage.POKEMON_STORAGE) != null) {
                    player.sendMessage(
                            Text.translatable("message.mega_showdown.already_fused").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                            true
                    );
                    return TypedActionResult.pass(stack);
                }
                particleEffect(pk, ParticleTypes.END_ROD);
                new StringSpeciesFeature("king_steed", "none").apply(pokemon);
                pokemon.setTradeable(true);

                Pokemon pokemon1 = Pokemon.Companion.loadFromNBT(player.getWorld().getRegistryManager(), pokemon.getPersistentData().getCompound("fusion_pokemon"));
                playerPartyStore.add(pokemon1);
                pokemon.getPersistentData().remove("fusion_forme");

                stack.set(DataManage.POKEMON_STORAGE, null);
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.reins_of_unity.inactive"));
            } else if (currentValue != null && pokemon.getSpecies().getName().equals("Calyrex")) {
                if (currentValue.getSpecies().getName().equals("Spectrier")) {
                    particleEffect(pk, ParticleTypes.SMOKE);
                    new StringSpeciesFeature("king_steed", "shadow").apply(pokemon);
                } else {
                    particleEffect(pk, ParticleTypes.END_ROD);
                    new StringSpeciesFeature("king_steed", "ice").apply(pokemon);
                }
                pokemon.setTradeable(false);

                NbtCompound otherPokemonNbt = currentValue.saveToNBT(player.getWorld().getRegistryManager(), new NbtCompound());
                pokemon.getPersistentData().put("fusion_pokemon", otherPokemonNbt);

                stack.set(DataManage.POKEMON_STORAGE, null);
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.reins_of_unity.inactive"));
            } else if (currentValue == null && pokemon.getSpecies().getName().equals("Spectrier") && pokemon.getEntity().getTethering() == null) {
                stack.set(DataManage.POKEMON_STORAGE, pk.getPokemon());
                playerPartyStore.remove(pk.getPokemon());
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.reins_of_unity.charged"));
            } else if (currentValue == null && pokemon.getSpecies().getName().equals("Glastrier") && pokemon.getEntity().getTethering() == null) {
                stack.set(DataManage.POKEMON_STORAGE, pk.getPokemon());
                playerPartyStore.remove(pk.getPokemon());
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.reins_of_unity.charged"));
            } else {
                return TypedActionResult.pass(stack);
            }

            player.setStackInHand(hand, stack);
            return TypedActionResult.success(stack);
        }

        return TypedActionResult.pass(stack);
    }

    private boolean checkEnabled(Pokemon pokemon) {
        return pokemon.getAspects().contains("shadow-rider") || pokemon.getAspects().contains("ice-rider");
    }
}
