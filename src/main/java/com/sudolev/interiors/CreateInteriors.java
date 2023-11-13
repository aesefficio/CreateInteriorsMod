package com.sudolev.interiors;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.sudolev.interiors.content.registry.CIBlocks;
import com.sudolev.interiors.content.registry.CIEntities;
import com.sudolev.interiors.content.registry.CITags;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CreateInteriors implements ModInitializer {
	public static final String ID = "interiors";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);
	public static final String VERSION = "0.5.1";

	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);

	static {
		REGISTRATE.setTooltipModifierFactory(item -> {
			return new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)
					.andThen(TooltipModifier.mapNull(KineticStats.create(item)));
		});
	}


	// REGISTRATE.setTooltipModifierFactory(item -> new Modifier(item, Palette.STANDARD_CREATE));

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(ID, path);
	}

	@Override
	public void onInitialize() {
		LOGGER.info("{} v{} initializing", ID, VERSION);
		CIEntities.register();
		CIBlocks.register();
		CITags.register();
		REGISTRATE.register();
		// ModLoadingContext modLoadingContext = new ModLoadingContext();
	}
}