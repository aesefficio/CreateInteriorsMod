package com.sudolev.interiors.forge;

import java.util.List;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

import net.minecraftforge.common.util.MavenVersionStringHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.forgespi.language.IModInfo;

import com.sudolev.interiors.CreateInteriors;

public abstract class UtilsImpl {
	public static String getVersion(String modid) {
		String versionString = "UNKNOWN";

		List<IModInfo> infoList = ModList.get().getModFileById(modid).getMods();
		if (infoList.size() > 1) {
			CreateInteriors.LOGGER.error("Multiple mods for ID: " + modid);
		}
		for (IModInfo info : infoList) {
			if (info.getModId().equals(modid)) {
				versionString = MavenVersionStringHelper.artifactVersionToString(info.getVersion());
				break;
			}
		}
		return versionString;
	}

	public static boolean isDevEnv() {
		return !FMLLoader.isProduction();
	}

	public static String platformName() {
		return "Forge";
	}

	public static CompoundTag getCustomData(Entity entity) {
		return entity.getPersistentData();
	}

	public static TagKey<Item> tagFromColor(DyeColor color) {
		return color.getTag();
	}

	public static int newCreativeTabIndex() {
		return CreativeModeTab.getGroupCountSafe();
	}
}
