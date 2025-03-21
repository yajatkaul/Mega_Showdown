package com.cobblemon.yajatkaul.mega_showdown.item;


import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.*;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion.DNA_Splicer;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion.N_Lunarizer;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion.N_Solarizer;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion.Unity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModItems {
    public static final Item KEYSTONE = registerItem("keystone", new Item(new Item.Settings()) {
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.keystone.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MEGA_BRACELET = registerItem("megabracelet", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MEGA_RED_BRACELET = registerItem("megabracelet_red", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MEGA_YELLOW_BRACELET = registerItem("megabracelet_yellow", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MEGA_PINK_BRACELET = registerItem("megabracelet_pink", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MEGA_GREEN_BRACELET = registerItem("megabracelet_green", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MEGA_BLUE_BRACELET = registerItem("megabracelet_blue", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MEGA_BLACK_BRACELET = registerItem("megabracelet_black", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MEGA_RING = registerItem("megaring", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item BRENDAN_MEGA_CUFF = registerItem("brendan_mega_cuff", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item LYSANDRE_RING = registerItem("lysandre_ring", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item KORRINA_GLOVE = registerItem("korrina_glove", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MAXIE_GLASSES = registerItem("maxie_glasses", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item ARCHIE_ANCHOR = registerItem("archie_anchor", new MegaBraceletItem(new Item.Settings().maxCount(1)));
    public static final Item MAY_BRACELET = registerItem("may_bracelet", new MegaBraceletItem(new Item.Settings().maxCount(1)));

    public static final Item N_LUNARIZER = registerItem("n_lunarizer", new N_Lunarizer(new Item.Settings().maxCount(1).component(DataManage.N_LUNAR, null)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.n_lunarizer.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });
    public static final Item N_SOLARIZER = registerItem("n_solarizer", new N_Solarizer(new Item.Settings().maxCount(1).component(DataManage.N_SOLAR, null)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.n_solarizer.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });
    public static final Item DNA_SPLICER = registerItem("dna_splicer", new DNA_Splicer(new Item.Settings().maxCount(1).component(DataManage.KYUREM_DATA, null)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.dna_splicer.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item RUSTED_SWORD = registerItem("rusted_sword", new Item(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.rusted_sword.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item RUSTED_SHIELD = registerItem("rusted_shield", new Item(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.rusted_shield.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item PRISON_BOTTLE = registerItem("prison_bottle", new Unbound(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.prison_bottle.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item REINS_OF_UNITY = registerItem("reins_of_unity", new Unity(new Item.Settings().maxCount(1).component(DataManage.CALYREX_DATA, null)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.reins_of_unity.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GRACIDEA_FLOWER = registerItem("gracidea_flower", new Gracidea(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.gracidea_flower.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SCROLL_OF_DARKNESS = registerItem("scroll_of_darkness", new Gracidea(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.scroll_of_darkness.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SCROLL_OF_WATERS = registerItem("scroll_of_waters", new Gracidea(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.scroll_of_waters.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item CORNERSTONE_MASK = registerItem("cornerstone_mask", new Gracidea(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.cornerstone_mask.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });


    public static final Item WELLSPRING_MASK = registerItem("wellspring_mask", new Gracidea(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.wellspring_mask.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item HEARTHFLAME_MASK = registerItem("hearthflame_mask", new Gracidea(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.hearthflame_mask.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item STAR_CORE = registerItem("star_core", new Gracidea(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.star_core.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GRISEOUS_ORB = registerItem("griseous_orb", new Item(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.griseous_orb.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ASH_CAP = registerItem("ash_cap", new Cap(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.ash_cap.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MEGA_STONE_CRYSTAL_ITEM = Registry.register(Registries.ITEM,
            Identifier.of(MegaShowdown.MOD_ID, "mega_stone_crystal"),
            new BlockItem(MegaOres.MEGA_STONE_CRYSTAL, new Item.Settings()));

    public static final Item ADAMANT_ORB = registerItem("adamant_orb", new Item(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.adamant_orb.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item LUSTROUS_GLOBE = registerItem("lustrous_globe", new Item(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.lustrous_globe.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item FLAME_PLATE = registerItem("flameplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.flameplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SPLASH_PLATE = registerItem("splashplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.splashplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ZAP_PLATE = registerItem("zapplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.zapplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MEADOW_PLATE = registerItem("meadowplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.meadowplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ICICLE_PLATE = registerItem("icicleplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.icicleplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item FIST_PLATE = registerItem("fistplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.fistplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item TOXIC_PLATE = registerItem("toxicplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.toxicplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item EARTH_PLATE = registerItem("earthplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.earthplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SKY_PLATE = registerItem("skyplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.skyplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item MIND_PLATE = registerItem("mindplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.mindplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item INSECT_PLATE = registerItem("insectplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.insectplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item STONE_PLATE = registerItem("stoneplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.stoneplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SPOOKY_PLATE = registerItem("spookyplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.spookyplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item DRACO_PLATE = registerItem("dracoplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.dracoplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item DREAD_PLATE = registerItem("dreadplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.dreadplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item IRON_PLATE = registerItem("ironplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.ironplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item PIXIE_PLATE = registerItem("pixieplate", new ArceusPlates(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.pixieplate.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item BUG_MEMORY = registerItem("bugmemory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.bugmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item DARK_MEMORY = registerItem("darkmemory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.darkmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item DRAGON_MEMORY = registerItem("dragonmemory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.dragonmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ELECTRIC_MEMORY = registerItem("electricmemory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.electricmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item FAIRY_MEMORY = registerItem("fairymemory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.fairymemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item FIGHTING_MEMORY = registerItem("fightingmemory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.fightingmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item FIRE_MEMORY = registerItem("firememory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.firememory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item FLYING_MEMORY = registerItem("flyingmemory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.flyingmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GHOST_MEMORY = registerItem("ghostmemory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.ghostmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GRASS_MEMORY = registerItem("grassmemory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.grassmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item GROUND_MEMORY = registerItem("groundmemory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.groundmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ICE_MEMORY = registerItem("icememory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.icememory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item POISON_MEMORY = registerItem("poisonmemory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.poisonmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item PSYCHIC_MEMORY = registerItem("psychicmemory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.psychicmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item ROCK_MEMORY = registerItem("rockmemory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.rockmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item STEEL_MEMORY = registerItem("steelmemory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.steelmemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item WATER_MEMORY = registerItem("watermemory", new Memories(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.watermemory.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item BURN_DRIVE = registerItem("burndrive", new Drives(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.burndrive.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item CHILL_DRIVE = registerItem("chilldrive", new Drives(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.chilldrive.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item DOUSE_DRIVE = registerItem("dousedrive", new Drives(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.dousedrive.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static final Item SHOCK_DRIVE = registerItem("shockdrive", new Drives(new Item.Settings().maxCount(1)){
        @Override
        public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
            tooltip.add(Text.translatable("tooltip.mega_showdown.shockdrive.tooltip"));
            super.appendTooltip(stack, context, tooltip, type);
        }
    });

    public static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(MegaShowdown.MOD_ID, name), item);
    }

    public static void registerModItem(){

    }


}
