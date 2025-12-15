package com.aesefficio.interiors;

import com.aesefficio.interiors.content.registry.CITags;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

#if forge
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.ModList;
#elif neoforge
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.ModList;
#endif

public abstract class Utils {
	public static String getVersion(String modid) {
		return
		#if forgelike
			ModList.get().getModContainerById(modid)
				.map(c -> c.getModInfo().getVersion().toString())
		#elif fabric
			net.fabricmc.loader.api.FabricLoader.getInstance()
				.getModContainer(modid)
				.map(c -> c.getMetadata().getVersion().getFriendlyString())
		#else
			#error "Unsupported platform"
		#endif
				.orElseThrow(() -> new IllegalStateException("Mod " + modid + " not found"));
	}

	public static boolean isDevEnv() {
		return
		#if fabric
			net.fabricmc.loader.api.FabricLoader.getInstance().isDevelopmentEnvironment();
		#elif forgelike
			!FMLLoader.isProduction();
		#else
			net.minecraft.SharedConstants.IS_RUNNING_IN_IDE;
		#endif
	}

	public static String platformName() {
		return
		#if fabric
			net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded("quilt_loader") ? "Quilt" : "Fabric";
		#elif neoforge
			"NeoForge";
		#elif forge
			"Forge";
		#else
			#error "Unsupported platform"
		#endif
	}

	public static ResourceLocation id(String ns, String path) {
		#if forgelike || MC >= 21
		return ResourceLocation.fromNamespaceAndPath(ns, path);
		#else
		return new ResourceLocation(ns, path);
		#endif
	}

	public static DyeColor colorFromItem(ItemStack stack) {
		if (stack.getItem() instanceof DyeItem) {
			return ((DyeItem)stack.getItem()).getDyeColor();
		} else {
			for (int x = 0; x < DyeColor.BLACK.getId(); ++x) {
				DyeColor color = DyeColor.byId(x);
				if (stack.is(CITags.DYES.get(color))) {
					return color;
				}
			}

			return null;
		}
	}

	public static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
		VoxelShape[] buffer = new VoxelShape[]{ shape, Shapes.empty() };

		int times = (to.ordinal() - from.get2DDataValue() + 4) % 4;
		for(int i = 0; i < times; i++) {
			buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1], Shapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
			buffer[0] = buffer[1];
			buffer[1] = Shapes.empty();
		}

		return buffer[0];
	}
}
