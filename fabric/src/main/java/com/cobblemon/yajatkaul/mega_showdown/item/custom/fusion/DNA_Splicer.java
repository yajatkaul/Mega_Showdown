package com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class DNA_Splicer extends Item {
    public DNA_Splicer(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack arg, PlayerEntity player, LivingEntity context, Hand hand) {
        if (player.getWorld().isClient) {
            return ActionResult.PASS;
        }

        if (!(context instanceof PokemonEntity pk)) {
            return ActionResult.PASS;
        }

        Pokemon pokemon = pk.getPokemon();
        if (pokemon.getOwnerPlayer() != player) {
            return ActionResult.PASS;
        }

        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);
        boolean currentValue = arg.getOrDefault(DataManage.KYUREM_DATA, false);

        if(pokemon.getSpecies().getName().equals("Kyurem") && checkEnabled(pokemon)){
            particleEffect(pk, ParticleTypes.ASH);
            new FlagSpeciesFeature("white", false).apply(pokemon);
            new FlagSpeciesFeature("black", false).apply(pokemon);
            playerPartyStore.add(player.getAttached(DataManage.KYUREM_FUSION));

            arg.set(DataManage.KYUREM_DATA, false);
            player.setAttached(DataManage.KYUREM_FUSION, new Pokemon());
            arg.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.dna_splicer.inactive"));
        }else if (currentValue && pokemon.getSpecies().getName().equals("Kyurem") && !player.getAttached(DataManage.KYUREM_FUSION).equals(new Pokemon())) {

            if(player.getAttached(DataManage.KYUREM_FUSION).getSpecies().getName().equals("Reshiram")){
                particleEffect(pk, ParticleTypes.END_ROD);
                new FlagSpeciesFeature("white", true).apply(pokemon);
            }else{
                particleEffect(pk, ParticleTypes.SMOKE);
                new FlagSpeciesFeature("black", true).apply(pokemon);
            }

            arg.set(DataManage.KYUREM_DATA, false);
            AdvancementHelper.grantAdvancement((ServerPlayerEntity) player, "fusion");
            arg.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.dna_splicer.inactive"));
        } else if (!currentValue && pokemon.getSpecies().getName().equals("Reshiram")) {
            arg.set(DataManage.KYUREM_DATA, true);
            player.setAttached(DataManage.KYUREM_FUSION, pk.getPokemon());
            playerPartyStore.remove(pk.getPokemon());
            arg.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.dna_splicer.charged"));
        }else if (!currentValue && pokemon.getSpecies().getName().equals("Zekrom")) {
            arg.set(DataManage.KYUREM_DATA, true);
            player.setAttached(DataManage.KYUREM_FUSION, pk.getPokemon());
            playerPartyStore.remove(pk.getPokemon());
            arg.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.dna_splicer.charged"));
        } else {
            return ActionResult.PASS;
        }

        player.setStackInHand(hand, arg);
        return ActionResult.SUCCESS;
    }

    private boolean checkEnabled(Pokemon pokemon){
        FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of("black"));
        FlagSpeciesFeature feature = featureProvider.get(pokemon);

        if(feature != null){
            boolean enabled = featureProvider.get(pokemon).getEnabled();

            if(enabled) {
                new FlagSpeciesFeature("black", false).apply(pokemon);
                return true;
            }
        }

        featureProvider = new FlagSpeciesFeatureProvider(List.of("white"));
        feature = featureProvider.get(pokemon);

        if(feature != null){
            boolean enabled = featureProvider.get(pokemon).getEnabled();

            if(enabled){
                new FlagSpeciesFeature("white", false).apply(pokemon);
                return true;
            }
        }

        return false;
    }

    public static void particleEffect(LivingEntity context, SimpleParticleType particleType) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();
            double entityDepth = entityWidth; // Usually same as width for most mobs

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 1.2; // Adjust this for more spread
            double adjustedWidth = entityWidth * scaleFactor;
            double adjustedHeight = entityHeight * scaleFactor;
            double adjustedDepth = entityDepth * scaleFactor;

            // Play sound effect
            serverWorld.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, // Change this if needed
                    SoundCategory.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (175 * adjustedWidth * adjustedHeight); // Scale particle amount

            for (int i = 0; i < particleCount; i++) {
                double xOffset = (Math.random() - 0.5) * adjustedWidth; // Random X within slightly expanded bounding box
                double yOffset = Math.random() * adjustedHeight; // Random Y within slightly expanded bounding box
                double zOffset = (Math.random() - 0.5) * adjustedDepth; // Random Z within slightly expanded bounding box

                serverWorld.spawnParticles(
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
}
