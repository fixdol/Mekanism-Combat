package com.fxd927.mekanismcombat.common.item.gear;

import com.fxd927.mekanismcombat.common.MekanismCombat;
import mekanism.api.annotations.NothingNullByDefault;
import mekanism.common.capabilities.ItemCapabilityWrapper;
import mekanism.common.capabilities.radiation.item.RadiationShieldingHandler;
import mekanism.common.integration.gender.GenderCapabilityHelper;
import mekanism.common.item.gear.BaseSpecialArmorMaterial;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;

public class ItemConcreteArmor extends ArmorItem {
    private static final ItemConcreteArmor.ConcreteMaterials CONCRETE_MATERIAL = new ItemConcreteArmor.ConcreteMaterials();

    public ItemConcreteArmor(ArmorItem.Type armorType, Properties properties) {
        super(CONCRETE_MATERIAL, armorType, properties.rarity(Rarity.COMMON));
    }

    public static double getShieldingByArmor(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> 0.25;
            case CHESTPLATE -> 0.4;
            case LEGGINGS -> 0.2;
            case BOOTS -> 0.15;
        };
    }

    @Override
    public int getDefaultTooltipHideFlags(@NotNull ItemStack stack) {
        return super.getDefaultTooltipHideFlags(stack) | ItemStack.TooltipPart.MODIFIERS.getMask();
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        ItemCapabilityWrapper wrapper = new ItemCapabilityWrapper(stack, RadiationShieldingHandler.create(item -> getShieldingByArmor(getType())));
        GenderCapabilityHelper.addGenderCapability(this, wrapper::add);
        return wrapper;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return material.getEnchantmentValue() > 0 && super.isEnchantable(stack);
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return isEnchantable(stack) && super.isBookEnchantable(stack, book);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return isEnchantable(stack) && super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @NothingNullByDefault
    protected static class ConcreteMaterials extends BaseSpecialArmorMaterial {

        @Override
        public String getName() {
            return MekanismCombat.MODID + ":concrete";
        }
    }
}
