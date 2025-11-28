package com.github.yajatkaul.mega_showdown.entitiy;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.entitiy.custom.TeraCrystalEntity;
import com.github.yajatkaul.mega_showdown.entitiy.custom.TeraHatEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class MegaShowdownEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(MegaShowdown.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<TeraHatEntity>> WATER_TERA_HAT_ENTITY =
            ENTITIES.register("water_tera_hat", () ->
                    EntityType.Builder.of(TeraHatEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)        // size of entity (hitbox)
                            .clientTrackingRange(32)  // distance client gets updates
                            .build("water_tera_hat")
            );

    public static final RegistrySupplier<EntityType<TeraHatEntity>> STEEL_TERA_HAT_ENTITY =
            ENTITIES.register("steel_tera_hat", () ->
                    EntityType.Builder.of(TeraHatEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)        // size of entity (hitbox)
                            .clientTrackingRange(32)  // distance client gets updates
                            .build("steel_tera_hat")
            );

    public static final RegistrySupplier<EntityType<TeraCrystalEntity>> TERA_CRYSTAL_ENTITY =
            ENTITIES.register("tera_crystal", () ->
                    EntityType.Builder.of(TeraCrystalEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)        // size of entity (hitbox)
                            .clientTrackingRange(32)  // distance client gets updates
                            .build("tera_crystal")
            );

    public static void register() {
        ENTITIES.register();
    }
}
