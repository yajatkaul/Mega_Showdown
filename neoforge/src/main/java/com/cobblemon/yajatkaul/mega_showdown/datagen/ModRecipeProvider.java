package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

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

    }
}
