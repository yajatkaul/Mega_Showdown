package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.megaevo.MegaLogic;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;


import java.util.List;


public class MegaBraceletItem extends Item {
    public MegaBraceletItem(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack arg, Player player, @NotNull LivingEntity context, InteractionHand hand) {
        if (player.level().isClientSide || Config.battleMode){
//            player.sendSystemMessage(Component.literal("BATTLE_MODE_ONLY is enabled this item is only required to be equipped in your off hand during battle, to enable megas outside battle please change your config settings")
//                    .withStyle(style -> style.withColor(0xFF0000)));
            return InteractionResult.PASS;
        }

        if(Config.braceletHandSensitive){
            if(hand == InteractionHand.MAIN_HAND && context instanceof PokemonEntity pk && !context.level().isClientSide()){
                List<String> megaKeys = List.of("mega-x", "mega-y", "mega");
                boolean end = false;
                for (String key : megaKeys) {
                    FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));
                    FlagSpeciesFeature feature = featureProvider.get(pk.getPokemon());

                    if(feature != null){
                        boolean enabled = featureProvider.get(pk.getPokemon()).getEnabled();

                        if(enabled){
                            end = true;
                            break;
                        }
                    }
                }

                if(!end){
                    MegaLogic.Evolve(context, player);
                }
            } else if (hand == InteractionHand.OFF_HAND && context instanceof PokemonEntity && !context.level().isClientSide()) {
                MegaLogic.Devolve(context, player);
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
                        MegaLogic.Devolve(context, player);
                        end = false;
                        break;
                    }else{
                        end = true;
                    }

                }
            }
            if(end){
                MegaLogic.Evolve(context, player);
            }
        }

        return InteractionResult.PASS;
    }


    @Override
    public void appendHoverText(ItemStack arg, TooltipContext arg2, List<Component> tooltipComponents, TooltipFlag arg3) {
        tooltipComponents.add(Component.translatable("tooltip.mega_showdown.megabracelet.tooltip"));
        super.appendHoverText(arg, arg2, tooltipComponents, arg3);
    }
}
