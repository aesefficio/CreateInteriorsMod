package com.sudolev.interiors.content.registry.fabric;

import java.util.function.Supplier;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;

import com.simibubi.create.AllCreativeModeTabs.TabInfo;

import com.sudolev.interiors.CreateInteriors;
import com.sudolev.interiors.content.registry.CIBlocks;

public class CITabImpl {
	public static final TabInfo TAB = register("main", FabricItemGroup.builder()
		.title(Component.translatable("itemGroup.create.base"))
		.icon(() -> CIBlocks.CHAIRS.get(DyeColor.RED).asItem().getDefaultInstance())
		.displayItems((parameters, output) -> CreateInteriors.REGISTRATE
			.getAll(Registries.BLOCK).stream()
			.map(entry -> entry.get().asItem())
			.forEach(output::accept))
		::build);


	public static ResourceKey<CreativeModeTab> getTab() {
		return TAB.key();
	}

	private static TabInfo register(String name, Supplier<CreativeModeTab> supplier) {
		ResourceLocation id = CreateInteriors.asResource(name);
		ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
		CreativeModeTab tab = supplier.get();
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, tab);
		return new TabInfo(key, tab);
	}
}
