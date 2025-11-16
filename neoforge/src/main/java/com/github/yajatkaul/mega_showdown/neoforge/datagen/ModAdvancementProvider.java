package com.github.yajatkaul.mega_showdown.neoforge.datagen;

import com.cobblemon.mod.common.CobblemonItemComponents;
import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.advancement.CobblemonCriteria;
import com.cobblemon.mod.common.advancement.criterion.PokemonCriterion;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.item.components.PokemonItemComponent;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.lang.reflect.Array;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class ModAdvancementProvider implements AdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existing) {
        AdvancementHolder ROOT = Advancement.Builder.advancement()
                .display(
                        // ICON TODO change
                        Items.ACACIA_BOAT,
                        // TITLE
                        Component.translatable("advancement.mega_showdown.root.title"),
                        // DESCRIPTION
                        Component.translatable("advancement.mega_showdown.root.description"),
                        // BACKGROUND TEXTURE
                        ResourceLocation.fromNamespaceAndPath("mega_showdown", "textures/block/mega_evo_brick.png"),
                        // FRAME TYPE
                        AdvancementType.TASK,
                        // showToast, announceToChat, hidden
                        true,
                        true,
                        false
                )
                // CRITERIA
                .addCriterion("root",    CobblemonCriteria.PICK_STARTER.createCriterion(
                                new PokemonCriterion(Optional.empty(), "any", new PokemonProperties())
                        )
                )
                // SAVE
                .save(saver, ResourceLocation.fromNamespaceAndPath("mega_showdown", "root"), existing);

        AdvancementHolder ASH_CAP_BOND = Advancement.Builder.advancement()
                .display(
                        MegaShowdownItems.ASH_CAP.get(),
                        Component.translatable("advancement.mega_showdown.ash_cap_bond.title"),
                        Component.translatable("advancement.mega_showdown.ash_cap_bond.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .parent(ROOT)
                .addCriterion("bond_any",
                        CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance())
                )
                .save(saver, ResourceLocation.fromNamespaceAndPath("mega_showdown", "ash_cap_bond"), existing);

        AdvancementHolder ASH_BATTLE_BOND = Advancement.Builder.advancement()
                .display(
                        // ICON TODO change
                        Items.ACACIA_BOAT,
                        Component.translatable("advancement.mega_showdown.ash_battle_bond.title"),
                        Component.translatable("advancement.mega_showdown.ash_battle_bond.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .parent(ASH_CAP_BOND)
                .addCriterion("battle_bond",
                        CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance())
                )
                .save(saver, ResourceLocation.fromNamespaceAndPath("mega_showdown", "ash_battle_bond"), existing);

        ItemStack pokemon = CobblemonItems.POKEMON_MODEL.getDefaultInstance();
        pokemon.set(CobblemonItemComponents.POKEMON_ITEM, new PokemonItemComponent(ResourceLocation.fromNamespaceAndPath("cobblemon", "greninja"), Set.of("ash"), null));

        AdvancementHolder ASH_GRENINJA = Advancement.Builder.advancement()
                .display(
                        pokemon,
                        Component.translatable("advancement.mega_showdown.ash_greninja.title"),
                        Component.translatable("advancement.mega_showdown.ash_greninja.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .parent(ASH_BATTLE_BOND)
                .addCriterion("ash_greninja",
                        CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance())
                )
                .save(saver, ResourceLocation.fromNamespaceAndPath("mega_showdown", "ash_greninja"), existing);

        AdvancementHolder ASH_PIKACHU = Advancement.Builder.advancement()
                .display(
                        MegaShowdownItems.PIKASHUNIUM_Z.get(),
                        Component.translatable("advancement.mega_showdown.ash_pikachu.title"),
                        Component.translatable("advancement.mega_showdown.ash_pikachu.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .parent(ASH_BATTLE_BOND)
                .addCriterion("ash_pikachu",
                        CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance())
                )
                .save(saver, ResourceLocation.fromNamespaceAndPath("mega_showdown", "ash_pikachu"), existing);

        AdvancementHolder GET_DYNAMAX_BAND = Advancement.Builder.advancement()
                .display(
                        //TODO change icon
                        MegaShowdownItems.DYNAMAX_BAND.get(),
                        Component.translatable("advancement.mega_showdown.get_dynamax_band.title"),
                        Component.translatable("advancement.mega_showdown.get_dynamax_band.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .parent(ASH_BATTLE_BOND)
                .addCriterion("dynamax_any",
                        CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance())
                )
                .save(saver, ResourceLocation.fromNamespaceAndPath("mega_showdown", "dynamax"), existing);

        ResourceKey<Structure> WEALD_KEY = ResourceKey.create(
                Registries.STRUCTURE,
                ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "wishing_weald")
        );
        HolderLookup.RegistryLookup<Structure> structureRegistry =
                registries.lookupOrThrow(Registries.STRUCTURE);
        Holder.Reference<Structure> wealdHolder =
                structureRegistry.getOrThrow(WEALD_KEY);

        AdvancementHolder FIND_DYNAMAX_STRUCT = Advancement.Builder.advancement()
                .display(
                        MegaShowdownItems.PIKASHUNIUM_Z.get(),
                        Component.translatable("advancement.mega_showdown.find_wishing_star.title"),
                        Component.translatable("advancement.mega_showdown.find_wishing_star.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .parent(ROOT)
                .addCriterion("in_dynamax_structure",
                        PlayerTrigger.TriggerInstance.located(
                                LocationPredicate.Builder.inStructure(wealdHolder)
                        )
                )
                .save(saver,
                        ResourceLocation.fromNamespaceAndPath("mega_showdown", "find_dynamax_structure"),
                        existing);

        AdvancementHolder FIND_WISHING_STAR = Advancement.Builder.advancement()
                .display(
                        //TODO change icon
                        MegaShowdownItems.PIKASHUNIUM_Z.get(),
                        Component.translatable("advancement.mega_showdown.find_wishing_star.title"),
                        Component.translatable("advancement.mega_showdown.find_wishing_star.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .parent(FIND_DYNAMAX_STRUCT)
                .addCriterion("dynamax_any",
                        CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance())
                )
                .save(saver, ResourceLocation.fromNamespaceAndPath("mega_showdown", "find_wishing_star"), existing);

        AdvancementHolder DYNAMAX = Advancement.Builder.advancement()
                .display(
                        //TODO change icon
                        MegaShowdownItems.PIKASHUNIUM_Z.get(),
                        Component.translatable("advancement.mega_showdown.dynamax.title"),
                        Component.translatable("advancement.mega_showdown.dynamax.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .parent(FIND_WISHING_STAR)
                .addCriterion("dynamax_any",
                        CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance())
                )
                .save(saver, ResourceLocation.fromNamespaceAndPath("mega_showdown", "dynamax"), existing);
    }
}