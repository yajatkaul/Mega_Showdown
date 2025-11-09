package com.github.yajatkaul.mega_showdown.gimmick;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.battles.runner.graal.GraalShowdownService;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Unit;
import org.graalvm.polyglot.Value;

import java.util.List;

public record MaxGimmick(
        String pokemon,
        String pokemonShowdownId,
        String gmaxMove,
        List<List<String>> blacklist_aspects,
        List<List<String>> required_aspects
) {
    public static final Codec<MaxGimmick> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("pokemon").forGetter(MaxGimmick::pokemon),
            Codec.STRING.fieldOf("pokemonShowdownId").forGetter(MaxGimmick::pokemonShowdownId),
            Codec.STRING.fieldOf("gmaxMove").forGetter(MaxGimmick::gmaxMove),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("blacklist_aspects", List.of()).forGetter(MaxGimmick::blacklist_aspects),
            Codec.list(Codec.list(Codec.STRING)).optionalFieldOf("required_aspects", List.of()).forGetter(MaxGimmick::required_aspects)
    ).apply(instance, MaxGimmick::new));

    //TODO complete this with datapacks
    public void register() {
        Cobblemon.INSTANCE.getShowdownThread().queue(showdownService -> {
            if (showdownService instanceof GraalShowdownService service) {
                Value receiveMoveDataFn = service.context.getBindings("js").getMember("receiveCustomGmaxMove");
            }
            return Unit.INSTANCE;
        });
    }
}