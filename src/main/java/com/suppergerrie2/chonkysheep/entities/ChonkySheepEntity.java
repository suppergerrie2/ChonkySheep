package com.suppergerrie2.chonkysheep.entities;

import com.google.common.collect.Maps;
import com.suppergerrie2.chonkysheep.ChonkySheepMod;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.data.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Util;
import net.minecraft.world.World;

import java.util.*;

public class ChonkySheepEntity extends SheepEntity {

    private static final TrackedData<Integer>           CHONKYNESS     = DataTracker.registerData(
            ChonkySheepEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final Map<DyeColor, ItemConvertible> DROPS;

    static {
        DROPS = Util.make(Maps.newEnumMap(DyeColor.class), (map) -> {
            map.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
            map.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
            map.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
            map.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
            map.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
            map.put(DyeColor.LIME, Blocks.LIME_WOOL);
            map.put(DyeColor.PINK, Blocks.PINK_WOOL);
            map.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
            map.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
            map.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
            map.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
            map.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
            map.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
            map.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
            map.put(DyeColor.RED, Blocks.RED_WOOL);
            map.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
        });
    }

    public ChonkySheepEntity(EntityType<? extends SheepEntity> entityType, World world) {
        super(entityType, world);
    }

    private static CraftingInventory createDyeMixingCraftingInventory(DyeColor firstColor, DyeColor secondColor) {
        CraftingInventory craftingInventory = new CraftingInventory(new ScreenHandler(null, -1) {
            public boolean canUse(PlayerEntity player) {
                return false;
            }
        }, 2, 1);
        craftingInventory.setStack(0, new ItemStack(DyeItem.byColor(firstColor)));
        craftingInventory.setStack(1, new ItemStack(DyeItem.byColor(secondColor)));
        return craftingInventory;
    }

    public static DefaultAttributeContainer.Builder createChonkySheepAttributes() {
        return createSheepAttributes();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CHONKYNESS, 0);
    }

    @Override
    public void sheared(SoundCategory shearedSoundCategory) {
        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        this.setSheared(true);
        int i = getChonkyness() + this.random.nextInt(getChonkyness() / 2);

        setChonkyness(0);

        for (int j = 0; j < i; ++j) {
            ItemEntity itemEntity = this.dropItem(DROPS.get(this.getColor()), 1);
            if (itemEntity != null) {
                itemEntity.setVelocity(itemEntity.getVelocity()
                                                 .add((this.random.nextFloat() - this.random.nextFloat()) * 0.1F,
                                                      this.random.nextFloat() * 0.05F,
                                                      (this.random.nextFloat() - this.random.nextFloat()) * 0.1F));
            }
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("chonkyness", getChonkyness());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setChonkyness(nbt.getInt("chonkyness"));
    }

    @Override
    public SheepEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        ChonkySheepEntity sheepEntity = (ChonkySheepEntity) passiveEntity;
        ChonkySheepEntity child       = ChonkySheepMod.CHONKY_SHEEP.create(serverWorld);
        Objects.requireNonNull(child)
               .setColor(getChildColor(this, sheepEntity));
        return child;
    }

    @Override
    public void onEatingGrass() {
        if (!isSheared()) {
            setChonkyness(getChonkyness() + 1);
        }

        super.onEatingGrass();
    }

    public int getChonkyness() {
        return Math.min(this.dataTracker.get(CHONKYNESS), getMaxChonkyness());
    }

    public void setChonkyness(int chonkyness) {
        chonkyness = Math.min(getMaxChonkyness(), chonkyness);
        this.dataTracker.set(CHONKYNESS, chonkyness);
    }

    private DyeColor getChildColor(AnimalEntity firstParent, AnimalEntity secondParent) {
        DyeColor          firstParentColor  = ((SheepEntity) firstParent).getColor();
        DyeColor          secondParentColor = ((SheepEntity) secondParent).getColor();
        CraftingInventory craftingInventory = createDyeMixingCraftingInventory(firstParentColor, secondParentColor);
        Optional<Item> dyeItem = this.world.getRecipeManager()
                                           .getFirstMatch(RecipeType.CRAFTING, craftingInventory, this.world)
                                           .map((craftingRecipe) -> craftingRecipe.craft(craftingInventory))
                                           .map(ItemStack::getItem);

        dyeItem = dyeItem.filter(DyeItem.class::isInstance);
        return dyeItem.map(DyeItem.class::cast)
                      .map(DyeItem::getColor)
                      .orElseGet(() -> this.world.random.nextBoolean() ? firstParentColor : secondParentColor);
    }

    public static int getMaxChonkyness() {
        return ChonkySheepMod.config.maxChonkyness;
    }
}
