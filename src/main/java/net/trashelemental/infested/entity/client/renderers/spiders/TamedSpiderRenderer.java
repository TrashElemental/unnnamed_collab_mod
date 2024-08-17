package net.trashelemental.infested.entity.client.renderers.spiders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.entity.custom.spiders.AttackSpiderEntity;
import net.trashelemental.infested.entity.custom.spiders.TamedSpiderEntity;
import net.trashelemental.infested.infested;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TamedSpiderRenderer extends MobRenderer<TamedSpiderEntity, SpiderModel<TamedSpiderEntity>> {
    private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(infested.MOD_ID, "textures/entity/cave_spider.png");
    private static final Map<String, ResourceLocation> EYE_TEXTURES = new HashMap<>();

    static {
        EYE_TEXTURES.put("BLACK", new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_black.png"));
        EYE_TEXTURES.put("BLUE", new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_blue.png"));
        EYE_TEXTURES.put("BROWN", new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_brown.png"));
        EYE_TEXTURES.put("CYAN", new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_cyan.png"));
        EYE_TEXTURES.put("GRAY", new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_gray.png"));
        EYE_TEXTURES.put("GREEN", new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_green.png"));
        EYE_TEXTURES.put("LIGHT_BLUE", new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_light_blue.png"));
        EYE_TEXTURES.put("LIGHT_GRAY", new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_light_gray.png"));
        EYE_TEXTURES.put("LIME", new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_lime.png"));
        EYE_TEXTURES.put("MAGENTA", new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_magenta.png"));
        EYE_TEXTURES.put("ORANGE", new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_orange.png"));
        EYE_TEXTURES.put("PINK", new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_pink.png"));
        EYE_TEXTURES.put("PURPLE", new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_purple.png"));
        EYE_TEXTURES.put("RED", new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_red.png"));
        EYE_TEXTURES.put("WHITE", new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_white.png"));
        EYE_TEXTURES.put("YELLOW", new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_yellow.png"));
    }

    public TamedSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, new SpiderModel<>(context.bakeLayer(ModelLayers.SPIDER)), 0.5f);
        this.addLayer(new EmissiveLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(TamedSpiderEntity entity) {
        return DEFAULT_TEXTURE;
    }

    private static class EmissiveLayer<T extends TamedSpiderEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

        public EmissiveLayer(RenderLayerParent<T, M> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            String eyeColor = entity.getEyeColor();
            ResourceLocation texture = EYE_TEXTURES.getOrDefault(eyeColor, EYE_TEXTURES.get("GREEN"));

            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.eyes(texture));
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}

