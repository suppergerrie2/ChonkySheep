package com.suppergerrie2.chonkysheep.entities.rendering;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ChonkySheepRenderer extends MobEntityRenderer<SheepEntity, ChonkySheepEntityModel<SheepEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/sheep/sheep.png");

    public ChonkySheepRenderer(EntityRendererFactory.Context context) {
        super(context, new ChonkySheepEntityModel<>(context.getPart(EntityModelLayers.SHEEP)), 0.7f);
        this.addFeature(new ChonkySheepWoolFeatureRenderer(this, context.getModelLoader()));
    }

    @Override
    public Identifier getTexture(SheepEntity entity) {
        return TEXTURE;
    }
}
