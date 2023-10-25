package com.sudolev.interiors.content.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;

import com.simibubi.create.foundation.utility.Components;

import com.sudolev.interiors.CreateInteriors;

public final class CITab {
	@ExpectPlatform
	public static ResourceKey<CreativeModeTab> getTab() {
		throw new AssertionError();
	}

	public static void register() {}
}