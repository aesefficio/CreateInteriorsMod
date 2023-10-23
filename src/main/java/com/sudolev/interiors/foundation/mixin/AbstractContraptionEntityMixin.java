package com.sudolev.interiors.foundation.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.Contraption;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;

import com.sudolev.interiors.content.block.seat.BigChairBlock;

@Mixin(value = AbstractContraptionEntity.class, remap = false)
public abstract class AbstractContraptionEntityMixin {

	@Shadow
	protected Contraption contraption;

	@Redirect(method = "getPassengerPosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;atLowerCornerOf(Lnet/minecraft/core/Vec3i;)Lnet/minecraft/world/phys/Vec3;"))
	private Vec3 raise(Vec3i vec3i) {
		Vec3 vec3 = Vec3.atLowerCornerOf(vec3i);
		BlockPos pos = (BlockPos) vec3i;

		if(contraption.getBlocks().get(pos).state().getBlock() instanceof BigChairBlock) {
			return vec3.add(0, 0.34, 0);
		}

		return vec3;
	}
}
