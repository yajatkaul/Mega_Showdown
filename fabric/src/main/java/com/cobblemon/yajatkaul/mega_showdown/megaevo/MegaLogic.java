package com.cobblemon.yajatkaul.mega_showdown.megaevo;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.PokeHandler;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.MegaData;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.utility.ModTags;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
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
                    Text.translatable("message.mega_showdown.not_so_fast").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return false;
        }

        boolean hasMegaItemTrinkets = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                trinkets.isEquipped(item -> (item.isIn(ModTags.Items.MEGA_BRACELETS)))).orElse(false);

        boolean hasOffhandMegaItem = player.getOffHandStack().isIn(ModTags.Items.MEGA_BRACELETS);
        boolean hasMainhandMegaItem = player.getMainHandStack().isIn(ModTags.Items.MEGA_BRACELETS);


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

            if(!ShowdownConfig.mega.get() || pk.getPokemon().getOwnerPlayer() != player || (!Utils.MEGA_POKEMONS.contains(pk.getPokemon().getSpecies().getName()) && !pk.getPokemon().getSpecies().getName().equals("Rayquaza")) || !Possible(player, false)){
                return;
            }

            if(pk.getAspects().contains("mega_x") || pk.getAspects().contains("mega") || pk.getAspects().contains("mega_y")){
                Devolve(pk.getPokemon(), false);
            }else {
                Evolve(pk, player, false);
            }
        }
    }

    public static void Evolve(PokemonEntity context, PlayerEntity player, Boolean fromBattle){
        if(context instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() != player){
            return;
        }
        if(player.getWorld().isClient){
            return;
        }

        Boolean playerData = player.getAttached(DataManage.MEGA_DATA);
        if(playerData == null){
            playerData = false;
        }

        Pokemon pokemon = (context).getPokemon();
        String species = Utils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());

        if(context instanceof PokemonEntity pk && (pk.isBattling() && !fromBattle)){
            player.sendMessage(
                    Text.translatable("message.mega_showdown.battle_not_allowed").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return;
        }

        if(pokemon.getSpecies().getName().equals("Rayquaza") && (!playerData || ShowdownConfig.multipleMegas.get())){
            if(ShowdownConfig.friendshipMode.get() && pokemon.getFriendship() < 200 && !pokemon.getEntity().isBattling()){
                player.sendMessage(
                        Text.translatable("message.mega_showdown.bond_not_close_mega").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
                return;
            }

            boolean found = false;
            for (int i = 0; i < 4; i++){
                if(pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")){
                    player.setAttached(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));
                    player.setAttached(DataManage.MEGA_DATA, true);

                    new StringSpeciesFeature("mega_evolution", "mega").apply(pokemon);
                    setTradable(pokemon, false);

                    playEvolveAnimation(context);
                    
                    found = true;
                }
            }
            if(!found){
                player.sendMessage(
                        Text.translatable("message.mega_showdown.rayquaza_no_dragonascent").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
            }
            return;
        } else if (pokemon.getSpecies().getName().equals("Rayquaza") && playerData) {
            player.sendMessage(
                    Text.translatable("message.mega_showdown.mega_limit").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return;
        }

        if(species == null){
            for(MegaData megaPok: Utils.megaRegistry){
                String[] parts = megaPok.item_id().split(":");
                Identifier paperId = Identifier.of(parts[0], parts[1]);
                Item paperItem = Registries.ITEM.get(paperId);
                if(paperItem == pokemon.heldItem().getItem()
                        && pokemon.heldItem().get(DataComponentTypes.CUSTOM_MODEL_DATA).value()
                        == megaPok.custom_model_data()){
                    species = megaPok.pokemon();
                }
                if(species == null){
                    continue;
                }
                if(species.equals(pokemon.getSpecies().getName()) && !playerData){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    playEvolveAnimation(context);

                    for(String aspect: megaPok.aspects()){
                        String[] aspectDiv = aspect.split("=");
                        new StringSpeciesFeature(aspectDiv[0], aspectDiv[1]).apply(pokemon);
                    }
                    setTradable(pokemon, false);

                    return;
                }else if(species.equals(pokemon.getSpecies().getName()) && playerData){
                    player.sendMessage(
                            Text.translatable("message.mega_showdown.mega_limit").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                            true
                    );
                    return;
                }else{
                    player.sendMessage(
                            Text.translatable("message.mega_showdown.incorrect_mega_stone").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                            true
                    );
                    return;
                }
            }
            return;
        }

        if(ShowdownConfig.friendshipMode.get() && pokemon.getFriendship() < 200 && !pokemon.getEntity().isBattling()){
            player.sendMessage(
                    Text.translatable("message.mega_showdown.bond_not_close_mega").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return;
        }

        if(species.equals(pokemon.getSpecies().getName()) && (!playerData || ShowdownConfig.multipleMegas.get())){
            if(species.equals("Charizard")){
                if(pokemon.heldItem().isOf(MegaStones.CHARIZARDITE_X)){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    playEvolveAnimation(context);

                    new StringSpeciesFeature("mega_evolution", "mega_x").apply(pokemon);
                    setTradable(pokemon, false);

                }else if(pokemon.heldItem().isOf(MegaStones.CHARIZARDITE_Y)){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    playEvolveAnimation(context);

                    new StringSpeciesFeature("mega_evolution", "mega_y").apply(pokemon);
                    setTradable(pokemon, false);
                }
            }
            else if(species.equals("Mewtwo")){
                if(pokemon.heldItem().isOf(MegaStones.MEWTWONITE_X)){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    playEvolveAnimation(context);

                    new StringSpeciesFeature("mega_evolution", "mega_x").apply(pokemon);

                    setTradable(pokemon, false);

                }else if(pokemon.heldItem().isOf(MegaStones.MEWTWONITE_Y)){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    playEvolveAnimation(context);

                    new StringSpeciesFeature("mega_evolution", "mega_y").apply(pokemon);

                    setTradable(pokemon, false);

                }
            }
            else{
                player.setAttached(DataManage.MEGA_DATA, true);
                player.setAttached(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                playEvolveAnimation(context);

                new StringSpeciesFeature("mega_evolution", "mega").apply(pokemon);

                setTradable(pokemon, false);

            }
        } else if(species.equals(pokemon.getSpecies().getName()) && playerData){
            player.sendMessage(
                    Text.translatable("message.mega_showdown.mega_limit").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
        }else{
            player.sendMessage(
                    Text.translatable("message.mega_showdown.incorrect_mega_stone").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
        }
    }

    public static void Devolve(Pokemon context, Boolean fromBattle){
        ServerPlayerEntity player = context.getOwnerPlayer();

        if(context.getEntity() != null && context.getEntity().isBattling() && !fromBattle){
            player.sendMessage(
                    Text.translatable("message.mega_showdown.battle_not_allowed").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return;
        }

        player.setAttached(DataManage.MEGA_DATA, false);
        player.removeAttached(DataManage.MEGA_POKEMON);

        if(context.getEntity() != null){
            playDevolveAnimation(context.getEntity());
        }

        new StringSpeciesFeature("mega_evolution", "none").apply(context);

        setTradable(context, true);
    }

    public static void playEvolveAnimation(PokemonEntity context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            AdvancementHelper.grantAdvancement(context.getPokemon().getOwnerPlayer(), "mega/mega_evolve");

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
