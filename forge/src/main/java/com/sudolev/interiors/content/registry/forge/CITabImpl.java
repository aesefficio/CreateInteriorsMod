package com.sudolev.interiors.content.registry.forge;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import com.simibubi.create.foundation.utility.Components;

import com.sudolev.interiors.CreateInteriors;
import com.sudolev.interiors.content.registry.CIBlocks;

public class CITabImpl {
	private static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateInteriors.ID);

	public static final RegistryObject<CreativeModeTab> TAB = REGISTER.register("main",
		CreativeModeTab.builder()
			.title(Components.literal(CreateInteriors.NAME))
			.icon(() -> CIBlocks.CHAIRS.get(DyeColor.RED).asStack(1))
			.displayItems((parameters, output) -> CreateInteriors.REGISTRATE
				.getAll(Registries.BLOCK).stream()
				.map(entry -> entry.get().asItem())
				.forEach(output::accept))
			::build);

	public static void register(IEventBus modEventBus) {
		REGISTER.register(modEventBus);
	}

	public static CreativeModeTab getTab() {
		return TAB.get();
	}
}
