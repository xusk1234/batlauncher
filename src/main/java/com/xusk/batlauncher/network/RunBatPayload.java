package com.xusk.batlauncher.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record RunBatPayload(String batName) implements CustomPayload {
    public static final Id<RunBatPayload> ID = new Id<>(Identifier.of("batlauncher", "run_bat"));
    public static final PacketCodec<RegistryByteBuf, RunBatPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.STRING, RunBatPayload::batName,
            RunBatPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}