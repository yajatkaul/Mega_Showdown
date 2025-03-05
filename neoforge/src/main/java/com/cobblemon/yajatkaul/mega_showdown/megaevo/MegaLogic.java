package com.cobblemon.yajatkaul.mega_showdown.megaevo;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.MegaBraceletItem;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MegaLogic {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 2000; // 2 sec

    public static boolean Possible(ServerPlayer player, boolean fromBattle) {
        UUID playerId = player.getUUID();
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId) && currentTime < cooldowns.get(playerId) && !fromBattle) {
            player.displayClientMessage(Component.literal("Not so fast!")
                    .withColor(0xFF0000), true);
            return false;
        }

        boolean hasMegaItemCurios = CuriosApi.getCuriosInventory(player)
                .map(inventory -> inventory.isEquipped(stack -> stack.getItem() instanceof MegaBraceletItem))
                .orElse(false);

        boolean hasOffhandMegaItem = player.getOffhandItem().getItem() instanceof MegaBraceletItem;
        boolean hasMainhandMegaItem = player.getMainHandItem().getItem() instanceof MegaBraceletItem;

        if (!hasMegaItemCurios && !hasOffhandMegaItem && !hasMainhandMegaItem) {
            return false;
        }

        // Apply cooldown
        cooldowns.put(playerId, currentTime + COOLDOWN_TIME);
        return true;
    }

    public static void EvoLogic(Player playerContext){
        ServerPlayer player = (ServerPlayer) playerContext;

        if(Config.battleModeOnly){
            return;
        }

        double range = 5.0;

        // Check for entities first
        Vec3 startPos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 endPos = startPos.add(lookVec.multiply(range, range, range));

        AABB searchBox = new AABB(startPos, endPos).inflate(1.0);

        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                player.level(),
                player,
                startPos,
                endPos,
                searchBox,
                entity -> !entity.isSpectator() && entity.isPickable(),
                0.3f
        );

        if (entityHit == null) {
            return;
        }

        if(entityHit.getEntity() instanceof PokemonEntity pk){
            if(pk.level().isClientSide){
                return;
            }

            if(pk.getPokemon().getOwnerPlayer() != player || !Possible(player, false)){
                return;
            }

            List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

            boolean end = false;
            for (String key : megaKeys) {
                FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));
                FlagSpeciesFeature feature = featureProvider.get(pk.getPokemon());

                if(feature != null){
                    boolean enabled = featureProvider.get(pk.getPokemon()).getEnabled();

                    if(enabled){
                        Devolve(pk, player, false);
                        end = false;
                        break;
                    }else{
                        end = true;
                    }

                }
            }
            if(end){
                Evolve(pk, player, false);
            }
        }
    }

    public static void Evolve(LivingEntity context, Player player, Boolean fromBattle){
        if(context instanceof PokemonEntity pk){
            if(pk.getPokemon().getOwnerPlayer() != player){
                return;
            }
        }

        Pokemon pokemon = ((PokemonEntity) context).getPokemon();
        Species species = Utils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());

        if(context instanceof PokemonEntity pk && (pk.isBattling() && !fromBattle)){
            player.displayClientMessage(Component.literal("Not allowed in battle")
                    .withColor(0xFF0000), true);
            return;
        }
        
        if(pokemon.getSpecies().getName().equals(Utils.getSpecies("rayquaza").getName()) &&
                (!player.getData(DataManage.MEGA_DATA) || Config.multipleMegas)){
            if(Config.friendshipMode && pokemon.getFriendship() < 200 && !pokemon.getEntity().isBattling()){
                player.displayClientMessage(Component.literal("You are not close enough with your pokemon to mega outside")
                        .withColor(0xFF0000), true);
                return;
            }

            boolean found = false;
            for (int i = 0; i < 4; i++){
                if(pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")){
                    player.setData(DataManage.MEGA_POKEMON, pokemon);
                    player.setData(DataManage.MEGA_DATA, true);

                    playEvolveAnimation(context);

                    new FlagSpeciesFeature("mega", true).apply(pokemon);
                    AdvancementHelper.grantAdvancement((ServerPlayer) player, "mega_evolve");
                    found = true;
                }
            }
            if(!found){
                player.displayClientMessage(Component.literal("Rayquaza doesn't have dragonascent")
                        .withColor(0xFF0000), true);
            }
            return;
        }else if(pokemon.getSpecies().getName().equals(Utils.getSpecies("rayquaza").getName()) && player.getData(DataManage.MEGA_DATA)){
            player.displayClientMessage(Component.literal("You can only have one mega at a time")
                    .withColor(0xFF0000), true);
            return;
        }

        if(species == null){
            player.displayClientMessage(Component.literal("Don't have the correct stone")
                    .withColor(0xFF0000), true);
            return;
        }

        if(Config.friendshipMode && pokemon.getFriendship() < 200 && !pokemon.getEntity().isBattling()){
            player.displayClientMessage(Component.literal("You are not close enough with your pokemon to mega outside")
                    .withColor(0xFF0000), true);
            return;
        }

        if(species.getName().equals(pokemon.getSpecies().getName()) &&
                (!player.getData(DataManage.MEGA_DATA) || Config.multipleMegas)){
            if(species.getName().equals(Utils.getSpecies("charizard").getName())){
                if(pokemon.heldItem().is(MegaStones.CHARIZARDITE_X)){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, pokemon);

                    playEvolveAnimation(context);

                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                    AdvancementHelper.grantAdvancement((ServerPlayer) player, "mega_evolve");
                }else if(pokemon.heldItem().is(MegaStones.CHARIZARDITE_Y)){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, pokemon);

                    playEvolveAnimation(context);

                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                    AdvancementHelper.grantAdvancement((ServerPlayer) player, "mega_evolve");
                }
            }
            else if(species.getName().equals(Utils.getSpecies("mewtwo").getName())){
                if(pokemon.heldItem().is(MegaStones.MEWTWONITE_X)){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, pokemon);

                    playEvolveAnimation(context);

                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                    AdvancementHelper.grantAdvancement((ServerPlayer) player, "mega_evolve");
                }else if(pokemon.heldItem().is(MegaStones.MEWTWONITE_Y)){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, pokemon);

                    playEvolveAnimation(context);

                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                    AdvancementHelper.grantAdvancement((ServerPlayer) player, "mega_evolve");
                }
            }
            else{
                player.setData(DataManage.MEGA_DATA, true);
                player.setData(DataManage.MEGA_POKEMON, pokemon);

                new FlagSpeciesFeature("mega", true).apply(pokemon);

                playEvolveAnimation(context);

                AdvancementHelper.grantAdvancement((ServerPlayer) player, "mega_evolve");
            }
        }else if(species.getName().equals(pokemon.getSpecies().getName()) && player.getData(DataManage.MEGA_DATA)){
            player.displayClientMessage(Component.literal("You can only have one mega at a time")
                    .withColor(0xFF0000), true);
        }else{
            player.displayClientMessage(Component.literal("Don't have the correct stone")
                    .withColor(0xFF0000), true);
        }

    }

    public static void Devolve(LivingEntity context, Player player, Boolean fromBattle){
        if(player.level().isClientSide){
            return;
        }

        if(context instanceof PokemonEntity pk){
            if(pk.getPokemon().getOwnerPlayer() != player){
                return;
            }

            if(pk.isBattling() && !fromBattle){
                player.displayClientMessage(Component.literal("Not allowed in battle")
                        .withColor(0xFF0000), true);
                return;
            }
        }

        Pokemon pokemon = ((PokemonEntity) context).getPokemon();

        player.setData(DataManage.MEGA_DATA, false);
        player.setData(DataManage.MEGA_POKEMON, new Pokemon());

        playDevolveAnimation(context);

        new FlagSpeciesFeature("mega", false).apply(pokemon);
        new FlagSpeciesFeature("mega-x", false).apply(pokemon);
        new FlagSpeciesFeature("mega-y", false).apply(pokemon);
    }

    public static void playEvolveAnimation(LivingEntity context) {
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BEACON_ACTIVATE, // Change this if needed
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

    public static void playDevolveAnimation(LivingEntity context){
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BEACON_DEACTIVATE, // Change this if needed
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
}
