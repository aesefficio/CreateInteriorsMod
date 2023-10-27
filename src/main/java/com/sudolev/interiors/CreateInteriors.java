package com.sudolev.interiors;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription.Modifier;
import com.simibubi.create.foundation.item.TooltipHelper.Palette;
import com.sudolev.interiors.content.registry.CIBlocks;
import com.sudolev.interiors.content.registry.CIEntities;
import com.sudolev.interiors.foundation.data.CIDatagen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

@Mod(CreateInteriors.ID)
public final class CreateInteriors {
    public static final String ID = "interiors";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);    public static final String NAME = getModProperty(IModInfo::getDisplayName);

    static {
        REGISTRATE.setTooltipModifierFactory(item -> new Modifier(item, Palette.STANDARD_CREATE));
    }    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
    public CreateInteriors() {
        final IEventBus forge = MinecraftForge.EVENT_BUS;

        LOGGER.info("{} v{} initializing", NAME, VERSION);
        CIBlocks.register();
        CIEntities.register();
        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        IEventBus mod = FMLJavaModLoadingContext.get()
                .getModEventBus();

        forge.register(this);
        mod.addListener(EventPriority.LOWEST, CIDatagen::gatherData);
        REGISTRATE.registerEventListeners(mod);
    }    public static final String VERSION = getModProperty(IModInfo::getVersion);

    private static String getModProperty(Function<IModInfo, ?> f) {
        List<IModInfo> infoList = ModList.get().getModFileById(ID).getMods();
        if (infoList.size() > 1 && LOGGER != null) {
            LOGGER.error("Multiple mods for ID: " + ID);
        }

        for (IModInfo info : infoList) {
            if (info.getModId().equals(ID)) {
                return f.apply(info).toString();
            }
        }
        return "UNKNOWN";
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(ID, path);
    }






}