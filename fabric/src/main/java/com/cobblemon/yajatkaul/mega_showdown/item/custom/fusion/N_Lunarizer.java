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

public class N_Lunarizer extends Item {
    public N_Lunarizer(Settings settings) {
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
        if (pokemon.getOwnerPlayer() != player) {
            return ActionResult.PASS;
        }

        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);
        Pokemon currentValue = arg.getOrDefault(DataManage.POKEMON_STORAGE, null);

        if (currentValue != null && pokemon.getSpecies().getName().equals("Necrozma")) {
            if (checkFused(pokemon)){
                player.sendMessage(
                        Text.translatable("message.mega_showdown.already_fused").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                        true
                );
                return ActionResult.PASS;
            }

            HashMap<UUID, Pokemon> map = player.getAttached(DataManage.DATA_MAP);
            if(map == null){
                map = new HashMap<>();
            }
            map.put(pokemon.getUuid(), currentValue);
            player.setAttached(DataManage.DATA_MAP, map);

            pk.setAttached(DataManage.N_LUNAR_POKEMON, new PokeHandler(currentValue));
            arg.set(DataManage.POKEMON_STORAGE, null);
            new FlagSpeciesFeature("dawn-fusion", true).apply(pokemon);
            particleEffect(pokemon.getEntity());
            setTradable(pokemon, false);

            arg.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.n_lunarizer.inactive"));
        } else if (currentValue == null && pokemon.getSpecies().getName().equals("Lunala")) {
            arg.set(DataManage.POKEMON_STORAGE, pokemon);
            playerPartyStore.remove(pokemon);
            arg.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.n_lunarizer.charged"));
        } else if (pokemon.getSpecies().getName().equals("Necrozma") && checkEnabled(pokemon)) {
            FlagSpeciesFeatureProvider featureProvider = new FlagSpeciesFeatureProvider(List.of("ultra"));
            FlagSpeciesFeature feature = featureProvider.get(pokemon);

            if(feature != null){
                boolean enabled = featureProvider.get(pokemon).getEnabled();

                if(enabled) {
                    return ActionResult.PASS;
                }
            }

            if(!pokemon.getEntity().hasAttached(DataManage.N_LUNAR_POKEMON)){
                HashMap<UUID, Pokemon> map = player.getAttached(DataManage.DATA_MAP);
                Pokemon toAdd = map.get(pokemon.getUuid());
                playerPartyStore.add(toAdd);
                map.remove(pokemon.getUuid());
                player.setAttached(DataManage.DATA_MAP, map);
            }else{
                playerPartyStore.add(pokemon.getEntity().getAttached(DataManage.N_LUNAR_POKEMON).getPokemon());
                pk.removeAttached(DataManage.N_LUNAR_POKEMON);
            }

            new FlagSpeciesFeature("dawn-fusion", false).apply(pokemon);
            particleEffect(pokemon.getEntity());
            setTradable(pokemon, true);
            arg.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.n_lunarizer.inactive"));
        } else {
            return ActionResult.PASS;
        }

        player.setStackInHand(hand, arg);
        return ActionResult.SUCCESS;
    }

    private boolean checkEnabled(Pokemon pokemon){
        return pokemon.getAspects().contains("dawn-fusion");
    }

    private boolean checkFused(Pokemon pokemon){
        return pokemon.getAspects().contains("dusk-fusion") || pokemon.getAspects().contains("dawn-fusion");
    }

    @Override
    public void onItemEntityDestroyed(ItemEntity entity) {
        if(entity.getOwner() instanceof ServerPlayerEntity player){
            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);
            Pokemon currentValue = entity.getStack().getOrDefault(DataManage.POKEMON_STORAGE, null);

            if(currentValue != null){
                playerPartyStore.add(currentValue);
                entity.getStack().set(DataManage.POKEMON_STORAGE, null);
            }
        }

        super.onItemEntityDestroyed(entity);
    }

    public static void particleEffect(LivingEntity context) {
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
