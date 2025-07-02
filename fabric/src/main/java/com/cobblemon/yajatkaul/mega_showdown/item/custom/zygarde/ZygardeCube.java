package com.cobblemon.yajatkaul.mega_showdown.item.custom.zygarde;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.dataAttachments.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.screen.custom.ZygardeCubeScreenHandler;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ZygardeCube extends Item {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 2000; // 2 sec

    public ZygardeCube(Settings settings) {
        super(settings);
    }

    public static boolean possible(ServerPlayerEntity player) {
        UUID playerId = player.getUuid();
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId) && currentTime < cooldowns.get(playerId)) {
            player.sendMessage(
                    Text.translatable("message.mega_showdown.not_so_fast").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                    true
            );
            return false;
        }

        // Apply cooldown
        cooldowns.put(playerId, currentTime + COOLDOWN_TIME);
        return true;
    }

    public static EntityHitResult getEntityLookingAt(PlayerEntity player, double distance) {
        Vec3d eyePos = player.getEyePos();
        Vec3d lookVec = player.getRotationVec(1.0F);
        Vec3d targetPos = eyePos.add(lookVec.multiply(distance));

        return ProjectileUtil.raycast(
                player,
                eyePos,
                targetPos,
                player.getBoundingBox().stretch(lookVec.multiply(distance)).expand(1.0, 1.0, 1.0),
                entity -> !entity.isSpectator() && entity.canHit() && entity instanceof LivingEntity,
                distance * distance
        );
    }

    public static NbtCompound serializeInventory(SimpleInventory inventory, RegistryWrapper.WrapperLookup registries) {
        NbtCompound tag = new NbtCompound();
        NbtList itemsList = new NbtList();

        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                NbtCompound entry = new NbtCompound();
                entry.putByte("Slot", (byte) i);

                // Use the encode() method to serialize the ItemStack
                NbtElement encodedStack = stack.encode(registries);
                entry.put("Item", encodedStack);

                itemsList.add(entry);
            }
        }

        tag.put("Items", itemsList);
        return tag;
    }

    public static SimpleInventory deserializeInventory(NbtCompound tag, RegistryWrapper.WrapperLookup registries) {
        SimpleInventory inventory = new SimpleInventory(2);
        NbtList itemsList = tag.getList("Items", NbtElement.COMPOUND_TYPE);

        for (int i = 0; i < itemsList.size(); i++) {
            NbtCompound entry = itemsList.getCompound(i);
            int slot = entry.getByte("Slot") & 255;

            // Deserialize using ItemStack.fromNbt()
            NbtCompound itemTag = entry.getCompound("Item");

            Optional<ItemStack> optionalStack = ItemStack.fromNbt(registries, itemTag);

            // Handle Optional<ItemStack> properly
            optionalStack.ifPresent(stack -> {
                if (!stack.isEmpty() && slot < inventory.size()) {
                    inventory.setStack(slot, stack);
                }
            });
        }

        return inventory;
    }

    public static void particleEffect(LivingEntity context) {
        if (context.getWorld() instanceof ServerWorld serverWorld) {
            Vec3d entityPos = context.getPos(); // Get entity position

            // Get entity's size
            double entityWidth = context.getWidth();
            double entityHeight = context.getHeight();

            // Scaling factor to slightly expand particle spread beyond the entity's bounding box
            double scaleFactor = 0.8; // Adjust this for more spread
            double adjustedWidth = entityWidth * scaleFactor;
            double adjustedHeight = entityHeight * scaleFactor;
            double adjustedDepth = entityWidth * scaleFactor;

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
                        ParticleTypes.END_ROD,
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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) {
            return TypedActionResult.fail(player.getStackInHand(hand));
        }

        EntityHitResult entityHit = getEntityLookingAt(player, 4.5);
        if (entityHit != null) {
            Entity entity = entityHit.getEntity();
            if (entity instanceof PokemonEntity pk) {
                if (pk.getPokemon().getSpecies().getName().equals("Zygarde")) {
                    ItemStack stack = player.getStackInHand(hand);

                    Pokemon pokemon = pk.getPokemon();
                    if (pokemon.getEntity() == null || pokemon.getEntity().getWorld().isClient || pokemon.getEntity().isBattling()) {
                        return TypedActionResult.pass(stack);
                    }

                    if (pk.getAspects().contains("core-percent")) {
                        SimpleInventory inventory = getInventory(stack, player);

                        if (inventory.getStack(1).getCount() >= 5) {
                            player.sendMessage(
                                    Text.translatable("message.mega_showdown.cube_core_full").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                                    true
                            );
                            return TypedActionResult.pass(stack);
                        }
                        int count = inventory.getStack(1).getCount() + 1;
                        ItemStack newStack = new ItemStack(FormeChangeItems.ZYGARDE_CORE, count);
                        inventory.setStack(1, newStack);

                        stack.set(DataManage.ZYGARDE_CUBE_INV, ZygardeCube.serializeInventory(inventory,
                                player.getWorld().getRegistryManager()));
                        player.setStackInHand(hand, stack);

                        if (pokemon.getOwnerPlayer() == player) {
                            Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player).remove(pokemon);
                        } else {
                            entity.discard();
                        }

                        return TypedActionResult.success(stack);
                    }

                    if (!pk.getAspects().contains("power-construct")) {
                        if (stack.get(DataManage.POKEMON_STORAGE) != null) {
                            player.sendMessage(
                                    Text.translatable("message.mega_showdown.cube_full").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                                    true
                            );
                            return TypedActionResult.fail(stack);
                        }
                        stack.set(DataComponentTypes.CUSTOM_NAME, Text.translatable("item.mega_showdown.zygarde_cube.full"));
                        stack.set(DataManage.POKEMON_STORAGE, pokemon);
                        player.setStackInHand(hand, stack);
                        Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player).remove(pokemon);
                        return TypedActionResult.success(stack);
                    }
                    if (pk.getAspects().contains("power-construct")) {
                        if (!possible((ServerPlayerEntity) player)) {
                            return TypedActionResult.pass(stack);
                        } else if (pk.getAspects().contains("10-percent")) {
                            particleEffect(pokemon.getEntity());
                            new StringSpeciesFeature("percent_cells", "50").apply(pk);
                        } else {
                            particleEffect(pokemon.getEntity());
                            new StringSpeciesFeature("percent_cells", "10").apply(pk);
                        }
                    } else {
                        player.sendMessage(
                                Text.translatable("message.mega_showdown.resassembly_zygarde_req").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF0000))),
                                true
                        );
                    }

                    return TypedActionResult.success(stack);
                } else {
                    ItemStack stack = player.getStackInHand(hand);
                    Pokemon currentValue = stack.getOrDefault(DataManage.POKEMON_STORAGE, null);

                    if (currentValue != null) {
                        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);
                        playerPartyStore.add(currentValue);
                        stack.set(DataManage.POKEMON_STORAGE, null);
                        return TypedActionResult.success(stack);
                    }
                }
            } else {
                ItemStack stack = player.getStackInHand(hand);
                Pokemon currentValue = stack.getOrDefault(DataManage.POKEMON_STORAGE, null);

                if (currentValue != null) {
                    PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);
                    playerPartyStore.add(currentValue);
                    stack.set(DataManage.POKEMON_STORAGE, null);
                    return TypedActionResult.success(stack);
                }
            }
        } else {
            ItemStack stack = player.getStackInHand(hand);
            Pokemon currentValue = stack.getOrDefault(DataManage.POKEMON_STORAGE, null);

            if (currentValue != null) {
                PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayerEntity) player);
                playerPartyStore.add(currentValue);
                stack.set(DataManage.POKEMON_STORAGE, null);
                return TypedActionResult.success(stack);
            }
        }

        RegistryWrapper.WrapperLookup registries = player.getWorld().getRegistryManager();

        ItemStack stack = player.getStackInHand(hand);

        NbtCompound tag = stack.getOrDefault(DataManage.ZYGARDE_CUBE_INV, new NbtCompound());

        SimpleInventory inventory = deserializeInventory(tag, registries);

        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                (syncId, inv, user) -> new ZygardeCubeScreenHandler(syncId, inv, inventory, user, stack),
                Text.translatable("menu.zygade_cube")
        ));

        return TypedActionResult.pass(player.getStackInHand(hand));
    }

    public SimpleInventory getInventory(ItemStack stack, PlayerEntity player) {
        NbtCompound tag = stack.get(DataManage.ZYGARDE_CUBE_INV);
        RegistryWrapper.WrapperLookup registries = player.getWorld().getRegistryManager();

        SimpleInventory inventory = new SimpleInventory(2);
        if (tag != null) {
            inventory = deserializeInventory(tag, registries);
        }

        return inventory;
    }
}
