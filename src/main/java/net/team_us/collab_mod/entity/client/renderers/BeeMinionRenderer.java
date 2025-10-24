package net.team_us.collab_mod.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.team_us.collab_mod.CollabMod;
import net.team_us.collab_mod.entity.client.models.BeeMinionModel;
import net.team_us.collab_mod.entity.custom.minions.BeeMinionEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BeeMinionRenderer extends GeoEntityRenderer<BeeMinionEntity> {

    public BeeMinionRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BeeMinionModel());
        this.shadowRadius = 0.4f;
    }

    @Override
    public ResourceLocation getTextureLocation(BeeMinionEntity animatable) {
        return new ResourceLocation(CollabMod.MOD_ID,"textures/entity/bee.png");
    }

    @Override
    public void render(BeeMinionEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
