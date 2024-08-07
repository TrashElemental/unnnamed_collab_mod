package net.trashelemental.infested.effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class AmbushCooldownMobEffect extends MobEffect {
    public AmbushCooldownMobEffect() {
        super(MobEffectCategory.BENEFICIAL, -10774487);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {

    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {

    }

    public ResourceLocation getIcon() {
        return new ResourceLocation("infested", "textures/mob_effect/ambush_cooldown.png");
    }

}
