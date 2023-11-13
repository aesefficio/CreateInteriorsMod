package com.sudolev.interiors.content.registry;

import javax.annotation.Nonnull;

import com.sudolev.interiors.CreateInteriors;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public final class CITab {
	public static final CreativeModeTab INTERIORS_TAB = new CreativeModeTab(CreativeModeTab.TABS.length - 1, CreateInteriors.ID) {
		@Nonnull
		public ItemStack makeIcon() {
			return CIBlocks.CHAIRS.get(DyeColor.RED).asStack();
		}
	};
}
