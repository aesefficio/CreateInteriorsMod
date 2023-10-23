package com.sudolev.interiors.foundation.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;

import com.simibubi.create.content.contraptions.actors.seat.SeatEntity;

import com.sudolev.interiors.content.block.seat.BigChairBlock;
import com.sudolev.interiors.content.entity.BigSeatEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SeatBlock.class, remap = false)
public abstract class SeatBlockMixin {
	@Inject(method = "isSeatOccupied", at = @At("HEAD"), cancellable = true)
	private static void sitDown(Level world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if(!world.getEntitiesOfClass(BigSeatEntity.class, new AABB(pos)).isEmpty())
			cir.setReturnValue(true);
	}

	@Redirect(method = "sitDown", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Lcom/simibubi/create/content/contraptions/actors/seat/SeatEntity;"))
	private static SeatEntity createSeatEntity(Level world, BlockPos pos) {
		if(world.getBlockState(pos).getBlock() instanceof BigChairBlock) {
			return new BigSeatEntity(world, pos);
		} else {
			return new SeatEntity(world, pos);
		}
	}
}
