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
                    Optional.of(1),
                    Optional.empty(),
                    Optional.of(new AnimationData(List.of("cry"), List.of(), List.of(), List.of()))
            )),
            Optional.empty()
    );

    public static Effect kyogrePrimalRevert = new Effect(
            Optional.of(new MinecraftParticle(
                    Optional.of("minecraft:bubble"),
                    Optional.of("minecraft:end_rod"),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of(1),
                    Optional.empty(),
                    Optional.of(new AnimationData(List.of("cry"), List.of(), List.of(), List.of()))
            )),
            Optional.empty()
    );

    public static Effect megaEvolutionRevert = new Effect(
            Optional.of(defaultConfigMCParticles("minecraft:end_rod")),
            Optional.of(simpleSnowstormParticles("cobblemon:mega_evolution",
                    4.7f,
                    "mega_showdown:mega")
            )
    );

    public static Effect kyuremBlackFusion = new Effect(
            Optional.of(defaultConfigMCParticles("minecraft:ash")),
            Optional.of(simpleSnowstormParticles("cobblemon:kyurem_b_effect",
                    4f,
                    "mega_showdown:kyurem_fusion")
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

    public static MinecraftParticle defaultConfigMCParticles(String id) {
        return new MinecraftParticle(
                Optional.empty(),
                Optional.of(id),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(1),
                Optional.empty()
        );
    }

    public static MinecraftParticle simpleMinecraftParticles(String id_apply, String id_revert, String sound_apply, String sound_revert) {
        return new MinecraftParticle(
                Optional.of(id_apply),
                Optional.of(id_revert),
                Optional.of(sound_apply),
                Optional.of(sound_revert),
                Optional.of(1),
                Optional.of(1),
                Optional.of(new AnimationData(List.of("cry"), List.of(), List.of(), List.of()))
        );
    }

    public static SnowStormParticle simpleSnowstormParticles(String id, float dur, String sound_id) {
        return new SnowStormParticle(
                Optional.of(List.of("root")),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(id),
                Optional.of(dur),
                Optional.empty(),
                Optional.empty(),
                Optional.of(sound_id),
                Optional.empty(),
                Optional.of(new AnimationData(List.of("cry"), List.of(), List.of(), List.of()))
        );
    }
}
