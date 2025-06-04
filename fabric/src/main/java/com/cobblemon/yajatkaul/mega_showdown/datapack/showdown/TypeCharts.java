package com.cobblemon.yajatkaul.mega_showdown.datapack.showdown;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.data.DataRegistry;
import com.cobblemon.mod.common.api.reactive.SimpleObservable;
import com.cobblemon.mod.common.battles.runner.graal.GraalShowdownService;
import com.cobblemon.mod.relocations.graalvm.polyglot.Value;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
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

public class TypeCharts implements DataRegistry {
    private static final Identifier ID = Identifier.of(MegaShowdown.MOD_ID, "showdown/typecharts");
    private static final SimpleObservable<TypeCharts> OBSERVABLE = new SimpleObservable<>();
    public static final TypeCharts INSTANCE = new TypeCharts();
    private final Map<String, String> typeChartScripts = new HashMap<>();

    private TypeCharts() {
        OBSERVABLE.subscribe(Priority.NORMAL, this::typeChartsLoad);
    }

    public Unit typeChartsLoad(DataRegistry typeChart) {
        registerTypeCharts();
        return Unit.INSTANCE;
    }

    public Map<String, String> getTypeChartScripts() {
        return typeChartScripts;
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
        typeChartScripts.clear();
        resourceManager.findAllResources("showdown/typecharts", path -> path.getPath().endsWith(".js")).forEach((id, resources) -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resources.getLast().getInputStream(), StandardCharsets.UTF_8))) {
                String js = reader.lines().collect(Collectors.joining("\n"));
                String typeChartId = new File(id.getPath()).getName().replace(".js", "");
                typeChartScripts.put(typeChartId, js);
            } catch (IOException e) {
                MegaShowdown.LOGGER.error("Failed to load typechart script: {} {}", id, e);
            }
        });
        OBSERVABLE.emit(this);
    }

    public void registerTypeCharts() {
        Cobblemon.INSTANCE.getShowdownThread().queue(showdownService -> {
            if (showdownService instanceof GraalShowdownService service) {
                Value receiveTypeChartDataFn = service.context.getBindings("js").getMember("receiveTypeChartData");
                for (Map.Entry<String, String> entry : TypeCharts.INSTANCE.getTypeChartScripts().entrySet()) {
                    String typeChartId = entry.getKey();
                    String js = entry.getValue().replace("\n", " ");
                    receiveTypeChartDataFn.execute(typeChartId, js);
                }
            }
            return Unit.INSTANCE;
        });
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
