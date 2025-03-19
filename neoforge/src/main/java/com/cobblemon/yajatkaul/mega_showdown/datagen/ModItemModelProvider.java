package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.item.*;
import net.minecraft.data.PackOutput;
import net.neoforged.fml.common.Mod;
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

        basicItem(MegaStones.BLUE_ORB.get());
        basicItem(MegaStones.RED_ORB.get());

        basicItem(ZMoves.ALORAICHIUM_Z.get());
        basicItem(ZMoves.BLANK_Z.get());
        basicItem(ZMoves.BUGINIUM_Z.get());
        basicItem(ZMoves.DARKINIUM_Z.get());
        basicItem(ZMoves.DECIDIUM_Z.get());
        basicItem(ZMoves.DRAGONIUM_Z.get());
        basicItem(ZMoves.EEVIUM_Z.get());
        basicItem(ZMoves.ELECTRIUM_Z.get());
        basicItem(ZMoves.FAIRIUM_Z.get());
        basicItem(ZMoves.FIGHTINIUM_Z.get());
        basicItem(ZMoves.FIRIUM_Z.get());
        basicItem(ZMoves.FLYINIUM_Z.get());
        basicItem(ZMoves.GHOSTIUM_Z.get());
        basicItem(ZMoves.GRASSIUM_Z.get());
        basicItem(ZMoves.GROUNDIUM_Z.get());
        basicItem(ZMoves.ICIUM_Z.get());
        basicItem(ZMoves.INCINIUM_Z.get());
        basicItem(ZMoves.KOMMONIUM_Z.get());
        basicItem(ZMoves.LUNALIUM_Z.get());
        basicItem(ZMoves.LYCANIUM_Z.get());
        basicItem(ZMoves.MARSHADIUM_Z.get());
        basicItem(ZMoves.MEWNIUM_Z.get());
        basicItem(ZMoves.MIMIKIUM_Z.get());
        basicItem(ZMoves.NORMALIUM_Z.get());
        basicItem(ZMoves.PIKANIUM_Z.get());
        basicItem(ZMoves.PIKASHUNIUM_Z.get());
        basicItem(ZMoves.POISONIUM_Z.get());
        basicItem(ZMoves.PRIMARIUM_Z.get());
        basicItem(ZMoves.PSYCHIUM_Z.get());
        basicItem(ZMoves.ROCKIUM_Z.get());
        basicItem(ZMoves.SNORLIUM_Z.get());
        basicItem(ZMoves.SOLGANIUM_Z.get());
        basicItem(ZMoves.STEELIUM_Z.get());
        basicItem(ZMoves.TAPUNIUM_Z.get());
        basicItem(ZMoves.ULTRANECROZIUM_Z.get());
        basicItem(ZMoves.WATERIUM_Z.get());

        basicItem(ModItems.N_LUNARIZER.get());
        basicItem(ModItems.N_SOLARIZER.get());

        basicItem(ModItems.DNA_SPLICER.get());

        basicItem(TeraMoves.BUG_TERA_SHARD.get());
        basicItem(TeraMoves.DARK_TERA_SHARD.get());
        basicItem(TeraMoves.DRAGON_TERA_SHARD.get());
        basicItem(TeraMoves.ELECTRIC_TERA_SHARD.get());
        basicItem(TeraMoves.FAIRY_TERA_SHARD.get());
        basicItem(TeraMoves.FIGHTING_TERA_SHARD.get());
        basicItem(TeraMoves.FIRE_TERA_SHARD.get());
        basicItem(TeraMoves.FLYING_TERA_SHARD.get());
        basicItem(TeraMoves.GHOST_TERA_SHARD.get());
        basicItem(TeraMoves.GRASS_TERA_SHARD.get());
        basicItem(TeraMoves.GROUND_TERA_SHARD.get());
        basicItem(TeraMoves.ICE_TERA_SHARD.get());
        basicItem(TeraMoves.NORMAL_TERA_SHARD.get());
        basicItem(TeraMoves.POISON_TERA_SHARD.get());
        basicItem(TeraMoves.PSYCHIC_TERA_SHARD.get());
        basicItem(TeraMoves.ROCK_TERA_SHARD.get());
        basicItem(TeraMoves.STEEL_TERA_SHARD.get());
        basicItem(TeraMoves.STELLAR_TERA_SHARD.get());
        basicItem(TeraMoves.WATER_TERA_SHARD.get());

        basicItem(ModItems.RUSTED_SWORD.get());
        basicItem(ModItems.RUSTED_SHIELD.get());

        basicItem(ModItems.PRISON_BOTTLE.get());

        basicItem(ModItems.REINS_OF_UNITY.get());

        basicItem(ModItems.GRACIDEA_FLOWER.get());

        basicItem(ModItems.SCROLL_OF_DARKNESS.get());
        basicItem(ModItems.SCROLL_OF_WATERS.get());

        basicItem(ModItems.HEARTHFLAME_MASK.get());
        basicItem(ModItems.CORNERSTONE_MASK.get());
        basicItem(ModItems.WELLSPRING_MASK.get());

        basicItem(ModItems.STAR_CORE.get());

        basicItem(ModItems.GRISEOUS_ORB.get());
        basicItem(ModItems.LUSTROUS_GLOBE.get());
        basicItem(ModItems.ADAMANT_ORB.get());

        basicItem(ModItems.ASH_CAP.get());

        basicItem(RotomFormes.FAN.get());
        basicItem(RotomFormes.FRIDGEUNIT.get());
        basicItem(RotomFormes.OVENUNIT.get());
        basicItem(RotomFormes.MOWUNIT.get());
        basicItem(RotomFormes.WASHUNIT.get());
        basicItem(RotomFormes.ROTOM_CATALOGUE.get());

        basicItem(ModItems.FLAME_PLATE.get());
        basicItem(ModItems.SPLASH_PLATE.get());
        basicItem(ModItems.ZAP_PLATE.get());
        basicItem(ModItems.MEADOW_PLATE.get());
        basicItem(ModItems.ICICLE_PLATE.get());
        basicItem(ModItems.FIST_PLATE.get());
        basicItem(ModItems.TOXIC_PLATE.get());
        basicItem(ModItems.EARTH_PLATE.get());
        basicItem(ModItems.SKY_PLATE.get());
        basicItem(ModItems.MIND_PLATE.get());
        basicItem(ModItems.INSECT_PLATE.get());
        basicItem(ModItems.STONE_PLATE.get());
        basicItem(ModItems.SPOOKY_PLATE.get());
        basicItem(ModItems.DRACO_PLATE.get());
        basicItem(ModItems.DREAD_PLATE.get());
        basicItem(ModItems.IRON_PLATE.get());
        basicItem(ModItems.PIXIE_PLATE.get());

        basicItem(ModItems.BUG_MEMORY.get());
        basicItem(ModItems.DARK_MEMORY.get());
        basicItem(ModItems.DRAGON_MEMORY.get());
        basicItem(ModItems.ELECTRIC_MEMORY.get());
        basicItem(ModItems.FAIRY_MEMORY.get());
        basicItem(ModItems.FIGHTING_MEMORY.get());
        basicItem(ModItems.FIRE_MEMORY.get());
        basicItem(ModItems.FLYING_MEMORY.get());
        basicItem(ModItems.GHOST_MEMORY.get());
        basicItem(ModItems.GRASS_MEMORY.get());
        basicItem(ModItems.GROUND_MEMORY.get());
        basicItem(ModItems.ICE_MEMORY.get());
        basicItem(ModItems.POISON_MEMORY.get());
        basicItem(ModItems.PSYCHIC_MEMORY.get());
        basicItem(ModItems.ROCK_MEMORY.get());
        basicItem(ModItems.STEEL_MEMORY.get());
        basicItem(ModItems.WATER_MEMORY.get());
    }
}
