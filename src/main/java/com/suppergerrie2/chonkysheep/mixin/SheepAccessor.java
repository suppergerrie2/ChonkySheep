package com.suppergerrie2.chonkysheep.mixin;

import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(SheepEntity.class)
public interface SheepAccessor {

    @Accessor("DROPS")
    static Map<DyeColor, ItemConvertible> getDrops() {
        throw new AssertionError();
    }

}
