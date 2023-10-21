package com.sudolev.interiors.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;

import com.simibubi.create.content.contraptions.actors.seat.SeatEntity;

public class BigSeatEntity extends SeatEntity implements IEntityAdditionalSpawnData {

    public BigSeatEntity(EntityType<?> type, Level world) {
        super(type, world);
    }

    public BigSeatEntity(Level world, BlockPos pos) {
        super(world, pos);
    }

    public static EntityType.Builder<BigSeatEntity> build(Builder<?> builder) {
        @SuppressWarnings("unchecked")
        EntityType.Builder<BigSeatEntity> entityBuilder = (EntityType.Builder<BigSeatEntity>) builder;
        return entityBuilder.sized(0.25f, 0.85f);
    }

    public static class Render extends SeatEntity.Render {
        public Render(EntityRendererProvider.Context context) {
            super(context);
        }
    }
}
