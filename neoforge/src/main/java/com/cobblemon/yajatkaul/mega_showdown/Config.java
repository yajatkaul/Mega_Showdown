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

    private static final ModConfigSpec.BooleanValue BATTLE_MODE = BUILDER
            .comment("Allows you to have outside megas but they devolve on battle and then you can have battle mode style theme")
            .define("battleMode", true);

    private static final ModConfigSpec.BooleanValue MULTIPLE_PRIMALS = BUILDER
            .comment("Allows you to have multiple primals in your team")
            .define("multiplePrimals", true);

    private static final ModConfigSpec.BooleanValue SCUFFED_MODE = BUILDER
            .comment("Allows you to have both the mega btn and allows u to carry your mega's into the battle, battleMode should be false for this")
            .define("scuffedMode", false);

    private static final ModConfigSpec.BooleanValue FRIENDSHIP_MODE = BUILDER
            .comment("Makes it so that you need to have 200+ friendship in order to mega outside")
            .define("friendshipMode", false);

    private static final ModConfigSpec.BooleanValue Z_MOVES = BUILDER
            .comment("Enables/Disables zMoves in game")
            .define("zMoves", true);

    private static final ModConfigSpec.BooleanValue TERA_EVO = BUILDER
            .comment("Enables/Disables teralization in game")
            .define("teralization", true);

    private static final ModConfigSpec.BooleanValue TRADE_FORM = BUILDER
            .comment("Allows you to trade even if your pokemon is not in base form")
            .define("tradeForm", false);

    private static final ModConfigSpec.BooleanValue ETERMAX_FORME = BUILDER
            .comment("Enables etermax eternus forme")
            .define("etermaxForme", true);

    private static final ModConfigSpec.BooleanValue DYNAMAX = BUILDER
            .comment("Enables/Disables dmax in game")
            .define("Dynamax", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean multipleMegas;
    public static boolean battleModeOnly;
    public static boolean battleMode;
    public static boolean multiplePrimals;
    public static boolean scuffedMode;
    public static boolean friendshipMode;
    public static boolean zMoves;
    public static boolean teralization;
    public static boolean tradeForm;
    public static boolean etermaxForme;
    public static boolean dynamax;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        multipleMegas = MULTIPLE_MEGAS.get();
        battleModeOnly = BATTLE_MODE_ONLY.get();
        battleMode = BATTLE_MODE.get();
        multiplePrimals = MULTIPLE_PRIMALS.get();
        scuffedMode = SCUFFED_MODE.get();
        friendshipMode = FRIENDSHIP_MODE.get();
        zMoves = Z_MOVES.get();
        teralization = TERA_EVO.get();
        tradeForm = TRADE_FORM.get();
        etermaxForme = ETERMAX_FORME.get();
        dynamax = DYNAMAX.get();
    }
}