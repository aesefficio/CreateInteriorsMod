package com.sudolev.interiors.fabric;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import com.sudolev.interiors.CreateInteriors;

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

	public static void registerConfig(ModConfig.Type type, IConfigSpec<?> spec) {
		#if PRE_CURRENT_MC_1_19_2
		ModLoadingContext.registerConfig(CreateInteriors.ID, type, spec);
		#elif POST_CURRENT_MC_1_20_1
		ForgeConfigRegistry.INSTANCE.register(CreateInteriors.ID, type, spec);
		#endif
	}

	public static CompoundTag getCustomData(Entity entity) {
		return entity.getCustomData();
	}

	public static TagKey<Item> tagFromColor(DyeColor color) {
		return color.getTag();
	}
}
