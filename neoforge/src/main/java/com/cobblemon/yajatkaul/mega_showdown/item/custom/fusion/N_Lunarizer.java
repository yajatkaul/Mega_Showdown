package com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonEntities;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.PokemonRef;
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
import java.util.Set;
import java.util.UUID;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;


public class N_Lunarizer extends Item {
    public N_Lunarizer(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack arg, Player player, @NotNull LivingEntity context, InteractionHand hand) {
        if (player.level().isClientSide) {
            return InteractionResult.PASS;
        }

        if (!(context instanceof PokemonEntity pk)) {
            return InteractionResult.PASS;
        }

        Pokemon pokemon = pk.getPokemon();
        if (pokemon.getOwnerPlayer() != player) {
            return InteractionResult.PASS;
        }

        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);
        PokemonRef refValue = arg.getOrDefault(DataManage.N_LUNAR, null);
        Pokemon currentValue;

        if(refValue == null){
            currentValue = null;
        }else{
            currentValue = refValue.getPokemon();
        }

        if (currentValue != null && pokemon.getSpecies().getName().equals("Necrozma")) {
            if (checkFused(pokemon)){
                player.displayClientMessage(Component.literal("Already fused!")
                        .withColor(0xFF0000), true);
                return InteractionResult.PASS;
            }

            HashMap<UUID, Pokemon> map = player.getData(DataManage.DATA_MAP);
            map.put(pokemon.getUuid(), currentValue);
            player.setData(DataManage.DATA_MAP, map);

            pk.setData(DataManage.N_LUNAR_POKEMON, currentValue);
            arg.set(DataManage.N_LUNAR, null);
            new FlagSpeciesFeature("dawn-fusion", true).apply(pokemon);
            particleEffect(pokemon.getEntity());
            setTradable(pokemon, false);

            AdvancementHelper.grantAdvancement((ServerPlayer) player, "fusion");
            arg.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.n_lunarizer.inactive"));
        } else if (currentValue == null && pokemon.getSpecies().getName().equals("Lunala")) {
            arg.set(DataManage.N_LUNAR, new PokemonRef(pokemon));
            playerPartyStore.remove(pokemon);
            arg.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.n_lunarizer.charged"));
        } else if (pokemon.getSpecies().getName().equals("Necrozma") && checkEnabled(pokemon)) {
            FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of("ultra"));
            FlagSpeciesFeature feature = featureProvider.get(pokemon);

            if(feature != null){
                boolean enabled = featureProvider.get(pokemon).getEnabled();

                if(enabled) {
                    return InteractionResult.PASS;
                }
            }

            if(!pokemon.getEntity().hasData(DataManage.N_LUNAR_POKEMON)){
                HashMap<UUID, Pokemon> map = player.getData(DataManage.DATA_MAP);
                Pokemon toAdd = map.get(pokemon.getUuid());
                playerPartyStore.add(toAdd);
                map.remove(pokemon.getUuid());
                player.setData(DataManage.DATA_MAP, map);
            }else{
                playerPartyStore.add(pokemon.getEntity().getData(DataManage.N_LUNAR_POKEMON));
                pk.removeData(DataManage.N_LUNAR_POKEMON);
            }

            new FlagSpeciesFeature("dawn-fusion", false).apply(pokemon);
            particleEffect(pokemon.getEntity());
            setTradable(pokemon, true);
            arg.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.n_lunarizer.inactive"));
        } else {
            return InteractionResult.PASS;
        }

        player.setItemInHand(hand, arg);
        player.getInventory().setChanged();
        return InteractionResult.SUCCESS;
    }

    private boolean checkEnabled(Pokemon pokemon){
        FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of("dawn-fusion"));
        FlagSpeciesFeature feature = featureProvider.get(pokemon);

        if(feature != null){
            return featureProvider.get(pokemon).getEnabled();
        }
        return false;
    }

    private boolean checkFused(Pokemon pokemon){
        FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of("dusk-fusion"));
        FlagSpeciesFeature feature = featureProvider.get(pokemon);

        if(feature != null){
            boolean enabled = featureProvider.get(pokemon).getEnabled();

            if(enabled){
                return true;
            }
        }

        featureProvider = new FlagSpeciesFeatureProvider(List.of("dawn-fusion"));
        feature = featureProvider.get(pokemon);

        if(feature != null){
            boolean enabled = featureProvider.get(pokemon).getEnabled();

            if(enabled){
                return true;
            }
        }

        return false;
    }

    @Override
    public void onDestroyed(ItemEntity entity, DamageSource damageSource) {
        if(entity.getOwner() instanceof ServerPlayer player){
            PokemonRef refValue = entity.getItem().getOrDefault(DataManage.N_LUNAR, null);
            Pokemon currentValue;

            if(refValue == null){
                currentValue = null;
            }else{
                currentValue = refValue.getPokemon();
            }

            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

            if(currentValue != null){
                playerPartyStore.add(currentValue);
                entity.getItem().set(DataManage.N_LUNAR, null);
            }
        }

        super.onDestroyed(entity, damageSource);
    }

    public static void particleEffect(LivingEntity conComponent) {
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
                        ParticleTypes.ASH,
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
}
