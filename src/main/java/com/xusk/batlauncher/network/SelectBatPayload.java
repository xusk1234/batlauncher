package com.xusk.batlauncher.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record SelectBatPayload(BlockPos pos, String batName) implements CustomPayload {
    public static final Id<SelectBatPayload> ID = new Id<>(Identifier.of("batlauncher", "select_bat"));
    public static final PacketCodec<RegistryByteBuf, SelectBatPayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, SelectBatPayload::pos,
            PacketCodecs.STRING, SelectBatPayload::batName,
            SelectBatPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}