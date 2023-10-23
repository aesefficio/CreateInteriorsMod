package com.sudolev.interiors.content.registry;

import com.simibubi.create.foundation.utility.Components;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import com.sudolev.interiors.CreateInteriors;

public final class CITab {
	private static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateInteriors.ID);

	public static final RegistryObject<CreativeModeTab> TAB = REGISTER.register("main",
		CreativeModeTab.builder()
			.title(Components.literal(CreateInteriors.NAME))
			.icon(() -> CIBlocks.CHAIRS.get(DyeColor.RED).asItem().getDefaultInstance())
			.displayItems((parameters, output) -> CreateInteriors.REGISTRATE
				.getAll(Registries.BLOCK).stream()
				.map(entry -> entry.get().asItem())
				.forEach(output::accept))
			::build);

	public static void register(IEventBus modEventBus) {
		REGISTER.register(modEventBus);
	}
}
