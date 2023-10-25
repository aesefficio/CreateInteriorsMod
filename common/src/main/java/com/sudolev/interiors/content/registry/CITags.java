package com.sudolev.interiors.content.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;

import com.sudolev.interiors.CreateInteriors;

public final class CITags {
	public static final class Blocks {
		public static final TagKey<Block> CHAIRS = TagKey.create(Registries.BLOCK, CreateInteriors.asResource("chairs"));
		public static final TagKey<Block> FLOOR_CHAIRS = TagKey.create(Registries.BLOCK, CreateInteriors.asResource("floor_chairs"));
	}

	public static final class Items {
		public static final TagKey<Item> CHAIRS = TagKey.create(Registries.ITEM, CreateInteriors.asResource("chairs"));
		public static final TagKey<Item> FLOOR_CHAIRS = TagKey.create(Registries.ITEM, CreateInteriors.asResource("floor_chairs"));
	}

	public static void register() {}
}
