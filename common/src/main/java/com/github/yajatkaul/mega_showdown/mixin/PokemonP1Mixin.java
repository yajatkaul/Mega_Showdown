package com.github.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.api.abilities.Abilities;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.util.codec.internal.PokemonP1;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = PokemonP1.class, remap = false)
public class PokemonP1Mixin {
    /**
     * @author YajatKaul
     * @reason Shiny fix
     */
    @Overwrite
    public @NotNull Pokemon into(Pokemon other) {
        PokemonP1 self = (PokemonP1) (Object) this;

        other.setUuid(self.getUuid());
        other.setAbility$common(self.getAbility());
        other.setSpecies(self.getSpecies());
        other.setForm(self.getForm());
        self.getNickname().ifPresent(n -> other.setNickname(n.copy()));
        other.setLevel(self.getLevel());
        other.setExperience$common(self.getExperience());
        ((PokemonAccessor) other).invokeSetFriendship(self.getFriendship());
        other.getIvs().doWithoutEmitting(() -> {
            self.getIvs().forEach((ivs) -> {
                other.getIvs().set(ivs.getKey(), ivs.getValue());

                if (self.getIvs().isHyperTrained(ivs.getKey())) {
                    Integer hyper = self.getIvs().getHyperTrainedIVs().get(ivs.getKey());
                    other.getIvs().setHyperTrainedIV(ivs.getKey(), hyper);
                }
            });
            return Unit.INSTANCE;
        });
        other.getEvs().doWithoutEmitting(() -> {
            self.getEvs().forEach((evs) -> {
                other.getEvs().set(evs.getKey(), evs.getValue());
            });
            return Unit.INSTANCE;
        });
        other.setCurrentHealth(self.getCurrentHealth());
        other.setGender(self.getGender());
        other.setShiny(self.getShiny());
        other.getMoveSet().copyFrom(self.getMoveSet());
        other.getBenchedMoves().copyFrom(self.getBenchedMoves());
        other.setScaleModifier(self.getScaleModifier());
        if (other.getAbility().getTemplate() == Abilities.INSTANCE.getDUMMY()) {
            other.rollAbility();
        }
        return other;
    }
}
