package com.cobblemon.yajatkaul.mega_showdown.mixin.pokemon;

import com.cobblemon.mod.common.command.ReloadShowdownCommand;
import com.cobblemon.yajatkaul.mega_showdown.datapack.showdown.HeldItems;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ReloadShowdownCommand.class, remap = false)
public class ShowdownReloadMixin {
    @Inject(
            method = "execute",
            at = @At("TAIL")
    )
    private void onExecuteTail(CommandContext<ServerCommandSource> context, CallbackInfoReturnable<Integer> cir) {
        HeldItems.INSTANCE.registerItems();
    }
}
