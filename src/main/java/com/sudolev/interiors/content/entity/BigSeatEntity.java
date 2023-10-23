package com.sudolev.interiors.content.entity;

import com.simibubi.create.content.contraptions.actors.seat.SeatEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
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

	@SuppressWarnings("unchecked")
	public static EntityType.Builder<BigSeatEntity> build(Builder<?> builder) {
		return ((EntityType.Builder<BigSeatEntity>) builder).sized(0.25f, 0.85f);
	}

	@Override
	public Vec3 getDismountLocationForPassenger(LivingEntity entity) {
		return super.getDismountLocationForPassenger(entity).add(0, 0.34f, 0);
	}

	public static class Render extends SeatEntity.Render {
		public Render(EntityRendererProvider.Context context) {
			super(context);
		}
	}
}
