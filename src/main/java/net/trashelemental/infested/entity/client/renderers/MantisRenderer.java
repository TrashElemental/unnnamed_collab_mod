package net.trashelemental.infested.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.entity.ModEventBusEvents;
import net.trashelemental.infested.entity.client.models.MantisModel;
import net.trashelemental.infested.entity.custom.MantisEntity;
import net.trashelemental.infested.infested;

public class MantisRenderer extends MobRenderer<MantisEntity, MantisModel<MantisEntity>> {
    public MantisRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new MantisModel<>(pContext.bakeLayer(ModEventBusEvents.MANTIS_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MantisEntity MantisEntity) {
        return new ResourceLocation(infested.MOD_ID, "textures/entity/mantis.png");
    }

    @Override
    public void render(MantisEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        float scale = 1.0f;
        if (pEntity.isBaby()) { scale = 0.5f; }
        pPoseStack.pushPose();
        pPoseStack.scale(scale, scale, scale);
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.popPose();
    }
}
