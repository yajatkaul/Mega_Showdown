package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.MegaLogic;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public class MegaBraceletItem extends Item {
    public MegaBraceletItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            ItemStack stack = user.getStackInHand(hand);

            // Get the player's Trinket component
            Optional<TrinketComponent> trinkets = TrinketsApi.getTrinketComponent(user);

            if (trinkets.isPresent()) {
                TrinketComponent trinketComponent = trinkets.get();

                // Get all Trinket slots under the "hand" slot group
                Map<String, TrinketInventory> handSlots = trinketComponent.getInventory().get("hand");

                if (handSlots != null) {
                    // Get the "bracelet" slot inside "hand" (hand/evowear)
                    TrinketInventory braceletInventory = handSlots.get("evowear");

                    if (braceletInventory != null) {
                        // Find an empty slot in "hand/bracelet"
                        for (int i = 0; i < braceletInventory.size(); i++) {
                            if (braceletInventory.getStack(i).isEmpty()) {
                                // Equip the item in the hand/bracelet slot
                                braceletInventory.setStack(i, stack.copy());
                                user.setStackInHand(hand, ItemStack.EMPTY); // Remove from hand

                                world.playSound(null, user.getX(), user.getY(), user.getZ(),
                                        SoundEvents.ITEM_ARMOR_EQUIP_GENERIC.value(),
                                        SoundCategory.PLAYERS, 1.0f, 1.0f);

                                return TypedActionResult.success(stack);
                            }
                        }
                    }
                }
            }
        }

        return super.use(world, user, hand);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity context, Hand hand) {
        //Battle Mode only
        if (player.getWorld().isClient || MegaShowdownConfig.battleModeOnly.get()) {
            return ActionResult.PASS;
        }

        if (context instanceof PokemonEntity pk) {
            Pokemon pokemon = pk.getPokemon();
            if (pokemon.getEntity() == null || pokemon.getEntity().getWorld().isClient) {
                return super.useOnEntity(stack, player, context, hand);
            }

            MegaLogic.EvoLogic((ServerPlayerEntity) player);
        }

        return super.useOnEntity(stack, player, context, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.mega_showdown.megabracelet.tooltip"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
