package com.sudolev.interiors;

#if FABRIC
import net.fabricmc.loader.api.FabricLoader;
#endif

#if FORGE
import net.minecraftforge.common.util.MavenVersionStringHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.forgespi.language.IModInfo;
import java.util.List;
#endif

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class Utils {
	public static String getVersion(String modid) {
		#if FORGE
		List<IModInfo> infoList = ModList.get().getModFileById(modid).getMods();
		if (infoList.size() > 1) {
			CreateInteriors.LOGGER.error("Multiple mods for ID: " + modid);
		}
		for (IModInfo info : infoList) {
			if (info.getModId().equals(modid)) {
				return MavenVersionStringHelper.artifactVersionToString(info.getVersion());
			}
		}
		throw new IllegalStateException("No mod found for ID: " + modid);
		#elif FABRIC
		return FabricLoader.getInstance()
			.getModContainer(modid)
			.orElseThrow()
			.getMetadata()
			.getVersion()
			.getFriendlyString();
		#else
		#error "Unsupported environment"
		#endif
	}

	#if FORGE
	@SuppressWarnings("UnstableApiUsage")
	#endif
	public static boolean isDevEnv() {
		#if FORGE
		return !FMLLoader.isProduction();
		#elif FABRIC
		return FabricLoader.getInstance().isDevelopmentEnvironment();
		#else
		#error "Unsupported environment"
		#endif
	}

	public static String platformName() {
		#if FORGE
		return "Forge";
		#elif FABRIC
		return FabricLoader.getInstance().isModLoaded("quilt_loader") ? "Quilt" : "Fabric";
		#else
		#error "Unsupported environment"
		#endif
	}

	public static CompoundTag getCustomData(Entity entity) {
		#if FORGE
			return entity.getPersistentData();
		#elif FABRIC
			#if PRE_CURRENT_MC_19_2
			return entity.getExtraCustomData();
			#else
			return entity.getCustomData();
			#endif
		#else
			#error "Unsupported environment"
		#endif
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
