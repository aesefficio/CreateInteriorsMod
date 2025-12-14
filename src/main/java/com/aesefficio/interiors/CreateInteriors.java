package com.aesefficio.interiors;

import com.aesefficio.interiors.content.registry.CIBlocks;
import com.aesefficio.interiors.content.registry.CIEntities;
import com.aesefficio.interiors.content.registry.CITab;
import com.aesefficio.interiors.content.registry.CITags;
import net.createmod.catnip.lang.FontHelper.Palette;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simibubi.create.CreateBuildInfo;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription.Modifier;

import net.minecraft.resources.ResourceLocation;

#if forge
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
#elif neoforge
import net.neoforged.fml.eventbus.api.IEventBus;
import net.neoforged.fml.common.Mod;
#endif

#if forgelike
@Mod(CreateInteriors.ID)
#endif
public final class CreateInteriors #if fabric implements net.fabricmc.api.ModInitializer #endif {
	public static final String ID = "interiors";
	public static final String NAME = "Create: Interiors";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static final String VERSION = Utils.getVersion(ID);

	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);

	static {
		REGISTRATE.setTooltipModifierFactory(item -> new Modifier(item, Palette.STANDARD_CREATE));
	}

	#if forgelike
	#if neoforge
	public CreateInteriors(IEventBus modBus) {
	#elif forge
	public CreateInteriors(net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext ctx) {
		IEventBus modBus = ctx.getModEventBus();
	#endif
		init();
		CITab.register(modBus);
		REGISTRATE.registerEventListeners(modBus);
	}
	#endif

	public static void init() {
		LOGGER.info("{} v{} initializing! Create version: {} on platform: {}",
				NAME, VERSION, CreateBuildInfo.VERSION, Utils.platformName());

		CITags.register();
		CIEntities.register();
		CIBlocks.register();
	}

	public static ResourceLocation asResource(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}
}
