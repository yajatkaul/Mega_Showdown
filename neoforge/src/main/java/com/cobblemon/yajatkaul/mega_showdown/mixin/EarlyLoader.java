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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Mixin(ShowdownThread.class)
public class EarlyLoader {

    @Inject(method = "run", at = @At("HEAD"))
    private void beforeShowdownStarts(CallbackInfo ci) {
        Path showdown_sim = Path.of("./showdown/sim");
        Path showdown_data = Path.of("./showdown/data");

        try {
            Files.createDirectories(showdown_sim);
            Files.createDirectories(showdown_data);

            yoink("/assets/mega_showdown/showdown/moves.js", showdown_data.resolve("moves.js"));
            yoink("/assets/mega_showdown/showdown/battle-actions.js", showdown_sim.resolve("battle-actions.js"));
            yoink("/assets/mega_showdown/showdown/pokemon.js", showdown_sim.resolve("pokemon.js"));
            yoink("/assets/mega_showdown/showdown/abilities.js", showdown_data.resolve("abilities.js"));
            yoink("/assets/mega_showdown/showdown/items.js", showdown_data.resolve("items.js"));
            yoink("/assets/mega_showdown/showdown/side.js", showdown_sim.resolve("side.js"));
            yoink("/assets/mega_showdown/showdown/conditions.js", showdown_sim.resolve("conditions.js"));

            MegaShowdown.LOGGER.info("All files are ready!");
        } catch (IOException e) {
            MegaShowdown.LOGGER.error("Failed to prepare required files: {}", e.getMessage());
        }
    }

    @Unique
    private void yoink(String resourcePath, Path targetPath) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
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