package com.github.yajatkaul.mega_showdown.gimmick;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.gimmick.codec.AspectSetCodec;
import com.github.yajatkaul.mega_showdown.status.MegaShowdownStatusEffects;
import com.github.yajatkaul.mega_showdown.utils.DelayedTicker;
import com.github.yajatkaul.mega_showdown.utils.GlowHandler;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

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

    public static void scaleDownDynamax(PokemonEntity pokemonEntity) {
        DelayedTicker.add(new DelayedTicker(MegaShowdownConfig.getDynamaxScaleDuration()) {
            @Override
            protected void function() {
                if (!pokemonEntity.isRemoved() && pokemonEntity.hasEffect(MegaShowdownStatusEffects.DYNAMAX)) {
                    pokemonEntity.removeEffect(MegaShowdownStatusEffects.DYNAMAX);
                    pokemonEntity.addEffect(
                            new MobEffectInstance(
                                    MegaShowdownStatusEffects.DYNAMAX,
                                    Integer.MAX_VALUE,
                                    (this.maxAge - this.age),
                                    true,
                                    true,
                                    true
                            ),
                            null
                    );
                } else {
                    this.age = this.maxAge;
                }
                if (this.age == this.maxAge) {
                    pokemonEntity.removeEffect(MobEffects.GLOWING);
                    pokemonEntity.removeEffect(MegaShowdownStatusEffects.DYNAMAX);
                }
            }
        });
    }

    public static void scaleUpDynamax(PokemonEntity pokemonEntity) {
        GlowHandler.applyDynamaxGlow(pokemonEntity);
        DelayedTicker.add(new DelayedTicker(MegaShowdownConfig.getDynamaxScaleDuration()) {
            @Override
            protected void function() {
                if (!pokemonEntity.isRemoved()) {
                    pokemonEntity.addEffect(
                            new MobEffectInstance(
                                    MegaShowdownStatusEffects.DYNAMAX,
                                    Integer.MAX_VALUE,
                                    this.age,
                                    true,
                                    true,
                                    true
                            )
                    );
                } else {
                    this.age = this.maxAge;
                }
            }
        });
    }
}