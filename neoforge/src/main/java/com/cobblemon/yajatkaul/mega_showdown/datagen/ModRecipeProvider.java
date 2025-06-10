package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.MegaOres;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.item.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RotomFormes.FANUNIT.get())
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, RotomFormes.WASHINGUNIT.get())
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
                .define('F', RotomFormes.FANUNIT)
                .define('W', RotomFormes.WASHINGUNIT)
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DynamaxItems.DYNAMAX_BAND.get())
                .pattern("PPP")
                .pattern("IWI")
                .pattern("BBB")
                .define('P', CobblemonItems.PINK_APRICORN)
                .define('I', Items.IRON_INGOT)
                .define('W', DynamaxItems.WISHING_STAR)
                .define('B', CobblemonItems.BLUE_APRICORN)
                .unlockedBy("has_wishing_star", has(DynamaxItems.WISHING_STAR)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DynamaxItems.MAX_SOUP.get())
                .pattern("MMM")
                .pattern(" B ")
                .pattern("   ")
                .define('M', DynamaxItems.MAX_MUSHROOM)
                .define('B', Items.BOWL)
                .unlockedBy("has_max_mushroom", has(DynamaxItems.MAX_MUSHROOM)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DynamaxItems.SWEET_MAX_SOUP.get())
                .requires(DynamaxItems.MAX_HONEY.get())
                .requires(DynamaxItems.MAX_SOUP.get())
                .unlockedBy("has_max_soup", has(DynamaxItems.MAX_SOUP)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.POWER_SPOT.get())
                .pattern("RMR")
                .pattern("RWR")
                .pattern("SSS")
                .define('R', Items.REDSTONE)
                .define('W', DynamaxItems.WISHING_STAR)
                .define('S', Items.STONE)
                .define('M', DynamaxItems.MAX_MUSHROOM)
                .unlockedBy("has_wishing_star", has(DynamaxItems.WISHING_STAR)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, DynamaxItems.DYNAMAX_CANDY.get())
                .pattern(" E ")
                .pattern("EME")
                .pattern(" E ")
                .define('E', CobblemonItems.EXPERIENCE_CANDY_S)
                .define('M', DynamaxItems.MAX_MUSHROOM)
                .unlockedBy("has_max_mushroom", has(DynamaxItems.MAX_MUSHROOM)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DynamaxItems.MAX_HONEY.get())
                .requires(Items.HONEY_BOTTLE)
                .requires(DynamaxItems.MAX_MUSHROOM.get())
                .unlockedBy("has_max_mushroom", has(DynamaxItems.MAX_MUSHROOM)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZCrystals.Z_RING.get())
                .pattern("ISI")
                .pattern("A A")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('S', ZCrystals.SPARKLING_STONE_LIGHT)
                .define('A', CobblemonItems.WHITE_APRICORN)
                .unlockedBy("has_light_sparkling_stone", has(ZCrystals.SPARKLING_STONE_LIGHT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZCrystals.Z_RING_BLACK.get())
                .pattern("ISI")
                .pattern("A A")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('S', ZCrystals.SPARKLING_STONE_LIGHT)
                .define('A', CobblemonItems.BLACK_APRICORN)
                .unlockedBy("has_light_sparkling_stone", has(ZCrystals.SPARKLING_STONE_LIGHT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZCrystals.Z_RING_YELLOW.get())
                .pattern("ISI")
                .pattern("A A")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('S', ZCrystals.SPARKLING_STONE_LIGHT)
                .define('A', CobblemonItems.YELLOW_APRICORN)
                .unlockedBy("has_light_sparkling_stone", has(ZCrystals.SPARKLING_STONE_LIGHT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZCrystals.Z_RING_RED.get())
                .pattern("ISI")
                .pattern("A A")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('S', ZCrystals.SPARKLING_STONE_LIGHT)
                .define('A', CobblemonItems.RED_APRICORN)
                .unlockedBy("has_light_sparkling_stone", has(ZCrystals.SPARKLING_STONE_LIGHT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZCrystals.OLIVIAS_Z_RING.get())
                .pattern("PSP")
                .pattern("CAC")
                .pattern("CIC")
                .define('P', CobblemonItems.PINK_APRICORN)
                .define('I', Items.IRON_NUGGET)
                .define('C', Items.COPPER_INGOT)
                .define('S', ZCrystals.SPARKLING_STONE_LIGHT)
                .define('A', Items.AMETHYST_SHARD)
                .unlockedBy("has_light_sparkling_stone", has(ZCrystals.SPARKLING_STONE_LIGHT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZCrystals.HAPUS_Z_RING.get())
                .pattern("GSG")
                .pattern("LYL")
                .pattern("LLL")
                .define('G', Items.GOLD_INGOT)
                .define('L', Items.LEATHER)
                .define('Y', CobblemonItems.YELLOW_APRICORN)
                .define('S', ZCrystals.SPARKLING_STONE_LIGHT)
                .unlockedBy("has_light_sparkling_stone", has(ZCrystals.SPARKLING_STONE_LIGHT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZCrystals.Z_RING_PINK.get())
                .pattern("ISI")
                .pattern("A A")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('S', ZCrystals.SPARKLING_STONE_LIGHT)
                .define('A', CobblemonItems.PINK_APRICORN)
                .unlockedBy("has_light_sparkling_stone", has(ZCrystals.SPARKLING_STONE_LIGHT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZCrystals.Z_RING_BLUE.get())
                .pattern("ISI")
                .pattern("A A")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('S', ZCrystals.SPARKLING_STONE_LIGHT)
                .define('A', CobblemonItems.BLUE_APRICORN)
                .unlockedBy("has_light_sparkling_stone", has(ZCrystals.SPARKLING_STONE_LIGHT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZCrystals.Z_RING_GREEN.get())
                .pattern("ISI")
                .pattern("A A")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('S', ZCrystals.SPARKLING_STONE_LIGHT)
                .define('A', CobblemonItems.GREEN_APRICORN)
                .unlockedBy("has_light_sparkling_stone", has(ZCrystals.SPARKLING_STONE_LIGHT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZCrystals.Z_RING_POWER.get())
                .pattern("ISI")
                .pattern("A A")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('S', ZCrystals.SPARKLING_STONE_DARK)
                .define('A', CobblemonItems.BLACK_APRICORN)
                .unlockedBy("has_light_sparkling_stone", has(ZCrystals.SPARKLING_STONE_DARK)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.SHOCK_DRIVE.get())
                .pattern("IEI")
                .pattern("RNR")
                .pattern("IRI")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('N', Items.NETHERITE_SCRAP)
                .define('E', CobblemonItems.ELECTRIC_GEM)
                .unlockedBy("has_netherite_scrap", has(Items.NETHERITE_SCRAP)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.BURN_DRIVE.get())
                .pattern("IFI")
                .pattern("RNR")
                .pattern("IRI")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('N', Items.NETHERITE_SCRAP)
                .define('F', CobblemonItems.FIRE_GEM)
                .unlockedBy("has_netherite_scrap", has(Items.NETHERITE_SCRAP)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.CHILL_DRIVE.get())
                .pattern("ICI")
                .pattern("RNR")
                .pattern("IRI")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('N', Items.NETHERITE_SCRAP)
                .define('C', CobblemonItems.ICE_GEM)
                .unlockedBy("has_netherite_scrap", has(Items.NETHERITE_SCRAP)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.DOUSE_DRIVE.get())
                .pattern("IWI")
                .pattern("RNR")
                .pattern("IRI")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('N', Items.NETHERITE_SCRAP)
                .define('W', CobblemonItems.WATER_GEM)
                .unlockedBy("has_netherite_scrap", has(Items.NETHERITE_SCRAP)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.ZYGARDE_CUBE.get())
                .pattern("BGB")
                .pattern("GNG")
                .pattern("BGB")
                .define('G', CobblemonItems.GREEN_APRICORN)
                .define('B', CobblemonItems.BLACK_APRICORN)
                .define('N', Items.NETHERITE_INGOT)
                .unlockedBy("has_netherite", has(Items.NETHERITE_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.STAR_CORE.get())
                .pattern("PSD")
                .pattern("SWS")
                .pattern("PSD")
                .define('P', CobblemonItems.POISON_GEM)
                .define('D', CobblemonItems.DRAGON_GEM)
                .define('W', DynamaxItems.WISHING_STAR)
                .define('S', Items.OBSIDIAN)
                .unlockedBy("has_wishing_star", has(DynamaxItems.WISHING_STAR)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PEDESTAL.get())
                .pattern(" S ")
                .pattern(" B ")
                .pattern("   ")
                .define('S', Items.SMOOTH_STONE_SLAB)
                .define('B', Items.CHISELED_STONE_BRICKS)
                .unlockedBy("has_chiseled_stone_bricks", has(Items.CHISELED_STONE_BRICKS)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.REASSEMBLY_UNIT.get())
                .pattern("RRR")
                .pattern("RTR")
                .pattern("III")
                .define('R', Items.REDSTONE)
                .define('T', CobblemonItems.RESTORATION_TANK)
                .define('I', Items.IRON_BLOCK)
                .unlockedBy("has_restoration_tank", has(CobblemonItems.RESTORATION_TANK)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LISIA_MEGA_TIARA.get())
                .pattern("L L")
                .pattern("IKI")
                .pattern("L L")
                .define('K', MegaStones.KEYSTONE)
                .define('L', Items.LAPIS_LAZULI)
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_keystone", has(MegaStones.KEYSTONE)).save(recipeOutput);

        // Heart Trim
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE), // template
                        Ingredient.of(Items.SHEARS),              // base item
                        Ingredient.of(Items.PINK_DYE),            // addition (pink matches heart trim's pink fur)
                        RecipeCategory.MISC,
                        FormeChangeItems.HEART_TRIM.get()         // result
                ).unlocks("has_furfrou_trim", has(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "furfrou_trim_heart"));

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE), // template
                        Ingredient.of(Items.SHEARS),              // base item
                        Ingredient.of(Items.LIGHT_BLUE_DYE),
                        RecipeCategory.MISC,
                        FormeChangeItems.STAR_TRIM.get()          // result
                ).unlocks("has_furfrou_trim", has(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "furfrou_trim_star"));

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE), // template
                        Ingredient.of(Items.SHEARS),              // base item
                        Ingredient.of(Items.ORANGE_DYE),            // addition (blue matches diamond trim's blue fur)
                        RecipeCategory.MISC,
                        FormeChangeItems.DIAMOND_TRIM.get()       // result
                ).unlocks("has_furfrou_trim", has(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "furfrou_trim_diamond"));

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE), // template
                        Ingredient.of(Items.SHEARS),              // base item
                        Ingredient.of(Items.CYAN_DYE),      // addition (light blue matches la reine's light blue fur)
                        RecipeCategory.MISC,
                        FormeChangeItems.LA_REINE_TRIM.get()      // result
                ).unlocks("has_furfrou_trim", has(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "furfrou_trim_la_reine"));

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE), // template
                        Ingredient.of(Items.SHEARS),              // base item
                        Ingredient.of(Items.RED_DYE),           // addition (white matches kabuki's primarily white fur)
                        RecipeCategory.MISC,
                        FormeChangeItems.KABUKI_TRIM.get()        // result
                ).unlocks("has_furfrou_trim", has(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "furfrou_trim_kabuki"));

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE), // template
                        Ingredient.of(Items.SHEARS),              // base item
                        Ingredient.of(Items.BLUE_DYE),            // addition (blue matches pharaoh's blue fur)
                        RecipeCategory.MISC,
                        FormeChangeItems.PHARAOH_TRIM.get()       // result
                ).unlocks("has_furfrou_trim", has(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "furfrou_trim_pharaoh"));

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE), // template
                        Ingredient.of(Items.SHEARS),              // base item
                        Ingredient.of(Items.MAGENTA_DYE),           // addition (green matches matron's dark green fur)
                        RecipeCategory.MISC,
                        FormeChangeItems.MATRON_TRIM.get()        // result
                ).unlocks("has_furfrou_trim", has(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "furfrou_trim_matron"));

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE), // template
                        Ingredient.of(Items.SHEARS),              // base item
                        Ingredient.of(Items.LIME_DYE),            // addition (lime matches dandy's light green fur)
                        RecipeCategory.MISC,
                        FormeChangeItems.DANDY_TRIM.get()         // result
                ).unlocks("has_furfrou_trim", has(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "furfrou_trim_dandy"));

        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE), // template
                        Ingredient.of(Items.SHEARS),              // base item
                        Ingredient.of(Items.YELLOW_DYE),          // addition (orange matches debutante's orange fur)
                        RecipeCategory.MISC,
                        FormeChangeItems.DEBUTANTE_TRIM.get()     // result
                ).unlocks("has_furfrou_trim", has(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "furfrou_trim_debutante"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE.get(), 2)
                .pattern("DTD")
                .pattern("DWD")
                .pattern("DDD")
                .define('T', FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE)
                .define('D', Items.DIAMOND)
                .define('W', Items.WHITE_WOOL)
                .unlockedBy("has_furfrou_trim", has(FormeChangeItems.FURFROU_TRIM_SMITHING_TEMPLATE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, KeyItems.LIKOS_PENDANT.get())
                .pattern("S S")
                .pattern(" D ")
                .pattern("   ")
                .define('D', ModBlocks.DORMANT_CRYSTAL)
                .define('S', Items.STRING)
                .unlockedBy("has_dormant_crystal", has(ModBlocks.DORMANT_CRYSTAL)).save(recipeOutput);
    }

}
