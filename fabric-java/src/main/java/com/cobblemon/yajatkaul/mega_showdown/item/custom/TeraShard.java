package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import static com.cobblemon.yajatkaul.mega_showdown.utility.TeraTypeHelper.getType;

public class TeraShard extends Item {
    public TeraShard(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity context, Hand hand) {
        if(player.getWorld().isClient){
            return ActionResult.PASS;
        }

        if(context instanceof PokemonEntity pk){
            Pokemon pokemon = pk.getPokemon();
            if(pokemon.getEntity() == null || pokemon.getEntity().getWorld().isClient || pokemon.getEntity().isBattling()){
                return ActionResult.PASS;
            }

            Item shard = stack.getItem().asItem();

            if(pokemon.getOwnerPlayer() == player && stack.getCount() == 50){
                AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "change_tera");
                stack.decrement(50);
                if(stack.getItem() != TeraMoves.STELLAR_TERA_SHARD.asItem()){
                    pokemon.setTeraType(getType(shard));
                }else{
                    pokemon.setTeraType(TeraTypes.getSTELLAR());
                }
            }else if (pokemon.getOwnerPlayer() == player && stack.getCount() != 50) {
                player.sendMessage(
                        Text.literal("Not allowed in battle").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
            }


            return ActionResult.SUCCESS;
        }

        return super.useOnEntity(stack, player, context, hand);
    }
}
