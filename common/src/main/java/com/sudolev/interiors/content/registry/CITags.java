package com.sudolev.interiors.content.registry;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;

import com.sudolev.interiors.CreateInteriors;

public final class CITags {
	public static final class Blocks {
		public static final TagKey<Block> CHAIRS = TagKey.create(Registry.BLOCK.key(), CreateInteriors.asResource("chairs"));
		public static final TagKey<Block> FLOOR_CHAIRS = TagKey.create(Registry.BLOCK.key(), CreateInteriors.asResource("floor_chairs"));
	}

	public static final class Items {
		public static final TagKey<Item> CHAIRS = TagKey.create(Registry.ITEM.key(), CreateInteriors.asResource("chairs"));
		public static final TagKey<Item> FLOOR_CHAIRS = TagKey.create(Registry.ITEM.key(), CreateInteriors.asResource("floor_chairs"));
	}

	public static void register() {}
}
