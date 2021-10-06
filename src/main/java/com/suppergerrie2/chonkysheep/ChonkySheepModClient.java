package com.suppergerrie2.chonkysheep;

import com.suppergerrie2.chonkysheep.entities.rendering.ChonkySheepRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class ChonkySheepModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(ChonkySheepMod.CHONKY_SHEEP, ChonkySheepRenderer::new);
    }
}
