package com.github.yajatkaul.mega_showdown.utils;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.List;

public class AspectUtils {
    public static void applyAspects(Pokemon pokemon, List<String> aspects) {
        for (String aspect: aspects) {
            String[] aspect_split = aspect.split("=");
            if(aspect_split[1].equals("true") || aspect_split[1].equals("false")) {
                new FlagSpeciesFeature(aspect_split[0], Boolean.parseBoolean(aspect_split[1])).apply(pokemon);
            } else {
                new StringSpeciesFeature(aspect_split[0], aspect_split[1]).apply(pokemon);
            }
        }
    }

    public static ListTag makeNbt(List<String> aspects) {
        ListTag nbtList = new ListTag();

        for (String aspect : aspects) {
            nbtList.add(StringTag.valueOf(aspect));
        }

        return nbtList;
    }

    public static List<String> decodeNbt(ListTag nbtList) {
        List<String> aspects = new ArrayList<>();

        for (Tag element : nbtList) {
            aspects.add(element.getAsString());
        }

        return aspects;
    }
}
