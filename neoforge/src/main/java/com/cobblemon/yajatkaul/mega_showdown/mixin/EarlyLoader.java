package com.cobblemon.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.battles.ShowdownThread;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Mixin(ShowdownThread.class)
public class EarlyLoader {

    @Inject(method = "run", at = @At("HEAD"))
    private void beforeShowdownStarts(CallbackInfo ci) {
        MegaShowdown.LOGGER.info("Checking internet connection...");

        boolean hasInternet = checkInternetConnection();
        Path showdownDir = Path.of("./showdown/sim");

        try {
            Files.createDirectories(showdownDir);

            if (hasInternet) {
                MegaShowdown.LOGGER.info("Internet available. Downloading required files...");
                downloadFile("https://raw.githubusercontent.com/yajatkaul/Mega_Showdown/refs/heads/main/showdown/moves.js", showdownDir.resolve("moves.js"));
                downloadFile("https://raw.githubusercontent.com/yajatkaul/Mega_Showdown/refs/heads/main/showdown/battle-actions.js", showdownDir.resolve("battle-actions.js"));
            } else {
                MegaShowdown.LOGGER.info("No internet detected. Using fallback files...");
                copyFallbackFile("/assets/mega_showdown/showdown/moves.js", showdownDir.resolve("moves.js"));
                copyFallbackFile("/assets/mega_showdown/showdown/battle-actions.js", showdownDir.resolve("battle-actions.js"));
            }

            MegaShowdown.LOGGER.info("All files are ready!");
        } catch (IOException e) {
            MegaShowdown.LOGGER.error("Failed to prepare required files: {}", e.getMessage());
        }
    }

    @Unique
    private boolean checkInternetConnection() {
        try {
            URL url = new URL("https://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(3000); // 3 seconds timeout
            connection.setReadTimeout(3000);
            connection.connect();
            return (connection.getResponseCode() == 200);
        } catch (IOException e) {
            return false; // No internet
        }
    }

    @Unique
    private void downloadFile(String url, Path targetPath) throws IOException {
        MegaShowdown.LOGGER.info("Downloading: {}", url);
        try (var inputStream = new URL(url).openStream()) {
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
        MegaShowdown.LOGGER.info("Saved: {}", targetPath);
    }

    @Unique
    private void copyFallbackFile(String resourcePath, Path targetPath) {
        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                MegaShowdown.LOGGER.error("Fallback file not found: {}", resourcePath);
                return;
            }
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            MegaShowdown.LOGGER.info("Loaded fallback file: {}", targetPath);
        } catch (IOException e) {
            MegaShowdown.LOGGER.error("Failed to copy fallback file {}: {}", resourcePath, e.getMessage());
        }
    }
}