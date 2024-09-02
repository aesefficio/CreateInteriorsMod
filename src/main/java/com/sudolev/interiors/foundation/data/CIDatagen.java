package com.sudolev.interiors.foundation.data;

#if FABRIC
import com.sudolev.interiors.CreateInteriors;
import com.sudolev.interiors.content.registry.CITags;
import com.tterrag.registrate.providers.ProviderType;

#if POST_MC_19_2
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
#else
import net.minecraftforge.common.data.ExistingFileHelper;
#endif

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Set;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;

import com.simibubi.create.foundation.utility.FilesHelper;

public class CIDatagen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator gen) {
		CreateInteriors.LOGGER.info("Initializing data generator");
		Path resources = Paths.get(System.getProperty(ExistingFileHelper.EXISTING_RESOURCES));
		// fixme re-enable the existing file helper when porting lib's ResourcePackLoader.createPackForMod is fixed
		ExistingFileHelper helper = new ExistingFileHelper(
			Set.of(resources), Set.of("create"), false, null, null
		);

		CreateInteriors.REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov ->
			CITags.DYES.values().forEach(tag -> tag.generateCommon(prov)));

		CreateInteriors.REGISTRATE.setupDatagen(gen
			#if POST_MC_19_2
				.createPack()
			#endif
			, helper);

		provideDefaultLang("tooltips");
	}

	@SuppressWarnings("SameParameterValue")
	private static void provideDefaultLang(String fileName) {
		String path = "assets/" + CreateInteriors.ID + "/lang/default/" + fileName + ".json";

		JsonObject jsonObject = Preconditions.checkNotNull(FilesHelper.loadJsonResource(path),
			"Could not find default lang file: %s", path).getAsJsonObject();

		jsonObject.entrySet().forEach(entry ->
			CreateInteriors.REGISTRATE.addRawLang(entry.getKey(), entry.getValue().getAsString())
		);
	}
}
#endif
