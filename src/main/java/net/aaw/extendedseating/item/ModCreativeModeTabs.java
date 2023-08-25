package net.aaw.extendedseating.item;

import net.aaw.extendedseating.ExtendedSeating;
import net.aaw.extendedseating.block.ModBlocks;
import net.aaw.extendedseating.block.util.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ExtendedSeating.MOD_ID);

    //public static final RegistryObject<CreativeModeTab> EXTENDED_SEATING_TAB = CREATIVE_MODE_TABS.register("extended_seating_tab",
    //       () -> CreativeModeTab.builder().icon(() -> new ItemStack(.get()))
    //               .title(Component.translatable("creativetab.extended_seating_tab"))
    //                .displayItems((pParameters, pOutput) -> {
    //                    pOutput.accept(ModBlocks.KELP_SEAT.get());
    //                 })
//                 .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}