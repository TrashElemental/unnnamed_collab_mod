package net.trashelemental.collab_mod.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.trashelemental.collab_mod.entity.custom.bosses.TestBossWithBar;
import software.bernie.geckolib.model.GeoModel;

public class TestBossWithBarModel extends GeoModel<TestBossWithBar> {

    @Override
    public ResourceLocation getModelResource(TestBossWithBar animatable) {
        return animatable.getModelLocation();
    }

    @Override
    public ResourceLocation getTextureResource(TestBossWithBar animatable) {
        return animatable.getTexture();
    }

    @Override
    public ResourceLocation getAnimationResource(TestBossWithBar animatable) {
        return animatable.getAnimationLocation();
    }

}
