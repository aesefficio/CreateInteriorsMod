package com.sudolev.interiors.fabric;

import io.github.fabricators_of_create.porting_lib.util.ItemGroupUtil;
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
		return entity.getExtraCustomData();
	}

	public static <T> TagAppender<T> tagAppender(RegistrateTagsProvider<T> prov, TagKey<T> tag) {
		return prov.tag(tag);
	}

	public static int newCreativeTabIndex() {
		return ItemGroupUtil.expandArrayAndGetId();
	}
}
