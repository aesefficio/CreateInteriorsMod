package com.aesefficio.interiors.content.registry;

import com.aesefficio.interiors.CreateInteriors;
import com.aesefficio.interiors.content.entity.BigSeatEntity;
import com.tterrag.registrate.util.entry.EntityEntry;

import net.minecraft.world.entity.MobCategory;

@SuppressWarnings("unused")
public final class CIEntities {
	public static final EntityEntry<BigSeatEntity> BIG_SEAT = createSeat();

	private static EntityEntry<BigSeatEntity> createSeat() {
		return CreateInteriors.REGISTRATE.<BigSeatEntity>entity("big_seat", BigSeatEntity::new, MobCategory.MISC)
				.properties(b -> b.fireImmune()
						.sized(.25f, .85f)
						.setTrackingRange(5)
						.setUpdateInterval(Integer.MAX_VALUE)
						.setShouldReceiveVelocityUpdates(false))
				.renderer(() -> BigSeatEntity.Render::new)
				.register();
	}

	public static void register() {}
}
