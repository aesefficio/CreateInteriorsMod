package com.sudolev.interiors.fabric;

import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

public abstract class UtilsImpl {
	public static String getVersion(String modid) {
		return FabricLoader.getInstance()
						   .getModContainer(modid)
						   .orElseThrow()
						   .getMetadata()
						   .getVersion()
						   .getFriendlyString();
	}

	public static boolean isDevEnv() {
		return FabricLoader.getInstance().isDevelopmentEnvironment();
	}

	public static String platformName() {
		return FabricLoader.getInstance().isModLoaded("quilt_loader") ? "Quilt" : "Fabric";
	}

	public static CompoundTag getCustomData(Entity entity) {
		return entity.getExtraCustomData();
	}

	public static TagKey<Item> tagFromColor(DyeColor color) {
		return color.getTag();
	}
}
