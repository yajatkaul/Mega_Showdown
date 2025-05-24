package com.cobblemon.yajatkaul.mega_showdown.utility.datapack

import com.cobblemon.mod.common.api.abilities.AbilityTemplate

object NewAbility {
    fun getAbility(name: String): AbilityTemplate{
        return AbilityTemplate(name);
    }
}