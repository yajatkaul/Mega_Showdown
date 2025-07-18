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
import kotlin.Unit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.*;

public class MegaLogic {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 6000; // 6 sec

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

        if (fromBattle) {
            if (!hasMegaItemCurios && !hasOffhandMegaItem) {
                return false;
            }
        } else {
            if (!hasMegaItemCurios && !hasOffhandMegaItem && !hasMainhandMegaItem) {
                return false;
            }
        }


        // Apply cooldown
        cooldowns.put(playerId, currentTime + COOLDOWN_TIME);
        return true;
    }

    public static void EvoLogic(Player playerContext) {
        ServerPlayer player = (ServerPlayer) playerContext;

        if (MegaShowdownConfig.battleModeOnly) {
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

        if (entityHit.getEntity() instanceof PokemonEntity pk) {
            if (pk.level().isClientSide) {
                return;
            }

            Pokemon pokemon = pk.getPokemon();
            if (!MegaShowdownConfig.mega || pokemon.getOwnerPlayer() != player || !Possible(player, false)) {
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
                            Item megaStone = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(megaData.item_id()));
                            if (heldItem.is(megaStone)) {
                                Evolve(pk, playerContext, megaData.apply_aspect());
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
                    Evolve(pk, playerContext, "mega");
                }
            }
        }
    }

    public static void Evolve(PokemonEntity context, Player player, String apply_aspect) {
        if (player.level().isClientSide) {
            return;
        }

        Pokemon pokemon = context.getPokemon();
        boolean hasMega = FormChangeHelper.hasMega((ServerPlayer) player);

        if (hasMega && !MegaShowdownConfig.multipleMegas) {
            player.displayClientMessage(Component.translatable("message.mega_showdown.mega_limit")
                    .withColor(0xFF0000), true);
            return;
        }

        if (pokemon.getEntity().isBattling()) {
            player.displayClientMessage(Component.translatable("message.mega_showdown.battle_not_allowed")
                    .withColor(0xFF0000), true);
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
                player.displayClientMessage(Component.translatable("message.mega_showdown.rayquaza_no_dragonascent")
                        .withColor(0xFF0000), true);
            }
            return;
        }

        megaEvolve(context, apply_aspect);
    }

    public static void Devolve(Pokemon context, boolean fromBattle) {
        ServerPlayer player = context.getOwnerPlayer();

        if (context.getOwnerPlayer() != player) {
            return;
        }

        if (context.getEntity() != null && (context.getEntity().isBattling() && !fromBattle)) {
            player.displayClientMessage(Component.translatable("message.mega_showdown.battle_not_allowed")
                    .withColor(0xFF0000), true);
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

        context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);
        SnowStormHandler.Companion.snowStormPartileSpawner(context, ResourceLocation.tryParse("cobblemon:mega_evolution"), List.of("target"));

        BlockPos entityPos = context.getOnPos();
        context.level().playSound(
                null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                ModSounds.MEGA.get(),
                SoundSource.PLAYERS, 0.2f, 0.8f
        );

        context.after(4.7F, () -> {
            SnowStormHandler.Companion.playAnimation(context, Set.of("cry"), List.of());
            context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), false);
            new StringSpeciesFeature("mega_evolution", type).apply(context.getPokemon());
            return Unit.INSTANCE;
        });
    }

    public static void megaEvolve(PokemonEntity context, String type, BattlePokemon battlePokemon, PokemonBattle pokemonBattle) {
        AdvancementHelper.grantAdvancement(context.getPokemon().getOwnerPlayer(), "mega/mega_evolve");

        context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);
        SnowStormHandler.Companion.snowStormPartileSpawner(context, ResourceLocation.tryParse("cobblemon:mega_evolution"), List.of("target"));

        BlockPos entityPos = context.getOnPos();
        context.level().playSound(
                null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                ModSounds.MEGA.get(),
                SoundSource.PLAYERS, 0.2f, 0.8f
        );

        context.after(4.7F, () -> {
            SnowStormHandler.Companion.playAnimation(context, Set.of("cry"), List.of());
            context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), false);
            new StringSpeciesFeature("mega_evolution", type).apply(context.getPokemon());
            EventUtils.updatePackets(pokemonBattle, battlePokemon);
            return Unit.INSTANCE;
        });
    }

    public static void Evolve(PokemonEntity context, Player player, BattlePokemon battlePokemon, PokemonBattle pokemonBattle) {
        if (player.level().isClientSide) {
            return;
        }

        Pokemon pokemon = context.getPokemon();
        ItemStack heldItem = pokemon.heldItem();
        boolean hasMega = FormChangeHelper.hasMega((ServerPlayer) player);

        if (hasMega && !MegaShowdownConfig.multipleMegas) {
            player.displayClientMessage(Component.translatable("message.mega_showdown.mega_limit")
                    .withColor(0xFF0000), true);
            return;
        }

        if (pokemon.getSpecies().getName().equals("Rayquaza")) {
            megaEvolve(context, "mega");
            return;
        }


        for (MegaData megaPok : Utils.megaRegistry) {
            ResourceLocation paperId = ResourceLocation.tryParse(megaPok.item_id());
            Item paperItem = BuiltInRegistries.ITEM.get(paperId);

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
                    player.displayClientMessage(Component.translatable("message.mega_showdown.incorrect_mega_stone")
                            .withColor(0xFF0000), true);
                    return;
                }
                return;
            }
            for (List<String> condition : megaPok.required_aspects()) {
                if (pokemon.getAspects().containsAll(condition)) {
                    if (candidateSpecies.equals(pokemon.getSpecies().getName())) {
                        megaEvolve(context, megaPok.apply_aspect(), battlePokemon, pokemonBattle);
                        pokemon.setTradeable(false);
                    } else {
                        player.displayClientMessage(Component.translatable("message.mega_showdown.incorrect_mega_stone")
                                .withColor(0xFF0000), true);
                        return;
                    }
                    return;
                }
            }
        }
    }

    public static void playDevolveAnimation(LivingEntity context) {
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

    //NPC
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
            ResourceLocation paperId = ResourceLocation.tryParse(megaPok.item_id());
            Item paperItem = BuiltInRegistries.ITEM.get(paperId);

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
