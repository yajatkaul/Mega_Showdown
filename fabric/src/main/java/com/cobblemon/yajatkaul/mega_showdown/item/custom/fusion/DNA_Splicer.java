package com.cobblemon.yajatkaul.mega_showdown.item.custom.fusion;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.PokeHandler;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.ItemEntity;
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
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class DNA_Splicer extends Item {
    public DNA_Splicer(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack arg, PlayerEntity player, LivingEntity context, Hand hand) {
        if (player.getWorld().isClient || player.isCrawling()) {
            return ActionResult.PASS;
        }

        if (!(context instanceof PokemonEntity pk)) {
            return ActionResult.PASS;
        }

        Pokemon pokemon = pk.getPokemon();
        if (pokemon.getOwnerPlayer() != player || pokemon.getEntity() == null) {
            return ActionResult.PASS;
        }

        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);
        Pokemon currentValue = arg.getOrDefault(DataManage.KYUREM_DATA, null);

        if(pokemon.getSpecies().getName().equals("Kyurem") && checkEnabled(pokemon)){
            if(arg.get(DataManage.KYUREM_DATA) != null){
                player.sendMessage(
                        Text.translatable("message.mega_showdown.already_fused").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
                return ActionResult.PASS;
            }
            particleEffect(pk, ParticleTypes.ASH);
            new FlagSpeciesFeature("white", false).apply(pokemon);
            new FlagSpeciesFeature("black", false).apply(pokemon);
            setTradable(pokemon, true);

            if(!pokemon.getEntity().hasAttached(DataManage.KYUREM_FUSED_WITH)){
                HashMap<UUID, Pokemon> map = player.getAttached(DataManage.DATA_MAP);
                Pokemon toAdd = map.get(pokemon.getUuid());
                playerPartyStore.add(toAdd);
                map.remove(pokemon.getUuid());
                player.setAttached(DataManage.DATA_MAP, map);
            }else{
                playerPartyStore.add(pokemon.getEntity().getAttached(DataManage.KYUREM_FUSED_WITH).getPokemon());
                pokemon.getEntity().removeAttached(DataManage.KYUREM_FUSED_WITH);
            }

            arg.set(DataManage.KYUREM_DATA, null);
            arg.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.dna_splicer.inactive"));
        }else if (currentValue != null && pokemon.getSpecies().getName().equals("Kyurem")) {
            if(currentValue.getSpecies().getName().equals("Reshiram")){
                particleEffect(pk, ParticleTypes.END_ROD);
                new FlagSpeciesFeature("white", true).apply(pokemon);
            }else{
                particleEffect(pk, ParticleTypes.SMOKE);
                new FlagSpeciesFeature("black", true).apply(pokemon);
            }
            setTradable(pokemon, false);

            pokemon.getEntity().setAttached(DataManage.KYUREM_FUSED_WITH, new PokeHandler(currentValue));

            HashMap<UUID, Pokemon> map = player.getAttached(DataManage.DATA_MAP);
            if(map == null){
                map = new HashMap<>();
            }
            map.put(pokemon.getUuid(), currentValue);
            player.setAttached(DataManage.DATA_MAP, map);

            arg.set(DataManage.KYUREM_DATA, null);
            arg.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.dna_splicer.inactive"));
        } else if (currentValue == null && pokemon.getSpecies().getName().equals("Reshiram")) {
            arg.set(DataManage.KYUREM_DATA, pk.getPokemon());
            playerPartyStore.remove(pk.getPokemon());
            arg.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.dna_splicer.charged"));
        }else if (currentValue == null && pokemon.getSpecies().getName().equals("Zekrom")) {
            arg.set(DataManage.KYUREM_DATA, pk.getPokemon());
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

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        if(entity.getOwner() instanceof ServerPlayerEntity player){
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
            Pokemon currentValue = entity.getStack().getOrDefault(DataManage.KYUREM_DATA, null);

            if(currentValue != null){
                playerPartyStore.add(currentValue);
                entity.getStack().set(DataManage.KYUREM_DATA, null);
            }
        }

        super.onItemEntityDestroyed(entity);
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
