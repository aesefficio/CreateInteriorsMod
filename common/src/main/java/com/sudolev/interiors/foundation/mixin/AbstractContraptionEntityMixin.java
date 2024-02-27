package com.sudolev.interiors.foundation.mixin;

import com.llamalad7.mixinextras.sugar.Local;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.Contraption;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import com.sudolev.interiors.content.block.chair.BigChairBlock;

@Mixin(AbstractContraptionEntity.class)
public abstract class AbstractContraptionEntityMixin {

	@Shadow
	protected Contraption contraption;

	@Inject(method = "getPassengerPosition", at = @At("TAIL"), cancellable = true)
	private void raiseSeatEntity(Entity passenger, float partialTicks, CallbackInfoReturnable<Vec3> cir, @Local BlockPos seat) {
		if(contraption.getBlocks().get(seat).state().getBlock() instanceof BigChairBlock) {
			cir.setReturnValue(cir.getReturnValue().add(0, 0.34, 0));
		}
	}
}
