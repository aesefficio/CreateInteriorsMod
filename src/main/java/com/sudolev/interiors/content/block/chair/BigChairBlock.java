package com.sudolev.interiors.content.block.chair;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BigChairBlock extends ChairBlock {
	private static final VoxelShape SHAPE = Shapes.or(Block.box(0, 5, 0, 16, 13, 16), Block.box(0, 0, 4, 16, 5, 12));

	public BigChairBlock(Properties properties, DyeColor color) {
		super(properties, color);
	}

	@Override
	public VoxelShape shape() {
		return SHAPE;
	}
}