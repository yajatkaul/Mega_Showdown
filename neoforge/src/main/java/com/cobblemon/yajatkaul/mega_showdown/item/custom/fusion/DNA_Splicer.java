package com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.PokemonRef;
import com.cobblemon.yajatkaul.mega_showdown.event.cobbleEvents.CobbleEventsHandler;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DNA_Splicer extends Item {
    public DNA_Splicer(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack arg, Player player, @NotNull LivingEntity conComponent, InteractionHand hand) {
        if (player.level().isClientSide) {
            return InteractionResult.PASS;
        }

        if (!(conComponent instanceof PokemonEntity pk)) {
            return InteractionResult.PASS;
        }

        Pokemon pokemon = pk.getPokemon();
        if (pokemon.getOwnerPlayer() != player) {
            return InteractionResult.PASS;
        }

        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);
        PokemonRef refValue = arg.getOrDefault(DataManage.KYUREM_DATA, null);
        Pokemon currentValue;

        if(refValue == null){
            currentValue = null;
        }else{
            currentValue = refValue.getPokemon();
        }

        if(pokemon.getSpecies().getName().equals("Kyurem") && checkEnabled(pokemon)){
            if(arg.get(DataManage.KYUREM_DATA) != null){
                player.displayClientMessage(Component.literal("Already fused!")
                        .withColor(0xFF0000), true);
                return InteractionResult.PASS;
            }
            particleEffect(pk, ParticleTypes.ASH);
            new FlagSpeciesFeature("white", false).apply(pokemon);
            new FlagSpeciesFeature("black", false).apply(pokemon);
            pokemon.setTradeable(true);

            if(!pokemon.getEntity().hasData(DataManage.KYUREM_FUSED_WITH)){
                HashMap<UUID, Pokemon> map = player.getData(DataManage.DATA_MAP);
                Pokemon toAdd = map.get(pokemon.getUuid());
                playerPartyStore.add(toAdd);
                map.remove(pokemon.getUuid());
                player.setData(DataManage.DATA_MAP, map);
            }else{
                playerPartyStore.add(pokemon.getEntity().getData(DataManage.KYUREM_FUSED_WITH));
                pokemon.getEntity().removeData(DataManage.KYUREM_FUSED_WITH);
            }
            arg.set(DataManage.KYUREM_DATA, null);
            arg.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.dna_splicer.inactive"));
        }else if (currentValue != null && pokemon.getSpecies().getName().equals("Kyurem")) {
            if(currentValue.getSpecies().getName().equals("Reshiram")){
                particleEffect(pk, ParticleTypes.END_ROD);
                new FlagSpeciesFeature("white", true).apply(pokemon);
            }else{
                particleEffect(pk, ParticleTypes.SMOKE);
                new FlagSpeciesFeature("black", true).apply(pokemon);
            }
            pokemon.setTradeable(false);

            pokemon.getEntity().setData(DataManage.KYUREM_FUSED_WITH, currentValue);

            HashMap<UUID, Pokemon> map = player.getData(DataManage.DATA_MAP);
            map.put(pokemon.getUuid(), currentValue);
            player.setData(DataManage.DATA_MAP, map);

            arg.set(DataManage.KYUREM_DATA, null);
            AdvancementHelper.grantAdvancement((ServerPlayer) player, "fusion");
            arg.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.dna_splicer.inactive"));
        } else if (currentValue == null && pokemon.getSpecies().getName().equals("Reshiram")) {
            arg.set(DataManage.KYUREM_DATA, new PokemonRef(pk.getPokemon()));
            playerPartyStore.remove(pk.getPokemon());
            arg.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.dna_splicer.charged"));
        }else if (currentValue == null && pokemon.getSpecies().getName().equals("Zekrom")) {
            arg.set(DataManage.KYUREM_DATA, new PokemonRef(pk.getPokemon()));
            playerPartyStore.remove(pk.getPokemon());
            arg.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.dna_splicer.charged"));
        } else {
            return InteractionResult.PASS;
        }

        player.setItemInHand(hand, arg);
        player.getInventory().setChanged();
        return InteractionResult.SUCCESS;
    }

    private boolean checkEnabled(Pokemon pokemon){
        FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of("black"));
        FlagSpeciesFeature feature = featureProvider.get(pokemon);

        if(feature != null){
            boolean enabled = featureProvider.get(pokemon).getEnabled();

            if(enabled) {
                return true;
            }
        }

        featureProvider = new FlagSpeciesFeatureProvider(List.of("white"));
        feature = featureProvider.get(pokemon);

        if(feature != null){
            boolean enabled = featureProvider.get(pokemon).getEnabled();

            if(enabled){
                return true;
            }
        }

        return false;
    }

    public static void particleEffect(LivingEntity conComponent, SimpleParticleType particleType) {
        if (conComponent.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = conComponent.position(); // Get entity position

            // Get entity's size
            double entityWidth = conComponent.getBbWidth();
            double entityHeight = conComponent.getBbHeight();
            double entityDepth = entityWidth; // Usually same as width for most mobs

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 1.2; // Adjust this for more spread
            double adjustedWidth = entityWidth * scaleFactor;
            double adjustedHeight = entityHeight * scaleFactor;
            double adjustedDepth = entityDepth * scaleFactor;

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.AMETHYST_BLOCK_CHIME, // Change this if needed
                    SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (175 * adjustedWidth * adjustedHeight); // Scale particle amount

            for (int i = 0; i < particleCount; i++) {
                double xOffset = (Math.random() - 0.5) * adjustedWidth; // Random X within slightly expanded bounding box
                double yOffset = Math.random() * adjustedHeight; // Random Y within slightly expanded bounding box
                double zOffset = (Math.random() - 0.5) * adjustedDepth; // Random Z within slightly expanded bounding box

                serverLevel.sendParticles(
                        particleType,
                        entityPos.x + xOffset,
                        entityPos.y + yOffset,
                        entityPos.z + zOffset,
                        1, // One particle per call for better spread
                        0, 0, 0, // No movement velocity
                        0.1 // Slight motion
                );
            }
        }
    }

    @Override
    public void onDestroyed(ItemEntity entity, DamageSource damageSource) {
        if(entity.getOwner() instanceof ServerPlayer player){
            PokemonRef refValue = entity.getItem().getOrDefault(DataManage.KYUREM_DATA, null);
            Pokemon currentValue;

            if(refValue == null){
                currentValue = null;
            }else{
                currentValue = refValue.getPokemon();
            }

            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

            if(currentValue != null){
                playerPartyStore.add(currentValue);
                entity.getItem().set(DataManage.KYUREM_DATA, null);
            }
        }

        super.onDestroyed(entity, damageSource);
    }
}
