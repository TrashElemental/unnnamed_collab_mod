package net.team_us.collab_mod.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.team_us.collab_mod.CollabMod;
import net.team_us.collab_mod.entity.custom.minions.BeeMinionEntity;
import net.team_us.collab_mod.entity.custom.minions.LarvaMinionEntity;
import software.bernie.geckolib.model.GeoModel;

public class LarvaMinionModel extends GeoModel<LarvaMinionEntity> {
    @Override
    public ResourceLocation getModelResource(LarvaMinionEntity bee) {
        return new ResourceLocation(CollabMod.MOD_ID, "geo/models/entities/grub.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LarvaMinionEntity bee) {
        return new ResourceLocation(CollabMod.MOD_ID,"textures/entities/grub.png");
    }

    @Override
    public ResourceLocation getAnimationResource(LarvaMinionEntity bee) {
        return new ResourceLocation(CollabMod.MOD_ID, "animations/grub.animation.json");
    }
}
