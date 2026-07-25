package com.aesefficio.interiors.content.registry;

import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;

import com.aesefficio.interiors.CreateInteriors;

import java.util.EnumMap;
import java.util.Map;

public final class CITags {
	public static final class Blocks {
		public static final TagKey<Block> CHAIRS = TagKey.create(Registries.BLOCK, CreateInteriors.id("chairs"));
		public static final TagKey<Block> FLOOR_CHAIRS = TagKey.create(Registries.BLOCK, CreateInteriors.id("floor_chairs"));
	}

	public static final class Items {
		public static final TagKey<Item> CHAIRS = TagKey.create(Registries.ITEM, CreateInteriors.id("chairs"));
		public static final TagKey<Item> FLOOR_CHAIRS = TagKey.create(Registries.ITEM, CreateInteriors.id("floor_chairs"));
	}

	public static final Map<DyeColor, TagKey<Item>> DYES = Util.make(new EnumMap<>(DyeColor.class), dyes -> {
		for (DyeColor color : DyeColor.values()) {
			dyes.put(color, TagKey.create(Registries.ITEM,
				CreateInteriors.id("internal/dyes/" + color.getName() + "_dyes")));
		}
	});

	public static void register() {}
}
