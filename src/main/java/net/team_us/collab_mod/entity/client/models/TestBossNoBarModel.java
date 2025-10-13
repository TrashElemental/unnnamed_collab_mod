package net.team_us.collab_mod.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.team_us.collab_mod.entity.custom.bosses.TestBossNoBar;
import software.bernie.geckolib.model.GeoModel;

public class TestBossNoBarModel extends GeoModel<TestBossNoBar> {

    @Override
    public ResourceLocation getModelResource(TestBossNoBar animatable) {
        return animatable.getModelLocation();
    }

    @Override
    public ResourceLocation getTextureResource(TestBossNoBar animatable) {
        return animatable.getTexture();
    }

    @Override
    public ResourceLocation getAnimationResource(TestBossNoBar animatable) {
        return animatable.getAnimationLocation();
    }

}
