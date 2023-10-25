package com.sudolev.interiors.content.block.chair;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.shapes.VoxelShape;

import com.simibubi.create.AllShapes;

public class FloorChairBlock extends ChairBlock {
	public FloorChairBlock(Properties properties, DyeColor color) {
		super(properties, color);
	}

	@Override
	public VoxelShape shape() {
		return AllShapes.SEAT;
	}
}
