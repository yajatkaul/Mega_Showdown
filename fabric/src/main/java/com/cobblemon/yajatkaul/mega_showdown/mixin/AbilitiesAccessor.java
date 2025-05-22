package com.cobblemon.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.api.abilities.Abilities;
import com.cobblemon.mod.common.api.abilities.AbilityTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = Abilities.class, remap = false)
public interface AbilitiesAccessor {
    @Accessor("abilityMap")
    static Map<String, AbilityTemplate> getAllAbilities() {
        throw new IllegalStateException("Mixin failed to apply");
    }
}
