package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.block.ModBlocks;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.RotomCatalogue;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.RotomUnit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.ITEMS;

public class RotomFormes {

    public static final DeferredItem<BlockItem> FANUNIT = ITEMS.register("rotom_fan",
            () -> new RotomUnit(ModBlocks.ROTOM_FAN.get(), new Item.Properties(), "fan"));
    public static final DeferredItem<BlockItem> FRIDGEUNIT = ITEMS.register("rotom_fridge",
            () -> new RotomUnit(ModBlocks.ROTOM_FRIDGE.get(), new Item.Properties(), "frost"));
    public static final DeferredItem<BlockItem> MOWUNIT = ITEMS.register("rotom_mow",
            () -> new RotomUnit(ModBlocks.ROTOM_MOW.get(), new Item.Properties(), "mow"));
    public static final DeferredItem<BlockItem> OVENUNIT = ITEMS.register("rotom_oven",
            () -> new RotomUnit(ModBlocks.ROTOM_OVEN.get(), new Item.Properties(), "heat"));
    public static final DeferredItem<BlockItem> WASHINGUNIT = ITEMS.register("rotom_washing_machine",
            () -> new RotomUnit(ModBlocks.ROTOM_WASHING_MACHINE.get(), new Item.Properties(), "wash"));

    public static final DeferredItem<Item> ROTOM_CATALOGUE = ITEMS.register("rotom_catalogue",
            () -> new RotomCatalogue(new Item.Properties().component(DataManage.CATALOGUE_PAGE, 1)));

    public static void register() {
    }
}
