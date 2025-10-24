package net.team_us.collab_mod.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.team_us.collab_mod.CollabMod;
import net.team_us.collab_mod.entity.client.models.HoneymanProjectileModel;
import net.team_us.collab_mod.entity.custom.projectiles.HoneymanProjectileEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HoneymanProjectileRenderer extends GeoEntityRenderer<HoneymanProjectileEntity> {
    public HoneymanProjectileRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new HoneymanProjectileModel());
    }

    @Override
    public ResourceLocation getTextureLocation(HoneymanProjectileEntity animatable) {
        return new ResourceLocation(CollabMod.MOD_ID,"textures/projectile/honeyman_projectile.png");
    }

    @Override
    public void render(HoneymanProjectileEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        float lerpedYaw = Mth.lerp(partialTick, entity.yRotO, entity.getYRot());
        float lerpedPitch = Mth.lerp(partialTick, entity.xRotO, entity.getXRot());

        poseStack.mulPose(Axis.YP.rotationDegrees(lerpedYaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(-lerpedPitch));

        poseStack.scale(0.5f, 0.5f, 0.5f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        poseStack.popPose();
    }
}
