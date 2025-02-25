package com.cobblemon.yajatkaul.mega_showdown;

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

    private static final ModConfigSpec.BooleanValue BATTLE_MODE_ONLY = BUILDER
            .comment("Enable mega evolution only for battles")
            .define("battleModeOnly", false);

    private static final ModConfigSpec.BooleanValue MEGA_TAKES_TURN = BUILDER
            .comment("Makes it so that evolving during battle consumes one turn (Only works if battleMode is enabled)")
            .define("megaTurns", false);

    private static final ModConfigSpec.BooleanValue BRACELET_HAND_SENSITIVE = BUILDER
            .comment("Makes it so you can devolve and evolve the pokemon with mega bracelet in the same hand")
            .define("braceletHandSensitive", false);

    private static final ModConfigSpec.BooleanValue BATTLE_MODE = BUILDER
            .comment("Allows you to have outside megas but they devolve on battle and then you can have battle mode style theme")
            .define("battleMode", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean multipleMegas;
    public static boolean battleModeOnly;
    public static boolean megaTurns;
    public static boolean braceletHandSensitive;
    public static boolean battleMode;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        multipleMegas = MULTIPLE_MEGAS.get();
        battleModeOnly = BATTLE_MODE_ONLY.get();
        battleMode = BATTLE_MODE.get();
        megaTurns = MEGA_TAKES_TURN.get();
        braceletHandSensitive = BRACELET_HAND_SENSITIVE.get();
    }
}