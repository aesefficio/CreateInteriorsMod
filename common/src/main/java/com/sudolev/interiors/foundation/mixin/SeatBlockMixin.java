package com.sudolev.interiors.foundation.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;

import com.simibubi.create.content.contraptions.actors.seat.SeatEntity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.sudolev.interiors.content.block.chair.BigChairBlock;
import com.sudolev.interiors.content.block.chair.FloorChairBlock;
import com.sudolev.interiors.content.entity.BigSeatEntity;

import com.sudolev.interiors.content.registry.CIBlocks;

import com.tterrag.registrate.util.entry.BlockEntry;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SeatBlock.class)
public abstract class SeatBlockMixin {

	@Inject(method = "isSeatOccupied", at = @At("HEAD"), cancellable = true)
	private static void weCountAsSeatsToo(Level level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if(!level.getEntitiesOfClass(BigSeatEntity.class, new AABB(pos)).isEmpty())
			cir.setReturnValue(true);
	}

	@ModifyExpressionValue(method = "sitDown", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Lcom/simibubi/create/content/contraptions/actors/seat/SeatEntity;"))
	private static SeatEntity createCorrectSeatEntity(SeatEntity original, @Local(argsOnly = true) Level level, @Local(argsOnly = true) BlockPos pos) {
		return level.getBlockState(pos).getBlock() instanceof BigChairBlock
			   ? new BigSeatEntity(level, pos)
			   : new SeatEntity(level, pos);
	}

	@ModifyArg(method = "sitDown", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/contraptions/actors/seat/SeatEntity;setPos(DDD)V"), index = 1)
	private static double getFixedY(double y, @Local SeatEntity seat) {
		return seat instanceof BigSeatEntity ? y + 0.34f : y;
	}

	@ModifyExpressionValue(method = "use", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/block/DyedBlockList;get(Lnet/minecraft/world/item/DyeColor;)Lcom/tterrag/registrate/util/entry/BlockEntry;"))
	private BlockEntry<?> useBigChairSeats(BlockEntry<?> original, @Local(argsOnly = true) BlockState state, @Local DyeColor color) {
		SeatBlock thiz = (SeatBlock) (Object) this;

		if(thiz instanceof BigChairBlock) {
			return thiz == CIBlocks.KELP_CHAIR.get() ? CIBlocks.KELP_CHAIR : CIBlocks.CHAIRS.get(color);
		} else if(thiz instanceof FloorChairBlock) {
			return thiz == CIBlocks.KELP_FLOOR_CHAIR.get() ? CIBlocks.KELP_FLOOR_CHAIR : CIBlocks.FLOOR_CHAIRS.get(color);
		} else {
			return thiz == CIBlocks.KELP_SEAT.get() ? CIBlocks.KELP_SEAT : original;
		}
	}
}
