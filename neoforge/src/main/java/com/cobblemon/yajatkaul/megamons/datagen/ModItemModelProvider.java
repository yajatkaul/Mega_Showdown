package com.cobblemon.yajatkaul.megamons.datagen;

import com.cobblemon.yajatkaul.megamons.MegaShowdown;
import com.cobblemon.yajatkaul.megamons.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper){
        super(output, MegaShowdown.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.ABOMASNOW.get());
        basicItem(ModItems.CHARIZARDITE_X.get());
        basicItem(ModItems.MEGA_BRACELET.get());
        basicItem(ModItems.ABSOLITE.get());
        basicItem(ModItems.ADAMANTORB.get());
        basicItem(ModItems.AERODACTYLITE.get());
        basicItem(ModItems.AGGRONITE.get());
        basicItem(ModItems.ALAKAZITE.get());
        basicItem(ModItems.ALTARIANITE.get());
        basicItem(ModItems.AMPHAROSITE.get());
        basicItem(ModItems.AUDINITE.get());
        basicItem(ModItems.BANETTITE.get());
        basicItem(ModItems.BEEDRILLITE.get());
        basicItem(ModItems.BLASTOISINITE.get());
        basicItem(ModItems.BLAZIKENITE.get());
        basicItem(ModItems.BLUEORB.get());
        basicItem(ModItems.BUGMEMORY.get());
        basicItem(ModItems.BURNDRIVE.get());
        basicItem(ModItems.CAMERUPTITE.get());
        basicItem(ModItems.CHARIZARDITE_Y.get());
        basicItem(ModItems.CHILLDRIVE.get());
        basicItem(ModItems.CORNERSTONEMASK.get());
        basicItem(ModItems.DARKMEMORY.get());
        basicItem(ModItems.DIANCITE.get());
        basicItem(ModItems.DNASPLICERS.get());
        basicItem(ModItems.DOUSEDRIVE.get());
        basicItem(ModItems.DRACOPLATE.get());
        basicItem(ModItems.DRAGONMEMORY.get());
        basicItem(ModItems.DREADPLATE.get());
        basicItem(ModItems.EARTHPLATE.get());
        basicItem(ModItems.ELECTRICMEMORY.get());
        basicItem(ModItems.GLALITITE.get());
        basicItem(ModItems.GARCHOMPITE.get());
        basicItem(ModItems.GARDEVOIRITE.get());
        basicItem(ModItems.GENGARITE.get());
        basicItem(ModItems.GALLADITE.get());
        basicItem(ModItems.GYARADOSITE.get());
        basicItem(ModItems.HERACRONITE.get());
        basicItem(ModItems.HOUNDOOMINITE.get());
        basicItem(ModItems.KANGASKHANITE.get());
        basicItem(ModItems.LATIASITE.get());
        basicItem(ModItems.LATIOSITE.get());
        basicItem(ModItems.LOPUNNITE.get());
        basicItem(ModItems.LUCARIONITE.get());
        basicItem(ModItems.MANECTITE.get());
        basicItem(ModItems.MAWILITE.get());
        basicItem(ModItems.MEDICHAMITE.get());
        basicItem(ModItems.METAGROSSITE.get());
        basicItem(ModItems.MEWTWONITE_Y.get());
        basicItem(ModItems.MEWTWONITE_X.get());
        basicItem(ModItems.PIDGEOTITE.get());
        basicItem(ModItems.PINSIRITE.get());
        basicItem(ModItems.SABLENITE.get());
        basicItem(ModItems.SALAMENCITE.get());
        basicItem(ModItems.SCIZORITE.get());
        basicItem(ModItems.SHARPEDONITE.get());
        basicItem(ModItems.SLOWBRONITE.get());
        basicItem(ModItems.STEELIXITE.get());
        basicItem(ModItems.SWAMPERTITE.get());
        basicItem(ModItems.TYRANITARITE.get());
        basicItem(ModItems.VENUSAURITE.get());
    }
}
