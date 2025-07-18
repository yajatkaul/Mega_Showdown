package com.cobblemon.yajatkaul.mega_showdown.formChangeLogic;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.MegaData;
import com.cobblemon.yajatkaul.mega_showdown.datapack.handler.HandlerUtils;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils.EventUtils;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.ModTags;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import dev.emi.trinkets.api.TrinketsApi;
import kotlin.Unit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

import java.util.*;

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

            Pokemon pokemon = pk.getPokemon();
            if (!MegaShowdownConfig.mega.get() || pokemon.getOwnerPlayer() != player || !Possible(player, false)) {
                return;
            }
            ItemStack heldItem = pokemon.heldItem();

            if (!pokemon.getSpecies().getName().equals("Rayquaza")) {
                for (MegaData megaData : Utils.megaRegistry) {
                    if (pokemon.getSpecies().getName().equals(megaData.pokemon())
                            && HandlerUtils.listCheck(megaData.required_aspects(), pokemon.getAspects(), false)
                            && !HandlerUtils.listCheck(megaData.blacklist_aspects(), pokemon.getAspects(), true)) {
                        boolean isMega = (pokemon.getAspects().stream().anyMatch(FormChangeHelper.mega_aspects::contains));

                        if (isMega) {
                            Devolve(pk.getPokemon(), false);
                            return;
                        } else {
                            Item megaStone = Registries.ITEM.get(Identifier.tryParse(megaData.item_id()));
                            if (heldItem.isOf(megaStone)) {
                                Evolve(pk, player, megaData.apply_aspect());
                                return;
                            }
                        }
                    }
                }
            } else {
                boolean isMega = (pokemon.getAspects().stream().anyMatch(FormChangeHelper.mega_aspects::contains));

                if (isMega) {
                    Devolve(pk.getPokemon(), false);
                } else {
                    Evolve(pk, player, "mega");
                }
            }
        }
    }

    public static void Evolve(PokemonEntity context, PlayerEntity player, String apply_aspects) {
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

        megaEvolve(context, apply_aspects);
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
        SnowStormHandler.Companion.snowStormPartileSpawner(context, Identifier.tryParse("cobblemon:mega_evolution"), List.of("target"));

        BlockPos entityPos = context.getBlockPos();
        context.getWorld().playSound(
                null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                ModSounds.MEGA,
                SoundCategory.PLAYERS, 0.2f, 0.8f
        );

        context.after(4.8F, () -> {
            SnowStormHandler.Companion.playAnimation(context, Set.of("cry"), List.of());
            context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), false);
            new StringSpeciesFeature("mega_evolution", type).apply(context.getPokemon());
            return Unit.INSTANCE;
        });
    }

    //BATTLE
    public static void megaEvolve(PokemonEntity context, String type, BattlePokemon battlePokemon, PokemonBattle pokemonBattle) {
        AdvancementHelper.grantAdvancement(context.getPokemon().getOwnerPlayer(), "mega/mega_evolve");

        context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), true);
        SnowStormHandler.Companion.snowStormPartileSpawner(context, Identifier.tryParse("cobblemon:mega_evolution"), List.of("target"));

        BlockPos entityPos = context.getBlockPos();
        context.getWorld().playSound(
                null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                ModSounds.MEGA,
                SoundCategory.PLAYERS, 0.2f, 0.8f
        );

        context.after(4.8F, () -> {
            SnowStormHandler.Companion.playAnimation(context, Set.of("cry"), List.of());
            context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), false);
            new StringSpeciesFeature("mega_evolution", type).apply(context.getPokemon());
            EventUtils.updatePackets(pokemonBattle, battlePokemon);
            return Unit.INSTANCE;
        });
    }

    public static void Evolve(PokemonEntity context, PlayerEntity player, BattlePokemon battlePokemon, PokemonBattle pokemonBattle) {
        if (context.getPokemon().getOwnerPlayer() != player || player.getWorld().isClient) {
            return;
        }

        Pokemon pokemon = context.getPokemon();
        ItemStack heldItem = pokemon.heldItem();
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
            Identifier paperId = Identifier.of(megaPok.item_id());
            Item paperItem = Registries.ITEM.get(paperId);

            String candidateSpecies = null;

            if (HandlerUtils.itemValidator(paperItem, megaPok.custom_model_data(), heldItem, megaPok.item_id())) {
                candidateSpecies = megaPok.pokemon();
            }

            if (candidateSpecies == null) {
                continue;
            } else {
                boolean blackListFound = false;
                for (List<String> black_list : megaPok.blacklist_aspects()) {
                    if (pokemon.getAspects().containsAll(black_list)) {
                        blackListFound = true;
                        break;
                    }
                }
                if (blackListFound) {
                    continue;
                }
            }

            if (megaPok.required_aspects().isEmpty()) {
                if (candidateSpecies.equals(pokemon.getSpecies().getName())) {
                    megaEvolve(context, megaPok.apply_aspect(), battlePokemon, pokemonBattle);
                    pokemon.setTradeable(false);
                } else {
                    player.sendMessage(
                            Text.translatable("message.mega_showdown.incorrect_mega_stone").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                            true
                    );
                }
                return;
            }
            for (List<String> condition : megaPok.required_aspects()) {
                if (pokemon.getAspects().containsAll(condition)) {
                    if (candidateSpecies.equals(pokemon.getSpecies().getName())) {
                        megaEvolve(context, megaPok.apply_aspect(), battlePokemon, pokemonBattle);
                        pokemon.setTradeable(false);
                    } else {
                        player.sendMessage(
                                Text.translatable("message.mega_showdown.incorrect_mega_stone").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                                true
                        );
                    }
                    return;
                }
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

    //NPCS
    public static void NPCEvolve(PokemonEntity context, BattlePokemon battlePokemon, PokemonBattle pokemonBattle) {
        Pokemon pokemon = context.getPokemon();
        ItemStack heldItem = pokemon.heldItem();

        if (pokemon.getSpecies().getName().equals("Rayquaza")) {
            for (int i = 0; i < 4; i++) {
                if (pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")) {
                    megaEvolve(context, "mega");
                    pokemon.setTradeable(false);
                }
            }
            return;
        }

        for (MegaData megaPok : Utils.megaRegistry) {
            Identifier paperId = Identifier.tryParse(megaPok.item_id());
            Item paperItem = Registries.ITEM.get(paperId);

            String candidateSpecies = null;

            if (HandlerUtils.itemValidator(paperItem, megaPok.custom_model_data(), heldItem, megaPok.item_id())) {
                candidateSpecies = megaPok.pokemon();
            }

            if (candidateSpecies == null) {
                continue;
            } else {
                boolean blackListFound = false;
                for (List<String> black_list : megaPok.blacklist_aspects()) {
                    if (pokemon.getAspects().containsAll(black_list)) {
                        blackListFound = true;
                        break;
                    }
                }
                if (blackListFound) {
                    continue;
                }
            }

            if (megaPok.required_aspects().isEmpty()) {
                if (candidateSpecies.equals(pokemon.getSpecies().getName())) {
                    megaEvolve(context, megaPok.apply_aspect(), battlePokemon, pokemonBattle);
                    pokemon.setTradeable(false);
                }
                return;
            }
            for (List<String> condition : megaPok.required_aspects()) {
                if (pokemon.getAspects().containsAll(condition)) {
                    if (candidateSpecies.equals(pokemon.getSpecies().getName())) {
                        megaEvolve(context, megaPok.apply_aspect(), battlePokemon, pokemonBattle);
                        pokemon.setTradeable(false);
                    }
                    return;
                }
            }
        }
    }
}
