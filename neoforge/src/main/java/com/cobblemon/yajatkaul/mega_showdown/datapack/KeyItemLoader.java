package com.cobblemon.yajatkaul.mega_showdown.datapack;

import com.cobblemon.mod.common.api.data.DataRegistry;
import com.cobblemon.mod.common.api.data.JsonDataRegistry;
import com.cobblemon.mod.common.api.reactive.SimpleObservable;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class KeyItemLoader implements JsonDataRegistry<KeyItems> {
    @NotNull
    @Override
    public Gson getGson() {
        return new GsonBuilder().disableHtmlEscaping()
                .create();
    }

    @NotNull
    @Override
    public TypeToken<KeyItems> getTypeToken() {
        return TypeToken.get(KeyItems.class);
    }

    @NotNull
    @Override
    public String getResourcePath() {
        return "key_items";
    }

    @Override
    public void reload(@NotNull ResourceManager resourceManager) {
        MegaShowdown.LOGGER.error(resourceManager.toString());
    }

    @Override
    public void reload(@NotNull Map<ResourceLocation, ? extends KeyItems> map) {
        for(KeyItems items: map.values()){
            MegaShowdown.LOGGER.info(items.item_id);
        }
    }

    @NotNull
    @Override
    public ResourceLocation getId() {
        return ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "key_items");
    }

    @NotNull
    @Override
    public PackType getType() {
        return PackType.SERVER_DATA;
    }

    private static final SimpleObservable<KeyItemLoader> OBSERVABLE = new SimpleObservable<>();

    @NotNull
    @Override
    public SimpleObservable<? extends DataRegistry> getObservable() {
        return OBSERVABLE;
    }

    @Override
    public void sync(@NotNull ServerPlayer serverPlayer) {

    }
}
