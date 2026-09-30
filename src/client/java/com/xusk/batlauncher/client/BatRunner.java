package com.xusk.batlauncher.client;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class BatRunner {

    public static void run(String batName) {
        Path batDir = FabricLoader.getInstance().getConfigDir().resolve("batlauncher");
        Path batFile = batDir.resolve(batName);

        if (!Files.exists(batFile)) {
            sendChat("§c[bat] 找不到文件: " + batName);
            return;
        }

        // 在新线程中执行，避免阻塞游戏
        Thread t = new Thread(() -> {
            try {
                ProcessBuilder pb = new ProcessBuilder(
                        "cmd.exe", "/c", batFile.toAbsolutePath().toString()
                );
                pb.directory(batDir.toFile());
                pb.redirectErrorStream(true); // 合并错误流到输出流

                Process process = pb.start();

                // 中文 Windows 控制台默认编码是 GBK，避免乱码
                Charset cs = Charset.forName(System.getProperty("sun.jnu.encoding", "GBK"));
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream(), cs))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        final String out = line;
                        sendChat("§7[bat] " + out);
                    }
                }

                int exit = process.waitFor();
                sendChat(exit == 0
                        ? "§a[bat] 执行完成"
                        : "§e[bat] 执行结束，退出码: " + exit);

            } catch (Exception e) {
                sendChat("§c[bat] 执行失败: " + e.getMessage());
            }
        }, "BatRunner");
        t.setDaemon(true);
        t.start();
    }

    private static void sendChat(String msg) {
        MinecraftClient.getInstance().execute(() -> {
            if (MinecraftClient.getInstance().player != null) {
                MinecraftClient.getInstance().player.sendMessage(Text.literal(msg), false);
            }
        });
    }
}