package com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PikaCase extends Item {
    public PikaCase(Properties arg) {
        super(arg);
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
    public InteractionResult interactLivingEntity(ItemStack arg, Player user, LivingEntity entity, InteractionHand arg4) {
        if (!user.level().isClientSide && entity instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == user && !pk.isBattling() && !user.isCrouching()) {
            Pokemon pokemon = pk.getPokemon();

            if (pokemon.getSpecies().getName().equals("Pikachu")) {
                String currentAspect = toggle_aspects.stream()
                        .filter(a -> pokemon.getAspects().contains(a))
                        .findFirst()
                        .orElse(null);

                // If it has none of them, do nothing
                if (currentAspect == null) {
                    return InteractionResult.PASS;
                }

                // Get the next aspect in the list
                int currentIndex = toggle_aspects.indexOf(currentAspect);
                int nextIndex = (currentIndex + 1) % toggle_aspects.size();
                String nextAspect = toggle_aspects.get(nextIndex);

                new StringSpeciesFeature("cosplay", nextAspect).apply(pokemon);

                Vec3 pos = pk.position();
                pk.level().playSound(
                        null, pos.x, pos.y, pos.z,
                        SoundEvents.ARMOR_EQUIP_GENERIC,
                        SoundSource.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
                );

                return InteractionResult.SUCCESS;
            }
        }

        return super.interactLivingEntity(arg, user, entity, arg4);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.mega_showdown.pika_case.tooltip"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
