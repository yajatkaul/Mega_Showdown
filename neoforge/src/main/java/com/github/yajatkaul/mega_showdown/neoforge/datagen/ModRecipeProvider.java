package com.github.yajatkaul.mega_showdown.neoforge.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(arg, completableFuture);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FormeChangeItems.PIKA_CASE.get())
//                .pattern("YYY")
//                .pattern("IEI")
//                .pattern("III")
//                .define('Y', Items.YELLOW_DYE)
//                .define('I', Items.IRON_INGOT)
//                .define('E', CobblemonItems.ELECTRIC_GEM)
//                .unlockedBy("has_electric_gem", has(CobblemonItems.ELECTRIC_GEM)).save(recipeOutput);
    }
}