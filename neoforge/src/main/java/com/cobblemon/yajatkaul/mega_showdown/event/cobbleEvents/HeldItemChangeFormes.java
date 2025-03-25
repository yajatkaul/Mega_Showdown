package com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents;

import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.api.pokemon.feature.SpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.ZCrystals;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.ArceusPlates;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.Drives;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.Memories;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class HeldItemChangeFormes {
    public static void genesectChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();
        if(pokemon.getSpecies().getName().equals("Genesect")){
            if(post.getReceived().is(FormeChangeItems.DOUSE_DRIVE)){
                new StringSpeciesFeature("techno_drive","water").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.BURN_DRIVE)){
                new StringSpeciesFeature("techno_drive","fire").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.CHILL_DRIVE)){
                new StringSpeciesFeature("techno_drive","ice").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.SHOCK_DRIVE)){
                new StringSpeciesFeature("techno_drive","electric").apply(pokemon);
            }
            else if (!(post.getReceived().getItem() instanceof Drives) && post.getReturned().getItem() instanceof Drives) {
                new StringSpeciesFeature("techno_drive","none").apply(pokemon);
            }
        }
    }

    public static void silvallyChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();
        if(pokemon.getSpecies().getName().equals("Silvally")){
            if(post.getReceived().is(FormeChangeItems.BUG_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","bug").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.DARK_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","dark").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.DRAGON_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","dragon").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.ELECTRIC_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","electric").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.FAIRY_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","fairy").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.FIGHTING_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","fighting").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.FIRE_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","fire").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.FLYING_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","flying").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.GHOST_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","ghost").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.GRASS_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","grass").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.GROUND_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","ground").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.ICE_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","ice").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.POISON_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","poison").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.PSYCHIC_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","psychic").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.ROCK_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","rock").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.STEEL_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","steel").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.WATER_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","water").apply(pokemon);
            } else if (!(post.getReceived().getItem() instanceof Memories) && post.getReturned().getItem() instanceof Memories) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","normal").apply(pokemon);
            }
        }
    }

    public static void arcuesChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();
        if(pokemon.getSpecies().getName().equals("Arceus")){
            if(post.getReceived().is(FormeChangeItems.FLAME_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","fire").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.SPLASH_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","water").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.ZAP_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","electric").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.MEADOW_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","grass").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.ICICLE_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","ice").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.FIST_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","fighting").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.TOXIC_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","poison").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.EARTH_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","ground").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.SKY_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","flying").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.MIND_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","psychic").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.INSECT_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","bug").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.STONE_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","rock").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.SPOOKY_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","ghost").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.DRACO_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","dragon").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.DREAD_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","dark").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.IRON_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","steel").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.PIXIE_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","fairy").apply(pokemon);
            }else if(post.getReceived().is(ZCrystals.BUGINIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "bug").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.DARKINIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "dark").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.DRAGONIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "dragon").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.ELECTRIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "electric").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.FAIRIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "fairy").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.FIGHTINIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "fighting").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.FIRIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "fire").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.FLYINIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "flying").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.GHOSTIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "ghost").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.GRASSIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "grass").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.GROUNDIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "ground").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.ICIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "ice").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.POISONIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "poison").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.PSYCHIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "psychic").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.ROCKIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "rock").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.STEELIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "steel").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.WATERIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "water").apply(pokemon);
            } else if(!(post.getReceived().getItem() instanceof ArceusPlates) && post.getReturned().getItem() instanceof ArceusPlates){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","normal").apply(pokemon);
            }
        }
    }

    public static void originChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();

        if(pokemon.getSpecies().getName().equals("Giratina")){
            if(post.getReceived().is(FormeChangeItems.GRISEOUS_ORB)){
                EventUtils.originAnimation(pokemon.getEntity(), ParticleTypes.ASH);
                new StringSpeciesFeature("orb_forme","origin").apply(pokemon);
            }else if (post.getReturned().is(FormeChangeItems.GRISEOUS_ORB)){
                EventUtils.originAnimation(pokemon.getEntity(), ParticleTypes.ASH);
                new StringSpeciesFeature("orb_forme","altered").apply(pokemon);
            }
        } else if (pokemon.getSpecies().getName().equals("Palkia")) {
            if(post.getReceived().is(FormeChangeItems.LUSTROUS_GLOBE)){
                EventUtils.originAnimation(pokemon.getEntity(), ParticleTypes.END_ROD);
                new StringSpeciesFeature("orb_forme","origin").apply(pokemon);
            }else if (post.getReturned().is(FormeChangeItems.LUSTROUS_GLOBE)){
                EventUtils.originAnimation(pokemon.getEntity(), ParticleTypes.END_ROD);
                new StringSpeciesFeature("orb_forme","altered").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Dialga")) {
            if(post.getReceived().is(FormeChangeItems.ADAMANT_ORB)){
                EventUtils.originAnimation(pokemon.getEntity(), ParticleTypes.END_ROD);
                new StringSpeciesFeature("orb_forme","origin").apply(pokemon);
            }else if (post.getReturned().is(FormeChangeItems.ADAMANT_ORB)){
                EventUtils.originAnimation(pokemon.getEntity(), ParticleTypes.END_ROD);
                new StringSpeciesFeature("orb_forme","altered").apply(pokemon);
            }
        }
    }

    public static void eternamaxChange(HeldItemEvent.Post post){
        if(!Config.etermaxForme){
            return;
        }
        Pokemon pokemon = post.getPokemon();

        if(pokemon.getSpecies().getName().equals("Eternatus") && post.getReceived().is(FormeChangeItems.STAR_CORE)){
            new FlagSpeciesFeature("eternamax",true).apply(pokemon);
        } else if (pokemon.getSpecies().getName().equals("Eternatus")) {
            new FlagSpeciesFeature("eternamax",false).apply(pokemon);
        }
    }

    public static void ogerponChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();

        if(!pokemon.getSpecies().getName().equals("Ogerpon")){
            return;
        }
        if(post.getReceived().is(FormeChangeItems.HEARTHFLAME_MASK)){
            new StringSpeciesFeature("ogre_mask","hearthflame").apply(pokemon);
            try {
                pokemon.setTeraType(TeraTypes.getFIRE());
            }catch (Exception e){
                MegaShowdown.LOGGER.info("Sike");
            }
        } else if (post.getReceived().is(FormeChangeItems.CORNERSTONE_MASK)) {
            new StringSpeciesFeature("ogre_mask","cornerstone").apply(pokemon);
            try {
                pokemon.setTeraType(TeraTypes.getROCK());
            }catch (Exception e){
                MegaShowdown.LOGGER.info("Sike");
            }
        }else if (post.getReceived().is(FormeChangeItems.WELLSPRING_MASK)) {
            new StringSpeciesFeature("ogre_mask","wellspring").apply(pokemon);
            try {
                pokemon.setTeraType(TeraTypes.getWATER());
            }catch (Exception e){
                MegaShowdown.LOGGER.info("Sike");
            }
        }else {
            new StringSpeciesFeature("ogre_mask","teal").apply(pokemon);
            try {
                pokemon.setTeraType(TeraTypes.getGRASS());
            }catch (Exception e){
                MegaShowdown.LOGGER.info("Sike");
            }
        }
    }

    public static void primalEvent(HeldItemEvent.Post post) {
        ServerPlayer player = post.getPokemon().getOwnerPlayer();
        Species species = post.getPokemon().getSpecies();

        if(species.getName().equals(Utils.getSpecies("kyogre").getName()) && post.getReceived().is(MegaStones.BLUE_ORB)){
            if(player.getData(DataManage.PRIMAL_DATA) && !Config.multiplePrimals){
                player.displayClientMessage(Component.literal("You can only have one primal at a time")
                        .withColor(0xFF0000), true);
                return;
            }
            new FlagSpeciesFeature("primal", true).apply(post.getPokemon());
            EventUtils.primalRevertAnimation(post.getPokemon().getEntity(), ParticleTypes.BUBBLE);
            AdvancementHelper.grantAdvancement(player, "primal_evo");
            player.setData(DataManage.PRIMAL_DATA, true);
            setTradable(post.getPokemon(), false);
        }
        else if(species.getName().equals(Utils.getSpecies("groudon").getName()) && post.getReceived().is(MegaStones.RED_ORB)){
            if(player.getData(DataManage.PRIMAL_DATA) && !Config.multiplePrimals){
                player.displayClientMessage(Component.literal("You can only have one primal at a time")
                        .withColor(0xFF0000), true);
                return;
            }
            new FlagSpeciesFeature("primal", true).apply(post.getPokemon());
            EventUtils.primalRevertAnimation(post.getPokemon().getEntity(), ParticleTypes.CAMPFIRE_COSY_SMOKE);
            AdvancementHelper.grantAdvancement(player, "primal_evo");
            player.setData(DataManage.PRIMAL_DATA, true);
            setTradable(post.getPokemon(), false);
        }else{
            SpeciesFeature feature = post.getPokemon().getFeature("primal");
            if(feature == null){
                return;
            }

            new FlagSpeciesFeature("primal", false).apply(post.getPokemon());
            EventUtils.primalRevertAnimation(post.getPokemon().getEntity(), ParticleTypes.END_ROD);
            player.setData(DataManage.PRIMAL_DATA, false);
            setTradable(post.getPokemon(), true);
        }
    }

    public static void megaEvent(HeldItemEvent.Post event){
        Pokemon pokemon = event.getPokemon();

        if(pokemon.getEntity() == null){
            return;
        }

        Species species = Utils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());
        if(pokemon.getEntity().level().isClientSide){
            return;
        }

        List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

        for (String key : megaKeys) {
            FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));
            ServerPlayer player = pokemon.getOwnerPlayer();

            FlagSpeciesFeature feature = featureProvider.get(pokemon);
            if(feature != null){
                boolean enabled = featureProvider.get(pokemon).getEnabled();

                if (enabled && feature.getName().equals("mega") && (species != pokemon.getSpecies() || event.getReceived() != event.getReturned())) {
                    MegaLogic.Devolve(pokemon.getEntity(), player, true);
                }else if(enabled && feature.getName().equals("mega-x") && (species != pokemon.getSpecies() || event.getReceived() != event.getReturned())){
                    MegaLogic.Devolve(pokemon.getEntity(), player, true);
                } else if (enabled && feature.getName().equals("mega-y") && (species != pokemon.getSpecies() || event.getReceived() != event.getReturned())) {
                    MegaLogic.Devolve(pokemon.getEntity(), player, true);
                }
            }
        }
    }

    public static void crownedEvent(HeldItemEvent.Post event){
        Pokemon pokemon = event.getPokemon();

        if(pokemon.getSpecies().getName().equals("Zacian") ||
                pokemon.getSpecies().getName().equals("Zamazenta")){
            if(event.getReceived().is(FormeChangeItems.RUSTED_SWORD) && pokemon.getSpecies().getName().equals("Zacian")){
                EventUtils.crownAnimation((ServerLevel) pokemon.getEntity().level(), pokemon.getEntity().getOnPos(), pokemon.getEntity());
                new FlagSpeciesFeature("crowned", true).apply(pokemon);
                setTradable(pokemon, false);
            } else if (event.getReceived().is(FormeChangeItems.RUSTED_SHIELD) && pokemon.getSpecies().getName().equals("Zamazenta")) {
                EventUtils.crownAnimation((ServerLevel) pokemon.getEntity().level(), pokemon.getEntity().getOnPos(), pokemon.getEntity());
                new FlagSpeciesFeature("crowned", true).apply(pokemon);
                setTradable(pokemon, false);
            } else if(pokemon.getSpecies().getName().equals("Zacian") && event.getReturned().is(FormeChangeItems.RUSTED_SWORD)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new FlagSpeciesFeature("crowned", false).apply(pokemon);
                setTradable(pokemon, true);
            }else if(pokemon.getSpecies().getName().equals("Zamazenta") && event.getReturned().is(FormeChangeItems.RUSTED_SHIELD)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new FlagSpeciesFeature("crowned", false).apply(pokemon);
                setTradable(pokemon, true);
            }
        }
    }

    public static void ultraEvent(HeldItemEvent.Post event){
        Pokemon pokemon = event.getPokemon();

        if(pokemon.getSpecies().getName().equals("Necrozma")){
            if(event.getReturned().is(ZCrystals.ULTRANECROZIUM_Z)){
                EventUtils.ultraAnimation(pokemon.getEntity());
                new FlagSpeciesFeature("ultra",false).apply(pokemon);
            }
        }
    }
}
