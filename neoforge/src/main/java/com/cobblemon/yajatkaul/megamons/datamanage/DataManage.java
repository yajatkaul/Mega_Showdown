package com.cobblemon.yajatkaul.megamons.datamanage;

import com.cobblemon.yajatkaul.megamons.MegaShowdown;
import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class DataManage {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MegaShowdown.MOD_ID);

    public static final Supplier<AttachmentType<Boolean>> MEGA_DATA = ATTACHMENT_TYPES.register(
            "mega_data", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build()
    );

    public static void register(IEventBus eventBus){
        ATTACHMENT_TYPES.register(eventBus);
    }

}
