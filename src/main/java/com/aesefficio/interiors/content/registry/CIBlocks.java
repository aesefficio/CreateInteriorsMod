package com.aesefficio.interiors.content.registry;


import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllDisplaySources;
import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.actors.seat.SeatInteractionBehaviour;
import com.simibubi.create.content.contraptions.actors.seat.SeatMovementBehaviour;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.utility.DyeHelper;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import com.aesefficio.interiors.CreateInteriors;
import com.aesefficio.interiors.content.block.CushionBlock;
import com.aesefficio.interiors.content.block.WallMountedTable;
import com.aesefficio.interiors.content.block.chair.BigChairBlock;
import com.aesefficio.interiors.content.block.chair.BigSeatMovementBehaviour;
import com.aesefficio.interiors.content.block.chair.ChairBlock;
import com.aesefficio.interiors.content.block.chair.DirectionalSeatBlock;
import com.aesefficio.interiors.content.block.chair.FloorChairBlock;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;

#if forge
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
#elif neoforge
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
#elif fabric
import io.github.fabricators_of_create.porting_lib.models.generators.ModelBuilder;
import io.github.fabricators_of_create.porting_lib.models.generators.block.BlockModelBuilder;
import io.github.fabricators_of_create.porting_lib.models.generators.block.BlockModelProvider;
import io.github.fabricators_of_create.porting_lib.models.generators.block.BlockStateProvider;
import io.github.fabricators_of_create.porting_lib.models.generators.ConfiguredModel;
import io.github.fabricators_of_create.porting_lib.models.generators.ModelFile;
#endif

import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.simibubi.create.api.behaviour.interaction.MovingInteractionBehaviour.interactionBehaviour;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.foundation.block.ProperWaterloggedBlock.WATERLOGGED;
import static com.simibubi.create.foundation.data.TagGen.axeOnly;
import static com.aesefficio.interiors.CreateInteriors.REGISTRATE;

@SuppressWarnings("unused")
public final class CIBlocks {

	public static final BlockEntry<Block> SEATWOOD_PLANKS = REGISTRATE.block("seatwood_planks", Block::new)
		.initialProperties(SharedProperties::wooden)
		.properties(p -> p.mapColor(DyeColor.ORANGE))
		.transform(axeOnly())
		.tag(BlockTags.PLANKS)
		.item()
		.tag(ItemTags.PLANKS)
		.build()
		.register();

	public static final BlockEntry<WallMountedTable> WALL_MOUNTED_TABLE = REGISTRATE.block("wall_mounted_table", WallMountedTable::new)
		.initialProperties(SharedProperties::wooden)
		.properties(p -> p.mapColor(DyeColor.ORANGE))
		.transform(axeOnly())
		.blockstate((c, p) -> p.getVariantBuilder(c.get())
			.forAllStatesExcept(state -> {
				String facing = state.getValue(ChairBlock.FACING).getSerializedName();

				ModelFile model = p.models().getExistingFile(p.modLoc("block/wall_mounted_table"));
				return ConfiguredModel.builder()
						.modelFile(model)
						.rotationY(facing(state))
						.build();
			}, WATERLOGGED))
		.simpleItem()
		.register();

	public static final DyedBlockList<FloorChairBlock> FLOOR_CHAIRS = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_floor_chair", p -> new FloorChairBlock(p, color))
			.initialProperties(SharedProperties::wooden)
			.properties(p -> p.mapColor(color))
			.transform(axeOnly())
			.blockstate((c, p) -> p.getVariantBuilder(c.get())
				.forAllStatesExcept(state -> {
					String armrest = state.getValue(ChairBlock.ARMRESTS).getSerializedName();
					String cropped_state = state.getValue(ChairBlock.CROPPED_BACK) ? "_cropped" : "";

					ResourceLocation top = Create.asResource("block/seat/top_" + colorName);
					ResourceLocation side = Create.asResource("block/seat/side_" + colorName);
					ResourceLocation sideTop = p.modLoc("block/chair/side_top_" + colorName);

					return chairModels(
							p,
							"block/floor_chair/",
							colorName + "_floor_chair_",
							armrest + cropped_state,
							top, side,  sideTop, side,
							facing(state)
					);
				}, WATERLOGGED))
			.recipe((c, p) -> {
				ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get())
					.requires(ItemTags.WOODEN_SLABS)
					.requires(ItemTags.WOODEN_SLABS)
					.requires(DyeHelper.getWoolOfDye(color))
					.unlockedBy("has_seat", RegistrateRecipeProvider.has(AllItemTags.SEATS.tag))
					.save(p, CreateInteriors.id("crafting/floor_chair/" + c.getName()));

				ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get())
					.requires(ItemTags.WOODEN_SLABS)
					.requires(AllBlocks.SEATS.get(color))
					.unlockedBy("has_seat", RegistrateRecipeProvider.has(AllItemTags.SEATS.tag))
					.save(p, CreateInteriors.id("crafting/floor_chair/" + c.getName() + "_from_seat"));

				ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get())
					.requires(CITags.Items.FLOOR_CHAIRS)
					.requires(CITags.DYES.get(color))
					.unlockedBy("has_floor_chair", RegistrateRecipeProvider.has(CITags.Items.FLOOR_CHAIRS))
					.save(p, CreateInteriors.id("crafting/floor_chair/" + c.getName() + "_from_other_floor_chair"));
			})
			.onRegister(movementBehaviour(new SeatMovementBehaviour()))
			.onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
			.transform(displaySource(AllDisplaySources.ENTITY_NAME))
			.onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.interiors.chair"))
			.tag(CITags.Blocks.FLOOR_CHAIRS)
			.item().tag(CITags.Items.FLOOR_CHAIRS)
			.model(AssetLookup.customBlockItemModel("floor_chair", colorName + "_floor_chair_" + ChairBlock.ArmrestConfiguration.DEFAULT.getSerializedName()))
			.build().register();
	});

	public static final DyedBlockList<BigChairBlock> CHAIRS = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();

		return REGISTRATE.block(colorName + "_chair", p -> new BigChairBlock(p, color))
			.initialProperties(SharedProperties::wooden)
			.properties(p -> p.mapColor(color))
			.transform(axeOnly())
			.blockstate((c, p) -> p.getVariantBuilder(c.get())
				.forAllStatesExcept(state -> {
					String armrest = state.getValue(ChairBlock.ARMRESTS).getSerializedName();
					String cropped_state = state.getValue(ChairBlock.CROPPED_BACK) ? "_cropped" : "";

					ResourceLocation top = Create.asResource("block/seat/top_" + colorName);
					ResourceLocation side = Create.asResource("block/seat/side_" + colorName);
					ResourceLocation sideTop = p.modLoc("block/chair/side_top_" + colorName);

					return chairModels(
							p,
							"block/chair/",
							colorName + "_chair_",
							armrest + cropped_state,
							top, side, sideTop, side,
							facing(state)
					);
				}, WATERLOGGED))
			.recipe((c, p) -> {
				ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get())
					.requires(ItemTags.WOODEN_SLABS)
					.requires(ItemTags.PLANKS)
					.requires(DyeHelper.getWoolOfDye(color))
					.unlockedBy("has_seat", RegistrateRecipeProvider.has(AllItemTags.SEATS.tag))
					.save(p, CreateInteriors.id("crafting/chair/" + c.getName()));

				ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get())
					.requires(ItemTags.PLANKS)
					.requires(AllBlocks.SEATS.get(color))
					.unlockedBy("has_seat", RegistrateRecipeProvider.has(AllItemTags.SEATS.tag))
					.save(p, CreateInteriors.id("crafting/chair/" + c.getName() + "_from_seat"));
				ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get())
					.requires(ItemTags.WOODEN_SLABS)
					.requires(FLOOR_CHAIRS.get(color))
					.unlockedBy("has_floor_chair", RegistrateRecipeProvider.has(CITags.Items.FLOOR_CHAIRS))
					.save(p, CreateInteriors.id("crafting/chair/" + c.getName() + "_from_floor_chair"));

				ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get())
					.requires(CITags.Items.CHAIRS)
					.requires(CITags.DYES.get(color))
					.unlockedBy("has_chair", RegistrateRecipeProvider.has(CITags.Items.CHAIRS))
					.save(p, CreateInteriors.id("crafting/chair/" + c.getName() + "_from_other_chair"));
			})
			.onRegister(movementBehaviour(new BigSeatMovementBehaviour()))
			.onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
			.transform(displaySource(AllDisplaySources.ENTITY_NAME))
			.onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.interiors.chair"))
			.tag(CITags.Blocks.CHAIRS)
			.item()
			.tag(CITags.Items.CHAIRS)
			.model(AssetLookup.customBlockItemModel("chair", colorName + "_chair_" + ChairBlock.ArmrestConfiguration.DEFAULT.getSerializedName()))
			.build()
			.register();
	});

	public static final BlockEntry<BigChairBlock> KELP_CHAIR = REGISTRATE.block("kelp_chair", p -> new BigChairBlock(p, DyeColor.BLACK))
		.initialProperties(SharedProperties::wooden)
		.properties(p -> p.mapColor(DyeColor.BLACK))
		.transform(axeOnly())
		.blockstate((c, p) -> p.getVariantBuilder(c.get())
			.forAllStatesExcept(state -> {
				String armrest = state.getValue(ChairBlock.ARMRESTS).getSerializedName();
				String cropped_state = state.getValue(ChairBlock.CROPPED_BACK) ? "_cropped" : "";

				return chairModels(
						p,
						"block/chair/",
						"kelp_chair_",
						armrest + cropped_state,
						null, null, null, null,
						facing(state)
				);
			}, WATERLOGGED))
		.onRegister(movementBehaviour(new BigSeatMovementBehaviour()))
		.onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
		.onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.interiors.chair"))
		.item()
		.model(AssetLookup.customBlockItemModel("chair", ChairBlock.ArmrestConfiguration.DEFAULT.getSerializedName()))
		.build()
		.register();

	public static final BlockEntry<FloorChairBlock> KELP_FLOOR_CHAIR = REGISTRATE.block("kelp_floor_chair", p -> new FloorChairBlock(p, DyeColor.BLACK))
		.initialProperties(SharedProperties::wooden)
		.properties(p -> p.mapColor(DyeColor.BLACK))
		.transform(axeOnly())
		.blockstate((c, p) -> p.getVariantBuilder(c.get())
			.forAllStatesExcept(state -> {
				String armrest = state.getValue(ChairBlock.ARMRESTS).getSerializedName();
				String cropped_state = state.getValue(ChairBlock.CROPPED_BACK) ? "_cropped" : "";

				return chairModels(
						p,
						"block/floor_chair/",
						"kelp_floor_chair_",
						armrest + cropped_state,
						null, null, null, null,
						facing(state)
				);
			}, WATERLOGGED))
		.onRegister(movementBehaviour(new SeatMovementBehaviour()))
		.onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
		.onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.interiors.chair"))
		.item()
		.model(AssetLookup.customBlockItemModel("chair", ChairBlock.ArmrestConfiguration.DEFAULT.getSerializedName()))
		.build()
		.register();

	public static final BlockEntry<DirectionalSeatBlock> KELP_SEAT = REGISTRATE.block("kelp_seat", p -> new DirectionalSeatBlock(p, DyeColor.BLACK))
		.initialProperties(SharedProperties::wooden)
		.properties(p -> p.mapColor(DyeColor.BLACK))
		.transform(axeOnly())
		.blockstate((c, p) -> p.getVariantBuilder(c.get())
			.forAllStatesExcept(state -> {
				String facing = state.getValue(ChairBlock.FACING).getSerializedName();
				int rotation = facing(state);
				ModelFile model = p.models().getExistingFile(p.modLoc("block/kelp_seat"));
				return ConfiguredModel.builder()
						.modelFile(model)
						.rotationY(rotation)
						.build();
			}, WATERLOGGED))
		.onRegister(movementBehaviour(new SeatMovementBehaviour()))
		.onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
		.transform(displaySource(AllDisplaySources.ENTITY_NAME))
		.onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.create.seat"))
		.simpleItem()
		.register();

	public static final DyedBlockList<CushionBlock> CUSHION_BLOCKS = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_cushion", CushionBlock::new)
			.initialProperties(SharedProperties::wooden)
			.properties(p -> p.mapColor(color))
			.transform(b -> b.tag(BlockTags.MINEABLE_WITH_AXE).tag(BlockTags.WOOL))
			.blockstate((c, p) -> {
				ResourceLocation texture = Create.asResource("block/seat/top_" + colorName);
				p.simpleBlock(((DataGenContext<Block, ?>) c).get(), p.models().cubeAll(c.getName(), texture));
			})
			.onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.interiors.cushion"))
			.recipe((c, p) ->
				ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get(), 2)
					.requires(ItemTags.PLANKS)
					.requires(DyeHelper.getWoolOfDye(color))
					.unlockedBy("has_planks", RegistrateRecipeProvider.has(ItemTags.PLANKS))
					.save(p, CreateInteriors.id("crafting/cushion/" + c.getName())))
			.simpleItem()
			.register();
	});

	public static void register() {
		// load class
	}

	private static int facing(BlockState state) {
		return switch(state.getValue(ChairBlock.FACING)) {
			case NORTH, UP, DOWN -> 0;
			case EAST -> 90;
			case SOUTH -> 180;
			case WEST -> 270;
		};
	}

	private static ConfiguredModel[] chairModels(
			BlockStateProvider p,
			String path, String detailer, String specifier,
			ResourceLocation top, ResourceLocation side,
			ResourceLocation sideTop, ResourceLocation sideFront,
			int rotation
	) {
		BlockModelBuilder model = p.models().withExistingParent(path + detailer + specifier,
						p.modLoc(path + specifier));
		if (top != null) model.texture("top", top);
		if (side != null) model.texture("side", side);
		if (sideTop != null) model.texture("side_top", sideTop);
		if (sideFront != null) model.texture("side_front", sideFront);
		return ConfiguredModel.builder()
				.modelFile(model)
				.rotationY(rotation)
				.build();
	}
}
