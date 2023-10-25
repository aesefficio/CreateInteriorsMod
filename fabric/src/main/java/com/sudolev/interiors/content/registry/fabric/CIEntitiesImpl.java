package com.sudolev.interiors.content.registry.fabric;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.MobCategory;

import com.sudolev.interiors.CreateInteriors;
import com.sudolev.interiors.content.entity.BigSeatEntity;
import com.tterrag.registrate.util.entry.EntityEntry;

public class CIEntitiesImpl {
	public static EntityEntry<BigSeatEntity> createSeat() {
		return CreateInteriors.REGISTRATE
			.<BigSeatEntity>entity("big_seat", BigSeatEntity::new, MobCategory.MISC)
			.properties(b -> b.trackRangeChunks(5)
							  .trackedUpdateRate(Integer.MAX_VALUE)
							  .forceTrackedVelocityUpdates(false)
							  .fireImmune()
							  .dimensions(EntityDimensions.fixed(0.25f, 0.85f)))
			.renderer(() -> BigSeatEntity.Render::new)
			.register();
	}
}
