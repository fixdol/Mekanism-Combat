package com.fxd927.mekanismcombat.common;

import com.fxd927.mekanismcombat.common.registries.MCBlocks;
import com.fxd927.mekanismcombat.common.registries.MCCreativeTab;
import com.fxd927.mekanismcombat.common.registries.MCEntities;
import com.fxd927.mekanismcombat.common.registries.MCItems;
import com.fxd927.mekanismscience.common.MekanismScience;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MekanismCombat.MODID)
public class MekanismCombat
{
    public static final String MODID = "mekanismcombat";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MekanismCombat()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MCBlocks.BLOCKS.register(modEventBus);
        MCCreativeTab.CREATIVE_TABS.register(modEventBus);
        MCEntities.BLOCK_ENTITIES.register(modEventBus);
        MCItems.ITEMS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation rl(String path){
        return new ResourceLocation(MekanismCombat.MODID,path);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
