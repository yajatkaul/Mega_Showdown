package com.cobblemon.yajatkaul.mega_showdown.showdown;

import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.api.events.storage.ReleasePokemonEvent;
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import kotlin.Unit;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowdownUtils {
    public static final Map<Item, Species> MEGA_STONE_IDS = new HashMap<>();

    public static Unit onHeldItemChange(HeldItemEvent.Post event) {
        Pokemon pokemon = event.getPokemon();
        Species species = ShowdownUtils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());
        if(pokemon.getEntity().level().isClientSide){
            return Unit.INSTANCE;
        }

        List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

        for (String key : megaKeys) {
            FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));
            ServerPlayer player = pokemon.getOwnerPlayer();

            FlagSpeciesFeature feature = featureProvider.get(pokemon);
            if(feature != null){
                boolean enabled = featureProvider.get(pokemon).getEnabled();

                if (enabled && feature.getName().equals("mega") && (species != pokemon.getSpecies() || event.getReceived() != event.getReturned())) {
                    player.setData(DataManage.MEGA_DATA, false);
                    player.setData(DataManage.MEGA_POKEMON, new Pokemon());

                    new FlagSpeciesFeature("mega", false).apply(pokemon);

                }else if(enabled && feature.getName().equals("mega-x") && (species != pokemon.getSpecies() || event.getReceived() != event.getReturned())){
                    player.setData(DataManage.MEGA_DATA, false);
                    player.setData(DataManage.MEGA_POKEMON, new Pokemon());

                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);

                } else if (enabled && feature.getName().equals("mega-y") && (species != pokemon.getSpecies() || event.getReceived() != event.getReturned())) {
                    player.setData(DataManage.MEGA_DATA, false);
                    player.setData(DataManage.MEGA_POKEMON, new Pokemon());

                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                }
            }
        }


        return Unit.INSTANCE;
    }

    public static Unit onReleasePokemon(ReleasePokemonEvent.Post post) {
        if(!post.getPlayer().level().isClientSide && post.getPlayer().getData(DataManage.MEGA_POKEMON) == post.getPokemon()){
            post.getPlayer().setData(DataManage.MEGA_DATA, false);
            post.getPlayer().setData(DataManage.MEGA_POKEMON, new Pokemon());
        }

        return Unit.INSTANCE;
    }

    public static Species getSpecies(String name) {
        return PokemonSpecies.INSTANCE.getByName(name);
    }

    public static void loadMegaStoneIds() {
        MEGA_STONE_IDS.put(ModItems.VENUSAURITE.asItem(), getSpecies("venusaur"));
        MEGA_STONE_IDS.put(ModItems.CHARIZARDITE_X.asItem(), getSpecies("charizard"));
        MEGA_STONE_IDS.put(ModItems.CHARIZARDITE_Y.asItem(), getSpecies("charizard"));
        MEGA_STONE_IDS.put(ModItems.BLASTOISINITE.asItem(), getSpecies("blastoise"));
        MEGA_STONE_IDS.put(ModItems.ALAKAZITE.asItem(), getSpecies("alakazam"));
        MEGA_STONE_IDS.put(ModItems.GENGARITE.asItem(), getSpecies("gengar"));
        MEGA_STONE_IDS.put(ModItems.KANGASKHANITE.asItem(), getSpecies("kangaskhan"));
        MEGA_STONE_IDS.put(ModItems.PINSIRITE.asItem(), getSpecies("pinsir"));
        MEGA_STONE_IDS.put(ModItems.GYARADOSITE.asItem(), getSpecies("gyarados"));
        MEGA_STONE_IDS.put(ModItems.AERODACTYLITE.asItem(), getSpecies("aerodactyl"));
        MEGA_STONE_IDS.put(ModItems.MEWTWONITE_X.asItem(), getSpecies("mewtwo"));
        MEGA_STONE_IDS.put(ModItems.MEWTWONITE_Y.asItem(), getSpecies("mewtwo"));
        MEGA_STONE_IDS.put(ModItems.AMPHAROSITE.asItem(), getSpecies("ampharos"));
        MEGA_STONE_IDS.put(ModItems.SCIZORITE.asItem(), getSpecies("scizor"));
        MEGA_STONE_IDS.put(ModItems.HERACRONITE.asItem(), getSpecies("heracross"));
        MEGA_STONE_IDS.put(ModItems.HOUNDOOMINITE.asItem(), getSpecies("houndoom"));
        MEGA_STONE_IDS.put(ModItems.TYRANITARITE.asItem(), getSpecies("tyranitar"));
        MEGA_STONE_IDS.put(ModItems.BLAZIKENITE.asItem(), getSpecies("blaziken"));
        MEGA_STONE_IDS.put(ModItems.GARDEVOIRITE.asItem(), getSpecies("gardevoir"));
        MEGA_STONE_IDS.put(ModItems.MAWILITE.asItem(), getSpecies("mawile"));
        MEGA_STONE_IDS.put(ModItems.AGGRONITE.asItem(), getSpecies("aggron"));
        MEGA_STONE_IDS.put(ModItems.MEDICHAMITE.asItem(), getSpecies("medicham"));
        MEGA_STONE_IDS.put(ModItems.MANECTITE.asItem(), getSpecies("manectric"));
        MEGA_STONE_IDS.put(ModItems.BANETTITE.asItem(), getSpecies("banette"));
        MEGA_STONE_IDS.put(ModItems.ABSOLITE.asItem(), getSpecies("absol"));
        MEGA_STONE_IDS.put(ModItems.LATIASITE.asItem(), getSpecies("latias"));
        MEGA_STONE_IDS.put(ModItems.LATIOSITE.asItem(), getSpecies("latios"));
        MEGA_STONE_IDS.put(ModItems.GARCHOMPITE.asItem(), getSpecies("garchomp"));
        MEGA_STONE_IDS.put(ModItems.LUCARIONITE.asItem(), getSpecies("lucario"));
        MEGA_STONE_IDS.put(ModItems.ABOMASITE.asItem(), getSpecies("abomasnow"));
        MEGA_STONE_IDS.put(ModItems.BEEDRILLITE.asItem(), getSpecies("beedrill"));
        MEGA_STONE_IDS.put(ModItems.PIDGEOTITE.asItem(), getSpecies("pidgeot"));
        MEGA_STONE_IDS.put(ModItems.SLOWBRONITE.asItem(), getSpecies("slowbro"));
        MEGA_STONE_IDS.put(ModItems.STEELIXITE.asItem(), getSpecies("steelix"));
        MEGA_STONE_IDS.put(ModItems.SCEPTILITE.asItem(), getSpecies("sceptile"));
        MEGA_STONE_IDS.put(ModItems.SWAMPERTITE.asItem(), getSpecies("swampert"));
        MEGA_STONE_IDS.put(ModItems.SABLENITE.asItem(), getSpecies("sableye"));
        MEGA_STONE_IDS.put(ModItems.SHARPEDONITE.asItem(), getSpecies("sharpedo"));
        MEGA_STONE_IDS.put(ModItems.CAMERUPTITE.asItem(), getSpecies("camerupt"));
        MEGA_STONE_IDS.put(ModItems.ALTARIANITE.asItem(), getSpecies("altaria"));
        MEGA_STONE_IDS.put(ModItems.GLALITITE.asItem(), getSpecies("glalie"));
        MEGA_STONE_IDS.put(ModItems.SALAMENCITE.asItem(), getSpecies("salamence"));
        MEGA_STONE_IDS.put(ModItems.METAGROSSITE.asItem(), getSpecies("metagross"));
        MEGA_STONE_IDS.put(ModItems.LOPUNNITE.asItem(), getSpecies("lopunny"));
        MEGA_STONE_IDS.put(ModItems.GALLADITE.asItem(), getSpecies("gallade"));
        MEGA_STONE_IDS.put(ModItems.AUDINITE.asItem(), getSpecies("audino"));
        MEGA_STONE_IDS.put(ModItems.DIANCITE.asItem(), getSpecies("diancie"));
    }
}
