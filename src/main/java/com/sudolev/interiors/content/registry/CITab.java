package com.sudolev.interiors.content.registry;

import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.Create;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;

import java.util.function.Supplier;

public final class CITab {

    public static final AllCreativeModeTabs.TabInfo INTERIORS_TAB = register("base",
            () -> FabricItemGroup.builder()
                    .title(Component.translatable("itemGroup.create.base"))
                    .icon(() -> CIBlocks.CHAIRS.get(DyeColor.RED).asStack())
                    .build());

    private static AllCreativeModeTabs.TabInfo register(String name, Supplier<CreativeModeTab> supplier) {
        ResourceLocation id = Create.asResource(name);
        ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
        CreativeModeTab tab = supplier.get();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, tab);
        return new AllCreativeModeTabs.TabInfo(key, tab);
	}

    public static void register() {
        // fabric: just load the class
    }
};
