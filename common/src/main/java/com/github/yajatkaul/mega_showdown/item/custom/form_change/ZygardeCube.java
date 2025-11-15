package com.github.yajatkaul.mega_showdown.item.custom.form_change;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.block.MegaShowdownBlocks;
import com.github.yajatkaul.mega_showdown.components.MegaShowdownDataComponents;
import com.github.yajatkaul.mega_showdown.item.MegaShowdownItems;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipItem;
import com.github.yajatkaul.mega_showdown.screen.custom.ZygardeCubesScreenHandler;
import com.github.yajatkaul.mega_showdown.utils.NBTInventoryUtils;
import com.github.yajatkaul.mega_showdown.utils.ParticlesList;
import com.github.yajatkaul.mega_showdown.utils.PlayerUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class ZygardeCube extends ToolTipItem {
    public ZygardeCube(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (level.isClientSide || player.isCrouching()) {
            return InteractionResultHolder.pass(stack);
        }

        EntityHitResult hitResult = PlayerUtils.getEntityLookingAt(player, 4.5f);

        Entity entity = null;
        if (hitResult != null) {
            entity = hitResult.getEntity();
        }

        CompoundTag storedTag = stack.get(MegaShowdownDataComponents.NBT_2_COMPONENT.get());
        Pokemon storedPokemon = null;
        if (storedTag != null) {
            storedPokemon = new Pokemon().loadFromNBT(MegaShowdown.getServer().registryAccess(), storedTag);
        }
        PlayerPartyStore playerPartyStore = Cobblemon.INSTANCE.getStorage().getParty((ServerPlayer) player);

        if (entity instanceof PokemonEntity pokemonEntity) {
            Pokemon pokemon = pokemonEntity.getPokemon();

            if (pokemonEntity.isBattling() || pokemonEntity.getTethering() != null || pokemon.getPersistentData().contains("form_changing")) {
                return InteractionResultHolder.pass(stack);
            }

            if (pokemon.getAspects().contains("core-percent")) {
                CompoundTag compoundTag = stack.get(MegaShowdownDataComponents.NBT_COMPONENT.get());
                if (compoundTag == null) {
                    compoundTag = new CompoundTag();
                }
                SimpleContainer inventory = NBTInventoryUtils.deserializeInventory(compoundTag);

                if (inventory.getItem(1).getCount() >= 5) {
                    player.displayClientMessage(Component.translatable("message.mega_showdown.cube_core_full")
                            .withStyle(ChatFormatting.RED), true);
                    return InteractionResultHolder.pass(stack);
                }
                int count = inventory.getItem(1).getCount() + 1;
                ItemStack newStack = new ItemStack(MegaShowdownItems.ZYGARDE_CORE.get(), count);
                inventory.setItem(1, newStack);

                CompoundTag updatedTag = NBTInventoryUtils.serializeInventory(inventory);
                stack.set(MegaShowdownDataComponents.NBT_COMPONENT.get(), updatedTag);

                if (pokemon.isPlayerOwned()) {
                    Cobblemon.INSTANCE.getStorage().getParty(pokemon.getOwnerPlayer()).remove(pokemon);
                } else {
                    entity.discard();
                }

                return InteractionResultHolder.success(stack);
            }

            if (pokemon.getOwnerPlayer() != player) {
                return InteractionResultHolder.pass(stack);
            }

            if (!pokemon.getAspects().contains("power-construct")) {
                if (storedPokemon != null) {
                    player.displayClientMessage(Component.translatable("message.mega_showdown.cube_full")
                            .withStyle(ChatFormatting.RED), true);
                    return InteractionResultHolder.pass(stack);
                }
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.zygarde_cube.full"));
                CompoundTag thisPokemon = pokemon.saveToNBT(MegaShowdown.getServer().registryAccess(), new CompoundTag());
                stack.set(MegaShowdownDataComponents.NBT_2_COMPONENT.get(), thisPokemon);
                playerPartyStore.remove(pokemon);
                return InteractionResultHolder.success(stack);
            } else {
                if (pokemon.getAspects().contains("10-percent")) {
                    ParticlesList.defaultParticles.applyEffects(pokemonEntity, List.of("percent_cells=50"), null);
                    return InteractionResultHolder.success(stack);
                } else if (pokemon.getAspects().contains("50-percent")) {
                    ParticlesList.defaultParticles.applyEffects(pokemonEntity, List.of("percent_cells=10"), null);
                    return InteractionResultHolder.success(stack);
                }
            }

        } else {
            BlockHitResult blockHitResult = PlayerUtils.getBlockLookingAt(player, 4.5f);

            if (storedPokemon != null) {
                playerPartyStore.add(storedPokemon);
                stack.remove(MegaShowdownDataComponents.NBT_2_COMPONENT.get());
                stack.set(DataComponents.CUSTOM_NAME, Component.translatable("item.mega_showdown.zygarde_cube.empty"));

                return InteractionResultHolder.success(stack);
            } else {
                if (blockHitResult.getType() == HitResult.Type.BLOCK &&
                        level.getBlockState(blockHitResult.getBlockPos()).is(MegaShowdownBlocks.REASSEMBLY_UNIT.get())) {
                    return InteractionResultHolder.pass(stack);
                }
                player.openMenu(
                        new SimpleMenuProvider(
                                (id, playerInventory, playerEntity) ->
                                        new ZygardeCubesScreenHandler(id, playerInventory, stack),
                                Component.translatable("menu.zygade_cube")
                        )
                );
            }
            return InteractionResultHolder.pass(stack);
        }

        return InteractionResultHolder.pass(stack);
    }
}
