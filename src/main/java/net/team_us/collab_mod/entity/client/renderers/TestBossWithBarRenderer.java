package net.team_us.collab_mod.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.team_us.collab_mod.entity.client.models.TestBossWithBarModel;
import net.team_us.collab_mod.entity.custom.bosses.TestBossWithBar;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TestBossWithBarRenderer extends GeoEntityRenderer<TestBossWithBar> {
    public TestBossWithBarRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TestBossWithBarModel());
        this.shadowRadius = 1f;
    }

    @Override
    public ResourceLocation getTextureLocation(TestBossWithBar animatable) {
        return animatable.getTexture();
    }

    @Override
    public void render(TestBossWithBar entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.scale(1f, 1f, 1f);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
