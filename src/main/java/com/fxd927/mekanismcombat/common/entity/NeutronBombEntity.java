package com.fxd927.mekanismcombat.common.entity;

import com.fxd927.mekanismcombat.common.item.gear.ItemConcreteArmor;
import com.fxd927.mekanismcombat.common.registries.MCEntities;
import mekanism.api.Coord4D;
import mekanism.api.radiation.IRadiationManager;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.registries.MekanismSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class NeutronBombEntity extends BlockEntity {
    private int timer = 0;
    private boolean activated = false;

    public NeutronBombEntity(BlockPos pos, BlockState state) {
        super(MCEntities.NEUTRON_BOMB.get(), pos, state);
    }

    public void activate() {
        if (!activated) {
            activated = true;
            this.timer = 0;
            if (this.level != null) {
                this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
            }
        }
    }

    public void tick() {
        if (!this.level.isClientSide && activated) {
            timer++;

            if (timer % 28  == 0) {
                playAlarmSound();
            }

            if (timer >= 200) {
                triggerRadiation(level, this.worldPosition, 5);
                this.level.removeBlock(this.worldPosition, false);
            }
        }
    }

    private void playAlarmSound() {
        if (this.level != null) {
            this.level.playSound(null, this.worldPosition, MekanismSounds.INDUSTRIAL_ALARM.get(), SoundSource.BLOCKS, 10.0F, 1.0F);
        }
    }

    private void triggerRadiation(Level level, BlockPos pos, int radius) {
        AABB radiationArea = new AABB(pos.offset(-radius, -radius, -radius), pos.offset(radius, radius, radius));

        List<LivingEntity> entitiesInRange = level.getEntitiesOfClass(LivingEntity.class, radiationArea);
        for (LivingEntity entity : entitiesInRange) {
            double magnitude = 10.0;

            if (entity instanceof Player player && !isPlayerWearingConcreteArmor(player)) {
                forceRadiate(entity, magnitude);
            } else if (!(entity instanceof Player)) {
                forceRadiate(entity, magnitude);
            }
        }

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos targetPos = pos.offset(x, y, z);
                    int blockDistance = (int) Math.sqrt(x * x + y * y + z * z);
                    IRadiationManager.INSTANCE.radiate(new Coord4D(targetPos, level), calculateRadiationLevel(blockDistance));
                }
            }
        }
    }

    private boolean isPlayerWearingConcreteArmor(Player player) {
        ItemStack helmet = player.getInventory().armor.get(3);
        ItemStack chestplate = player.getInventory().armor.get(2);
        ItemStack leggings = player.getInventory().armor.get(1);
        ItemStack boots = player.getInventory().armor.get(0);

        return helmet.getItem() instanceof ItemConcreteArmor ||
                chestplate.getItem() instanceof ItemConcreteArmor ||
                leggings.getItem() instanceof ItemConcreteArmor ||
                boots.getItem() instanceof ItemConcreteArmor;
    }

    private void forceRadiate(LivingEntity entity, double magnitude) {
        entity.getCapability(Capabilities.RADIATION_ENTITY).ifPresent(c -> {
            c.radiate(magnitude);
        });
    }

    private double calculateRadiationLevel(int distance) {
        return 1000.0 / (distance + 1);
    }
}
