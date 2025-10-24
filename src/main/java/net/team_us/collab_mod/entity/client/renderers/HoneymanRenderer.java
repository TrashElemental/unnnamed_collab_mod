package net.team_us.collab_mod.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.team_us.collab_mod.entity.client.models.HoneymanModel;
import net.team_us.collab_mod.entity.custom.bosses.HoneymanEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class HoneymanRenderer extends GeoEntityRenderer<HoneymanEntity> {
    public HoneymanRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HoneymanModel());
        this.shadowRadius = 1f;
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(HoneymanEntity animatable) {
        return animatable.getTexture();
    }

    @Override
    public void render(HoneymanEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(1f, 1f, 1f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
