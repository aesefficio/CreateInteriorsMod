package net.aaw.extendedseating.block.util;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.foundation.data.CreateEntityBuilder;
import com.tterrag.registrate.util.entry.EntityEntry;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.simibubi.create.foundation.utility.Lang;

import net.aaw.extendedseating.ExtendedSeating;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.MobCategory;

public class EntityTypes {
public static final EntityEntry<BigSeatEntity> TALL_SEAT = register("tall_seat", BigSeatEntity::new, () -> BigSeatEntity.Render::new,
        MobCategory.MISC, 5, Integer.MAX_VALUE, false, true, BigSeatEntity::build).register();

//
private static <T extends Entity> CreateEntityBuilder<T, ?> register(String name, EntityFactory<T> factory,
        NonNullSupplier<NonNullFunction<EntityRendererProvider.Context, EntityRenderer<? super T>>> renderer,
        MobCategory group, int range, int updateFrequency, boolean sendVelocity, boolean immuneToFire,
        NonNullConsumer<EntityType.Builder<T>> propertyBuilder) {
        String id = Lang.asId(name);
        return (CreateEntityBuilder<T, ?>) ExtendedSeating.REGISTRATE
        .entity(id, factory, group)
        .properties(b -> b.setTrackingRange(range)
        .setUpdateInterval(updateFrequency)
        .setShouldReceiveVelocityUpdates(sendVelocity))
        .properties(propertyBuilder)
        .properties(b -> {
        if (immuneToFire)
        b.fireImmune();
        })
        .renderer(renderer);
        }

public static void register() {}
        }
