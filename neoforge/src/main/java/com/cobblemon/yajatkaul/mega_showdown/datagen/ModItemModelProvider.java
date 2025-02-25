package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper){
        super(output, MegaShowdown.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(MegaStones.MEGA_STONE.get());
        basicItem(MegaStones.ABOMASITE.get());
        basicItem(MegaStones.CHARIZARDITE_X.get());
        basicItem(MegaStones.ABSOLITE.get());
        basicItem(MegaStones.AERODACTYLITE.get());
        basicItem(MegaStones.AGGRONITE.get());
        basicItem(MegaStones.ALAKAZITE.get());
        basicItem(MegaStones.ALTARIANITE.get());
        basicItem(MegaStones.AMPHAROSITE.get());
        basicItem(MegaStones.AUDINITE.get());
        basicItem(MegaStones.BANETTITE.get());
        basicItem(MegaStones.BEEDRILLITE.get());
        basicItem(MegaStones.BLASTOISINITE.get());
        basicItem(MegaStones.BLAZIKENITE.get());
        basicItem(MegaStones.CAMERUPTITE.get());
        basicItem(MegaStones.CHARIZARDITE_Y.get());
        basicItem(MegaStones.DIANCITE.get());
        basicItem(MegaStones.GLALITITE.get());
        basicItem(MegaStones.GARCHOMPITE.get());
        basicItem(MegaStones.GARDEVOIRITE.get());
        basicItem(MegaStones.GENGARITE.get());
        basicItem(MegaStones.GALLADITE.get());
        basicItem(MegaStones.GYARADOSITE.get());
        basicItem(MegaStones.HERACRONITE.get());
        basicItem(MegaStones.HOUNDOOMINITE.get());
        basicItem(MegaStones.KANGASKHANITE.get());
        basicItem(MegaStones.LATIASITE.get());
        basicItem(MegaStones.LATIOSITE.get());
        basicItem(MegaStones.LOPUNNITE.get());
        basicItem(MegaStones.LUCARIONITE.get());
        basicItem(MegaStones.MANECTITE.get());
        basicItem(MegaStones.MAWILITE.get());
        basicItem(MegaStones.MEDICHAMITE.get());
        basicItem(MegaStones.METAGROSSITE.get());
        basicItem(MegaStones.MEWTWONITE_Y.get());
        basicItem(MegaStones.MEWTWONITE_X.get());
        basicItem(MegaStones.PIDGEOTITE.get());
        basicItem(MegaStones.PINSIRITE.get());
        basicItem(MegaStones.SABLENITE.get());
        basicItem(MegaStones.SALAMENCITE.get());
        basicItem(MegaStones.SCIZORITE.get());
        basicItem(MegaStones.SCEPTILITE.get());
        basicItem(MegaStones.SHARPEDONITE.get());
        basicItem(MegaStones.SLOWBRONITE.get());
        basicItem(MegaStones.STEELIXITE.get());
        basicItem(MegaStones.SWAMPERTITE.get());
        basicItem(MegaStones.TYRANITARITE.get());
        basicItem(MegaStones.VENUSAURITE.get());
        basicItem(MegaStones.KEYSTONE.get());
    }
}
