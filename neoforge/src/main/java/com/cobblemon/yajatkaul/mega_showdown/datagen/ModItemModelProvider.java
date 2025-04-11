package com.cobblemon.yajatkaul.mega_showdown.datagen;

import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.item.*;
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

        basicItem(MegaStones.BLUE_ORB.get());
        basicItem(MegaStones.RED_ORB.get());

        basicItem(ZCrystals.ALORAICHIUM_Z.get());
        basicItem(ZCrystals.BLANK_Z.get());
        basicItem(ZCrystals.BUGINIUM_Z.get());
        basicItem(ZCrystals.DARKINIUM_Z.get());
        basicItem(ZCrystals.DECIDIUM_Z.get());
        basicItem(ZCrystals.DRAGONIUM_Z.get());
        basicItem(ZCrystals.EEVIUM_Z.get());
        basicItem(ZCrystals.ELECTRIUM_Z.get());
        basicItem(ZCrystals.FAIRIUM_Z.get());
        basicItem(ZCrystals.FIGHTINIUM_Z.get());
        basicItem(ZCrystals.FIRIUM_Z.get());
        basicItem(ZCrystals.FLYINIUM_Z.get());
        basicItem(ZCrystals.GHOSTIUM_Z.get());
        basicItem(ZCrystals.GRASSIUM_Z.get());
        basicItem(ZCrystals.GROUNDIUM_Z.get());
        basicItem(ZCrystals.ICIUM_Z.get());
        basicItem(ZCrystals.INCINIUM_Z.get());
        basicItem(ZCrystals.KOMMONIUM_Z.get());
        basicItem(ZCrystals.LUNALIUM_Z.get());
        basicItem(ZCrystals.LYCANIUM_Z.get());
        basicItem(ZCrystals.MARSHADIUM_Z.get());
        basicItem(ZCrystals.MEWNIUM_Z.get());
        basicItem(ZCrystals.MIMIKIUM_Z.get());
        basicItem(ZCrystals.NORMALIUM_Z.get());
        basicItem(ZCrystals.PIKANIUM_Z.get());
        basicItem(ZCrystals.PIKASHUNIUM_Z.get());
        basicItem(ZCrystals.POISONIUM_Z.get());
        basicItem(ZCrystals.PRIMARIUM_Z.get());
        basicItem(ZCrystals.PSYCHIUM_Z.get());
        basicItem(ZCrystals.ROCKIUM_Z.get());
        basicItem(ZCrystals.SNORLIUM_Z.get());
        basicItem(ZCrystals.SOLGANIUM_Z.get());
        basicItem(ZCrystals.STEELIUM_Z.get());
        basicItem(ZCrystals.TAPUNIUM_Z.get());
        basicItem(ZCrystals.ULTRANECROZIUM_Z.get());
        basicItem(ZCrystals.WATERIUM_Z.get());

        basicItem(FormeChangeItems.N_LUNARIZER.get());
        basicItem(FormeChangeItems.N_SOLARIZER.get());

        basicItem(FormeChangeItems.DNA_SPLICER.get());

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

        basicItem(FormeChangeItems.RUSTED_SWORD.get());
        basicItem(FormeChangeItems.RUSTED_SHIELD.get());

        basicItem(FormeChangeItems.PRISON_BOTTLE.get());

        basicItem(FormeChangeItems.REINS_OF_UNITY.get());

        basicItem(FormeChangeItems.GRACIDEA_FLOWER.get());

        basicItem(ModItems.SCROLL_OF_DARKNESS.get());
        basicItem(ModItems.SCROLL_OF_WATERS.get());

        basicItem(FormeChangeItems.HEARTHFLAME_MASK.get());
        basicItem(FormeChangeItems.CORNERSTONE_MASK.get());
        basicItem(FormeChangeItems.WELLSPRING_MASK.get());

        basicItem(FormeChangeItems.STAR_CORE.get());

        basicItem(FormeChangeItems.GRISEOUS_CORE.get());
        basicItem(FormeChangeItems.LUSTROUS_GLOBE.get());
        basicItem(FormeChangeItems.ADAMANT_CRYSTAL.get());

        basicItem(CompiItems.ADAMANT_ORB.get());
        basicItem(CompiItems.GRISEOUS_ORB.get());
        basicItem(CompiItems.LUSTROUS_ORB.get());

        basicItem(FormeChangeItems.ASH_CAP.get());

        basicItem(RotomFormes.FAN.get());
        basicItem(RotomFormes.FRIDGEUNIT.get());
        basicItem(RotomFormes.OVENUNIT.get());
        basicItem(RotomFormes.MOWUNIT.get());
        basicItem(RotomFormes.WASHUNIT.get());
        basicItem(RotomFormes.ROTOM_CATALOGUE.get());

        basicItem(FormeChangeItems.FLAME_PLATE.get());
        basicItem(FormeChangeItems.SPLASH_PLATE.get());
        basicItem(FormeChangeItems.ZAP_PLATE.get());
        basicItem(FormeChangeItems.MEADOW_PLATE.get());
        basicItem(FormeChangeItems.ICICLE_PLATE.get());
        basicItem(FormeChangeItems.FIST_PLATE.get());
        basicItem(FormeChangeItems.TOXIC_PLATE.get());
        basicItem(FormeChangeItems.EARTH_PLATE.get());
        basicItem(FormeChangeItems.SKY_PLATE.get());
        basicItem(FormeChangeItems.MIND_PLATE.get());
        basicItem(FormeChangeItems.INSECT_PLATE.get());
        basicItem(FormeChangeItems.STONE_PLATE.get());
        basicItem(FormeChangeItems.SPOOKY_PLATE.get());
        basicItem(FormeChangeItems.DRACO_PLATE.get());
        basicItem(FormeChangeItems.DREAD_PLATE.get());
        basicItem(FormeChangeItems.IRON_PLATE.get());
        basicItem(FormeChangeItems.PIXIE_PLATE.get());

        basicItem(FormeChangeItems.BUG_MEMORY.get());
        basicItem(FormeChangeItems.DARK_MEMORY.get());
        basicItem(FormeChangeItems.DRAGON_MEMORY.get());
        basicItem(FormeChangeItems.ELECTRIC_MEMORY.get());
        basicItem(FormeChangeItems.FAIRY_MEMORY.get());
        basicItem(FormeChangeItems.FIGHTING_MEMORY.get());
        basicItem(FormeChangeItems.FIRE_MEMORY.get());
        basicItem(FormeChangeItems.FLYING_MEMORY.get());
        basicItem(FormeChangeItems.GHOST_MEMORY.get());
        basicItem(FormeChangeItems.GRASS_MEMORY.get());
        basicItem(FormeChangeItems.GROUND_MEMORY.get());
        basicItem(FormeChangeItems.ICE_MEMORY.get());
        basicItem(FormeChangeItems.POISON_MEMORY.get());
        basicItem(FormeChangeItems.PSYCHIC_MEMORY.get());
        basicItem(FormeChangeItems.ROCK_MEMORY.get());
        basicItem(FormeChangeItems.STEEL_MEMORY.get());
        basicItem(FormeChangeItems.WATER_MEMORY.get());

        basicItem(FormeChangeItems.BURN_DRIVE.get());
        basicItem(FormeChangeItems.DOUSE_DRIVE.get());
        basicItem(FormeChangeItems.CHILL_DRIVE.get());
        basicItem(FormeChangeItems.SHOCK_DRIVE.get());

        basicItem(FormeChangeItems.RED_NECTAR.get());
        basicItem(FormeChangeItems.PINK_NECTAR.get());
        basicItem(FormeChangeItems.PURPLE_NECTAR.get());
        basicItem(FormeChangeItems.YELLOW_NECTAR.get());

        basicItem(CompiItems.BOOSTER_ENERGY.get());
        basicItem(CompiItems.LEGEND_PLATE.get());

        basicItem(CompiItems.CLEAR_AMULET.get());
        basicItem(CompiItems.LAGGING_TAIL.get());

        basicItem(KeyItems.AZURE_FLUTE.get());
        basicItem(KeyItems.RED_CHAIN.get());

        basicItem(CompiItems.SOULDEW.get());
        basicItem(CompiItems.ADRENALINEORB.get());
        basicItem(CompiItems.GRIPCLAW.get());
    }
}
