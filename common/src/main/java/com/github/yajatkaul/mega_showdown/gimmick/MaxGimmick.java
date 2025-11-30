package com.github.yajatkaul.mega_showdown.gimmick;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.github.yajatkaul.mega_showdown.gimmick.codec.AspectSetCodec;
import com.github.yajatkaul.mega_showdown.utils.GlowHandler;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public record MaxGimmick(
        String pokemonShowdownId,
        String gmaxMove,
        AspectSetCodec aspectSetCodec
) {
    public static final Codec<MaxGimmick> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("pokemon_showdown_id").forGetter(MaxGimmick::pokemonShowdownId),
            Codec.STRING.fieldOf("gmax_move").forGetter(MaxGimmick::gmaxMove),
            AspectSetCodec.CODEC.fieldOf("aspect_conditions").forGetter(MaxGimmick::aspectSetCodec)
    ).apply(instance, MaxGimmick::new));
    private static final Map<LivingEntity, ScalingData> ACTIVE_SCALING_ANIMATIONS = new HashMap<>();
    private static final int DEFAULT_SCALING_DURATION = 60;

    public static void startGradualScaling(PokemonEntity entity, float targetScale) {
        if (entity == null) return;

        GlowHandler.applyDynamaxGlow(entity);

        AttributeInstance scaleAttr = entity.getAttribute(Attributes.SCALE);
        if (scaleAttr == null) return;

        float startScale = (float) scaleAttr.getBaseValue();
        ScalingData scalingData = new ScalingData(startScale, targetScale, DEFAULT_SCALING_DURATION);

        ACTIVE_SCALING_ANIMATIONS.put(entity, scalingData);
    }

    public static void startGradualScalingDown(PokemonEntity entity) {
        if (entity == null) return;

        entity.removeEffect(MobEffects.GLOWING);

        AttributeInstance scaleAttr = entity.getAttribute(Attributes.SCALE);
        if (scaleAttr == null) return;

        float startScale = (float) scaleAttr.getBaseValue();
        ScalingData scalingData = new ScalingData(startScale, 1, DEFAULT_SCALING_DURATION);

        ACTIVE_SCALING_ANIMATIONS.put(entity, scalingData);
    }

    public static void updateScalingAnimations(MinecraftServer server) {
        if (server == null) return;

        Iterator<Map.Entry<LivingEntity, ScalingData>> iterator = ACTIVE_SCALING_ANIMATIONS.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<LivingEntity, ScalingData> entry = iterator.next();
            LivingEntity entity = entry.getKey();
            ScalingData data = entry.getValue();
            data.currentTick++;

            if (!entity.isAlive()) {
                iterator.remove();
                continue;
            }

            AttributeInstance scaleAttr = entity.getAttribute(Attributes.SCALE);
            if (scaleAttr != null) {
                float progress = Math.min(1.0f, (float) data.currentTick / data.durationTicks);
                float newScale = data.startScale + (data.targetScale - data.startScale) * progress;
                scaleAttr.setBaseValue(newScale);
            }

            if (data.currentTick >= data.durationTicks) {
                iterator.remove();
            }
        }
    }

    private static class ScalingData {
        final float startScale;
        final float targetScale;
        final int durationTicks;
        int currentTick;

        public ScalingData(float startScale, float targetScale, int durationTicks) {
            this.startScale = startScale;
            this.targetScale = targetScale;
            this.durationTicks = durationTicks;
            this.currentTick = 0;
        }
    }
}