package com.suppergerrie2.chonkysheep.entities.rendering;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class ChonkySheepWoolFeatureRenderer extends FeatureRenderer<SheepEntity, ChonkySheepEntityModel<SheepEntity>> {
    private static final Identifier                              SKIN = new Identifier("textures/entity/sheep/sheep_fur.png");
    private final        ChonkySheepWoolEntityModel model;

    public ChonkySheepWoolFeatureRenderer(
            FeatureRendererContext<SheepEntity, ChonkySheepEntityModel<SheepEntity>> context,
            EntityModelLoader loader) {
        super(context);
        this.model = new ChonkySheepWoolEntityModel(loader.getModelPart(EntityModelLayers.SHEEP_FUR));
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light,
                       SheepEntity chonkySheepEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (chonkySheepEntity.isSheared()) {
            return;
        }

        if (chonkySheepEntity.isInvisible()) {
            if (MinecraftClient.getInstance().hasOutline(chonkySheepEntity)) {
                this.getContextModel()
                    .copyStateTo(this.model);
                this.model.animateModel(chonkySheepEntity, limbAngle, limbDistance, tickDelta);
                this.model.setAngles(chonkySheepEntity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
                VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getOutline(SKIN));
                this.model.render(matrixStack, vertexConsumer, light,
                                  LivingEntityRenderer.getOverlay(chonkySheepEntity, 0.0F), 0.0F, 0.0F, 0.0F, 1.0F);
            }

        } else {
            float r;
            float g;
            float b;
            if (chonkySheepEntity.hasCustomName() && "jeb_".equals(chonkySheepEntity.getName()
                                                                                    .asString())) {
                int     time  = chonkySheepEntity.age / 25 + chonkySheepEntity.getId();
                int     dyeAmount  = DyeColor.values().length;
                int     dyeId  = time % dyeAmount;
                int     nextDyeId  = (time + 1) % dyeAmount;
                float   percentage  = ((float) (chonkySheepEntity.age % 25) + tickDelta) / 25.0F;
                float[] currentColor = SheepEntity.getRgbColor(DyeColor.byId(dyeId));
                float[] nextColor = SheepEntity.getRgbColor(DyeColor.byId(nextDyeId));
                r = currentColor[0] * (1.0F - percentage) + nextColor[0] * percentage;
                g = currentColor[1] * (1.0F - percentage) + nextColor[1] * percentage;
                b = currentColor[2] * (1.0F - percentage) + nextColor[2] * percentage;
            } else {
                float[] rgb = SheepEntity.getRgbColor(chonkySheepEntity.getColor());
                r = rgb[0];
                g = rgb[1];
                b = rgb[2];
            }

            matrixStack.push();

            render(this.getContextModel(), this.model, SKIN, matrixStack, vertexConsumerProvider, light,
                   chonkySheepEntity, limbAngle, limbDistance, animationProgress, headYaw, headPitch, tickDelta, r, g, b);

            matrixStack.pop();
        }
    }

}
