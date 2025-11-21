package com.github.yajatkaul.mega_showdown.battle.effect;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.resources.ResourceLocation;

public record EffectWrapper(int tickInterval, ResourceLocation effectId) {
    public static final EffectWrapper STEALTH_ROCKS = new EffectWrapper(20, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "hazard_stealth_rock"));
    public static final EffectWrapper STICKY_WEB = new EffectWrapper(100, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "hazard_sticky_web"));
    public static final EffectWrapper SPIKES = new EffectWrapper(60, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "hazard_spikes"));
    public static final EffectWrapper TOXIC_SPIKES = new EffectWrapper(60, ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "hazard_toxic_spikes"));
}
