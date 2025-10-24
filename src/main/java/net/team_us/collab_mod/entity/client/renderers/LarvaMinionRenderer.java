package net.team_us.collab_mod.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.team_us.collab_mod.CollabMod;
import net.team_us.collab_mod.entity.client.models.BeeMinionModel;
import net.team_us.collab_mod.entity.client.models.LarvaMinionModel;
import net.team_us.collab_mod.entity.custom.minions.BeeMinionEntity;
import net.team_us.collab_mod.entity.custom.minions.LarvaMinionEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LarvaMinionRenderer extends GeoEntityRenderer<LarvaMinionEntity> {

    public LarvaMinionRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LarvaMinionModel());
        this.shadowRadius = 0.2f;
    }

    @Override
    public ResourceLocation getTextureLocation(LarvaMinionEntity animatable) {
        return new ResourceLocation(CollabMod.MOD_ID,"textures/entity/grub.png");
    }

    @Override
    public void render(LarvaMinionEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
