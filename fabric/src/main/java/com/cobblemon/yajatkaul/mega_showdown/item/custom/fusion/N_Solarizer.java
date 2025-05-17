package com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.PokeHandler;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
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
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class N_Solarizer extends Item {
    public N_Solarizer(Settings settings) {
        super(settings);
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
            if (pokemon.getOwnerPlayer() != player) {
                return TypedActionResult.pass(stack);
            }

            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);

            if (currentValue != null && pokemon.getSpecies().getName().equals("Necrozma")) {
                if (checkFused(pokemon)){
                    player.sendMessage(
                            Text.translatable("message.mega_showdown.already_fused").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                            true
                    );
                    return TypedActionResult.pass(stack);
                }

                HashMap<UUID, Pokemon> map = player.getAttached(DataManage.DATA_MAP);
                if(map == null){
                    map = new HashMap<>();
                }
                map.put(pokemon.getUuid(), currentValue);
                player.setAttached(DataManage.DATA_MAP, map);

                pk.setAttached(DataManage.N_SOLAR_POKEMON, new PokeHandler(currentValue));
                stack.set(DataManage.POKEMON_STORAGE, null);
                new FlagSpeciesFeature("dusk-fusion", true).apply(pokemon);
                particleEffect(pokemon.getEntity());
                setTradable(pokemon, false);

                stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.n_solarizer.inactive"));
            } else if (currentValue == null && pokemon.getSpecies().getName().equals("Solgaleo")) {
                stack.set(DataManage.POKEMON_STORAGE, pokemon);
                playerPartyStore.remove(pokemon);
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.n_solarizer.charged"));
            } else if (pokemon.getSpecies().getName().equals("Necrozma") && checkEnabled(pokemon)) {
                FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of("ultra"));
                FlagSpeciesFeature feature = featureProvider.get(pokemon);

                if(feature != null){
                    boolean enabled = featureProvider.get(pokemon).getEnabled();

                    if(enabled) {
                        return TypedActionResult.pass(stack);
                    }
                }

                if(!pokemon.getEntity().hasAttached(DataManage.N_SOLAR_POKEMON)){
                    HashMap<UUID, Pokemon> map = player.getAttached(DataManage.DATA_MAP);
                    Pokemon toAdd = map.get(pokemon.getUuid());
                    playerPartyStore.add(toAdd);
                    map.remove(pokemon.getUuid());
                    player.setAttached(DataManage.DATA_MAP, map);
                }else{
                    playerPartyStore.add(pokemon.getEntity().getAttached(DataManage.N_SOLAR_POKEMON).getPokemon());
                    pk.removeAttached(DataManage.N_SOLAR_POKEMON);
                }

                new FlagSpeciesFeature("dusk-fusion", false).apply(pokemon);
                particleEffect(pokemon.getEntity());
                setTradable(pokemon, true);
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.n_solarizer.inactive"));
            } else {
                return TypedActionResult.pass(stack);
            }

            player.setStackInHand(hand, stack);
            return TypedActionResult.success(stack);
        }

        return TypedActionResult.pass(stack);
    }

    private boolean checkEnabled(Pokemon pokemon){
        return pokemon.getAspects().contains("dusk-fusion");
    }

    private boolean checkFused(Pokemon pokemon){
        return pokemon.getAspects().contains("dusk-fusion") || pokemon.getAspects().contains("dawn-fusion");
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
                        ParticleTypes.ASH,
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
}
