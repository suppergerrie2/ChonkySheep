package com.suppergerrie2.chonkysheep.entities.rendering;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.entity.passive.SheepEntity;

public class ChonkySheepEntityModel<T extends SheepEntity> extends QuadrupedEntityModel<T> {

    private float headPitchModifier;

    public ChonkySheepEntityModel(ModelPart root) {
        super(root, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
    }

    public void animateModel(T sheepEntity, float f, float g, float h) {
        super.animateModel(sheepEntity, f, g, h);
        this.head.pivotY       = 6.0F + sheepEntity.getNeckAngle(h) * 9.0F;
        this.headPitchModifier = sheepEntity.getHeadAngle(h);
    }

    public void setAngles(T sheepEntity, float f, float g, float h, float i, float j) {
        super.setAngles(sheepEntity, f, g, h, i, j);
        this.head.pitch = this.headPitchModifier;
    }

}
