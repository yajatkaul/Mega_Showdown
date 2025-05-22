package com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.events.pokemon.HeldItemEvent;
import com.cobblemon.mod.common.api.events.pokemon.PokemonRecalledEvent;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.PokeHandler;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.FormChangeData;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.ZCrystals;
import com.cobblemon.yajatkaul.mega_showdown.item.configActions.ConfigResults;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.ArceusPlates;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.Drives;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.Memories;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.LazyLib;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import kotlin.Unit;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class HeldItemChangeFormes {
    public static void genesectChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();
        if(pokemon.getSpecies().getName().equals("Genesect")){
            if(post.getReceived().isOf(FormeChangeItems.DOUSE_DRIVE)){
                LazyLib.Companion.cryAnimation(pokemon.getEntity());
                new StringSpeciesFeature("techno_drive","water").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.BURN_DRIVE)){
                LazyLib.Companion.cryAnimation(pokemon.getEntity());
                new StringSpeciesFeature("techno_drive","fire").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.CHILL_DRIVE)){
                LazyLib.Companion.cryAnimation(pokemon.getEntity());
                new StringSpeciesFeature("techno_drive","ice").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.SHOCK_DRIVE)){
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
            if(post.getReceived().isOf(FormeChangeItems.BUG_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","bug").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.DARK_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","dark").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.DRAGON_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","dragon").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.ELECTRIC_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","electric").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.FAIRY_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","fairy").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.FIGHTING_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","fighting").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.FIRE_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","fire").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.FLYING_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","flying").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.GHOST_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","ghost").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.GRASS_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","grass").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.GROUND_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","ground").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.ICE_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","ice").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.POISON_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","poison").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.PSYCHIC_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","psychic").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.ROCK_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","rock").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.STEEL_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","steel").apply(pokemon);
            }
            else if(post.getReceived().isOf(FormeChangeItems.WATER_MEMORY)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","water").apply(pokemon);
            } else if (!(post.getReceived().getItem() instanceof Memories) && post.getReturned().getItem() instanceof Memories) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("rks_memory","normal").apply(pokemon);
            }
        }
    }

    public static void arcuesChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();
        if(pokemon.getSpecies().getName().equals("Arceus")){
            if(post.getReceived().isOf(FormeChangeItems.FLAME_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","fire").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.SPLASH_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","water").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.ZAP_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","electric").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.MEADOW_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","grass").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.ICICLE_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","ice").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.FIST_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","fighting").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.TOXIC_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","poison").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.EARTH_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","ground").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.SKY_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","flying").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.MIND_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","psychic").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.INSECT_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","bug").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.STONE_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","rock").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.SPOOKY_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","ghost").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.DRACO_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","dragon").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.DREAD_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","dark").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.IRON_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","steel").apply(pokemon);
            } else if(post.getReceived().isOf(FormeChangeItems.PIXIE_PLATE)){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","fairy").apply(pokemon);
            }else if(post.getReceived().isOf(ZCrystals.BUGINIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "bug").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.DARKINIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "dark").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.DRAGONIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "dragon").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.ELECTRIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "electric").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.FAIRIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "fairy").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.FIGHTINIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "fighting").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.FIRIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "fire").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.FLYINIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "flying").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.GHOSTIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "ghost").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.GRASSIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "grass").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.GROUNDIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "ground").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.ICIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "ice").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.POISONIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "poison").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.PSYCHIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "psychic").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.ROCKIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "rock").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.STEELIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "steel").apply(pokemon);
            } else if(post.getReceived().isOf(ZCrystals.WATERIUM_Z)) {
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype", "water").apply(pokemon);
            } else if(!(post.getReceived().getItem() instanceof ArceusPlates) && post.getReturned().getItem() instanceof ArceusPlates){
                playHeldItemFormeChange(pokemon.getEntity());
                new StringSpeciesFeature("multitype","normal").apply(pokemon);
            }
        }
    }

    public static void originChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();

        if(pokemon.getSpecies().getName().equals("Giratina")){
            if(post.getReceived().isOf(FormeChangeItems.GRISEOUS_CORE)){
                originAnimation(pokemon.getEntity(), true);
            }else if (post.getReturned().isOf(FormeChangeItems.GRISEOUS_CORE)){
                originAnimation(pokemon.getEntity(), false);
            }
        } else if (pokemon.getSpecies().getName().equals("Palkia")) {
            if(post.getReceived().isOf(FormeChangeItems.LUSTROUS_GLOBE)){
                originAnimation(pokemon.getEntity(), true);
            }else if (post.getReturned().isOf(FormeChangeItems.LUSTROUS_GLOBE)){
                originAnimation(pokemon.getEntity(), false);
            }
        }else if (pokemon.getSpecies().getName().equals("Dialga")) {
            if(post.getReceived().isOf(FormeChangeItems.ADAMANT_CRYSTAL)){
                originAnimation(pokemon.getEntity(), true);
            }else if (post.getReturned().isOf(FormeChangeItems.ADAMANT_CRYSTAL)){
                originAnimation(pokemon.getEntity(), false);
            }
        }
    }

    public static void eternamaxChange(HeldItemEvent.Post post){
        if(!ShowdownConfig.etermaxForme.get()){
            return;
        }
        Pokemon pokemon = post.getPokemon();

        if(!pokemon.getSpecies().getName().equals("Eternatus")){
            return;
        }

        if(post.getReceived().isOf(FormeChangeItems.STAR_CORE)){
            LazyLib.Companion.cryAnimation(pokemon.getEntity());
            new FlagSpeciesFeature("eternamax",true).apply(pokemon);
            setTradable(pokemon, false);
        } else if (post.getReturned().isOf(FormeChangeItems.STAR_CORE)) {
            new FlagSpeciesFeature("eternamax",false).apply(pokemon);
            setTradable(pokemon, true);
        }
    }

    public static void ogerponChange(HeldItemEvent.Post post){
        Pokemon pokemon = post.getPokemon();

        if(!pokemon.getSpecies().getName().equals("Ogerpon")){
            return;
        }
        if(post.getReceived().isOf(FormeChangeItems.HEARTHFLAME_MASK)){
            new StringSpeciesFeature("ogre_mask","hearthflame").apply(pokemon);
            LazyLib.Companion.cryAnimation(pokemon.getEntity());
            try {
                pokemon.setTeraType(TeraTypes.getFIRE());
            }catch (Exception e){
                MegaShowdown.LOGGER.info("Sike");
            }
        } else if (post.getReceived().isOf(FormeChangeItems.CORNERSTONE_MASK)) {
            new StringSpeciesFeature("ogre_mask","cornerstone").apply(pokemon);
            LazyLib.Companion.cryAnimation(pokemon.getEntity());
            try {
                pokemon.setTeraType(TeraTypes.getROCK());
            }catch (Exception e){
                MegaShowdown.LOGGER.info("Sike");
            }
        }else if (post.getReceived().isOf(FormeChangeItems.WELLSPRING_MASK)) {
            new StringSpeciesFeature("ogre_mask","wellspring").apply(pokemon);
            LazyLib.Companion.cryAnimation(pokemon.getEntity());
            try {
                pokemon.setTeraType(TeraTypes.getWATER());
            }catch (Exception e){
                MegaShowdown.LOGGER.info("Sike");
            }
        }else if (post.getReturned().isOf(FormeChangeItems.WELLSPRING_MASK)
                || post.getReturned().isOf(FormeChangeItems.CORNERSTONE_MASK)
                || post.getReturned().isOf(FormeChangeItems.HEARTHFLAME_MASK)){
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
        ServerPlayerEntity player = pre.getPokemon().getOwnerPlayer();
        Species species = pre.getPokemon().getSpecies();

        if(!species.getName().equals("Kyogre") && !species.getName().equals("Groudon")){
            return;
        }

        if(!player.hasAttached(DataManage.PRIMAL_DATA)){
            player.setAttached(DataManage.PRIMAL_DATA, false);
        }

        boolean primalData = player.getAttached(DataManage.PRIMAL_DATA);

        if(species.getName().equals("Kyogre") && pre.getReceiving().isOf(MegaStones.BLUE_ORB) && !pre.getPokemon().getAspects().contains("primal")){
            if(!primalData || ShowdownConfig.multiplePrimals.get()){
                new StringSpeciesFeature("reversion_state", "primal").apply(pre.getPokemon());
                primalRevertAnimation(pre.getPokemon().getEntity(), ParticleTypes.BUBBLE, true);
                player.setAttached(DataManage.PRIMAL_DATA, true);
                player.setAttached(DataManage.PRIMAL_POKEMON, new PokeHandler(pre.getPokemon()));
                setTradable(pre.getPokemon(), false);
            }else{
                pre.cancel();
                player.sendMessage(
                        Text.translatable("message.mega_showdown.primal_limit").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
            }
        } else if (species.getName().equals("Groudon") && pre.getReceiving().isOf(MegaStones.RED_ORB) && !pre.getPokemon().getAspects().contains("primal")) {
            if(!primalData || ShowdownConfig.multiplePrimals.get()){
                new StringSpeciesFeature("reversion_state", "primal").apply(pre.getPokemon());
                primalRevertAnimation(pre.getPokemon().getEntity(), ParticleTypes.CAMPFIRE_COSY_SMOKE, true);
                player.setAttached(DataManage.PRIMAL_DATA, true);
                player.setAttached(DataManage.PRIMAL_POKEMON, new PokeHandler(pre.getPokemon()));
                setTradable(pre.getPokemon(), false);
            }else{
                pre.cancel();
                player.sendMessage(
                        Text.translatable("message.mega_showdown.primal_limit").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
            }
        } else if (species.getName().equals("Kyogre") && !pre.getReceiving().isOf(MegaStones.BLUE_ORB) && pre.getReturning().isOf(MegaStones.BLUE_ORB)) {
            new StringSpeciesFeature("reversion_state", "standard").apply(pre.getPokemon());
            primalRevertAnimation(pre.getPokemon().getEntity(), ParticleTypes.END_ROD, false);
            player.setAttached(DataManage.PRIMAL_DATA, false);
            player.removeAttached(DataManage.PRIMAL_POKEMON);
            setTradable(pre.getPokemon(), true);
        } else if (species.getName().equals("Groudon") && !pre.getReceiving().isOf(MegaStones.RED_ORB) && pre.getReturning().isOf(MegaStones.RED_ORB)) {
            new StringSpeciesFeature("reversion_state", "standard").apply(pre.getPokemon());
            primalRevertAnimation(pre.getPokemon().getEntity(), ParticleTypes.END_ROD, false);
            player.setAttached(DataManage.PRIMAL_DATA, false);
            player.removeAttached(DataManage.PRIMAL_POKEMON);
            setTradable(pre.getPokemon(), true);
        }
    }

    public static void megaEvent(HeldItemEvent.Post event) {
        Pokemon pokemon = event.getPokemon();

        if(pokemon.getAspects().contains("mega_x") || pokemon.getAspects().contains("mega_y") || pokemon.getAspects().contains("mega")){
            MegaLogic.Devolve(pokemon, true);
        }
    }

    public static void crownedEvent(HeldItemEvent.Post event){
        Pokemon pokemon = event.getPokemon();

        if(pokemon.getSpecies().getName().equals("Zacian") ||
                pokemon.getSpecies().getName().equals("Zamazenta")){
            if(event.getReceived().isOf(FormeChangeItems.RUSTED_SWORD) && pokemon.getSpecies().getName().equals("Zacian")){
                crownAnimation((ServerWorld) pokemon.getEntity().getWorld(), pokemon.getEntity().getBlockPos(), pokemon.getEntity());
                new FlagSpeciesFeature("crowned", true).apply(pokemon);
                setTradable(pokemon, false);
            } else if (event.getReceived().isOf(FormeChangeItems.RUSTED_SHIELD) && pokemon.getSpecies().getName().equals("Zamazenta")) {
                crownAnimation((ServerWorld) pokemon.getEntity().getWorld(), pokemon.getEntity().getBlockPos(), pokemon.getEntity());
                new FlagSpeciesFeature("crowned", true).apply(pokemon);
                setTradable(pokemon, false);
            } else if(pokemon.getSpecies().getName().equals("Zacian") && event.getReturned().isOf(FormeChangeItems.RUSTED_SWORD)){
                playHeldItemFormeChange(pokemon.getEntity());
                new FlagSpeciesFeature("crowned", false).apply(pokemon);
                setTradable(pokemon, true);
            }else if(pokemon.getSpecies().getName().equals("Zamazenta") && event.getReturned().isOf(FormeChangeItems.RUSTED_SHIELD)){
                playHeldItemFormeChange(pokemon.getEntity());
                new FlagSpeciesFeature("crowned", false).apply(pokemon);
                setTradable(pokemon, true);
            }
        }
    }

    public static void ultraEvent(HeldItemEvent.Post event){
        Pokemon pokemon = event.getPokemon();

        if(pokemon.getSpecies().getName().equals("Necrozma") && pokemon.getAspects().contains("ultra")){
            if(event.getReturned().isOf(ZCrystals.ULTRANECROZIUM_Z)){
                if(!pokemon.getEntity().isBattling()){
                    LazyLib.Companion.cryAnimation(pokemon.getEntity());
                }
                EventUtils.ultraAnimation(pokemon.getEntity());
                new FlagSpeciesFeature("ultra",false).apply(pokemon);
            }
        }
    }

    public static void customEvents(HeldItemEvent.Post event){
        Pokemon pokemon = event.getPokemon();

        for(FormChangeData heldItem: Utils.formChangeRegistry){
            if(heldItem.battle_mode_only()){
                return;
            }
            if(heldItem.pokemons().contains(pokemon.getSpecies().getName())){
                if(!pokemon.getEntity().isBattling()){
                    if(!heldItem.required_aspects().isEmpty()){
                        List<String> aspectList = new ArrayList<>();
                        for (String aspects : heldItem.required_aspects()) {
                            String[] aspectsDiv = aspects.split("=");
                            if(aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")){
                                aspectList.add(aspects.split("=")[0]);
                            }else{
                                aspectList.add(aspects.split("=")[1]);
                            }
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
                    String[] nameSpace = heldItem.item_id().split(":");
                    Identifier customItem = Identifier.of(nameSpace[0], nameSpace[1]);
                    Item item = Registries.ITEM.get(customItem);
                    if(receivedItem.isOf(item) && ((receivedItem.get(DataComponentTypes.CUSTOM_MODEL_DATA) != null
                            && receivedItem.get(DataComponentTypes.CUSTOM_MODEL_DATA).value()
                            == heldItem.custom_model_data()) || heldItem.custom_model_data() == 0)){
                        if(!heldItem.tradable_form()){
                            setTradable(pokemon, false);
                        }
                        for(String aspects: heldItem.aspects()){
                            String[] aspectsDiv = aspects.split("=");
                            if(aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")){
                                new FlagSpeciesFeature(aspectsDiv[0],Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                            }else{
                                new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                            }
                        }
                        if(!heldItem.tradable_form()){
                            setTradable(pokemon, false);
                        }
                        ConfigResults.particleEffect(pokemon.getEntity(), heldItem.effects(), true);
                        return;
                    }else if (!receivedItem.isOf(item) ||
                           (( receivedItem.get(DataComponentTypes.CUSTOM_MODEL_DATA) != null &&
                            receivedItem.get(DataComponentTypes.CUSTOM_MODEL_DATA).value()
                                    == heldItem.custom_model_data()) || heldItem.custom_model_data() == 0)){
                        if(!heldItem.tradable_form()){
                            setTradable(pokemon, true);
                        }
                        for(String aspects: heldItem.default_aspects()){
                            String[] aspectsDiv = aspects.split("=");
                            if(aspectsDiv[1].equals("true") || aspectsDiv[1].equals("false")){
                                new FlagSpeciesFeature(aspectsDiv[0],Boolean.parseBoolean(aspectsDiv[1])).apply(pokemon);
                            }else{
                                new StringSpeciesFeature(aspectsDiv[0], aspectsDiv[1]).apply(pokemon);
                            }
                        }
                        if(!heldItem.tradable_form()){
                            setTradable(pokemon, true);
                        }
                        ConfigResults.particleEffect(pokemon.getEntity(), heldItem.effects(), false);
                        return;
                    }
                }
            }
        }
    }

    private static void playHeldItemFormeChange(LivingEntity context) {
        LazyLib.Companion.cryAnimation(context);
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, // Yarn mapping for BEACON_ACTIVATE
                    SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (100 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverWorld.spawnParticles(
                        ParticleTypes.END_ROD, // Same particle type
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
    private static void originAnimation(PokemonEntity context, boolean enabled) {
        BlockPos entityPos = context.getBlockPos();

        context.getWorld().playSound(
                null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                ModSounds.ORIGIN_FORM,
                SoundCategory.PLAYERS, 0.2f, 1.1f
        );

        LazyLib.Companion.snowStormPartileSpawner(context, "origin_effect", "target");
        context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), true);

        context.after(4F, () -> {
            if(enabled){
                new StringSpeciesFeature("orb_forme", "origin").apply(context.getPokemon());
            }else {
                new StringSpeciesFeature("orb_forme", "altered").apply(context.getPokemon());
            }
            context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), false);
            LazyLib.Companion.cryAnimation(context);
            return Unit.INSTANCE;
        });
    }
    private static void crownAnimation(ServerWorld level, BlockPos pos, LivingEntity context) {
        LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(level);
        if (lightning != null) {
            lightning.setPosition(Vec3d.ofBottomCenter(pos));
            lightning.setCosmetic(true);
            level.spawnEntity(lightning);
            playHeldItemFormeChange(context);
        }
    }
    private static void primalRevertAnimation(PokemonEntity context, SimpleParticleType particleType, Boolean revert) {
        if(revert){
            context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), true);
            context.after(2.5f, () -> {
                LazyLib.Companion.cryAnimation(context);
                context.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                return Unit.INSTANCE;
            });
        }

        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();
            double entityDepth = entityWidth; // Usually same as width for most mobs

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 1.2; // Adjust this for more spread
            double adjustedWidth = entityWidth * scaleFactor;
            double adjustedHeight = entityHeight * scaleFactor;
            double adjustedDepth = entityDepth * scaleFactor;

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_BEACON_ACTIVATE, // Change this if needed
                    SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (175 * adjustedWidth * adjustedHeight); // Scale particle amount

            for (int i = 0; i < particleCount; i++) {
                double xOffset = (Math.random() - 0.5) * adjustedWidth; // Random X within slightly expanded bounding box
                double yOffset = Math.random() * adjustedHeight; // Random Y within slightly expanded bounding box
                double zOffset = (Math.random() - 0.5) * adjustedDepth; // Random Z within slightly expanded bounding box

                serverWorld.spawnParticles(
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
