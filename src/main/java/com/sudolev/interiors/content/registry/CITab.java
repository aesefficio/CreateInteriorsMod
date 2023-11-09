package com.sudolev.interiors.content.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public final class CITab {
	public static final CreativeModeTab TAB_INTERIORS = new CreativeModeTab(CreativeModeTab.TABS.length, "interiors") {
		public ItemStack makeIcon() {
			return CIBlocks.CHAIRS.get(DyeColor.RED).asStack();
		};
};
}
