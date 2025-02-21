package com.cobblemon.yajatkaul.mega_showdown.megaevo;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.MegaBraceletItem;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;

import java.util.List;


public class MegaLogic {
    public static void EvoLogic(ServerPlayerEntity player){
        if(ShowdownConfig.battleModeOnly.get()){
            return;
        }

        boolean hasMegaItem = TrinketsApi.getTrinketComponent(player).map(trinkets ->
                trinkets.isEquipped(item -> item.getItem() instanceof MegaBraceletItem)).orElse(false);

        if(!hasMegaItem || player.getOffHandStack().getItem() instanceof MegaBraceletItem ||
                player.getMainHandStack().getItem() instanceof MegaBraceletItem){
            return;
        }

        double range = 5.0;

        Vec3d startPos = player.getEyePos();
        Vec3d lookVec = player.getRotationVector();
        Vec3d endPos = startPos.add(lookVec.multiply(range));

        EntityHitResult entityHit = ProjectileUtil.raycast(
                player,
                startPos,
                endPos,
                player.getBoundingBox().stretch(lookVec.multiply(range)).expand(1.0),
                entity -> !entity.isSpectator() && entity.canHit(),
                range * range
        );

        if(entityHit == null){
            return;
        }

        if(entityHit.getEntity() instanceof PokemonEntity pk) {
            if(pk.getWorld().isClient){
                return;
            }

            List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

            boolean end = false;
            for (String key : megaKeys) {
                FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));
                FlagSpeciesFeature feature = featureProvider.get(pk.getPokemon());

                if(feature != null){
                    boolean enabled = featureProvider.get(pk.getPokemon()).getEnabled();

                    if(enabled){
                        Devolve(pk, player);
                        end = false;
                        break;
                    }else{
                        end = true;
                    }

                }
            }
            if(end){
                Evolve(pk, player);
            }
        }
    }

    public static void Evolve(LivingEntity context, PlayerEntity player){
        if(context instanceof PokemonEntity pk){
            if(pk.getPokemon().getOwnerPlayer() != player){
                return;
            }
        }

        Pokemon pokemon = ((PokemonEntity) context).getPokemon();
        Species species = Utils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());
        Boolean playerData = player.getAttached(DataManage.MEGA_DATA);
        if(playerData == null){
            playerData = false;
        }

        if(pokemon.getSpecies().getName().equals(Utils.getSpecies("rayquaza").getName()) && (!playerData || ShowdownConfig.multipleMegas.get())){
            boolean found = false;
            for (int i = 0; i < 4; i++){
                if(pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")){
                    player.setAttached(DataManage.MEGA_POKEMON, pokemon);
                    player.setAttached(DataManage.MEGA_DATA, true);

                    new FlagSpeciesFeature("mega", true).apply(pokemon);
                    AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "mega_evolve");
                    found = true;
                }
            }
            if(!found){
                player.sendMessage(
                        Text.literal("Rayquaza doesn't have dragonascent").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
            }
            return;
        } else if (pokemon.getSpecies().getName().equals(Utils.getSpecies("rayquaza").getName()) && playerData) {
            player.sendMessage(
                    Text.literal("You can only have one mega at a time").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return;
        }

        if(species == null){
            player.sendMessage(
                    Text.literal("Don't have the correct stone").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return;
        }

        //Multiple megas
        if(species.getName().equals(pokemon.getSpecies().getName()) && (!playerData || ShowdownConfig.multipleMegas.get())){
            if(species.getName().equals(Utils.getSpecies("charizard").getName())){
                if(pokemon.heldItem().isOf(MegaStones.CHARIZARDITE_X)){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, pokemon);

                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                    AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "mega_evolve");
                }else if(pokemon.heldItem().isOf(MegaStones.CHARIZARDITE_Y)){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, pokemon);

                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                    AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "mega_evolve");
                }
            }
            else if(species.getName().equals(Utils.getSpecies("mewtwo").getName())){
                if(pokemon.heldItem().isOf(MegaStones.MEWTWONITE_X)){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, pokemon);

                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                    AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "mega_evolve");
                }else if(pokemon.heldItem().isOf(MegaStones.MEWTWONITE_Y)){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, pokemon);

                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                    AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "mega_evolve");
                }
            }
            else{
                player.setAttached(DataManage.MEGA_DATA, true);
                player.setAttached(DataManage.MEGA_POKEMON, pokemon);

                new FlagSpeciesFeature("mega", true).apply(pokemon);
                AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "mega_evolve");
            }
        } else if(species.getName().equals(pokemon.getSpecies().getName()) && playerData){
            player.sendMessage(
                    Text.literal("You can only have one mega at a time").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
        }else{
            player.sendMessage(
                    Text.literal("Don't have the correct stone").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
        }
    }

    public static void Devolve(LivingEntity context, PlayerEntity player){
        if(context instanceof PokemonEntity pk){
            if(pk.getPokemon().getOwnerPlayer() != player){
                return;
            }
        }

        Pokemon pokemon = ((PokemonEntity) context).getPokemon();

        if(player.getWorld().isClient){
            return;
        }

        player.setAttached(DataManage.MEGA_DATA, false);
        player.setAttached(DataManage.MEGA_POKEMON, null);

        new FlagSpeciesFeature("mega", false).apply(pokemon);
        new FlagSpeciesFeature("mega-x", false).apply(pokemon);
        new FlagSpeciesFeature("mega-y", false).apply(pokemon);
    }
}
