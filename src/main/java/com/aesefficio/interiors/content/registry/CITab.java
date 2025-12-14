package com.aesefficio.interiors.content.registry;

import com.aesefficio.interiors.CreateInteriors;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;

public final class CITab {
	#if forgelike
	private static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateInteriors.ID);

	public static final RegistryObject<CreativeModeTab> TAB = REGISTER.register("main",
			CreativeModeTab.builder()
					.title(Component.literal(CreateInteriors.NAME))
					.icon(() -> CIBlocks.CHAIRS.get(DyeColor.RED).asStack(1))
					.displayItems((parameters, output) -> CreateInteriors.REGISTRATE
							.getAll(Registries.BLOCK).stream()
							.map(entry -> entry.get().asItem())
							.forEach(output::accept))
					::build);

	public static void register(IEventBus modEventBus) {
		REGISTER.register(modEventBus);
	}
	#else
	public static void register() {}
	#endif

	public static ResourceKey<CreativeModeTab> getKey() {
		#if forgelike
		return TAB.getKey();
		#endif
	}

	public static CreativeModeTab get() {
		#if forgelike
		return TAB.get();
		#endif
	}
}