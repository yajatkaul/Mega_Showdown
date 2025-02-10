//package com.cobblemon.yajatkaul.mega_showdown.event;
//
//import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
//import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.level.ServerLevel;
//import net.minecraft.tags.TagKey;
//import net.minecraft.world.entity.npc.VillagerProfession;
//import net.minecraft.world.entity.npc.VillagerTrades;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.Items;
//import net.minecraft.world.item.MapItem;
//import net.minecraft.world.item.trading.ItemCost;
//import net.minecraft.world.item.trading.MerchantOffer;
//import net.neoforged.bus.api.SubscribeEvent;
//import net.neoforged.fml.common.EventBusSubscriber;
//import net.neoforged.neoforge.event.village.VillagerTradesEvent;
//
//import java.util.List;
//
//@EventBusSubscriber(modid = MegaShowdown.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
//public class ModEvents {
//
//    @SubscribeEvent
//    public static void addCustomTrades(VillagerTradesEvent event){
//        if(event.getType() == VillagerProfession.CARTOGRAPHER){
//            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
//
//            trades.get(1).add((entity, randomSource) -> {
//                ServerLevel level = (ServerLevel) entity.level();
//
//                ResourceLocation structureId = ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "megaroid");
//
//                BlockPos structurePos = level.findNearestMapStructure(
//                        TagKey.create(Registries.STRUCTURE, structureId),
//                        entity.blockPosition(),
//                        Integer.MAX_VALUE,  // Search radius
//                        false
//                );
//
//                ItemStack map = MapItem.create(level, structurePos.getX(), structurePos.getZ(), (byte) 2, true, false);
//
//                return new MerchantOffer(
//                        new ItemCost(Items.DIAMOND, 6),
//                        new ItemStack(map.getItemHolder(), 18), 1,3,0.05f
//                );
//            });
//        }
//    }
//}
