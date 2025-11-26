package com.github.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.client.entity.PokemonClientDelegate;
import com.cobblemon.mod.common.client.render.MatrixWrapper;
import com.cobblemon.mod.common.client.render.pokemon.PokemonRenderer;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.github.yajatkaul.mega_showdown.entitiy.MegaShowdownEntities;
import com.github.yajatkaul.mega_showdown.entitiy.TeraHatEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = PokemonRenderer.class)
public class PokemonRendererMixin {

    @Inject(method = "render*", at = @At(value = "TAIL"))
    public void render(PokemonEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        PokemonClientDelegate clientDelegate = (PokemonClientDelegate) entity.getDelegate();
        Map<String, MatrixWrapper> locatorStates = clientDelegate.getLocatorStates();

        MatrixWrapper headLocator = locatorStates.get("head");
        if (headLocator == null) return;

        poseStack.pushPose();

        poseStack.mulPose(headLocator.getMatrix());

        Level level = entity.level();
        TeraHatEntity attachedEntity = new TeraHatEntity(MegaShowdownEntities.TERA_HAT_ENTITY.get(), level);

        Minecraft.getInstance().getEntityRenderDispatcher().render(
                attachedEntity,
                0, 0, 0,
                0,
                partialTicks,
                poseStack,
                buffer,
                packedLight
        );

        poseStack.popPose();
    }
}
