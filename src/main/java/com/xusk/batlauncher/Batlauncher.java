package com.xusk.batlauncher;

import com.xusk.batlauncher.block.BatRunnerBlock;
import com.xusk.batlauncher.block.ModBlockEntities;
import com.xusk.batlauncher.network.OpenSelectScreenPayload;
import com.xusk.batlauncher.network.RunBatPayload;
import com.xusk.batlauncher.network.SelectBatPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Batlauncher implements ModInitializer {
    public static final String MOD_ID = "batlauncher";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final BatRunnerBlock BAT_RUNNER_BLOCK = new BatRunnerBlock(Block.Settings.create().strength(4.0f));

    @Override
    public void onInitialize() {
        // 注册方块和物品
        Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, "bat_runner"), BAT_RUNNER_BLOCK);
        Registry.register(Registries.ITEM, Identifier.of(MOD_ID, "bat_runner"), new BlockItem(BAT_RUNNER_BLOCK, new Item.Settings()));

        // 注册方块实体
        ModBlockEntities.register();

        // 注册数据包
        PayloadTypeRegistry.playC2S().register(SelectBatPayload.ID, SelectBatPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(RunBatPayload.ID, RunBatPayload.CODEC);

        // 服务端接收"选择bat"数据包
        ServerPlayNetworking.registerGlobalReceiver(SelectBatPayload.ID, (payload, context) -> {
            context.server().execute(() -> {
                var world = context.player().getServerWorld();
                var be = world.getBlockEntity(payload.pos());
                if (be instanceof com.xusk.batlauncher.block.BatRunnerBlockEntity batBE) {
                    batBE.setSelectedBat(payload.batName());
                }
            });
        });
        PayloadTypeRegistry.playS2C().register(OpenSelectScreenPayload.ID, OpenSelectScreenPayload.CODEC);
    }
}