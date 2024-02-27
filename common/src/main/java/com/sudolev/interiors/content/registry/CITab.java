package com.sudolev.interiors.content.registry;

import com.sudolev.interiors.CreateInteriors;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import dev.architectury.injectables.annotations.ExpectPlatform;

public final class CITab extends CreativeModeTab {
	private static final CITab INSTANCE = new CITab();

	public static CITab getInstance() {
		return INSTANCE;
	}

	public CITab() {
		super(CreativeModeTab.TABS.length - 1, CreateInteriors.ID);
	}

	public static void register() {
	}


	@Override
	public ItemStack makeIcon() {
		return CIBlocks.CHAIRS.get(DyeColor.RED).asStack();
	}
}