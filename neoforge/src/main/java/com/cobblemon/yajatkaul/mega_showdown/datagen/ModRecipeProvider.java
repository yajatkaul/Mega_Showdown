package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.item.TeraMoves;
import com.cobblemon.yajatkaul.mega_showdown.item.ZMoves;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.crafting.StonecutterRecipe;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(arg, completableFuture);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput){
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.N_LUNARIZER.get())
                .pattern("IWI")
                .pattern("ISI")
                .pattern("IRI")
                .define('I', Items.IRON_INGOT)
                .define('W', Items.LIGHT_BLUE_WOOL)
                .define('S', CobblemonItems.MOON_STONE)
                .define('R', Items.REDSTONE)
                .unlockedBy("has_moonstone", has(CobblemonItems.MOON_STONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.N_SOLARIZER.get())
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

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DNA_SPLICER.get())
                .pattern(" BN")
                .pattern("BIB")
                .pattern("IB ")
                .define('B', Items.BLAZE_ROD)
                .define('I', Items.IRON_INGOT)
                .define('N', Items.NETHERITE_INGOT)
                .unlockedBy("has_netherite", has(Items.NETHERITE_INGOT)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZMoves.Z_RING.get())
                .pattern("FLF")
                .pattern("WRW")
                .pattern("FDF")
                .define('F', Items.QUARTZ)
                .define('L', Items.GLASS_PANE)
                .define('W', CobblemonItems.WHITE_APRICORN)
                .define('R', Items.DIAMOND)
                .define('D', Items.IRON_INGOT)

                .unlockedBy("has_diamond", has(Items.DIAMOND)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZMoves.Z_RING_BLACK.get())
                .pattern("FLF")
                .pattern("BRB")
                .pattern("FDF")
                .define('F', Items.QUARTZ)
                .define('L', Items.GLASS_PANE)
                .define('B', CobblemonItems.BLACK_APRICORN)
                .define('R', Items.DIAMOND)
                .define('D', Items.IRON_INGOT)

                .unlockedBy("has_diamond", has(Items.DIAMOND)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ZMoves.Z_RING_POWER.get())
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
    }
}
