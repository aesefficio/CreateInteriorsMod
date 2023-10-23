package com.sudolev.interiors.foundation.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;

import com.sudolev.interiors.content.block.seat.BigChairBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Contraption.class)
public class ContraptionMixin {
	@Redirect(method = "addPassengersToWorld",
		at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/contraptions/actors/seat/SeatBlock;sitDown(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)V"),
		remap = false)
	private void sitDown(Level level, BlockPos pos, Entity entity) {
		if(level.getBlockState(pos).getBlock() instanceof BigChairBlock) {
			if(BigChairBlock.isSeatOccupied(level, pos))
				return;
			BigChairBlock.sitDown(level, pos, entity);
		} else {
			SeatBlock.sitDown(level, pos, entity);
		}
	}
}
