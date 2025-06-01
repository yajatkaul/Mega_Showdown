package com.cobblemon.yajatkaul.mega_showdown;

import com.cobblemon.yajatkaul.mega_showdown.block.entity.ModBlockEntities;
import com.cobblemon.yajatkaul.mega_showdown.block.entity.renderer.PedestalBlockEntityRenderer;
import com.cobblemon.yajatkaul.mega_showdown.item.render.LikosPendantLayer;
import com.cobblemon.yajatkaul.mega_showdown.item.render.LikosPendantRenderer;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.MegaEvo;
import com.cobblemon.yajatkaul.mega_showdown.networking.packets.UltraTrans;
import com.cobblemon.yajatkaul.mega_showdown.screen.ModMenuTypes;
import com.cobblemon.yajatkaul.mega_showdown.screen.custom.ZygardeCubeScreen;
import com.cobblemon.yajatkaul.mega_showdown.utility.PackRegister;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

import static com.cobblemon.yajatkaul.mega_showdown.megaevo.Controls.MEGA_ITEM_KEY;
import static com.cobblemon.yajatkaul.mega_showdown.megaevo.Controls.ULTRA_KEY;

@EventBusSubscriber(modid = MegaShowdown.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MegaShowdownClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.addListener(MegaShowdownClient::onClientTick);
//        NeoForge.EVENT_BUS.addListener(LikosPendantRenderer::onRenderLiving);
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
        for (PlayerSkin.Model skin : event.getSkins()) {
            LivingEntityRenderer<? extends Player, ? extends HumanoidModel<? extends Player>> playerRenderer = event.getSkin(skin);
            if (playerRenderer instanceof LivingEntityRenderer) {
                @SuppressWarnings("unchecked")
                LivingEntityRenderer<Player, HumanoidModel<Player>> casted =
                        (LivingEntityRenderer<Player, HumanoidModel<Player>>) playerRenderer;
                casted.addLayer(new LikosPendantLayer<>(casted));
            }
        }
        addLayerToEntity(event, EntityType.PLAYER);
        addLayerToEntity(event, EntityType.ZOMBIE);
        addLayerToEntity(event, EntityType.SKELETON);
    }

    private static <T extends LivingEntity> void addLayerToEntity(EntityRenderersEvent.AddLayers event, EntityType<T> entityType) {
        LivingEntityRenderer<T, ?> renderer = event.getRenderer(entityType);
        if (renderer != null && renderer.getModel() instanceof HumanoidModel) {
            @SuppressWarnings("unchecked")
            LivingEntityRenderer<T, HumanoidModel<T>> typedRenderer = (LivingEntityRenderer<T, HumanoidModel<T>>) renderer;
            typedRenderer.addLayer(new LikosPendantLayer<>(typedRenderer));
        }
    }
}
