package com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MaxSoup extends Item {
    private static final Set<String> GMAX_SPECIES = new HashSet<>();

    static {
        GMAX_SPECIES.add("Venusaur");
        GMAX_SPECIES.add("Charizard");
        GMAX_SPECIES.add("Blastoise");
        GMAX_SPECIES.add("Butterfree");
        GMAX_SPECIES.add("Pikachu");
        GMAX_SPECIES.add("Meowth");
        GMAX_SPECIES.add("Machamp");
        GMAX_SPECIES.add("Gengar");
        GMAX_SPECIES.add("Kingler");
        GMAX_SPECIES.add("Lapras");
        GMAX_SPECIES.add("Eevee");
        GMAX_SPECIES.add("Snorlax");
        GMAX_SPECIES.add("Garbodor");
        GMAX_SPECIES.add("Melmetal");
        GMAX_SPECIES.add("Rillaboom");
        GMAX_SPECIES.add("Cinderace");
        GMAX_SPECIES.add("Inteleon");
        GMAX_SPECIES.add("Corviknight");
        GMAX_SPECIES.add("Orbeetle");
        GMAX_SPECIES.add("Drednaw");
        GMAX_SPECIES.add("Coalossal");
        GMAX_SPECIES.add("Flapple");
        GMAX_SPECIES.add("Appletun");
        GMAX_SPECIES.add("Sandaconda");
        GMAX_SPECIES.add("Toxtricity");
        GMAX_SPECIES.add("Centiskorch");
        GMAX_SPECIES.add("Hatterene");
        GMAX_SPECIES.add("Grimmsnarl");
        GMAX_SPECIES.add("Alcremie");
        GMAX_SPECIES.add("Copperajah");
        GMAX_SPECIES.add("Duraludon");
    }

    public MaxSoup(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity context, InteractionHand arg4) {
        if(player.level().isClientSide || player.isCrouching()){
            return InteractionResult.PASS;
        }

        if(context instanceof PokemonEntity pk){
            Pokemon pokemon = pk.getPokemon();
            if(pokemon.getEntity() == null || pokemon.getEntity().level().isClientSide || pokemon.getEntity().isBattling()){
                return InteractionResult.PASS;
            }

            if(!GMAX_SPECIES.contains(pokemon.getSpecies().getName())){
                return InteractionResult.PASS;
            }

            if(pokemon.getOwnerPlayer() == player && pokemon.getGmaxFactor()){
                pokemon.setGmaxFactor(false);

                stack.shrink(1);

                player.displayClientMessage(Component.literal("Your pokemon cannot gmax now")
                        .withColor(0xFFFFFF), true);
                return InteractionResult.SUCCESS;
            }else if (pokemon.getOwnerPlayer() == player && !pokemon.getGmaxFactor()){
                pokemon.setGmaxFactor(true);
                stack.shrink(1);

                player.displayClientMessage(Component.literal("Your pokemon can gmax now")
                        .withColor(0xFFFFFF), true);
                return InteractionResult.SUCCESS;
            }
        }

        return super.interactLivingEntity(stack, player, context, arg4);
    }

    @Override
    public void appendHoverText(ItemStack arg, TooltipContext arg2, List<Component> tooltipComponents, TooltipFlag arg3) {
        tooltipComponents.add(Component.translatable("tooltip.mega_showdown.max_soup.tooltip"));
        super.appendHoverText(arg, arg2, tooltipComponents, arg3);
    }
}
