package com.fxd927.mekanismcombat.common.entity;

import com.fxd927.mekanismcombat.common.item.gear.ItemConcreteArmor;
import com.fxd927.mekanismcombat.common.registries.MCEntities;
import mekanism.api.Coord4D;
import mekanism.api.radiation.IRadiationManager;
import mekanism.api.text.EnumColor;
import mekanism.common.MekanismLang;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.item.gear.ItemHazmatSuitArmor;
import mekanism.common.lib.radiation.RadiationManager;
import mekanism.common.util.UnitDisplayUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class NeutronBombEntity extends BlockEntity {
    private int timer = 0; // タイマー用のカウンタ（ティック）
    private boolean activated = false; // 爆弾が起動したかどうか

    public NeutronBombEntity(BlockPos pos, BlockState state) {
        super(MCEntities.NEUTRON_BOMB.get(), pos, state);
    }

    public void activate() {
        if (!activated) {
            activated = true; // 爆弾を起動
            this.timer = 0; // タイマーをリセット
            // 自動的にティックを進めるための設定
            if (this.level != null) {
                this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
            }
        }
    }

    public void tick() {
        if (!this.level.isClientSide && activated) {
            timer++; // カウントを進める

            // 10秒（200ティック）経過後に放射能を発生させる
            if (timer >= 200) {
                triggerRadiation(level, this.worldPosition, 5); // 放射能の範囲は5ブロック
                this.level.removeBlock(this.worldPosition, false); // 放射能を発生させた後、ブロックを削除
            }
        }
    }

    private void triggerRadiation(Level level, BlockPos pos, int radius) {
        // パーティクル表示処理
        if (!level.isClientSide) {
            int particleCount = 100; // パーティクルの数
            for (int i = 0; i < particleCount; i++) {
                double offsetX = (level.random.nextDouble() - 0.5) * 2;
                double offsetY = (level.random.nextDouble() - 0.5) * 2;
                double offsetZ = (level.random.nextDouble() - 0.5) * 2;

                level.addParticle(ParticleTypes.EXPLOSION,
                        pos.getX() + 0.5 + offsetX,
                        pos.getY() + 0.5 + offsetY,
                        pos.getZ() + 0.5 + offsetZ,
                        0, 0, 0);
            }
        }

        // 範囲内にいるLivingEntity（プレイヤーなどのエンティティ）を取得
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
                    IRadiationManager.INSTANCE.radiate(new Coord4D(targetPos, level), calculateRadiationLevel(blockDistance, radius));
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

    // プレイヤーに放射線ダメージを適用するメソッド
    private void applyRadiationDamage(Player player) {
        double radiationLevel = 0.5; // 放射線レベル
        IRadiationManager.INSTANCE.radiate(player, radiationLevel);
    }

    // 距離に基づいて放射線レベルを計算
    private double calculateRadiationLevel(int distance, int radius) {
        return 1000.0 / (distance + 1);
    }
}
