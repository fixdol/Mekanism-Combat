package com.fxd927.mekanismcombat.common.registries;

import com.fxd927.mekanismcombat.common.MCLang;
import com.fxd927.mekanismcombat.common.MekanismCombat;
import com.fxd927.mekanismscience.common.MSLang;
import com.fxd927.mekanismscience.common.MekanismScience;
import com.fxd927.mekanismscience.common.registries.MSBlocks;
import com.fxd927.mekanismscience.common.registries.MSFluids;
import com.fxd927.mekanismscience.common.registries.MSItems;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import mekanism.common.registration.impl.CreativeTabRegistryObject;
import mekanism.common.registries.MekanismCreativeTabs;

public class MCCreativeTab {
    public static final CreativeTabDeferredRegister CREATIVE_TABS = new CreativeTabDeferredRegister(MekanismCombat.MODID);

    public static final CreativeTabRegistryObject MEKANISM_COMBAT = CREATIVE_TABS.registerMain(MCLang.MEKANISM_COMBAT, MCBlocks.NEUTRON_BOMB, builder ->
            builder.withBackgroundLocation(MekanismScience.rl("textures/gui/creative_tab.png"))
                    .withSearchBar(70)
                    .withTabsBefore(MekanismCreativeTabs.MEKANISM.key())
                    .displayItems((displayParameters, output) -> {
                        CreativeTabDeferredRegister.addToDisplay(MCItems.ITEMS, output);
                        CreativeTabDeferredRegister.addToDisplay(MCBlocks.BLOCKS, output);
                    })
    );
}
