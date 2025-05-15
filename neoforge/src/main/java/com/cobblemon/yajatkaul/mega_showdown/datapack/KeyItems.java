package com.cobblemon.yajatkaul.mega_showdown.datapack;

import com.cobblemon.mod.common.api.data.ClientDataSynchronizer;
import com.cobblemon.yajatkaul.mega_showdown.config.structure.FormeChange;
import com.cobblemon.yajatkaul.mega_showdown.config.structure.abstracts.Effects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KeyItems implements ClientDataSynchronizer<FormeChange> {
    public String msd_id;
    public String item_id;
    public String item_name;
    public List<String> item_description;
    public List<String> pokemons;
    public List<String> aspects;
    public List<String> default_aspects;
    public List<String> required_aspects;
    public List<List<String>> toggle_aspects;
    public Integer custom_model_data;
    public Boolean tradable_form;
    public Effects effects;

    public KeyItems() {

    }

    @Override
    public boolean shouldSynchronize(FormeChange formeChange) {
        return false;
    }

    @Override
    public void decode(@NotNull RegistryFriendlyByteBuf buf) {
        msd_id = buf.readUtf();
        item_id = buf.readUtf();
        item_name = buf.readUtf();
        item_description = buf.readList(FriendlyByteBuf::readUtf);
        custom_model_data = buf.readInt();
        pokemons = buf.readList(FriendlyByteBuf::readUtf);
        aspects = buf.readList(FriendlyByteBuf::readUtf);
        default_aspects = buf.readList(FriendlyByteBuf::readUtf);
        toggle_aspects = buf.readList(b -> b.readList(FriendlyByteBuf::readUtf));
        required_aspects = buf.readList(FriendlyByteBuf::readUtf);
        tradable_form = buf.readBoolean();
        effects = Effects.read(buf);
    }

    @Override
    public void encode(@NotNull RegistryFriendlyByteBuf buf) {
        buf.writeUtf(msd_id);
        buf.writeUtf(item_id);
        buf.writeUtf(item_name);
        buf.writeCollection(item_description, FriendlyByteBuf::writeUtf);
        buf.writeInt(custom_model_data);
        buf.writeCollection(pokemons, FriendlyByteBuf::writeUtf);
        buf.writeCollection(aspects, FriendlyByteBuf::writeUtf);
        buf.writeCollection(default_aspects, FriendlyByteBuf::writeUtf);
        buf.writeCollection(toggle_aspects, (b, list) -> b.writeCollection(list, FriendlyByteBuf::writeUtf));
        buf.writeCollection(required_aspects, FriendlyByteBuf::writeUtf);
        buf.writeBoolean(tradable_form);
        effects.write(buf);
    }
}