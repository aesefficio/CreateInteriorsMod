package com.sudolev.interiors.content.block.seat;

import com.simibubi.create.AllShapes;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("NullableProblems")
public class FloorChairBlock extends ChairBlock {
    public FloorChairBlock(Properties properties, DyeColor color) {
        super(properties, color);
    }

    @Override
    public VoxelShape shape() {
        return AllShapes.SEAT;
    }
}
