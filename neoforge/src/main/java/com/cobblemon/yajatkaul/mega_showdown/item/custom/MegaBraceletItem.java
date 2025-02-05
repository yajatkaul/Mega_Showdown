package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.showdown.ShowdownUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.level.NoteBlockEvent;
import org.jetbrains.annotations.NotNull;


import java.util.List;


public class MegaBraceletItem extends Item {
    public MegaBraceletItem(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack arg, Player player, @NotNull LivingEntity context, InteractionHand hand) {
        if(context instanceof PokemonEntity pk){
            if(!pk.getPokemon().isPlayerOwned()){
                return InteractionResult.PASS;
            }
        }

        if (!player.level().isClientSide && Config.battleMode){
            player.sendSystemMessage(Component.literal("BATTLE_MODE_ONLY is enabled this item is only required to be equipped in your off hand during battle, to enable megas outside battle please change your config settings")
                    .withStyle(style -> style.withColor(0xFF0000)));
            return InteractionResult.PASS;
        }

        if(Config.braceletHandSensitive){
            if(hand == InteractionHand.MAIN_HAND && context instanceof PokemonEntity && !context.level().isClientSide()){
                Evolve(context, player);
            } else if (hand == InteractionHand.OFF_HAND && context instanceof PokemonEntity && !context.level().isClientSide()) {
                Devolve(context, player);
            }
        }else if(!Config.braceletHandSensitive && context instanceof PokemonEntity pk){
            Pokemon pokemon = pk.getPokemon();
            if(pokemon.getEntity() == null || pokemon.getEntity().level().isClientSide){
                return InteractionResult.PASS;
            }
            List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

            boolean end = false;
            for (String key : megaKeys) {
                FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));
                FlagSpeciesFeature feature = featureProvider.get(pokemon);

                if(feature != null){
                    boolean enabled = featureProvider.get(pokemon).getEnabled();

                    if(enabled){
                        Devolve(context, player);
                        end = false;
                        break;
                    }else{
                        end = true;
                    }

                }
            }
            if(end){
                Evolve(context, player);
            }
        }


        return InteractionResult.PASS;
    }

    private void Evolve(LivingEntity context, Player player){
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

    private void Devolve(LivingEntity context, Player player){
        Pokemon pokemon = ((PokemonEntity) context).getPokemon();
        List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

        for (String key : megaKeys) {
            FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));

            FlagSpeciesFeature feature = featureProvider.get(pokemon);
            if(feature != null){
                boolean enabled = featureProvider.get(pokemon).getEnabled();

                if (enabled && feature.getName().equals("mega")) {
                    player.setData(DataManage.MEGA_DATA, false);
                    player.setData(DataManage.MEGA_POKEMON, new Pokemon());

                    new FlagSpeciesFeature("mega", false).apply(pokemon);

                }else if(enabled && feature.getName().equals("mega-x")){
                    player.setData(DataManage.MEGA_DATA, false);
                    player.setData(DataManage.MEGA_POKEMON, new Pokemon());

                    new FlagSpeciesFeature("mega-x", false).apply(pokemon);

                } else if (enabled && feature.getName().equals("mega-y")) {
                    player.setData(DataManage.MEGA_DATA, false);
                    player.setData(DataManage.MEGA_POKEMON, new Pokemon());

                    new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack arg, TooltipContext arg2, List<Component> tooltipComponents, TooltipFlag arg3) {
        tooltipComponents.add(Component.translatable("tooltip.mega_showdown.megabracelet.tooltip"));
        super.appendHoverText(arg, arg2, tooltipComponents, arg3);
    }
}
