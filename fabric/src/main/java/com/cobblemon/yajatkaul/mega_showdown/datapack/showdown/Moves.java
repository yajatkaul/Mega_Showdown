package com.cobblemon.yajatkaul.mega_showdown.datapack.showdown;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.data.DataRegistry;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.api.moves.animations.ActionEffectTimeline;
import com.cobblemon.mod.common.api.moves.animations.ActionEffects;
import com.cobblemon.mod.common.api.moves.categories.DamageCategories;
import com.cobblemon.mod.common.api.moves.categories.DamageCategory;
import com.cobblemon.mod.common.api.reactive.SimpleObservable;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.battles.MoveTarget;
import com.cobblemon.mod.common.battles.runner.graal.GraalShowdownService;
import com.cobblemon.mod.relocations.graalvm.polyglot.Value;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.mixin.MovesAccessor;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Moves implements DataRegistry {
    private static final Identifier ID = Identifier.of(MegaShowdown.MOD_ID, "showdown/moves");
    private static final SimpleObservable<Moves> OBSERVABLE = new SimpleObservable<>();

    private final Map<String, String> moveScripts = new HashMap<>();

    public static final Moves INSTANCE = new Moves();

    Gson gson = new Gson();

    private Moves() {
        OBSERVABLE.subscribe(Priority.NORMAL , this::movesLoad);
    }

    private Unit movesLoad(Moves move) {
        Cobblemon.INSTANCE.getShowdownThread().queue(showdownService -> {
            if(showdownService instanceof GraalShowdownService service){
                Value receiveMoveDataFn = service.context.getBindings("js").getMember("receiveMoveData");
                for (Map.Entry<String, String> entry : Moves.INSTANCE.getMoveScripts().entrySet()) {
                    String moveId = entry.getKey();
                    String js = entry.getValue().replace("\n", " ");
                    JsonObject moveData = gson.fromJson(receiveMoveDataFn.execute(moveId, js).asString(), JsonObject.class);
                    MoveTemplate newMove = newTemplateMove(moveData);
                    String name = moveData.get("name").getAsString();
                    int num = moveData.get("num").getAsInt();

                    MovesAccessor.getAllMoves().put(name, newMove);
                    MovesAccessor.getIdMapping().put(num, newMove);
                }
            }
            return Unit.INSTANCE;
        });
        return Unit.INSTANCE;
    }

    private MoveTemplate newTemplateMove(JsonObject moveData){
        String name = moveData.get("name").getAsString();
        int num = moveData.get("num").getAsInt();
        ElementalType elementalType = ElementalTypes.INSTANCE.getOrException(moveData.get("type").getAsString());
        DamageCategory damageCategory = DamageCategories.INSTANCE.getOrException((moveData.get("category").getAsString()));
        double power = moveData.get("basePower").getAsDouble();
        MoveTarget target = MoveTarget.valueOf(moveData.get("target").getAsString());

        JsonElement accuracyElem = moveData.get("accuracy");
        double accuracy = accuracyElem.isJsonPrimitive() && accuracyElem.getAsJsonPrimitive().isNumber()
                ? accuracyElem.getAsDouble()
                : -1.0;

        int pp = moveData.get("pp").getAsInt();
        int priority = moveData.get("priority").getAsInt();
        double critRatio = 1.0;
        Double[] effectChances = new Double[0];

        Identifier id = Identifier.of("cobblemon", "generic_move");
        ActionEffectTimeline actionEffect = ActionEffects.INSTANCE.getActionEffects().get(id);

        return new MoveTemplate(
                name, num, elementalType, damageCategory,
                power, target, accuracy, pp, priority,
                critRatio, effectChances, actionEffect
        );
    }

    public Map<String, String> getMoveScripts() {
        return moveScripts;
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
        moveScripts.clear();
        resourceManager.findAllResources("showdown/moves", path -> path.getPath().endsWith(".js")).forEach((id, resource) -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getFirst().getInputStream(), StandardCharsets.UTF_8))) {
                String js = reader.lines().collect(Collectors.joining("\n"));
                String moveId = new File(id.getPath()).getName().replace(".js", "");
                moveScripts.put(moveId, js);
            } catch (IOException e) {
                MegaShowdown.LOGGER.error("Failed to load move script: {} {}", id, e);
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
