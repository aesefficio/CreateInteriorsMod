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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.phys.Vec3;

import com.sudolev.interiors.content.block.chair.BigChairBlock;

@Mixin(AbstractContraptionEntity.class)
public abstract class AbstractContraptionEntityMixin {

	@Shadow(remap = false)
	protected Contraption contraption;

	@Inject(method = "getPassengerPosition", at = @At("TAIL"), cancellable = true, remap = false)
	private void raiseSeatEntity(Entity passenger, float partialTicks, CallbackInfoReturnable<Vec3> cir, @Local BlockPos seat) {
		#if MC <= "19.2"
		BlockState state = contraption.getBlocks().get(seat).state;
		#else
		BlockState state = contraption.getBlocks().get(seat).state();
		#endif
		if(state.getBlock() instanceof BigChairBlock) {
			cir.setReturnValue(cir.getReturnValue().add(0, 0.34, 0));
		}
	}
}
