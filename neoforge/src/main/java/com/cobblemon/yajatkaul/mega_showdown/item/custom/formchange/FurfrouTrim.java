package com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

public class FurfrouTrim extends Item {
    final private String form;

    public FurfrouTrim(Properties arg, String form) {
        super(arg);
        this.form = form;
    }

    public static final List<String> furfrouAspects = List.of(
            "heart-trim",
            "star-trim",
            "diamond-trim",
            "debutante-trim",
            "matron-trim",
            "dandy-trim",
            "la_reine-trim",
            "kabuki-trim",
            "pharaoh-trim"
    );

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
        if (user.level().isClientSide || user.isCrouching()) {
            return InteractionResult.PASS;
        }

        if (entity instanceof PokemonEntity pk && pk.getPokemon().getSpecies().getName().equals("Furfrou") && !pk.isBattling()
                && pk.getAspects().stream().noneMatch(furfrouAspects::contains)) {
            new StringSpeciesFeature("poodle_trim", form).apply(pk.getPokemon());
            pk.getPokemon().getPersistentData().putBoolean("trimmed", true);
            stack.setDamageValue(stack.getDamageValue() + 20);
            Vec3 pos = pk.position();
            user.level().playSound(
                    null, pos.x, pos.y, pos.z,
                    SoundEvents.SHEEP_SHEAR,
                    SoundSource.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
            );
            Random random = new Random();
            int woolCount = random.nextInt(2) + 1;
            ItemStack drops = pk.getPokemon().getShiny() ?
                    new ItemStack(Items.BLACK_WOOL, woolCount) : new ItemStack(Items.WHITE_WOOL, woolCount);

            ItemEntity woolDrop = new ItemEntity(
                    user.level(),
                    pos.x, pos.y + 0.5, pos.z,
                    drops
            );

            user.level().addFreshEntity(woolDrop);
            if (stack.getDamageValue() >= stack.getMaxDamage()) {
                stack.shrink(1);
                user.level().playSound(
                        null, pos.x, pos.y, pos.z,
                        SoundEvents.ITEM_BREAK,
                        SoundSource.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
                );
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
