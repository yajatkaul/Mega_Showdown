package com.cobblemon.yajatkaul.mega_showdown.item.configActions;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.helditem.CobblemonHeldItemManager;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.commands.MegaCommands;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.*;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
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

            for(FusionData fusion: Utils.fusionRegistry){
                Item item = Registries.ITEM.get(Identifier.tryParse(fusion.item_id()));
                if(itemStack.isOf(item) && ((nbt != null && fusion.custom_model_data() == nbt.value()) || fusion.custom_model_data() == 0)){
                    EntityHitResult entityHit = getEntityLookingAt(player, 4.5);
                    if (entityHit == null) {
                        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);
                        Pokemon currentValue = itemStack.getOrDefault(DataManage.POKEMON_STORAGE, null);
                        if(currentValue != null){
                            playerPartyStore.add(currentValue);
                            itemStack.set(DataManage.POKEMON_STORAGE, null);
                        }

                        return TypedActionResult.pass(itemStack);
                    }
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

                    if (checkEnabled(fusion ,pokemon) && fusion.fusion_mon().contains(pokemon.getSpecies().getName())) {
                        if(!fusion.required_aspects_fusion_mon().isEmpty()){
                            List<String> aspectList = new ArrayList<>();
                            for (String aspects : fusion.required_aspects_fusion_mon()) {
                                String[] aspectDiv = aspects.split("=");
                                if(aspectDiv[1].equals("true") || aspectDiv[1].equals("false")){
                                    aspectList.add(aspectDiv[0]);
                                }else{
                                    aspectList.add(aspectDiv[1]);
                                }
                            }

                            boolean allMatch = true;
                            for (String requiredAspect : aspectList) {
                                boolean matched = false;
                                for (String pokemonAspect : pokemon.getAspects()) {
                                    if (pokemonAspect.startsWith(requiredAspect)) {
                                        matched = true;
                                        break;
                                    }
                                }
                                if (!matched) {
                                    allMatch = false;
                                    break;
                                }
                            }

                            if(!allMatch){
                                return TypedActionResult.pass(itemStack);
                            }
                        }

                        if (itemStack.get(DataManage.POKEMON_STORAGE) != null) {
                            player.sendMessage(
                                    Text.translatable("message.mega_showdown.already_fused").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                                    true
                            );
                            return TypedActionResult.success(itemStack);
                        }

                        particleEffect(pk, fusion.effects(), false);

                        for(String aspects: fusion.default_aspects()){
                            String[] aspectsDiv = aspects.split("=");
                            if(aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")){
                                new FlagSpeciesFeature(aspectsDiv[0],Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                            }else{
                                new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                            }
                        }
                        if(!fusion.tradable_form()){
                            setTradable(pokemon, true);
                        }

                        HashMap<UUID, Pokemon> map = player.getAttached(DataManage.DATA_MAP);
                        if(map == null){
                            map = new HashMap<>();
                        }
                        Pokemon toAdd = map.get(pokemon.getUuid());
                        playerPartyStore.add(toAdd);
                        map.remove(pokemon.getUuid());
                        player.setAttached(DataManage.DATA_MAP, map);

                        itemStack.set(DataManage.POKEMON_STORAGE, null);
                        itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(fusion.item_name() + ".inactive"));
                        return TypedActionResult.success(itemStack);
                    } else if (currentValue != null && fusion.fusion_mon().contains(pokemon.getSpecies().getName())) {
                        if(!fusion.required_aspects_fusion_mon().isEmpty()){
                            List<String> aspectList = new ArrayList<>();
                            for (String aspects : fusion.required_aspects_fusion_mon()) {
                                String[] aspectDiv = aspects.split("=");
                                if(aspectDiv[1].equals("true") || aspectDiv[1].equals("false")){
                                    aspectList.add(aspectDiv[0]);
                                }else{
                                    aspectList.add(aspectDiv[1]);
                                }
                            }

                            boolean allMatch = true;
                            for (String requiredAspect : aspectList) {
                                boolean matched = false;
                                for (String pokemonAspect : pokemon.getAspects()) {
                                    if (pokemonAspect.startsWith(requiredAspect)) {
                                        matched = true;
                                        break;
                                    }
                                }
                                if (!matched) {
                                    allMatch = false;
                                    break;
                                }
                            }

                            if(!allMatch){
                                return TypedActionResult.pass(itemStack);
                            }
                        }

                        for(String aspects: fusion.fusion_aspects()){
                            String[] aspectsDiv = aspects.split("=");
                            if(aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")){
                                new FlagSpeciesFeature(aspectsDiv[0],Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                            }else{
                                new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                            }
                        }
                        particleEffect(pk, fusion.effects(), true);

                        if(!fusion.tradable_form()){
                            setTradable(pokemon, false);
                        }

                        HashMap<UUID, Pokemon> map = player.getAttached(DataManage.DATA_MAP);
                        if (map == null) {
                            map = new HashMap<>();
                        }
                        map.put(pokemon.getUuid(), currentValue);
                        player.setAttached(DataManage.DATA_MAP, map);

                        itemStack.set(DataManage.POKEMON_STORAGE, null);
                        itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(fusion.item_name() + ".inactive"));
                        return TypedActionResult.success(itemStack);
                    } else if (currentValue == null && fusion.fuse_with_mon().contains(pokemon.getSpecies().getName())) {
                        if(!fusion.required_aspects_fuse_with_mon().isEmpty()){
                            List<String> aspectList = new ArrayList<>();
                            for (String aspects : fusion.required_aspects_fuse_with_mon()) {
                                String[] aspectDiv = aspects.split("=");
                                if(aspectDiv[1].equals("true") || aspectDiv[1].equals("false")){
                                    aspectList.add(aspectDiv[0]);
                                }else{
                                    aspectList.add(aspectDiv[1]);
                                }
                            }

                            boolean allMatch = true;
                            for (String requiredAspect : aspectList) {
                                boolean matched = false;
                                for (String pokemonAspect : pokemon.getAspects()) {
                                    if (pokemonAspect.startsWith(requiredAspect)) {
                                        matched = true;
                                        break;
                                    }
                                }
                                if (!matched) {
                                    allMatch = false;
                                    break;
                                }
                            }

                            if(!allMatch){
                                return TypedActionResult.pass(itemStack);
                            }
                        }

                        itemStack.set(DataManage.POKEMON_STORAGE, pk.getPokemon());
                        playerPartyStore.remove(pk.getPokemon());
                        itemStack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable(fusion.item_name() + ".charged"));
                        return TypedActionResult.success(itemStack);
                    }

                    player.setStackInHand(hand, itemStack);

                }
            }

            for(KeyItemData keyItems: Utils.keyItemsRegistry){
                Item item = Registries.ITEM.get(Identifier.tryParse(keyItems.item_id()));
                if(itemStack.isOf(item) && ((nbt != null && keyItems.custom_model_data() == nbt.value()) || keyItems.custom_model_data() == 0)) {
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

                        if(keyItems.pokemons().contains(pokemon.getSpecies().getName())){
                            if(!keyItems.required_aspects().isEmpty()){
                                List<String> aspectList = new ArrayList<>();
                                for (String aspects : keyItems.required_aspects()) {
                                    String[] aspectDiv = aspects.split("=");
                                    if(aspectDiv[1].equals("true") || aspectDiv[1].equals("false")){
                                        aspectList.add(aspectDiv[0]);
                                    }else{
                                        aspectList.add(aspectDiv[1]);
                                    }
                                }

                                boolean allMatch = true;
                                for (String requiredAspect : aspectList) {
                                    boolean matched = false;
                                    for (String pokemonAspect : pokemon.getAspects()) {
                                        if (pokemonAspect.startsWith(requiredAspect)) {
                                            matched = true;
                                            break;
                                        }
                                    }
                                    if (!matched) {
                                        allMatch = false;
                                        break;
                                    }
                                }

                                if(!allMatch){
                                    return TypedActionResult.pass(itemStack);
                                }
                            }

                            if(!Possible((ServerPlayerEntity) player)){
                                return TypedActionResult.pass(itemStack);
                            }

                            if (keyItems.toggle_aspects().isEmpty()) {
                                List<String> aspectList = new ArrayList<>();
                                for (String aspects : keyItems.aspects()) {
                                    String[] aspectDiv = aspects.split("=");
                                    if(aspectDiv[1].equals("true") || aspectDiv[1].equals("false")){
                                        aspectList.add(aspectDiv[0]);
                                    }else{
                                        aspectList.add(aspectDiv[1]);
                                    }
                                }

                                boolean allMatch = true;
                                for (String requiredAspect : aspectList) {
                                    boolean matched = false;
                                    for (String pokemonAspect : pokemon.getAspects()) {
                                        if (pokemonAspect.startsWith(requiredAspect)) {
                                            matched = true;
                                            break;
                                        }
                                    }
                                    if (!matched) {
                                        allMatch = false;
                                        break;
                                    }
                                }

                                if (allMatch) {
                                    for (String aspects : keyItems.default_aspects()) {
                                        String[] aspectsDiv = aspects.split("=");
                                        if (aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")) {
                                            new FlagSpeciesFeature(aspectsDiv[0], Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                                        } else {
                                            new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                                        }
                                        if(!keyItems.tradable_form()){
                                            setTradable(pokemon, true);
                                        }
                                    }
                                    if(keyItems.consume()){
                                        itemStack.decrement(1);
                                    }
                                    particleEffect(pk, keyItems.effects(), false);
                                }else{
                                    for(String aspects: keyItems.aspects()){
                                        String[] aspectsDiv = aspects.split("=");
                                        if(aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")){
                                            new FlagSpeciesFeature(aspectsDiv[0],Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                                        }else{
                                            new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                                        }
                                        if(!keyItems.tradable_form()){
                                            setTradable(pokemon, false);
                                        }
                                    }
                                    particleEffect(pk, keyItems.effects(), true);
                                    if(keyItems.consume()){
                                        itemStack.decrement(1);
                                    }
                                }
                            }else{
                                int currentIndex = -1;

                                List<String> currentAspects = pokemon.getAspects().stream()
                                        .map(String::toLowerCase)
                                        .toList();

                                for (int i = 0; i < keyItems.toggle_aspects().size(); i++) {
                                    List<String> sublist = keyItems.toggle_aspects().get(i);
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

                                int nextIndex = (currentIndex + 1) % keyItems.toggle_aspects().size();
                                List<String> nextAspects = keyItems.toggle_aspects().get(nextIndex);

                                for (String aspect : nextAspects) {
                                    String[] parts = aspect.split("=");
                                    String key = parts[0];
                                    String value = parts[1];

                                    if (value.equals("true") || value.equals("false")) {
                                        new FlagSpeciesFeature(key, Boolean.parseBoolean(value)).apply(pokemon);
                                    } else {
                                        new StringSpeciesFeature(key, value).apply(pokemon);
                                    }
                                }

                                particleEffect(pk, keyItems.effects(), true);
                                setTradable(pokemon, !keyItems.tradable_form());
                                if(keyItems.consume()){
                                    itemStack.decrement(1);
                                }
                            }
                        }
                    }
                }
            }
        }

        return TypedActionResult.pass(itemStack);
    }

    public static void registerCustomShowdown(){
        Utils.GMAX_SPECIES.clear();
        Utils.addGmaxToMap();
        Utils.MEGA_POKEMONS.clear();
        Utils.addMegaList();
        Utils.MEGA_STONE_IDS.clear();
        Utils.loadMegaStoneIds();
        MegaCommands.VALID_ITEMS.clear();

        //MEGAW
        for(MegaData pokemon: Utils.megaRegistry){
            //COMMAND UTILS
            MegaCommands.VALID_ITEMS.add(pokemon.msd_id());
            //

            Utils.MEGA_POKEMONS.add(pokemon.pokemon());
            String[] parts = pokemon.item_id().split(":");
            Identifier custom_stone_item_id = Identifier.of(parts[0], parts[1]);
            Item customStone = Registries.ITEM.get(custom_stone_item_id);

            CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                if (stack.getItem().equals(customStone) &&
                        ((stack.get(DataComponentTypes.CUSTOM_MODEL_DATA) != null &&
                        stack.get(DataComponentTypes.CUSTOM_MODEL_DATA).value() == pokemon.custom_model_data()) ||
                                pokemon.custom_model_data() == 0)) {
                    return pokemon.showdown_id();
                }
                return null;
            });
        }

        //HELD ITEMS
        for(HeldItemData items: Utils.heldItemsRegistry){
            //COMMAND UTILS
            MegaCommands.VALID_ITEMS.add(items.msd_id());
            //

            String[] parts = items.item_id().split(":");
            Identifier custom_held_item_id = Identifier.of(parts[0], parts[1]);
            Item customHeldItem = Registries.ITEM.get(custom_held_item_id);

            CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                if (stack.getItem().equals(customHeldItem) &&
                        ((stack.get(DataComponentTypes.CUSTOM_MODEL_DATA) != null &&
                        stack.get(DataComponentTypes.CUSTOM_MODEL_DATA).value() == items.custom_model_data()) || items.custom_model_data() == 0)) {
                    return items.showdown_id();
                }
                return null;
            });
        }

        //BATTLE ONLY FORME CHANGE
        for(FormChangeData items: Utils.formChangeRegistry){
            //COMMAND UTILS
            MegaCommands.VALID_ITEMS.add(items.msd_id());
            //

            if(items.battle_mode_only()){
                String[] parts = items.item_id().split(":");
                Identifier custom_held_item_id = Identifier.of(parts[0], parts[1]);
                Item customHeldItem = Registries.ITEM.get(custom_held_item_id);

                CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                    if (stack.getItem().equals(customHeldItem) &&
                            ((stack.get(DataComponentTypes.CUSTOM_MODEL_DATA) != null &&
                            stack.get(DataComponentTypes.CUSTOM_MODEL_DATA).value() == items.custom_model_data())
                                    || items.custom_model_data() == 0)) {
                        return items.showdown_id();
                    }
                    return null;
                });
            }
        }

        //GMAX
        for(GmaxData pokemon: Utils.gmaxRegistry){
            Utils.GMAX_SPECIES.add(pokemon.pokemon());
        }

        //FUSIONS
        for(FusionData fusion: Utils.fusionRegistry){
            MegaCommands.VALID_ITEMS.add(fusion.msd_id());
        }

        //KEY ITEMS
        for(KeyItemData keyItems: Utils.keyItemsRegistry){
            MegaCommands.VALID_ITEMS.add(keyItems.msd_id());
        }
    }


    //Helpers
    private static boolean checkEnabled(FusionData fusion, Pokemon pk){
        for(String aspects: fusion.fusion_aspects()){
            String[] aspectsDiv = aspects.split("=");
            if(aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")){
                if(pk.getAspects().contains(aspectsDiv[0])) return true;
            }else{
                for (String aspect : pk.getAspects()) {
                    if (aspect.startsWith(aspectsDiv[1])) return true;
                }
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

    public static void particleEffect(LivingEntity context, EffectsData effects, boolean apply) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            int amplifier = apply ? effects.particle_apply_amplifier() : effects.particle_revert_amplifier();

            String[] partsParticle;
            String[] partsSound;

            if (apply) {
                partsParticle = effects.particle_apply().split(":");
                partsSound = effects.sound_apply().split(":");
            } else {
                partsParticle = effects.particle_revert().split(":");
                partsSound = effects.sound_revert().split(":");
            }

            Identifier custom_particle_id = Identifier.of(partsParticle[0], partsParticle[1]);
            ParticleType<?> particleType = Registries.PARTICLE_TYPE.get(custom_particle_id);

            Identifier custom_sound_id = Identifier.of(partsSound[0], partsSound[1]);
            SoundEvent soundEvent = Registries.SOUND_EVENT.get(custom_sound_id);

            Vec3d entityPos = context.getPos();

            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            double scaleFactor = apply ? effects.particle_apply_amplifier() : effects.particle_revert_amplifier();
            double ampFactor = Math.max(1, amplifier); // Ensure amplifier is at least 1

            double adjustedWidth = entityWidth * scaleFactor * ampFactor;
            double adjustedHeight = entityHeight * scaleFactor * ampFactor;
            double adjustedDepth = entityWidth * scaleFactor * ampFactor;

            if (soundEvent == null) {
                if (apply && !effects.sound_apply().isEmpty()) {
                    MegaShowdown.LOGGER.error("A: Invalid Sound used for pokemon: {}, sound id: {}",
                            ((PokemonEntity) context).getPokemon().getSpecies().getName(),
                            effects.sound_apply());
                } else if (!apply && !effects.sound_revert().isEmpty()) {
                    MegaShowdown.LOGGER.error("R: Invalid Sound used for pokemon: {}, sound id: {}",
                            ((PokemonEntity) context).getPokemon().getSpecies().getName(),
                            effects.sound_revert());
                }
            } else {
                serverWorld.playSound(
                        null, entityPos.x, entityPos.y, entityPos.z,
                        soundEvent,
                        SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
                );
            }

            int particleCount = (int) (175 * adjustedWidth * adjustedHeight);

            if (particleType instanceof ParticleEffect particle) {
                for (int i = 0; i < particleCount; i++) {
                    double xOffset = (Math.random() - 0.5) * adjustedWidth;
                    double yOffset = Math.random() * adjustedHeight;
                    double zOffset = (Math.random() - 0.5) * adjustedDepth;

                    serverWorld.spawnParticles(
                            particle,
                            entityPos.x + xOffset,
                            entityPos.y + yOffset,
                            entityPos.z + zOffset,
                            1,
                            0, 0, 0,
                            0.1
                    );
                }
            } else {
                if (apply && !effects.particle_apply().isEmpty()) {
                    MegaShowdown.LOGGER.error("A: Invalid Particle used for pokemon: {}, sound id: {}",
                            ((PokemonEntity) context).getPokemon().getSpecies().getName(),
                            effects.particle_apply());
                } else if (!apply && !effects.particle_revert().isEmpty()) {
                    MegaShowdown.LOGGER.error("R: Invalid Particle used for pokemon: {}, sound id: {}",
                            ((PokemonEntity) context).getPokemon().getSpecies().getName(),
                            effects.particle_revert());
                }
            }
        }
    }
}
