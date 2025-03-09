package com.cobblemon.yajatkaul.mega_showdown.datamanage;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.mojang.serialization.Codec;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
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

    public static final Supplier<AttachmentType<Boolean>> PRIMAL_DATA = ATTACHMENT_TYPES.register(
            "primal_data", () -> AttachmentType.builder(() -> false)
                    .serialize(Codec.BOOL).copyOnDeath().build()
    );

    public static final Supplier<AttachmentType<Pokemon>> MEGA_POKEMON = ATTACHMENT_TYPES.register(
            "mega_pokemon", () -> AttachmentType.builder(Pokemon::new)
                    .serialize(Pokemon.getCODEC()).copyOnDeath().build()
    );

    public static final Supplier<AttachmentType<Pokemon>> PRIMAL_POKEMON = ATTACHMENT_TYPES.register(
            "primal_pokemon", () -> AttachmentType.builder(Pokemon::new)
                    .serialize(Pokemon.getCODEC()).copyOnDeath().build()
    );


    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, "mega_showdown");

    public static final Supplier<DataComponentType<Boolean>> N_LUNAR = REGISTRAR.registerComponentType(
            "n_lunar",
            builder -> builder
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
    );

    public static final Supplier<DataComponentType<Boolean>> N_SOLAR = REGISTRAR.registerComponentType(
            "n_solar",
            builder -> builder
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
    );

    public static final Supplier<AttachmentType<Pokemon>> N_LUNAR_POKEMON = ATTACHMENT_TYPES.register(
            "n_lunar_pokemon", () -> AttachmentType.builder(Pokemon::new)
                    .serialize(Pokemon.getCODEC()).copyOnDeath().build()
    );

    public static final Supplier<AttachmentType<Pokemon>> N_SOLAR_POKEMON = ATTACHMENT_TYPES.register(
            "n_solar_pokemon", () -> AttachmentType.builder(Pokemon::new)
                    .serialize(Pokemon.getCODEC()).copyOnDeath().build()
    );

    public static final Supplier<AttachmentType<Pokemon>> KYUREM_FUSION = ATTACHMENT_TYPES.register(
            "kyurem_fusion", () -> AttachmentType.builder(Pokemon::new)
                    .serialize(Pokemon.getCODEC()).copyOnDeath().build()
    );

    public static final Supplier<DataComponentType<Boolean>> KYUREM_DATA = REGISTRAR.registerComponentType(
            "kyurem_data",
            builder -> builder
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
    );

    public static void register(IEventBus eventBus){
        ATTACHMENT_TYPES.register(eventBus);
        REGISTRAR.register(eventBus);
    }
}
