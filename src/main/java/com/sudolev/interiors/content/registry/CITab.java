package com.sudolev.interiors.content.registry;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.foundation.utility.Components;

import com.simibubi.create.infrastructure.item.CreateCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.world.item.*;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import com.sudolev.interiors.CreateInteriors;

import javax.annotation.Nonnull;

public final class CITab {
	public static final CreativeModeTab TAB_INTERIORS = new CreativeModeTab(CreativeModeTab.TABS.length, "interiors") {
		public ItemStack makeIcon() {
			return CIBlocks.CHAIRS.get(DyeColor.RED).asStack();
		};
};
}
