package com.xusk.batlauncher.client.screen;

import com.xusk.batlauncher.network.SelectBatPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BatSelectScreen extends Screen {
    private final BlockPos pos;
    private List<String> batFiles = new ArrayList<>();

    public BatSelectScreen(BlockPos pos) {
        super(Text.literal("选择要运行的脚本"));
        this.pos = pos;
    }

    @Override
    protected void init() {
        super.init();
        Path batDir = FabricLoader.getInstance().getConfigDir().resolve("batlauncher");
        try {
            Files.createDirectories(batDir);
            try (Stream<Path> stream = Files.list(batDir)) {
                batFiles = stream.filter(p -> p.toString().toLowerCase().endsWith(".bat"))
                        .map(p -> p.getFileName().toString()).sorted().toList();
            }
        } catch (IOException e) {
            batFiles = List.of();
        }

        int startY = this.height / 2 - (batFiles.size() * 24) / 2;
        for (int i = 0; i < batFiles.size(); i++) {
            String name = batFiles.get(i);
            this.addDrawableChild(ButtonWidget.builder(Text.literal(name), btn -> {
                ClientPlayNetworking.send(new SelectBatPayload(pos, name));
                this.close();
            }).dimensions(this.width / 2 - 100, startY + i * 24, 200, 20).build());
        }

        this.addDrawableChild(ButtonWidget.builder(Text.literal("关闭"), btn -> this.close())
                .dimensions(this.width / 2 - 50, this.height - 40, 100, 20).build());
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx, mouseX, mouseY, delta);
        ctx.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        if (batFiles.isEmpty()) {
            ctx.drawCenteredTextWithShadow(this.textRenderer, Text.literal("§c没有找到bat文件，请放入 config/batlauncher/"), this.width / 2, this.height / 2, 0xFFFFFF);
        }
        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() { return false; }
}