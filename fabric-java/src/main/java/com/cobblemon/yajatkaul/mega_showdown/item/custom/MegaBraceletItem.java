package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.cobblemon.yajatkaul.mega_showdown.Config;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.showdown.ShowdownUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.List;

public class MegaBraceletItem extends Item {

    public MegaBraceletItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity context, Hand hand) {
        if(hand == Hand.MAIN_HAND && context instanceof PokemonEntity && !context.getWorld().isClient){
            Pokemon pokemon = ((PokemonEntity) context).getPokemon();
            Species species = ShowdownUtils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());
            Boolean playerData = player.getAttached(DataManage.MEGA_DATA);
            if(playerData == null){
                playerData = false;
            }

            if(species == pokemon.getSpecies() && (!playerData || Config.getInstance().multipleMegas)){

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
                for (int i = 0; i < 4; i++){
                    if(pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")){
                        player.setAttached(DataManage.MEGA_DATA, true);
                        player.setAttached(DataManage.MEGA_POKEMON, pokemon);

                        new FlagSpeciesFeature("mega", true).apply(pokemon);
                    }
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
        } else if (hand == Hand.OFF_HAND && !context.getWorld().isClient) {
            Pokemon pokemon = ((PokemonEntity) context).getPokemon();
            List<String> megaKeys = List.of("mega-x", "mega-y", "mega");

            for (String key : megaKeys) {
                FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of(key));

                FlagSpeciesFeature feature = featureProvider.get(pokemon);
                if(feature != null){
                    boolean enabled = featureProvider.get(pokemon).getEnabled();

                    if (enabled && feature.getName().equals("mega")) {
                        player.setAttached(DataManage.MEGA_DATA, false);
                        player.setAttached(DataManage.MEGA_POKEMON, null);

                        new FlagSpeciesFeature("mega", false).apply(pokemon);

                    }else if(enabled && feature.getName().equals("mega-x")){
                        player.setAttached(DataManage.MEGA_DATA, false);
                        player.setAttached(DataManage.MEGA_POKEMON, null);

                        new FlagSpeciesFeature("mega-x", false).apply(pokemon);

                    } else if (enabled && feature.getName().equals("mega-y")) {
                        player.setAttached(DataManage.MEGA_DATA, false);
                        player.setAttached(DataManage.MEGA_POKEMON, null);

                        new FlagSpeciesFeature("mega-y", false).apply(pokemon);
                    }
                }
            }
        }

        return super.useOnEntity(stack, player, context, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.mega_showdown.megabracelet.tooltip"));
        super.appendTooltip(stack, context, tooltip, type);
    }

}
