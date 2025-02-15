package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import com.cobblemon.yajatkaul.mega_showdown.utility.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper){
        super(output, lookupProvider, blockTags, MegaShowdown.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.MEGA_BRACELETS)
                .add(ModItems.MEGA_BRACELET.get())
                .add(ModItems.MEGA_BLACK_BRACELET.get())
                .add(ModItems.MEGA_PINK_BRACELET.get())
                .add(ModItems.MEGA_GREEN_BRACELET.get())
                .add(ModItems.MEGA_RING.get())
                .add(ModItems.MEGA_RED_BRACELET.get())
                .add(ModItems.MEGA_BLUE_BRACELET.get())
                .add(ModItems.MEGA_YELLOW_BRACELET.get())
                .add(ModItems.BRENDAN_MEGA_CUFF.get());

        tag(ModTags.Items.MEGA_STONES)
                .add(ModItems.MEGA_STONE.get())
                .add(ModItems.ABSOLITE.get())
                .add(ModItems.AERODACTYLITE.get())
                .add(ModItems.AGGRONITE.get())
                .add(ModItems.ALAKAZITE.get())
                .add(ModItems.ALTARIANITE.get())
                .add(ModItems.AMPHAROSITE.get())
                .add(ModItems.AUDINITE.get())
                .add(ModItems.BANETTITE.get())
                .add(ModItems.BEEDRILLITE.get())
                .add(ModItems.BLASTOISINITE.get())
                .add(ModItems.BLAZIKENITE.get())
                .add(ModItems.CAMERUPTITE.get())
                .add(ModItems.CHARIZARDITE_Y.get())
                .add(ModItems.CHARIZARDITE_X.get())
                .add(ModItems.DIANCITE.get())
                .add(ModItems.GALLADITE.get())
                .add(ModItems.GARCHOMPITE.get())
                .add(ModItems.GARDEVOIRITE.get())
                .add(ModItems.GENGARITE.get())
                .add(ModItems.GLALITITE.get())
                .add(ModItems.GYARADOSITE.get())
                .add(ModItems.HERACRONITE.get())
                .add(ModItems.HOUNDOOMINITE.get())
                .add(ModItems.KANGASKHANITE.get())
                .add(ModItems.LATIASITE.get())
                .add(ModItems.LATIOSITE.get())
                .add(ModItems.LOPUNNITE.get())
                .add(ModItems.LUCARIONITE.get())
                .add(ModItems.MANECTITE.get())
                .add(ModItems.MAWILITE.get())
                .add(ModItems.MEDICHAMITE.get())
                .add(ModItems.METAGROSSITE.get())
                .add(ModItems.MEWTWONITE_Y.get())
                .add(ModItems.MEWTWONITE_X.get())
                .add(ModItems.PIDGEOTITE.get())
                .add(ModItems.PINSIRITE.get())
                .add(ModItems.SABLENITE.get())
                .add(ModItems.SALAMENCITE.get())
                .add(ModItems.SCIZORITE.get())
                .add(ModItems.SCEPTILITE.get())
                .add(ModItems.SHARPEDONITE.get())
                .add(ModItems.SLOWBRONITE.get())
                .add(ModItems.STEELIXITE.get())
                .add(ModItems.SWAMPERTITE.get())
                .add(ModItems.TYRANITARITE.get())
                .add(ModItems.VENUSAURITE.get());
    }
}
