package com.github.yajatkaul.mega_showdown.item;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.api.types.ElementalTypes;
import com.cobblemon.mod.common.api.types.tera.TeraType;
import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.github.yajatkaul.mega_showdown.MegaShowdown;
import com.github.yajatkaul.mega_showdown.creative.MegaShowdownTabs;
import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import com.github.yajatkaul.mega_showdown.item.custom.DebugStick;
import com.github.yajatkaul.mega_showdown.item.custom.ToolTipItem;
import com.github.yajatkaul.mega_showdown.item.custom.dynamax.DynamaxCandy;
import com.github.yajatkaul.mega_showdown.item.custom.dynamax.MaxHoney;
import com.github.yajatkaul.mega_showdown.item.custom.dynamax.MaxSoup;
import com.github.yajatkaul.mega_showdown.item.custom.dynamax.SweetMaxSoup;
import com.github.yajatkaul.mega_showdown.item.custom.form_change.FormChangeItem;
import com.github.yajatkaul.mega_showdown.item.custom.form_change.ZygardeCube;
import com.github.yajatkaul.mega_showdown.item.custom.fusion.DuFusion;
import com.github.yajatkaul.mega_showdown.item.custom.fusion.SoloFusion;
import com.github.yajatkaul.mega_showdown.item.custom.gimmick.*;
import com.github.yajatkaul.mega_showdown.item.custom.mega.MegaStone;
import com.github.yajatkaul.mega_showdown.item.custom.tera.TeraShard;
import com.github.yajatkaul.mega_showdown.item.custom.z.ElementalZCrystal;
import com.github.yajatkaul.mega_showdown.item.custom.z.SpecialZCrystal;
import com.github.yajatkaul.mega_showdown.utils.Effect;
import com.github.yajatkaul.mega_showdown.utils.ParticlesList;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.function.Supplier;

public class MegaShowdownItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MegaShowdown.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> KEYSTONE = registerTooltipItem("keystone", MegaShowdownTabs.MEGA_TAB);
    public static final RegistrySupplier<Item> MEGA_STONE = registerTooltipItem("mega_stone", MegaShowdownTabs.MEGA_TAB);
    public static final RegistrySupplier<Item> ABOMASITE = registerMegaStone("abomasite", MegaGimmick.ABOMASITE);
    public static final RegistrySupplier<Item> ABSOLITE = registerMegaStone("absolite", MegaGimmick.ABSOLITE);
    public static final RegistrySupplier<Item> AERODACTYLITE = registerMegaStone("aerodactylite", MegaGimmick.AERODACTYLITE);
    public static final RegistrySupplier<Item> AGGRONITE = registerMegaStone("aggronite", MegaGimmick.AGGRONITE);
    public static final RegistrySupplier<Item> ALAKAZITE = registerMegaStone("alakazite", MegaGimmick.ALAKAZITE);
    public static final RegistrySupplier<Item> ALTARIANITE = registerMegaStone("altarianite", MegaGimmick.ALTARIANITE);
    public static final RegistrySupplier<Item> AMPHAROSITE = registerMegaStone("ampharosite", MegaGimmick.AMPHAROSITE);
    public static final RegistrySupplier<Item> AUDINITE = registerMegaStone("audinite", MegaGimmick.AUDINITE);
    public static final RegistrySupplier<Item> BANETTITE = registerMegaStone("banettite", MegaGimmick.BANETTITE);
    public static final RegistrySupplier<Item> BEEDRILLITE = registerMegaStone("beedrillite", MegaGimmick.BEEDRILLITE);
    public static final RegistrySupplier<Item> BLASTOISINITE = registerMegaStone("blastoisinite", MegaGimmick.BLASTOISINITE);
    public static final RegistrySupplier<Item> BLAZIKENITE = registerMegaStone("blazikenite", MegaGimmick.BLAZIKENITE);
    public static final RegistrySupplier<Item> CAMERUPTITE = registerMegaStone("cameruptite", MegaGimmick.CAMERUPTITE);
    public static final RegistrySupplier<Item> CHARIZARDITE_X = registerMegaStone("charizardite_x", MegaGimmick.CHARIZARDITE_X);
    public static final RegistrySupplier<Item> CHARIZARDITE_Y = registerMegaStone("charizardite_y", MegaGimmick.CHARIZARDITE_Y);
    public static final RegistrySupplier<Item> DIANCITE = registerMegaStone("diancite", MegaGimmick.DIANCITE);
    public static final RegistrySupplier<Item> GALLADITE = registerMegaStone("galladite", MegaGimmick.GALLADITE);
    public static final RegistrySupplier<Item> GARCHOMPITE = registerMegaStone("garchompite", MegaGimmick.GARCHOMPITE);
    public static final RegistrySupplier<Item> GARDEVOIRITE = registerMegaStone("gardevoirite", MegaGimmick.GARDEVOIRITE);
    public static final RegistrySupplier<Item> GENGARITE = registerMegaStone("gengarite", MegaGimmick.GENGARITE);
    public static final RegistrySupplier<Item> GYARADOSITE = registerMegaStone("gyaradosite", MegaGimmick.GYARADOSITE);
    public static final RegistrySupplier<Item> HERACRONITE = registerMegaStone("heracronite", MegaGimmick.HERACRONITE);
    public static final RegistrySupplier<Item> HOUNDOOMINITE = registerMegaStone("houndoominite", MegaGimmick.HOUNDOOMINITE);
    public static final RegistrySupplier<Item> KANGASKHANITE = registerMegaStone("kangaskhanite", MegaGimmick.KANGASKHANITE);
    public static final RegistrySupplier<Item> LATIASITE = registerMegaStone("latiasite", MegaGimmick.LATIASITE);
    public static final RegistrySupplier<Item> LATIOSITE = registerMegaStone("latiosite", MegaGimmick.LATIOSITE);
    public static final RegistrySupplier<Item> LOPUNNITE = registerMegaStone("lopunnite", MegaGimmick.LOPUNNITE);
    public static final RegistrySupplier<Item> LUCARIONITE = registerMegaStone("lucarionite", MegaGimmick.LUCARIONITE);
    public static final RegistrySupplier<Item> MANECTITE = registerMegaStone("manectite", MegaGimmick.MANECTITE);
    public static final RegistrySupplier<Item> MAWILITE = registerMegaStone("mawilite", MegaGimmick.MAWILITE);
    public static final RegistrySupplier<Item> MEDICHAMITE = registerMegaStone("medichamite", MegaGimmick.MEDICHAMITE);
    public static final RegistrySupplier<Item> METAGROSSITE = registerMegaStone("metagrossite", MegaGimmick.METAGROSSITE);
    public static final RegistrySupplier<Item> MEWTWONITE_X = registerMegaStone("mewtwonite_x", MegaGimmick.MEWTWONITE_X);
    public static final RegistrySupplier<Item> MEWTWONITE_Y = registerMegaStone("mewtwonite_y", MegaGimmick.MEWTWONITE_Y);
    public static final RegistrySupplier<Item> PIDGEOTITE = registerMegaStone("pidgeotite", MegaGimmick.PIDGEOTITE);
    public static final RegistrySupplier<Item> PINSIRITE = registerMegaStone("pinsirite", MegaGimmick.PINSIRITE);
    public static final RegistrySupplier<Item> SABLENITE = registerMegaStone("sablenite", MegaGimmick.SABLENITE);
    public static final RegistrySupplier<Item> SALAMENCITE = registerMegaStone("salamencite", MegaGimmick.SALAMENCITE);
    public static final RegistrySupplier<Item> SCEPTILITE = registerMegaStone("sceptilite", MegaGimmick.SCEPTILITE);
    public static final RegistrySupplier<Item> SCIZORITE = registerMegaStone("scizorite", MegaGimmick.SCIZORITE);
    public static final RegistrySupplier<Item> SHARPEDONITE = registerMegaStone("sharpedonite", MegaGimmick.SHARPEDONITE);
    public static final RegistrySupplier<Item> SLOWBRONITE = registerMegaStone("slowbronite", MegaGimmick.SLOWBRONITE);
    public static final RegistrySupplier<Item> STEELIXITE = registerMegaStone("steelixite", MegaGimmick.STEELIXITE);
    public static final RegistrySupplier<Item> SWAMPERTITE = registerMegaStone("swampertite", MegaGimmick.SWAMPERTITE);
    public static final RegistrySupplier<Item> TYRANITARITE = registerMegaStone("tyranitarite", MegaGimmick.TYRANITARITE);
    public static final RegistrySupplier<Item> VENUSAURITE = registerMegaStone("venusaurite", MegaGimmick.VENUSAURITE);

    public static final RegistrySupplier<Item> RED_ORB = registerFormChangeItems(
            "red_orb",
            "reversion_state=primal",
            "reversion_state=standard",
            List.of("Groudon"),
            ParticlesList.groudonPrimalRevert,
            false
    );

    public static final RegistrySupplier<Item> BLUE_ORB = registerFormChangeItems(
            "blue_orb",
            "reversion_state=primal",
            "reversion_state=standard",
            List.of("Kyogre"),
            ParticlesList.kyogrePrimalRevert,
            false
    );

    public static final RegistrySupplier<Item> NORMAL_TERA_SHARD = registerTeraShards("normal_tera_shard", TeraTypes.getNORMAL());
    public static final RegistrySupplier<Item> FIRE_TERA_SHARD = registerTeraShards("fire_tera_shard", TeraTypes.getFIRE());
    public static final RegistrySupplier<Item> WATER_TERA_SHARD = registerTeraShards("water_tera_shard", TeraTypes.getWATER());
    public static final RegistrySupplier<Item> ELECTRIC_TERA_SHARD = registerTeraShards("electric_tera_shard", TeraTypes.getELECTRIC());
    public static final RegistrySupplier<Item> GRASS_TERA_SHARD = registerTeraShards("grass_tera_shard", TeraTypes.getGRASS());
    public static final RegistrySupplier<Item> ICE_TERA_SHARD = registerTeraShards("ice_tera_shard", TeraTypes.getICE());
    public static final RegistrySupplier<Item> FIGHTING_TERA_SHARD = registerTeraShards("fighting_tera_shard", TeraTypes.getFIGHTING());
    public static final RegistrySupplier<Item> POISON_TERA_SHARD = registerTeraShards("poison_tera_shard", TeraTypes.getPOISON());
    public static final RegistrySupplier<Item> GROUND_TERA_SHARD = registerTeraShards("ground_tera_shard", TeraTypes.getGROUND());
    public static final RegistrySupplier<Item> FLYING_TERA_SHARD = registerTeraShards("flying_tera_shard", TeraTypes.getFLYING());
    public static final RegistrySupplier<Item> PSYCHIC_TERA_SHARD = registerTeraShards("psychic_tera_shard", TeraTypes.getPSYCHIC());
    public static final RegistrySupplier<Item> BUG_TERA_SHARD = registerTeraShards("bug_tera_shard", TeraTypes.getBUG());
    public static final RegistrySupplier<Item> ROCK_TERA_SHARD = registerTeraShards("rock_tera_shard", TeraTypes.getROCK());
    public static final RegistrySupplier<Item> GHOST_TERA_SHARD = registerTeraShards("ghost_tera_shard", TeraTypes.getGHOST());
    public static final RegistrySupplier<Item> DRAGON_TERA_SHARD = registerTeraShards("dragon_tera_shard", TeraTypes.getDRAGON());
    public static final RegistrySupplier<Item> DARK_TERA_SHARD = registerTeraShards("dark_tera_shard", TeraTypes.getDARK());
    public static final RegistrySupplier<Item> STEEL_TERA_SHARD = registerTeraShards("steel_tera_shard", TeraTypes.getSTEEL());
    public static final RegistrySupplier<Item> FAIRY_TERA_SHARD = registerTeraShards("fairy_tera_shard", TeraTypes.getFAIRY());
    public static final RegistrySupplier<Item> STELLAR_TERA_SHARD = registerTeraShards("stellar_tera_shard", TeraTypes.getSTELLAR());

    // Elemental Z-Crystals
    public static final RegistrySupplier<Item> BUGINIUM_Z = registerZElementalCrystals("buginium_z", ElementalTypes.INSTANCE.getBUG());
    public static final RegistrySupplier<Item> DARKINIUM_Z = registerZElementalCrystals("darkinium_z", ElementalTypes.INSTANCE.getDARK());
    public static final RegistrySupplier<Item> DRAGONIUM_Z = registerZElementalCrystals("dragonium_z", ElementalTypes.INSTANCE.getDRAGON());
    public static final RegistrySupplier<Item> ELECTRIUM_Z = registerZElementalCrystals("electrium_z", ElementalTypes.INSTANCE.getELECTRIC());
    public static final RegistrySupplier<Item> FAIRIUM_Z = registerZElementalCrystals("fairium_z", ElementalTypes.INSTANCE.getFAIRY());
    public static final RegistrySupplier<Item> FIGHTINIUM_Z = registerZElementalCrystals("fightinium_z", ElementalTypes.INSTANCE.getFIGHTING());
    public static final RegistrySupplier<Item> FIRIUM_Z = registerZElementalCrystals("firium_z", ElementalTypes.INSTANCE.getFIRE());
    public static final RegistrySupplier<Item> FLYINIUM_Z = registerZElementalCrystals("flyinium_z", ElementalTypes.INSTANCE.getFLYING());
    public static final RegistrySupplier<Item> GHOSTIUM_Z = registerZElementalCrystals("ghostium_z", ElementalTypes.INSTANCE.getGHOST());
    public static final RegistrySupplier<Item> GRASSIUM_Z = registerZElementalCrystals("grassium_z", ElementalTypes.INSTANCE.getGRASS());
    public static final RegistrySupplier<Item> GROUNDIUM_Z = registerZElementalCrystals("groundium_z", ElementalTypes.INSTANCE.getGROUND());
    public static final RegistrySupplier<Item> ICIUM_Z = registerZElementalCrystals("icium_z", ElementalTypes.INSTANCE.getICE());
    public static final RegistrySupplier<Item> POISONIUM_Z = registerZElementalCrystals("poisonium_z", ElementalTypes.INSTANCE.getPOISON());
    public static final RegistrySupplier<Item> PSYCHIUM_Z = registerZElementalCrystals("psychium_z", ElementalTypes.INSTANCE.getPSYCHIC());
    public static final RegistrySupplier<Item> ROCKIUM_Z = registerZElementalCrystals("rockium_z", ElementalTypes.INSTANCE.getROCK());
    public static final RegistrySupplier<Item> STEELIUM_Z = registerZElementalCrystals("steelium_z", ElementalTypes.INSTANCE.getSTEEL());
    public static final RegistrySupplier<Item> WATERIUM_Z = registerZElementalCrystals("waterium_z", ElementalTypes.INSTANCE.getWATER());

    // Special Z-Crystals
    public static final RegistrySupplier<Item> ALORAICHIUM_Z = registerZSpecialCrystals("aloraichium_z", ElementalTypes.INSTANCE.getELECTRIC());
    public static final RegistrySupplier<Item> DECIDIUM_Z = registerZSpecialCrystals("decidium_z", ElementalTypes.INSTANCE.getGRASS());
    public static final RegistrySupplier<Item> EEVIUM_Z = registerZSpecialCrystals("eevium_z", ElementalTypes.INSTANCE.getNORMAL());
    public static final RegistrySupplier<Item> INCINIUM_Z = registerZSpecialCrystals("incinium_z", ElementalTypes.INSTANCE.getFIRE());
    public static final RegistrySupplier<Item> KOMMONIUM_Z = registerZSpecialCrystals("kommonium_z", ElementalTypes.INSTANCE.getDRAGON());
    public static final RegistrySupplier<Item> LUNALIUM_Z = registerZSpecialCrystals("lunalium_z", ElementalTypes.INSTANCE.getPSYCHIC());
    public static final RegistrySupplier<Item> LYCANIUM_Z = registerZSpecialCrystals("lycanium_z", ElementalTypes.INSTANCE.getROCK());
    public static final RegistrySupplier<Item> MARSHADIUM_Z = registerZSpecialCrystals("marshadium_z", ElementalTypes.INSTANCE.getGHOST());
    public static final RegistrySupplier<Item> MEWNIUM_Z = registerZSpecialCrystals("mewnium_z", ElementalTypes.INSTANCE.getPSYCHIC());
    public static final RegistrySupplier<Item> MIMIKIUM_Z = registerZSpecialCrystals("mimikium_z", ElementalTypes.INSTANCE.getGHOST());
    public static final RegistrySupplier<Item> PIKANIUM_Z = registerZSpecialCrystals("pikanium_z", ElementalTypes.INSTANCE.getELECTRIC());
    public static final RegistrySupplier<Item> PIKASHUNIUM_Z = registerZSpecialCrystals("pikashunium_z", ElementalTypes.INSTANCE.getELECTRIC());
    public static final RegistrySupplier<Item> PRIMARIUM_Z = registerZSpecialCrystals("primarium_z", ElementalTypes.INSTANCE.getWATER());
    public static final RegistrySupplier<Item> SNORLIUM_Z = registerZSpecialCrystals("snorlium_z", ElementalTypes.INSTANCE.getNORMAL());
    public static final RegistrySupplier<Item> SOLGANIUM_Z = registerZSpecialCrystals("solganium_z", ElementalTypes.INSTANCE.getSTEEL());
    public static final RegistrySupplier<Item> TAPUNIUM_Z = registerZSpecialCrystals("tapunium_z", ElementalTypes.INSTANCE.getFAIRY());
    public static final RegistrySupplier<Item> ULTRANECROZIUM_Z = registerZSpecialCrystals("ultranecrozium_z", ElementalTypes.INSTANCE.getPSYCHIC());

    public static final RegistrySupplier<Item> DNA_SPLICER = registerDuFusion(
            "dna_splicer",
            List.of("black-fusion"),
            List.of("white-fusion"),
            List.of("Zekrom"),
            List.of("Reshiram"),
            List.of("Kyurem"),
            List.of("absofusion=black"),
            List.of("absofusion=white"),
            List.of("absofusion=none"),
            List.of("absofusion=none"),
            ParticlesList.kyuremBlackFusion,
            ParticlesList.kyuremWhiteFusion
    );

    public static final RegistrySupplier<Item> REINS_OF_UNITY = registerDuFusion(
            "reins_of_unity",
            List.of("shadow-rider"),
            List.of("ice-rider"),
            List.of("Spectrier"),
            List.of("Glastrier"),
            List.of("Calyrex"),
            List.of("king_steed=shadow"),
            List.of("king_steed=ice"),
            List.of("king_steed=none"),
            List.of("king_steed=none"),
            ParticlesList.calyrexShadowFusion,
            ParticlesList.calyrexIceFusion
    );

    public static final RegistrySupplier<Item> N_LUNARIZER = registerSoloFusion(
            "n_lunarizer",
            List.of("dusk-fusion", "dawn-fusion"),
            List.of("Lunala"),
            List.of("Necrozma"),
            ParticlesList.nLunSolFusion,
            List.of("prism_fusion=dawn"),
            List.of("prism_fusion=none")
    );

    public static final RegistrySupplier<Item> N_SOLARIZER = registerSoloFusion(
            "n_solarizer",
            List.of("dusk-fusion", "dawn-fusion"),
            List.of("Solgaleo"),
            List.of("Necrozma"),
            ParticlesList.nLunSolFusion,
            List.of("prism_fusion=dusk"),
            List.of("prism_fusion=none")
    );

    public static final RegistrySupplier<Item> DEBUG_STICK = registerItem("debug_stick", () -> new DebugStick(new Item.Properties()));

    public static final RegistrySupplier<Item> MEGA_BRACELET = registerMegaBracelet("mega_bracelet");

    public static final RegistrySupplier<Item> TERA_ORB = registerTeraOrb("tera_orb");

    public static final RegistrySupplier<Item> DYNAMAX_BAND = registerDynamaxBand("dynamax_band");

    public static final RegistrySupplier<Item> Z_RING = registerZRing("z_ring");

    public static final RegistrySupplier<Item> OMNI_RING = registerOmniRing("omni_ring");

    public static final RegistrySupplier<Item> DYNAMAX_CANDY = registerItem("dynamax_candy", () -> new DynamaxCandy(new Item.Properties().arch$tab(MegaShowdownTabs.DYNAMAX_TAB)));
    public static final RegistrySupplier<Item> MAX_HONEY = registerItem("max_honey", () -> new MaxHoney(new Item.Properties().arch$tab(MegaShowdownTabs.DYNAMAX_TAB)));
    public static final RegistrySupplier<Item> MAX_SOUP = registerItem("max_soup", () -> new MaxSoup(new Item.Properties().arch$tab(MegaShowdownTabs.DYNAMAX_TAB)));
    public static final RegistrySupplier<Item> SWEET_MAX_SOUP = registerItem("sweet_max_soup", () -> new SweetMaxSoup(new Item.Properties().arch$tab(MegaShowdownTabs.DYNAMAX_TAB)));

    public static final RegistrySupplier<Item> ZYGARDE_CUBE = registerItem("zygarde_cube", () -> new ZygardeCube(new Item.Properties().stacksTo(1).arch$tab(MegaShowdownTabs.FORM_TAB)));
    public static final RegistrySupplier<Item> ZYGARDE_CELL = registerItem("zygarde_cell", () -> new ToolTipItem(new Item.Properties().stacksTo(95).arch$tab(MegaShowdownTabs.FORM_TAB)));
    public static final RegistrySupplier<Item> ZYGARDE_CORE = registerItem("zygarde_core", () -> new ToolTipItem(new Item.Properties().stacksTo(5).arch$tab(MegaShowdownTabs.FORM_TAB)));

    private static RegistrySupplier<Item> registerMegaStone(String name, MegaGimmick megaGimmick) {
        return ITEMS.register(name, () -> new MegaStone(
                new Item.Properties().arch$tab(MegaShowdownTabs.MEGA_TAB),
                megaGimmick)
        );
    }

    private static RegistrySupplier<Item> registerMegaBracelet(String name) {
        return ITEMS.register(name, () -> new MegaBracelet(
                new Item.Properties()
                        .stacksTo(1)
                        .arch$tab(MegaShowdownTabs.MEGA_TAB))
        );
    }

    private static RegistrySupplier<Item> registerOmniRing(String name) {
        return ITEMS.register(name, () -> new OmniRing(
                new Item.Properties()
                        .stacksTo(1)
                        .arch$tab(MegaShowdownTabs.KEY_TAB))
        );
    }

    private static RegistrySupplier<Item> registerTeraOrb(String name) {
        return ITEMS.register(name, () -> new TeraOrb(
                new Item.Properties()
                        .stacksTo(1)
                        .durability(100)
                        .arch$tab(MegaShowdownTabs.TERA_TAB))
        );
    }

    private static RegistrySupplier<Item> registerDynamaxBand(String name) {
        return ITEMS.register(name, () -> new DynamaxBand(
                new Item.Properties()
                        .stacksTo(1)
                        .arch$tab(MegaShowdownTabs.DYNAMAX_TAB))
        );
    }

    private static RegistrySupplier<Item> registerZRing(String name) {
        return ITEMS.register(name, () -> new ZRing(
                new Item.Properties()
                        .stacksTo(1)
                        .arch$tab(MegaShowdownTabs.Z_TAB))
        );
    }

    private static RegistrySupplier<Item> registerDuFusion(String name,
                                                           List<String> fusion1,
                                                           List<String> fusion2,
                                                           List<String> pokemon1,
                                                           List<String> pokemon2,
                                                           List<String> pokemonMain,
                                                           List<String> applyAspect1,
                                                           List<String> applyAspect2,
                                                           List<String> revertAspect1,
                                                           List<String> revertAspect2,
                                                           Effect effect1,
                                                           Effect effect2
    ) {
        return ITEMS.register(name, () -> new DuFusion(
                new Item.Properties()
                        .arch$tab(MegaShowdownTabs.FORM_TAB)
                        .stacksTo(1),
                fusion1,
                fusion2,
                pokemon1,
                pokemon2,
                pokemonMain,
                applyAspect1,
                applyAspect2,
                revertAspect1,
                revertAspect2,
                effect1,
                effect2)
        );
    }

    private static RegistrySupplier<Item> registerSoloFusion(String name,
                                                             List<String> fusions,
                                                             List<String> pokemon,
                                                             List<String> pokemonMain,
                                                             Effect effect,
                                                             List<String> applyAspect,
                                                             List<String> revertAspect
    ) {
        return ITEMS.register(name, () -> new SoloFusion(
                new Item.Properties()
                        .arch$tab(MegaShowdownTabs.FORM_TAB)
                        .stacksTo(1),
                fusions,
                pokemon,
                pokemonMain,
                effect,
                applyAspect,
                revertAspect)
        );
    }

    private static RegistrySupplier<Item> registerTeraShards(String name, TeraType teraType) {
        return ITEMS.register(name, () -> new TeraShard(new Item.Properties().arch$tab(MegaShowdownTabs.TERA_TAB), teraType));
    }

    private static RegistrySupplier<Item> registerZElementalCrystals(String name, ElementalType type) {
        return ITEMS.register(name, () -> new ElementalZCrystal(new Item.Properties().arch$tab(MegaShowdownTabs.Z_TAB), type));
    }

    private static RegistrySupplier<Item> registerZSpecialCrystals(String name, ElementalType type) {
        return ITEMS.register(name, () -> new SpecialZCrystal(new Item.Properties().arch$tab(MegaShowdownTabs.Z_TAB), type));
    }

    private static RegistrySupplier<Item> registerFormChangeItems(String name, String revertAspect, String applyAspect, List<String> pokemons, Effect effect, boolean tradable) {
        return ITEMS.register(name,
                () -> new FormChangeItem(
                        new Item.Properties().arch$tab(MegaShowdownTabs.FORM_TAB),
                        revertAspect,
                        applyAspect,
                        pokemons,
                        effect,
                        tradable
                ));
    }

    private static RegistrySupplier<Item> registerTooltipItem(String name, DeferredSupplier<CreativeModeTab> tab) {
        return ITEMS.register(name, () -> new ToolTipItem(
                new Item.Properties().arch$tab(tab))
        );
    }

    private static RegistrySupplier<Item> registerItem(String name, Supplier<Item> item) {
        return ITEMS.register(name, item);
    }

    public static void register() {
        ITEMS.register();
    }
}
