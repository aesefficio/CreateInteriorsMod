package com.sudolev.interiors.content.registry;

import com.simibubi.create.foundation.data.CreateEntityBuilder;
import com.simibubi.create.foundation.utility.Lang;
import com.sudolev.interiors.CreateInteriors;
import com.sudolev.interiors.content.entity.BigSeatEntity;
import com.tterrag.registrate.util.entry.EntityEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.MobCategory;

@SuppressWarnings("unused")
public final class CIEntities {
    public static final EntityEntry<BigSeatEntity> BIG_SEAT = CIEntities.<BigSeatEntity>register("big_seat", BigSeatEntity::new,
                    () -> BigSeatEntity.Render::new, MobCategory.MISC, 5, Integer.MAX_VALUE, false, true, BigSeatEntity::build)
            .register();

    @SuppressWarnings("SameParameterValue")
    private static <T extends Entity> CreateEntityBuilder<T, ?> register(String name, EntityFactory<T> factory, NonNullSupplier<NonNullFunction<Context, EntityRenderer<? super T>>> renderer, MobCategory group, int range, int updateFrequency, boolean sendVelocity, boolean immuneToFire, NonNullConsumer<Builder<T>> propertyBuilder) {
        String id = Lang.asId(name);
        return (CreateEntityBuilder<T, ?>) CreateInteriors.REGISTRATE.entity(id, factory, group).properties(b -> b.setTrackingRange(range).setUpdateInterval(updateFrequency).setShouldReceiveVelocityUpdates(sendVelocity)).properties(propertyBuilder).properties(b -> {
            if (immuneToFire) b.fireImmune();
        }).renderer(renderer);
    }

    public static void register() {
    }
}
