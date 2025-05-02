package com.cobblemon.yajatkaul.mega_showdown.datamanage;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Supplier;

public class DataManage {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MegaShowdown.MOD_ID);

    public static final Supplier<AttachmentType<Boolean>> MEGA_DATA = ATTACHMENT_TYPES.register(
            "mega_data", () -> AttachmentType.builder(() -> false)
                    .serialize(Codec.BOOL).copyOnDeath().build()
    );

    public static final Supplier<AttachmentType<PokeHandler>> MEGA_POKEMON = ATTACHMENT_TYPES.register(
            "mega_pokemon", () -> AttachmentType.builder(() -> new PokeHandler(new Pokemon()))
                    .serialize(PokeHandler.CODEC).copyOnDeath().build()
    );

    public static final Supplier<AttachmentType<Boolean>> PRIMAL_DATA = ATTACHMENT_TYPES.register(
            "primal_data", () -> AttachmentType.builder(() -> false)
                    .serialize(Codec.BOOL).copyOnDeath().build()
    );

    public static final Supplier<AttachmentType<PokeHandler>> PRIMAL_POKEMON = ATTACHMENT_TYPES.register(
            "primal_pokemon", () -> AttachmentType.builder(() -> new PokeHandler(new Pokemon()))
                    .serialize(PokeHandler.CODEC).copyOnDeath().build()
    );

    public static final Supplier<AttachmentType<HashMap<UUID, Pokemon>>> DATA_MAP = ATTACHMENT_TYPES.register(
            "data_map",
            () -> AttachmentType.<HashMap<UUID, Pokemon>>builder(() -> new HashMap<>())
                    .serialize(Codec.unboundedMap(Codec.STRING.xmap(UUID::fromString, UUID::toString), Pokemon.getCODEC())
                            .xmap(HashMap::new, map -> map))
                    .copyOnDeath()
                    .build()
    );


    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, "mega_showdown");

    public static final Supplier<AttachmentType<PokeHandler>> KYUREM_FUSED_WITH = ATTACHMENT_TYPES.register(
            "kyurem_fused_with", () -> AttachmentType.builder(() -> new PokeHandler(new Pokemon()))
                    .serialize(PokeHandler.CODEC).build()
    );

    public static final Supplier<DataComponentType<PokeHandler>> KYUREM_DATA = REGISTRAR.registerComponentType(
            "kyurem_data",
            builder -> builder
                    .persistent(PokeHandler.CODEC)
                    .networkSynchronized(PokeHandler.S2C_CODEC)
    );

    public static final Supplier<AttachmentType<PokeHandler>> CALYREX_FUSED_WITH = ATTACHMENT_TYPES.register(
            "calyrex_fusion_with", () -> AttachmentType.builder(() -> new PokeHandler(new Pokemon()))
                    .serialize(PokeHandler.CODEC).build()
    );

    public static final Supplier<DataComponentType<PokeHandler>> CALYREX_DATA = REGISTRAR.registerComponentType(
            "calyrex_data",
            builder -> builder
                    .persistent(PokeHandler.CODEC)
                    .networkSynchronized(PokeHandler.S2C_CODEC)
    );

    public static final Supplier<DataComponentType<PokeHandler>> N_LUNAR = REGISTRAR.registerComponentType(
            "n_lunar",
            builder -> builder
                    .persistent(PokeHandler.CODEC)
                    .networkSynchronized(PokeHandler.S2C_CODEC)
    );

    public static final Supplier<DataComponentType<PokeHandler>> N_SOLAR = REGISTRAR.registerComponentType(
            "n_solar",
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

    public static final Supplier<DataComponentType<CompoundTag>> ZYGARDE_INV = REGISTRAR.registerComponentType(
            "zygarde_inventory",
            builder -> builder
                    .persistent(CompoundTag.CODEC)
                    .networkSynchronized(ByteBufCodecs.COMPOUND_TAG)
    );

    public static final Supplier<AttachmentType<PokeHandler>> N_LUNAR_POKEMON = ATTACHMENT_TYPES.register(
            "n_lunar_pokemon", () -> AttachmentType.builder(() -> new PokeHandler(new Pokemon()))
                    .serialize(PokeHandler.CODEC).build()
    );

    public static final Supplier<AttachmentType<PokeHandler>> N_SOLAR_POKEMON = ATTACHMENT_TYPES.register(
            "n_solar_pokemon", () -> AttachmentType.builder(() -> new PokeHandler(new Pokemon()))
                    .serialize(PokeHandler.CODEC).build()
    );

    public static final Supplier<DataComponentType<PokeHandler>> ZYGARDE_CUBE_DATA = REGISTRAR.registerComponentType(
            "zygarde_cube_data",
            builder -> builder
                    .persistent(PokeHandler.CODEC)
                    .networkSynchronized(PokeHandler.S2C_CODEC)
    );


    public static void register(IEventBus eventBus){
        ATTACHMENT_TYPES.register(eventBus);
        REGISTRAR.register(eventBus);
    }
}
