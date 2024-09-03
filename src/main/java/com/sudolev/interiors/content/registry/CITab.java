package com.sudolev.interiors.content.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;

import com.sudolev.interiors.CreateInteriors;

#if MC <= "19.2"
	import net.minecraft.world.item.ItemStack;

	#if FABRIC
	import io.github.fabricators_of_create.porting_lib.util.ItemGroupUtil;
	#endif
#else
	import net.minecraft.network.chat.Component;
	import com.tterrag.registrate.util.entry.RegistryEntry;
	import net.minecraft.core.registries.Registries;

	#if FABRIC
	import com.simibubi.create.AllCreativeModeTabs.TabInfo;
	import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
	import java.util.function.Supplier;
	import net.minecraft.core.Registry;
	import net.minecraft.core.registries.BuiltInRegistries;
	import net.minecraft.resources.ResourceKey;
	import net.minecraft.resources.ResourceLocation;
	#elif FORGE
	import net.minecraftforge.registries.DeferredRegister;
	import net.minecraftforge.registries.RegistryObject;
	import net.minecraftforge.eventbus.api.IEventBus;
	#endif
#endif

public final class CITab #if MC <= "19.2" extends CreativeModeTab #endif {
	#if MC > "19.2"
	private static CreativeModeTab configure(CreativeModeTab.Builder builder) {
		return builder
			.title(Component.translatable("itemGroup.interiors"))
			.icon(() -> CIBlocks.CHAIRS.get(DyeColor.RED).asItem().getDefaultInstance())
			.displayItems((parameters, output) -> {
				CreateInteriors.REGISTRATE
					.getAll(Registries.BLOCK).stream()
					.map(entry -> entry.get().asItem())
					.forEach(output::accept);
				CreateInteriors.REGISTRATE
					.getAll(Registries.ITEM).stream()
					.map(RegistryEntry::get)
					.forEach(output::accept);
			})
			.build();
	}

	#if FABRIC
	public static final TabInfo TAB = register("main", () -> configure(FabricItemGroup.builder()));

	private static TabInfo register(String name, Supplier<CreativeModeTab> supplier) {
		ResourceLocation id = CreateInteriors.asResource(name);
		ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
		CreativeModeTab tab = supplier.get();
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, tab);
		return new TabInfo(key, tab);
	}

	public static void register() {}

	#elif FORGE
	private static final DeferredRegister<CreativeModeTab> REGISTER =
		DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateInteriors.ID);

	public static final RegistryObject<CreativeModeTab> TAB =
		REGISTER.register("main", () -> configure(CreativeModeTab.builder()));

	public static void register(IEventBus modEventBus) {
		REGISTER.register(modEventBus);
	}
   #endif
#else
	private static final CITab INSTANCE = new CITab();

	public static CITab getInstance() {
		return INSTANCE;
	}

	public CITab() {
		super(
			#if FABRIC
				ItemGroupUtil.expandArrayAndGetId(),
			#else
				CreativeModeTab.getGroupCountSafe(),
			#endif
			CreateInteriors.ID);
	}

	public static void register() {
	}


	@Override
	public ItemStack makeIcon() {
		return CIBlocks.CHAIRS.get(DyeColor.RED).asStack();
	}
	#endif
}