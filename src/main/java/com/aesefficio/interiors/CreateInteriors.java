package com.aesefficio.interiors;

import com.aesefficio.interiors.content.registry.CIBlocks;
import com.aesefficio.interiors.content.registry.CIEntities;
import com.aesefficio.interiors.content.registry.CITab;
import com.aesefficio.interiors.content.registry.CITags;
import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import net.createmod.catnip.lang.FontHelper.Palette;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simibubi.create.CreateBuildInfo;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription.Modifier;
import com.simibubi.create.foundation.utility.FilesHelper;

import net.minecraft.resources.ResourceLocation;

#if forge
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
#elif neoforge
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
#elif fabric
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import com.tterrag.registrate.providers.ProviderType;
#endif

#if forgelike
@Mod(CreateInteriors.ID)
#endif
public final class CreateInteriors
		#if fabric
		implements net.fabricmc.api.ModInitializer, net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
		#endif
{
	public static final String ID = "interiors";
	public static final String NAME = "Create: Interiors";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
	public static final String VERSION = Utils.getVersion(ID);

	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);

	static {
		REGISTRATE.setTooltipModifierFactory(item -> new Modifier(item, Palette.STANDARD_CREATE));
	}

	public static void init() {
		LOGGER.info("{} v{} initializing! Create version: {} on platform: {}",
				NAME, VERSION, CreateBuildInfo.VERSION, Utils.platformName());

		CITags.register();
		CIEntities.register();
		CIBlocks.register();
		provideDefaultLang("tooltips");
	}

	#if forgelike
	#if neoforge
	public CreateInteriors(IEventBus modBus) {
	#elif forge
	public CreateInteriors(net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext ctx) {
		IEventBus modBus = ctx.getModEventBus();
	#endif
		REGISTRATE.registerEventListeners(modBus);
		init();
		CITab.register(modBus);
	}

	#elif fabric
	@Override
	public void onInitialize() {
		CITab.register();
		init();
		REGISTRATE.register();
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator gen) {
		LOGGER.info("Initializing data generator");

		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov ->
			CITags.DYES.values().forEach(prov::addTag));

		//ExistingFileHelper efh = ExistingFileHelper.withResourcesFromArg();
		ExistingFileHelper efh = ExistingFileHelper.withResources(java.util.Set.of("create"), java.nio.file.Path.of(System.getProperty(ExistingFileHelper.EXISTING_RESOURCES)));

		CreateInteriors.REGISTRATE.setupDatagen(gen.createPack(), efh);
	}
	#endif

	@SuppressWarnings("SameParameterValue")
	private static void provideDefaultLang(String fileName) {
		String path = "assets/" + ID + "/lang/default/" + fileName + ".json";

		JsonObject jsonObject = Preconditions.checkNotNull(FilesHelper.loadJsonResource(path),
				"Could not find default lang file: %s", path).getAsJsonObject();

		jsonObject.entrySet().forEach(entry ->
				REGISTRATE.addRawLang(entry.getKey(), entry.getValue().getAsString())
		);
	}

	public static ResourceLocation id(String path) {
		return Utils.id(ID, path);
	}
}
