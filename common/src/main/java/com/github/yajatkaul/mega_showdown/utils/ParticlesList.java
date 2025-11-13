package com.github.yajatkaul.mega_showdown.utils;

import com.github.yajatkaul.mega_showdown.utils.particles.AnimationData;
import com.github.yajatkaul.mega_showdown.utils.particles.MinecraftParticle;
import com.github.yajatkaul.mega_showdown.utils.particles.SnowStormParticle;

import java.util.List;
import java.util.Optional;

public class ParticlesList {
    //TODO adds sounds to these 3
    public static Effect groudonPrimalRevert = new Effect(
            Optional.of(new MinecraftParticle(
                    Optional.of("minecraft:campfire_cosy_smoke"),
                    Optional.of("minecraft:end_rod"),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of(1f),
                    Optional.empty(),
                    Optional.of(new AnimationData(List.of("cry"), List.of(), List.of(), List.of(), 0, 0))
            )),
            Optional.empty()
    );

    public static Effect kyogrePrimalRevert = new Effect(
            Optional.of(new MinecraftParticle(
                    Optional.of("minecraft:bubble"),
                    Optional.of("minecraft:end_rod"),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of(1f),
                    Optional.empty(),
                    Optional.of(new AnimationData(List.of("cry"), List.of(), List.of(), List.of(), 0, 0))
            )),
            Optional.empty()
    );

    public static Effect megaEvolution = new Effect(
            Optional.of(defaultConfigMCParticles("minecraft:end_rod")),
            Optional.of(simpleSnowstormParticles("cobblemon:mega_evolution",
                    4.7f,
                    "mega_showdown:mega")
            )
    );

    public static Effect likosPendantAnim = new Effect(
            Optional.of(defaultConfigMCParticles("minecraft:end_rod")),
            Optional.empty()
    );

    public static Effect heartAnim = new Effect(
            Optional.of(defaultConfigMCParticles("minecraft:heart")),
            Optional.empty()
    );


    public static Effect zMoves = new Effect(
            Optional.empty(),
            Optional.of(zmoveSnowStorm("cobblemon:z_moves",
                    4f,
                    "mega_showdown:z_moves")
            )
    );

    public static Effect kyuremBlackFusion = new Effect(
            Optional.of(defaultConfigMCParticles("minecraft:ash")),
            Optional.of(simpleSnowstormParticles("cobblemon:kyurem_b_effect",
                    4f,
                    "mega_showdown:z_move")
            )
    );

    public static Effect kyuremWhiteFusion = new Effect(
            Optional.of(defaultConfigMCParticles("minecraft:ash")),
            Optional.of(simpleSnowstormParticles("cobblemon:kyurem_w_effect",
                    4f,
                    "mega_showdown:kyurem_fusion")
            )
    );

    public static Effect calyrexShadowFusion = new Effect(
            Optional.of(simpleMinecraftParticles("minecraft:smoke",
                    "minecraft:end_rod",
                    "minecraft:block.amethyst_block.chime",
                    "minecraft:block.amethyst_block.chime")),
            Optional.empty()
    );

    public static Effect calyrexIceFusion = new Effect(
            Optional.of(simpleMinecraftParticles("minecraft:end_rod",
                    "minecraft:end_rod",
                    "minecraft:block.amethyst_block.chime",
                    "minecraft:block.amethyst_block.chime")),
            Optional.empty()
    );

    public static Effect nLunSolFusion = new Effect(
            Optional.of(simpleMinecraftParticles("minecraft:ash",
                    "minecraft:ash",
                    "minecraft:block.amethyst_block.chime",
                    "minecraft:block.amethyst_block.chime")),
            Optional.empty()
    );

    public static Effect ultraBurst = new Effect(
            Optional.of(simpleMinecraftParticles("minecraft:glow",
                    "minecraft:ash",
                    "minecraft:block.amethyst_block.chime",
                    "minecraft:block.amethyst_block.chime")),
            Optional.empty()
    );

    public static MinecraftParticle calyrexDynamaxLevelUpParticles = simpleMinecraftParticlesApply(
            "minecraft:soul_fire_flame",
            "minecraft:block.amethyst_block.chime",
            0.5f
    );

    public static MinecraftParticle otherDynamaxLevelUpParticles = simpleMinecraftParticlesApply(
            "minecraft:flame",
            "minecraft:block.amethyst_block.chime",
            0.5f
    );

    public static MinecraftParticle endRodParticles = simpleMinecraftParticlesApply(
            "minecraft:end_rod",
            "minecraft:block.amethyst_block.chime",
            1f
    );

    public static MinecraftParticle glowParticles = simpleMinecraftParticlesApply(
            "minecraft:glow",
            "minecraft:block.amethyst_block.chime",
            1.2f
    );

    public static Effect defaultParticles = new Effect(
            Optional.of(endRodParticles),
            Optional.empty()
    );

    public static Effect defaultGlowParticles = new Effect(
            Optional.of(glowParticles),
            Optional.empty()
    );

    public static MinecraftParticle defaultConfigMCParticles(String id) {
        return new MinecraftParticle(
                Optional.empty(),
                Optional.of(id),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(1f),
                Optional.empty()
        );
    }

    public static MinecraftParticle simpleMinecraftParticles(String id_apply, String id_revert, String sound_apply, String sound_revert) {
        return new MinecraftParticle(
                Optional.of(id_apply),
                Optional.of(id_revert),
                Optional.of(sound_apply),
                Optional.of(sound_revert),
                Optional.of(1.0f),
                Optional.of(1.0f),
                Optional.of(new AnimationData(List.of("cry"), List.of(), List.of(), List.of(), 0, 0))
        );
    }

    public static MinecraftParticle simpleMinecraftParticlesApply(String id_apply, String sound_apply, float scale) {
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

    public static SnowStormParticle simpleSnowstormParticles(String id, float dur, String sound_id) {
        return new SnowStormParticle(
                Optional.of(List.of("target")),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(id),
                Optional.of(dur),
                Optional.empty(),
                Optional.empty(),
                Optional.of(sound_id),
                Optional.empty(),
                Optional.of(new AnimationData(List.of("cry"), List.of(), List.of(), List.of(), 0, 0))
        );
    }

    public static SnowStormParticle zmoveSnowStorm(String id, float dur, String sound_id) {
        return new SnowStormParticle(
                Optional.of(List.of("target")),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(id),
                Optional.of(dur),
                Optional.empty(),
                Optional.empty(),
                Optional.of(sound_id),
                Optional.empty(),
                Optional.of(new AnimationData(List.of("cry"), List.of(), List.of(), List.of(), 2.5f, 0))
        );
    }
}
