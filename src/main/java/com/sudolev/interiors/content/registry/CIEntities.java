package com.sudolev.interiors.content.registry;

import com.sudolev.interiors.CreateInteriors;
import com.sudolev.interiors.content.entity.BigSeatEntity;
import com.tterrag.registrate.util.entry.EntityEntry;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.MobCategory;

@SuppressWarnings("unused")
public final class CIEntities {
	public static final EntityEntry<BigSeatEntity> BIG_SEAT = CreateInteriors.REGISTRATE
		.<BigSeatEntity>entity("big_seat",BigSeatEntity::new, MobCategory.MISC)
			.properties(b -> b.fireImmune()
				#if FABRIC
					.trackRangeChunks(5)
					.trackedUpdateRate(Integer.MAX_VALUE)
					.forceTrackedVelocityUpdates(false)
					.dimensions(EntityDimensions.fixed(.25f, .85f))
				#elif FORGE
					.setTrackingRange(5)
					.setUpdateInterval(Integer.MAX_VALUE)
					.setShouldReceiveVelocityUpdates(false)
					.sized(.25f, .85f)
				#endif
				)
		.renderer(() -> BigSeatEntity.Render::new)
		.register();

	public static void register() {}
}
