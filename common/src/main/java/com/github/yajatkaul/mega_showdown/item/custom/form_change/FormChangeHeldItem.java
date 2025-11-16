package com.github.yajatkaul.mega_showdown.item.custom.form_change;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipItem;
import com.github.yajatkaul.mega_showdown.utils.Effect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class FormChangeHeldItem extends ToolTipItem {
    private final String revertAspect;
    private final String applyAspect;
    private final List<String> pokemons;
    private final Effect effect;
    private final boolean tradable;
    private final boolean thunder;

    public FormChangeHeldItem(Properties properties, String revertAspect, String applyAspect, List<String> pokemons, Effect effect, boolean tradable, boolean thunder) {
        super(properties);
        this.revertAspect = revertAspect;
        this.applyAspect = applyAspect;
        this.pokemons = pokemons;
        this.effect = effect;
        this.tradable = tradable;
        this.thunder = thunder;
    }

    public void apply(Pokemon pokemon) {
        if (pokemons.contains(pokemon.getSpecies().getName())) {
            if (thunder) {
                Level level = pokemon.getEntity().level();
                LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
                if (lightning != null) {
                    lightning.moveTo(Vec3.atBottomCenterOf(pokemon.getEntity().blockPosition()));
                    lightning.setVisualOnly(true);
                    level.addFreshEntity(lightning);
                }
            }
            effect.applyEffects(pokemon.getEntity(), List.of(applyAspect), null);
            if (!tradable) {
                pokemon.setTradeable(false);
            }
        }
    }

    public void revert(Pokemon pokemon) {
        if (pokemons.contains(pokemon.getSpecies().getName())) {
            effect.revertEffects(pokemon.getEntity(), List.of(revertAspect), null);
            if (!tradable) {
                pokemon.setTradeable(true);
            }
        }
    }
}
