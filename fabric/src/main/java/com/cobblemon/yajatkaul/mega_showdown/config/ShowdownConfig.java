package com.cobblemon.yajatkaul.mega_showdown.config;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.supermartijn642.configlib.api.ConfigBuilders;
import com.supermartijn642.configlib.api.IConfigBuilder;

import java.util.function.Supplier;

public class ShowdownConfig {
    public static final Supplier<Boolean> multipleMegas;
    public static final Supplier<Boolean> battleModeOnly;
    public static final Supplier<Boolean> friendshipMode;
    public static final Supplier<Boolean> zMoves;
    public static final Supplier<Boolean> teralization;
    public static final Supplier<Boolean> disableTeraShardDrop;
    public static final Supplier<Boolean> dynamax;
    public static final Supplier<Boolean> mega;
    public static final Supplier<Boolean> etermaxForme;
    public static final Supplier<Boolean> dynamaxAnywhere;
    public static final Supplier<Integer> powerSpotRange;
    public static final Supplier<Integer> dynamaxScaleFactor;
    public static final Supplier<Boolean> showdownFilesLoading;
    public static final Supplier<Boolean> multiplePrimals;
    public static final Supplier<Boolean> revertMegas;
    public static final Supplier<Double> teraShardDropChance;
    public static final Supplier<Double> stellarTeraShardDropChance;

    static {
        // construct a new config builder
        IConfigBuilder builder = ConfigBuilders.newTomlConfig(MegaShowdown.MOD_ID, "common", false);

        // a boolean value
        multipleMegas = builder.comment("Enable multiple mega's at one time").define("multipleMegas", false);
        battleModeOnly = builder.comment("Enable mega evolution only for battles").define("battleModeOnly", false);
        friendshipMode = builder.comment("Makes it so that you need to have 200+ friendship in order to mega outside").define("friendshipMode", false);
        zMoves = builder.comment("Enables/Disables zMoves in game").define("zMoves", true);
        teralization = builder.comment("Enables/Disables teralization in game").define("teralization", true);
        disableTeraShardDrop = builder.comment("Disables pokemons from dropping tera shards").define("disableTeraShardDrop", false);
        etermaxForme = builder.comment("Enables etermax eternus forme").define("etermaxForme", true);
        dynamax = builder.comment("Enables/Disables Dmaxing in game").define("dynamax", true);
        mega = builder.comment("Enables/Disables Mega in game").define("mega", true);
        dynamaxAnywhere = builder.comment("Allows you to dynamax anywhere you dont need to be near the dynamax area").define("dynamaxAnywhere", false);
        powerSpotRange = builder.comment("Range around a power spot where Dynamax is allowed").define("powerSpotRange", 20, 0, 10000);
        dynamaxScaleFactor = builder.comment("By how many times should the pokemon size increase when g/dmaxing").define("dynamaxScaleFactor", 4, 1, 10000);
        showdownFilesLoading = builder.comment("""
                 Enable/Disable loading of showdown files from the mod,\s
                 note this means once you load the game and the showdown changes have been affected you disable
                 this it will stop overwriting it ever load\
                , this is for people who want to edit showdown\s
                 but can't since mega showdown keeps overriding the files""").define("showdownFilesLoading", true);
        multiplePrimals = builder.comment("Allows you to have multiple primals").define("multiplePrimals", true);
        revertMegas = builder.comment("Enable/Disable mega pokemons form reverting when battle starts").define("revertMegas", true);

        teraShardDropChance = builder.comment("Drop chance for regular Tera Shards (default is 0.1 = 10%)")
                .define("teraShardDropChance", 0.10, 0, 1);
        stellarTeraShardDropChance = builder.comment("Drop chance for Stellar Tera Shards (default is 0.01 = 1%)")
                .define("stellarTeraShardDropChance", 0.01, 0, 1);

        builder.build();
    }
}