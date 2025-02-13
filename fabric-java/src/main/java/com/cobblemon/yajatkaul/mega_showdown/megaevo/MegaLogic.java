package com.cobblemon.yajatkaul.mega_showdown.megaevo;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.config.ShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.MegaBraceletItem;
import com.cobblemon.yajatkaul.mega_showdown.showdown.ShowdownUtils;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ActionResult;
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

        if(!hasMegaItem){
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

            if(pk.getPokemon().getOwnerPlayer() != player){
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

    private static void Evolve(LivingEntity context, PlayerEntity player){
        Pokemon pokemon = ((PokemonEntity) context).getPokemon();
        Species species = ShowdownUtils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());
        Boolean playerData = player.getAttached(DataManage.MEGA_DATA);
        if(playerData == null){
            playerData = false;
        }

        //Multiple megas
        if(species == pokemon.getSpecies() && (!playerData || ShowdownConfig.multipleMegas.get())){

            if(species == ShowdownUtils.getSpecies("charizard")){
                if(pokemon.heldItem().isOf(ModItems.CHARIZARDITE_X)){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, pokemon);

                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                }else if(pokemon.heldItem().isOf(ModItems.CHARIZARDITE_Y)){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, pokemon);

                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                }
            }
            else if(species == ShowdownUtils.getSpecies("mewtwo")){
                if(pokemon.heldItem().isOf(ModItems.MEWTWONITE_X)){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, pokemon);

                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                }else if(pokemon.heldItem().isOf(ModItems.MEWTWONITE_Y)){
                    player.setAttached(DataManage.MEGA_DATA, true);
                    player.setAttached(DataManage.MEGA_POKEMON, pokemon);

                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                }
            }
            else{
                player.setAttached(DataManage.MEGA_DATA, true);
                player.setAttached(DataManage.MEGA_POKEMON, pokemon);

                new FlagSpeciesFeature("mega", true).apply(pokemon);
            }
        }else if(pokemon.getSpecies() == ShowdownUtils.getSpecies("rayquaza")){
            boolean found = false;
            for (int i = 0; i < 4; i++){
                if(pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")){
                    player.setAttached(DataManage.MEGA_POKEMON, pokemon);
                    player.setAttached(DataManage.MEGA_DATA, true);

                    new FlagSpeciesFeature("mega", true).apply(pokemon);
                    found = true;
                }
            }
            if(!found){
                player.sendMessage(
                        Text.literal("Rayquaza doesn't have dragonascent").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
            }
        }else if(species == pokemon.getSpecies() && playerData){
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

    private static void Devolve(LivingEntity context, PlayerEntity player){
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
