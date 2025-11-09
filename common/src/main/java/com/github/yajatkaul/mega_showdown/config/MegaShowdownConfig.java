package com.github.yajatkaul.mega_showdown.config;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

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

    public static void register() {
        load();
    }

    private static void save() {
        JsonObject json = new JsonObject();
        json.addProperty("teraShardRequired", teraShardRequired);
        json.addProperty("multipleMegas", multipleMegas);

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
        } catch (Exception e) {
            MegaShowdown.LOGGER.error("Failed to load MegaShowdown config:", e);
        }
    }
}
