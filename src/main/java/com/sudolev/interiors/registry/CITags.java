package com.sudolev.interiors.registry;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import com.sudolev.interiors.CreateInteriors;

public final class CITags {
	public static final class Blocks {
		public static final TagKey<Block> CHAIRS = BlockTags.create(CreateInteriors.asResource("chairs"));
		public static final TagKey<Block> FLOOR_CHAIRS = BlockTags.create(CreateInteriors.asResource("floor_chairs"));
	}

	public static final class Items {
		public static final TagKey<Item> CHAIRS = ItemTags.create(CreateInteriors.asResource("chairs"));
		public static final TagKey<Item> FLOOR_CHAIRS = ItemTags.create(CreateInteriors.asResource("floor_chairs"));
	}
}
