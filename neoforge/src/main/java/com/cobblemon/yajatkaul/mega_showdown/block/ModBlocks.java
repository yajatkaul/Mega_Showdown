package com.cobblemon.yajatkaul.mega_showdown.block;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.MegaShowdown;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.GracideaBlock;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.MaxMushroomBlock;
import com.cobblemon.yajatkaul.mega_showdown.block.custom.PedestalBlock;
import com.cobblemon.yajatkaul.mega_showdown.item.FormeChangeItems;
import com.cobblemon.yajatkaul.mega_showdown.item.ModItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MegaShowdown.MOD_ID);

    public static final DeferredBlock<Block> KEYSTONE_BLOCK = registerBlock("keystone_block",
            () -> new Block(
                    BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> MEGA_METEOROID_BLOCK = registerBlock("mega_meteorid_block",
            () -> new Block(BlockBehaviour
                            .Properties.of()
                            .strength(3f)
                            .requiresCorrectToolForDrops()
                            .mapColor(MapColor.COLOR_PURPLE)
                            .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> MEGA_EVO_BLOCK = registerBlock("mega_evo_block",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .requiresCorrectToolForDrops()
                    .mapColor(MapColor.COLOR_PURPLE)
                    .sound(SoundType.STONE)));

    //decor
    public static final DeferredBlock<Block> MEGA_EVO_BRICK = registerBlock("mega_evo_brick",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> CHISELED_MEGA_EVO_BRICK = registerBlock("chiseled_mega_evo_brick",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> CHISELED_MEGA_EVO_BLOCK = registerBlock("chiseled_mega_evo_block",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> POLISHED_MEGA_EVO_BLOCK = registerBlock("polished_mega_evo_block",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> DEOXYS_METEORITE = BLOCKS.register("deoxys_meteorite",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .mapColor(MapColor.COLOR_PINK)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)));

    public static final DeferredBlock<Block> POWER_SPOT = registerBlockWithToolTip("power_spot",
            () -> new Block(BlockBehaviour
                    .Properties.of()
                    .strength(3f)
                    .lightLevel((state) -> 15)
                    .mapColor(MapColor.COLOR_PURPLE)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)){
                public static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 4, 14);

                @Override
                protected VoxelShape getShape(BlockState arg, BlockGetter arg2, BlockPos arg3, CollisionContext arg4) {
                    return SHAPE;
                }
            });

    public static final DeferredBlock<Block> MAX_MUSHROOM = BLOCKS.register("max_mushroom", () ->
            new MaxMushroomBlock(BlockBehaviour
                    .Properties.of()
                    .sound(SoundType.FLOWERING_AZALEA)));

    public static final DeferredBlock<Block> GRACIDEA_FLOWER = BLOCKS.register("gracidea_flower", GracideaBlock::new);

    public static final DeferredBlock<Block> POTTED_GRACIDEA = BLOCKS.register("potted_gracidea",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GRACIDEA_FLOWER,
                    BlockBehaviour.Properties.of().noOcclusion().instabreak()));

    public static final DeferredBlock<Block> PEDESTAL = registerBlock("pedestal",
            () -> new PedestalBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(2)));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerBlockWithToolTip(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItemWithToolTip(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> void registerBlockItemWithToolTip(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()){
            @Override
            public void appendHoverText(ItemStack arg, TooltipContext arg2, List<Component> list, TooltipFlag arg3) {
                list.add(Component.translatable("tooltip.mega_showdown."  + name + ".tooltip"));
                super.appendHoverText(arg, arg2, list, arg3);
            }
        });
    }


    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
