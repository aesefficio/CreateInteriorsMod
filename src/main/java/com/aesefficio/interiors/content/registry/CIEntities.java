package com.aesefficio.interiors.content.registry;

import com.aesefficio.interiors.CreateInteriors;
import com.aesefficio.interiors.content.entity.BigSeatEntity;
import com.tterrag.registrate.util.entry.EntityEntry;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.MobCategory;

@SuppressWarnings("unused")
public final class CIEntities {
	public static final EntityEntry<BigSeatEntity> BIG_SEAT = CreateInteriors.REGISTRATE
			.<BigSeatEntity>entity("big_seat", BigSeatEntity::new, MobCategory.MISC)
			.properties(b -> b.fireImmune()
					#if forgelike .sized #elif fabric .dimensions(EntityDimensions.fixed #endif (0.25f, 0.85f) #if fabric ) #endif
					#if forgelike .setTrackingRange #elif fabric .trackRangeChunks #endif (5)
					#if forgelike .setUpdateInterval #elif fabric .trackedUpdateRate #endif (Integer.MAX_VALUE)
					#if forgelike .setShouldReceiveVelocityUpdates #elif fabric .forceTrackedVelocityUpdates #endif (false)
			)
			.renderer(() -> BigSeatEntity.Render::new)
			.register();

	public static void register() {}
}
