package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.utils.NoRenderArmorMaterial;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.LazyLib;
import kotlin.Unit;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LikosPendant extends ArmorItem {
    public LikosPendant(Settings settings) {
        super(NoRenderArmorMaterial.NO_RENDER, Type.CHESTPLATE, settings);
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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            ItemStack handStack = user.getStackInHand(hand);
            EquipmentSlot chestSlot = EquipmentSlot.CHEST;
            ItemStack chestItem = user.getEquippedStack(chestSlot);

            if (chestItem.isEmpty()) {
                user.equipStack(chestSlot, handStack.copy());

                handStack.decrement(1);

                return TypedActionResult.success(handStack, world.isClient());
            }
        }

        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (entity.getWorld().isClient) return;

        int tick = stack.getOrDefault(DataManage.LIKO_PENDANT_TICK, 600);

        if (tick > 0) {
            tick--;
            stack.set(DataManage.LIKO_PENDANT_TICK, tick);

            if (tick % 20 == 0) {
                Text loreLine = Text.literal(ticksToTime(tick))
                        .setStyle(Style.EMPTY.withColor(0xAAAAAA));
                stack.set(DataComponentTypes.LORE, new LoreComponent(List.of(loreLine)));
            }
        }

        if (tick <= 0) {
            int shinyRoll = ThreadLocalRandom.current().nextInt(1, (int) (Cobblemon.config.getShinyRate() + 1)); // 8193 is exclusive

            stack.decrement(1);

            PokemonEntity terapagos = PokemonProperties.Companion.parse("terapagos").createEntity(world);

            terapagos.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 100, 0, false, false));
            terapagos.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 120, 0, false, false));

            terapagos.getPokemon().setTeraType(TeraTypes.getSTELLAR());
            if (shinyRoll == 1) {
                terapagos.getPokemon().setShiny(true);
            }

            // Get the direction the entity is facing (yaw in degrees)
            float yaw = entity.getYaw();

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

            terapagos.refreshPositionAndAngles(spawnX, spawnY, spawnZ, entity.getYaw(), 0.0f);

            world.spawnEntity(terapagos);

            terapagos.after(0.01f, () -> {
                LazyLib.Companion.snowStormPartileSpawner(terapagos,
                        "pendant_effect", "target");
                BlockPos entityPos = terapagos.getBlockPos();
                terapagos.getWorld().playSound(
                        null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                        ModSounds.TERAPAGOS_SPAWN,
                        SoundCategory.PLAYERS, 0.2f, 1.2f
                );
                return Unit.INSTANCE;
            });

            terapagos.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), true);

            terapagos.after(4F, () -> {
                LazyLib.Companion.cryAnimation(terapagos);
                terapagos.getDataTracker().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                return Unit.INSTANCE;
            });
        }
    }

    @Override
    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.mega_showdown.likos_pendant.tooltip"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
