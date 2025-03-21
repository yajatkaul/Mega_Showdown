package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.*;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion.Unity;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion.DNA_Splicer;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion.N_Lunarizer;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion.N_Solarizer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;


public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MegaShowdown.MOD_ID);

    public static final DeferredItem<Item> MEGA_BRACELET = ITEMS.register("megabracelet",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_RED_BRACELET = ITEMS.register("megabracelet_red",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_YELLOW_BRACELET = ITEMS.register("megabracelet_yellow",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_PINK_BRACELET = ITEMS.register("megabracelet_pink",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_GREEN_BRACELET = ITEMS.register("megabracelet_green",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_BLUE_BRACELET = ITEMS.register("megabracelet_blue",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_BLACK_BRACELET = ITEMS.register("megabracelet_black",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MAY_BRACELET = ITEMS.register("may_bracelet",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MEGA_RING = ITEMS.register("megaring",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> LYSANDRE_RING = ITEMS.register("lysandre_ring",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> BRENDAN_MEGA_CUFF = ITEMS.register("brendan_mega_cuff",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> KORRINA_GLOVE = ITEMS.register("korrina_glove",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> MAXIE_GLASSES = ITEMS.register("maxie_glasses",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> ARCHIE_ANCHOR = ITEMS.register("archie_anchor",
            () -> new MegaBraceletItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> N_LUNARIZER = ITEMS.register("n_lunarizer",
            () -> new N_Lunarizer(new Item.Properties().stacksTo(1).component(DataManage.N_LUNAR, null)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.n_lunarizer.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> N_SOLARIZER = ITEMS.register("n_solarizer",
            () -> new N_Solarizer(new Item.Properties().stacksTo(1).component(DataManage.N_SOLAR, null)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.n_solarizer.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> DNA_SPLICER = ITEMS.register("dna_splicer",
            () -> new DNA_Splicer(new Item.Properties().stacksTo(1).component(DataManage.KYUREM_DATA, null)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.dna_splicer.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> RUSTED_SWORD = ITEMS.register("rusted_sword",
            () -> new Item(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.rusted_sword.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> RUSTED_SHIELD = ITEMS.register("rusted_shield",
            () -> new Item(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.rusted_shield.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> PRISON_BOTTLE = ITEMS.register("prison_bottle",
            () -> new Unbound(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.prison_bottle.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> REINS_OF_UNITY = ITEMS.register("reins_of_unity",
            () -> new Unity(new Item.Properties().stacksTo(1).component(DataManage.CALYREX_DATA, null)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.reins_of_unity.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> GRACIDEA_FLOWER = ITEMS.register("gracidea_flower",
            () -> new Gracidea(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.gracidea_flower.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> SCROLL_OF_DARKNESS = ITEMS.register("scroll_of_darkness",
            () -> new Item(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.scroll_of_darkness.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> SCROLL_OF_WATERS = ITEMS.register("scroll_of_waters",
            () -> new Item(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.scroll_of_waters.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> CORNERSTONE_MASK = ITEMS.register("cornerstone_mask",
            () -> new Item(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.cornerstone_mask.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> WELLSPRING_MASK = ITEMS.register("wellspring_mask",
            () -> new Item(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.wellspring_mask.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> HEARTHFLAME_MASK = ITEMS.register("hearthflame_mask",
            () -> new Item(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.hearthflame_mask.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> STAR_CORE = ITEMS.register("star_core",
            () -> new Item(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.star_core.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> GRISEOUS_ORB = ITEMS.register("griseous_orb",
            () -> new Item(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.griseous_orb.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ASH_CAP = ITEMS.register("ash_cap",
            () -> new Cap(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.ash_cap.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ADAMANT_ORB = ITEMS.register("adamant_orb",
            () -> new Item(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.adamant_orb.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> LUSTROUS_GLOBE = ITEMS.register("lustrous_globe",
            () -> new Item(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.lustrous_globe.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> FLAME_PLATE = ITEMS.register("flameplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.flameplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> SPLASH_PLATE = ITEMS.register("splashplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.splashplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ZAP_PLATE = ITEMS.register("zapplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.zapplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> MEADOW_PLATE = ITEMS.register("meadowplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.meadowplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ICICLE_PLATE = ITEMS.register("icicleplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.icicleplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> FIST_PLATE = ITEMS.register("fistplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.fistplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> TOXIC_PLATE = ITEMS.register("toxicplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.toxicplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> EARTH_PLATE = ITEMS.register("earthplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.earthplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> SKY_PLATE = ITEMS.register("skyplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.skyplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> MIND_PLATE = ITEMS.register("mindplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.mindplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> INSECT_PLATE = ITEMS.register("insectplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.insectplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> STONE_PLATE = ITEMS.register("stoneplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.stoneplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> SPOOKY_PLATE = ITEMS.register("spookyplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.spookyplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> DRACO_PLATE = ITEMS.register("dracoplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.dracoplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> DREAD_PLATE = ITEMS.register("dreadplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.dreadplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> IRON_PLATE = ITEMS.register("ironplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.ironplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> PIXIE_PLATE = ITEMS.register("pixieplate",
            () -> new ArceusPlates(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.pixieplate.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> BUG_MEMORY = ITEMS.register("bugmemory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.bugmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> DARK_MEMORY = ITEMS.register("darkmemory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.darkmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> DRAGON_MEMORY = ITEMS.register("dragonmemory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.dragonmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ELECTRIC_MEMORY = ITEMS.register("electricmemory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.electricmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> FAIRY_MEMORY = ITEMS.register("fairymemory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.fairymemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> FIGHTING_MEMORY = ITEMS.register("fightingmemory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.fightingmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> FIRE_MEMORY = ITEMS.register("firememory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.firememory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> FLYING_MEMORY = ITEMS.register("flyingmemory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.flyingmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> GHOST_MEMORY = ITEMS.register("ghostmemory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.ghostmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> GRASS_MEMORY = ITEMS.register("grassmemory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.grassmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> GROUND_MEMORY = ITEMS.register("groundmemory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.groundmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ICE_MEMORY = ITEMS.register("icememory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.icememory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> POISON_MEMORY = ITEMS.register("poisonmemory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.poisonmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> PSYCHIC_MEMORY = ITEMS.register("psychicmemory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.psychicmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> ROCK_MEMORY = ITEMS.register("rockmemory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.rockmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> STEEL_MEMORY = ITEMS.register("steelmemory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.steelmemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> WATER_MEMORY = ITEMS.register("watermemory",
            () -> new Memories(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.watermemory.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> BURN_DRIVE = ITEMS.register("burndrive",
            () -> new Drives(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.burndrive.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> CHILL_DRIVE = ITEMS.register("chilldrive",
            () -> new Drives(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.chilldrive.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> DOUSE_DRIVE = ITEMS.register("dousedrive",
            () -> new Drives(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.dousedrive.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> SHOCK_DRIVE = ITEMS.register("shockdrive",
            () -> new Drives(new Item.Properties().stacksTo(1)){
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag){
                    tooltipComponents.add(Component.translatable("tooltip.mega_showdown.shockdrive.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
