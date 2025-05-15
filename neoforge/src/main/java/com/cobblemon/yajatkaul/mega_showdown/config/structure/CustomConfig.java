package com.cobblemon.yajatkaul.mega_showdown.config.structure;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.google.gson.*;
import net.neoforged.fml.loading.FMLPaths;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CustomConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE;

    static {
        Path configDir = FMLPaths.CONFIGDIR.get(); // NeoForge's way to get config dir
        CONFIG_FILE = new File(configDir.toFile(), "mega_showdown/mega_showdown-customs.json");
    }

    private static final String DEFAULT_JSON = "{ \"items\": [] }";

    // Item storage
    public static List<Fusion> fusionItems = new ArrayList<>();
    public static List<FormeChange> formeChange = new ArrayList<>();
    public static List<HeldItem> heldItems = new ArrayList<>();
    public static List<MegaItem> megaItems = new ArrayList<>();
    public static List<Gmax> gmax = new ArrayList<>();
    public static List<KeyItems> keyItems = new ArrayList<>();

    public static void load() {
        if (!CONFIG_FILE.exists()) {
            try {
                File parent = CONFIG_FILE.getParentFile();
                if (!parent.exists()) parent.mkdirs();

                try (Writer writer = new FileWriter(CONFIG_FILE)) {
                    writer.write(DEFAULT_JSON);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to create default config: " + CONFIG_FILE, e);
            }
        }

        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray items = root.getAsJsonArray("items");

            for (JsonElement element : items) {
                JsonObject obj = element.getAsJsonObject();
                String type = obj.get("type").getAsString();

                switch (type) {
                    case "FUSION" -> fusionItems.add(GSON.fromJson(obj, Fusion.class));
                    case "FORME_CHANGE" -> formeChange.add(GSON.fromJson(obj, FormeChange.class));
                    case "HELD_ITEM" -> heldItems.add(GSON.fromJson(obj, HeldItem.class));
                    case "MEGA" -> megaItems.add(GSON.fromJson(obj, MegaItem.class));
                    case "GMAX" -> gmax.add(GSON.fromJson(obj, Gmax.class));
                    case "KEY_ITEM" -> keyItems.add(GSON.fromJson(obj, KeyItems.class));
                    default -> System.err.println("Unknown item type in config: " + type);
                }
            }

            MegaShowdown.LOGGER.info("Successfully reloaded MegaShowdown Custom Config");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config from: " + CONFIG_FILE, e);
        }
    }
}