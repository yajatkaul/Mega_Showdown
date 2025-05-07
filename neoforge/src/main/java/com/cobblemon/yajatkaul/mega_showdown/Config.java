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

    private static final ModConfigSpec.BooleanValue MULTIPLE_PRIMALS = BUILDER
            .comment("Allows you to have multiple primals in your team")
            .define("multiplePrimals", true);

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
            .define("dynamax", true);

    private static final ModConfigSpec.BooleanValue MEGA = BUILDER
            .comment("Enables/Disables dmax in game")
            .define("mega", true);

    private static final ModConfigSpec.BooleanValue DYNAMAX_ANYWHERE = BUILDER
            .comment("Allows you to dynamax anywhere you dont need to be near the dynamax area")
            .define("dynamaxAnywhere", false);

    private static final ModConfigSpec.IntValue POWER_SPOT_RANGE = BUILDER
            .comment("Range around a power spot where Dynamax is allowed")
            .defineInRange("powerSpotRange", 20, 0, 10000);

    private static final ModConfigSpec.IntValue DMAX_SCALE_FACTOR = BUILDER
            .comment("By how many times should the pokemon size increase when g/dmaxing")
            .defineInRange("dynamaxScaleFactor", 4, 0, 10000);

    private static final ModConfigSpec.BooleanValue SHOWDOWN_FILES_LOADING = BUILDER
            .comment("""
                     Enable/Disable loading of showdown files from the mod,\s
                     note this means once you load the game and the showdown changes have been affected you disable
                     this it will stop overwriting it ever load\
                    , this is for people who want to edit showdown\s
                     but can't since mega showdown keeps overriding the files""")
            .define("showdownFilesLoading", true);

    private static final ModConfigSpec.BooleanValue DISABLE_TERASHARD_DROP = BUILDER
            .comment("Disables pokemons from dropping tera shards")
            .define("disableTeraShardDrop", false);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean multipleMegas;
    public static boolean battleModeOnly;
    public static boolean multiplePrimals;
    public static boolean friendshipMode;
    public static boolean zMoves;
    public static boolean disableTeraShardDrop;
    public static boolean teralization;
    public static boolean tradeForm;
    public static boolean etermaxForme;
    public static boolean dynamax;
    public static boolean mega;
    public static boolean dynamaxAnywhere;
    public static int powerSpotRange;
    public static int dynamaxScaleFactor;
    public static boolean showdownFilesLoading;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        multipleMegas = MULTIPLE_MEGAS.get();
        battleModeOnly = BATTLE_MODE_ONLY.get();
        multiplePrimals = MULTIPLE_PRIMALS.get();
        friendshipMode = FRIENDSHIP_MODE.get();
        zMoves = Z_MOVES.get();
        teralization = TERA_EVO.get();
        disableTeraShardDrop = DISABLE_TERASHARD_DROP.get();
        tradeForm = TRADE_FORM.get();
        etermaxForme = ETERMAX_FORME.get();
        dynamax = DYNAMAX.get();
        dynamaxAnywhere = DYNAMAX_ANYWHERE.get();
        powerSpotRange = POWER_SPOT_RANGE.get();
        dynamaxScaleFactor = DMAX_SCALE_FACTOR.get();
        showdownFilesLoading = SHOWDOWN_FILES_LOADING.get();
        mega = MEGA.get();
    }
}