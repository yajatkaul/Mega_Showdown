package com.cobblemon.yajatkaul.mega_showdown;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "mega_showdown-common.json");

    // Configuration fields
    public boolean multipleMegas = false;
    public boolean battleModeOnly = false;
    public boolean megaTurns = false;

    private static Config instance;

    public static Config getInstance() {
        if (instance == null) {
            load();
        }
        return instance;
    }

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                instance = GSON.fromJson(reader, Config.class);
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        } else {
            instance = new Config(); // Load default values if no config exists
            save();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(instance, writer);
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }
}
