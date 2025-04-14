package com.cobblemon.yajatkaul.mega_showdown.block.custom;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class GracideaBlock extends FlowerBlock {
    public GracideaBlock() {
        super(
                MobEffects.REGENERATION,
                8,
                Properties.of()
                        .mapColor(MapColor.PLANT)
                        .noCollission()
                        .noOcclusion()
                        .lightLevel((state) -> 15)
                        .strength(0.0f)
                        .sound(SoundType.GRASS)
        );
    }
}