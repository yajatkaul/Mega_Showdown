package com.github.yajatkaul.mega_showdown.gimmick;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.pc.PCStore;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.codec.Effect;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.gimmick.codec.AspectSetCodec;
import com.github.yajatkaul.mega_showdown.tag.MegaShowdownTags;
import com.github.yajatkaul.mega_showdown.utils.AccessoriesUtils;
import com.github.yajatkaul.mega_showdown.utils.AspectUtils;
import com.github.yajatkaul.mega_showdown.utils.RegistryLocator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
    public static final Codec<MegaGimmick> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("showdown_id").forGetter(MegaGimmick::showdown_id),
            Codec.list(Codec.STRING).fieldOf("pokemons").forGetter(MegaGimmick::pokemons),
            AspectSetCodec.CODEC.fieldOf("aspect").forGetter(MegaGimmick::aspect_conditions)
    ).apply(instance, MegaGimmick::new));
    private static final Set<String> mega_aspects = new HashSet<>(Set.of("mega", "mega_y", "mega_x"));

    public static void appendMegaAspect(String aspect) {
        mega_aspects.add(aspect);
    }

    public static Set<String> getMegaAspects() {
        return mega_aspects;
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

    public static void megaEvolveInBattle(Pokemon pokemon, BattlePokemon battlePokemon) {
        ItemStack heldItem = pokemon.heldItem();
        MegaGimmick megaGimmick = RegistryLocator.getComponent(MegaGimmick.class, heldItem);

        if (megaGimmick != null || pokemon.getSpecies().getName().equals("Rayquaza")) {
            if (pokemon.getSpecies().getName().equals("Rayquaza")) {
                Effect.getEffect("mega_showdown:mega_evolution").applyEffectsBattle(pokemon, List.of("mega_evolution=mega"), null, battlePokemon);

                AspectUtils.appendRevertDataPokemon(
                        Effect.getEffect("mega_showdown:mega_evolution"),
                        List.of("mega_evolution=none"),
                        pokemon,
                        "battle_end_revert"
                );
            } else if (megaGimmick.canMega(pokemon)) {
                Effect.getEffect("mega_showdown:mega_evolution").applyEffectsBattle(pokemon, megaGimmick.aspect_conditions.apply_aspects(), null, battlePokemon);

                AspectUtils.appendRevertDataPokemon(
                        Effect.getEffect("mega_showdown:mega_evolution"),
                        megaGimmick.aspect_conditions.revert_aspects(),
                        pokemon,
                        "battle_end_revert"
                );
            }
        }
    }

    private static void megaEvolve(Pokemon pokemon, List<String> aspects, List<String> revertAspects) {
        AspectUtils.appendRevertDataPokemon(
                Effect.getEffect("mega_showdown:mega_evolution"),
                revertAspects,
                pokemon,
                "revert_aspects"
        );

        Effect.getEffect("mega_showdown:mega_evolution").applyEffects(pokemon, aspects, null);

        pokemon.setTradeable(false);
    }

    public static void megaToggle(Pokemon pokemon) {
        if (!MegaShowdownConfig.outSideMega ||
                pokemon == null ||
                pokemon.getPersistentData().getBoolean("form_changing")
        ) {
            return;
        }

        ServerPlayer player = pokemon.getOwnerPlayer();

        ItemStack heldItem = pokemon.heldItem();
        MegaGimmick megaGimmick = RegistryLocator.getComponent(MegaGimmick.class, heldItem);

        if (megaGimmick != null || pokemon.getSpecies().getName().equals("Rayquaza")) {
            if (pokemon.getAspects().stream().anyMatch(mega_aspects::contains)) {
                if (pokemon.getSpecies().getName().equals("Rayquaza")) {
                    Effect.getEffect("mega_showdown:mega_evolution").revertEffects(pokemon, List.of("mega_evolution=none"), null);
                } else {
                    Effect.getEffect("mega_showdown:mega_evolution").revertEffects(pokemon, megaGimmick.aspect_conditions.revert_aspects(), null);
                }
                pokemon.setTradeable(true);
            } else if (pokemon.getSpecies().getName().equals("Rayquaza")) {
                if (player != null &&
                        !AccessoriesUtils.checkTagInAccessories(player, MegaShowdownTags.Items.MEGA_BRACELET) &&
                        !AccessoriesUtils.checkTagInAccessories(player, MegaShowdownTags.Items.OMNI_RING)) {
                    player.displayClientMessage(Component.translatable("message.mega_showdown.no_mega_bracelet")
                            .withStyle(ChatFormatting.RED), true);
                    return;
                }

                boolean found = false;
                for (int i = 0; i < 4; i++) {
                    if (pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")) {
                        MegaGimmick.megaEvolve(pokemon, List.of("mega_evolution=mega"), List.of("mega_evolution=none"));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (player != null) {
                        player.displayClientMessage(Component.translatable("message.mega_showdown.rayquaza_no_dragonascent")
                                .withStyle(ChatFormatting.RED), true);
                    }
                }
            } else if (megaGimmick.canMega(pokemon)) {
                if (player != null &&
                        !AccessoriesUtils.checkTagInAccessories(player, MegaShowdownTags.Items.MEGA_BRACELET) &&
                        !AccessoriesUtils.checkTagInAccessories(player, MegaShowdownTags.Items.OMNI_RING)) {
                    player.displayClientMessage(Component.translatable("message.mega_showdown.no_mega_bracelet")
                            .withStyle(ChatFormatting.RED), true);
                    return;
                }

                MegaGimmick.megaEvolve(pokemon, megaGimmick.aspect_conditions.apply_aspects(), megaGimmick.aspect_conditions.revert_aspects());
            }
        } else {
            player.displayClientMessage(Component.translatable("message.mega_showdown.no_or_incorrect_mega_stone")
                    .withStyle(ChatFormatting.RED), true);
        }
    }

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
}
