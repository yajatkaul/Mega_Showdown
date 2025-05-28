package com.cobblemon.yajatkaul.mega_showdown.utility.datapack

import com.cobblemon.mod.common.api.abilities.AbilityTemplate

object NewAbility {
    fun getAbility(id: String): AbilityTemplate {
        return AbilityTemplate(id)
    }
}