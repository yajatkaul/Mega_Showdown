package com.cobblemon.yajatkaul.mega_showdown.dataAttachments;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class DataManage {
    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, "mega_showdown");

    public static final Supplier<DataComponentType<PokeHandler>> POKEMON_STORAGE = REGISTRAR.registerComponentType(
            "pokemon_storage",
            builder -> builder
                    .persistent(PokeHandler.CODEC)
                    .networkSynchronized(PokeHandler.S2C_CODEC)
    );
    public static final Supplier<DataComponentType<Integer>> CATALOGUE_PAGE = REGISTRAR.registerComponentType(
            "catalogue_page",
            builder -> builder
                    .persistent(Codec.INT)
                    .networkSynchronized(StreamCodec.of(
                            FriendlyByteBuf::writeInt,
                            FriendlyByteBuf::readInt
                    ))
    );
    public static final Supplier<DataComponentType<Integer>> LIKO_PENDANT_TICK = REGISTRAR.registerComponentType(
            "liko_pendant",
            builder -> builder
                    .persistent(Codec.INT)
                    .networkSynchronized(StreamCodec.of(
                            FriendlyByteBuf::writeInt,
                            FriendlyByteBuf::readInt
                    ))
    );

    public static final Supplier<DataComponentType<CompoundTag>> ZYGARDE_INV = REGISTRAR.registerComponentType(
            "zygarde_inventory",
            builder -> builder
                    .persistent(CompoundTag.CODEC)
                    .networkSynchronized(ByteBufCodecs.COMPOUND_TAG)
    );
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MegaShowdown.MOD_ID);

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
        REGISTRAR.register(eventBus);
    }
}
