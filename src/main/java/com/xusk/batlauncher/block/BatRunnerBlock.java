package com.xusk.batlauncher.block;

import com.mojang.serialization.MapCodec;
import com.xusk.batlauncher.network.OpenSelectScreenPayload;
import com.xusk.batlauncher.network.RunBatPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BatRunnerBlock extends BlockWithEntity {
    public static final MapCodec<BatRunnerBlock> CODEC = createCodec(BatRunnerBlock::new);

    public BatRunnerBlock(Settings settings) { super(settings); }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BatRunnerBlockEntity(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient && player instanceof ServerPlayerEntity serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, new OpenSelectScreenPayload(pos));
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            boolean powered = world.isReceivingRedstonePower(pos);
            BlockEntity be = world.getBlockEntity(pos);
            if (be instanceof BatRunnerBlockEntity batBE) {
                if (powered && !batBE.wasPowered() && batBE.getSelectedBat() != null) {
                    for (PlayerEntity p : world.getPlayers()) {
                        if (p instanceof ServerPlayerEntity serverPlayer) {
                            if (serverPlayer.squaredDistanceTo(pos.toCenterPos()) < 64 * 64) {
                                ServerPlayNetworking.send(serverPlayer, new RunBatPayload(batBE.getSelectedBat()));
                            }
                        }
                    }
                }
                batBE.setWasPowered(powered);
            }
        }
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) { return false; }
}