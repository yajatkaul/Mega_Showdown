package com.cobblemon.yajatkaul.mega_showdown.item;

import com.cobblemon.mod.common.api.types.tera.TeraTypes;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.tera.TeraItem;
import com.cobblemon.yajatkaul.mega_showdown.item.custom.tera.TeraShard;
import net.minecraft.item.Item;

import static com.cobblemon.yajatkaul.mega_showdown.item.ModItems.registerItem;

public class TeraMoves {
    public static final Item TERA_ORB = registerItem("tera_orb", new TeraItem(new Item.Settings().maxCount(1).maxDamage(100)));

    public static final Item BUG_TERA_SHARD = registerItem("bug_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getBUG()));
    public static final Item DARK_TERA_SHARD = registerItem("dark_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getDARK()));
    public static final Item DRAGON_TERA_SHARD = registerItem("dragon_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getDRAGON()));
    public static final Item ELECTRIC_TERA_SHARD = registerItem("electric_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getELECTRIC()));
    public static final Item FAIRY_TERA_SHARD = registerItem("fairy_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getFAIRY()));
    public static final Item FIGHTING_TERA_SHARD = registerItem("fighting_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getFIGHTING()));
    public static final Item FIRE_TERA_SHARD = registerItem("fire_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getFIRE()));
    public static final Item FLYING_TERA_SHARD = registerItem("flying_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getFLYING()));
    public static final Item GHOST_TERA_SHARD = registerItem("ghost_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getGHOST()));
    public static final Item GRASS_TERA_SHARD = registerItem("grass_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getGRASS()));
    public static final Item GROUND_TERA_SHARD = registerItem("ground_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getGROUND()));
    public static final Item ICE_TERA_SHARD = registerItem("ice_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getICE()));
    public static final Item NORMAL_TERA_SHARD = registerItem("normal_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getNORMAL()));
    public static final Item POISON_TERA_SHARD = registerItem("poison_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getPOISON()));
    public static final Item PSYCHIC_TERA_SHARD = registerItem("psychic_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getPSYCHIC()));
    public static final Item ROCK_TERA_SHARD = registerItem("rock_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getROCK()));
    public static final Item STEEL_TERA_SHARD = registerItem("steel_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getSTEEL()));
    public static final Item STELLAR_TERA_SHARD = registerItem("stellar_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getSTELLAR()));
    public static final Item WATER_TERA_SHARD = registerItem("water_tera_shard", new TeraShard(new Item.Settings().maxCount(50), TeraTypes.getWATER()));

    public static void registerModItem() {

    }
}
