package com.github.yajatkaul.mega_showdown.status.custom;

import com.github.yajatkaul.mega_showdown.MegaShowdown;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class DynamaxStatusEffect extends MobEffect {
    public DynamaxStatusEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x7C2123);
        this.addAttributeModifier(
                Attributes.SCALE,
                ResourceLocation.fromNamespaceAndPath(MegaShowdown.MOD_ID, "dynamax"),
                0.1,
                AttributeModifier.Operation.ADD_VALUE
        );
    }
}