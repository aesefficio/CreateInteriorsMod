package net.aaw.extendedseating;

import com.jozufozu.flywheel.util.NonNullSupplier;
import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.*;
import com.tterrag.registrate.Registrate;
import net.aaw.extendedseating.block.ModBlocks;
import net.aaw.extendedseating.block.util.ItemDescriptionsCreate;
import net.aaw.extendedseating.item.ModCreativeModeTabs;
import net.aaw.extendedseating.item.ModItems;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import com.tterrag.registrate.AbstractRegistrate;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.simibubi.create.Create;



import java.util.function.Function;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(net.aaw.extendedseating.ExtendedSeating.MOD_ID)
public class ExtendedSeating {
    @Nullable
    protected Function<Item, TooltipModifier> currentTooltipModifierFactory;

    public static final String MOD_ID = "extendedseating";
    public static final Registrate REGISTRATE = Registrate.create(MOD_ID);
    //static {
    //    REGISTRATE.setTooltipModifierFactory(item -> {
    //        return new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)
    //                .andThen(TooltipModifier.mapNull(KineticStats.create(item)));
    //   });
   //}
    public static final Logger LOGGER = LogUtils.getLogger();
    public ExtendedSeating() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);



    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}