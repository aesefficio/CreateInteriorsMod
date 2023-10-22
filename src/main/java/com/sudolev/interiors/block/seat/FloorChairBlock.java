package com.sudolev.interiors.block.seat;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllShapes;

@SuppressWarnings("NullableProblems")
public class FloorChairBlock extends ChairBlock {
	public FloorChairBlock(Properties properties, DyeColor color) {
		super(properties, color);
	}

	@Override
	public VoxelShape shape() {
		return AllShapes.SEAT;
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
								   BlockHitResult result) {
		ItemStack heldItem = player.getItemInHand(hand);

		if(heldItem == AllItems.WRENCH.asStack(1)
		   || heldItem.is(AllItems.WRENCH.asItem())) {
			return InteractionResult.PASS;
		}

		return super.use(state, world, pos, player, hand, result);
	}
}
