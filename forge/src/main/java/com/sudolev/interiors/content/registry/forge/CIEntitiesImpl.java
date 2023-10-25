package com.sudolev.interiors.content.registry.forge;

import net.minecraft.world.entity.MobCategory;

import com.sudolev.interiors.CreateInteriors;
import com.sudolev.interiors.content.entity.BigSeatEntity;
import com.tterrag.registrate.util.entry.EntityEntry;

public class CIEntitiesImpl {

	public static EntityEntry<BigSeatEntity> createSeat() {
		return CreateInteriors.REGISTRATE
			.<BigSeatEntity>entity("big_seat", BigSeatEntity::new, MobCategory.MISC)
			.properties(b -> b.fireImmune()
				.sized(.25f, .85f)
				.setTrackingRange(5)
				.setUpdateInterval(Integer.MAX_VALUE)
				.setShouldReceiveVelocityUpdates(false))
			.renderer(() -> BigSeatEntity.Render::new)
			.register();
	}
}
