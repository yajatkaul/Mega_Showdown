package com.github.yajatkaul.mega_showdown.utils;

import com.github.yajatkaul.mega_showdown.datapack.MegaShowdownDatapackRegister;
import com.github.yajatkaul.mega_showdown.utils.particles.AnimationData;
import com.github.yajatkaul.mega_showdown.utils.particles.MinecraftParticle;
import com.github.yajatkaul.mega_showdown.utils.particles.SnowStormParticle;
import com.github.yajatkaul.mega_showdown.utils.particles.SoundCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Optional;

public class ParticlesList {
    public static MinecraftParticle calyrexDynamaxLevelUpParticles = simpleMinecraftParticlesApply(
            "minecraft:soul_fire_flame",
            new SoundCodec("minecraft:block.amethyst_block.chime", 1, 1),
            0.5f
    );

    public static MinecraftParticle otherDynamaxLevelUpParticles = simpleMinecraftParticlesApply(
            "minecraft:flame",
            new SoundCodec("minecraft:block.amethyst_block.chime", 1, 1),
            0.5f
    );

    public static MinecraftParticle glowParticles = simpleMinecraftParticlesApply(
            "minecraft:glow",
            new SoundCodec("minecraft:block.amethyst_block.chime", 1, 1),
            1.2f
    );

    public static MinecraftParticle simpleMinecraftParticlesApply(String id_apply, SoundCodec sound_apply, float scale) {
        return new MinecraftParticle(
                Optional.of(id_apply),
                Optional.empty(),
                Optional.of(sound_apply),
                Optional.empty(),
                Optional.of(scale),
                Optional.empty(),
                Optional.of(new AnimationData(List.of("cry"), List.of(), List.of(), List.of(), 0, 0))
        );
    }

    public static Effect getEffect(String effectId) {
        Effect effect = MegaShowdownDatapackRegister.EFFECT_REGISTRY.get(ResourceLocation.tryParse(effectId));
        if (effect == null) {
            return Effect.empty();
        } else {
            return effect;
        }
    }

    public static Effect getEffect(ResourceLocation effectId) {
        Effect effect = MegaShowdownDatapackRegister.EFFECT_REGISTRY.get(effectId);
        if (effect == null) {
            return Effect.empty();
        } else {
            return effect;
        }
    }
}
