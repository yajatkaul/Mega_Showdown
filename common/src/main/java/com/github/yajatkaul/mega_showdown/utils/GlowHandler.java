package com.github.yajatkaul.mega_showdown.utils;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.api.types.tera.elemental.ElementalTypeTeraType;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.github.yajatkaul.mega_showdown.item.custom.z.ElementalZCrystal;
import com.github.yajatkaul.mega_showdown.item.custom.z.SpecialZCrystal;
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

    public static void applyZGlow(PokemonEntity pokemon) {
        ElementalType type;
        if (pokemon.getPokemon().heldItem().getItem() instanceof ElementalZCrystal crystal) {
            type = crystal.getElement();
        } else if (pokemon.getPokemon().heldItem().getItem() instanceof SpecialZCrystal crystal) {
            type = crystal.getElement();
        } else {
            type = pokemon.getPokemon().getPrimaryType();
        }

        if (pokemon.level() instanceof ServerLevel serverLevel) {
            pokemon.addEffect(new MobEffectInstance(MobEffects.GLOWING, 140, 0, false, false));
            ServerScoreboard scoreboard = serverLevel.getScoreboard();
            String teamName = "glow_type_" + type.getName().toLowerCase(Locale.ROOT);

            PlayerTeam team = scoreboard.getPlayerTeam(teamName);

            ChatFormatting color = getGlowForElemental(type);
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
        if (type.equals(ElementalTypes.INSTANCE.getBUG())) return ChatFormatting.DARK_GREEN;
        if (type.equals(ElementalTypes.INSTANCE.getDARK())) return ChatFormatting.BLACK;
        if (type.equals(ElementalTypes.INSTANCE.getDRAGON())) return ChatFormatting.DARK_BLUE;
        if (type.equals(ElementalTypes.INSTANCE.getELECTRIC())) return ChatFormatting.YELLOW;
        if (type.equals(ElementalTypes.INSTANCE.getFAIRY())) return ChatFormatting.LIGHT_PURPLE;
        if (type.equals(ElementalTypes.INSTANCE.getFIGHTING())) return ChatFormatting.DARK_RED;
        if (type.equals(ElementalTypes.INSTANCE.getFIRE())) return ChatFormatting.RED;
        if (type.equals(ElementalTypes.INSTANCE.getFLYING())) return ChatFormatting.GRAY;
        if (type.equals(ElementalTypes.INSTANCE.getGHOST())) return ChatFormatting.DARK_PURPLE;
        if (type.equals(ElementalTypes.INSTANCE.getGRASS())) return ChatFormatting.GREEN;
        if (type.equals(ElementalTypes.INSTANCE.getGROUND())) return ChatFormatting.DARK_RED;
        if (type.equals(ElementalTypes.INSTANCE.getICE())) return ChatFormatting.AQUA;
        if (type.equals(ElementalTypes.INSTANCE.getNORMAL())) return ChatFormatting.WHITE;
        if (type.equals(ElementalTypes.INSTANCE.getPOISON())) return ChatFormatting.DARK_PURPLE;
        if (type.equals(ElementalTypes.INSTANCE.getPSYCHIC())) return ChatFormatting.LIGHT_PURPLE;
        if (type.equals(ElementalTypes.INSTANCE.getROCK())) return ChatFormatting.DARK_GRAY;
        if (type.equals(ElementalTypes.INSTANCE.getSTEEL())) return ChatFormatting.GRAY;
        if (type.equals(ElementalTypes.INSTANCE.getWATER())) return ChatFormatting.BLUE;
        return ChatFormatting.WHITE;
    }
}