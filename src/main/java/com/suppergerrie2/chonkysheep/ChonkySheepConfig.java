package com.suppergerrie2.chonkysheep;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = ChonkySheepMod.MOD_ID)
public class ChonkySheepConfig implements ConfigData {
    @ConfigEntry.BoundedDiscrete(min=0, max=64)
    public int maxChonkyness = 20;
}
