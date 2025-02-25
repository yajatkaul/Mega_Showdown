package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
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
                .add(ModItems.ARCHIE_ANCHOR.get())

                .add(ModItems.MAXIE_GLASSES.get())

                .add(ModItems.KORRINA_GLOVE.get())
                .add(ModItems.MEGA_BRACELET.get())
                .add(ModItems.MEGA_BLACK_BRACELET.get())
                .add(ModItems.MEGA_PINK_BRACELET.get())
                .add(ModItems.MEGA_GREEN_BRACELET.get())
                .add(ModItems.MEGA_RING.get())
                .add(ModItems.LYSANDRE_RING.get())
                .add(ModItems.MEGA_RED_BRACELET.get())
                .add(ModItems.MEGA_BLUE_BRACELET.get())
                .add(ModItems.MEGA_YELLOW_BRACELET.get())
                .add(ModItems.BRENDAN_MEGA_CUFF.get());

        tag(ModTags.Items.MEGA_STONES)
                .add(MegaStones.MEGA_STONE.get())
                .add(MegaStones.ABSOLITE.get())
                .add(MegaStones.AERODACTYLITE.get())
                .add(MegaStones.AGGRONITE.get())
                .add(MegaStones.ALAKAZITE.get())
                .add(MegaStones.ALTARIANITE.get())
                .add(MegaStones.AMPHAROSITE.get())
                .add(MegaStones.AUDINITE.get())
                .add(MegaStones.BANETTITE.get())
                .add(MegaStones.BEEDRILLITE.get())
                .add(MegaStones.BLASTOISINITE.get())
                .add(MegaStones.BLAZIKENITE.get())
                .add(MegaStones.CAMERUPTITE.get())
                .add(MegaStones.CHARIZARDITE_Y.get())
                .add(MegaStones.CHARIZARDITE_X.get())
                .add(MegaStones.DIANCITE.get())
                .add(MegaStones.GALLADITE.get())
                .add(MegaStones.GARCHOMPITE.get())
                .add(MegaStones.GARDEVOIRITE.get())
                .add(MegaStones.GENGARITE.get())
                .add(MegaStones.GLALITITE.get())
                .add(MegaStones.GYARADOSITE.get())
                .add(MegaStones.HERACRONITE.get())
                .add(MegaStones.HOUNDOOMINITE.get())
                .add(MegaStones.KANGASKHANITE.get())
                .add(MegaStones.LATIASITE.get())
                .add(MegaStones.LATIOSITE.get())
                .add(MegaStones.LOPUNNITE.get())
                .add(MegaStones.LUCARIONITE.get())
                .add(MegaStones.MANECTITE.get())
                .add(MegaStones.MAWILITE.get())
                .add(MegaStones.MEDICHAMITE.get())
                .add(MegaStones.METAGROSSITE.get())
                .add(MegaStones.MEWTWONITE_Y.get())
                .add(MegaStones.MEWTWONITE_X.get())
                .add(MegaStones.PIDGEOTITE.get())
                .add(MegaStones.PINSIRITE.get())
                .add(MegaStones.SABLENITE.get())
                .add(MegaStones.SALAMENCITE.get())
                .add(MegaStones.SCIZORITE.get())
                .add(MegaStones.SCEPTILITE.get())
                .add(MegaStones.SHARPEDONITE.get())
                .add(MegaStones.SLOWBRONITE.get())
                .add(MegaStones.STEELIXITE.get())
                .add(MegaStones.SWAMPERTITE.get())
                .add(MegaStones.TYRANITARITE.get())
                .add(MegaStones.VENUSAURITE.get());
    }
}
