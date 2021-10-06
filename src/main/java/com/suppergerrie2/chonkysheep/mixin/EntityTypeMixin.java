package com.suppergerrie2.chonkysheep.mixin;

import com.suppergerrie2.chonkysheep.ChonkySheepMod;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityType.class)
public class EntityTypeMixin {

    @Inject(method = "register", at = @At("HEAD"), cancellable = true)
    private static void mixinRegister(String id, EntityType.Builder<?> type,
                                      CallbackInfoReturnable<EntityType<?>> cir) {

        if (id.equals("sheep")) {
            cir.setReturnValue(ChonkySheepMod.CHONKY_SHEEP);
        }

    }

}
