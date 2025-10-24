package net.team_us.collab_mod.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.team_us.collab_mod.CollabMod;
import net.team_us.collab_mod.entity.custom.projectiles.HoneymanProjectileEntity;
import software.bernie.geckolib.model.GeoModel;

public class HoneymanProjectileModel extends GeoModel<HoneymanProjectileEntity> {
    @Override
    public ResourceLocation getModelResource(HoneymanProjectileEntity animatable) {
        return new ResourceLocation(CollabMod.MOD_ID,"geo/models/projectiles/honeyman_projectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HoneymanProjectileEntity animatable) {
        return new ResourceLocation(CollabMod.MOD_ID,"textures/projectile/honeyman_projectile.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HoneymanProjectileEntity animatable) {
        return new ResourceLocation(CollabMod.MOD_ID,"animations/honeyman_projectile.animation.json");
    }

}
