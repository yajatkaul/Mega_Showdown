package com.cobblemon.yajatkaul.mega_showdown.datapack.showdown;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.data.DataRegistry;
import com.cobblemon.mod.common.api.reactive.SimpleObservable;
import com.cobblemon.mod.common.battles.runner.graal.GraalShowdownService;
import com.cobblemon.mod.relocations.graalvm.polyglot.Value;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.mixin.AbilitiesAccessor;
import com.cobblemon.yajatkaul.mega_showdown.utility.datapack.NewAbility;
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
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Abilities implements DataRegistry {
    private static final Identifier ID = Identifier.of(MegaShowdown.MOD_ID, "showdown/abilities");
    private static final SimpleObservable<Abilities> OBSERVABLE = new SimpleObservable<>();

    private final Map<String, String> abilityScripts = new HashMap<>();

    public static final Abilities INSTANCE = new Abilities();

    Gson gson = new Gson();

    private Abilities() {
        OBSERVABLE.subscribe(Priority.NORMAL , this::abilitiesLoad);
    }

    private Unit abilitiesLoad(Abilities abilities) {
        Cobblemon.INSTANCE.getShowdownThread().queue(showdownService -> {
            if(showdownService instanceof GraalShowdownService service){
                Value receiveAbilityDataFn = service.context.getBindings("js").getMember("receiveAbilityData");
                //TODO FIX THIS
                if(receiveAbilityDataFn == null){
                    return Unit.INSTANCE;
                }
                for (Map.Entry<String, String> entry : Abilities.INSTANCE.getAbilityScripts().entrySet()) {
                    String abilityId = entry.getKey();
                    String js = entry.getValue().replace("\n", " ");
                    JsonObject abilityData = gson.fromJson(receiveAbilityDataFn.execute(abilityId, js).asString(), JsonObject.class);
                    String name = abilityData.get("name").getAsString().toLowerCase(Locale.ROOT);

                    AbilitiesAccessor.getAllAbilities().put(name
                            , NewAbility.INSTANCE.getAbility(abilityId));
                }
            }
            return Unit.INSTANCE;
        });
        return Unit.INSTANCE;
    }

    public Map<String, String> getAbilityScripts() {
        return abilityScripts;
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
        abilityScripts.clear();
        resourceManager.findAllResources("showdown/abilities", path -> path.getPath().endsWith(".js")).forEach((id, resource) -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getFirst().getInputStream(), StandardCharsets.UTF_8))) {
                String js = reader.lines().collect(Collectors.joining("\n"));
                String abilityId = new File(id.getPath()).getName().replace(".js", "");
                abilityScripts.put(abilityId, js);
            } catch (IOException e) {
                MegaShowdown.LOGGER.error("Failed to load abilities script: {} {}", id, e);
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
