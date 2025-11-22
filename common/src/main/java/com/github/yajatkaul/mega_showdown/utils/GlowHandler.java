package com.github.yajatkaul.mega_showdown.utils;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.api.types.tera.elemental.ElementalTypeTeraType;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.github.yajatkaul.mega_showdown.codec.ZCrystal;
import net.minecraft.ChatFormatting;
import net.minecraft.server.ServerScoreboard;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.scores.PlayerTeam;

import java.util.Locale;

public class GlowHandler {
    public static void applyDynamaxGlow(PokemonEntity pokemonEntity) {
        if (pokemonEntity.level() instanceof ServerLevel serverLevel) {
            pokemonEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, Integer.MAX_VALUE, 0, false, false));
            ServerScoreboard scoreboard = serverLevel.getScoreboard();

            String teamName;
            ChatFormatting teamColour;
            if (pokemonEntity.getPokemon().getSpecies().getName().equalsIgnoreCase("calyrex")) {
                teamName = "glow_dynamax_blue";
                teamColour = ChatFormatting.BLUE;
            } else {
                teamName = "glow_dynamax_red";
                teamColour = ChatFormatting.RED;
            }

            PlayerTeam team = scoreboard.getPlayerTeam(teamName);
            if (team == null) {
                team = scoreboard.addPlayerTeam(teamName);
                team.setColor(teamColour);
            }
            scoreboard.addPlayerToTeam(pokemonEntity.getScoreboardName(), team);
        }
    }

    public static void applyTeraGlow(PokemonEntity pokemon) {
        if (pokemon.level() instanceof ServerLevel serverLevel) {
            pokemon.addEffect(new MobEffectInstance(MobEffects.GLOWING, Integer.MAX_VALUE, 0, false, false));
            ServerScoreboard scoreboard = serverLevel.getScoreboard();
            String teamName = "glow_tera_" + pokemon.getPokemon().getTeraType().showdownId();

            PlayerTeam team = scoreboard.getPlayerTeam(teamName);

            ChatFormatting color = getGlowForTera(pokemon.getPokemon().getTeraType());
            if (team == null) {
                team = scoreboard.addPlayerTeam(teamName);
                team.setColor(color);
            }

            scoreboard.addPlayerToTeam(pokemon.getScoreboardName(), team);
        }
    }

    public static void applyZGlow(PokemonEntity pokemon, ZCrystal zCrystal) {
        if (pokemon.level() instanceof ServerLevel serverLevel) {
            pokemon.addEffect(new MobEffectInstance(MobEffects.GLOWING, 140, 0, false, false));
            ServerScoreboard scoreboard = serverLevel.getScoreboard();
            String teamName = "glow_type_" + zCrystal.color().toLowerCase(Locale.ROOT);

            PlayerTeam team = scoreboard.getPlayerTeam(teamName);

            ChatFormatting color = getGlowForColor(zCrystal.color());
            if (team == null) {
                team = scoreboard.addPlayerTeam(teamName);
                team.setColor(color);
            }

            scoreboard.addPlayerToTeam(pokemon.getScoreboardName(), team);
        }
    }

    private static ChatFormatting getGlowForTera(TeraType teraType) {
        if (teraType instanceof ElementalTypeTeraType elementalTera) {
            return getGlowForElemental(elementalTera.getType());
        }
        return ChatFormatting.WHITE;
    }

    private static ChatFormatting getGlowForElemental(ElementalType type) {
        if (type.equals(ElementalTypes.BUG)) return ChatFormatting.DARK_GREEN;
        if (type.equals(ElementalTypes.DARK)) return ChatFormatting.BLACK;
        if (type.equals(ElementalTypes.DRAGON)) return ChatFormatting.DARK_BLUE;
        if (type.equals(ElementalTypes.ELECTRIC)) return ChatFormatting.YELLOW;
        if (type.equals(ElementalTypes.FAIRY)) return ChatFormatting.LIGHT_PURPLE;
        if (type.equals(ElementalTypes.FIGHTING)) return ChatFormatting.DARK_RED;
        if (type.equals(ElementalTypes.FIRE)) return ChatFormatting.RED;
        if (type.equals(ElementalTypes.FLYING)) return ChatFormatting.GRAY;
        if (type.equals(ElementalTypes.GHOST)) return ChatFormatting.DARK_PURPLE;
        if (type.equals(ElementalTypes.GRASS)) return ChatFormatting.GREEN;
        if (type.equals(ElementalTypes.GROUND)) return ChatFormatting.DARK_RED;
        if (type.equals(ElementalTypes.ICE)) return ChatFormatting.AQUA;
        if (type.equals(ElementalTypes.NORMAL)) return ChatFormatting.WHITE;
        if (type.equals(ElementalTypes.POISON)) return ChatFormatting.DARK_PURPLE;
        if (type.equals(ElementalTypes.PSYCHIC)) return ChatFormatting.LIGHT_PURPLE;
        if (type.equals(ElementalTypes.ROCK)) return ChatFormatting.DARK_GRAY;
        if (type.equals(ElementalTypes.STEEL)) return ChatFormatting.GRAY;
        if (type.equals(ElementalTypes.WATER)) return ChatFormatting.BLUE;
        return ChatFormatting.WHITE;
    }

    private static ChatFormatting getGlowForColor(String color) {
        if (color == null) return ChatFormatting.WHITE;

        return switch (color.toLowerCase()) {
            case "black" -> ChatFormatting.BLACK;
            case "dark_blue" -> ChatFormatting.DARK_BLUE;
            case "dark_green" -> ChatFormatting.DARK_GREEN;
            case "dark_aqua" -> ChatFormatting.DARK_AQUA;
            case "dark_red" -> ChatFormatting.DARK_RED;
            case "dark_purple" -> ChatFormatting.DARK_PURPLE;
            case "gold" -> ChatFormatting.GOLD;
            case "gray" -> ChatFormatting.GRAY;
            case "dark_gray" -> ChatFormatting.DARK_GRAY;
            case "blue" -> ChatFormatting.BLUE;
            case "green" -> ChatFormatting.GREEN;
            case "aqua" -> ChatFormatting.AQUA;
            case "red" -> ChatFormatting.RED;
            case "light_purple" -> ChatFormatting.LIGHT_PURPLE;
            case "yellow" -> ChatFormatting.YELLOW;
            default -> ChatFormatting.WHITE;
        };
    }
}