package net.team_us.collab_mod.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.team_us.collab_mod.entity.custom.bosses.HoneymanEntity;
import software.bernie.geckolib.model.GeoModel;

public class HoneymanModel extends GeoModel<HoneymanEntity> {

    @Override
    public ResourceLocation getModelResource(HoneymanEntity animatable) {
        return animatable.getModelLocation();
    }

    @Override
    public ResourceLocation getTextureResource(HoneymanEntity animatable) {
        return animatable.getTexture();
    }

    @Override
    public ResourceLocation getAnimationResource(HoneymanEntity animatable) {
        return animatable.getAnimationLocation();
    }

}
