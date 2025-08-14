package com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class PikaCase extends Item {
    public PikaCase(Settings settings) {
        super(settings);
    }

    private final List<String> toggle_aspects = List.of(
            "cosplay",
            "belle",
            "libre",
            "phd",
            "pop_star",
            "rock_star"
    );

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getWorld().isClient && entity instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == user && !pk.isBattling() && !user.isCrawling()) {
            Pokemon pokemon = pk.getPokemon();

            if (pokemon.getSpecies().getName().equals("Pikachu")) {
                String currentAspect = toggle_aspects.stream()
                        .filter(a -> pokemon.getAspects().contains(a))
                        .findFirst()
                        .orElse(null);

                // If it has none of them, do nothing
                if (currentAspect == null) {
                    return ActionResult.PASS;
                }

                // Get the next aspect in the list
                int currentIndex = toggle_aspects.indexOf(currentAspect);
                int nextIndex = (currentIndex + 1) % toggle_aspects.size();
                String nextAspect = toggle_aspects.get(nextIndex);

                new StringSpeciesFeature("cosplay", nextAspect).apply(pokemon);

                Vec3d pos = pk.getPos();
                pk.getWorld().playSound(
                        null, pos.x, pos.y, pos.z,
                        SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
                        SoundCategory.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
                );

                return ActionResult.SUCCESS;
            }
        }

        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.mega_showdown.pika_case.tooltip"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
