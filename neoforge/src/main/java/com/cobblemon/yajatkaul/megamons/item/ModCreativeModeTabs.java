package com.cobblemon.yajatkaul.megamons.item;

import com.cobblemon.yajatkaul.megamons.MegaShowdown;
import com.cobblemon.yajatkaul.megamons.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MegaShowdown.MOD_ID);

    public static final Supplier<CreativeModeTab> MEGAMONS_TAB = CREATIVE_MODE_TAB.register("megamons_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CHARIZARDITE_X.get()))
                    .title(Component.translatable("creativeTab.megamons.megamons_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.CHARIZARDITE_X);
                        output.accept(ModItems.ABOMASNOW);
                        output.accept(ModItems.MEGA_BRACELET);

                        //Blocks
                        output.accept(ModBlocks.CHARIZARDITE_X_ORE);
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
