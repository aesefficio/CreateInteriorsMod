package com.sudolev.interiors.foundation.data;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.sudolev.interiors.CreateInteriors;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CILangGen extends FabricLanguageProvider {
	protected CILangGen(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	private static void provideDefaultLang(String fileName) {
		String path = "assets/" + CreateInteriors.ID + "/lang/default/" + fileName + ".json";

		JsonObject jsonObject = Preconditions.checkNotNull(FilesHelper.loadJsonResource(path),
				"Could not find default lang file: %s", path).getAsJsonObject();

		jsonObject.entrySet().forEach(entry ->
				CreateInteriors.REGISTRATE.addRawLang(entry.getKey(), entry.getValue().getAsString())
		);
	}

	@Override
	public void generateTranslations(TranslationBuilder translationBuilder) {
		provideDefaultLang("tooltips");
	}

}
