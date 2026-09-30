package com.xusk.batlauncher.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    // 1. 声明并初始化方块实例
    // 1.20.5 及以上版本用 Block.Settings.create()
    // 1.20.5 以下版本用 FabricBlockSettings.create()
    public static final Block MY_BLOCK = new Block(Block.Settings.create().strength(4.0f));

    // 2. 编写注册方法
    public static void register() {
        registerBlock("batlauncher", MY_BLOCK);
    }

    private static void registerBlock(String name, Block block) {
        Identifier id = Identifier.of("com.xusk.batlauncher", name);
        // 注册方块
        Registry.register(Registries.BLOCK, id, block);
        // 注册对应的方块物品，使其能在物品栏中找到
        Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
    }
}