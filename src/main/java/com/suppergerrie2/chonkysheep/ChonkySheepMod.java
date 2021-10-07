package com.suppergerrie2.chonkysheep;

import com.google.gson.GsonBuilder;
import com.suppergerrie2.chonkysheep.entities.ChonkySheepEntity;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ChonkySheepMod implements ModInitializer {

    public static final String MOD_ID = "schonkysheep";
    public static ChonkySheepConfig config;

    // @formatter:off
	public static final EntityType<ChonkySheepEntity> CHONKY_SHEEP = Registry.register(
				Registry.ENTITY_TYPE,
				new Identifier("minecraft", "sheep"), FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ChonkySheepEntity::new).dimensions(
					EntityDimensions.fixed(1,1)).build()
	);
    //@formatter:on

    @Override
    public void onInitialize() {
        FabricDefaultAttributeRegistry.register(CHONKY_SHEEP, ChonkySheepEntity.createChonkySheepAttributes());
        AutoConfig.register(ChonkySheepConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ChonkySheepConfig.class).getConfig();
    }
}
