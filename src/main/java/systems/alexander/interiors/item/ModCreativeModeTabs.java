package systems.alexander.interiors.item;

import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import it.unimi.dsi.fastutil.objects.*;
import systems.alexander.interiors.Interiors;
import systems.alexander.interiors.block.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Interiors.MOD_ID);
    public static final RegistryObject<CreativeModeTab> EXTENDED_SEATING = CREATIVE_MODE_TABS.register("extended_seating",
           () -> CreativeModeTab.builder()
                    .title(Component.translatable("creativetab.extended_seating"))
                     //.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                     .icon(() -> new ItemStack(ModBlocks.KELP_CHAIR.get()))
                     .displayItems(new ModCreativeModeTabs.RegistrateDisplayItemsGenerator(false))
                    .build());

    public static class RegistrateDisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator {
        private final boolean mainTab;

        public RegistrateDisplayItemsGenerator(boolean mainTab) {
            this.mainTab = mainTab;
        }

        private List<Item> collectBlocks(RegistryObject<CreativeModeTab> tab) {
            List<Item> items = new ReferenceArrayList<>();
            for (RegistryEntry<Block> entry : Interiors.REGISTRATE.getAll(Registries.BLOCK)) {
                Item item = entry.get()
                        .asItem();
                items.add(item);
            }
            items = new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items));
            return items;
        }
        private static Function<Item, ItemStack> makeStackFunc() {
            Map<Item, Function<Item, ItemStack>> factories = new Reference2ReferenceOpenHashMap<>();

            Map<ItemProviderEntry<?>, Function<Item, ItemStack>> simpleFactories = Map.of(
            );

            simpleFactories.forEach((entry, factory) -> {
                factories.put(entry.asItem(), factory);
            });

            return item -> {
                Function<Item, ItemStack> factory = factories.get(item);
                if (factory != null) {
                    return factory.apply(item);
                }
                return new ItemStack(item);
            };
        }
        private static Function<Item, CreativeModeTab.TabVisibility> makeVisibilityFunc() {
            Map<Item, CreativeModeTab.TabVisibility> visibilities = new Reference2ObjectOpenHashMap<>();

            Map<ItemProviderEntry<?>, CreativeModeTab.TabVisibility> simpleVisibilities = Map.of(
            );

            simpleVisibilities.forEach((entry, factory) -> {
                visibilities.put(entry.asItem(), factory);
            });

            return item -> {
                CreativeModeTab.TabVisibility visibility = visibilities.get(item);
                if (visibility != null) {
                    return visibility;
                }
                return CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;
            };
        }


        @Override
        public void accept(CreativeModeTab.ItemDisplayParameters pParameters, CreativeModeTab.Output output) {
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            RegistryObject<CreativeModeTab> tab = EXTENDED_SEATING;
            Function<Item, ItemStack> stackFunc = makeStackFunc();
            Function<Item, CreativeModeTab.TabVisibility> visibilityFunc = makeVisibilityFunc();

            List<Item> items = new LinkedList<>();
            items.addAll(collectBlocks(tab));
            for (Item item : items) {
                output.accept(stackFunc.apply(item), visibilityFunc.apply(item));
            }
        }

    }

    //public static final RegistryObject<CreativeModeTab> EXTENDED_SEATING_TAB = CREATIVE_MODE_TABS.register("extended_seating_tab",
           //      () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.KELP_SEAT.get()))
           //               .title(Component.translatable("creativetab.extended_seating_tab"))
           //                .displayItems((pParameters, pOutput) -> {
                        //                    pOutput.accept(ModBlocks.KELP_SEAT.get());
                        //                      pOutput.accept(ModBlocks.SEATWOOD_PLANKS.get());
                        //                 })
   //             .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
    }