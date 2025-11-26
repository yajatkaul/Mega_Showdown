package com.github.yajatkaul.mega_showdown.entitiy;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class MegaShowdownEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(MegaShowdown.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<TeraHatEntity>> TERA_HAT_ENTITY =
            ENTITIES.register("tera_hat", () ->
                    EntityType.Builder.of(TeraHatEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)        // size of entity (hitbox)
                            .clientTrackingRange(32)  // distance client gets updates
                            .build("tera_hat")
            );
    private static RegistrySupplier<EntityType<?>> registerEntity(String name, Supplier<EntityType<?>> entity) {
        return ENTITIES.register(name, entity);
    }

    public static void register() {
        ENTITIES.register();
    }
}
