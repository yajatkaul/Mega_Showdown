package com.cobblemon.yajatkaul.mega_showdown.megaevo;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.MegaBraceletItem;
import com.cobblemon.yajatkaul.mega_showdown.showdown.ShowdownUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

public class MegaLogic {
    public static void EvoLogic(Player playerContext){
        ServerPlayer player = (ServerPlayer) playerContext;

        if(Config.battleMode){
            return;
        }

        boolean hasMegaItem = CuriosApi.getCuriosInventory(player).map(inventory -> inventory.isEquipped(stack -> stack.getItem() instanceof MegaBraceletItem)).orElse(false);

        if(!hasMegaItem){
            return;
        }

        double range = 5.0;

        // Check for entities first
        Vec3 startPos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 endPos = startPos.add(lookVec.multiply(range, range, range));

        AABB searchBox = new AABB(startPos, endPos).inflate(1.0);

        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                player.level(),
                player,
                startPos,
                endPos,
                searchBox,
                entity -> !entity.isSpectator() && entity.isPickable(),
                0.3f
        );

        if (entityHit == null) {
            return;
        }

        if(entityHit.getEntity() instanceof PokemonEntity pk){
            if(pk.level().isClientSide){
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

    private static void Evolve(LivingEntity context, Player player){
        Pokemon pokemon = ((PokemonEntity) context).getPokemon();
        Species species = ShowdownUtils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());

        if(species == pokemon.getSpecies() && (!player.getData(DataManage.MEGA_DATA) || Config.multipleMegas)){
            if(species == ShowdownUtils.getSpecies("charizard")){
                if(pokemon.heldItem().is(ModItems.CHARIZARDITE_X)){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, pokemon);

                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                }else if(pokemon.heldItem().is(ModItems.CHARIZARDITE_Y)){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, pokemon);


                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                }
            }
            else if(species == ShowdownUtils.getSpecies("mewtwo")){
                if(pokemon.heldItem().is(ModItems.MEWTWONITE_X)){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, pokemon);

                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-x", true).apply(pokemon);
                }else if(pokemon.heldItem().is(ModItems.MEWTWONITE_Y)){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, pokemon);

                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);
                    new FlagSpeciesFeature("mega-y", true).apply(pokemon);
                }
            }
            else{
                player.setData(DataManage.MEGA_DATA, true);
                player.setData(DataManage.MEGA_POKEMON, pokemon);

                new FlagSpeciesFeature("mega", true).apply(pokemon);
            }
        }else if(pokemon.getSpecies() == ShowdownUtils.getSpecies("rayquaza")){
            boolean found = false;
            for (int i = 0; i < 4; i++){
                if(pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")){
                    player.setData(DataManage.MEGA_POKEMON, pokemon);
                    player.setData(DataManage.MEGA_DATA, true);

                    new FlagSpeciesFeature("mega", true).apply(pokemon);
                    found = true;
                }
            }
            if(!found){
                player.displayClientMessage(Component.literal("Rayquaza doesn't have dragonascent")
                        .withColor(0xFF0000), true);
            }
        }else if(species == pokemon.getSpecies() && player.getData(DataManage.MEGA_DATA)){
            player.displayClientMessage(Component.literal("You can only have one mega at a time")
                    .withColor(0xFF0000), true);
        }else{
            player.displayClientMessage(Component.literal("Don't have the correct stone")
                    .withColor(0xFF0000), true);
        }
    }

    private static void Devolve(LivingEntity context, Player player){
        if(player.level().isClientSide){
            return;
        }

        Pokemon pokemon = ((PokemonEntity) context).getPokemon();

        player.setData(DataManage.MEGA_DATA, false);
        player.setData(DataManage.MEGA_POKEMON, new Pokemon());

        new FlagSpeciesFeature("mega", false).apply(pokemon);
        new FlagSpeciesFeature("mega-x", false).apply(pokemon);
        new FlagSpeciesFeature("mega-y", false).apply(pokemon);
    }
}
