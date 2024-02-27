package com.sudolev.interiors.content.registry.forge;

import com.sudolev.interiors.CreateInteriors;

import com.sudolev.interiors.content.registry.CITab;

import org.jetbrains.annotations.ApiStatus;

import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;

import net.minecraft.resources.ResourceLocation;

@SuppressWarnings("unchecked")
public class CIBlocksImpl {
	@ApiStatus.Internal
	public static <ModelFile> ModelFile customChairModelFile(Object o, String parent, String name,
															 ResourceLocation top, ResourceLocation side,
															 ResourceLocation sideTop, ResourceLocation sideFront) {
		BlockStateProvider p = (BlockStateProvider) o;
		return (ModelFile) p.models()
			.withExistingParent(name,
				p.modLoc(parent))
			.texture("top", top)
			.texture("side_top", sideTop)
			.texture("side_front", sideFront)
			.texture("side", side);
	}

	@ApiStatus.Internal
	public static <ModelFile> ModelFile getExistingModelFile(Object o, String name) {
		BlockStateProvider p = (BlockStateProvider) o;
		return (ModelFile) p.models().getExistingFile(p.modLoc(name));
	}

	@ApiStatus.Internal
	public static <ModelFile> ModelFile createModelFileWithExistingParent(Object p, String parent, String name) {
		BlockStateProvider provider = (BlockStateProvider) p;
		return (ModelFile) provider.models().withExistingParent(name, provider.modLoc(parent));
	}

	@ApiStatus.Internal
	public static <CM> CM modelWithRotation(Object model, int rotation) {
		return (CM) ConfiguredModel.builder()
			.modelFile((ModelFile) model)
			.rotationY(rotation)
			.build();
	}
}
