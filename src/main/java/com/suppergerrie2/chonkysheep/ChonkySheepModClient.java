package com.suppergerrie2.chonkysheep;

import com.suppergerrie2.chonkysheep.entities.rendering.ChonkySheepRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.entity.EntityType;

public class ChonkySheepModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(EntityType.SHEEP, ChonkySheepRenderer::new);
    }
}
