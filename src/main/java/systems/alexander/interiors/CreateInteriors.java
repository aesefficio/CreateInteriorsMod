package systems.alexander.interiors;

import com.simibubi.create.foundation.data.CreateRegistrate;

import java.util.List;
import java.util.function.Function;
import systems.alexander.interiors.block.ModBlocks;
import systems.alexander.interiors.item.ModCreativeModeTabs;
import systems.alexander.interiors.item.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModInfo;

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
        LOGGER.info("Create: Interiors v" + VERSION +  " initializing");
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
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
}