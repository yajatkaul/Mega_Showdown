package com.cobblemon.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.battles.ShowdownActionRequest;
import com.cobblemon.mod.common.battles.ShowdownMoveset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = ShowdownActionRequest.class, remap = false)
public interface ShowdownActionRequestAccessor {
    @Accessor("active")
    List<ShowdownMoveset> getActive();
}