package com.cobblemon.yajatkaul.mega_showdown.datapack.showdown;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.data.DataRegistry;
import com.cobblemon.mod.common.api.reactive.SimpleObservable;
import com.cobblemon.mod.common.battles.runner.graal.GraalShowdownService;
import com.cobblemon.mod.relocations.graalvm.polyglot.Value;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.google.gson.Gson;
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

public class Conditions implements DataRegistry {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "showdown/conditions");
    private static final SimpleObservable<Conditions> OBSERVABLE = new SimpleObservable<>();

    private final Map<String, String> conditionScripts = new HashMap<>();

    public static final Conditions INSTANCE = new Conditions();

    private Conditions() {
        OBSERVABLE.subscribe(Priority.NORMAL , this::conditionsLoad);
    }

    private Unit conditionsLoad(Conditions conditions) {
        Cobblemon.INSTANCE.getShowdownThread().queue(showdownService -> {
            if(showdownService instanceof GraalShowdownService service){
                Value receiveConditionDataFn = service.context.getBindings("js").getMember("receiveConditionData");
                //TODO FIX THIS
                if(receiveConditionDataFn == null){
                    return Unit.INSTANCE;
                }
                for (Map.Entry<String, String> entry : Conditions.INSTANCE.getConditionScripts().entrySet()) {
                    String conditionId = entry.getKey();
                    String js = entry.getValue().replace("\n", " ");
                    receiveConditionDataFn.execute(conditionId, js);
                }
            }
            return Unit.INSTANCE;
        });
        return Unit.INSTANCE;
    }

    public Map<String, String> getConditionScripts() {
        return conditionScripts;
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
        conditionScripts.clear();
        resourceManager.listResources("showdown/conditions", path -> path.getPath().endsWith(".js")).forEach((id, resource) -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.open(), StandardCharsets.UTF_8))) {
                String js = reader.lines().collect(Collectors.joining("\n"));
                String conditionId = new File(id.getPath()).getName().replace(".js", "");
                conditionScripts.put(conditionId, js);
            } catch (IOException e) {
                MegaShowdown.LOGGER.error("Failed to load conditions script: {} {}", id, e);
            }
        });

        OBSERVABLE.emit(this);
    }

    @Override
    public void sync(@NotNull ServerPlayer serverPlayer) {
        // no sync needed for showdown injection
    }
}
