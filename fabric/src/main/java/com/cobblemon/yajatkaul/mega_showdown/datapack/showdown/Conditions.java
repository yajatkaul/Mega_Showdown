package com.cobblemon.yajatkaul.mega_showdown.datapack.showdown;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.data.DataRegistry;
import com.cobblemon.mod.common.api.reactive.SimpleObservable;
import com.cobblemon.mod.common.battles.runner.graal.GraalShowdownService;
import com.cobblemon.mod.relocations.graalvm.polyglot.Value;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import kotlin.Unit;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
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
    private static final Identifier ID = Identifier.of(MegaShowdown.MOD_ID, "showdown/conditions");
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
    public Identifier getId() {
        return ID;
    }


    @NotNull
    @Override
    public SimpleObservable<? extends DataRegistry> getObservable() {
        return OBSERVABLE;
    }

    @Override
    public void reload(@NotNull ResourceManager resourceManager) {
        conditionScripts.clear();
        resourceManager.findAllResources("showdown/conditions", path -> path.getPath().endsWith(".js")).forEach((id, resource) -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getFirst().getInputStream(), StandardCharsets.UTF_8))) {
                String js = reader.lines().collect(Collectors.joining("\n"));
                String conditionId = new File(id.getPath()).getName().replace(".js", "");
                conditionScripts.put(conditionId, js);
            } catch (IOException e) {
                MegaShowdown.LOGGER.error("Failed to load conditions script: {} {}", id, e);
            }
        });
        OBSERVABLE.emit(this);
    }

    @NotNull
    @Override
    public ResourceType getType() {
        return ResourceType.SERVER_DATA;
    }

    @Override
    public void sync(@NotNull ServerPlayerEntity serverPlayerEntity) {

    }
}
