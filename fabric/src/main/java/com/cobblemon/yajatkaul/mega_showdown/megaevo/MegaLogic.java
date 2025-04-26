package com.cobblemon.yajatkaul.mega_showdown.megaevo;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.PokeHandler;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.MegaBraceletItem;
import com.cobblemon.yajatkaul.mega_showdown.utility.LazyLib;
import com.cobblemon.yajatkaul.mega_showdown.utility.ModTags;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;


public class MegaLogic {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 2000; // 2 sec

    public static boolean Possible(ServerPlayerEntity player, Boolean fromBattle){
        UUID playerId = player.getUuid();
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId) && currentTime < cooldowns.get(playerId) && !fromBattle) {
            player.sendMessage(
                    Text.literal("Not so fast!").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return false;
        }

        boolean hasMegaItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                trinkets.isEquipped(item -> (item.getItem() instanceof MegaBraceletItem || item.isIn(ModTags.Items.MEGA_BRACELETS)))).orElse(false);

        boolean hasOffhandMegaItem = player.getOffHandStack().getItem() instanceof MegaBraceletItem || player.getOffHandStack().isIn(ModTags.Items.MEGA_BRACELETS);
        boolean hasMainhandMegaItem = player.getMainHandStack().getItem() instanceof MegaBraceletItem || player.getMainHandStack().isIn(ModTags.Items.MEGA_BRACELETS);


        if(fromBattle){
            if (!hasMegaItemTrinkets && !hasOffhandMegaItem && !hasMainhandMegaItem) {
                return false;
            }
        }else {
            if (!hasMegaItemTrinkets && !hasOffhandMegaItem) {
                return false;
            }
        }


        // Apply cooldown
        cooldowns.put(playerId, currentTime + COOLDOWN_TIME);
        return true;
    }

    public static void EvoLogic(ServerPlayerEntity player){
        if(ShowdownConfig.battleModeOnly.get()){
            return;
        }

        double range = 5.0;

        Vec3d startPos = player.getEyePos();
        Vec3d lookVec = player.getRotationVector();
        Vec3d endPos = startPos.add(lookVec.multiply(range));

        EntityHitResult entityHit = ProjectileUtil.raycast(
                player,
                startPos,
                endPos,
                player.getBoundingBox().stretch(lookVec.multiply(range)).expand(1.0),
                entity -> !entity.isSpectator() && entity.canHit(),
                range * range
        );

        if(entityHit == null){
            return;
        }

        if(entityHit.getEntity() instanceof PokemonEntity pk) {
            if(pk.getWorld().isClient){
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
                        Devolve(pk.getPokemon(), player, false);
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

    public static void Evolve(LivingEntity context, PlayerEntity player, Boolean fromBattle){
        if(context instanceof PokemonEntity pk){
            if(pk.getPokemon().getOwnerPlayer() != player){
                return;
            }
        }

        Pokemon pokemon = ((PokemonEntity) context).getPokemon();
        Species species = Utils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());
        Boolean playerData = player.getAttached(DataManage.MEGA_DATA);
        if(playerData == null){
            playerData = false;
        }

        if(context instanceof PokemonEntity pk && (pk.isBattling() && !fromBattle)){
            player.sendMessage(
                    Text.literal("Not allowed in battle").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return;
        }

        if(pokemon.getSpecies().getName().equals(Utils.getSpecies("rayquaza").getName()) && (!playerData || ShowdownConfig.multipleMegas.get())){
            if(ShowdownConfig.friendshipMode.get() && pokemon.getFriendship() < 200 && !pokemon.getEntity().isBattling()){
                player.sendMessage(
                        Text.literal("You are not close enough with your pokemon to mega outside").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
                return;
            }

            boolean found = false;
            for (int i = 0; i < 4; i++){
                if(pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")){
                    player.setAttached(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));
                    player.setAttached(DataManage.MEGA_DATA, true);

                    new FlagSpeciesFeature("mega", true).apply(pokemon);
                    setTradable(pokemon, false);

                    playEvolveAnimation(context);
                    
                    AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "mega_evolve");
                    found = true;
                }
            }
            if(!found){
                player.sendMessage(
                        Text.literal("Rayquaza doesn't have dragonascent").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
            }
            return;
        } else if (pokemon.getSpecies().getName().equals(Utils.getSpecies("rayquaza").getName()) && playerData) {
            player.sendMessage(
                    Text.literal("You can only have one mega at a time").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return;
        }

        if(species == null){
            player.sendMessage(
                    Text.literal("Don't have the correct stone").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return;
        }

        if(ShowdownConfig.friendshipMode.get() && pokemon.getFriendship() < 200 && !pokemon.getEntity().isBattling()){
            player.sendMessage(
                    Text.literal("You are not close enough with your pokemon to mega outside").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return;
        }

        //Multiple megas
        if(species.getName().equals(pokemon.getSpecies().getName()) && (!playerData || ShowdownConfig.multipleMegas.get())){
            if(species.getName().equals(Utils.getSpecies("charizard").getName())){
                if(pokemon.heldItem().isOf(MegaStones.CHARIZARDITE_X)){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    playEvolveAnimation(context);

                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                    setTradable(pokemon, false);

                    AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "mega_evolve");
                }else if(pokemon.heldItem().isOf(MegaStones.CHARIZARDITE_Y)){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    playEvolveAnimation(context);

                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                    setTradable(pokemon, false);

                    AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "mega_evolve");
                }
            }
            else if(species.getName().equals(Utils.getSpecies("mewtwo").getName())){
                if(pokemon.heldItem().isOf(MegaStones.MEWTWONITE_X)){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    playEvolveAnimation(context);

                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                    setTradable(pokemon, false);

                    AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "mega_evolve");
                }else if(pokemon.heldItem().isOf(MegaStones.MEWTWONITE_Y)){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    playEvolveAnimation(context);

                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                    setTradable(pokemon, false);

                    AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "mega_evolve");
                }
            }
            else{
                player.setAttached(DataManage.MEGA_DATA, true);
                player.setAttached(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                playEvolveAnimation(context);

                new FlagSpeciesFeature("mega", true).apply(pokemon);
                setTradable(pokemon, false);

                AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "mega_evolve");
            }
        } else if(species.getName().equals(pokemon.getSpecies().getName()) && playerData){
            player.sendMessage(
                    Text.literal("You can only have one mega at a time").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
        }else{
            player.sendMessage(
                    Text.literal("Don't have the correct stone").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
        }
    }

    public static void Devolve(Pokemon context, PlayerEntity player, Boolean fromBattle){
        if(player.getWorld().isClient || context == null || context.getOwnerPlayer() != player){
            return;
        }

        if(context.getEntity() != null && context.getEntity().isBattling() && !fromBattle){
            player.sendMessage(
                    Text.literal("Not allowed in battle").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return;
        }

        player.setAttached(DataManage.MEGA_DATA, false);
        player.removeAttached(DataManage.MEGA_POKEMON);

        if(context.getEntity() != null){
            playDevolveAnimation(context.getEntity());
        }

        new FlagSpeciesFeature("mega", false).apply(context);
        new FlagSpeciesFeature("mega-x", false).apply(context);
        new FlagSpeciesFeature("mega-y", false).apply(context);
        setTradable(context, true);
    }

    public static void playEvolveAnimation(LivingEntity context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_BEACON_ACTIVATE, // Yarn mapping for BEACON_ACTIVATE
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

    public static void playDevolveAnimation(LivingEntity context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_BEACON_DEACTIVATE, // Yarn mapping for BEACON_DEACTIVATE
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
}
