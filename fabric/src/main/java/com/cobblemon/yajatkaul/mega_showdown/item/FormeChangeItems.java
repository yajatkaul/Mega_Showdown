package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.dataAttachments.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange.*;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion.DNA_Splicer;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion.N_Lunarizer;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion.N_Solarizer;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion.Unity;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.zygarde.ZygardeCube;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.List;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.registerItem;

public class FormeChangeItems {

    public static final Item ADAMANT_CRYSTAL = registerItem("adamant_crystal", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.adamant_crystal.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });


    public static final Item LUSTROUS_GLOBE = registerItem("lustrous_globe", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.lustrous_globe.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item FLAME_PLATE = registerItem("flameplate", new ArceusType(new Item.Settings().maxCount(1), "fire") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.flameplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SPLASH_PLATE = registerItem("splashplate", new ArceusType(new Item.Settings().maxCount(1), "water") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.splashplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ZAP_PLATE = registerItem("zapplate", new ArceusType(new Item.Settings().maxCount(1), "electric") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.zapplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MEADOW_PLATE = registerItem("meadowplate", new ArceusType(new Item.Settings().maxCount(1), "grass") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.meadowplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ICICLE_PLATE = registerItem("icicleplate", new ArceusType(new Item.Settings().maxCount(1), "ice") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.icicleplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item FIST_PLATE = registerItem("fistplate", new ArceusType(new Item.Settings().maxCount(1), "fighting") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.fistplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item TOXIC_PLATE = registerItem("toxicplate", new ArceusType(new Item.Settings().maxCount(1), "poison") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.toxicplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item EARTH_PLATE = registerItem("earthplate", new ArceusType(new Item.Settings().maxCount(1), "ground") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.earthplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SKY_PLATE = registerItem("skyplate", new ArceusType(new Item.Settings().maxCount(1), "flying") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.skyplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MIND_PLATE = registerItem("mindplate", new ArceusType(new Item.Settings().maxCount(1), "psychic") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.mindplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item INSECT_PLATE = registerItem("insectplate", new ArceusType(new Item.Settings().maxCount(1), "bug") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.insectplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item STONE_PLATE = registerItem("stoneplate", new ArceusType(new Item.Settings().maxCount(1), "rock") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.stoneplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SPOOKY_PLATE = registerItem("spookyplate", new ArceusType(new Item.Settings().maxCount(1), "ghost") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.spookyplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item DRACO_PLATE = registerItem("dracoplate", new ArceusType(new Item.Settings().maxCount(1), "dragon") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.dracoplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item DREAD_PLATE = registerItem("dreadplate", new ArceusType(new Item.Settings().maxCount(1), "dark") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.dreadplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item IRON_PLATE = registerItem("ironplate", new ArceusType(new Item.Settings().maxCount(1), "steel") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.ironplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item PIXIE_PLATE = registerItem("pixieplate", new ArceusType(new Item.Settings().maxCount(1), "fairy") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.pixieplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item BUG_MEMORY = registerItem("bugmemory", new Memory(new Item.Settings().maxCount(1), "bug") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.bugmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item DARK_MEMORY = registerItem("darkmemory", new Memory(new Item.Settings().maxCount(1), "dark") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.darkmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item DRAGON_MEMORY = registerItem("dragonmemory", new Memory(new Item.Settings().maxCount(1), "dragon") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.dragonmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ELECTRIC_MEMORY = registerItem("electricmemory", new Memory(new Item.Settings().maxCount(1), "electric") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.electricmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item FAIRY_MEMORY = registerItem("fairymemory", new Memory(new Item.Settings().maxCount(1), "fairy") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.fairymemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item FIGHTING_MEMORY = registerItem("fightingmemory", new Memory(new Item.Settings().maxCount(1), "fighting") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.fightingmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item FIRE_MEMORY = registerItem("firememory", new Memory(new Item.Settings().maxCount(1), "fire") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.firememory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item FLYING_MEMORY = registerItem("flyingmemory", new Memory(new Item.Settings().maxCount(1), "flying") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.flyingmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GHOST_MEMORY = registerItem("ghostmemory", new Memory(new Item.Settings().maxCount(1), "ghost") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.ghostmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GRASS_MEMORY = registerItem("grassmemory", new Memory(new Item.Settings().maxCount(1), "grass") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.grassmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GROUND_MEMORY = registerItem("groundmemory", new Memory(new Item.Settings().maxCount(1), "ground") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.groundmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ICE_MEMORY = registerItem("icememory", new Memory(new Item.Settings().maxCount(1), "ice") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.icememory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item POISON_MEMORY = registerItem("poisonmemory", new Memory(new Item.Settings().maxCount(1), "poison") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.poisonmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item PSYCHIC_MEMORY = registerItem("psychicmemory", new Memory(new Item.Settings().maxCount(1), "psychic") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.psychicmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ROCK_MEMORY = registerItem("rockmemory", new Memory(new Item.Settings().maxCount(1), "rock") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.rockmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item STEEL_MEMORY = registerItem("steelmemory", new Memory(new Item.Settings().maxCount(1), "steel") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.steelmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item WATER_MEMORY = registerItem("watermemory", new Memory(new Item.Settings().maxCount(1), "water") {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.watermemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item BURN_DRIVE = registerItem("burndrive", new Drives(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.burndrive.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item CHILL_DRIVE = registerItem("chilldrive", new Drives(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.chilldrive.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item DOUSE_DRIVE = registerItem("dousedrive", new Drives(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.dousedrive.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SHOCK_DRIVE = registerItem("shockdrive", new Drives(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.shockdrive.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item PINK_NECTAR = registerItem("pink_nectar", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.pink_nectar.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }

        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
            if (entity instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == user && !pk.isBattling() && !user.isCrawling()) {
                Pokemon pokemon = pk.getPokemon();

                if (pokemon.getSpecies().getName().equals("Oricorio")) {
                    new StringSpeciesFeature("dance_style", "pau").apply(pokemon);
                    stack.decrement(1);
                    playFormeChangeAnimation(pk);
                    return ActionResult.SUCCESS;
                }
            }

            return super.useOnEntity(stack, user, entity, hand);
        }
    });

    public static final Item PURPLE_NECTAR = registerItem("purple_nectar", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.purple_nectar.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }

        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
            if (entity instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == user && !pk.isBattling() && !user.isCrawling()) {
                Pokemon pokemon = pk.getPokemon();

                if (pokemon.getSpecies().getName().equals("Oricorio")) {
                    new StringSpeciesFeature("dance_style", "sensu").apply(pokemon);
                    playFormeChangeAnimation(pk);
                    stack.decrement(1);
                    return ActionResult.SUCCESS;
                }
            }

            return super.useOnEntity(stack, user, entity, hand);
        }
    });

    public static final Item RED_NECTAR = registerItem("red_nectar", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.red_nectar.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }

        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
            if (entity instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == user && !pk.isBattling() && !user.isCrawling()) {
                Pokemon pokemon = pk.getPokemon();

                if (pokemon.getSpecies().getName().equals("Oricorio")) {
                    new StringSpeciesFeature("dance_style", "baile").apply(pokemon);
                    stack.decrement(1);
                    playFormeChangeAnimation(pk);
                    return ActionResult.SUCCESS;
                }
            }

            return super.useOnEntity(stack, user, entity, hand);
        }
    });

    public static final Item YELLOW_DRIVE = registerItem("yellow_nectar", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.yellow_nectar.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }

        @Override
        public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
            if (entity instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == user && !pk.isBattling() && !user.isCrawling()) {
                Pokemon pokemon = pk.getPokemon();

                if (pokemon.getSpecies().getName().equals("Oricorio")) {
                    new StringSpeciesFeature("dance_style", "pom-pom").apply(pokemon);
                    playFormeChangeAnimation(pk);
                    stack.decrement(1);
                    return ActionResult.SUCCESS;
                }
            }

            return super.useOnEntity(stack, user, entity, hand);
        }
    });

    public static final Item CORNERSTONE_MASK = registerItem("cornerstone_mask", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.cornerstone_mask.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });


    public static final Item WELLSPRING_MASK = registerItem("wellspring_mask", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.wellspring_mask.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item HEARTHFLAME_MASK = registerItem("hearthflame_mask", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.hearthflame_mask.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item STAR_CORE = registerItem("star_core", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.star_core.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GRISEOUS_CORE = registerItem("griseous_core", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.griseous_core.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ASH_CAP = registerItem("ash_cap", new Cap(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.ash_cap.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item N_LUNARIZER = registerItem("n_lunarizer", new N_Lunarizer(new Item.Settings().maxCount(1).component(DataManage.POKEMON_STORAGE, null)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.n_lunarizer.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });
    public static final Item N_SOLARIZER = registerItem("n_solarizer", new N_Solarizer(new Item.Settings().maxCount(1).component(DataManage.POKEMON_STORAGE, null)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.n_solarizer.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });
    public static final Item DNA_SPLICER = registerItem("dna_splicer", new DNA_Splicer(new Item.Settings().maxCount(1).component(DataManage.POKEMON_STORAGE, null)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.dna_splicer.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item RUSTED_SWORD = registerItem("rusted_sword", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.rusted_sword.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item RUSTED_SHIELD = registerItem("rusted_shield", new Item(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.rusted_shield.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item PRISON_BOTTLE = registerItem("prison_bottle", new Unbound(new Item.Settings().maxCount(1)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.prison_bottle.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item REINS_OF_UNITY = registerItem("reins_of_unity", new Unity(new Item.Settings().maxCount(1).component(DataManage.POKEMON_STORAGE, null)) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.reins_of_unity.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GRACIDEA_FLOWER = registerItem("gracidea_flower", new Gracidea(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.gracidea_flower.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final BlockItem DEOXYS_METEORITE = Registry.register(Registries.ITEM, Identifier.of(MegaShowdown.MOD_ID, "deoxys_meteorite"), new DeoxysMeteorite(ModBlocks.DEOXYS_METEORITE, new Item.Settings()));

    public static final Item ZYGARDE_CUBE = registerItem("zygarde_cube",
            new ZygardeCube(new Item.Settings().maxCount(1).component(DataManage.ZYGARDE_CUBE_INV, null)
                    .component(DataManage.POKEMON_STORAGE, null)) {
                @Override
                public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
                    tooltip.add(Text.translatable("tooltip.mega_showdown.zygarde_cube.tooltip"));
                    super.appendTooltip(stack, context, tooltip, type);
                }
            });

    public static final Item ZYGARDE_CELL = registerItem("zygarde_cell",
            new Item(new Item.Settings().maxCount(95)) {
                @Override
                public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
                    tooltip.add(Text.translatable("tooltip.mega_showdown.zygarde_cell.tooltip"));
                    super.appendTooltip(stack, context, tooltip, type);
                }
            });

    public static final Item ZYGARDE_CORE = registerItem("zygarde_core",
            new Item(new Item.Settings().maxCount(5)) {
                @Override
                public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
                    tooltip.add(Text.translatable("tooltip.mega_showdown.zygarde_core.tooltip"));
                    super.appendTooltip(stack, context, tooltip, type);
                }
            });

    public static final Item PIKA_CASE = registerItem("pika_case",
            new PikaCase(new Item.Settings().maxCount(1)));

    public static final Item REVEAL_GLASS = registerItem("reveal_glass",
            new RevealGlass(new Item.Settings().maxCount(1)));

    public static final Item FURFROU_TRIM_SMITHING_TEMPLATE = Registry.register(
            Registries.ITEM,
            Identifier.of(MegaShowdown.MOD_ID, "furfrou_upgrade"),
            new SmithingTemplateItem(
                    Text.translatable("item.mega_showdown.furfrou_trim_smithing_template.applies_to"),       // Applies to
                    Text.translatable("item.mega_showdown.furfrou_trim_smithing_template.ingredients"),       // Ingredients
                    Text.translatable("item.mega_showdown.furfrou_trim_smithing_template.upgrade"),           // Description title
                    Text.translatable("item.mega_showdown.furfrou_trim_smithing_template.base_slot"),         // Base slot text
                    Text.translatable("item.mega_showdown.furfrou_trim_smithing_template.additions_slot"),    // Additions slot text
                    List.of(
                            Identifier.of(MegaShowdown.MOD_ID, "item/shears")
                    ),
                    List.of(
                            Identifier.of(MegaShowdown.MOD_ID, "item/dye")
                    )
            )
    );
    //TRIMS

    public static final Item HEART_TRIM = registerItem("heart_trim",
            new FurfrouTrim(new Item.Settings().maxCount(1).maxDamage(100), "heart"));
    public static final Item STAR_TRIM = registerItem("star_trim",
            new FurfrouTrim(new Item.Settings().maxCount(1).maxDamage(100), "star"));
    public static final Item DIAMOND_TRIM = registerItem("diamond_trim",
            new FurfrouTrim(new Item.Settings().maxCount(1).maxDamage(100), "diamond"));
    public static final Item DEBUTANTE_TRIM = registerItem("debutante_trim",
            new FurfrouTrim(new Item.Settings().maxCount(1).maxDamage(100), "debutante"));
    public static final Item MATRON_TRIM = registerItem("matron_trim",
            new FurfrouTrim(new Item.Settings().maxCount(1).maxDamage(100), "matron"));
    public static final Item DANDY_TRIM = registerItem("dandy_trim",
            new FurfrouTrim(new Item.Settings().maxCount(1).maxDamage(100), "dandy"));
    public static final Item LA_REINE_TRIM = registerItem("la_reine_trim",
            new FurfrouTrim(new Item.Settings().maxCount(1).maxDamage(100), "la_reine"));
    public static final Item KABUKI_TRIM = registerItem("kabuki_trim",
            new FurfrouTrim(new Item.Settings().maxCount(1).maxDamage(100), "kabuki"));
    public static final Item PHARAOH_TRIM = registerItem("pharaoh_trim",
            new FurfrouTrim(new Item.Settings().maxCount(1).maxDamage(100), "pharaoh"));

    public static void registerModItem() {

    }

    public static void playFormeChangeAnimation(LivingEntity context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, // Yarn mapping for BEACON_ACTIVATE
                    SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (100 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverWorld.spawnParticles(
                        ParticleTypes.END_ROD, // Same particle type
                        entityPos.x + xOffset,
                        entityPos.y + yOffset,
                        entityPos.z + zOffset,
                        1, // One particle per call for better spread
                        0, 0, 0, // No movement velocity
                        0.1 // Slight motion
                );
            }
        }
    }
}
