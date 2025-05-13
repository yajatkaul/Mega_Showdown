package com.cobblemon.yajatkaul.mega_showdown.item.configActions;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.helditem.CobblemonHeldItemManager;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownCustomsConfig;
import com.cobblemon.yajatkaul.mega_showdown.config.Structure.*;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.utility.ModTags;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class ConfigResults {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 1000; // 1 sec

    public static boolean Possible(ServerPlayerEntity player){
        UUID playerId = player.getUuid();
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId) && currentTime < cooldowns.get(playerId)) {
            player.sendMessage(
                    Text.translatable("message.mega_showdown.not_so_fast").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return false;
        }

        // Apply cooldown
        cooldowns.put(playerId, currentTime + COOLDOWN_TIME);
        return true;
    }

    public static TypedActionResult<ItemStack> useItem(PlayerEntity player, World world, Hand hand){
        ItemStack itemStack = player.getStackInHand(hand);
        if(world.isClient || player.isCrawling()){
            return TypedActionResult.pass(itemStack);
        }
        if (!itemStack.isEmpty()) {
            CustomModelDataComponent nbt = itemStack.get(DataComponentTypes.CUSTOM_MODEL_DATA);
            for(Fusion fusion: ShowdownCustomsConfig.fusionItems){
                if(nbt != null && fusion.custom_model_data == nbt.value()){

                    EntityHitResult entityHit = getEntityLookingAt(player, 4.5);
                    if (entityHit != null) {
                        Entity context = entityHit.getEntity();

                        if (!(context instanceof PokemonEntity pk)) {
                            return TypedActionResult.pass(itemStack);
                        }

                        Pokemon pokemon = pk.getPokemon();
                        if (pokemon.getOwnerPlayer() != player || pokemon.getEntity() == null || pk.isBattling()) {
                            return TypedActionResult.pass(itemStack);
                        }

                        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);
                        Pokemon currentValue = itemStack.getOrDefault(DataManage.POKEMON_STORAGE, null);

                        if (checkEnabled(fusion ,pokemon) && fusion.fusionMon.contains(pokemon.getSpecies().getName())) {
                            if (itemStack.get(DataManage.POKEMON_STORAGE) != null) {
                                player.sendMessage(
                                        Text.translatable("message.mega_showdown.already_fused").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                                        true
                                );
                                return TypedActionResult.success(itemStack);
                            }
                            particleEffect(pk, ParticleTypes.END_ROD);

                            for(String aspects: fusion.defaultAspects){
                                String[] aspectsDiv = aspects.split("=");
                                if(aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")){
                                    new FlagSpeciesFeature(aspectsDiv[0],Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                                }else{
                                    new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                                }
                            }
                            setTradable(pokemon, true);

                            HashMap<UUID, Pokemon> map = player.getAttached(DataManage.DATA_MAP);
                            if(map == null){
                                map = new HashMap<>();
                            }
                            Pokemon toAdd = map.get(pokemon.getUuid());
                            playerPartyStore.add(toAdd);
                            map.remove(pokemon.getUuid());
                            player.setAttached(DataManage.DATA_MAP, map);

                            itemStack.set(DataManage.POKEMON_STORAGE, null);
                            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.custom.inactive"));
                            return TypedActionResult.success(itemStack);
                        } else if (currentValue != null && fusion.fusionMon.contains(pokemon.getSpecies().getName())) {
                            for(String aspects: fusion.fusionAspects){
                                String[] aspectsDiv = aspects.split("=");
                                if(aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")){
                                    new FlagSpeciesFeature(aspectsDiv[0],Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                                }else{
                                    new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                                }
                            }
                            particleEffect(pk, ParticleTypes.SMOKE);

                            setTradable(pokemon, false);

                            HashMap<UUID, Pokemon> map = player.getAttached(DataManage.DATA_MAP);
                            if (map == null) {
                                map = new HashMap<>();
                            }
                            map.put(pokemon.getUuid(), currentValue);
                            player.setAttached(DataManage.DATA_MAP, map);

                            itemStack.set(DataManage.POKEMON_STORAGE, null);
                            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.reins_of_unity.inactive"));
                            return TypedActionResult.success(itemStack);
                        } else if (currentValue == null && fusion.fuseWithMon.contains(pokemon.getSpecies().getName())) {
                            itemStack.set(DataManage.POKEMON_STORAGE, pk.getPokemon());
                            playerPartyStore.remove(pk.getPokemon());
                            itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.reins_of_unity.charged"));
                            return TypedActionResult.success(itemStack);
                        }

                        player.setStackInHand(hand, itemStack);
                    }
                }
            }

            for(KeyItems keyItems: ShowdownCustomsConfig.keyItems){
                if(nbt != null && keyItems.custom_model_data == nbt.value()) {
                    EntityHitResult entityHit = getEntityLookingAt(player, 4.5);
                    if (entityHit != null) {
                        Entity context = entityHit.getEntity();

                        if (!(context instanceof PokemonEntity pk)) {
                            return TypedActionResult.pass(itemStack);
                        }

                        Pokemon pokemon = pk.getPokemon();
                        if (pokemon.getOwnerPlayer() != player || pokemon.getEntity() == null || pk.isBattling()) {
                            return TypedActionResult.pass(itemStack);
                        }

                        if(keyItems.pokemons.contains(pokemon.getSpecies().getName())){
                            if(!Possible((ServerPlayerEntity) player)){
                                return TypedActionResult.pass(itemStack);
                            }
                            if(keyItems.toggleAspects.isEmpty()){
                                List<String> aspectList = new ArrayList<>(List.of());
                                for(String aspects: keyItems.aspects){
                                    aspectList.add(aspects.split("=")[1]);
                                }
                                if(pokemon.getAspects().containsAll(aspectList)){
                                    for(String aspects: keyItems.defaultAspects){
                                        String[] aspectsDiv = aspects.split("=");
                                        if(aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")){
                                            new FlagSpeciesFeature(aspectsDiv[0],Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                                        }else{
                                            new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                                        }
                                        particleEffect(pk, ParticleTypes.END_ROD);
                                    }
                                }else{
                                    for(String aspects: keyItems.aspects){
                                        String[] aspectsDiv = aspects.split("=");
                                        if(aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")){
                                            new FlagSpeciesFeature(aspectsDiv[0],Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                                        }else{
                                            new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                                        }
                                        particleEffect(pk, ParticleTypes.END_ROD);
                                    }
                                }
                            }else{
                                int currentIndex = -1;

                                List<String> currentAspects = pokemon.getAspects().stream()
                                        .map(String::toLowerCase)
                                        .toList();

                                for (int i = 0; i < keyItems.toggleAspects.size(); i++) {
                                    List<String> sublist = keyItems.toggleAspects.get(i);
                                    for (String aspect : sublist) {
                                        String value = aspect.split("=")[1].toLowerCase();

                                        for (String current : currentAspects) {
                                            if (current.contains(value) || value.contains(current)) {
                                                currentIndex = i;
                                                break;
                                            }
                                        }
                                        if (currentIndex != -1) break;
                                    }
                                    if (currentIndex != -1) break;
                                }

                                int nextIndex = (currentIndex + 1) % keyItems.toggleAspects.size();
                                List<String> nextAspects = keyItems.toggleAspects.get(nextIndex);
                                MegaShowdown.LOGGER.info(String.valueOf(nextAspects));

                                for (String aspect : nextAspects) {
                                    MegaShowdown.LOGGER.info(aspect);
                                    String[] parts = aspect.split("=");
                                    String key = parts[0];
                                    String value = parts[1];

                                    if (value.equals("true") || value.equals("false")) {
                                        new FlagSpeciesFeature(key, Boolean.parseBoolean(value)).apply(pokemon);
                                    } else {
                                        new StringSpeciesFeature(key, value).apply(pokemon);
                                    }
                                }

                                particleEffect(pk, ParticleTypes.END_ROD);
                            }
                        }
                    }
                }
            }
        }

        return TypedActionResult.pass(itemStack);
    }

    public static void registerCustomShowdown(){
        //MEGA
        for(MegaItem pokemon: ShowdownCustomsConfig.megaItems){
            Utils.MEGA_POKEMONS.add(pokemon.pokemon);
            String[] parts = pokemon.item_id.split(":");
            Identifier custom_stone_item_id = Identifier.of(parts[0], parts[1]);
            Item customStone = Registries.ITEM.get(custom_stone_item_id);

            CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                if (stack.getItem().equals(customStone) &&
                        stack.get(DataComponentTypes.CUSTOM_MODEL_DATA) != null &&
                        stack.get(DataComponentTypes.CUSTOM_MODEL_DATA).value() == pokemon.custom_model_data) {
                    return pokemon.showdown_id;
                }
                return null;
            });
        }

        //HELD ITEMS
        for(HeldItem items: ShowdownCustomsConfig.heldItems){
            String[] parts = items.item_id.split(":");
            Identifier custom_held_item_id = Identifier.of(parts[0], parts[1]);
            Item customHeldItem = Registries.ITEM.get(custom_held_item_id);
            ItemStack heldItemStack = customHeldItem.getDefaultStack();
            heldItemStack.set(DataComponentTypes.CUSTOM_MODEL_DATA,
                    new CustomModelDataComponent(items.custom_model_data));

            CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                if (stack.getItem().equals(heldItemStack.getItem()) &&
                        stack.get(DataComponentTypes.CUSTOM_MODEL_DATA) != null &&
                        stack.get(DataComponentTypes.CUSTOM_MODEL_DATA).value() == heldItemStack.get(DataComponentTypes.CUSTOM_MODEL_DATA).value()) {
                    return items.showdown_id;
                }
                return null;
            });
        }

        //BATTLE ONLY FORME CHANGE
        for(FormeChange items: ShowdownCustomsConfig.formeChange){
            if(items.battleModeOnly){
                String[] parts = items.item_id.split(":");
                Identifier custom_held_item_id = Identifier.of(parts[0], parts[1]);
                Item customHeldItem = Registries.ITEM.get(custom_held_item_id);

                CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                    MegaShowdown.LOGGER.info(String.valueOf(stack.getItem().getName()));
                    if (stack.getItem().equals(customHeldItem) &&
                            stack.get(DataComponentTypes.CUSTOM_MODEL_DATA) != null &&
                            stack.get(DataComponentTypes.CUSTOM_MODEL_DATA).value() == items.custom_model_data) {
                        return items.showdown_id;
                    }
                    return null;
                });
            }
        }

        //GMAX
        for(Gmax pokemon: ShowdownCustomsConfig.gmax){
            Utils.GMAX_SPECIES.add(pokemon.pokemon);
        }
    }



    //Helpers
    private static boolean checkEnabled(Fusion fusion, Pokemon pk){
        for(String aspects: fusion.fusionAspects){
            String[] aspectsDiv = aspects.split("=");
            if(aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")){
                if(pk.getAspects().contains(aspectsDiv[0])) return true;
            }else{
                if(pk.getAspects().contains(aspectsDiv[1])) return true;
            }
        }
        return false;
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
}
