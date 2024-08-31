package net.trashelemental.infested.magic.effects.custom;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class AmbushMobEffect extends MobEffect {
    public AmbushMobEffect() {
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

        if (!entity.level().isClientSide())  {
                if (!entity.hasEffect(MobEffects.INVISIBILITY)) {
                    entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 300, 0, false, false));
                }
                if (!entity.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                    entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, 2, false, false));
                }
            }
        }

    public ResourceLocation getIcon() {
        return new ResourceLocation("infested", "textures/mob_effect/ambush.png");
    }

}
