package com.xusk.batlauncher.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

public class BatRunnerBlockEntity extends BlockEntity {
    private String selectedBat = null;
    private boolean wasPowered = false;

    public BatRunnerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BAT_RUNNER, pos, state);
    }

    public String getSelectedBat() { return selectedBat; }
    public void setSelectedBat(String name) { this.selectedBat = name; markDirty(); }

    public boolean wasPowered() { return wasPowered; }
    public void setWasPowered(boolean v) { this.wasPowered = v; }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        if (selectedBat != null) nbt.putString("SelectedBat", selectedBat);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        if (nbt.contains("SelectedBat")) selectedBat = nbt.getString("SelectedBat");
    }
}