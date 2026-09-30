package com.xusk.batlauncher.network;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record OpenSelectScreenPayload(BlockPos pos) implements CustomPayload {
    public static final Id<OpenSelectScreenPayload> ID = new Id<>(Identifier.of("batlauncher", "open_select_screen"));
    public static final PacketCodec<RegistryByteBuf, OpenSelectScreenPayload> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, OpenSelectScreenPayload::pos,
            OpenSelectScreenPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }
}