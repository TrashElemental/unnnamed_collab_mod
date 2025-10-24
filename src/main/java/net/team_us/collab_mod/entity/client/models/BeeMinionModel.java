package net.team_us.collab_mod.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.team_us.collab_mod.CollabMod;
import net.team_us.collab_mod.entity.custom.minions.BeeMinionEntity;
import software.bernie.geckolib.model.GeoModel;

public class BeeMinionModel extends GeoModel<BeeMinionEntity> {
    @Override
    public ResourceLocation getModelResource(BeeMinionEntity bee) {
        return new ResourceLocation(CollabMod.MOD_ID, "geo/models/entities/bee.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BeeMinionEntity bee) {
        return new ResourceLocation(CollabMod.MOD_ID,"textures/entities/bee.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BeeMinionEntity bee) {
        return new ResourceLocation(CollabMod.MOD_ID, "animations/bee.animation.json");
    }
}
