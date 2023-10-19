package com.sudolev.interiors.block.util;

import com.simibubi.create.content.contraptions.actors.seat.SeatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class BigSeatEntity extends SeatEntity {

    public BigSeatEntity(EntityType<?> p_i48580_1_, Level world, BlockPos pos) {
        super(world, pos);
    }

    @Override
    protected void positionRider(Entity pEntity, Entity.MoveFunction pCallback) {
        if (!this.hasPassenger(pEntity))
            return;
        double d0 = this.getY() + 0.3 + this.getPassengersRidingOffset() + pEntity.getMyRidingOffset();
        pCallback.accept(pEntity, this.getX(), d0 + getCustomEntitySeatOffset(pEntity), this.getZ());
    }
}
