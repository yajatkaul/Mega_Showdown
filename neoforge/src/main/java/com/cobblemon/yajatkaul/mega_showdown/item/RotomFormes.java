package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.ITEMS;

public class RotomFormes {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 2000; // 2 sec
    public static final DeferredItem<Item> FAN = ITEMS.register("fanunit",
            () -> new Item(new Item.Properties()) {
                @Override
                public InteractionResult interactLivingEntity(ItemStack arg, Player player, LivingEntity context, InteractionHand arg4) {
                    if (!player.level().isClientSide && context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == player && !pk.isBattling() && !player.isCrouching()) {
                        Pokemon pokemon = pk.getPokemon();
                        if (pokemon.getSpecies().getName().equals("Rotom")) {
                            if (!possible((ServerPlayer) player)) {
                                return InteractionResult.PASS;
                            }

                            playFormeChangeAnimation(pk);

                            if (pokemon.getAspects().contains("fan-appliance")) {
                                new StringSpeciesFeature("appliance", "none").apply(pk);
                                return InteractionResult.SUCCESS;
                            }

                            new StringSpeciesFeature("appliance", "fan").apply(pk);
                            AdvancementHelper.grantAdvancement((ServerPlayer) player, "rotom/rotom_form_change");
                            return InteractionResult.SUCCESS;
                        }
                    }

                    return super.interactLivingEntity(arg, player, context, arg4);
                }
            });
    public static final DeferredItem<Item> FRIDGEUNIT = ITEMS.register("fridgeunit",
            () -> new Item(new Item.Properties()) {
                @Override
                public InteractionResult interactLivingEntity(ItemStack arg, Player player, LivingEntity context, InteractionHand arg4) {
                    if (!player.level().isClientSide && context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == player && !pk.isBattling() && !player.isCrouching()) {
                        Pokemon pokemon = pk.getPokemon();
                        if (pk.getPokemon().getSpecies().getName().equals("Rotom")) {
                            if (!possible((ServerPlayer) player)) {
                                return InteractionResult.PASS;
                            }

                            playFormeChangeAnimation(pk);

                            if (pokemon.getAspects().contains("frost-appliance")) {
                                new StringSpeciesFeature("appliance", "none").apply(pk);
                                return InteractionResult.SUCCESS;
                            }

                            new StringSpeciesFeature("appliance", "frost").apply(pk);
                            AdvancementHelper.grantAdvancement((ServerPlayer) player, "rotom/rotom_form_change");
                            return InteractionResult.SUCCESS;
                        }
                    }

                    return super.interactLivingEntity(arg, player, context, arg4);
                }
            });
    public static final DeferredItem<Item> MOWUNIT = ITEMS.register("mowunit",
            () -> new Item(new Item.Properties()) {
                @Override
                public InteractionResult interactLivingEntity(ItemStack arg, Player player, LivingEntity context, InteractionHand arg4) {
                    if (!player.level().isClientSide && context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == player && !pk.isBattling() && !player.isCrouching()) {
                        Pokemon pokemon = pk.getPokemon();
                        if (pk.getPokemon().getSpecies().getName().equals("Rotom")) {
                            if (!possible((ServerPlayer) player)) {
                                return InteractionResult.PASS;
                            }

                            playFormeChangeAnimation(pk);

                            if (pokemon.getAspects().contains("mow-appliance")) {
                                new StringSpeciesFeature("appliance", "none").apply(pk);
                                return InteractionResult.SUCCESS;
                            }

                            new StringSpeciesFeature("appliance", "mow").apply(pk);
                            AdvancementHelper.grantAdvancement((ServerPlayer) player, "rotom/rotom_form_change");
                            return InteractionResult.SUCCESS;
                        }

                    }

                    return super.interactLivingEntity(arg, player, context, arg4);
                }
            });
    public static final DeferredItem<Item> OVENUNIT = ITEMS.register("ovenunit",
            () -> new Item(new Item.Properties()) {
                @Override
                public InteractionResult interactLivingEntity(ItemStack arg, Player player, LivingEntity context, InteractionHand arg4) {
                    if (!player.level().isClientSide && context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == player && !pk.isBattling() && !player.isCrouching()) {
                        Pokemon pokemon = pk.getPokemon();
                        if (pk.getPokemon().getSpecies().getName().equals("Rotom")) {
                            if (!possible((ServerPlayer) player)) {
                                return InteractionResult.PASS;
                            }

                            playFormeChangeAnimation(pk);

                            if (pokemon.getAspects().contains("heat-appliance")) {
                                new StringSpeciesFeature("appliance", "none").apply(pk);
                                return InteractionResult.SUCCESS;
                            }

                            new StringSpeciesFeature("appliance", "heat").apply(pk);
                            AdvancementHelper.grantAdvancement((ServerPlayer) player, "rotom/rotom_form_change");
                            return InteractionResult.SUCCESS;
                        }
                    }

                    return super.interactLivingEntity(arg, player, context, arg4);
                }
            });
    public static final DeferredItem<Item> WASHUNIT = ITEMS.register("washunit",
            () -> new Item(new Item.Properties()) {
                @Override
                public InteractionResult interactLivingEntity(ItemStack arg, Player player, LivingEntity context, InteractionHand arg4) {
                    if (!player.level().isClientSide && context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == player && !pk.isBattling() && !player.isCrouching()) {
                        Pokemon pokemon = pk.getPokemon();
                        if (pk.getPokemon().getSpecies().getName().equals("Rotom")) {
                            if (!possible((ServerPlayer) player)) {
                                return InteractionResult.PASS;
                            }

                            playFormeChangeAnimation(pk);

                            if (pokemon.getAspects().contains("wash-appliance")) {
                                new StringSpeciesFeature("appliance", "none").apply(pk);
                                return InteractionResult.SUCCESS;
                            }

                            new StringSpeciesFeature("appliance", "wash").apply(pk);
                            AdvancementHelper.grantAdvancement((ServerPlayer) player, "rotom/rotom_form_change");
                            return InteractionResult.SUCCESS;
                        }
                    }

                    return super.interactLivingEntity(arg, player, context, arg4);
                }
            });
    public static final DeferredItem<Item> ROTOM_CATALOGUE = ITEMS.register("rotom_catalogue",
            () -> new Item(new Item.Properties().component(DataManage.CATALOGUE_PAGE, 1)) {
                @Override
                public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity context, InteractionHand hand) {
                    if (!player.level().isClientSide && context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == player && !pk.isBattling() && !player.isCrouching()) {
                        Pokemon pokemon = pk.getPokemon();
                        if (pokemon.getSpecies().getName().equals("Rotom")) {

                            if (!possible((ServerPlayer) player)) {
                                return InteractionResult.PASS;
                            }

                            playFormeChangeAnimation(pk);

                            int currentPage = stack.getOrDefault(DataManage.CATALOGUE_PAGE, 1);

                            // Apply new form based on page
                            switch (currentPage) {
                                case 1:
                                    new StringSpeciesFeature("appliance", "heat").apply(pk);
                                    AdvancementHelper.grantAdvancement((ServerPlayer) player, "rotom/rotom_form_change");
                                    break;
                                case 2:
                                    new StringSpeciesFeature("appliance", "fan").apply(pk);
                                    AdvancementHelper.grantAdvancement((ServerPlayer) player, "rotom/rotom_form_change");
                                    break;
                                case 3:
                                    new StringSpeciesFeature("appliance", "mow").apply(pk);
                                    AdvancementHelper.grantAdvancement((ServerPlayer) player, "rotom/rotom_form_change");
                                    break;
                                case 4:
                                    new StringSpeciesFeature("appliance", "frost").apply(pk);
                                    AdvancementHelper.grantAdvancement((ServerPlayer) player, "rotom/rotom_form_change");
                                    break;
                                case 5:
                                    new StringSpeciesFeature("appliance", "wash").apply(pk);
                                    AdvancementHelper.grantAdvancement((ServerPlayer) player, "rotom/rotom_form_change");
                                    break;
                                default:
                                    new StringSpeciesFeature("appliance", "none").apply(pk); // Fallback
                                    break;
                            }

                            int nextPage = currentPage + 1;
                            if (nextPage == 7) {
                                nextPage = 1;
                            }
                            stack.set(DataManage.CATALOGUE_PAGE, nextPage);

                            player.setItemInHand(hand, stack);
                            player.getInventory().setChanged();

                            return InteractionResult.SUCCESS;
                        }
                    }

                    return super.interactLivingEntity(stack, player, context, hand);
                }
            });

    public static boolean possible(ServerPlayer player) {
        UUID playerId = player.getUUID();
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId) && currentTime < cooldowns.get(playerId)) {
            player.displayClientMessage(Component.translatable("message.mega_showdown.not_so_fast")
                    .withColor(0xFF0000), true);
            return false;
        }

        // Apply cooldown
        cooldowns.put(playerId, currentTime + COOLDOWN_TIME);
        return true;
    }

    public static void playFormeChangeAnimation(LivingEntity context) {
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.AMETHYST_BLOCK_CHIME, // Change this if needed
                    SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (100 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverLevel.sendParticles(
                        ParticleTypes.END_ROD, // Change this to any particle type
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

    public static void register() {
    }
}
