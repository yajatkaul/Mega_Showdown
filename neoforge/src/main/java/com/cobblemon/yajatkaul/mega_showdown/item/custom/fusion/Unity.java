package com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.dataAttachments.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.dataAttachments.PokeHandler;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class Unity extends Item {
    public Unity(Properties arg) {
        super(arg);
    }

    public static EntityHitResult getEntityLookingAt(Player player, float distance) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 targetPos = eyePos.add(lookVec.scale(distance));

        AABB rayTraceBox = new AABB(eyePos, targetPos);

        return ProjectileUtil.getEntityHitResult(
                player.level(),
                player,
                eyePos,
                targetPos,
                rayTraceBox, // Use the ray trace box directly
                entity -> !entity.isSpectator() && entity instanceof LivingEntity && entity.isPickable(),
                0.3f // Smaller collision expansion value for more precise detection
        );
    }

    public static void particleEffect(LivingEntity conComponent, SimpleParticleType particleType) {
        if (conComponent.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = conComponent.position(); // Get entity position

            // Get entity's size
            double entityWidth = conComponent.getBbWidth();
            double entityHeight = conComponent.getBbHeight();
            double entityDepth = entityWidth; // Usually same as width for most mobs

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 2; // Adjust this for more spread
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide) {
            return InteractionResultHolder.fail(stack);
        }

        EntityHitResult hitResult = getEntityLookingAt(player, 4.5f);

        PokeHandler pokeHandler = stack.get(DataManage.POKEMON_STORAGE);

        if (hitResult == null && pokeHandler != null) {
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);

            playerPartyStore.add(pokeHandler.getPokemon());
            stack.set(DataManage.POKEMON_STORAGE, null);
            stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.reins_of_unity.inactive"));
            player.setItemInHand(hand, stack);
            return InteractionResultHolder.consume(stack);
        } else if (hitResult != null && hitResult.getEntity() instanceof PokemonEntity pkmn) {
            if(pkmn.isBattling()) return InteractionResultHolder.pass(stack);

            Pokemon context = pkmn.getPokemon();

            if (player.isCrouching()) {
                return InteractionResultHolder.pass(stack);
            }

            if (!(context.getEntity() instanceof PokemonEntity pk)) {
                return InteractionResultHolder.pass(stack);
            }

            Pokemon pokemon = pk.getPokemon();
            if (pokemon.getOwnerPlayer() != player) {
                return InteractionResultHolder.pass(stack);
            }

            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);
            PokeHandler refValue = stack.getOrDefault(DataManage.POKEMON_STORAGE, null);
            Pokemon currentValue;

            if (refValue == null) {
                currentValue = null;
            } else {
                currentValue = refValue.getPokemon();
            }

            if (pokemon.getSpecies().getName().equals("Calyrex") && checkEnabled(pokemon)) {
                if (stack.get(DataManage.POKEMON_STORAGE) != null) {
                    player.displayClientMessage(Component.translatable("message.mega_showdown.already_fused")
                            .withColor(0xFF0000), true);
                    return InteractionResultHolder.pass(stack);
                }
                particleEffect(pk, ParticleTypes.END_ROD);
                new StringSpeciesFeature("king_steed", "none").apply(pokemon);
                pokemon.setTradeable(true);

                Pokemon pokemon1 = Pokemon.Companion.loadFromNBT(player.level().registryAccess(), pokemon.getPersistentData().getCompound("fusion_pokemon"));
                playerPartyStore.add(pokemon1);
                pokemon.getPersistentData().remove("fusion_forme");

                stack.set(DataManage.POKEMON_STORAGE, null);
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.reins_of_unity.inactive"));
            } else if (currentValue != null && pokemon.getSpecies().getName().equals("Calyrex")) {
                if (currentValue.getSpecies().getName().equals("Spectrier")) {
                    particleEffect(pk, ParticleTypes.SMOKE);
                    new StringSpeciesFeature("king_steed", "shadow").apply(pokemon);
                } else {
                    particleEffect(pk, ParticleTypes.END_ROD);
                    new StringSpeciesFeature("king_steed", "ice").apply(pokemon);
                }
                pokemon.setTradeable(false);

                CompoundTag otherPokemonNbt = currentValue.saveToNBT(player.level().registryAccess(), new CompoundTag());
                pokemon.getPersistentData().put("fusion_pokemon", otherPokemonNbt);

                stack.set(DataManage.POKEMON_STORAGE, null);
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.reins_of_unity.inactive"));
            } else if (currentValue == null && pokemon.getSpecies().getName().equals("Spectrier") && pokemon.getEntity().getTethering() == null) {
                stack.set(DataManage.POKEMON_STORAGE, new PokeHandler(pk.getPokemon()));
                playerPartyStore.remove(pk.getPokemon());
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.reins_of_unity.charged"));
            } else if (currentValue == null && pokemon.getSpecies().getName().equals("Glastrier") && pokemon.getEntity().getTethering() == null) {
                stack.set(DataManage.POKEMON_STORAGE, new PokeHandler(pk.getPokemon()));
                playerPartyStore.remove(pk.getPokemon());
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.reins_of_unity.charged"));
            } else {
                return InteractionResultHolder.pass(stack);
            }

            player.setItemInHand(hand, stack);
            player.getInventory().setChanged();
            return InteractionResultHolder.success(stack);
        }

        return InteractionResultHolder.pass(stack);
    }

    private boolean checkEnabled(Pokemon pokemon) {
        return pokemon.getAspects().contains("shadow-rider") || pokemon.getAspects().contains("ice-rider");
    }
}
