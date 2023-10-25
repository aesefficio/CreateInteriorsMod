package com.sudolev.interiors.forge;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

import com.sudolev.interiors.CreateInteriors;

public abstract class Events {
	@Mod.EventBusSubscriber(modid = CreateInteriors.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static abstract class ClientModBusEvents {
		@SubscribeEvent
		static void onLoadComplete(FMLLoadCompleteEvent event) {
//			ModContainer container = ModList.get()
//											.getModContainerById(CreateInteriors.ID)
//											.orElseThrow(() -> new IllegalStateException("Create: Interiors mod container missing on LoadComplete"));
//			container.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
//				() -> new ConfigScreenHandler.ConfigScreenFactory(CIConfigs::createConfigScreen));
		}
	}
}
