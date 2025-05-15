package com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents;

import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.config.Config;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.config.structure.CustomConfig;
import com.cobblemon.yajatkaul.mega_showdown.config.structure.FormeChange;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.PokeHandler;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.ZCrystals;
import com.cobblemon.yajatkaul.mega_showdown.item.configActions.ConfigResults;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.ArceusPlates;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.Drives;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.Memories;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.utility.LazyLib;
import kotlin.Unit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class HeldItemChangeFormes {
    public static void genesectChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();
        if(pokemon.getSpecies().getName().equals("Genesect")){
            if(post.getReceived().is(FormeChangeItems.DOUSE_DRIVE)){
                LazyLib.Companion.cryAnimation(pokemon.getEntity());
                new StringSpeciesFeature("techno_drive","water").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.BURN_DRIVE)){
                LazyLib.Companion.cryAnimation(pokemon.getEntity());
                new StringSpeciesFeature("techno_drive","fire").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.CHILL_DRIVE)){
                LazyLib.Companion.cryAnimation(pokemon.getEntity());
                new StringSpeciesFeature("techno_drive","ice").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.SHOCK_DRIVE)){
                LazyLib.Companion.cryAnimation(pokemon.getEntity());
                new StringSpeciesFeature("techno_drive","electric").apply(pokemon);
            }
            else if (!(post.getReceived().getItem() instanceof Drives) && post.getReturned().getItem() instanceof Drives) {
                LazyLib.Companion.cryAnimation(pokemon.getEntity());
                new StringSpeciesFeature("techno_drive","none").apply(pokemon);
            }
        }
    }

    public static void silvallyChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();
        if(pokemon.getSpecies().getName().equals("Silvally")){
            if(post.getReceived().is(FormeChangeItems.BUG_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","bug").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.DARK_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","dark").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.DRAGON_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","dragon").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.ELECTRIC_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","electric").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.FAIRY_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","fairy").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.FIGHTING_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","fighting").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.FIRE_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","fire").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.FLYING_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","flying").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.GHOST_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","ghost").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.GRASS_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","grass").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.GROUND_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","ground").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.ICE_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","ice").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.POISON_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","poison").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.PSYCHIC_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","psychic").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.ROCK_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","rock").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.STEEL_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","steel").apply(pokemon);
            }
            else if(post.getReceived().is(FormeChangeItems.WATER_MEMORY)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","water").apply(pokemon);
            } else if (!(post.getReceived().getItem() instanceof Memories) && post.getReturned().getItem() instanceof Memories) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","normal").apply(pokemon);
            }
        }
    }

    public static void arcuesChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();
        if(pokemon.getSpecies().getName().equals("Arceus")){
            if(post.getReceived().is(FormeChangeItems.FLAME_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","fire").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.SPLASH_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","water").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.ZAP_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","electric").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.MEADOW_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","grass").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.ICICLE_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","ice").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.FIST_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","fighting").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.TOXIC_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","poison").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.EARTH_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","ground").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.SKY_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","flying").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.MIND_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","psychic").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.INSECT_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","bug").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.STONE_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","rock").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.SPOOKY_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","ghost").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.DRACO_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","dragon").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.DREAD_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","dark").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.IRON_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","steel").apply(pokemon);
            } else if(post.getReceived().is(FormeChangeItems.PIXIE_PLATE)){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","fairy").apply(pokemon);
            }else if(post.getReceived().is(ZCrystals.BUGINIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "bug").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.DARKINIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "dark").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.DRAGONIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "dragon").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.ELECTRIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "electric").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.FAIRIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "fairy").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.FIGHTINIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "fighting").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.FIRIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "fire").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.FLYINIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "flying").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.GHOSTIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "ghost").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.GRASSIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "grass").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.GROUNDIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "ground").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.ICIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "ice").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.POISONIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "poison").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.PSYCHIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "psychic").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.ROCKIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "rock").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.STEELIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "steel").apply(pokemon);
            } else if(post.getReceived().is(ZCrystals.WATERIUM_Z)) {
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "water").apply(pokemon);
            } else if(!(post.getReceived().getItem() instanceof ArceusPlates) && post.getReturned().getItem() instanceof ArceusPlates){
                playHeldItemChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","normal").apply(pokemon);
            }
        }
    }

    public static void originChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();

        if(pokemon.getSpecies().getName().equals("Giratina")){
            if(post.getReceived().is(FormeChangeItems.GRISEOUS_CORE)){
                originAnimation(pokemon.getEntity(), ParticleTypes.ASH);
                new StringSpeciesFeature("orb_forme","origin").apply(pokemon);
            }else if (post.getReturned().is(FormeChangeItems.GRISEOUS_CORE)){
                originAnimation(pokemon.getEntity(), ParticleTypes.ASH);
                new StringSpeciesFeature("orb_forme","altered").apply(pokemon);
            }
        } else if (pokemon.getSpecies().getName().equals("Palkia")) {
            if(post.getReceived().is(FormeChangeItems.LUSTROUS_GLOBE)){
                originAnimation(pokemon.getEntity(), ParticleTypes.END_ROD);
                new StringSpeciesFeature("orb_forme","origin").apply(pokemon);
            }else if (post.getReturned().is(FormeChangeItems.LUSTROUS_GLOBE)){
                originAnimation(pokemon.getEntity(), ParticleTypes.END_ROD);
                new StringSpeciesFeature("orb_forme","altered").apply(pokemon);
            }
        }else if (pokemon.getSpecies().getName().equals("Dialga")) {
            if(post.getReceived().is(FormeChangeItems.ADAMANT_CRYSTAL)){
                originAnimation(pokemon.getEntity(), ParticleTypes.END_ROD);
                new StringSpeciesFeature("orb_forme","origin").apply(pokemon);
            }else if (post.getReturned().is(FormeChangeItems.ADAMANT_CRYSTAL)){
                originAnimation(pokemon.getEntity(), ParticleTypes.END_ROD);
                new StringSpeciesFeature("orb_forme","altered").apply(pokemon);
            }
        }
    }

    public static void eternamaxChange(HeldItemEvent.Post post){
        if(!Config.etermaxForme){
            return;
        }
        Pokemon pokemon = post.getPokemon();

        if(!pokemon.getSpecies().getName().equals("Eternatus")){
            return;
        }

        if(post.getReceived().is(FormeChangeItems.STAR_CORE)){
            LazyLib.Companion.cryAnimation(pokemon.getEntity());
            new FlagSpeciesFeature("eternamax",true).apply(pokemon);
            setTradable(pokemon, false);
        } else if (post.getReturned().is(FormeChangeItems.STAR_CORE)) {
            new FlagSpeciesFeature("eternamax",false).apply(pokemon);
            setTradable(pokemon, true);
        }
    }

    public static void ogerponChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();

        if(!pokemon.getSpecies().getName().equals("Ogerpon")){
            return;
        }
        if(post.getReceived().is(FormeChangeItems.HEARTHFLAME_MASK)){
            new StringSpeciesFeature("ogre_mask","hearthflame").apply(pokemon);
            LazyLib.Companion.cryAnimation(pokemon.getEntity());
            pokemon.setTeraType(TeraTypes.getFIRE());
        } else if (post.getReceived().is(FormeChangeItems.CORNERSTONE_MASK)) {
            new StringSpeciesFeature("ogre_mask","cornerstone").apply(pokemon);
            LazyLib.Companion.cryAnimation(pokemon.getEntity());
            pokemon.setTeraType(TeraTypes.getROCK());
        }else if (post.getReceived().is(FormeChangeItems.WELLSPRING_MASK)) {
            new StringSpeciesFeature("ogre_mask","wellspring").apply(pokemon);
            LazyLib.Companion.cryAnimation(pokemon.getEntity());
            pokemon.setTeraType(TeraTypes.getWATER());
        }else if (post.getReturned().is(FormeChangeItems.WELLSPRING_MASK)
                || post.getReturned().is(FormeChangeItems.CORNERSTONE_MASK)
                || post.getReturned().is(FormeChangeItems.HEARTHFLAME_MASK)){
            new StringSpeciesFeature("ogre_mask","teal").apply(pokemon);
            LazyLib.Companion.cryAnimation(pokemon.getEntity());
            try {
                pokemon.setTeraType(TeraTypes.getGRASS());
            }catch (Exception e){
                MegaShowdown.LOGGER.info("Sike");
            }
        }
    }

    public static void primalEvent(HeldItemEvent.Pre pre) {
        ServerPlayer player = pre.getPokemon().getOwnerPlayer();
        Species species = pre.getPokemon().getSpecies();

        if(!species.getName().equals("Kyogre") && !species.getName().equals("Groudon")){
            return;
        }

        if(!player.hasData(DataManage.PRIMAL_DATA)){
            player.setData(DataManage.PRIMAL_DATA, false);
        }

        boolean primalData = player.getData(DataManage.PRIMAL_DATA);

        if(species.getName().equals("Kyogre") && pre.getReceiving().is(MegaStones.BLUE_ORB) && !pre.getPokemon().getAspects().contains("primal")){
            if(!primalData || Config.multiplePrimals){
                new StringSpeciesFeature("reversion_state", "primal").apply(pre.getPokemon());
                primalRevertAnimation(pre.getPokemon().getEntity(), ParticleTypes.BUBBLE, true);
                player.setData(DataManage.PRIMAL_DATA, true);
                player.setData(DataManage.PRIMAL_POKEMON, new PokeHandler(pre.getPokemon()));
                setTradable(pre.getPokemon(), false);
            }else{
                pre.cancel();
                player.displayClientMessage(Component.translatable("message.mega_showdown.primal_limit")
                        .withColor(0xFF0000), true);
            }
        } else if (species.getName().equals("Groudon") && pre.getReceiving().is(MegaStones.RED_ORB) && !pre.getPokemon().getAspects().contains("primal")) {
            if(!primalData || Config.multiplePrimals){
                new StringSpeciesFeature("reversion_state", "primal").apply(pre.getPokemon());
                primalRevertAnimation(pre.getPokemon().getEntity(), ParticleTypes.CAMPFIRE_COSY_SMOKE, true);
                player.setData(DataManage.PRIMAL_DATA, true);
                player.setData(DataManage.PRIMAL_POKEMON, new PokeHandler(pre.getPokemon()));
                setTradable(pre.getPokemon(), false);
            }else{
                pre.cancel();
                player.displayClientMessage(Component.translatable("message.mega_showdown.primal_limit")
                        .withColor(0xFF0000), true);
            }
        } else if (species.getName().equals("Kyogre") && !pre.getReceiving().is(MegaStones.BLUE_ORB) && pre.getReturning().is(MegaStones.BLUE_ORB)) {
            new StringSpeciesFeature("reversion_state", "standard").apply(pre.getPokemon());
            primalRevertAnimation(pre.getPokemon().getEntity(), ParticleTypes.END_ROD, false);
            player.setData(DataManage.PRIMAL_DATA, false);
            player.removeData(DataManage.PRIMAL_POKEMON);
            setTradable(pre.getPokemon(), true);
        } else if (species.getName().equals("Groudon") && !pre.getReceiving().is(MegaStones.RED_ORB) && pre.getReturning().is(MegaStones.RED_ORB)) {
            new StringSpeciesFeature("reversion_state", "standard").apply(pre.getPokemon());
            primalRevertAnimation(pre.getPokemon().getEntity(), ParticleTypes.END_ROD, false);
            player.setData(DataManage.PRIMAL_DATA, false);
            player.removeData(DataManage.PRIMAL_POKEMON);
            setTradable(pre.getPokemon(), true);
        }
    }

    public static void megaEvent(HeldItemEvent.Post event){
        Pokemon pokemon = event.getPokemon();

        if(pokemon.getAspects().contains("mega_x") || pokemon.getAspects().contains("mega_y") || pokemon.getAspects().contains("mega")){
            MegaLogic.Devolve(pokemon, true);
        }
    }

    public static void customEvents(HeldItemEvent.Post event){
        Pokemon pokemon = event.getPokemon();

        for(FormeChange heldItem: CustomConfig.formeChange){
            if(heldItem.pokemons.contains(pokemon.getSpecies().getName())){
                if(!pokemon.getEntity().isBattling()){
                    if(!heldItem.required_aspects.isEmpty()){
                        List<String> aspectList = new ArrayList<>();
                        for (String aspects : heldItem.required_aspects) {
                            aspectList.add(aspects.split("=")[1]);
                        }

                        boolean allMatch = true;
                        for (String requiredAspect : aspectList) {
                            boolean matched = false;
                            for (String pokemonAspect : pokemon.getAspects()) {
                                if (pokemonAspect.startsWith(requiredAspect)) {
                                    matched = true;
                                    break;
                                }
                            }
                            if (!matched) {
                                allMatch = false;
                                break;
                            }
                        }

                        if(!allMatch){
                            return;
                        }
                    }

                    ItemStack receivedItem = event.getReceived();
                    String[] nameSpace = heldItem.item_id.split(":");
                    ResourceLocation customItem = ResourceLocation.fromNamespaceAndPath(nameSpace[0], nameSpace[1]);
                    Item item = BuiltInRegistries.ITEM.get(customItem);
                    if(receivedItem.is(item) && receivedItem.get(DataComponents.CUSTOM_MODEL_DATA) != null
                            && receivedItem.get(DataComponents.CUSTOM_MODEL_DATA).value()
                            == heldItem.custom_model_data){
                        if(!heldItem.tradable_form){
                            setTradable(pokemon, false);
                        }
                        for(String aspects: heldItem.aspects){
                            String[] aspectsDiv = aspects.split("=");
                            if(aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")){
                                new FlagSpeciesFeature(aspectsDiv[0],Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                            }else{
                                new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                            }
                        }
                        if(!heldItem.tradable_form){
                            setTradable(pokemon, false);
                        }
                        ConfigResults.particleEffect(pokemon.getEntity(), heldItem.effects, true);
                        return;
                    }else if (!receivedItem.is(item) ||
                            receivedItem.get(DataComponents.CUSTOM_MODEL_DATA).value() == heldItem.custom_model_data){
                        if(!heldItem.tradable_form){
                            setTradable(pokemon, true);
                        }
                        for(String aspects: heldItem.default_aspects){
                            String[] aspectsDiv = aspects.split("=");
                            if(aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")){
                                new FlagSpeciesFeature(aspectsDiv[0],Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                            }else{
                                new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                            }
                        }
                        if(!heldItem.tradable_form){
                            setTradable(pokemon, true);
                        }
                        ConfigResults.particleEffect(pokemon.getEntity(), heldItem.effects, false);
                        return;
                    }
                }
            }
        }
    }

    public static void crownedEvent(HeldItemEvent.Post event){
        Pokemon pokemon = event.getPokemon();

        if(pokemon.getSpecies().getName().equals("Zacian") ||
                pokemon.getSpecies().getName().equals("Zamazenta")){
            if(event.getReceived().is(FormeChangeItems.RUSTED_SWORD) && pokemon.getSpecies().getName().equals("Zacian")){
                crownAnimation((ServerLevel) pokemon.getEntity().level(), pokemon.getEntity().getOnPos(), pokemon.getEntity());
                new FlagSpeciesFeature("crowned", true).apply(pokemon);
                setTradable(pokemon, false);
            } else if (event.getReceived().is(FormeChangeItems.RUSTED_SHIELD) && pokemon.getSpecies().getName().equals("Zamazenta")) {
                crownAnimation((ServerLevel) pokemon.getEntity().level(), pokemon.getEntity().getOnPos(), pokemon.getEntity());
                new FlagSpeciesFeature("crowned", true).apply(pokemon);
                setTradable(pokemon, false);
            } else if(pokemon.getSpecies().getName().equals("Zacian") && event.getReturned().is(FormeChangeItems.RUSTED_SWORD)){
                playHeldItemChange(pokemon.getEntity());
                new FlagSpeciesFeature("crowned", false).apply(pokemon);
                setTradable(pokemon, true);
            }else if(pokemon.getSpecies().getName().equals("Zamazenta") && event.getReturned().is(FormeChangeItems.RUSTED_SHIELD)){
                playHeldItemChange(pokemon.getEntity());
                new FlagSpeciesFeature("crowned", false).apply(pokemon);
                setTradable(pokemon, true);
            }
        }
    }

    public static void ultraEvent(HeldItemEvent.Post event){
        Pokemon pokemon = event.getPokemon();

        if(pokemon.getSpecies().getName().equals("Necrozma") && pokemon.getAspects().contains("ultra")){
            if(event.getReturned().is(ZCrystals.ULTRANECROZIUM_Z)){
                if(!pokemon.getEntity().isBattling()){
                    LazyLib.Companion.cryAnimation(pokemon.getEntity());
                }
                EventUtils.ultraAnimation(pokemon.getEntity());
                new FlagSpeciesFeature("ultra",false).apply(pokemon);
            }
        }
    }

    private static void playHeldItemChange(LivingEntity context) {
        LazyLib.Companion.cryAnimation(context);
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.AMETHYST_BLOCK_CHIME, // Change this if needed
                    SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (100 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverLevel.sendParticles(
                        ParticleTypes.END_ROD, // Change this to any particle type
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
    private static void originAnimation(LivingEntity context, SimpleParticleType particleType) {
        LazyLib.Companion.cryAnimation(context);
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();
            double entityDepth = entityWidth; // Usually same as width for most mobs

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 1.2; // Adjust this for more spread
            double adjustedWidth = entityWidth * scaleFactor;
            double adjustedHeight = entityHeight * scaleFactor;
            double adjustedDepth = entityDepth * scaleFactor;

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.AMETHYST_BLOCK_CHIME, // Change this if needed
                    SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (175 * adjustedWidth * adjustedHeight); // Scale particle amount

            for (int i = 0; i < particleCount; i++) {
                double xOffset = (Math.random() - 0.5) * adjustedWidth; // Random X within slightly expanded bounding box
                double yOffset = Math.random() * adjustedHeight; // Random Y within slightly expanded bounding box
                double zOffset = (Math.random() - 0.5) * adjustedDepth; // Random Z within slightly expanded bounding box

                serverLevel.sendParticles(
                        particleType,
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
    private static void crownAnimation(ServerLevel level, BlockPos pos, LivingEntity context) {
        LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
        if (lightning != null) {
            lightning.moveTo(Vec3.atBottomCenterOf(pos));
            lightning.setVisualOnly(true);
            level.addFreshEntity(lightning);
            playHeldItemChange(context);
        }
    }
    private static void primalRevertAnimation(PokemonEntity context, SimpleParticleType particleType, Boolean revert) {
        if(revert){
            context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);
            context.after(2.5f, () -> {
                LazyLib.Companion.cryAnimation(context);
                context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                return Unit.INSTANCE;
            });
        }

        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();
            double entityDepth = entityWidth; // Usually same as width for most mobs

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 1.2; // Adjust this for more spread
            double adjustedWidth = entityWidth * scaleFactor;
            double adjustedHeight = entityHeight * scaleFactor;
            double adjustedDepth = entityDepth * scaleFactor;

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BEACON_ACTIVATE, // Change this if needed
                    SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (175 * adjustedWidth * adjustedHeight); // Scale particle amount

            for (int i = 0; i < particleCount; i++) {
                double xOffset = (Math.random() - 0.5) * adjustedWidth; // Random X within slightly expanded bounding box
                double yOffset = Math.random() * adjustedHeight; // Random Y within slightly expanded bounding box
                double zOffset = (Math.random() - 0.5) * adjustedDepth; // Random Z within slightly expanded bounding box

                serverLevel.sendParticles(
                        particleType,
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
}
