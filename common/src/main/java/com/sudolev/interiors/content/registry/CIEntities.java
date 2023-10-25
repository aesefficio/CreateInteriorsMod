package com.sudolev.interiors.content.registry;

import com.sudolev.interiors.content.entity.BigSeatEntity;
import com.tterrag.registrate.util.entry.EntityEntry;
import dev.architectury.injectables.annotations.ExpectPlatform;

@SuppressWarnings("unused")
public final class CIEntities {
	public static final EntityEntry<BigSeatEntity> BIG_SEAT = createSeat();

	@ExpectPlatform
	private static EntityEntry<BigSeatEntity> createSeat() {
		throw new AssertionError();
	}

	public static void register() {}
}
