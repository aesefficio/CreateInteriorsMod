package com.sudolev.interiors.content.entity;

import com.simibubi.create.content.contraptions.actors.seat.SeatEntity;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BigSeatEntity extends SeatEntity {

	public BigSeatEntity(EntityType<?> type, Level world) {
		super(type, world);
	}

	public BigSeatEntity(Level world, BlockPos pos) {
		super(world, pos);
	}

	@Override
	public Vec3 getDismountLocationForPassenger(LivingEntity entity) {
		return super.getDismountLocationForPassenger(entity).add(0, 0.34f, 0);
	}

	@Override
	public double getPassengersRidingOffset() {
		return super.getPassengersRidingOffset() + 0.34;
	}

	public static class Render extends EntityRenderer<BigSeatEntity> {
		public Render(EntityRendererProvider.Context context) {
			super(context);
		}

		@Override
		public boolean shouldRender(BigSeatEntity entity, Frustum camera, double camX, double camY, double camZ) {
			return false;
		}

		@Override
		public ResourceLocation getTextureLocation(BigSeatEntity entity) {
			return null;
		}
	}
}
