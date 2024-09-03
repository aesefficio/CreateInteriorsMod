package com.sudolev.interiors;

import net.minecraft.resources.ResourceLocation;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription.Modifier;
import com.simibubi.create.foundation.item.TooltipHelper.Palette;

import com.sudolev.interiors.content.registry.CIBlocks;
import com.sudolev.interiors.content.registry.CIEntities;
import com.sudolev.interiors.content.registry.CITab;
import com.sudolev.interiors.content.registry.CITags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

#if FORGE
@net.minecraftforge.fml.common.Mod(CreateInteriors.ID)
#endif
public final class CreateInteriors {
	public static final String ID = "interiors";
	public static final String NAME = "Create: Interiors";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static final String VERSION = Utils.getVersion(ID);

	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);

	static {
		REGISTRATE.setTooltipModifierFactory(item -> new Modifier(item, Palette.STANDARD_CREATE));
	}

	public CreateInteriors() {
		init();
	}

	public static void init() {
		LOGGER.info("{} v{} initializing! Create version: {} on platform: {}",
			NAME, VERSION, Create.VERSION, Utils.platformName());

		CITags.register();
		CITab.register(
		#if FORGE && MC >= "20.1"
			net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext.get().getModEventBus()
		#endif);
		CIEntities.register();
		CIBlocks.register();
	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(ID, path);
	}
}
