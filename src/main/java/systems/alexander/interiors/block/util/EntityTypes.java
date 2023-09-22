package systems.alexander.interiors.block.util;

import com.simibubi.create.foundation.data.CreateEntityBuilder;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.simibubi.create.foundation.utility.Lang;

import systems.alexander.interiors.Interiors;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.MobCategory;

public class EntityTypes {

//
private static <T extends Entity> CreateEntityBuilder<T, ?> register(String name, EntityFactory<T> factory,
        NonNullSupplier<NonNullFunction<EntityRendererProvider.Context, EntityRenderer<? super T>>> renderer,
        MobCategory group, int range, int updateFrequency, boolean sendVelocity, boolean immuneToFire,
        NonNullConsumer<EntityType.Builder<T>> propertyBuilder) {
        String id = Lang.asId(name);
        return (CreateEntityBuilder<T, ?>) Interiors.REGISTRATE
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
