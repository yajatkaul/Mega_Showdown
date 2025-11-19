package com.github.yajatkaul.mega_showdown.gimmick;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.pc.PCStore;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.components.MegaShowdownDataComponents;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.datapack.MegaShowdownDatapackRegister;
import com.github.yajatkaul.mega_showdown.gimmick.codec.AspectSetCodec;
import com.github.yajatkaul.mega_showdown.item.custom.gimmick.MegaStone;
import com.github.yajatkaul.mega_showdown.utils.AspectUtils;
import com.github.yajatkaul.mega_showdown.utils.ParticlesList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Unit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record MegaGimmick(
        String showdown_id,
        List<String> pokemons,
        AspectSetCodec aspect_conditions
) {
    private static final Set<String> mega_aspects = new HashSet<>(Set.of("mega", "mega_y", "mega_x"));

    public static void appendMegaAspect(String aspect) {
        mega_aspects.add(aspect);
    }

    public static Set<String> getMegaAspects() {
        return mega_aspects;
    }

    public static final Codec<MegaGimmick> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("showdown_id").forGetter(MegaGimmick::showdown_id),
            Codec.list(Codec.STRING).fieldOf("pokemons").forGetter(MegaGimmick::pokemons),
            AspectSetCodec.CODEC.fieldOf("aspect").forGetter(MegaGimmick::aspect_conditions)
    ).apply(instance, MegaGimmick::new));

    public boolean canMega(Pokemon pokemon) {
        ServerPlayer player = pokemon.getOwnerPlayer();
        if (player != null && hasMega(player)) {
            player.displayClientMessage(Component.translatable("message.mega_showdown.mega_limit")
                    .withStyle(ChatFormatting.RED), true);
            return false;
        }

        if (!this.aspect_conditions.validate_apply(pokemon)) {
            return false;
        }

        return this.pokemons.contains(pokemon.getSpecies().getName());
    }

    public static boolean hasMega(ServerPlayer player) {
        if (MegaShowdownConfig.multipleMegas) {
            return false;
        }

        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
        PCStore pcStore = Cobblemon.INSTANCE.getStorage().getPC(player);

        for (Pokemon pokemon : playerPartyStore) {
            if (pokemon.getAspects().stream().anyMatch(mega_aspects::contains)) {
                return true;
            }
        }

        for (Pokemon pokemon : pcStore) {
            if (pokemon.getAspects().stream().anyMatch(mega_aspects::contains)) {
                return true;
            }
        }

        return false;
    }

    public static void megaEvolveInBattle(Pokemon pokemon, BattlePokemon battlePokemon, List<String> aspects, List<String> revertAspects) {
        PokemonBattle battle = battlePokemon.actor.getBattle();
        battle.dispatchWaitingToFront(5.9F, () -> Unit.INSTANCE);

        AspectUtils.appendRevertDataPokemon(
                ParticlesList.getEffect("mega_showdown:mega_evolution"),
                revertAspects,
                pokemon,
                "battle_end_revert"
        );

        ParticlesList.getEffect("mega_showdown:mega_evolution").applyEffectsBattle(pokemon, aspects, null, battlePokemon);
    }

    private static void megaEvolve(Pokemon pokemon, List<String> aspects, List<String> revertAspects) {
        ParticlesList.getEffect("mega_showdown:mega_evolution").applyEffects(pokemon, aspects, null);

        AspectUtils.appendRevertDataPokemon(
                ParticlesList.getEffect("mega_showdown:mega_evolution"),
                revertAspects,
                pokemon,
                "revert_aspects"
        );

        pokemon.setTradeable(false);
    }

    public static void megaToggle(PokemonEntity pokemonEntity) {
        if (!MegaShowdownConfig.outSideMega || pokemonEntity.getPokemon().getPersistentData().getBoolean("form_changing")) {
            return;
        }

        ItemStack heldItem = pokemonEntity.getPokemon().heldItem();
        MegaGimmick megaGimmick;

        if (heldItem.getItem() instanceof MegaStone megaStone) {
            megaGimmick = MegaShowdownDatapackRegister.MEGA_REGISTRY.get(megaStone.getMegaStone());
        } else {
            megaGimmick = heldItem.get(MegaShowdownDataComponents.MEGA_STONE_COMPONENT.get());
        }

        Pokemon pokemon = pokemonEntity.getPokemon();

        if (megaGimmick != null || pokemon.getSpecies().getName().equals("Rayquaza")) {
            if (pokemon.getAspects().stream().anyMatch(mega_aspects::contains)) {
                if (pokemon.getSpecies().getName().equals("Rayquaza")) {
                    ParticlesList.getEffect("mega_showdown:mega_evolution").revertEffects(pokemon, List.of("mega_evolution=none"), null);
                } else {
                    ParticlesList.getEffect("mega_showdown:mega_evolution").revertEffects(pokemon, megaGimmick.aspect_conditions.revert_aspects(), null);
                }
                pokemon.setTradeable(true);
            } else if (pokemonEntity.getPokemon().getSpecies().getName().equals("Rayquaza")) {
                boolean found = false;
                for (int i = 0; i < 4; i++) {
                    if (pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")) {
                        MegaGimmick.megaEvolve(pokemon, List.of("mega_evolution=mega"), List.of("mega_evolution=none"));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (pokemon.getOwnerPlayer() instanceof ServerPlayer player) {
                        player.displayClientMessage(Component.translatable("message.mega_showdown.rayquaza_no_dragonascent")
                                .withStyle(ChatFormatting.RED), true);
                    }
                }
            } else if (megaGimmick.canMega(pokemon)) {
                MegaGimmick.megaEvolve(pokemon, megaGimmick.aspect_conditions.apply_aspects(), megaGimmick.aspect_conditions.revert_aspects());
            }
        }
    }
}
