package com.sudolev.interiors;

import dev.architectury.injectables.annotations.ExpectPlatform;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public abstract class Utils {
	@ExpectPlatform
	public static String getVersion(String modid) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static boolean isDevEnv() {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static String platformName() {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static void registerConfig(ModConfig.Type type, IConfigSpec<?> spec) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static CompoundTag getCustomData(Entity entity) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static TagKey<Item> tagFromColor(DyeColor color) {
		throw new AssertionError();
	}

	public static DyeColor colorFromItem(ItemStack stack) {
		if (stack.getItem() instanceof DyeItem) {
			return ((DyeItem)stack.getItem()).getDyeColor();
		} else {
			for (int x = 0; x < DyeColor.BLACK.getId(); ++x) {
				DyeColor color = DyeColor.byId(x);
				if (stack.is(tagFromColor(color))) {
					return color;
				}
			}

			return null;
		}
	}
}
