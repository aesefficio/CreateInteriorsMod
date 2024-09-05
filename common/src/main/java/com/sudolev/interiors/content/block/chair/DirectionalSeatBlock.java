package com.sudolev.interiors.content.block.chair;

import com.sudolev.interiors.CreateInteriors;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import com.simibubi.create.content.equipment.wrench.IWrenchable;

@SuppressWarnings("NullableProblems")
public class DirectionalSeatBlock extends SeatBlock implements IWrenchable {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public DirectionalSeatBlock(Properties properties, DyeColor color) {
		super(properties, color);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(FACING));
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack heldItem = player.getItemInHand(hand);

		if(heldItem == AllItems.WRENCH.asStack(1) || heldItem.is(AllItems.WRENCH.asItem())) {
			return InteractionResult.PASS;
		}

		return super.use(state, world, pos, player, hand, result);
	}

	@Override
	public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
		return InteractionResult.PASS;
	}
}
