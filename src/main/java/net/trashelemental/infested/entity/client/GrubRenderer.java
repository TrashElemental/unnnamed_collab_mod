package net.trashelemental.infested.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.entity.custom.CrimsonBeetleEntity;
import net.trashelemental.infested.entity.custom.GrubEntity;
import net.trashelemental.infested.infested;

public class GrubRenderer extends MobRenderer<GrubEntity, GrubModel<GrubEntity>> {
    public GrubRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new GrubModel<>(pContext.bakeLayer(ModModelLayers.GRUB_LAYER)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(GrubEntity GrubEntity) {
        return new ResourceLocation(infested.MOD_ID, "textures/entity/grub.png");
    }

    @Override
    public void render(GrubEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {



        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }
}
