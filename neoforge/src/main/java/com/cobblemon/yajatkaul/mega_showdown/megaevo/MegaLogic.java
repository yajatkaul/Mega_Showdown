package com.cobblemon.yajatkaul.mega_showdown.megaevo;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.PokeHandler;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.MegaBraceletItem;
import com.cobblemon.yajatkaul.mega_showdown.utility.ModTags;
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

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class MegaLogic {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 2000; // 2 sec

    public static boolean Possible(ServerPlayer player, boolean fromBattle) {
        UUID playerId = player.getUUID();
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId) && currentTime < cooldowns.get(playerId) && !fromBattle) {
            player.displayClientMessage(Component.translatable("message.mega_showdown.not_so_fast")
                    .withColor(0xFF0000), true);
            return false;
        }

        boolean hasMegaItemCurios = CuriosApi.getCuriosInventory(player)
                .map(inventory -> inventory.isEquipped(stack -> (stack.is(ModTags.Items.MEGA_BRACELETS))))
                .orElse(false);

        boolean hasOffhandMegaItem = player.getOffhandItem().is(ModTags.Items.MEGA_BRACELETS);
        boolean hasMainhandMegaItem = player.getMainHandItem().is(ModTags.Items.MEGA_BRACELETS);

        if(fromBattle){
            if (!hasMegaItemCurios && !hasOffhandMegaItem) {
                return false;
            }
        }else {
            if (!hasMegaItemCurios && !hasOffhandMegaItem && !hasMainhandMegaItem) {
                return false;
            }
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

            if(!Config.mega || pk.getPokemon().getOwnerPlayer() != player || (!Utils.MEGA_POKEMONS.contains(pk.getPokemon().getSpecies().getName()) && !pk.getPokemon().getSpecies().getName().equals("Rayquaza")) || !Possible(player, false)){
                return;
            }

            if(pk.getAspects().contains("mega_x") || pk.getAspects().contains("mega") || pk.getAspects().contains("mega_y")){
                Devolve(pk.getPokemon(), false);
            }else {
                Evolve(pk, player, false);
            }
        }
    }

    public static void Evolve(PokemonEntity context, Player player, Boolean fromBattle){
        if(context.getPokemon().getOwnerPlayer() != player){
            return;
        }

        Pokemon pokemon = context.getPokemon();
        String species = Utils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());

        if(context instanceof PokemonEntity pk && (pk.isBattling() && !fromBattle)){
            player.displayClientMessage(Component.translatable("message.mega_showdown.battle_not_allowed")
                    .withColor(0xFF0000), true);
            return;
        }

        if(pokemon.getSpecies().getName().equals("Rayquaza") &&
                (!player.getData(DataManage.MEGA_DATA) || Config.multipleMegas)){
            if(Config.friendshipMode && pokemon.getFriendship() < 200 && !pokemon.getEntity().isBattling()){
                player.displayClientMessage(Component.translatable("message.mega_showdown.bond_not_close_mega")
                        .withColor(0xFF0000), true);
                return;
            }

            boolean found = false;
            for (int i = 0; i < 4; i++){
                if (pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")) {
                    player.setData(DataManage.MEGA_POKEMON, new PokeHandler(pokemon)); // ✅ Wrap in PokemonRef
                    player.setData(DataManage.MEGA_DATA, true);

                    playEvolveAnimation(context);

                    new StringSpeciesFeature("mega_evolution", "mega").apply(pokemon);
                    setTradable(pokemon, false);
                    found = true;
                }
            }
            if(!found){
                player.displayClientMessage(Component.translatable("message.mega_showdown.rayquaza_no_dragonascent")
                        .withColor(0xFF0000), true);
            }
            return;
        }else if(pokemon.getSpecies().getName().equals("Rayquaza") && player.getData(DataManage.MEGA_DATA)){
            player.displayClientMessage(Component.translatable("message.mega_showdown.mega_limit")
                    .withColor(0xFF0000), true);
            return;
        }

        if(species == null){
            player.displayClientMessage(Component.translatable( "message.mega_showdown.incorrect_mega_stone")
                    .withColor(0xFF0000), true);
            return;
        }

        if(Config.friendshipMode && pokemon.getFriendship() < 200 && !pokemon.getEntity().isBattling()){
            player.displayClientMessage(Component.translatable("message.mega_showdown.bond_not_close_mega")
                    .withColor(0xFF0000), true);
            return;
        }

        if(species.equals(pokemon.getSpecies().getName()) &&
                (!player.getData(DataManage.MEGA_DATA) || Config.multipleMegas)){
            if(species.equals("Charizard")){
                if(pokemon.heldItem().is(MegaStones.CHARIZARDITE_X)){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    playEvolveAnimation(context);

                    new StringSpeciesFeature("mega_evolution", "mega_x").apply(pokemon);
                    setTradable(pokemon, false);

                }else if(pokemon.heldItem().is(MegaStones.CHARIZARDITE_Y)){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    playEvolveAnimation(context);

                    new StringSpeciesFeature("mega_evolution", "mega_y").apply(pokemon);
                    setTradable(pokemon, false);

                }
            }
            else if(species.equals("Mewtwo")){
                if(pokemon.heldItem().is(MegaStones.MEWTWONITE_X)){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    playEvolveAnimation(context);

                    new StringSpeciesFeature("mega_evolution", "mega_x").apply(pokemon);
                    setTradable(pokemon, false);

                }else if(pokemon.heldItem().is(MegaStones.MEWTWONITE_Y)){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    playEvolveAnimation(context);

                    new StringSpeciesFeature("mega_evolution", "mega_y").apply(pokemon);
                    setTradable(pokemon, false);
                }
            }
            else{
                player.setData(DataManage.MEGA_DATA, true);
                player.setData(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                new StringSpeciesFeature("mega_evolution", "mega").apply(pokemon);

                setTradable(pokemon, false);

                playEvolveAnimation(context);
            }
        }else if(species.equals(pokemon.getSpecies().getName()) && player.getData(DataManage.MEGA_DATA)){
            player.displayClientMessage(Component.translatable("message.mega_showdown.mega_limit")
                    .withColor(0xFF0000), true);
        }else{
            player.displayClientMessage(Component.translatable( "message.mega_showdown.incorrect_mega_stone")
                    .withColor(0xFF0000), true);
        }

    }

    public static void Devolve(Pokemon context, Boolean fromBattle){
        ServerPlayer player = context.getOwnerPlayer();

        if(context instanceof Pokemon pk){
            if(pk.getOwnerPlayer() != player){
                return;
            }

            if(pk.getEntity() != null && pk.getEntity().isBattling() && !fromBattle){
                player.displayClientMessage(Component.translatable("message.mega_showdown.battle_not_allowed")
                        .withColor(0xFF0000), true);
                return;
            }


            player.setData(DataManage.MEGA_DATA, false);
            player.removeData(DataManage.MEGA_POKEMON);

            if(context.getEntity() != null){
                playDevolveAnimation(context.getEntity());
            }

            new StringSpeciesFeature("mega_evolution", "none").apply(pk);
            setTradable(pk, true);
        }
    }
    public static void playEvolveAnimation(PokemonEntity context) {
        AdvancementHelper.grantAdvancement(context.getPokemon().getOwnerPlayer(), "mega/mega_evolve");

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
