package com.cobblemon.yajatkaul.mega_showdown.item.custom.dynamax;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class MaxSoup extends Item {
    public MaxSoup(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity context, Hand hand) {
        if (player.getWorld().isClient || player.isCrawling()) {
            return ActionResult.PASS;
        }

        if (context instanceof PokemonEntity pk) {
            Pokemon pokemon = pk.getPokemon();
            if (pokemon.getEntity() == null || pokemon.getEntity().getWorld().isClient || pokemon.getEntity().isBattling()) {
                return ActionResult.PASS;
            }

            if (!Utils.GMAX_SPECIES.contains(pokemon.getSpecies().getName())) {
                return ActionResult.PASS;
            }

            if (pokemon.getOwnerPlayer() == player && pokemon.getGmaxFactor()) {
                pokemon.setGmaxFactor(false);

                player.setStackInHand(hand, new ItemStack(Items.BOWL));
                Vec3d pos = pk.getPos();

                player.getWorld().playSound(
                        null, pos.x, pos.y, pos.z,
                        SoundEvents.ENTITY_GENERIC_DRINK,
                        SoundCategory.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
                );

                player.sendMessage(
                        Text.translatable("message.mega_showdown.gmax_not_possible").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFFF))),
                        true
                );
                return ActionResult.SUCCESS;
            } else if (pokemon.getOwnerPlayer() == player && !pokemon.getGmaxFactor()) {
                pokemon.setGmaxFactor(true);

                player.setStackInHand(hand, new ItemStack(Items.BOWL));
                Vec3d pos = pk.getPos();

                player.getWorld().playSound(
                        null, pos.x, pos.y, pos.z,
                        SoundEvents.ENTITY_GENERIC_DRINK,
                        SoundCategory.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
                );

                player.sendMessage(
                        Text.translatable("message.mega_showdown.gmax_possible").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFFFFFF))),
                        true
                );
                return ActionResult.SUCCESS;
            }
        }

        return super.useOnEntity(stack, player, context, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.mega_showdown.max_soup.tooltip"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
