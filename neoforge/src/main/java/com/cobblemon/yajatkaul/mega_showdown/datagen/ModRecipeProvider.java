package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.item.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(arg, completableFuture);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MEGA_BRACELET.get())
                .pattern("IDI")
                .pattern("AKA")
                .pattern("III")
                .define('K', MegaStones.KEYSTONE)
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.WHITE_APRICORN)
                .unlockedBy("has_keystone", has(MegaStones.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MEGA_RED_BRACELET.get())
                .pattern("IDI")
                .pattern("AKA")
                .pattern("III")
                .define('K', MegaStones.KEYSTONE)
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.RED_APRICORN)
                .unlockedBy("has_keystone", has(MegaStones.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MEGA_BLACK_BRACELET.get())
                .pattern("IDI")
                .pattern("AKA")
                .pattern("III")
                .define('K', MegaStones.KEYSTONE)
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.BLACK_APRICORN)
                .unlockedBy("has_keystone", has(MegaStones.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MEGA_BLUE_BRACELET.get())
                .pattern("IDI")
                .pattern("AKA")
                .pattern("III")
                .define('K', MegaStones.KEYSTONE)
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.BLUE_APRICORN)
                .unlockedBy("has_keystone", has(MegaStones.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MEGA_GREEN_BRACELET.get())
                .pattern("IDI")
                .pattern("AKA")
                .pattern("III")
                .define('K', MegaStones.KEYSTONE)
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.GREEN_APRICORN)
                .unlockedBy("has_keystone", has(MegaStones.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MEGA_PINK_BRACELET.get())
                .pattern("IDI")
                .pattern("AKA")
                .pattern("III")
                .define('K', MegaStones.KEYSTONE)
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.PINK_APRICORN)
                .unlockedBy("has_keystone", has(MegaStones.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MEGA_YELLOW_BRACELET.get())
                .pattern("IDI")
                .pattern("AKA")
                .pattern("III")
                .define('K', MegaStones.KEYSTONE)
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.YELLOW_APRICORN)
                .unlockedBy("has_keystone", has(MegaStones.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.KEYSTONE_BLOCK.get())
                .pattern("KKK")
                .pattern("KKK")
                .pattern("KKK")
                .define('K', MegaStones.KEYSTONE)
                .unlockedBy("has_keystone", has(MegaStones.KEYSTONE)).save(recipeOutput);

        SingleItemRecipeBuilder.stonecutting(
                        Ingredient.of(ModBlocks.MEGA_EVO_BLOCK), // Input item (change this as needed)
                        RecipeCategory.BUILDING_BLOCKS, // Recipe category
                        ModBlocks.MEGA_EVO_BRICK.get() // Output block
                ).unlockedBy("has_mega_evo_block", has(ModBlocks.MEGA_EVO_BLOCK)) // Unlock condition
                .save(recipeOutput);

        SingleItemRecipeBuilder.stonecutting(
                        Ingredient.of(ModBlocks.MEGA_EVO_BLOCK), // Input item (change this as needed)
                        RecipeCategory.BUILDING_BLOCKS, // Recipe category
                        ModBlocks.CHISELED_MEGA_EVO_BLOCK.get() // Output block
                ).unlockedBy("has_mega_evo_block", has(ModBlocks.MEGA_EVO_BLOCK)) // Unlock condition
                .save(recipeOutput);

        SingleItemRecipeBuilder.stonecutting(
                        Ingredient.of(ModBlocks.MEGA_EVO_BLOCK), // Input item (change this as needed)
                        RecipeCategory.BUILDING_BLOCKS, // Recipe category
                        ModBlocks.CHISELED_MEGA_EVO_BRICK.get() // Output block
                ).unlockedBy("has_mega_evo_block", has(ModBlocks.MEGA_EVO_BLOCK)) // Unlock condition
                .save(recipeOutput);


        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(ModBlocks.MEGA_EVO_BLOCK), // Input item
                        RecipeCategory.BUILDING_BLOCKS, // Recipe category
                        ModBlocks.POLISHED_MEGA_EVO_BLOCK.get(), // Output item
                        0.1f, // Experience gained
                        200 // Cooking time in ticks (10 seconds)
                ).unlockedBy("has_mega_evo_block", has(ModBlocks.MEGA_EVO_BLOCK))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MegaStones.KEYSTONE, 9)
                .requires(ModBlocks.KEYSTONE_BLOCK.get())
                .unlockedBy("has_keystone_block", has(ModBlocks.KEYSTONE_BLOCK.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MAXIE_GLASSES.get())
                .pattern("   ")
                .pattern("DKD")
                .pattern("IGI")
                .define('K', MegaStones.KEYSTONE)
                .define('D', Items.DIAMOND)
                .define('I', Items.IRON_INGOT)
                .define('G', CobblemonItems.WISE_GLASSES)
                .unlockedBy("has_keystone", has(MegaStones.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ARCHIE_ANCHOR.get())
                .pattern("GCG")
                .pattern("GKG")
                .pattern("DGD")
                .define('K', MegaStones.KEYSTONE)
                .define('D', Items.DIAMOND)
                .define('G', Items.GOLD_INGOT)
                .define('C', Items.CHAIN)
                .unlockedBy("has_keystone", has(MegaStones.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRENDAN_MEGA_CUFF.get())
                .pattern(" N ")
                .pattern("AKA")
                .pattern("ADA")
                .define('K', MegaStones.KEYSTONE)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.RED_APRICORN)
                .define('N', Items.NETHERITE_INGOT)
                .unlockedBy("has_keystone", has(MegaStones.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LYSANDRE_RING.get())
                .pattern("IDI")
                .pattern("IKI")
                .pattern("III")
                .define('K', MegaStones.KEYSTONE)
                .define('D', Items.DIAMOND)
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_keystone", has(MegaStones.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.KORRINA_GLOVE.get())
                .pattern(" D ")
                .pattern("IKI")
                .pattern("AAA")
                .define('K', MegaStones.KEYSTONE)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.RED_APRICORN)
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_keystone", has(MegaStones.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MEGA_RING.get())
                .pattern("ADA")
                .pattern("AKA")
                .pattern("IAI")
                .define('K', MegaStones.KEYSTONE)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.BLACK_APRICORN)
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_keystone", has(MegaStones.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.N_LUNARIZER.get())
                .pattern("IWI")
                .pattern("ISI")
                .pattern("IRI")
                .define('I', Items.IRON_INGOT)
                .define('W', Items.LIGHT_BLUE_WOOL)
                .define('S', CobblemonItems.MOON_STONE)
                .define('R', Items.REDSTONE)
                .unlockedBy("has_moonstone", has(CobblemonItems.MOON_STONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.N_SOLARIZER.get())
                .pattern("IWI")
                .pattern("ISI")
                .pattern("IRI")
                .define('I', Items.IRON_INGOT)
                .define('W', Items.ORANGE_WOOL)
                .define('S', CobblemonItems.SUN_STONE)
                .define('R', Items.REDSTONE)
                .unlockedBy("has_sunstone", has(CobblemonItems.SUN_STONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TeraMoves.TERA_ORB.get())
                .pattern("SES")
                .pattern("GDG")
                .pattern("SBS")
                .define('S', Items.AMETHYST_SHARD)
                .define('G', Items.GLOWSTONE_DUST)
                .define('B', Items.BLAZE_POWDER)
                .define('D', Items.DIAMOND)
                .define('E', Items.ENDER_PEARL)
                .unlockedBy("has_amethyst", has(Items.AMETHYST_SHARD)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.DNA_SPLICER.get())
                .pattern(" BN")
                .pattern("BIB")
                .pattern("IB ")
                .define('B', Items.BLAZE_ROD)
                .define('I', Items.IRON_INGOT)
                .define('N', Items.NETHERITE_INGOT)
                .unlockedBy("has_netherite", has(Items.NETHERITE_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZCrystals.Z_RING.get())
                .pattern("FLF")
                .pattern("WRW")
                .pattern("FDF")
                .define('F', Items.QUARTZ)
                .define('L', Items.GLASS_PANE)
                .define('W', CobblemonItems.WHITE_APRICORN)
                .define('R', Items.DIAMOND)
                .define('D', Items.IRON_INGOT)

                .unlockedBy("has_diamond", has(Items.DIAMOND)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZCrystals.Z_RING_BLACK.get())
                .pattern("FLF")
                .pattern("BRB")
                .pattern("FDF")
                .define('F', Items.QUARTZ)
                .define('L', Items.GLASS_PANE)
                .define('B', CobblemonItems.BLACK_APRICORN)
                .define('R', Items.DIAMOND)
                .define('D', Items.IRON_INGOT)

                .unlockedBy("has_diamond", has(Items.DIAMOND)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZCrystals.Z_RING_POWER.get())
                .pattern("FLF")
                .pattern("BNB")
                .pattern("FDF")
                .define('F', Items.QUARTZ)
                .define('L', Items.GLASS_PANE)
                .define('B', CobblemonItems.BLACK_APRICORN)
                .define('N', Items.NETHERITE_INGOT)
                .define('D', Items.IRON_INGOT)
                .unlockedBy("has_netherite", has(Items.NETHERITE_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MAY_BRACELET.get())
                .pattern("YSY")
                .pattern("IKI")
                .pattern("YPY")
                .define('S', Items.NAUTILUS_SHELL)
                .define('P', CobblemonItems.PINK_APRICORN)
                .define('I', Items.IRON_INGOT)
                .define('K', MegaStones.KEYSTONE)
                .define('Y', CobblemonItems.YELLOW_APRICORN)
                .unlockedBy("has_keystone", has(MegaStones.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.REINS_OF_UNITY.get())
                .pattern("SGS")
                .pattern("L  ")
                .pattern("SIS")
                .define('L', Items.LAPIS_LAZULI)
                .define('S', Items.STRING)
                .define('I', CobblemonItems.ICE_GEM)
                .define('G', CobblemonItems.GHOST_GEM)
                .unlockedBy("has_gem", has(CobblemonItems.GHOST_GEM)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RotomFormes.FAN.get())
                .pattern(" C ")
                .pattern("CWC")
                .pattern(" B ")
                .define('C', Items.COPPER_INGOT)
                .define('W', Items.WIND_CHARGE)
                .define('B', Items.COPPER_BLOCK)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RotomFormes.FRIDGEUNIT.get())
                .pattern("BBB")
                .pattern("DID")
                .pattern("BRB")
                .define('D', Items.IRON_DOOR)
                .define('R', Items.REDSTONE)
                .define('I', Items.ICE)
                .define('B', Items.COPPER_BLOCK)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RotomFormes.MOWUNIT.get())
                .pattern(" C ")
                .pattern("BMB")
                .pattern("RRR")
                .define('C', Items.COPPER_INGOT)
                .define('R', Items.REDSTONE)
                .define('M', Items.MINECART)
                .define('B', Items.COPPER_BLOCK)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RotomFormes.OVENUNIT.get())
                .pattern("BBB")
                .pattern("DTD")
                .pattern("BRB")
                .define('T', Items.IRON_TRAPDOOR)
                .define('R', Items.REDSTONE)
                .define('D', Items.BLAZE_POWDER)
                .define('B', Items.COPPER_BLOCK)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RotomFormes.WASHUNIT.get())
                .pattern("BBB")
                .pattern("BCB")
                .pattern("BRB")
                .define('C', Items.CAULDRON)
                .define('R', Items.REDSTONE)
                .define('B', Items.COPPER_BLOCK)
                .unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RotomFormes.ROTOM_CATALOGUE.get())
                .pattern(" F ")
                .pattern("OBW")
                .pattern(" M ")
                .define('F', RotomFormes.FAN)
                .define('W', RotomFormes.WASHUNIT)
                .define('M', RotomFormes.MOWUNIT)
                .define('O', RotomFormes.OVENUNIT)
                .define('B', Items.BOOK)
                .unlockedBy("has_book", has(Items.BOOK)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.PINK_NECTAR.get())
                .pattern("   ")
                .pattern("HPH")
                .pattern(" H ")
                .define('H', Items.HONEYCOMB)
                .define('P', Items.PEONY)
                .unlockedBy("has_honey", has(Items.HONEYCOMB)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.RED_NECTAR.get())
                .pattern("   ")
                .pattern("HRH")
                .pattern(" H ")
                .define('H', Items.HONEYCOMB)
                .define('R', Items.ROSE_BUSH)
                .unlockedBy("has_honey", has(Items.HONEYCOMB)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.YELLOW_NECTAR.get())
                .pattern("   ")
                .pattern("HYH")
                .pattern(" H ")
                .define('H', Items.HONEYCOMB)
                .define('Y', Items.SUNFLOWER)
                .unlockedBy("has_honey", has(Items.HONEYCOMB)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.PURPLE_NECTAR.get())
                .pattern("   ")
                .pattern("HPH")
                .pattern(" H ")
                .define('H', Items.HONEYCOMB)
                .define('P', Items.LILAC)
                .unlockedBy("has_honey", has(Items.HONEYCOMB)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.ASH_CAP.get())
                .pattern(" WR")
                .pattern("RGR")
                .pattern("RWR")
                .define('W', Items.WHITE_WOOL)
                .define('R', Items.RED_WOOL)
                .define('G', Items.GREEN_WOOL)
                .unlockedBy("has_wool", has(Items.WHITE_WOOL)).save(recipeOutput);

// Water
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(MegaOres.MEGA_METEORID_WATER_ORE), RecipeCategory.MISC, CobblemonItems.WATER_STONE, 0.7f, 100)
                .unlockedBy("has_mega_water_ore", has(MegaOres.MEGA_METEORID_WATER_ORE)).save(recipeOutput, "water_stone_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(MegaOres.MEGA_METEORID_WATER_ORE), RecipeCategory.MISC, CobblemonItems.WATER_STONE, 0.7f, 200)
                .unlockedBy("has_mega_water_ore", has(MegaOres.MEGA_METEORID_WATER_ORE)).save(recipeOutput, "water_stone_smelting");

// Dawn
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(MegaOres.MEGA_METEORID_DAWN_ORE), RecipeCategory.MISC, CobblemonItems.DAWN_STONE, 0.7f, 100)
                .unlockedBy("has_mega_dawn_ore", has(MegaOres.MEGA_METEORID_DAWN_ORE)).save(recipeOutput, "dawn_stone_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(MegaOres.MEGA_METEORID_DAWN_ORE), RecipeCategory.MISC, CobblemonItems.DAWN_STONE, 0.7f, 200)
                .unlockedBy("has_mega_dawn_ore", has(MegaOres.MEGA_METEORID_DAWN_ORE)).save(recipeOutput, "dawn_stone_smelting");

// Dusk
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(MegaOres.MEGA_METEORID_DUSK_ORE), RecipeCategory.MISC, CobblemonItems.DUSK_STONE, 0.7f, 100)
                .unlockedBy("has_mega_dusk_ore", has(MegaOres.MEGA_METEORID_DUSK_ORE)).save(recipeOutput, "dusk_stone_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(MegaOres.MEGA_METEORID_DUSK_ORE), RecipeCategory.MISC, CobblemonItems.DUSK_STONE, 0.7f, 200)
                .unlockedBy("has_mega_dusk_ore", has(MegaOres.MEGA_METEORID_DUSK_ORE)).save(recipeOutput, "dusk_stone_smelting");

// Fire
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(MegaOres.MEGA_METEORID_FIRE_ORE), RecipeCategory.MISC, CobblemonItems.FIRE_STONE, 0.7f, 100)
                .unlockedBy("has_mega_fire_ore", has(MegaOres.MEGA_METEORID_FIRE_ORE)).save(recipeOutput, "fire_stone_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(MegaOres.MEGA_METEORID_FIRE_ORE), RecipeCategory.MISC, CobblemonItems.FIRE_STONE, 0.7f, 200)
                .unlockedBy("has_mega_fire_ore", has(MegaOres.MEGA_METEORID_FIRE_ORE)).save(recipeOutput, "fire_stone_smelting");

// Ice
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(MegaOres.MEGA_METEORID_ICE_ORE), RecipeCategory.MISC, CobblemonItems.ICE_STONE, 0.7f, 100)
                .unlockedBy("has_mega_ice_ore", has(MegaOres.MEGA_METEORID_ICE_ORE)).save(recipeOutput, "ice_stone_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(MegaOres.MEGA_METEORID_ICE_ORE), RecipeCategory.MISC, CobblemonItems.ICE_STONE, 0.7f, 200)
                .unlockedBy("has_mega_ice_ore", has(MegaOres.MEGA_METEORID_ICE_ORE)).save(recipeOutput, "ice_stone_smelting");

// Leaf
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(MegaOres.MEGA_METEORID_LEAF_ORE), RecipeCategory.MISC, CobblemonItems.LEAF_STONE, 0.7f, 100)
                .unlockedBy("has_mega_leaf_ore", has(MegaOres.MEGA_METEORID_LEAF_ORE)).save(recipeOutput, "leaf_stone_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(MegaOres.MEGA_METEORID_LEAF_ORE), RecipeCategory.MISC, CobblemonItems.LEAF_STONE, 0.7f, 200)
                .unlockedBy("has_mega_leaf_ore", has(MegaOres.MEGA_METEORID_LEAF_ORE)).save(recipeOutput, "leaf_stone_smelting");

// Moon
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(MegaOres.MEGA_METEORID_MOON_ORE), RecipeCategory.MISC, CobblemonItems.MOON_STONE, 0.7f, 100)
                .unlockedBy("has_mega_moon_ore", has(MegaOres.MEGA_METEORID_MOON_ORE)).save(recipeOutput, "moon_stone_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(MegaOres.MEGA_METEORID_MOON_ORE), RecipeCategory.MISC, CobblemonItems.MOON_STONE, 0.7f, 200)
                .unlockedBy("has_mega_moon_ore", has(MegaOres.MEGA_METEORID_MOON_ORE)).save(recipeOutput, "moon_stone_smelting");

// Shiny
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(MegaOres.MEGA_METEORID_SHINY_ORE), RecipeCategory.MISC, CobblemonItems.SHINY_STONE, 0.7f, 100)
                .unlockedBy("has_mega_shiny_ore", has(MegaOres.MEGA_METEORID_SHINY_ORE)).save(recipeOutput, "shiny_stone_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(MegaOres.MEGA_METEORID_SHINY_ORE), RecipeCategory.MISC, CobblemonItems.SHINY_STONE, 0.7f, 200)
                .unlockedBy("has_mega_shiny_ore", has(MegaOres.MEGA_METEORID_SHINY_ORE)).save(recipeOutput, "shiny_stone_smelting");

// Sun
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(MegaOres.MEGA_METEORID_SUN_ORE), RecipeCategory.MISC, CobblemonItems.SUN_STONE, 0.7f, 100)
                .unlockedBy("has_mega_sun_ore", has(MegaOres.MEGA_METEORID_SUN_ORE)).save(recipeOutput, "sun_stone_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(MegaOres.MEGA_METEORID_SUN_ORE), RecipeCategory.MISC, CobblemonItems.SUN_STONE, 0.7f, 200)
                .unlockedBy("has_mega_sun_ore", has(MegaOres.MEGA_METEORID_SUN_ORE)).save(recipeOutput, "sun_stone_smelting");

// Thunder
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(MegaOres.MEGA_METEORID_THUNDER_ORE), RecipeCategory.MISC, CobblemonItems.THUNDER_STONE, 0.7f, 100)
                .unlockedBy("has_mega_thunder_ore", has(MegaOres.MEGA_METEORID_THUNDER_ORE)).save(recipeOutput, "thunder_stone_blasting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(MegaOres.MEGA_METEORID_THUNDER_ORE), RecipeCategory.MISC, CobblemonItems.THUNDER_STONE, 0.7f, 200)
                .unlockedBy("has_mega_thunder_ore", has(MegaOres.MEGA_METEORID_THUNDER_ORE)).save(recipeOutput, "thunder_stone_smelting");
    }
}
