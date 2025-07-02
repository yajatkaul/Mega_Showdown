package com.cobblemon.yajatkaul.mega_showdown.formChangeLogic;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.MegaData;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers.CobbleEventsHandler;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.ModTags;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import dev.emi.trinkets.api.TrinketsApi;
import kotlin.Unit;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MegaLogic {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 6000; // 6 sec

    public static boolean Possible(ServerPlayerEntity player, Boolean fromBattle) {
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


        if (fromBattle) {
            if (!hasMegaItemTrinkets && !hasOffhandMegaItem && !hasMainhandMegaItem) {
                return false;
            }
        } else {
            if (!hasMegaItemTrinkets && !hasOffhandMegaItem) {
                return false;
            }
        }


        // Apply cooldown
        cooldowns.put(playerId, currentTime + COOLDOWN_TIME);
        return true;
    }

    public static void EvoLogic(ServerPlayerEntity player) {
        if (MegaShowdownConfig.battleModeOnly.get()) {
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

        if (entityHit == null) {
            return;
        }

        if (entityHit.getEntity() instanceof PokemonEntity pk) {
            if (pk.getWorld().isClient) {
                return;
            }

            if (!MegaShowdownConfig.mega.get() || pk.getPokemon().getOwnerPlayer() != player || (!Utils.MEGA_POKEMONS.contains(pk.getPokemon().getSpecies().getName()) && !pk.getPokemon().getSpecies().getName().equals("Rayquaza")) || !Possible(player, false)) {
                return;
            }

            boolean isMega = pk.getAspects().stream()
                    .anyMatch(aspect -> aspect.startsWith("mega"));

            if (isMega) {
                Devolve(pk.getPokemon(), false);
            } else {
                Evolve(pk, player);
            }
        }
    }

    public static void Evolve(PokemonEntity context, PlayerEntity player) {
        if (context.getPokemon().getOwnerPlayer() != player || player.getWorld().isClient) {
            return;
        }

        Pokemon pokemon = context.getPokemon();
        boolean hasMega = FormChangeHelper.hasMega((ServerPlayerEntity) player);

        if (hasMega && !MegaShowdownConfig.multipleMegas.get()) {
            player.sendMessage(
                    Text.translatable("message.mega_showdown.mega_limit").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return;
        }

        if (context.isBattling()) {
            player.sendMessage(
                    Text.translatable("message.mega_showdown.battle_not_allowed").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return;
        }

        if (pokemon.getSpecies().getName().equals("Rayquaza")) {

            boolean found = false;
            for (int i = 0; i < 4; i++) {
                if (pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")) {

                    megaEvolve(context, "mega");
                    pokemon.setTradeable(false);

                    found = true;
                }
            }
            if (!found) {
                player.sendMessage(
                        Text.translatable("message.mega_showdown.rayquaza_no_dragonascent").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
            }
            return;
        }

        for (MegaData megaPok : Utils.megaRegistry) {
            String[] parts = megaPok.item_id().split(":");
            Identifier paperId = Identifier.of(parts[0], parts[1]);
            Item paperItem = Registries.ITEM.get(paperId);

            String candidateSpecies = null;

            if (paperItem == pokemon.heldItem().getItem() &&
                    ((pokemon.heldItem().get(DataComponentTypes.CUSTOM_MODEL_DATA) != null &&
                            pokemon.heldItem().get(DataComponentTypes.CUSTOM_MODEL_DATA).value() == megaPok.custom_model_data())
                            || megaPok.custom_model_data() == 0)) {
                candidateSpecies = megaPok.pokemon();
            }

            if (candidateSpecies == null
                    || !pokemon.getAspects().containsAll(megaPok.required_aspects())
                    || pokemon.getAspects().stream().anyMatch(megaPok.blacklist_aspects()::contains)) {
                continue;
            }

            if (candidateSpecies.equals(pokemon.getSpecies().getName())) {

                for (String aspect : megaPok.aspects()) {
                    String[] aspectDiv = aspect.split("=");
                    if (aspectDiv[1].equals("true") || aspectDiv[1].equals("false")) {
                        megaEvolve(context, aspectDiv[0]);
                    } else {
                        megaEvolve(context, aspectDiv[1]);
                    }
                }
                pokemon.setTradeable(false);
                return;
            } else {
                player.sendMessage(
                        Text.translatable("message.mega_showdown.incorrect_mega_stone").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
                return;
            }
        }
    }

    public static void Devolve(Pokemon context, Boolean fromBattle) {
        ServerPlayerEntity player = context.getOwnerPlayer();

        if (player != null && context.getEntity() != null && context.getEntity().isBattling() && !fromBattle) {
            player.sendMessage(
                    Text.translatable("message.mega_showdown.battle_not_allowed").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return;
        }

        if (context.getEntity() != null) {
            playDevolveAnimation(context.getEntity());
        }

        new StringSpeciesFeature("mega_evolution", "none").apply(context);

        context.setTradeable(true);
    }

    public static void megaEvolve(PokemonEntity context, String type) {
        AdvancementHelper.grantAdvancement(context.getPokemon().getOwnerPlayer(), "mega/mega_evolve");

        context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), true);
        SnowStormHandler.Companion.snowStormPartileSpawner(context, "mega_evolution", "target");

        BlockPos entityPos = context.getBlockPos();
        context.getWorld().playSound(
                null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                ModSounds.MEGA,
                SoundCategory.PLAYERS, 0.2f, 0.8f
        );

        context.after(4.8F, () -> {
            SnowStormHandler.Companion.cryAnimation(context);
            context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), false);
            new StringSpeciesFeature("mega_evolution", type).apply(context.getPokemon());
            return Unit.INSTANCE;
        });
    }

    // IN BATTLE
    public static void Evolve(PokemonEntity context, PlayerEntity player, BattlePokemon battlePokemon, PokemonBattle pokemonBattle) {
        if (context.getPokemon().getOwnerPlayer() != player || player.getWorld().isClient) {
            return;
        }

        Pokemon pokemon = context.getPokemon();
        boolean hasMega = FormChangeHelper.hasMega((ServerPlayerEntity) player);

        if (hasMega && !MegaShowdownConfig.multipleMegas.get()) {
            player.sendMessage(
                    Text.translatable("message.mega_showdown.mega_limit").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return;
        }

        if (pokemon.getSpecies().getName().equals("Rayquaza")) {
            boolean found = false;
            for (int i = 0; i < 4; i++) {
                if (pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")) {

                    megaEvolve(context, "mega", battlePokemon, pokemonBattle);
                    pokemon.setTradeable(false);

                    found = true;
                }
            }
            if (!found) {
                player.sendMessage(
                        Text.translatable("message.mega_showdown.rayquaza_no_dragonascent").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
            }
            return;
        }

        for (MegaData megaPok : Utils.megaRegistry) {
            String[] parts = megaPok.item_id().split(":");
            Identifier paperId = Identifier.of(parts[0], parts[1]);
            Item paperItem = Registries.ITEM.get(paperId);

            String candidateSpecies = null;

            if (paperItem == pokemon.heldItem().getItem() &&
                    ((pokemon.heldItem().get(DataComponentTypes.CUSTOM_MODEL_DATA) != null &&
                            pokemon.heldItem().get(DataComponentTypes.CUSTOM_MODEL_DATA).value() == megaPok.custom_model_data())
                            || megaPok.custom_model_data() == 0)) {
                candidateSpecies = megaPok.pokemon();
            }

            if (candidateSpecies == null
                    || !pokemon.getAspects().containsAll(megaPok.required_aspects())
                    || pokemon.getAspects().stream().anyMatch(megaPok.blacklist_aspects()::contains)) {
                continue;
            }

            if (candidateSpecies.equals(pokemon.getSpecies().getName())) {

                for (String aspect : megaPok.aspects()) {
                    String[] aspectDiv = aspect.split("=");
                    if (aspectDiv[1].equals("true") || aspectDiv[1].equals("false")) {
                        megaEvolve(context, aspectDiv[0], battlePokemon, pokemonBattle);
                    } else {
                        megaEvolve(context, aspectDiv[1], battlePokemon, pokemonBattle);
                    }
                }
                pokemon.setTradeable(false);
                return;
            } else {
                player.sendMessage(
                        Text.translatable("message.mega_showdown.incorrect_mega_stone").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
                return;
            }
        }
    }

    public static void megaEvolve(PokemonEntity context, String type, BattlePokemon battlePokemon, PokemonBattle pokemonBattle) {
        AdvancementHelper.grantAdvancement(context.getPokemon().getOwnerPlayer(), "mega/mega_evolve");

        context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), true);
        SnowStormHandler.Companion.snowStormPartileSpawner(context, "mega_evolution", "target");

        BlockPos entityPos = context.getBlockPos();
        context.getWorld().playSound(
                null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                ModSounds.MEGA,
                SoundCategory.PLAYERS, 0.2f, 0.8f
        );

        context.after(4.8F, () -> {
            SnowStormHandler.Companion.cryAnimation(context);
            context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), false);
            new StringSpeciesFeature("mega_evolution", type).apply(context.getPokemon());
            CobbleEventsHandler.updatePackets(pokemonBattle, battlePokemon, true);
            return Unit.INSTANCE;
        });
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

    //NPCS
    public static void NPCEvolve(PokemonEntity context, BattlePokemon battlePokemon, PokemonBattle pokemonBattle) {
        Pokemon pokemon = context.getPokemon();

        if (pokemon.getSpecies().getName().equals("Rayquaza")) {
            for (int i = 0; i < 4; i++) {
                if (pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")) {
                    megaEvolve(context, "mega", battlePokemon, pokemonBattle);
                    pokemon.setTradeable(false);
                }
            }
            return;
        }


        for (MegaData megaPok : Utils.megaRegistry) {
            String[] parts = megaPok.item_id().split(":");
            Identifier paperId = Identifier.of(parts[0], parts[1]);
            Item paperItem = Registries.ITEM.get(paperId);

            String candidateSpecies = null;

            if (paperItem == pokemon.heldItem().getItem() &&
                    ((pokemon.heldItem().get(DataComponentTypes.CUSTOM_MODEL_DATA) != null &&
                            pokemon.heldItem().get(DataComponentTypes.CUSTOM_MODEL_DATA).value() == megaPok.custom_model_data())
                            || megaPok.custom_model_data() == 0)) {
                candidateSpecies = megaPok.pokemon();
            }

            if (candidateSpecies == null
                    || !pokemon.getAspects().containsAll(megaPok.required_aspects())
                    || pokemon.getAspects().stream().anyMatch(megaPok.blacklist_aspects()::contains)) {
                continue;
            }

            if (candidateSpecies.equals(pokemon.getSpecies().getName())) {
                for (String aspect : megaPok.aspects()) {
                    String[] aspectDiv = aspect.split("=");
                    if (aspectDiv[1].equals("true") || aspectDiv[1].equals("false")) {
                        megaEvolve(context, aspectDiv[0], battlePokemon, pokemonBattle);
                    } else {
                        megaEvolve(context, aspectDiv[1], battlePokemon, pokemonBattle);
                    }
                }
                pokemon.setTradeable(false);
                return;
            } else {
                return;
            }
        }

    }
}
