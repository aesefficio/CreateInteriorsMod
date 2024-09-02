package com.sudolev.interiors.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.sudolev.interiors.CreateInteriors;

@Mod(CreateInteriors.ID)
public class CreateInteriorsForge {
	public CreateInteriorsForge() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

		CreateInteriors.init();
		CreateInteriors.REGISTRATE.registerEventListeners(modEventBus);
	}
}
