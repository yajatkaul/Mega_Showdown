package com.cobblemon.yajatkaul.mega_showdown.datamanage;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.mojang.serialization.Codec;
import net.minecraft.core.UUIDUtil;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.UUID;
import java.util.function.Supplier;

import static net.minecraft.Util.NIL_UUID;

public class DataManage {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MegaShowdown.MOD_ID);

    public static final Supplier<AttachmentType<Boolean>> MEGA_DATA = ATTACHMENT_TYPES.register(
            "mega_data", () -> AttachmentType.builder(() -> false)
                    .serialize(Codec.BOOL).copyOnDeath().build()
    );

    public static final Supplier<AttachmentType<Pokemon>> MEGA_POKEMON = ATTACHMENT_TYPES.register(
            "mega_pokemon", () -> AttachmentType.builder(Pokemon::new)
                    .serialize(Pokemon.getCODEC()).copyOnDeath().build()
    );

    public static final Supplier<AttachmentType<UUID>> BATTLE_ID = ATTACHMENT_TYPES.register(
            "battle_id", () -> AttachmentType.builder(() -> NIL_UUID)
                    .serialize(UUIDUtil.CODEC).copyOnDeath().build()
    );


    public static void register(IEventBus eventBus){
        ATTACHMENT_TYPES.register(eventBus);
    }
}
