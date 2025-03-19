package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.registerItem;

public class RotomFormes {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 2000; // 2 sec

    public static boolean possible(ServerPlayerEntity player) {
        UUID playerId = player.getUuid();
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId) && currentTime < cooldowns.get(playerId)) {
            player.sendMessage(
                    Text.literal("Not so fast!").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return false;
        }

        // Apply cooldown
        cooldowns.put(playerId, currentTime + COOLDOWN_TIME);
        return true;
    }

    public static final Item FAN = registerItem("fanunit", new Item(new Item.Settings()){
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity context, Hand hand) {
            if(context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == player && !pk.isBattling()){
                Pokemon pokemon = pk.getPokemon();
                if(pokemon.getSpecies().getName().equals("Rotom")){
                    if(!possible((ServerPlayerEntity) player)){
                        return ActionResult.PASS;
                    }

                    playFormeChangeAnimation(pk);

                    if(pokemon.getAspects().contains("fan-appliance")){
                        new StringSpeciesFeature("appliance","none").apply(pk);
                        return ActionResult.SUCCESS;
                    }

                    new StringSpeciesFeature("appliance","fan").apply(pk);
                    return ActionResult.SUCCESS;
                }
            }

            return super.useOnEntity(stack, player, context, hand);
        }
    });

    public static final Item FRIDGEUNIT = registerItem("fridgeunit", new Item(new Item.Settings()){
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity context, Hand hand) {
            if(context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == player && !pk.isBattling()){
                Pokemon pokemon = pk.getPokemon();
                if(pokemon.getSpecies().getName().equals("Rotom")){
                    if(!possible((ServerPlayerEntity) player)){
                        return ActionResult.PASS;
                    }

                    playFormeChangeAnimation(pk);

                    if(pokemon.getAspects().contains("frost-appliance")){
                        new StringSpeciesFeature("appliance","none").apply(pk);
                        return ActionResult.SUCCESS;
                    }

                    new StringSpeciesFeature("appliance","frost").apply(pk);
                    return ActionResult.SUCCESS;
                }
            }

            return super.useOnEntity(stack, player, context, hand);
        }
    });

    public static final Item MOWUNIT = registerItem("mowunit", new Item(new Item.Settings()){
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity context, Hand hand) {
            if(context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == player && !pk.isBattling()){
                Pokemon pokemon = pk.getPokemon();
                if(pokemon.getSpecies().getName().equals("Rotom")){
                    if(!possible((ServerPlayerEntity) player)){
                        return ActionResult.PASS;
                    }

                    playFormeChangeAnimation(pk);

                    if(pokemon.getAspects().contains("mow-appliance")){
                        new StringSpeciesFeature("appliance","none").apply(pk);
                        return ActionResult.SUCCESS;
                    }

                    new StringSpeciesFeature("appliance","mow").apply(pk);
                    return ActionResult.SUCCESS;
                }
            }

            return super.useOnEntity(stack, player, context, hand);
        }
    });

    public static final Item OVENUNIT = registerItem("ovenunit", new Item(new Item.Settings()){
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity context, Hand hand) {
            if(context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == player && !pk.isBattling()){
                Pokemon pokemon = pk.getPokemon();
                if(pokemon.getSpecies().getName().equals("Rotom")){
                    if(!possible((ServerPlayerEntity) player)){
                        return ActionResult.PASS;
                    }

                    playFormeChangeAnimation(pk);

                    if(pokemon.getAspects().contains("heat-appliance")){
                        new StringSpeciesFeature("appliance","none").apply(pk);
                        return ActionResult.SUCCESS;
                    }

                    new StringSpeciesFeature("appliance","heat").apply(pk);
                    return ActionResult.SUCCESS;
                }
            }

            return super.useOnEntity(stack, player, context, hand);
        }
    });

    public static final Item WASHUNIT = registerItem("washunit", new Item(new Item.Settings()){
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity context, Hand hand) {
            if(context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == player && !pk.isBattling()){
                Pokemon pokemon = pk.getPokemon();
                if(pokemon.getSpecies().getName().equals("Rotom")){
                    if(!possible((ServerPlayerEntity) player)){
                        return ActionResult.PASS;
                    }

                    playFormeChangeAnimation(pk);

                    if(pokemon.getAspects().contains("wash-appliance")){
                        new StringSpeciesFeature("appliance","none").apply(pk);
                        return ActionResult.SUCCESS;
                    }

                    new StringSpeciesFeature("appliance","wash").apply(pk);
                    return ActionResult.SUCCESS;
                }
            }

            return super.useOnEntity(stack, player, context, hand);
        }
    });

    public static final Item ROTOM_CATALOGUE = registerItem("rotom_catalogue", new Item(new Item.Settings()){
        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity context, Hand hand) {
            if (context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == player && !pk.isBattling()) {
                Pokemon pokemon = pk.getPokemon();
                if (pokemon.getSpecies().getName().equals("Rotom")) {

                    if(!possible((ServerPlayerEntity) player)){
                        return ActionResult.PASS;
                    }

                    playFormeChangeAnimation(pk);

                    int currentPage = stack.getOrDefault(DataManage.CATALOGUE_PAGE, 1);

                    // Apply new form based on page
                    switch (currentPage) {
                        case 1:
                            new StringSpeciesFeature("appliance", "heat").apply(pk);
                            break;
                        case 2:
                            new StringSpeciesFeature("appliance", "fan").apply(pk);
                            break;
                        case 3:
                            new StringSpeciesFeature("appliance", "mow").apply(pk);
                            break;
                        case 4:
                            new StringSpeciesFeature("appliance", "frost").apply(pk);
                            break;
                        case 5:
                            new StringSpeciesFeature("appliance", "wash").apply(pk);
                            break;
                        default:
                            new StringSpeciesFeature("appliance", "none").apply(pk); // Fallback
                            break;
                    }

                    int nextPage = currentPage + 1;
                    if(nextPage == 7){
                        nextPage = 1;
                    }
                    stack.set(DataManage.CATALOGUE_PAGE, nextPage);
                    player.setStackInHand(hand, stack);

                    return ActionResult.SUCCESS;
                }
            }

            return super.useOnEntity(stack, player, context, hand);
        }
    });

    public static void playFormeChangeAnimation(LivingEntity context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, // Yarn mapping for BEACON_ACTIVATE
                    SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (100 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverWorld.spawnParticles(
                        ParticleTypes.END_ROD, // Same particle type
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

    public static void register(){
    }
}
