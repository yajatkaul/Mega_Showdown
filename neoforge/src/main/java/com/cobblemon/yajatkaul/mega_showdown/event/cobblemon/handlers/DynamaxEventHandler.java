package com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.handlers;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events.DynamaxEventEnd;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.events.DynamaxEventStart;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils.DynamaxUtils;
import com.cobblemon.yajatkaul.mega_showdown.event.cobblemon.utils.EventUtils;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.GlowHandler;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import kotlin.Unit;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;

import java.util.List;
import java.util.Set;

public class DynamaxEventHandler {

    @SubscribeEvent
    public void onDynamax(DynamaxEventStart event) {
        PokemonEntity entity = event.getPokemon().getEntity();
        Vec3 entityPos = entity.position();

        entity.level().playSound(
                null, entityPos.x, entityPos.y, entityPos.z,
                ModSounds.DYNAMAX.get(),
                SoundSource.PLAYERS, 0.4f, 0.5f + (float) Math.random() * 0.5f
        );

        if (entity == null) return;

        if (event.getGmax()) {
            new StringSpeciesFeature("dynamax_form", "gmax").apply(event.getPokemon().getEffectedPokemon());
            BattlePokemon pokemon = event.getPokemon();
            EventUtils.updatePackets(event.getBattle(), pokemon);
            AdvancementHelper.grantAdvancement(entity.getPokemon().getOwnerPlayer(), "dynamax/gigantamax");
        } else {
            AdvancementHelper.grantAdvancement(entity.getPokemon().getOwnerPlayer(), "dynamax/dynamax");
        }

        if (DynamaxUtils.server == null && entity.level() instanceof ServerLevel serverLevel) {
            DynamaxUtils.server = serverLevel.getServer();
        }

        DynamaxUtils.startGradualScaling(entity, MegaShowdownConfig.dynamaxScaleFactor);

        GlowHandler.applyDynamaxGlow(entity);

        event.getBattle().dispatchWaitingToFront(3F, () -> {
            SnowStormHandler.Companion.playAnimation(entity, Set.of("cry"), List.of());
            return Unit.INSTANCE;
        });
    }

    @SubscribeEvent
    public void onDynamaxEnd(DynamaxEventEnd event) {
        new StringSpeciesFeature("dynamax_form", "none").apply(event.getPokemon().getEffectedPokemon());
        BattlePokemon pokemon = event.getPokemon();

        EventUtils.updatePackets(event.getBattle(), pokemon);

        LivingEntity entity = event.getPokemon().getEntity();
        if (entity == null) return;

        entity.removeEffect(MobEffects.GLOWING);

        if (DynamaxUtils.server == null && entity.level() instanceof ServerLevel serverLevel) {
            DynamaxUtils.server = serverLevel.getServer();
        }

        DynamaxUtils.resetToDefaultSize(entity);
    }
}