package com.github.yajatkaul.mega_showdown.block;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.block.block_entity.PedestalBlockEntity;
import com.github.yajatkaul.mega_showdown.block.block_entity.ReassemblyUnitBlockEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class MegaShowdownBlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(MegaShowdown.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static void register() {
        BLOCK_ENTITIES.register();
    }

    public static final RegistrySupplier<BlockEntityType<ReassemblyUnitBlockEntity>> REASSEMBLY_UNIT_ENTITY =
            BLOCK_ENTITIES.register("reassembly_unit_entity", () ->
                    BlockEntityType.Builder.of(ReassemblyUnitBlockEntity::new, MegaShowdownBlocks.REASSEMBLY_UNIT.get()).build(null)
            );

    public static final RegistrySupplier<BlockEntityType<PedestalBlockEntity>> PEDESTAL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("pedestal_block_entity", () ->
                    BlockEntityType.Builder.of(PedestalBlockEntity::new, MegaShowdownBlocks.PEDESTAL.get()).build(null)
            );


}
