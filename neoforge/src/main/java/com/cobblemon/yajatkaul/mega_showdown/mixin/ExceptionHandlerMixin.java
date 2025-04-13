package com.cobblemon.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.CobblemonNetwork;
import com.cobblemon.mod.common.net.PacketRegisterInfo;
import com.cobblemon.yajatkaul.mega_showdown.utility.PacketHandler;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = CobblemonNetwork.class, remap = false)
public class ExceptionHandlerMixin {
    @Inject(method = "generateS2CPacketInfoList", at = @At("RETURN"), remap = false)
    private void generateS2CPacketInfoList(CallbackInfoReturnable<List<PacketRegisterInfo<?>>> cir, @Local List<PacketRegisterInfo<?>> list) {
        PacketHandler.INSTANCE.handleCobblemonPacket(list);
    }
}
