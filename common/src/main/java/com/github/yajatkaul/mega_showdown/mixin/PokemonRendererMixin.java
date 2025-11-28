package com.github.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.client.entity.PokemonClientDelegate;
import com.cobblemon.mod.common.client.render.MatrixWrapper;
import com.cobblemon.mod.common.client.render.models.blockbench.PosableModel;
import com.cobblemon.mod.common.client.render.models.blockbench.repository.RenderContext;
import com.cobblemon.mod.common.client.render.models.blockbench.repository.VaryingModelRepository;
import com.cobblemon.mod.common.client.render.pokemon.PokemonRenderer;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.block.block_entity.renderer.state.TeraHatState;
import com.github.yajatkaul.mega_showdown.config.MegaShowdownConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import kotlin.Unit;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Mixin(value = PokemonRenderer.class)
public class PokemonRendererMixin {
    @Unique
    private final RenderContext mega_showdown$context = new RenderContext();
    @Unique
    private final ResourceLocation mega_showdown$poserId = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "tera_hat");
    @Unique
    private final TeraHatState mega_showdown$teraHatState = new TeraHatState();
    @Unique
    private final Set<String> mega_showdown$aspects = new HashSet<>();

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    public void init(EntityRendererProvider.Context context, CallbackInfo ci) {
        this.mega_showdown$context.put(RenderContext.Companion.getRENDER_STATE(), RenderContext.RenderState.WORLD);
        this.mega_showdown$context.put(RenderContext.Companion.getDO_QUIRKS(), true);
    }

    @Inject(method = "render*", at = @At(value = "TAIL"))
    public void render(PokemonEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        PokemonClientDelegate clientDelegate = (PokemonClientDelegate) entity.getDelegate();

        Pokemon pokemon = entity.getPokemon();
        Optional<String> aspect = pokemon.getAspects().stream()
                .filter(a -> a.startsWith("msd:tera_")).findFirst();

        if (aspect.isPresent() && MegaShowdownConfig.teraHats) {
            if (pokemon.getSpecies().getName().equals("Terapagos")) return;

            Map<String, MatrixWrapper> locatorStates = clientDelegate.getLocatorStates();
            MatrixWrapper headLocator = locatorStates.get("tera_hat");
            if (headLocator == null) return;

            poseStack.pushPose();
            poseStack.mulPose(headLocator.getMatrix());
            poseStack.translate(0, 0.55f, 0);

            // Update state BEFORE getting model
            mega_showdown$aspects.clear(); // Clear and re-add
            mega_showdown$aspects.add(aspect.get());
            mega_showdown$teraHatState.setCurrentAspects(mega_showdown$aspects);
            mega_showdown$teraHatState.updatePartialTicks(partialTicks);

            // Get model and texture
            PosableModel model = VaryingModelRepository.INSTANCE.getPoser(mega_showdown$poserId, mega_showdown$teraHatState);
            ResourceLocation texture = VaryingModelRepository.INSTANCE.getTexture(mega_showdown$poserId, mega_showdown$teraHatState);

            // Initialize model if needed (first time or model changed)
            if (mega_showdown$teraHatState.getCurrentModel() != model) {
                mega_showdown$teraHatState.setCurrentModel(model);
                // Initialize to a default pose if your model has one
                if (model.getPoses().containsKey("idle")) {
                    model.moveToPose(mega_showdown$teraHatState, model.getPoses().get("idle"));
                }
            }

            // Setup context
            model.context = mega_showdown$context;
            mega_showdown$context.put(RenderContext.Companion.getASPECTS(), mega_showdown$aspects);
            mega_showdown$context.put(RenderContext.Companion.getTEXTURE(), texture);
            mega_showdown$context.put(RenderContext.Companion.getSPECIES(), mega_showdown$poserId);
            mega_showdown$context.put(RenderContext.Companion.getPOSABLE_STATE(), mega_showdown$teraHatState);

            // Apply animations
            model.applyAnimations(
                    null,
                    mega_showdown$teraHatState,
                    0F,
                    0F,
                    0F,
                    0F,
                    mega_showdown$teraHatState.getAnimationSeconds() * 20
            );

            // Render
            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(texture));
            model.setBufferProvider(buffer);
            model.render(mega_showdown$context, poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, -0x1);

            model.withLayerContext(
                    buffer,
                    mega_showdown$teraHatState,
                    VaryingModelRepository.INSTANCE.getLayers(mega_showdown$poserId, mega_showdown$teraHatState),
                    () -> {
                        model.render(mega_showdown$context, poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, -0x1);
                        return Unit.INSTANCE;
                    }
            );
            model.setDefault();

            poseStack.popPose();
        }
    }
}
