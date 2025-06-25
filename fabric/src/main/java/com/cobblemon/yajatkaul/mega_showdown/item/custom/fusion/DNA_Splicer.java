package com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.PokeHandler;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import kotlin.Unit;
import net.minecraft.component.DataComponentTypes;
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
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class DNA_Splicer extends Item {
    public DNA_Splicer(Settings settings) {
        super(settings);
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

    public static void fuseEffect(PokemonEntity pokemon, boolean black) {
        SnowStormHandler.Companion.snowStormPartileSpawner(pokemon,
                black ? "kyurem_b_effect" : "kyurem_w_effect", "target");
        pokemon.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), true);

        BlockPos entityPos = pokemon.getBlockPos();
        pokemon.getWorld().playSound(
                null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                ModSounds.KYUREM_FUSION,
                SoundCategory.PLAYERS, 0.2f, 1.0f
        );

        pokemon.after(4F, () -> {
            new FlagSpeciesFeature(black ? "black" : "white", true).apply(pokemon);
            SnowStormHandler.Companion.cryAnimation(pokemon);
            pokemon.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), false);
            return Unit.INSTANCE;
        });
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
            if (pokemon.getOwnerPlayer() != player || pokemon.getEntity() == null) {
                return TypedActionResult.pass(stack);
            }

            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);

            if (pokemon.getSpecies().getName().equals("Kyurem") && checkEnabled(pokemon)) {
                if (stack.get(DataManage.POKEMON_STORAGE) != null) {
                    player.sendMessage(
                            Text.translatable("message.mega_showdown.already_fused").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                            true
                    );
                    return TypedActionResult.pass(stack);
                }
                particleEffect(pk);
                new FlagSpeciesFeature("white", false).apply(pokemon);
                new FlagSpeciesFeature("black", false).apply(pokemon);
                setTradable(pokemon, true);

                if (!pokemon.getEntity().hasAttached(DataManage.KYUREM_FUSED_WITH)) {
                    HashMap<UUID, Pokemon> map = player.getAttached(DataManage.DATA_MAP);
                    Pokemon toAdd = map.get(pokemon.getUuid());
                    playerPartyStore.add(toAdd);
                    map.remove(pokemon.getUuid());
                    player.setAttached(DataManage.DATA_MAP, map);
                } else {
                    playerPartyStore.add(pokemon.getEntity().getAttached(DataManage.KYUREM_FUSED_WITH).getPokemon());
                    pokemon.getEntity().removeAttached(DataManage.KYUREM_FUSED_WITH);
                }

                stack.set(DataManage.POKEMON_STORAGE, null);
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.dna_splicer.inactive"));
            } else if (currentValue != null && pokemon.getSpecies().getName().equals("Kyurem")) {
                fuseEffect(pk, !currentValue.getSpecies().getName().equals("Reshiram"));
                setTradable(pokemon, false);

                pokemon.getEntity().setAttached(DataManage.KYUREM_FUSED_WITH, new PokeHandler(currentValue));

                HashMap<UUID, Pokemon> map = player.getAttached(DataManage.DATA_MAP);
                if (map == null) {
                    map = new HashMap<>();
                }
                map.put(pokemon.getUuid(), currentValue);
                player.setAttached(DataManage.DATA_MAP, map);

                stack.set(DataManage.POKEMON_STORAGE, null);
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.dna_splicer.inactive"));
            } else if (currentValue == null && pokemon.getSpecies().getName().equals("Reshiram")) {
                stack.set(DataManage.POKEMON_STORAGE, pk.getPokemon());
                playerPartyStore.remove(pk.getPokemon());
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.dna_splicer.charged"));
            } else if (currentValue == null && pokemon.getSpecies().getName().equals("Zekrom")) {
                stack.set(DataManage.POKEMON_STORAGE, pk.getPokemon());
                playerPartyStore.remove(pk.getPokemon());
                stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.dna_splicer.charged"));
            } else {
                return TypedActionResult.pass(stack);
            }

            player.setStackInHand(hand, stack);
            return TypedActionResult.success(stack);
        }

        return TypedActionResult.pass(stack);
    }

    private boolean checkEnabled(Pokemon pokemon) {
        return pokemon.getAspects().contains("black") || pokemon.getAspects().contains("white");
    }
}
