package systems.alexander.interiors;

import java.util.List;
import java.util.function.Function;
import systems.alexander.interiors.data.CIDatagen;
import systems.alexander.interiors.registry.CIBlocks;
import systems.alexander.interiors.registry.CITab;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModInfo;

import com.simibubi.create.foundation.data.CreateRegistrate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CreateInteriors.ID)
public class CreateInteriors {
    public static final String ID = "interiors";
    public static final String NAME = getModProperty(IModInfo::getDisplayName);
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);
    public static final String VERSION = getModProperty(IModInfo::getVersion);
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);

    public CreateInteriors() {
        final IEventBus forge = MinecraftForge.EVENT_BUS,
                mod = REGISTRATE.getModEventBus();

        LOGGER.info("{} v{} initializing", NAME, VERSION);
        CIBlocks.register();

        forge.register(this);
        mod.addListener(EventPriority.LOWEST, CIDatagen::gatherData);
        REGISTRATE.registerEventListeners(mod);
        REGISTRATE.setCreativeTab(CITab.TAB);
    }

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