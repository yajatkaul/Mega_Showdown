package com.cobblemon.yajatkaul.mega_showdown;

import com.cobblemon.yajatkaul.mega_showdown.block.custom.entity.ModBlockEntities;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.entity.renderer.PedestalBlockEntityRenderer;
import com.cobblemon.yajatkaul.mega_showdown.curios.render.LikosPendantLayer;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.MegaEvo;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.UltraTrans;
import com.cobblemon.yajatkaul.mega_showdown.screen.ModMenuTypes;
import com.cobblemon.yajatkaul.mega_showdown.screen.custom.ZygardeCubeScreen;
import com.cobblemon.yajatkaul.mega_showdown.utility.PackRegister;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.Controls.MEGA_ITEM_KEY;
import static com.cobblemon.yajatkaul.mega_showdown.formChangeLogic.Controls.ULTRA_KEY;

@EventBusSubscriber(modid = MegaShowdown.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MegaShowdownClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.addListener(MegaShowdownClient::onClientTick);
    }

    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        PackRegister.register(event);
    }

    // Register the key binding
    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(MEGA_ITEM_KEY.get());
        event.register(ULTRA_KEY.get());
    }

    public static void onClientTick(ClientTickEvent.Post event) {
        while (MEGA_ITEM_KEY.get().consumeClick()) {
            PacketDistributor.sendToServer(new MegaEvo("mega_evo"));
        }
        while (ULTRA_KEY.get().consumeClick()) {
            PacketDistributor.sendToServer(new UltraTrans("ultra_trans"));
        }
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.PEDESTAL_BE.get(), PedestalBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.ZYGARDE_CUBE_MENU.get(), ZygardeCubeScreen::new);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skin : PlayerSkin.Model.values()) {
            LivingEntityRenderer<Player, HumanoidModel<Player>> renderer = event.getSkin(skin);
            if (renderer != null) {
                renderer.addLayer(new LikosPendantLayer<>(renderer));
            }
        }

        LivingEntityRenderer<Zombie, ZombieModel<Zombie>> zombieRenderer = event.getRenderer(EntityType.ZOMBIE);
        if (zombieRenderer != null) {
            zombieRenderer.addLayer(new LikosPendantLayer<>(zombieRenderer));
        }

        LivingEntityRenderer<Skeleton, SkeletonModel<Skeleton>> skeletonRenderer = event.getRenderer(EntityType.SKELETON);
        if (skeletonRenderer != null) {
            skeletonRenderer.addLayer(new LikosPendantLayer<>(skeletonRenderer));
        }

        LivingEntityRenderer<ArmorStand, HumanoidModel<ArmorStand>> armorStandRenderer =
                event.getRenderer(EntityType.ARMOR_STAND);
        if (armorStandRenderer != null) {
            armorStandRenderer.addLayer(new LikosPendantLayer<>(armorStandRenderer));
        }
    }
}
