package com.sudolev.interiors.fabric;

import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.data.tags.TagsProvider.TagAppender;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;

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
		return entity.getCustomData();
	}

	public static <T> TagAppender<T> tagAppender(RegistrateTagsProvider<T> prov, TagKey<T> tag) {
		return prov.addTag(tag);
	}
}
