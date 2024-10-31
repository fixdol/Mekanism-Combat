package com.fxd927.mekanismcombat.common.registries;

import com.fxd927.mekanismcombat.common.MekanismCombat;
import com.fxd927.mekanismcombat.common.block.NeutronBomb;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MCBlocks {
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(MekanismCombat.MODID);

    public static final BlockRegistryObject<Block, BlockItem> NEUTRON_BOMB;

    static {
        NEUTRON_BOMB = BLOCKS.register("neutron_bomb", () -> new NeutronBomb(BlockBehaviour.Properties.of()));
    }
}
