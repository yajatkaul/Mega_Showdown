package com.github.yajatkaul.mega_showdown.config;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MegaShowdownConfig {
    private static final String FILE_PATH = "./config/mega_showdown/config.json";

    public static int teraShardRequired = 50;
    public static boolean multipleMegas = false;
    public static boolean showdownFilesLoading = true;

    public static boolean mega = true;
    public static boolean zMoves = true;
    public static boolean teralization = true;
    public static boolean dynamax = true;
    public static int powerSpotRange = 20;
    public static boolean dynamaxAnywhere = false;
    public static float dynamaxScaleFactor = 4f;

    public static double teraShardDropRate = 10.0;
    public static double stellarShardDropRate = 1.0;

    public static void register() {
        load();
    }

    private static void save() {
        JsonObject json = getJsonObject();

        try {
            Files.createDirectories(Path.of("./config/mega_showdown"));
            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                writer.write(gson.toJson(json));
            }
        } catch (IOException e) {
            MegaShowdown.LOGGER.error("Failed to save MegaShowdown config:", e);
        }
    }

    private static @NotNull JsonObject getJsonObject() {
        JsonObject json = new JsonObject();
        json.addProperty("teraShardRequired", teraShardRequired);
        json.addProperty("multipleMegas", multipleMegas);
        json.addProperty("showdownFilesLoading", showdownFilesLoading);

        json.addProperty("mega", mega);
        json.addProperty("zMoves", zMoves);
        json.addProperty("teralization", teralization);
        json.addProperty("dynamax", dynamax);
        json.addProperty("powerSpotRange", powerSpotRange);
        json.addProperty("dynamaxAnywhere", dynamaxAnywhere);
        json.addProperty("dynamaxScaleFactor", dynamaxScaleFactor);
        json.addProperty("teraShardDropRate", teraShardDropRate);
        json.addProperty("stellarShardDropRate", stellarShardDropRate);
        return json;
    }

    private static void load() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            MegaShowdown.LOGGER.info("MegaShowdown config not found, creating default.");
            save();
            return;
        }

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            if (json.has("teraShardRequired")) {
                teraShardRequired = json.get("teraShardRequired").getAsInt();
            }
            if (json.has("multipleMegas")) {
                multipleMegas = json.get("multipleMegas").getAsBoolean();
            }
            if (json.has("showdownFilesLoading")) {
                showdownFilesLoading = json.get("showdownFilesLoading").getAsBoolean();
            }
            if (json.has("mega")) {
                mega = json.get("mega").getAsBoolean();
            }
            if (json.has("zMoves")) {
                zMoves = json.get("zMoves").getAsBoolean();
            }
            if (json.has("teralization")) {
                teralization = json.get("teralization").getAsBoolean();
            }
            if (json.has("dynamax")) {
                dynamax = json.get("dynamax").getAsBoolean();
            }
            if (json.has("powerSpotRange")) {
                powerSpotRange = json.get("powerSpotRange").getAsInt();
            }
            if (json.has("dynamaxAnywhere")) {
                dynamaxAnywhere = json.get("dynamaxAnywhere").getAsBoolean();
            }
            if (json.has("dynamaxScaleFactor")) {
                dynamaxScaleFactor = json.get("dynamaxScaleFactor").getAsFloat();
            }
            if (json.has("teraShardDropRate")) {
                teraShardDropRate = json.get("teraShardDropRate").getAsDouble();
            }
            if (json.has("stellarShardDropRate")) {
                stellarShardDropRate = json.get("stellarShardDropRate").getAsDouble();
            }
        } catch (Exception e) {
            MegaShowdown.LOGGER.error("Failed to load MegaShowdown config:", e);
        }
    }

    public static int getDynamaxScaleDuration() {
        return (int) (MegaShowdownConfig.dynamaxScaleFactor / 0.1f);
    }
}
