package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
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
                .define('K', ModItems.KEYSTONE)
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.WHITE_APRICORN)
                .unlockedBy("has_keystone", has(ModItems.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MEGA_RED_BRACELET.get())
                .pattern("IDI")
                .pattern("AKA")
                .pattern("III")
                .define('K', ModItems.KEYSTONE)
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.RED_APRICORN)
                .unlockedBy("has_keystone", has(ModItems.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MEGA_BLACK_BRACELET.get())
                .pattern("IDI")
                .pattern("AKA")
                .pattern("III")
                .define('K', ModItems.KEYSTONE)
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.BLACK_APRICORN)
                .unlockedBy("has_keystone", has(ModItems.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MEGA_BLUE_BRACELET.get())
                .pattern("IDI")
                .pattern("AKA")
                .pattern("III")
                .define('K', ModItems.KEYSTONE)
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.BLUE_APRICORN)
                .unlockedBy("has_keystone", has(ModItems.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MEGA_GREEN_BRACELET.get())
                .pattern("IDI")
                .pattern("AKA")
                .pattern("III")
                .define('K', ModItems.KEYSTONE)
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.GREEN_APRICORN)
                .unlockedBy("has_keystone", has(ModItems.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MEGA_PINK_BRACELET.get())
                .pattern("IDI")
                .pattern("AKA")
                .pattern("III")
                .define('K', ModItems.KEYSTONE)
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.PINK_APRICORN)
                .unlockedBy("has_keystone", has(ModItems.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MEGA_YELLOW_BRACELET.get())
                .pattern("IDI")
                .pattern("AKA")
                .pattern("III")
                .define('K', ModItems.KEYSTONE)
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.YELLOW_APRICORN)
                .unlockedBy("has_keystone", has(ModItems.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.KEYSTONE_BLOCK.get())
                .pattern("KKK")
                .pattern("KKK")
                .pattern("KKK")
                .define('K', ModItems.KEYSTONE)
                .unlockedBy("has_keystone", has(ModItems.KEYSTONE)).save(recipeOutput);

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

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.KEYSTONE, 9)
                .requires(ModBlocks.KEYSTONE_BLOCK.get())
                .unlockedBy("has_keystone_block", has(ModBlocks.KEYSTONE_BLOCK.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MAXIE_GLASSES.get())
                .pattern("   ")
                .pattern("DKD")
                .pattern("IGI")
                .define('K', ModItems.KEYSTONE)
                .define('D', Items.DIAMOND)
                .define('I', Items.IRON_INGOT)
                .define('G', CobblemonItems.WISE_GLASSES)
                .unlockedBy("has_keystone", has(ModItems.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ARCHIE_ANCHOR.get())
                .pattern("GCG")
                .pattern("GKG")
                .pattern("DGD")
                .define('K', ModItems.KEYSTONE)
                .define('D', Items.DIAMOND)
                .define('G', Items.GOLD_INGOT)
                .define('C', Items.CHAIN)
                .unlockedBy("has_keystone", has(ModItems.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRENDAN_MEGA_CUFF.get())
                .pattern(" N ")
                .pattern("AKA")
                .pattern("ADA")
                .define('K', ModItems.KEYSTONE)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.RED_APRICORN)
                .define('N', Items.NETHERITE_INGOT)
                .unlockedBy("has_keystone", has(ModItems.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.LYSANDRE_RING.get())
                .pattern("IDI")
                .pattern("IKI")
                .pattern("III")
                .define('K', ModItems.KEYSTONE)
                .define('D', Items.DIAMOND)
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_keystone", has(ModItems.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.KORRINA_GLOVE.get())
                .pattern(" D ")
                .pattern("IKI")
                .pattern("AAA")
                .define('K', ModItems.KEYSTONE)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.RED_APRICORN)
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_keystone", has(ModItems.KEYSTONE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MEGA_RING.get())
                .pattern("ADA")
                .pattern("AKA")
                .pattern("IAI")
                .define('K', ModItems.KEYSTONE)
                .define('D', Items.DIAMOND)
                .define('A', CobblemonItems.BLACK_APRICORN)
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_keystone", has(ModItems.KEYSTONE)).save(recipeOutput);
    }
}
