package com.cobblemon.yajatkaul.mega_showdown.utility;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.*;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import java.nio.file.Path;

public class PackRegister {
    public static void register(AddPackFindersEvent event){
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            // Get the mod's file path
            var modContainer = ModList.get().getModContainerById(MegaShowdown.MOD_ID)
                    .orElseThrow(() -> new IllegalStateException("Mod container not found for ID: " + MegaShowdown.MOD_ID));
            var modFile = modContainer.getModInfo().getOwningFile().getFile();
            Path packPath = modFile.findResource("resourcepacks", "gyaradosjumpingmega");

            // Create PackLocationInfo
            PackLocationInfo packInfo = new PackLocationInfo(
                    "gyaradosjumpingmega",           // Internal ID
                    Component.literal("Gyrados Jumping Mega"), // Display name
                    PackSource.BUILT_IN,              // Source
                    java.util.Optional.empty()       // No explicit format
            );

            // Implement ResourcesSupplier
            Pack.ResourcesSupplier resourcesSupplier = new Pack.ResourcesSupplier() {
                @Override
                public PackResources openPrimary(PackLocationInfo info) {
                    return new PathPackResources(packInfo, packPath);
                }

                @Override
                public PackResources openFull(PackLocationInfo info, Pack.Metadata metadata) {
                    return new PathPackResources(packInfo, packPath);
                }
            };

            // Create PackSelectionConfig
            PackSelectionConfig selectionConfig = new PackSelectionConfig(
                    false,                            // Enabled by default
                    Pack.Position.TOP,               // Position in list
                    true                            // Not required/fixed (toggleable)
            );

            // Create the Pack
            Pack pack = Pack.readMetaAndCreate(
                    packInfo,                        // Pack info
                    resourcesSupplier,               // Resources supplier
                    PackType.CLIENT_RESOURCES,       // Pack type
                    selectionConfig                  // Selection config
            );

            // Register the pack
            if (pack != null) {
                event.addRepositorySource((profileAdder) -> profileAdder.accept(pack));
            }
        }
    }
}
