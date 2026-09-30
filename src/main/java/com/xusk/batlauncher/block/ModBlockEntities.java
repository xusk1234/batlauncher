package com.xusk.batlauncher.block;

import com.xusk.batlauncher.Batlauncher;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static final BlockEntityType<BatRunnerBlockEntity> BAT_RUNNER =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of("batlauncher", "bat_runner"),
                    FabricBlockEntityTypeBuilder.create(BatRunnerBlockEntity::new, Batlauncher.BAT_RUNNER_BLOCK).build());

    public static void register() {
        Batlauncher.LOGGER.info("注册方块实体");
    }
}