package systems.alexander.interiors.registry;

import systems.alexander.interiors.CreateInteriors;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import com.simibubi.create.foundation.utility.Components;

public class CITab {
	private static final DeferredRegister<CreativeModeTab> REGISTER =
			DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateInteriors.ID);

	public static final RegistryObject<CreativeModeTab> TAB = REGISTER.register("main",
			CreativeModeTab.builder()
					.title(Components.literal(CreateInteriors.NAME))
					.icon(() -> CIBlocks.CHAIRS.get(DyeColor.RED).asItem().getDefaultInstance())
					.displayItems((parameters, output) ->
							CreateInteriors.REGISTRATE.getAll(Registries.BLOCK).stream()
													  .map(entry -> entry.get().asItem())
													  .forEach(output::accept)
					)::build);

	public static void register(IEventBus modEventBus) {
		REGISTER.register(modEventBus);
	}
}
