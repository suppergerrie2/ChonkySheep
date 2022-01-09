package com.suppergerrie2.chonkysheep.mixin;

import com.suppergerrie2.chonkysheep.ChonkySheepMod;
import com.suppergerrie2.chonkysheep.IChonkyness;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.data.*;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SheepEntity.class)
public class SheepMixin implements IChonkyness {

    private static final TrackedData<Integer>           CHONKYNESS = DataTracker.registerData(
            SheepEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void injectDataTracker(CallbackInfo ci) {
        ((SheepEntity) ((Object) this)).getDataTracker().startTracking(CHONKYNESS, 0);
    }

    @Inject(method = "sheared", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/SheepEntity;setSheared(Z)V"), cancellable = true)
    private void injectSheared(CallbackInfo ci) {
        ci.cancel();

        SheepEntity thus = ((SheepEntity) ((Object) this));

        int i = getChonkyness() + thus.getRandom().nextInt(getChonkyness() / 2);

        setChonkyness(0);

        for (int j = 0; j < i; ++j) {
            ItemEntity itemEntity = thus.dropItem(SheepAccessor.getDrops().get(thus.getColor()), 1);
            if (itemEntity != null) {
                itemEntity.setVelocity(itemEntity.getVelocity()
                                                 .add((thus.getRandom().nextFloat() - thus.getRandom().nextFloat()) * 0.1F,
                                                      thus.getRandom().nextFloat() * 0.05F,
                                                      (thus.getRandom().nextFloat() - thus.getRandom().nextFloat()) * 0.1F));
            }
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void injectWriteCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("chonkyness", getChonkyness());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void injectReadCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        setChonkyness(nbt.getInt("chonkyness"));
    }

    @Inject(method = "onEatingGrass", at = @At("HEAD"))
    private void injectOnEatingGrass(CallbackInfo ci) {
        if (!((SheepEntity) ((Object) this)).isSheared()) {
            setChonkyness(getChonkyness() + 1);
        }
    }

    public int getChonkyness() {
        return Math.min(((SheepEntity) ((Object) this)).getDataTracker().get(CHONKYNESS), ChonkySheepMod.getMaxChonkyness());
    }

    public void setChonkyness(int chonkyness) {
        chonkyness = Math.min(ChonkySheepMod.getMaxChonkyness(), chonkyness);
        ((SheepEntity) ((Object) this)).getDataTracker().set(CHONKYNESS, chonkyness);
    }
}
