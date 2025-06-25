package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.formchange.*;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion.DNA_Splicer;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion.N_Lunarizer;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion.N_Solarizer;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion.Unity;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.zygarde.ZygardeCube;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.Random;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.ITEMS;

public class FormeChangeItems {
    public static final DeferredItem<Item> PINK_NECTAR = ITEMS.register("pink_nectar",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.pink_nectar.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }

                @Override
                public InteractionResult interactLivingEntity(ItemStack arg, Player user, LivingEntity entity, InteractionHand arg4) {
                    if (entity instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == user && !pk.isBattling() && !user.isCrouching()) {
                        Pokemon pokemon = pk.getPokemon();

                        if (pokemon.getSpecies().getName().equals("Oricorio")) {
                            new StringSpeciesFeature("dance_style", "pau").apply(pokemon);
                            arg.shrink(1);
                            playFormeChangeAnimation(pk);
                            return InteractionResult.SUCCESS;
                        }
                    }

                    return super.interactLivingEntity(arg, user, entity, arg4);
                }
            });

    public static final DeferredItem<Item> PURPLE_NECTAR = ITEMS.register("purple_nectar",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.purple_nectar.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }

                @Override
                public InteractionResult interactLivingEntity(ItemStack arg, Player user, LivingEntity entity, InteractionHand arg4) {
                    if (entity instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == user && !pk.isBattling() && !user.isCrouching()) {
                        Pokemon pokemon = pk.getPokemon();

                        if (pokemon.getSpecies().getName().equals("Oricorio")) {
                            new StringSpeciesFeature("dance_style", "sensu").apply(pokemon);
                            arg.shrink(1);
                            playFormeChangeAnimation(pk);
                            return InteractionResult.SUCCESS;
                        }
                    }

                    return super.interactLivingEntity(arg, user, entity, arg4);
                }
            });

    public static final DeferredItem<Item> RED_NECTAR = ITEMS.register("red_nectar",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.red_nectar.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }

                @Override
                public InteractionResult interactLivingEntity(ItemStack arg, Player user, LivingEntity entity, InteractionHand arg4) {
                    if (entity instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == user && !pk.isBattling() && !user.isCrouching()) {
                        Pokemon pokemon = pk.getPokemon();

                        if (pokemon.getSpecies().getName().equals("Oricorio")) {
                            new StringSpeciesFeature("dance_style", "baile").apply(pokemon);
                            arg.shrink(1);
                            playFormeChangeAnimation(pk);
                            return InteractionResult.SUCCESS;
                        }
                    }

                    return super.interactLivingEntity(arg, user, entity, arg4);
                }
            });

    public static final DeferredItem<Item> YELLOW_NECTAR = ITEMS.register("yellow_nectar",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.yellow_nectar.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }

                @Override
                public InteractionResult interactLivingEntity(ItemStack arg, Player user, LivingEntity entity, InteractionHand arg4) {
                    if (entity instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == user && !pk.isBattling() && !user.isCrouching()) {
                        Pokemon pokemon = pk.getPokemon();
                        if (pokemon.getSpecies().getName().equals("Oricorio")) {
                            new StringSpeciesFeature("dance_style", "pom-pom").apply(pokemon);
                            arg.shrink(1);
                            playFormeChangeAnimation(pk);
                            return InteractionResult.SUCCESS;
                        }
                    }
                    return super.interactLivingEntity(arg, user, entity, arg4);
                }
            });

    public static final DeferredItem<Item> CORNERSTONE_MASK = ITEMS.register("cornerstone_mask",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.cornerstone_mask.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> WELLSPRING_MASK = ITEMS.register("wellspring_mask",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.wellspring_mask.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> HEARTHFLAME_MASK = ITEMS.register("hearthflame_mask",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.hearthflame_mask.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> STAR_CORE = ITEMS.register("star_core",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.star_core.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> GRISEOUS_CORE = ITEMS.register("griseous_core",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.griseous_core.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ASH_CAP = ITEMS.register("ash_cap",
            () -> new Cap(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.ash_cap.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ADAMANT_CRYSTAL = ITEMS.register("adamant_crystal",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.adamant_crystal.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> LUSTROUS_GLOBE = ITEMS.register("lustrous_globe",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.lustrous_globe.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> FLAME_PLATE = ITEMS.register("flameplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "fire") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.flameplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> SPLASH_PLATE = ITEMS.register("splashplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "water") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.splashplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ZAP_PLATE = ITEMS.register("zapplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "electric") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.zapplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> MEADOW_PLATE = ITEMS.register("meadowplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "grass") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.meadowplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ICICLE_PLATE = ITEMS.register("icicleplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "ice") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.icicleplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> FIST_PLATE = ITEMS.register("fistplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "fighting") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.fistplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> TOXIC_PLATE = ITEMS.register("toxicplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "poison") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.toxicplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> EARTH_PLATE = ITEMS.register("earthplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "ground") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.earthplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> SKY_PLATE = ITEMS.register("skyplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "flying") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.skyplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> MIND_PLATE = ITEMS.register("mindplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "psychic") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.mindplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> INSECT_PLATE = ITEMS.register("insectplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "bug") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.insectplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> STONE_PLATE = ITEMS.register("stoneplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "rock") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.stoneplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> SPOOKY_PLATE = ITEMS.register("spookyplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "ghost") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.spookyplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> DRACO_PLATE = ITEMS.register("dracoplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "dragon") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.dracoplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> DREAD_PLATE = ITEMS.register("dreadplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "dark") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.dreadplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> IRON_PLATE = ITEMS.register("ironplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "steel") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.ironplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> PIXIE_PLATE = ITEMS.register("pixieplate",
            () -> new ArceusType(new Item.Properties().stacksTo(1), "fairy") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.pixieplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> BUG_MEMORY = ITEMS.register("bugmemory",
            () -> new Memory(new Item.Properties().stacksTo(1), "bug") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.bugmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> DARK_MEMORY = ITEMS.register("darkmemory",
            () -> new Memory(new Item.Properties().stacksTo(1), "dark") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.darkmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> DRAGON_MEMORY = ITEMS.register("dragonmemory",
            () -> new Memory(new Item.Properties().stacksTo(1), "dragon") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.dragonmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ELECTRIC_MEMORY = ITEMS.register("electricmemory",
            () -> new Memory(new Item.Properties().stacksTo(1), "electic") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.electricmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> FAIRY_MEMORY = ITEMS.register("fairymemory",
            () -> new Memory(new Item.Properties().stacksTo(1), "fairy") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.fairymemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> FIGHTING_MEMORY = ITEMS.register("fightingmemory",
            () -> new Memory(new Item.Properties().stacksTo(1), "fighting") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.fightingmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> FIRE_MEMORY = ITEMS.register("firememory",
            () -> new Memory(new Item.Properties().stacksTo(1), "fire") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.firememory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> FLYING_MEMORY = ITEMS.register("flyingmemory",
            () -> new Memory(new Item.Properties().stacksTo(1), "flying") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.flyingmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> GHOST_MEMORY = ITEMS.register("ghostmemory",
            () -> new Memory(new Item.Properties().stacksTo(1), "ghost") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.ghostmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> GRASS_MEMORY = ITEMS.register("grassmemory",
            () -> new Memory(new Item.Properties().stacksTo(1), "grass") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.grassmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> GROUND_MEMORY = ITEMS.register("groundmemory",
            () -> new Memory(new Item.Properties().stacksTo(1), "ground") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.groundmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ICE_MEMORY = ITEMS.register("icememory",
            () -> new Memory(new Item.Properties().stacksTo(1), "ice") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.icememory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> POISON_MEMORY = ITEMS.register("poisonmemory",
            () -> new Memory(new Item.Properties().stacksTo(1), "poison") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.poisonmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> PSYCHIC_MEMORY = ITEMS.register("psychicmemory",
            () -> new Memory(new Item.Properties().stacksTo(1), "psychic") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.psychicmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ROCK_MEMORY = ITEMS.register("rockmemory",
            () -> new Memory(new Item.Properties().stacksTo(1), "rock") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.rockmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> STEEL_MEMORY = ITEMS.register("steelmemory",
            () -> new Memory(new Item.Properties().stacksTo(1), "steel") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.steelmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> WATER_MEMORY = ITEMS.register("watermemory",
            () -> new Memory(new Item.Properties().stacksTo(1), "water") {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.watermemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> BURN_DRIVE = ITEMS.register("burndrive",
            () -> new Drives(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.burndrive.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> CHILL_DRIVE = ITEMS.register("chilldrive",
            () -> new Drives(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.chilldrive.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> DOUSE_DRIVE = ITEMS.register("dousedrive",
            () -> new Drives(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.dousedrive.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> SHOCK_DRIVE = ITEMS.register("shockdrive",
            () -> new Drives(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.shockdrive.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> N_LUNARIZER = ITEMS.register("n_lunarizer",
            () -> new N_Lunarizer(new Item.Properties().stacksTo(1).component(DataManage.POKEMON_STORAGE, null)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.n_lunarizer.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> N_SOLARIZER = ITEMS.register("n_solarizer",
            () -> new N_Solarizer(new Item.Properties().stacksTo(1).component(DataManage.POKEMON_STORAGE, null)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.n_solarizer.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> DNA_SPLICER = ITEMS.register("dna_splicer",
            () -> new DNA_Splicer(new Item.Properties().stacksTo(1).component(DataManage.POKEMON_STORAGE, null)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.dna_splicer.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> RUSTED_SWORD = ITEMS.register("rusted_sword",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.rusted_sword.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> RUSTED_SHIELD = ITEMS.register("rusted_shield",
            () -> new Item(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.rusted_shield.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> PRISON_BOTTLE = ITEMS.register("prison_bottle",
            () -> new Unbound(new Item.Properties().stacksTo(1)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.prison_bottle.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> REINS_OF_UNITY = ITEMS.register("reins_of_unity",
            () -> new Unity(new Item.Properties().stacksTo(1).component(DataManage.POKEMON_STORAGE, null)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.reins_of_unity.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> GRACIDEA_FLOWER = ITEMS.register("gracidea_flower",
            () -> new Gracidea(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.gracidea_flower.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ZYGARDE_CUBE = ITEMS.register("zygarde_cube",
            () -> new ZygardeCube(new Item.Properties().stacksTo(1).component(DataManage.ZYGARDE_INV, null).component(DataManage.POKEMON_STORAGE, null)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.zygarde_cube.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ZYGARDE_CELL = ITEMS.register("zygarde_cell",
            () -> new Item(new Item.Properties().stacksTo(95)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.zygarde_cell.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ZYGARDE_CORE = ITEMS.register("zygarde_core",
            () -> new Item(new Item.Properties().stacksTo(5)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.zygarde_core.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> REVEAL_GLASS = ITEMS.register("reveal_glass",
            () -> new RevealGlass(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<BlockItem> DEOXYS_METEORITE = ModItems.ITEMS.register("deoxys_meteorite", () -> new BlockItem(ModBlocks.DEOXYS_METEORITE.get(),
            new Item.Properties()) {
        @Override
        public InteractionResult interactLivingEntity(ItemStack arg, Player user, LivingEntity entity, InteractionHand arg4) {
            if (!user.level().isClientSide && entity instanceof PokemonEntity pk && pk.getPokemon().getOwnerPlayer() == user && !pk.isBattling() && !user.isCrouching()) {
                Pokemon pokemon = pk.getPokemon();

                if (pokemon.getSpecies().getName().equals("Deoxys")) {
                    if (pokemon.getAspects().contains("normal-forme")) {
                        new StringSpeciesFeature("meteorite_forme", "attack").apply(pokemon);
                    } else if (pokemon.getAspects().contains("attack-forme")) {
                        new StringSpeciesFeature("meteorite_forme", "speed").apply(pokemon);
                    } else if (pokemon.getAspects().contains("speed-forme")) {
                        new StringSpeciesFeature("meteorite_forme", "defense").apply(pokemon);
                    } else if (pokemon.getAspects().contains("defense-forme")) {
                        new StringSpeciesFeature("meteorite_forme", "normal").apply(pokemon);
                    }

                    arg.shrink(1);
                    return InteractionResult.SUCCESS;
                }
            }

            return super.interactLivingEntity(arg, user, entity, arg4);
        }

        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            tooltipComponents.add(Component.translatable("tooltip.mega_showdown.deoxys_meteorite.tooltip"));
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        }
    });
    public static final DeferredItem<SmithingTemplateItem> FURFROU_TRIM_SMITHING_TEMPLATE = ITEMS.register("furfrou_upgrade",
            () -> new SmithingTemplateItem(
                    Component.translatable("item.mega_showdown.furfrou_trim_smithing_template.applies_to"),       // Applies To
                    Component.translatable("item.mega_showdown.furfrou_trim_smithing_template.ingredients"),       // Ingredients
                    Component.translatable("item.mega_showdown.furfrou_trim_smithing_template.upgrade"),           // Description Title
                    Component.translatable("item.mega_showdown.furfrou_trim_smithing_template.base_slot"),         // Base Slot Text
                    Component.translatable("item.mega_showdown.furfrou_trim_smithing_template.additions_slot"),    // Addition Slot Text
                    List.of(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "item/shears")),
                    List.of(ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "item/dye"))
            )
    );
    public static final DeferredItem<Item> MELTAN = ITEMS.register("meltan",
            () -> new Item(new Item.Properties().stacksTo(75)));
    //TRIMS
    private static final List<String> furfrouAspects = List.of(
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
    public static final DeferredItem<Item> HEART_TRIM = ITEMS.register("heart_trim",
            () -> new Item(new Item.Properties().stacksTo(1).durability(100)) {
                @Override
                public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
                    if (user.level().isClientSide || user.isCrouching()) {
                        return InteractionResult.PASS;
                    }

                    if (entity instanceof PokemonEntity pk && pk.getPokemon().getSpecies().getName().equals("Furfrou") && !pk.isBattling()
                            && pk.getAspects().stream().noneMatch(furfrouAspects::contains)) {
                        new StringSpeciesFeature("poodle_trim", "heart").apply(pk.getPokemon());
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
            });
    public static final DeferredItem<Item> STAR_TRIM = ITEMS.register("star_trim",
            () -> new Item(new Item.Properties().stacksTo(1).durability(100)) {
                @Override
                public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
                    if (user.level().isClientSide || user.isCrouching()) {
                        return InteractionResult.PASS;
                    }

                    if (entity instanceof PokemonEntity pk && pk.getPokemon().getSpecies().getName().equals("Furfrou") && !pk.isBattling()
                            && pk.getAspects().stream().noneMatch(furfrouAspects::contains)) {
                        new StringSpeciesFeature("poodle_trim", "star").apply(pk.getPokemon());
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
            });
    public static final DeferredItem<Item> DIAMOND_TRIM = ITEMS.register("diamond_trim",
            () -> new Item(new Item.Properties().stacksTo(1).durability(100)) {
                @Override
                public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
                    if (user.level().isClientSide || user.isCrouching()) {
                        return InteractionResult.PASS;
                    }

                    if (entity instanceof PokemonEntity pk && pk.getPokemon().getSpecies().getName().equals("Furfrou") && !pk.isBattling()
                            && pk.getAspects().stream().noneMatch(furfrouAspects::contains)) {
                        new StringSpeciesFeature("poodle_trim", "diamond").apply(pk.getPokemon());
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
            });
    public static final DeferredItem<Item> DEBUTANTE_TRIM = ITEMS.register("debutante_trim",
            () -> new Item(new Item.Properties().stacksTo(1).durability(100)) {
                @Override
                public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
                    if (user.level().isClientSide || user.isCrouching()) {
                        return InteractionResult.PASS;
                    }

                    if (entity instanceof PokemonEntity pk && pk.getPokemon().getSpecies().getName().equals("Furfrou") && !pk.isBattling()
                            && pk.getAspects().stream().noneMatch(furfrouAspects::contains)) {
                        new StringSpeciesFeature("poodle_trim", "debutante").apply(pk.getPokemon());
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
            });
    public static final DeferredItem<Item> MATRON_TRIM = ITEMS.register("matron_trim",
            () -> new Item(new Item.Properties().stacksTo(1).durability(100)) {
                @Override
                public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
                    if (user.level().isClientSide || user.isCrouching()) {
                        return InteractionResult.PASS;
                    }

                    if (entity instanceof PokemonEntity pk && pk.getPokemon().getSpecies().getName().equals("Furfrou") && !pk.isBattling()
                            && pk.getAspects().stream().noneMatch(furfrouAspects::contains)) {
                        new StringSpeciesFeature("poodle_trim", "matron").apply(pk.getPokemon());
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
            });
    public static final DeferredItem<Item> DANDY_TRIM = ITEMS.register("dandy_trim",
            () -> new Item(new Item.Properties().stacksTo(1).durability(100)) {
                @Override
                public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
                    if (user.level().isClientSide || user.isCrouching()) {
                        return InteractionResult.PASS;
                    }

                    if (entity instanceof PokemonEntity pk && pk.getPokemon().getSpecies().getName().equals("Furfrou") && !pk.isBattling()
                            && pk.getAspects().stream().noneMatch(furfrouAspects::contains)) {
                        new StringSpeciesFeature("poodle_trim", "dandy").apply(pk.getPokemon());
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
            });
    public static final DeferredItem<Item> LA_REINE_TRIM = ITEMS.register("la_reine_trim",
            () -> new Item(new Item.Properties().stacksTo(1).durability(100)) {
                @Override
                public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
                    if (user.level().isClientSide || user.isCrouching()) {
                        return InteractionResult.PASS;
                    }

                    if (entity instanceof PokemonEntity pk && pk.getPokemon().getSpecies().getName().equals("Furfrou") && !pk.isBattling()
                            && pk.getAspects().stream().noneMatch(furfrouAspects::contains)) {
                        new StringSpeciesFeature("poodle_trim", "la_reine").apply(pk.getPokemon());
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
            });
    public static final DeferredItem<Item> KABUKI_TRIM = ITEMS.register("kabuki_trim",
            () -> new Item(new Item.Properties().stacksTo(1).durability(100)) {
                @Override
                public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
                    if (user.level().isClientSide || user.isCrouching()) {
                        return InteractionResult.PASS;
                    }

                    if (entity instanceof PokemonEntity pk && pk.getPokemon().getSpecies().getName().equals("Furfrou") && !pk.isBattling()
                            && pk.getAspects().stream().noneMatch(furfrouAspects::contains)) {
                        new StringSpeciesFeature("poodle_trim", "kabuki").apply(pk.getPokemon());
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
            });
    public static final DeferredItem<Item> PHARAOH_TRIM = ITEMS.register("pharaoh_trim",
            () -> new Item(new Item.Properties().stacksTo(1).durability(100)) {
                @Override
                public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
                    if (user.level().isClientSide || user.isCrouching()) {
                        return InteractionResult.PASS;
                    }

                    if (entity instanceof PokemonEntity pk && pk.getPokemon().getSpecies().getName().equals("Furfrou") && !pk.isBattling()
                            && pk.getAspects().stream().noneMatch(furfrouAspects::contains)) {
                        new StringSpeciesFeature("poodle_trim", "pharaoh").apply(pk.getPokemon());
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
            });

    public static void register() {

    }

    private static void playFormeChangeAnimation(LivingEntity context) {
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.AMETHYST_BLOCK_CHIME, // Change this if needed
                    SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (100 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverLevel.sendParticles(
                        ParticleTypes.END_ROD, // Change this to any particle type
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
