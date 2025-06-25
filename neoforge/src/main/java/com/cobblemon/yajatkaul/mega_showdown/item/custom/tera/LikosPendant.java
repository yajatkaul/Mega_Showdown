package com.cobblemon.yajatkaul.mega_showdown.item.custom.tera;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.yajatkaul.mega_showdown.curios.render.NoRenderArmorMaterial;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.SnowStormHandler;
import kotlin.Unit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LikosPendant extends ArmorItem {
    public LikosPendant(Properties arg) {
        super(NoRenderArmorMaterial.NO_RENDER, Type.CHESTPLATE, arg);
    }

    public static String ticksToTime(int ticks) {
        int minutes = (int) Math.floor(ticks / 1200.0);
        int seconds = (int) Math.floor((ticks % 1200) / 20.0);

        String formattedSeconds = String.format("%02d", seconds);

        if (minutes <= 0) {
            return formattedSeconds;
        } else {
            return minutes + ":" + formattedSeconds;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level arg, Player user, InteractionHand hand) {
        if (!user.level().isClientSide) {
            ItemStack handStack = user.getItemInHand(hand);
            EquipmentSlot chestSlot = EquipmentSlot.CHEST;
            ItemStack chestItem = user.getItemBySlot(chestSlot);

            if (chestItem.isEmpty()) {
                user.setItemSlot(chestSlot, handStack.copy());

                handStack.shrink(1);

                return InteractionResultHolder.success(user.getItemInHand(hand));
            }
        }

        return InteractionResultHolder.fail(user.getItemInHand(hand));
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);

        if (entity.level().isClientSide) return;

        int tick = stack.getOrDefault(DataManage.LIKO_PENDANT_TICK, 72000);

        if (tick > 0) {
            tick--;
            stack.set(DataManage.LIKO_PENDANT_TICK, tick);

            if (tick % 20 == 0) {
                Component loreLine = Component.literal(ticksToTime(tick))
                        .withStyle(Style.EMPTY.withColor(0xAAAAAA));
                stack.set(DataComponents.LORE, new ItemLore(List.of(loreLine)));
            }
        }

        if (tick <= 0) {
            int shinyRoll = ThreadLocalRandom.current().nextInt(1, (int) (Cobblemon.config.getShinyRate() + 1)); // 8193 is exclusive

            stack.shrink(1);

            PokemonEntity terapagos = PokemonProperties.Companion.parse("terapagos").createEntity(level);
            terapagos.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 100, 0, false, false));
            terapagos.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 120, 0, false, false));

            terapagos.getPokemon().setTeraType(TeraTypes.getSTELLAR());
            if (shinyRoll == 1) {
                terapagos.getPokemon().setShiny(true);
            }

            // Get the direction the entity is facing (yaw in degrees)
            float yaw = entity.getYRot();

            // Convert yaw to radians and calculate direction vector
            double radians = Math.toRadians(yaw);
            double dx = -Math.sin(radians);
            double dz = Math.cos(radians);

            // Distance in front of entity
            double distance = 2.0;

            // Compute spawn coordinates
            double spawnX = entity.getX() + dx * distance;
            double spawnY = entity.getY();
            double spawnZ = entity.getZ() + dz * distance;

            terapagos.moveTo(spawnX, spawnY, spawnZ, entity.getYRot(), 0.0f);

            level.addFreshEntity(terapagos);

            terapagos.after(0.01f, () -> {
                SnowStormHandler.Companion.snowStormPartileSpawner(terapagos,
                        "pendant_effect", "target");
                BlockPos entityPos = terapagos.getOnPos();
                terapagos.level().playSound(
                        null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                        ModSounds.TERAPAGOS_SPAWN.get(),
                        SoundSource.PLAYERS, 0.2f, 1.2f
                );
                return Unit.INSTANCE;
            });

            terapagos.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);

            terapagos.after(4F, () -> {
                SnowStormHandler.Companion.cryAnimation(terapagos);
                terapagos.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                return Unit.INSTANCE;
            });
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.mega_showdown.likos_pendant.tooltip"));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
