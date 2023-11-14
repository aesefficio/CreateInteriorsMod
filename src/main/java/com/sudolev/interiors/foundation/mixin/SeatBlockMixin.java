package com.sudolev.interiors.foundation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.simibubi.create.content.contraptions.components.actors.SeatBlock;
import com.simibubi.create.content.contraptions.components.actors.SeatEntity;
import com.sudolev.interiors.content.block.seat.BigChairBlock;
import com.sudolev.interiors.content.entity.BigSeatEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

@Mixin(SeatBlock.class)
public abstract class SeatBlockMixin {

	@Inject(method = "isSeatOccupied", at = @At("HEAD"), cancellable = true, remap = false)
	private static void sitDown(Level world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if(!world.getEntitiesOfClass(BigSeatEntity.class, new AABB(pos)).isEmpty())
			cir.setReturnValue(true);
	}

	@Redirect(method = "sitDown", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Lcom/simibubi/create/content/contraptions/components/actors/SeatEntity;"), remap = false)
	private static SeatEntity createSeatEntity(Level world, BlockPos pos) {
		return world.getBlockState(pos).getBlock() instanceof BigChairBlock
				? new BigSeatEntity(world, pos)
				: new SeatEntity(world, pos);
	}

	@Inject(method = "sitDown", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
	private static void getY(Level world, BlockPos pos, Entity entity, CallbackInfo ci, SeatEntity seat) {
		if(seat instanceof BigSeatEntity) {
			seat.setPos(seat.getX() + .5f, seat.getY() + .34f, seat.getZ() + .5f);
		}
	}

}
