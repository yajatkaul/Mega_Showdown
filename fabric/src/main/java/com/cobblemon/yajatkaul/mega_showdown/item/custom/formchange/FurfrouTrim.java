package com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Random;

public class FurfrouTrim extends Item {
    final private String form;

    public FurfrouTrim(Settings settings, String form) {
        super(settings);
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
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user.getWorld().isClient || user.isCrawling()) {
            return ActionResult.PASS;
        }

        if (entity instanceof PokemonEntity pk && pk.getPokemon().getSpecies().getName().equals("Furfrou") && !pk.isBattling()
                && pk.getAspects().stream().noneMatch(furfrouAspects::contains)) {
            new StringSpeciesFeature("poodle_trim", form).apply(pk.getPokemon());
            pk.getPokemon().getPersistentData().putBoolean("trimmed", true);
            stack.setDamage(stack.getDamage() + 20);
            Vec3d pos = pk.getPos();
            user.getWorld().playSound(
                    null, pos.x, pos.y, pos.z,
                    SoundEvents.ENTITY_SHEEP_SHEAR,
                    SoundCategory.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
            );
            int woolCount = new Random().nextInt(2) + 1;
            ItemStack drops = pk.getPokemon().getShiny() ?
                    new ItemStack(Items.BLACK_WOOL, woolCount) : new ItemStack(Items.WHITE_WOOL, woolCount);

            ItemEntity woolDrop = new ItemEntity(
                    user.getWorld(),
                    pos.x, pos.y + 0.5, pos.z,
                    drops
            );

            user.getWorld().spawnEntity(woolDrop);

            if (stack.getDamage() >= stack.getMaxDamage()) {
                stack.decrement(1);
                user.getWorld().playSound(
                        null, pos.x, pos.y, pos.z,
                        SoundEvents.ENTITY_ITEM_BREAK,
                        SoundCategory.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
                );
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
