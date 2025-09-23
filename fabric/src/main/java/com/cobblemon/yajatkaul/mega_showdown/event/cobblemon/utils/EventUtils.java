package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.battles.model.actor.ActorType;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.moves.Moves;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.battles.ActiveBattlePokemon;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.net.messages.client.battle.BattleTransformPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.battle.BattleUpdateTeamPokemonPacket;
import com.cobblemon.mod.common.net.messages.client.pokemon.update.AbilityUpdatePacket;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.BattleFormChange;
import com.cobblemon.yajatkaul.mega_showdown.datapack.handler.HandlerUtils;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.FormChangeHelper;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.UltraLogic;
import com.cobblemon.yajatkaul.mega_showdown.item.CompiItems;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

public class EventUtils {
    public static void giveItems(ServerPlayerEntity player, ItemStack itemStack) {
        if (player.getInventory().getEmptySlot() == -1) {
            ItemEntity itemEntity = new ItemEntity(
                    player.getWorld(),
                    player.getX(),
                    player.getY() + 1,
                    player.getZ(),
                    itemStack
            );
            itemEntity.setPickupDelay(20);
            player.getWorld().spawnEntity(itemEntity);
        } else {
            player.giveItemStack(itemStack);
        }
    }

    public static void revertFormesEnd(Pokemon pokemon) {
        if (pokemon.getEntity() != null) {
            pokemon.getEntity().removeStatusEffect(StatusEffects.GLOWING);
            DynamaxUtils.resetToDefaultSize(pokemon.getEntity());
        }

        if (pokemon.getAspects().contains("gmax")) {
            new StringSpeciesFeature("dynamax_form", "none").apply(pokemon);
        }

        if (pokemon.getPersistentData().getBoolean("is_tera")) {
            pokemon.getPersistentData().remove("is_tera");
        }

        boolean isMega = (pokemon.getAspects().stream().anyMatch(FormChangeHelper.mega_aspects::contains));

        if ((MegaShowdownConfig.revertMegas.get() || MegaShowdownConfig.battleModeOnly.get()) && isMega) {
            MegaLogic.Devolve(pokemon, true);
        }

        if (pokemon.getSpecies().getName().equals("Castform")) {
            new StringSpeciesFeature("forecast_form", "normal").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Aegislash")) {
            new StringSpeciesFeature("stance_forme", "shield").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Greninja") && pokemon.getAspects().contains("ash")) {
            new StringSpeciesFeature("battle_bond", "bond").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Morpeko")) {
            new StringSpeciesFeature("hunger_mode", "full_belly").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Mimikyu")) {
            new StringSpeciesFeature("disguise_form", "disguised").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Ogerpon")) {
            new FlagSpeciesFeature("embody-aspect", false).apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Wishiwashi")) {
            new StringSpeciesFeature("schooling_form", "solo").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Eiscue")) {
            new StringSpeciesFeature("penguin_head", "ice_face").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Cramorant")) {
            new StringSpeciesFeature("missile_form", "none").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Palafin")) {
            new StringSpeciesFeature("dolphin_form", "zero").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Cherrim")) {
            new StringSpeciesFeature("blossom_form", "overcast").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Darmanitan")) {
            new StringSpeciesFeature("blazing_mode", "standard").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Arceus") && pokemon.heldItem().isOf(CompiItems.LEGEND_PLATE)) {
            new StringSpeciesFeature("multitype", "normal").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Xerneas")) {
            new StringSpeciesFeature("life_mode", "neutral").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Necrozma") && pokemon.getAspects().contains("ultra-fusion")) {
            if (pokemon.getEntity() != null) {
                UltraLogic.ultraAnimation(pokemon.getEntity());
            }
            new StringSpeciesFeature("prism_fusion", pokemon.getPersistentData().getString("fusion_form")).apply(pokemon);
            pokemon.getPersistentData().remove("fusion_form");
        } else if (pokemon.getSpecies().getName().equals("Terapagos")) {
            new StringSpeciesFeature("tera_form", "normal").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Meloetta") && pokemon.getAspects().contains("pirouette-forme")) {
            new StringSpeciesFeature("song_forme", "aria").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Zygarde")) {
            if (!pokemon.getPersistentData().getString("zygarde_form").isEmpty()) {
                new StringSpeciesFeature("percent_cells", pokemon.getPersistentData().getString("zygarde_form")).apply(pokemon);
            }
            pokemon.getPersistentData().remove("zygarde_form");
        }
        //REVERSE
        else if (pokemon.getSpecies().getName().equals("Palkia") && pokemon.getAspects().contains("origin-forme")
                && !pokemon.heldItem().isOf(FormeChangeItems.LUSTROUS_GLOBE)) {
            new StringSpeciesFeature("orb_forme", "altered").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Dialga") && pokemon.getAspects().contains("origin-forme")
                && !pokemon.heldItem().isOf(FormeChangeItems.ADAMANT_CRYSTAL)) {
            new StringSpeciesFeature("orb_forme", "altered").apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Giratina") && pokemon.getAspects().contains("origin-forme")
                && !pokemon.heldItem().isOf(FormeChangeItems.GRISEOUS_CORE)) {
            new StringSpeciesFeature("orb_forme", "altered").apply(pokemon);
        }

        // DATAPACK
        for (BattleFormChange forme : Utils.battleFormRegistry) {
            if (forme.pokemons().contains(pokemon.getSpecies().getName()) && pokemon.getAspects().contains(forme.showdown_form_id_apply())) {
                HandlerUtils.applyAspects(forme.revert_aspects(), pokemon);
            }
        }

        NbtCompound pokemonPersistentData = pokemon.getPersistentData();
        if (pokemonPersistentData.contains("revert_aspect")) {
            HandlerUtils.applyAspects(HandlerUtils.decodeNbt(pokemonPersistentData.getList("revert_aspect", NbtElement.STRING_TYPE)), pokemon);
        }

        if (pokemon.getSpecies().getName().equals("Keldeo")) {
            boolean hasMove = false;

            for (Move move : pokemon.getMoveSet().getMoves()) {
                if (move.getName().equals(Moves.INSTANCE.getByName("secretsword").getName())) {
                    hasMove = true;
                }
            }

            if (pokemon.getAspects().contains("resolute-form")) {
                if (!hasMove) {
                    new StringSpeciesFeature("sword_form", "ordinary").apply(pokemon);
                    if (pokemon.getEntity() != null) {
                        EventUtils.playEvolveAnimation(pokemon.getEntity());
                    }
                }
            } else {
                if (hasMove) {
                    new StringSpeciesFeature("sword_form", "resolute").apply(pokemon);
                    if (pokemon.getEntity() != null) {
                        EventUtils.playEvolveAnimation(pokemon.getEntity());
                    }
                }
            }
        }
    }

    public static void playFormeChangeAnimation(LivingEntity context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, // Yarn mapping for BEACON_ACTIVATE
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

    public static void playFormeChangeAngryAnimation(LivingEntity context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, // Yarn mapping for BEACON_ACTIVATE
                    SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (10 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverWorld.spawnParticles(
                        ParticleTypes.ANGRY_VILLAGER, // Same particle type
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

    public static void playEvolveAnimation(LivingEntity context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, // Yarn mapping for BEACON_ACTIVATE
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

    public static void updatePackets(PokemonBattle battle, BattlePokemon pk) {
        Pokemon pokemon = pk.getEntity().getPokemon();

        if (pk.actor.getType().equals(ActorType.PLAYER)) {
            battle.sendUpdate(new AbilityUpdatePacket(pk::getEffectedPokemon, pokemon.getAbility().getTemplate()));
            battle.sendUpdate(new BattleUpdateTeamPokemonPacket(pokemon));
        }

        for (ActiveBattlePokemon activeBattlePokemon : battle.getActivePokemon()) {
            if (!pk.actor.getType().equals(ActorType.PLAYER)) {
                continue;
            }
            if (activeBattlePokemon.getBattlePokemon() != null &&
                    activeBattlePokemon.getBattlePokemon().getEffectedPokemon().getOwnerPlayer() == pk.getEffectedPokemon().getOwnerPlayer()
                    && activeBattlePokemon.getBattlePokemon() == pk) {
                battle.sendSidedUpdate(activeBattlePokemon.getActor(),
                        new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pk, true),
                        new BattleTransformPokemonPacket(activeBattlePokemon.getPNX(), pk, false),
                        false);
            }
        }
    }
}
