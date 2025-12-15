package com.aesefficio.interiors.content.registry;

import com.aesefficio.interiors.CreateInteriors;

#if forge
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
#elif neoforge
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
#elif fabric
import com.simibubi.create.AllCreativeModeTabs.TabInfo;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import java.util.function.Supplier;
#endif
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;

public final class CITab {
	#if forgelike
	private static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateInteriors.ID);

	public static void register(IEventBus modEventBus) {
		REGISTER.register(modEventBus);
	}

	public static
	#if forge RegistryObject<CreativeModeTab>
	#elif neoforge DeferredHolder<CreativeModeTab, CreativeModeTab>
	#endif get() {
		return TAB;
	}
	#else

	private static TabInfo register(String name, Supplier<CreativeModeTab> supplier) {
		ResourceLocation id = CreateInteriors.id(name);
		ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
		CreativeModeTab tab = supplier.get();
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, tab);
		return new TabInfo(key, tab);
	}

	public static void register() {}

	public static ResourceKey<CreativeModeTab> get() {
		return TAB.key();
	}
	#endif

	#if forgelike
	public static final
			#if forge RegistryObject<CreativeModeTab>
			#elif neoforge DeferredHolder<CreativeModeTab, CreativeModeTab>
			#endif TAB = REGISTER.register("main", CreativeModeTab.builder()
	#elif fabric
	public static final TabInfo TAB = register("main", FabricItemGroup.builder()
	#endif
			.title(Component.translatable("itemGroup.interiors"))
			.icon(() -> CIBlocks.CHAIRS.get(DyeColor.RED).asStack())
			.displayItems((parameters, output) -> CreateInteriors.REGISTRATE
					.getAll(Registries.BLOCK).stream()
					.map(entry -> entry.get().asItem())
					.filter(i -> i.isEnabled(parameters.enabledFeatures()))
					.forEach(output::accept))
			::build);
}