package com.fxd927.mekanismcombat.common.registries;

import com.fxd927.mekanismcombat.common.MekanismCombat;
import com.fxd927.mekanismcombat.common.item.gear.ItemConcreteArmor;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.item.ArmorItem;

public class MCItems {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MekanismCombat.MODID);

    public static final ItemRegistryObject<ItemConcreteArmor> CONCRETE_HELMET = ITEMS.register("concrete_helmet", props -> new ItemConcreteArmor(ArmorItem.Type.HELMET, props));
    public static final ItemRegistryObject<ItemConcreteArmor> CONCRETE_CHESTPLATE = ITEMS.register("concrete_chestplate", props -> new ItemConcreteArmor(ArmorItem.Type.CHESTPLATE, props));
    public static final ItemRegistryObject<ItemConcreteArmor> CONCRETE_LEGGINGS = ITEMS.register("concrete_leggings", props -> new ItemConcreteArmor(ArmorItem.Type.LEGGINGS, props));
    public static final ItemRegistryObject<ItemConcreteArmor> CONCRETE_BOOTS = ITEMS.register("concrete_boots", props -> new ItemConcreteArmor(ArmorItem.Type.BOOTS, props));

    private MCItems(){
    }
}
