package com.xusk.batlauncher.client;

import com.xusk.batlauncher.client.screen.BatSelectScreen;
import com.xusk.batlauncher.network.OpenSelectScreenPayload;
import com.xusk.batlauncher.network.RunBatPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class BatlauncherClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // 收到“打开选择界面”的包
        ClientPlayNetworking.registerGlobalReceiver(OpenSelectScreenPayload.ID, (payload, context) -> {
            context.client().execute(() -> {
                context.client().setScreen(new BatSelectScreen(payload.pos()));
            });
        });

        // 收到“执行bat”的包
        ClientPlayNetworking.registerGlobalReceiver(RunBatPayload.ID, (payload, context) -> {
            context.client().execute(() -> BatRunner.run(payload.batName()));
        });
    }
}