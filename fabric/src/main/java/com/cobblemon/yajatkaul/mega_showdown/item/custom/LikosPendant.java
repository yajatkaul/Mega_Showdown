package com.cobblemon.yajatkaul.mega_showdown.item.custom;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.CobblemonEntities;
import com.cobblemon.mod.common.CobblemonImplementation;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.api.spawning.SpawnCause;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnAction;
import com.cobblemon.mod.common.api.spawning.detail.PokemonSpawnDetail;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.mod.common.command.SpawnPokemon;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.utils.NoRenderArmorMaterial;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LikosPendant extends ArmorItem {
    public LikosPendant(Settings settings) {
        super(NoRenderArmorMaterial.NO_RENDER, Type.CHESTPLATE, settings);
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
            terapagos.getPokemon().setTeraType(TeraTypes.getSTELLAR());
            if (shinyRoll == 1) {
                terapagos.getPokemon().setShiny(true);
            }

            double spawnX = entity.getX() + (world.random.nextDouble() - 1) * 2;
            double spawnY = entity.getY() + 1;
            double spawnZ = entity.getZ() + (world.random.nextDouble() - 1) * 2;

            terapagos.refreshPositionAndAngles(spawnX, spawnY, spawnZ, entity.getYaw(), 0.0f);
            world.spawnEntity(terapagos);
        }
    }

    @Override
    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
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
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.mega_showdown.likos_pendant.tooltip"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
