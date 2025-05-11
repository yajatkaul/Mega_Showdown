package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.PokeHandler;
import com.cobblemon.yajatkaul.mega_showdown.item.inventory.ItemInventoryUtil;
import com.cobblemon.yajatkaul.mega_showdown.screen.custom.ZygardeCubeMenu;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ZygardeCube extends Item {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 2000; // 2 sec

    public static boolean possible(ServerPlayer player) {
        UUID playerId = player.getUUID();
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId) && currentTime < cooldowns.get(playerId)) {
            player.displayClientMessage(Component.translatable("message.mega_showdown.not_so_fast")
                    .withColor(0xFF0000), true);
            return false;
        }

        // Apply cooldown
        cooldowns.put(playerId, currentTime + COOLDOWN_TIME);
        return true;
    }

    public ZygardeCube(Properties arg) {
        super(arg);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide || hand == InteractionHand.OFF_HAND) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }

        EntityHitResult entityHit = getEntityLookingAt(player, 4.5f);
        if (entityHit != null) {
            Entity entity = entityHit.getEntity();
            if (entity instanceof PokemonEntity pk) {
                if (pk.getPokemon().getSpecies().getName().equals("Zygarde")) {
                    return InteractionResultHolder.pass(player.getItemInHand(hand));
                }
            }
        }

        ItemStack stack = player.getItemInHand(hand);
        ItemInventoryUtil inventory = new ItemInventoryUtil(stack, player);

        CompoundTag capTag = stack.get(DataManage.ZYGARDE_INV);
        HolderLookup.Provider provider = level.registryAccess();

        ItemStackHandler handler = inventory.getHandler();

        // Deserialize saved inventory if it exists
        if (capTag != null) {
            handler.deserializeNBT(provider, capTag);
        }

        // Serialize current state to send to client
        CompoundTag serializedTag = handler.serializeNBT(provider);

        player.openMenu(
                new SimpleMenuProvider(
                        (id, playerInventory, playerEntity) ->
                                new ZygardeCubeMenu(id, playerInventory, handler),
                        Component.translatable("menu.zygade_cube")
                ),
                buf -> buf.writeNbt(serializedTag)
        );

        return InteractionResultHolder.success(stack);
    }

    public static EntityHitResult getEntityLookingAt(Player player, float distance) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 targetPos = eyePos.add(lookVec.scale(distance));

        AABB rayTraceBox = new AABB(eyePos, targetPos);

        return ProjectileUtil.getEntityHitResult(
                player.level(),
                player,
                eyePos,
                targetPos,
                rayTraceBox, // Use the ray trace box directly
                entity -> !entity.isSpectator() && entity instanceof LivingEntity && entity.isPickable(),
                0.3f // Smaller collision expansion value for more precise detection
        );
    }

    public ItemStackHandler getInventory(ItemStack stack, Level level, Player player){
        CompoundTag capTag = stack.get(DataManage.ZYGARDE_INV);
        HolderLookup.Provider provider = level.registryAccess();

        ItemInventoryUtil inventory = new ItemInventoryUtil(stack, player);

        ItemStackHandler handler = inventory.getHandler();


        // Deserialize saved inventory if it exists
        if (capTag != null) {
            handler.deserializeNBT(provider, capTag);
        }

        return handler;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack arg, Player player, LivingEntity entity, InteractionHand arg4) {
        if(player.level().isClientSide || player.isCrouching()){
            return InteractionResult.FAIL;
        }

        if(entity instanceof PokemonEntity pk){
            Pokemon pokemon = pk.getPokemon();
            if(pokemon.getEntity() == null || pokemon.getEntity().level().isClientSide || pokemon.getEntity().isBattling()){
                return InteractionResult.PASS;
            }

            if(!pokemon.getSpecies().getName().equals("Zygarde")){
                return InteractionResult.PASS;
            }

            if(arg4 == InteractionHand.OFF_HAND && !pk.getAspects().contains("power-construct")){
                if(arg.get(DataManage.ZYGARDE_CUBE_DATA) != null){
                    player.displayClientMessage(Component.translatable("message.mega_showdown.cube_full")
                            .withColor(0xFF0000), true);
                    return InteractionResult.FAIL;
                }
                arg.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.zygarde_cube.full"));
                arg.set(DataManage.ZYGARDE_CUBE_DATA, new PokeHandler(pokemon));
                Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player).remove(pokemon);
                player.setItemInHand(arg4, arg);
                return InteractionResult.SUCCESS;
            }

            if(pk.getAspects().contains("power-construct")){
                if(!possible((ServerPlayer) player)){
                    return InteractionResult.PASS;
                } else if(pk.getAspects().contains("10-percent")){
                    particleEffect(pokemon.getEntity());
                    new StringSpeciesFeature("percent_cells","50").apply(pk);
                }else{
                    particleEffect(pokemon.getEntity());
                    new StringSpeciesFeature("percent_cells","10").apply(pk);
                }
            }else {
                player.displayClientMessage(Component.translatable("message.mega_showdown.resassembly_zygarde_req")
                        .withColor(0xFF0000), true);
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    private void particleEffect(LivingEntity context) {
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();
            double entityDepth = entityWidth; // Usually same as width for most mobs

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 0.8; // Adjust this for more spread
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
                        ParticleTypes.GLOW,
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
            PokeHandler refValue = entity.getItem().getOrDefault(DataManage.ZYGARDE_CUBE_DATA, null);
            Pokemon currentValue;

            if(refValue == null){
                currentValue = null;
            }else{
                currentValue = refValue.getPokemon();
            }

            PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty(player);

            if(currentValue != null){
                playerPartyStore.add(currentValue);
                entity.getItem().set(DataManage.ZYGARDE_CUBE_DATA, null);
            }
        }

        super.onDestroyed(entity, damageSource);
    }
}
