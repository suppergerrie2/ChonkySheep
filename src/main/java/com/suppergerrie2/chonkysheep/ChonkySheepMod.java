package com.suppergerrie2.chonkysheep;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class ChonkySheepMod implements ModInitializer {

    public static final String MOD_ID = "schonkysheep";
    public static ChonkySheepConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(ChonkySheepConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ChonkySheepConfig.class).getConfig();
    }

    public static int getMaxChonkyness() {
        return config.maxChonkyness;
    }
}
