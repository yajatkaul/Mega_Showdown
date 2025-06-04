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

public class TypeCharts implements DataRegistry {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "showdown/typecharts");
    private static final SimpleObservable<TypeCharts> OBSERVABLE = new SimpleObservable<>();
    public static final TypeCharts INSTANCE = new TypeCharts();
    private final Map<String, String> typeChartScripts = new HashMap<>();

    private TypeCharts() {
        OBSERVABLE.subscribe(Priority.NORMAL, this::typeChartsLoad);
    }

    private Unit typeChartsLoad(TypeCharts typeChart) {
        registerTypeCharts();
        return Unit.INSTANCE;
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

    public Map<String, String> getTypeChartScripts() {
        return typeChartScripts;
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
        typeChartScripts.clear();
        resourceManager.listResources("showdown/typecharts", path -> path.getPath().endsWith(".js")).forEach((id, resource) -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.open(), StandardCharsets.UTF_8))) {
                String js = reader.lines().collect(Collectors.joining("\n"));
                String typeChartId = new File(id.getPath()).getName().replace(".js", "");
                typeChartScripts.put(typeChartId, js);
            } catch (IOException e) {
                MegaShowdown.LOGGER.error("Failed to load typechart script: {} {}", id, e);
            }
        });

        OBSERVABLE.emit(this);
    }

    @Override
    public void sync(@NotNull ServerPlayer serverPlayer) {
        // no sync needed for showdown injection
    }
}
