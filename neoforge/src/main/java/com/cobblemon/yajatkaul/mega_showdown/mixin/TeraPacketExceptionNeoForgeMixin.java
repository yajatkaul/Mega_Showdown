package com.cobblemon.yajatkaul.mega_showdown.mixin;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class TeraPacketExceptionNeoForgeMixin {
    @Inject(
            method = "exceptionCaught",
            at = @At("HEAD"),
            cancellable = true
    )
    private void handleTeraPacketEncoderException(ChannelHandlerContext context, Throwable ex, CallbackInfo ci) {
        // Check if this is an encoder exception
        if (ex instanceof EncoderException) {
            String errorMessage = ex.getMessage();

            // Specifically check for the Tera packet error based on your exact error message
            if (errorMessage != null &&
                    errorMessage.contains("Failed to encode packet") &&
                    errorMessage.contains("cobblemon:tera_type_update")) {

                // Log the exception but suppress it
                System.out.println("[CobblemonMod] Suppressed Tera packet encoder exception in NeoForge: " + errorMessage);
                ci.cancel(); // Prevent default exception handling that would cause disconnect
            }
        }
    }
}