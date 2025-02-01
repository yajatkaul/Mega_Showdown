package com.cobblemon.yajatkaul.megamons;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = MegaShowdown.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue MULTIPLE_MEGAS = BUILDER
            .comment("Enable multiple megas at one time")
            .define("multipleMegas", false);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean multipleMegas;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        multipleMegas = MULTIPLE_MEGAS.get();
    }
}