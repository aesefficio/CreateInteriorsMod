package com.sudolev.interiors.foundation.data;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.sudolev.interiors.CreateInteriors;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class CIDatagen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        CreateInteriors.LOGGER.info("Generating data for Create Interiors");
        DataGenerator gen = event.getGenerator();
        genLang();
    }

    private static void genLang() {
        provideDefaultLang("tooltips");
    }

    private static void provideDefaultLang(String fileName) {
        String path = "assets/" + CreateInteriors.ID + "/lang/default/" + fileName + ".json";

        JsonObject jsonObject = Preconditions.checkNotNull(FilesHelper.loadJsonResource(path),
                "Could not find default lang file: %s", path).getAsJsonObject();

        jsonObject.entrySet().forEach(entry ->
                CreateInteriors.REGISTRATE.addRawLang(entry.getKey(), entry.getValue().getAsString())
        );
    }
}
