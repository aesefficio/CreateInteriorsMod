package com.sudolev.interiors.content.block.seat;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.utility.BlockHelper;
import com.sudolev.interiors.content.entity.BigSeatEntity;
import com.sudolev.interiors.content.registry.CIBlocks;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
public class BigChairBlock extends ChairBlock {
	private static final VoxelShape SHAPE = Shapes.or(Block.box(0, 5, 0, 16, 13, 16), Block.box(0, 0, 4, 16, 5, 12));

	public BigChairBlock(Properties properties, DyeColor color) {
		super(properties, color);
	}

	@Override
	public VoxelShape shape() {
		return SHAPE;
	}

	@Override
	public void doSitDown(@NotNull Level world, @NotNull BlockPos pos, @NotNull Entity entity) {
		if(world.isClientSide) return;
		BigSeatEntity seat = new BigSeatEntity(world, pos);
		seat.setPos(pos.getX() + .5f, pos.getY() + .34f, pos.getZ() + .5f);
		world.addFreshEntity(seat);
		entity.startRiding(seat, true);
		if(entity instanceof TamableAnimal ta)
			ta.setInSittingPose(true);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack heldItem = player.getItemInHand(hand);

		if(heldItem == AllItems.WRENCH.asStack(1)
		   || heldItem.is(AllItems.WRENCH.asItem())) {
			return InteractionResult.PASS;
		}

		DyeColor color = DyeColor.getColor(heldItem);

		if(color != null && color != this.color) {
			if(world.isClientSide) return InteractionResult.SUCCESS;
			BlockState newState = BlockHelper.copyProperties(state, CIBlocks.CHAIRS.get(color).getDefaultState());
			world.setBlockAndUpdate(pos, newState);
			return InteractionResult.SUCCESS;
		}

		List<BigSeatEntity> seats = world.getEntitiesOfClass(BigSeatEntity.class, new AABB(pos));
		if(!seats.isEmpty()) {
			BigSeatEntity entity = seats.get(0);
			List<Entity> passengers = entity.getPassengers();
			if(!passengers.isEmpty() && passengers.get(0) instanceof Player) return InteractionResult.PASS;
			if(!world.isClientSide) {
				entity.ejectPassengers();
				player.startRiding(entity);
			}
			return InteractionResult.SUCCESS;
		}

		if(!world.isClientSide) {
			doSitDown(world, pos, getLeashed(world, player).or(player));
		}
		return InteractionResult.SUCCESS;
	}
}
