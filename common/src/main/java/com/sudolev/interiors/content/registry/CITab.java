package com.sudolev.interiors.content.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import com.sudolev.interiors.CreateInteriors;
import com.sudolev.interiors.Utils;

public final class CITab extends CreativeModeTab {
	private static final CITab INSTANCE = new CITab();

	public static CITab getInstance() {
		return INSTANCE;
	}

	public CITab() {
		super(Utils.newCreativeTabIndex(), CreateInteriors.ID);
	}

	public static void register() {
	}


	@Override
	public ItemStack makeIcon() {
		return CIBlocks.CHAIRS.get(DyeColor.RED).asStack();
	}
}