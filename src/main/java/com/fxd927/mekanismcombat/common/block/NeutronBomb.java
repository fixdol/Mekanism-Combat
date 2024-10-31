package com.fxd927.mekanismcombat.common.block;

import com.fxd927.mekanismcombat.common.entity.NeutronBombEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class NeutronBomb extends Block implements EntityBlock {
    public NeutronBomb(Properties properties) {
        super(properties);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.hasNeighborSignal(pos)) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof NeutronBombEntity) {
                ((NeutronBombEntity) be).activate(); // 爆弾を起動
            }
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new NeutronBombEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        // サーバーサイドでのみティックを行う
        return level.isClientSide ? null : (lvl, pos, st, be) -> {
            if (be instanceof NeutronBombEntity bombEntity) {
                bombEntity.tick(); // BlockEntity の tick メソッドを呼び出す
            }
        };
    }
}
