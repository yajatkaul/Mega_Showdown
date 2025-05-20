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
import com.cobblemon.yajatkaul.mega_showdown.datamanage.PokeHandler;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.*;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.*;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class ConfigResults {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 1000; // 1 sec

    public static boolean Possible(ServerPlayer player){
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

    public static void registerCustomShowdown(){
        Utils.GMAX_SPECIES.clear();
        Utils.addGmaxToMap();
        Utils.MEGA_POKEMONS.clear();
        Utils.addMegaList();
        Utils.MEGA_STONE_IDS.clear();
        Utils.loadMegaStoneIds();
        MegaCommands.VALID_ITEMS.clear();

        //MEGA
        for(MegaData pokemon: Utils.megaRegistry){
            //COMMAND UTILS
            MegaCommands.VALID_ITEMS.add(pokemon.msd_id());
            //

            Utils.MEGA_POKEMONS.add(pokemon.pokemon());
            String[] parts = pokemon.item_id().split(":");
            ResourceLocation custom_stone_item_id = ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]);
            Item customStone = BuiltInRegistries.ITEM.get(custom_stone_item_id);

            CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                if (stack.getItem().equals(customStone) &&
                        ((stack.get(DataComponents.CUSTOM_MODEL_DATA) != null &&
                        stack.get(DataComponents.CUSTOM_MODEL_DATA).value() == pokemon.custom_model_data())
                                || pokemon.custom_model_data() == 0)) {
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
            ResourceLocation custom_held_item_id = ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]);
            Item customHeldItem = BuiltInRegistries.ITEM.get(custom_held_item_id);

            CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                if (stack.getItem().equals(customHeldItem) &&
                        ((stack.get(DataComponents.CUSTOM_MODEL_DATA) != null &&
                        stack.get(DataComponents.CUSTOM_MODEL_DATA).value() == items.custom_model_data())
                                || items.custom_model_data() == 0)) {
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
                ResourceLocation custom_held_item_id = ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]);
                Item customHeldItem = BuiltInRegistries.ITEM.get(custom_held_item_id);

                CobblemonHeldItemManager.INSTANCE.registerStackRemap(stack -> {
                    if (stack.getItem().equals(customHeldItem) &&
                            ((stack.get(DataComponents.CUSTOM_MODEL_DATA) != null &&
                            stack.get(DataComponents.CUSTOM_MODEL_DATA).value() == items.custom_model_data())
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
        for(KeyItemData keyItem: Utils.keyItemsRegistry){
            MegaCommands.VALID_ITEMS.add(keyItem.msd_id());
        }
    }

    public static boolean useItem(Player player, Level level, InteractionHand hand, ItemStack itemStack) {
        if(level.isClientSide || player.isCrouching()){
            return false;
        }
        if (!itemStack.isEmpty()) {
            CustomModelData nbt = itemStack.get(DataComponents.CUSTOM_MODEL_DATA);
            for(FusionData fusion: Utils.fusionRegistry){
                Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(fusion.item_id()));
                if(itemStack.is(item) && ((nbt != null && fusion.custom_model_data() == nbt.value()) || fusion.custom_model_data() == 0)){
                    EntityHitResult entityHit = getEntityLookingAt(player, 4.5f);
                    if (entityHit == null) {
                        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);
                        PokeHandler currentValue = itemStack.getOrDefault(DataManage.POKEMON_STORAGE, null);

                        if(currentValue.getPokemon() != null){
                            playerPartyStore.add(currentValue.getPokemon());
                            itemStack.set(DataManage.POKEMON_STORAGE, null);
                            return true;
                        }

                        return false;
                    }
                        Entity context = entityHit.getEntity();

                        if (!(context instanceof PokemonEntity pk)) {
                            return false;
                        }

                        Pokemon pokemon = pk.getPokemon();
                        if (pokemon.getOwnerPlayer() != player || pokemon.getEntity() == null || pk.isBattling()) {
                            return false;
                        }

                        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);
                        Pokemon currentValue = null;
                        if(itemStack.get(DataManage.POKEMON_STORAGE) != null){
                            currentValue = itemStack.get(DataManage.POKEMON_STORAGE).getPokemon();
                        }

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
                                    return false;
                                }
                            }

                            if (itemStack.get(DataManage.POKEMON_STORAGE) != null) {
                                player.displayClientMessage(Component.translatable("message.mega_showdown.already_fused")
                                        .withColor(0xFF0000), true);
                                return false;
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

                            HashMap<UUID, Pokemon> map = player.getData(DataManage.DATA_MAP);
                            if(map == null){
                                map = new HashMap<>();
                            }
                            Pokemon toAdd = map.get(pokemon.getUuid());
                            playerPartyStore.add(toAdd);
                            map.remove(pokemon.getUuid());
                            player.setData(DataManage.DATA_MAP, map);

                            itemStack.set(DataManage.POKEMON_STORAGE, null);
                            itemStack.set(DataComponents.CUSTOM_NAME, Component.translatable(fusion.item_name() + "inactive"));
                            return true;
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
                                    return false;
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

                            HashMap<UUID, Pokemon> map = player.getData(DataManage.DATA_MAP);
                            if (map == null) {
                                map = new HashMap<>();
                            }
                            map.put(pokemon.getUuid(), currentValue);
                            player.setData(DataManage.DATA_MAP, map);

                            itemStack.set(DataManage.POKEMON_STORAGE, null);
                            itemStack.set(DataComponents.CUSTOM_NAME, Component.translatable(fusion.item_name() + "inactive"));
                            return true;
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
                                    return false;
                                }
                            }

                            itemStack.set(DataManage.POKEMON_STORAGE, new PokeHandler(pk.getPokemon()));
                            playerPartyStore.remove(pk.getPokemon());
                            itemStack.set(DataComponents.CUSTOM_NAME, Component.translatable(fusion.item_name() + "charged"));
                            return true;
                        }

                        player.setItemInHand(hand, itemStack);
                }
            }

            for(KeyItemData keyItems: Utils.keyItemsRegistry){
                Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(keyItems.item_id()));
                if(itemStack.is(item) && ((nbt != null && keyItems.custom_model_data() == nbt.value()) || keyItems.custom_model_data() == 0)) {
                    EntityHitResult entityHit = getEntityLookingAt(player, 4.5f);
                    if (entityHit != null) {
                        Entity context = entityHit.getEntity();

                        if (!(context instanceof PokemonEntity pk)) {
                            return false;
                        }

                        Pokemon pokemon = pk.getPokemon();
                        if (pokemon.getOwnerPlayer() != player || pokemon.getEntity() == null || pk.isBattling()) {
                            return false;
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
                                    return false;
                                }
                            }

                            if(!Possible((ServerPlayer) player)){
                                return false;
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
                                    particleEffect(pk, keyItems.effects(), false);
                                    if(keyItems.consume()){
                                        itemStack.shrink(1);
                                    }
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
                                        itemStack.shrink(1);
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
                                    itemStack.shrink(1);
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
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

    public static void particleEffect(LivingEntity context, EffectsData effects, boolean apply) {
        int amplifier = apply ? effects.particle_apply_amplifier() : effects.particle_revert_amplifier();

        if (context.level() instanceof ServerLevel serverLevel) {
            String[] partsParticle;
            String[] partsSound;

            if (apply) {
                partsParticle = effects.particle_apply().split(":");
                partsSound = effects.sound_apply().split(":");
            } else {
                partsParticle = effects.particle_revert().split(":");
                partsSound = effects.sound_revert().split(":");
            }

            ResourceLocation custom_particle_id = ResourceLocation.fromNamespaceAndPath(partsParticle[0], partsParticle[1]);
            ParticleType<?> particleType = BuiltInRegistries.PARTICLE_TYPE.get(custom_particle_id);

            ResourceLocation custom_sound_id = ResourceLocation.fromNamespaceAndPath(partsSound[0], partsSound[1]);
            SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.get(custom_sound_id);

            Vec3 entityPos = context.position();

            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();

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
                serverLevel.playSound(
                        null, entityPos.x, entityPos.y, entityPos.z,
                        soundEvent,
                        SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
                );
            }

            int baseParticleCount = (int) (100 * entityWidth * entityHeight);
            int particleCount = baseParticleCount * Math.max(1, amplifier); // Amplify particle count
            double radius = entityWidth * (0.8 + amplifier * 0.1); // Slightly increase radius with amplifier

            if (particleType instanceof ParticleOptions particle) {
                for (int i = 0; i < particleCount; i++) {
                    double angle = Math.random() * 2 * Math.PI;
                    double xOffset = Math.cos(angle) * radius;
                    double zOffset = Math.sin(angle) * radius;
                    double yOffset = Math.random() * entityHeight;

                    serverLevel.sendParticles(
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
                    MegaShowdown.LOGGER.error("A: Invalid Particle used for pokemon: {}, particle id: {}",
                            ((PokemonEntity) context).getPokemon().getSpecies().getName(),
                            effects.particle_apply());
                } else if (!apply && !effects.particle_revert().isEmpty()) {
                    MegaShowdown.LOGGER.error("R: Invalid Particle used for pokemon: {}, particle id: {}",
                            ((PokemonEntity) context).getPokemon().getSpecies().getName(),
                            effects.particle_revert());
                }
            }
        }
    }
}
