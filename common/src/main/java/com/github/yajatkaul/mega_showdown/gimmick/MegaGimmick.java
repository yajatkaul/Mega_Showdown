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
import com.github.yajatkaul.mega_showdown.gimmick.codec.AspectSetCodec;
import com.github.yajatkaul.mega_showdown.utils.AspectUtils;
import com.github.yajatkaul.mega_showdown.utils.ParticlesList;
import com.mojang.datafixers.util.Pair;
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
        List<String> pokemon,
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
            Codec.list(Codec.STRING).fieldOf("pokemon").forGetter(MegaGimmick::pokemon),
            AspectSetCodec.CODEC.fieldOf("aspect_conditions").forGetter(MegaGimmick::aspect_conditions)
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

        return this.pokemon.contains(pokemon.getSpecies().getName());
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

    public static MegaGimmick ABOMASITE = createModItemMegaStone("abomasite", "Abomasnow", "mega");
    public static MegaGimmick ABSOLITE = createModItemMegaStone("absolite", "Absol", "mega");
    public static MegaGimmick AERODACTYLITE = createModItemMegaStone("aerodactylite", "Aerodactyl", "mega");
    public static MegaGimmick AGGRONITE = createModItemMegaStone("aggronite", "Aggron", "mega");
    public static MegaGimmick ALAKAZITE = createModItemMegaStone("alakazite", "Alakazam", "mega");
    public static MegaGimmick ALTARIANITE = createModItemMegaStone("altarianite", "Altaria", "mega");
    public static MegaGimmick AMPHAROSITE = createModItemMegaStone("ampharosite", "Ampharos", "mega");
    public static MegaGimmick AUDINITE = createModItemMegaStone("audinite", "Audino", "mega");
    public static MegaGimmick BANETTITE = createModItemMegaStone("banettite", "Banette", "mega");
    public static MegaGimmick BEEDRILLITE = createModItemMegaStone("beedrillite", "Beedrill", "mega");
    public static MegaGimmick BLASTOISINITE = createModItemMegaStone("blastoisinite", "Blastoise", "mega");
    public static MegaGimmick BLAZIKENITE = createModItemMegaStone("blazikenite", "Blaziken", "mega");
    public static MegaGimmick CAMERUPTITE = createModItemMegaStone("cameruptite", "Camerupt", "mega");
    public static MegaGimmick CHARIZARDITE_X = createModItemMegaStone("charizarditex", "Charizard", "mega_x");
    public static MegaGimmick CHARIZARDITE_Y = createModItemMegaStone("charizarditey", "Charizard", "mega_y");
    public static MegaGimmick DIANCITE = createModItemMegaStone("diancite", "Diancie", "mega");
    public static MegaGimmick GALLADITE = createModItemMegaStone("galladite", "Gallade", "mega");
    public static MegaGimmick GLALITITE = createModItemMegaStone("glalitite", "Glalie", "mega");
    public static MegaGimmick GARCHOMPITE = createModItemMegaStone("garchompite", "Garchomp", "mega");
    public static MegaGimmick GARDEVOIRITE = createModItemMegaStone("gardevoirite", "Gardevoir", "mega");
    public static MegaGimmick GENGARITE = createModItemMegaStone("gengarite", "Gengar", "mega");
    public static MegaGimmick GYARADOSITE = createModItemMegaStone("gyaradosite", "Gyarados", "mega");
    public static MegaGimmick HERACRONITE = createModItemMegaStone("heracronite", "Heracross", "mega");
    public static MegaGimmick HOUNDOOMINITE = createModItemMegaStone("houndoominite", "Houndoom", "mega");
    public static MegaGimmick KANGASKHANITE = createModItemMegaStone("kangaskhanite", "Kangaskhan", "mega");
    public static MegaGimmick LATIASITE = createModItemMegaStone("latiasite", "Latias", "mega");
    public static MegaGimmick LATIOSITE = createModItemMegaStone("latiosite", "Latios", "mega");
    public static MegaGimmick LOPUNNITE = createModItemMegaStone("lopunnite", "Lopunny", "mega");
    public static MegaGimmick LUCARIONITE = createModItemMegaStone("lucarionite", "Lucario", "mega");
    public static MegaGimmick MANECTITE = createModItemMegaStone("manectite", "Manectric", "mega");
    public static MegaGimmick MAWILITE = createModItemMegaStone("mawilite", "Mawile", "mega");
    public static MegaGimmick MEDICHAMITE = createModItemMegaStone("medichamite", "Medicham", "mega");
    public static MegaGimmick METAGROSSITE = createModItemMegaStone("metagrossite", "Metagross", "mega");
    public static MegaGimmick MEWTWONITE_X = createModItemMegaStone("mewtwonitex", "Mewtwo", "mega_x");
    public static MegaGimmick MEWTWONITE_Y = createModItemMegaStone("mewtwonitey", "Mewtwo", "mega_y");
    public static MegaGimmick PIDGEOTITE = createModItemMegaStone("pidgeotite", "Pidgeot", "mega");
    public static MegaGimmick PINSIRITE = createModItemMegaStone("pinsirite", "Pinsir", "mega");
    public static MegaGimmick SABLENITE = createModItemMegaStone("sablenite", "Sableye", "mega");
    public static MegaGimmick SALAMENCITE = createModItemMegaStone("salamencite", "Salamence", "mega");
    public static MegaGimmick SCEPTILITE = createModItemMegaStone("sceptilite", "Sceptile", "mega");
    public static MegaGimmick SCIZORITE = createModItemMegaStone("scizorite", "Scizor", "mega");
    public static MegaGimmick SHARPEDONITE = createModItemMegaStone("sharpedonite", "Sharpedo", "mega");
    public static MegaGimmick SLOWBRONITE = createModItemMegaStoneWithBlackList("slowbronite", "Slowbro", "mega", "galarian");
    public static MegaGimmick STEELIXITE = createModItemMegaStone("steelixite", "Steelix", "mega");
    public static MegaGimmick SWAMPERTITE = createModItemMegaStone("swampertite", "Swampert", "mega");
    public static MegaGimmick TYRANITARITE = createModItemMegaStone("tyranitarite", "Tyranitar", "mega");
    public static MegaGimmick VENUSAURITE = createModItemMegaStone("venusaurite", "Venusaur", "mega");

    public static MegaGimmick createModItemMegaStone(String mega_stone_id, String pokemon, String aspect) {
        String mega_aspect = "mega_evolution=" + aspect;
        return new MegaGimmick(
                mega_stone_id,
                List.of(pokemon),
                new AspectSetCodec(
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of(mega_aspect),
                        List.of("mega_evolution=none")
                )
        );
    }

    public static MegaGimmick createModItemMegaStoneWithBlackList(String mega_stone_id,
                                                                  String pokemon,
                                                                  String aspect,
                                                                  String blackListAspect
    ) {
        String mega_aspect = "mega_evolution=" + aspect;
        return new MegaGimmick(
                mega_stone_id,
                List.of(pokemon),
                new AspectSetCodec(
                        List.of(),
                        List.of(),
                        List.of(),
                        List.of(List.of(blackListAspect)),
                        List.of(mega_aspect),
                        List.of("mega_evolution=none")
                )
        );
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

    private static void megaEvolve(Pokemon pokemon, List<String> aspects) {
        ParticlesList.getEffect("mega_showdown:mega_evolution").applyEffects(pokemon, aspects, null);
        pokemon.setTradeable(false);
    }

    public static void megaToggle(PokemonEntity pokemonEntity) {
        if (!MegaShowdownConfig.outSideMega) {
            return;
        }

        ItemStack heldItem = pokemonEntity.getPokemon().heldItem();
        MegaGimmick megaGimmick = heldItem.get(MegaShowdownDataComponents.MEGA_STONE_COMPONENT.get());

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
                        MegaGimmick.megaEvolve(pokemon, List.of("mega"));
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
                MegaGimmick.megaEvolve(pokemon, megaGimmick.aspect_conditions().apply_aspects());
            }
        }
    }
}
