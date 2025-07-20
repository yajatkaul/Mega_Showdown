package com.cobblemon.yajatkaul.mega_showdown.config;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = MegaShowdown.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class MegaShowdownConfig {
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
    private static final ModConfigSpec.BooleanValue Z_MOVES = BUILDER
            .comment("Enables/Disables zMoves in game")
            .define("zMoves", true);
    private static final ModConfigSpec.BooleanValue TERA_EVO = BUILDER
            .comment("Enables/Disables teralization in game")
            .define("teralization", true);
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
            .defineInRange("dynamaxScaleFactor", 4, -10, 10000);
    private static final ModConfigSpec.BooleanValue SHOWDOWN_FILES_LOADING = BUILDER
            .comment("""
                     Enable/Disable loading of showdown files from the mod,\s
                     note this means once you load the game and the showdown changes have been affected you disable
                     this it will stop overwriting it ever load\
                    , this is for people who want to edit showdown\s
                     but can't since mega showdown keeps overriding the files""")
            .define("showdownFilesLoading", true);
    private static final ModConfigSpec.BooleanValue REVERT_MEGAS = BUILDER
            .comment("Enable/Disable mega pokemons form reverting when battle starts")
            .define("revertMegas", true);
    private static final ModConfigSpec.IntValue TERA_SHARDS_REQUIRED = BUILDER
            .comment("Number of tera shards required to change tera type")
            .defineInRange("teraShardRequired", 50, 1, 50);
    private static final ModConfigSpec.DoubleValue TERA_SHARD_DROPRATE = BUILDER
            .comment("Terashard drop rate")
            .defineInRange("teraShardDropRate", 10.0, -1, 100);
    private static final ModConfigSpec.DoubleValue STELLAR_SHARD_DROPRATE = BUILDER
            .comment("Stellar tera drop rate")
            .defineInRange("stellarShardDropRate", 1.0, -1, 100);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean multipleMegas;
    public static boolean battleModeOnly;
    public static boolean multiplePrimals;
    public static boolean zMoves;
    public static boolean teralization;
    public static boolean etermaxForme;
    public static boolean dynamax;
    public static boolean mega;
    public static boolean dynamaxAnywhere;
    public static int powerSpotRange;
    public static int dynamaxScaleFactor;
    public static boolean showdownFilesLoading;
    public static boolean revertMegas;
    public static int teraShardRequired;
    public static double teraShardDropRate;
    public static double stellarShardDropRate;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        multipleMegas = MULTIPLE_MEGAS.get();
        battleModeOnly = BATTLE_MODE_ONLY.get();
        multiplePrimals = MULTIPLE_PRIMALS.get();
        zMoves = Z_MOVES.get();
        teralization = TERA_EVO.get();
        etermaxForme = ETERMAX_FORME.get();
        dynamax = DYNAMAX.get();
        dynamaxAnywhere = DYNAMAX_ANYWHERE.get();
        powerSpotRange = POWER_SPOT_RANGE.get();
        dynamaxScaleFactor = DMAX_SCALE_FACTOR.get();
        showdownFilesLoading = SHOWDOWN_FILES_LOADING.get();
        mega = MEGA.get();
        revertMegas = REVERT_MEGAS.get();
        teraShardRequired = TERA_SHARDS_REQUIRED.get();
        teraShardDropRate = TERA_SHARD_DROPRATE.get();
        stellarShardDropRate = STELLAR_SHARD_DROPRATE.get();
    }
}