package com.sudolev.interiors.content.registry;

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;

import com.sudolev.interiors.CreateInteriors;
import com.sudolev.interiors.foundation.data.CommonTag;

import java.util.EnumMap;
import java.util.Map;

public final class CITags {
	#if PRE_CURRENT_MC_19_2
	private static final ResourceKey<Registry<Block>> BLOCK_REGISTRY = Registry.BLOCK_REGISTRY;
	private static final ResourceKey<Registry<Item>> ITEM_REGISTRY = Registry.ITEM_REGISTRY;
	#else
	private static final ResourceKey<Registry<Block>> BLOCK_REGISTRY = net.minecraft.core.registries.Registries.BLOCK;
	private static final ResourceKey<Registry<Item>> ITEM_REGISTRY = net.minecraft.core.registries.Registries.ITEM;
	#endif

	public static final class Blocks {
		public static final TagKey<Block> CHAIRS = TagKey.create(BLOCK_REGISTRY, CreateInteriors.asResource("chairs"));
		public static final TagKey<Block> FLOOR_CHAIRS = TagKey.create(BLOCK_REGISTRY, CreateInteriors.asResource("floor_chairs"));
	}

	public static final class Items {
		public static final TagKey<Item> CHAIRS = TagKey.create(ITEM_REGISTRY, CreateInteriors.asResource("chairs"));
		public static final TagKey<Item> FLOOR_CHAIRS = TagKey.create(ITEM_REGISTRY, CreateInteriors.asResource("floor_chairs"));
	}

	public static final Map<DyeColor, CommonTag<Item>> DYES = Util.make(new EnumMap<>(DyeColor.class), dyes -> {
		for (DyeColor color : DyeColor.values()) {
			String name = color.getName();
			String common = "dyes/" + name + "_dyes";
			String fabric = name + "_dyes";
			String forge = "dyes/" + name;
			dyes.put(color, CommonTag.conventional(ITEM_REGISTRY, common, fabric, forge));
		}
	});

	public static void register() {}
}
