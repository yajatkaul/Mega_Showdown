package com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents;

import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.api.pokemon.feature.SpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.ZCrystals;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.ArceusPlates;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.Drives;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.Memories;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

import java.util.List;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class HeldItemChangeFormes {
    public static void genesectChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();
        if(pokemon.getSpecies().getName().equals("Genesect")){
            if(post.getReceived().isOf(FormeChangeItems.DOUSE_DRIVE)){
                new StringSpeciesFeature("techno_drive","water").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.BURN_DRIVE)){
                new StringSpeciesFeature("techno_drive","fire").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.CHILL_DRIVE)){
                new StringSpeciesFeature("techno_drive","ice").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.SHOCK_DRIVE)){
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
            if(post.getReceived().isOf(FormeChangeItems.BUG_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","bug").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.DARK_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","dark").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.DRAGON_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","dragon").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.ELECTRIC_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","electric").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.FAIRY_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","fairy").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.FIGHTING_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","fighting").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.FIRE_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","fire").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.FLYING_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","flying").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.GHOST_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","ghost").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.GRASS_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","grass").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.GROUND_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","ground").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.ICE_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","ice").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.POISON_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","poison").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.PSYCHIC_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","psychic").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.ROCK_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","rock").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.STEEL_MEMORY)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","steel").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.WATER_MEMORY)){
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
            if(post.getReceived().isOf(FormeChangeItems.FLAME_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","fire").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.SPLASH_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","water").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.ZAP_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","electric").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.MEADOW_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","grass").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.ICICLE_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","ice").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.FIST_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","fighting").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.TOXIC_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","poison").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.EARTH_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","ground").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.SKY_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","flying").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.MIND_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","psychic").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.INSECT_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","bug").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.STONE_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","rock").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.SPOOKY_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","ghost").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.DRACO_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","dragon").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.DREAD_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","dark").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.IRON_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","steel").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.PIXIE_PLATE)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype","fairy").apply(pokemon);
            }else if(post.getReceived().isOf(ZCrystals.BUGINIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "bug").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.DARKINIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "dark").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.DRAGONIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "dragon").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.ELECTRIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "electric").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.FAIRIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "fairy").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.FIGHTINIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "fighting").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.FIRIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "fire").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.FLYINIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "flying").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.GHOSTIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "ghost").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.GRASSIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "grass").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.GROUNDIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "ground").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.ICIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "ice").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.POISONIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "poison").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.PSYCHIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "psychic").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.ROCKIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "rock").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.STEELIUM_Z)) {
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "steel").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.WATERIUM_Z)) {
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
            if(post.getReceived().isOf(FormeChangeItems.GRISEOUS_CORE)){
                EventUtils.originAnimation(pokemon.getEntity(), ParticleTypes.ASH);
                new StringSpeciesFeature("orb_forme","origin").apply(pokemon);
            }else if (post.getReturned().isOf(FormeChangeItems.GRISEOUS_CORE)){
                EventUtils.originAnimation(pokemon.getEntity(), ParticleTypes.ASH);
                new StringSpeciesFeature("orb_forme","altered").apply(pokemon);
            }
        } else if (pokemon.getSpecies().getName().equals("Palkia")) {
            if(post.getReceived().isOf(FormeChangeItems.LUSTROUS_GLOBE)){
                EventUtils.originAnimation(pokemon.getEntity(), ParticleTypes.END_ROD);
                new StringSpeciesFeature("orb_forme","origin").apply(pokemon);
            }else if (post.getReturned().isOf(FormeChangeItems.LUSTROUS_GLOBE)){
                EventUtils.originAnimation(pokemon.getEntity(), ParticleTypes.END_ROD);
                new StringSpeciesFeature("orb_forme","altered").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Dialga")) {
            if(post.getReceived().isOf(FormeChangeItems.ADAMANT_CRYSTAL)){
                EventUtils.originAnimation(pokemon.getEntity(), ParticleTypes.END_ROD);
                new StringSpeciesFeature("orb_forme","origin").apply(pokemon);
            }else if (post.getReturned().isOf(FormeChangeItems.ADAMANT_CRYSTAL)){
                EventUtils.originAnimation(pokemon.getEntity(), ParticleTypes.END_ROD);
                new StringSpeciesFeature("orb_forme","altered").apply(pokemon);
            }
        }
    }

    public static void eternamaxChange(HeldItemEvent.Post post){
        if(!ShowdownConfig.etermaxForme.get()){
            return;
        }
        Pokemon pokemon = post.getPokemon();

        if(pokemon.getSpecies().getName().equals("Eternatus") && post.getReceived().isOf(FormeChangeItems.STAR_CORE)){
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
        if(post.getReceived().isOf(FormeChangeItems.HEARTHFLAME_MASK)){
            new StringSpeciesFeature("ogre_mask","hearthflame").apply(pokemon);
            try {
                pokemon.setTeraType(TeraTypes.getFIRE());
            }catch (Exception e){
                MegaShowdown.LOGGER.info("Sike");
            }
        } else if (post.getReceived().isOf(FormeChangeItems.CORNERSTONE_MASK)) {
            new StringSpeciesFeature("ogre_mask","cornerstone").apply(pokemon);
            try {
                pokemon.setTeraType(TeraTypes.getROCK());
            }catch (Exception e){
                MegaShowdown.LOGGER.info("Sike");
            }
        }else if (post.getReceived().isOf(FormeChangeItems.WELLSPRING_MASK)) {
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
        ServerPlayerEntity player = post.getPokemon().getOwnerPlayer();
        Species species = post.getPokemon().getSpecies();

        if(species.getName().equals(Utils.getSpecies("kyogre").getName()) && post.getReceived().isOf(MegaStones.BLUE_ORB)){
            if(player.getAttached(DataManage.PRIMAL_DATA) && !ShowdownConfig.multiplePrimals.get()){
                player.sendMessage(
                        Text.literal("You can only have one primal at a time").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
                return;
            }
            new FlagSpeciesFeature("primal", true).apply(post.getPokemon());
            setTradable(post.getPokemon(), false);
            EventUtils.primalRevertAnimation(post.getPokemon().getEntity(), ParticleTypes.BUBBLE);
            AdvancementHelper.grantAdvancement(player, "primal_evo");
            player.setAttached(DataManage.PRIMAL_DATA, true);
        }
        else if(species.getName().equals(Utils.getSpecies("groudon").getName()) && post.getReceived().isOf(MegaStones.RED_ORB)){
            if(player.getAttached(DataManage.PRIMAL_DATA) && !ShowdownConfig.multiplePrimals.get()){
                player.sendMessage(
                        Text.literal("You can only have one primal at a time").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
                return;
            }
            new FlagSpeciesFeature("primal", true).apply(post.getPokemon());
            setTradable(post.getPokemon(), false);
            EventUtils.primalRevertAnimation(post.getPokemon().getEntity(), ParticleTypes.CAMPFIRE_COSY_SMOKE);
            AdvancementHelper.grantAdvancement(player, "primal_evo");
            player.setAttached(DataManage.PRIMAL_DATA, true);
        }else{
            SpeciesFeature feature = post.getPokemon().getFeature("primal");
            if(feature == null){
                return;
            }

            new FlagSpeciesFeature("primal", false).apply(post.getPokemon());
            setTradable(post.getPokemon(), true);
            EventUtils.primalRevertAnimation(post.getPokemon().getEntity(), ParticleTypes.END_ROD);
            player.setAttached(DataManage.PRIMAL_DATA, false);
        }
    }

    public static void megaEvent(HeldItemEvent.Post event) {
        Pokemon pokemon = event.getPokemon();

        if(pokemon.getEntity() == null){
            return;
        }

        if(pokemon.getEntity().getWorld().isClient){
            return;
        }

        Species species = Utils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());


        List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

        for (String key : megaKeys) {
            FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));
            ServerPlayerEntity player = pokemon.getOwnerPlayer();

            FlagSpeciesFeature feature = featureProvider.get(pokemon);
            if(feature != null){
                boolean enabled = featureProvider.get(pokemon).getEnabled();

                if (enabled && feature.getName().equals("mega") && (species != pokemon.getSpecies() || event.getReceived() != event.getReturned())) {
                    MegaLogic.Devolve(pokemon, player, true);
                }else if(enabled && feature.getName().equals("mega-x") && (species != pokemon.getSpecies() || event.getReceived() != event.getReturned())){
                    MegaLogic.Devolve(pokemon, player, true);
                } else if (enabled && feature.getName().equals("mega-y") && (species != pokemon.getSpecies() || event.getReceived() != event.getReturned())) {
                    MegaLogic.Devolve(pokemon, player, true);
                }
            }
        }
    }

    public static void crownedEvent(HeldItemEvent.Post event){
        Pokemon pokemon = event.getPokemon();

        if(pokemon.getSpecies().getName().equals("Zacian") ||
                pokemon.getSpecies().getName().equals("Zamazenta")){
            if(event.getReceived().isOf(FormeChangeItems.RUSTED_SWORD) && pokemon.getSpecies().getName().equals("Zacian")){
                EventUtils.crownAnimation((ServerWorld) pokemon.getEntity().getWorld(), pokemon.getEntity().getBlockPos(), pokemon.getEntity());
                new FlagSpeciesFeature("crowned", true).apply(pokemon);
                setTradable(pokemon, false);
            } else if (event.getReceived().isOf(FormeChangeItems.RUSTED_SHIELD) && pokemon.getSpecies().getName().equals("Zamazenta")) {
                EventUtils.crownAnimation((ServerWorld) pokemon.getEntity().getWorld(), pokemon.getEntity().getBlockPos(), pokemon.getEntity());
                new FlagSpeciesFeature("crowned", true).apply(pokemon);
                setTradable(pokemon, false);
            } else if(pokemon.getSpecies().getName().equals("Zacian") && event.getReturned().isOf(FormeChangeItems.RUSTED_SWORD)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new FlagSpeciesFeature("crowned", false).apply(pokemon);
                setTradable(pokemon, true);
            }else if(pokemon.getSpecies().getName().equals("Zamazenta") && event.getReturned().isOf(FormeChangeItems.RUSTED_SHIELD)){
                EventUtils.playEvolveAnimation(pokemon.getEntity());
                new FlagSpeciesFeature("crowned", false).apply(pokemon);
                setTradable(pokemon, true);
            }
        }
    }

    public static void ultraEvent(HeldItemEvent.Post event){
        Pokemon pokemon = event.getPokemon();

        if(pokemon.getSpecies().getName().equals("Necrozma") && pokemon.getAspects().contains("ultra")){
            if(event.getReturned().isOf(ZCrystals.ULTRANECROZIUM_Z)){
                EventUtils.ultraAnimation(pokemon.getEntity());
                new FlagSpeciesFeature("ultra",false).apply(pokemon);
            }
        }
    }
}
