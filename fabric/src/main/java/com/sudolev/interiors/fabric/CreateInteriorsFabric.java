package com.sudolev.interiors.fabric;

import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.fabricmc.api.ModInitializer;
import com.sudolev.interiors.CreateInteriors;


public class CreateInteriorsFabric implements ModInitializer {

	@Override
	public void onInitialize() {
//		ModConfigEvents.loading(CreateInteriors.ID).register(CIConfigs::onLoad);
//		ModConfigEvents.reloading(CreateInteriors.ID).register(CIConfigs::onReload);
		CreateInteriors.init();
		CreateInteriors.REGISTRATE.register();
	}
}
