package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import static com.cobblemon.yajatkaul.mega_showdown.utility.TeraTypeHelper.getType;

public class TeraShard extends Item {
    public TeraShard(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack arg, Player player, @NotNull LivingEntity context, InteractionHand hand) {
        if (player.level().isClientSide){
            return InteractionResult.PASS;
        }

        if(context instanceof PokemonEntity pk){
            Pokemon pokemon = pk.getPokemon();
            if(pokemon.getEntity() == null || pokemon.getEntity().level().isClientSide || pokemon.getEntity().isBattling()){
                return InteractionResult.PASS;
            }

            Item shard = arg.getItem().asItem();

            if(pokemon.getOwnerPlayer() == player && arg.getCount() == 50){
                AdvancementHelper.grantAdvancement((ServerPlayer) player, "change_tera");
                arg.shrink(50);
                if(arg.getItem() != TeraMoves.STELLAR_TERA_SHARD.get()){
                    pokemon.setTeraType(getType(shard));
                }else{
                    pokemon.setTeraType(TeraTypes.getSTELLAR());
                }
            } else if (pokemon.getOwnerPlayer() == player && arg.getCount() != 50) {
                player.displayClientMessage(Component.literal("You need 50 of these to be used")
                        .withColor(0xFF0000), true);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
