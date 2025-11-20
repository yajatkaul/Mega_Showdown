package com.github.yajatkaul.mega_showdown.fabric.datapack.showdown;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.data.DataRegistry;
import com.cobblemon.mod.common.api.reactive.SimpleObservable;
import com.cobblemon.mod.common.battles.runner.graal.GraalShowdownService;
import com.cobblemon.mod.relocations.graalvm.polyglot.Value;
import com.github.yajatkaul.mega_showdown.MegaShowdown;
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

public class HeldItems implements DataRegistry {
    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "showdown/held_items");
    private static final SimpleObservable<HeldItems> OBSERVABLE = new SimpleObservable<>();
    private final Map<String, String> heldItemsScripts = new HashMap<>();
    public static final HeldItems INSTANCE = new HeldItems();

    private HeldItems() {
        OBSERVABLE.subscribe(Priority.NORMAL, this::heldItemsLoad);
    }

    public void heldItemsLoad(HeldItems heldItem) {
        Cobblemon.INSTANCE.getShowdownThread().queue(showdownService -> {
            if (showdownService instanceof GraalShowdownService service) {
                Value receiveHeldItemDataFn = service.context.getBindings("js").getMember("receiveHeldItemData");
                for (Map.Entry<String, String> entry : HeldItems.INSTANCE.getHeldItemsScripts().entrySet()) {
                    String itemId = entry.getKey();
                    String js = entry.getValue().replace("\n", " ");
                    receiveHeldItemDataFn.execute(itemId, js);
                }
            }
            return Unit.INSTANCE;
        });
    }

    public Map<String, String> getHeldItemsScripts() {
        return heldItemsScripts;
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
        heldItemsScripts.clear();
        resourceManager.listResources("showdown/held_items", path -> path.getPath().endsWith(".js")).forEach((id, resource) -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.open(), StandardCharsets.UTF_8))) {
                String js = reader.lines().collect(Collectors.joining("\n"));
                String itemId = new File(id.getPath()).getName().replace(".js", "");
                heldItemsScripts.put(itemId, js);
            } catch (IOException e) {
                MegaShowdown.LOGGER.error("Failed to load held item script: {} {}", id, e);
            }
        });

        OBSERVABLE.emit(this);
    }

    @Override
    public void sync(@NotNull ServerPlayer serverPlayer) {
        // no sync needed for showdown injection
    }
}
