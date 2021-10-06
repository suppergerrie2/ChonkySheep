package com.suppergerrie2.chonkysheep.entities.rendering;

import com.suppergerrie2.chonkysheep.entities.ChonkySheepEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class ChonkySheepWoolEntityModel extends QuadrupedEntityModel<ChonkySheepEntity> {
    private static final float MAX_SCALE = 2;

    final float childHeadYOffset       = 8.0F;
    final float childHeadZOffset       = 4.0F;
    final float invertedChildBodyScale = 2.0F;
    final float childBodyYOffset       = 24;
    float scale = 1;
    private float headAngle;

    public ChonkySheepWoolEntityModel(ModelPart root) {
        super(root, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
    }

    public void animateModel(ChonkySheepEntity sheepEntity, float f, float g, float h) {
        super.animateModel(sheepEntity, f, g, h);
        this.head.pivotY = 6.0F + sheepEntity.getNeckAngle(h) * 9.0F;
        this.headAngle   = sheepEntity.getHeadAngle(h);

        //noinspection PointlessArithmeticExpression
        scale = ((sheepEntity.getChonkyness() / (float) sheepEntity.getMaxChonkyness()) * (MAX_SCALE - 1)) + 1;
    }

    public void setAngles(ChonkySheepEntity sheepEntity, float f, float g, float h, float i, float j) {
        super.setAngles(sheepEntity, f, g, h, i, j);
        this.head.pitch = this.headAngle;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
                       float blue, float alpha) {
        if (this.child) {
            matrices.push();

            matrices.translate(0.0D, this.childHeadYOffset / 16.0F, this.childHeadZOffset / 16.0F);
            this.getHeadParts()
                .forEach((headPart) -> headPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));
            matrices.pop();
            matrices.push();
            float scale = 1.0F / this.invertedChildBodyScale;
            matrices.scale(scale, scale, scale);
            matrices.translate(0.0D, this.childBodyYOffset / 16.0F, 0.0D);
            this.getBodyParts()
                .forEach((bodyPart) -> bodyPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));
            matrices.pop();
        } else {
            this.renderWithScale(this.head, scale, scale, 1, matrices, vertices, light, overlay, red, green, blue,
                                 alpha);
            this.renderWithScale(this.body, scale, scale, 1, 0.5f, matrices, vertices, light, overlay, red, green, blue,
                                 alpha);
            this.renderWithScale(this.leftFrontLeg, scale, 1, scale, matrices, vertices, light, overlay, red, green,
                                 blue, alpha);
            this.renderWithScale(this.rightFrontLeg, scale, 1, scale, matrices, vertices, light, overlay, red, green,
                                 blue, alpha);
            this.renderWithScale(this.leftHindLeg, scale, 1, scale, matrices, vertices, light, overlay, red, green,
                                 blue, alpha);
            this.renderWithScale(this.rightHindLeg, scale, 1, scale, matrices, vertices, light, overlay, red, green,
                                 blue, alpha);
        }
    }

    private void renderWithScale(ModelPart model, float xScale, float yScale, float zScale, MatrixStack matrices,
                                 VertexConsumer vertices, int light, int overlay, float red, float green, float blue,
                                 float alpha) {
        matrices.push();
        matrices.translate(model.pivotX / 16f, model.pivotY / 16f, model.pivotZ / 16f);
        matrices.scale(xScale, yScale, zScale);
        matrices.translate(-model.pivotX / 16f, -model.pivotY / 16f, -model.pivotZ / 16f);
        model.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        matrices.pop();
    }

    private void renderWithScale(ModelPart model, float xScale, float yScale, float zScale, float yOffset,
                                 MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red,
                                 float green, float blue, float alpha) {
        matrices.push();
        matrices.translate(model.pivotX / 16f, model.pivotY / 16f + yOffset, model.pivotZ / 16f);
        matrices.scale(xScale, yScale, zScale);
        matrices.translate(-model.pivotX / 16f, -model.pivotY / 16f - yOffset, -model.pivotZ / 16f);
        model.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        matrices.pop();
    }

}
