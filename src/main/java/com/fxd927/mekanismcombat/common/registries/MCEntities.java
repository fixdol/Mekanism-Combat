package com.fxd927.mekanismcombat.common.registries;

import com.fxd927.mekanismcombat.common.MekanismCombat;
import com.fxd927.mekanismcombat.common.entity.NeutronBombEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MCEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MekanismCombat.MODID);

    public static final RegistryObject<BlockEntityType<NeutronBombEntity>> NEUTRON_BOMB = BLOCK_ENTITIES.register("neutron_bomb",
            () -> BlockEntityType.Builder.of(NeutronBombEntity::new, MCBlocks.NEUTRON_BOMB.getBlock()).build(null));
}

