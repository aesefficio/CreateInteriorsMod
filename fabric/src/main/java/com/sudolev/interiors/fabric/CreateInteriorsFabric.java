package com.sudolev.interiors.fabric;

import net.fabricmc.api.ModInitializer;
import com.sudolev.interiors.CreateInteriors;


public class CreateInteriorsFabric implements ModInitializer {

	@Override
	public void onInitialize() {
		CreateInteriors.init();
		CreateInteriors.REGISTRATE.register();
	}
}
