package com.cobblemon.yajatkaul.mega_showdown.datapack.showdown;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.data.DataRegistry;
import com.cobblemon.mod.common.api.reactive.SimpleObservable;
import com.cobblemon.mod.common.battles.runner.graal.GraalShowdownService;
import com.cobblemon.mod.relocations.graalvm.polyglot.Value;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import kotlin.Unit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Scripts implements DataRegistry {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "showdown/scripts");
    private static final SimpleObservable<Scripts> OBSERVABLE = new SimpleObservable<>();
    public static final Scripts INSTANCE = new Scripts();
    private final Map<String, String> scripts = new HashMap<>();

    private Scripts() {
        OBSERVABLE.subscribe(Priority.NORMAL, this::scriptsLoad);
    }

    private Unit scriptsLoad(Scripts scripts) {
        registerScripts();
        return Unit.INSTANCE;
    }

    public void registerScripts() {
        Cobblemon.INSTANCE.getShowdownThread().queue(showdownService -> {
            if (showdownService instanceof GraalShowdownService service) {
                Value receiveScriptDataFn = service.context.getBindings("js").getMember("receiveScriptData");
                for (Map.Entry<String, String> entry : Scripts.INSTANCE.getScripts().entrySet()) {
                    String scriptId = entry.getKey();
                    String js = entry.getValue().replace("\n", " ");
                    receiveScriptDataFn.execute(scriptId, js);
                }
            }
            return Unit.INSTANCE;
        });
    }

    public Map<String, String> getScripts() {
        return scripts;
    }

    @NotNull
    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @NotNull
    @Override
    public PackType getType() {
        return PackType.SERVER_DATA;
    }

    @NotNull
    @Override
    public SimpleObservable<? extends DataRegistry> getObservable() {
        return OBSERVABLE;
    }

    @Override
    public void reload(@NotNull ResourceManager resourceManager) {
        scripts.clear();
        resourceManager.listResources("showdown/scripts", path -> path.getPath().endsWith(".js")).forEach((id, resource) -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.open(), StandardCharsets.UTF_8))) {
                String js = reader.lines().collect(Collectors.joining("\n"));
                String scriptId = new File(id.getPath()).getName().replace(".js", "");
                scripts.put(scriptId, js);
            } catch (IOException e) {
                MegaShowdown.LOGGER.error("Failed to load script: {} {}", id, e);
            }
        });

        OBSERVABLE.emit(this);
    }

    @Override
    public void sync(@NotNull ServerPlayer serverPlayer) {
        // no sync needed for showdown injection
    }
}
