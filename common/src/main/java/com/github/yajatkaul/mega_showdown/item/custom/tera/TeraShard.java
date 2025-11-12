package com.github.yajatkaul.mega_showdown.item.custom.tera;

import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import com.github.yajatkaul.mega_showdown.utils.ParticlesList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class TeraShard extends Item {
    private final TeraType teraType;

    public TeraShard(Properties arg, TeraType teraType) {
        super(arg);
        this.teraType = teraType;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, @NotNull LivingEntity context, InteractionHand hand) {
        if (player.level().isClientSide || player.isCrouching()) {
            return InteractionResult.PASS;
        }

        if (context instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();
            if (pokemon.getEntity() == null || pokemon.getEntity().isBattling() || pokemon.getOwnerPlayer() != player) {
                return InteractionResult.PASS;
            }

            if (pokemon.getSpecies().getName().equals("Ogerpon")
                    || pokemon.getSpecies().getName().equals("Terapagos")) {
                return InteractionResult.PASS;
            }

            final int required_shards = MegaShowdownConfig.teraShardRequired;

            if (itemStack.getCount() >= required_shards) {
                if (pokemon.getTeraType() == teraType) {
                    player.displayClientMessage(Component.translatable("message.mega_showdown.same_tera")
                            .withColor(0xFF0000), true);
                    return InteractionResult.PASS;
                }
                itemStack.consume(required_shards, player);

                if (itemStack.getItem() == MegaShowdownItems.STELLAR_TERA_SHARD.get()) {
                    AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "tera/change_tera_stellar");
                }

                ParticlesList.glowParticles.apply(pokemonEntity);
                pokemon.setTeraType(teraType);
                pokemon.setTeraType(teraType);
                AdvancementHelper.grantAdvancement(pokemon.getOwnerPlayer(), "tera/change_tera");
            } else {
                player.displayClientMessage(Component.translatable("message.mega_showdown.tera_requirements", required_shards)
                        .withColor(0xFF0000), true);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
