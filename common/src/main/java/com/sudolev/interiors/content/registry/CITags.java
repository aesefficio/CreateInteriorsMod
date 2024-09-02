package com.sudolev.interiors.content.registry;

import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;

import com.sudolev.interiors.CreateInteriors;
import com.sudolev.interiors.foundation.data.CommonTag;

import java.util.EnumMap;
import java.util.Map;

public final class CITags {
	public static final class Blocks {
		public static final TagKey<Block> CHAIRS = TagKey.create(Registries.BLOCK, CreateInteriors.asResource("chairs"));
		public static final TagKey<Block> FLOOR_CHAIRS = TagKey.create(Registries.BLOCK, CreateInteriors.asResource("floor_chairs"));
	}

	public static final class Items {
		public static final TagKey<Item> CHAIRS = TagKey.create(Registries.ITEM, CreateInteriors.asResource("chairs"));
		public static final TagKey<Item> FLOOR_CHAIRS = TagKey.create(Registries.ITEM, CreateInteriors.asResource("floor_chairs"));
	}

	public static final Map<DyeColor, CommonTag<Item>> DYES = Util.make(new EnumMap<>(DyeColor.class), dyes -> {
		for (DyeColor color : DyeColor.values()) {
			String name = color.getName();
			String common = "dyes/" + name + "_dyes";
			String fabric = name + "_dyes";
			String forge = "dyes/" + name;
			dyes.put(color, CommonTag.conventional(Registries.ITEM, common, fabric, forge));
		}
	});

	public static void register() {}
}
