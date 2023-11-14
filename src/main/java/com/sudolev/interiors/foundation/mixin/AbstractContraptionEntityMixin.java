package com.sudolev.interiors.foundation.mixin;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.Contraption;
import com.sudolev.interiors.content.block.seat.BigChairBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.UUID;

@Mixin(value = AbstractContraptionEntity.class, remap = false)
public abstract class AbstractContraptionEntityMixin {

	@Shadow
	protected Contraption contraption;

	@Inject(method = "getPassengerPosition", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	private void raise(Entity passenger, float partialTicks, CallbackInfoReturnable<Vec3> cir, UUID id, AABB bb, double ySize, BlockPos seat, Vec3 transformedVector) {
		Vec3 vec3 = cir.getReturnValue();
		if(contraption.getBlocks().get(seat).state().getBlock() instanceof BigChairBlock) {
			cir.setReturnValue(vec3.add(0, 0.34, 0));
		}
	}
}
