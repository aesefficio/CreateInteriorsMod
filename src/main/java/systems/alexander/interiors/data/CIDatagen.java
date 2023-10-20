package systems.alexander.interiors.data;

import java.util.Map.Entry;
import systems.alexander.interiors.CreateInteriors;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.AbstractRegistrate;

public class CIDatagen {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		CreateInteriors.LOGGER.info("Generating data for Create Interiors");
		DataGenerator gen = event.getGenerator();
		genLang();
	}

	private static void genLang() {
		CreateRegistrate r = CreateInteriors.REGISTRATE;

		provideDefaultLang("tooltips", r);
		r.addRawLang("itemGroup.interiors", "Create: Interiors");
	}

	private static void provideDefaultLang(String fileName, AbstractRegistrate<?> r) {
		String path = "assets/" + CreateInteriors.ID +  "/lang/default/" + fileName + ".json";

		JsonObject jsonObject = Preconditions.checkNotNull(FilesHelper.loadJsonResource(path),
													 "Could not find default lang file: %s", path)
											 .getAsJsonObject();
		for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
			r.addRawLang(entry.getKey(), entry.getValue().getAsString());
		}
	}
}
