package com.sudolev.interiors.content.registry;


import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.actors.seat.SeatInteractionBehaviour;
import com.simibubi.create.content.contraptions.actors.seat.SeatMovementBehaviour;
import com.simibubi.create.content.redstone.displayLink.source.EntityNameDisplaySource;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.utility.DyeHelper;

import net.minecraft.core.Registry;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;

import com.sudolev.interiors.CreateInteriors;
import com.sudolev.interiors.content.block.WallMountedTable;
import com.sudolev.interiors.content.block.chair.BigChairBlock;
import com.sudolev.interiors.content.block.chair.BigSeatMovementBehaviour;
import com.sudolev.interiors.content.block.chair.ChairBlock;
import com.sudolev.interiors.content.block.chair.DirectionalSeatBlock;
import com.sudolev.interiors.content.block.chair.FloorChairBlock;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;

#if FABRIC && MC >= "20.1"
import io.github.fabricators_of_create.porting_lib.models.generators.ConfiguredModel;
import io.github.fabricators_of_create.porting_lib.models.generators.ModelFile;
import io.github.fabricators_of_create.porting_lib.models.generators.block.BlockStateProvider;
#else
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
#endif

import static com.simibubi.create.AllInteractionBehaviours.interactionBehaviour;
import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;
import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.assignDataBehaviour;
import static com.simibubi.create.foundation.block.ProperWaterloggedBlock.WATERLOGGED;
import static com.simibubi.create.foundation.data.TagGen.axeOnly;
import static com.sudolev.interiors.CreateInteriors.REGISTRATE;

@SuppressWarnings("unused")
public final class CIBlocks {

	static {
		#if MC <= "19.2"
		REGISTRATE.creativeModeTab(CITab::getInstance);
		#else
			#if FABRIC
			REGISTRATE.setCreativeTab(CITab.TAB.key());
			#elif FORGE
			REGISTRATE.setCreativeTab(CITab.TAB);
			#endif
		#endif
	}

	#if MC <= "19.2"
	private static final ResourceKey<Registry<Item>> ITEM_REGISTRY = Registry.ITEM_REGISTRY;
	private static final Object RECIPE_CATEGORY_BUILDING_BLOCKS = null;
	#else
	private static final ResourceKey<Registry<Item>> ITEM_REGISTRY = net.minecraft.core.registries.Registries.ITEM;
	private static final net.minecraft.data.recipes.RecipeCategory RECIPE_CATEGORY_BUILDING_BLOCKS =net.minecraft.data.recipes.RecipeCategory.BUILDING_BLOCKS;
	#endif

	public static final BlockEntry<Block> SEATWOOD_PLANKS = REGISTRATE.block("seatwood_planks", Block::new)
		.initialProperties(SharedProperties::wooden)
		.properties(color(DyeColor.ORANGE))
		.transform(axeOnly())
		.tag(BlockTags.PLANKS)
		.item()
		.tag(ItemTags.PLANKS)
		.build()
		.register();

	public static final BlockEntry<WallMountedTable> WALL_MOUNTED_TABLE = REGISTRATE.block("wall_mounted_table", WallMountedTable::new)
		.initialProperties(SharedProperties::wooden)
		.properties(color(DyeColor.ORANGE))
		.transform(axeOnly())
		.blockstate((c, p) -> p.getVariantBuilder(c.get())
			.forAllStatesExcept(state -> {
				String facing = state.getValue(WallMountedTable.FACING).getSerializedName();
				int rotation = facing(state);

				ModelFile model = p.models().getExistingFile(p.modLoc("block/wall_mounted_table"));
				return ConfiguredModel.builder()
					.modelFile(model)
					.rotationY(rotation)
					.build();
			}, WATERLOGGED))
		.simpleItem()
		.register();

	public static final DyedBlockList<FloorChairBlock> FLOOR_CHAIRS = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();
		return REGISTRATE.block(colorName + "_floor_chair", p -> new FloorChairBlock(p, color))
			.initialProperties(SharedProperties::wooden)
			.properties(color(color))
			.transform(axeOnly())
			.blockstate((c, p) -> p.getVariantBuilder(c.get())
				.forAllStatesExcept(state -> {
					String armrest = state.getValue(ChairBlock.ARMRESTS).getSerializedName();
					String cropped_state = state.getValue(ChairBlock.CROPPED_BACK) ? "_cropped" : "";

					int rotation = facing(state);

					ResourceLocation top = Create.asResource("block/seat/top_" + colorName);
					ResourceLocation side = Create.asResource("block/seat/side_" + colorName);
					ResourceLocation sideTop = p.modLoc("block/chair/side_top_" + colorName);

					ModelFile model = customChairModelFile(p, "block/floor_chair/" + armrest + cropped_state,
						"block/floor_chair/" + colorName + "_floor_chair_" + armrest + cropped_state,
						top, side, sideTop, side);
					return ConfiguredModel.builder()
						.modelFile(model)
						.rotationY(rotation)
						.build();
				}, WATERLOGGED))
			.recipe((c, p) -> {
				shapeless(RECIPE_CATEGORY_BUILDING_BLOCKS, c.get())
					.requires(ItemTags.WOODEN_SLABS)
					.requires(ItemTags.WOODEN_SLABS)
					.requires(DyeHelper.getWoolOfDye(color))
					.unlockedBy("has_seat", RegistrateRecipeProvider.has(AllItemTags.SEATS.tag))
					.save(p, CreateInteriors.asResource("crafting/floor_chair/" + c.getName()));

				shapeless(RECIPE_CATEGORY_BUILDING_BLOCKS, c.get())
					.requires(ItemTags.WOODEN_SLABS)
					.requires(AllBlocks.SEATS.get(color) #if MC <= "19.2" .get() #endif)
					.unlockedBy("has_seat", RegistrateRecipeProvider.has(AllItemTags.SEATS.tag))
					.save(p, CreateInteriors.asResource("crafting/floor_chair/" + c.getName() + "_from_seat"));

				shapeless(RECIPE_CATEGORY_BUILDING_BLOCKS, c.get())
					.requires(CITags.Items.FLOOR_CHAIRS)
					.requires(CITags.DYES.get(color).tag)
					.unlockedBy("has_floor_chair", RegistrateRecipeProvider.has(CITags.Items.FLOOR_CHAIRS))
					.save(p, CreateInteriors.asResource("crafting/floor_chair/" + c.getName() + "_from_other_floor_chair"));
			})
			.onRegister(movementBehaviour(new SeatMovementBehaviour()))
			.onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
			.onRegister(assignDataBehaviour(new EntityNameDisplaySource(), "entity_name"))
			.onRegisterAfter(ITEM_REGISTRY, v -> ItemDescription.useKey(v, "block.interiors.chair"))
			.tag(CITags.Blocks.FLOOR_CHAIRS)
			.item().tag(CITags.Items.FLOOR_CHAIRS)
			.model(AssetLookup.customBlockItemModel("floor_chair",
				colorName + "_floor_chair_" + ChairBlock.ArmrestConfiguration.DEFAULT.getSerializedName()))
			.build().register();
	});

	public static final DyedBlockList<BigChairBlock> CHAIRS = new DyedBlockList<>(color -> {
		String colorName = color.getSerializedName();

		return REGISTRATE.block(colorName + "_chair", p -> new BigChairBlock(p, color))
			.initialProperties(SharedProperties::wooden)
			.properties(color(color))
			.transform(axeOnly())
			.blockstate((c, p) -> p.getVariantBuilder(c.get())
				.forAllStatesExcept(state -> {
					String armrest = state.getValue(ChairBlock.ARMRESTS).getSerializedName();
					String cropped_state = state.getValue(ChairBlock.CROPPED_BACK) ? "_cropped" : "";

					int rotation = facing(state);

					ResourceLocation top = Create.asResource("block/seat/top_" + colorName);
					ResourceLocation side = Create.asResource("block/seat/side_" + colorName);
					ResourceLocation sideTop = p.modLoc("block/chair/side_top_" + colorName);

					ModelFile model = customChairModelFile(p, "block/chair/" + armrest + cropped_state,
						"block/chair/" + colorName + "_chair_" + armrest + cropped_state,
						top, side, sideTop, side);
					return ConfiguredModel.builder()
						.modelFile(model)
						.rotationY(rotation)
						.build();
				}, WATERLOGGED))
			.recipe((c, p) -> {
				shapeless(RECIPE_CATEGORY_BUILDING_BLOCKS, c.get())
					.requires(ItemTags.WOODEN_SLABS)
					.requires(ItemTags.PLANKS)
					.requires(DyeHelper.getWoolOfDye(color))
					.unlockedBy("has_seat", RegistrateRecipeProvider.has(AllItemTags.SEATS.tag))
					.save(p, CreateInteriors.asResource("crafting/chair/" + c.getName()));

				shapeless(RECIPE_CATEGORY_BUILDING_BLOCKS, c.get())
					.requires(ItemTags.PLANKS)
					.requires(AllBlocks.SEATS.get(color) #if MC <= "19.2" .get() #endif)
					.unlockedBy("has_seat", RegistrateRecipeProvider.has(AllItemTags.SEATS.tag))
					.save(p, CreateInteriors.asResource("crafting/chair/" + c.getName() + "_from_seat"));
				shapeless(RECIPE_CATEGORY_BUILDING_BLOCKS, c.get())
					.requires(ItemTags.WOODEN_SLABS)
					.requires(FLOOR_CHAIRS.get(color) #if MC <= "19.2" .get() #endif)
					.unlockedBy("has_floor_chair", RegistrateRecipeProvider.has(CITags.Items.FLOOR_CHAIRS))
					.save(p, CreateInteriors.asResource("crafting/chair/" + c.getName() + "_from_floor_chair"));

				shapeless(RECIPE_CATEGORY_BUILDING_BLOCKS, c.get())
					.requires(CITags.Items.CHAIRS)
					.requires(CITags.DYES.get(color).tag)
					.unlockedBy("has_chair", RegistrateRecipeProvider.has(CITags.Items.CHAIRS))
					.save(p, CreateInteriors.asResource("crafting/chair/" + c.getName() + "_from_other_chair"));
			})
			.onRegister(movementBehaviour(new BigSeatMovementBehaviour()))
			.onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
			.onRegister(assignDataBehaviour(new EntityNameDisplaySource(), "entity_name"))
			.onRegisterAfter(ITEM_REGISTRY, v -> ItemDescription.useKey(v, "block.interiors.chair"))
			.tag(CITags.Blocks.CHAIRS)
			.item()
			.tag(CITags.Items.CHAIRS)
			.model(AssetLookup.customBlockItemModel("chair", colorName + "_chair_" + ChairBlock.ArmrestConfiguration.DEFAULT.getSerializedName()))
			.build()
			.register();
	});

	public static final BlockEntry<BigChairBlock> KELP_CHAIR = REGISTRATE.block("kelp_chair", p -> new BigChairBlock(p, DyeColor.BLACK))
		.initialProperties(SharedProperties::wooden)
		.properties(color(DyeColor.BLACK))
		.transform(axeOnly())
		.blockstate((c, p) -> p.getVariantBuilder(c.get())
			.forAllStatesExcept(state -> {
				String armrest = state.getValue(ChairBlock.ARMRESTS).getSerializedName();
				String cropped_state = state.getValue(ChairBlock.CROPPED_BACK) ? "_cropped" : "";

				int rotation = facing(state);

				ModelFile model = p.models().withExistingParent(
					"block/chair/" + armrest + cropped_state,
						"block/chair/kelp_chair_" + armrest + cropped_state
					);
				return ConfiguredModel.builder()
					.modelFile(model)
					.rotationY(rotation)
					.build();
			}, WATERLOGGED))
		.onRegister(movementBehaviour(new BigSeatMovementBehaviour()))
		.onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
		.onRegisterAfter(ITEM_REGISTRY, v -> ItemDescription.useKey(v, "block.interiors.chair"))
		.item()
		.model(AssetLookup.customBlockItemModel("chair", "kelp_chair_" + ChairBlock.ArmrestConfiguration.DEFAULT.getSerializedName()))
		.build()
		.register();

	public static final BlockEntry<FloorChairBlock> KELP_FLOOR_CHAIR = REGISTRATE.block("kelp_floor_chair", p -> new FloorChairBlock(p, DyeColor.BLACK))
		.initialProperties(SharedProperties::wooden)
		.properties(color(DyeColor.BLACK))
		.transform(axeOnly())
		.blockstate((c, p) -> p.getVariantBuilder(c.get())
			.forAllStatesExcept(state -> {
				String armrest = state.getValue(ChairBlock.ARMRESTS).getSerializedName();
				String cropped_state = state.getValue(ChairBlock.CROPPED_BACK) ? "_cropped" : "";

				int rotation = facing(state);
				ModelFile model = p.models().withExistingParent(
					"block/floor_chair/" + armrest + cropped_state,
						"block/chair/kelp_floor_chair_" + armrest + cropped_state
					);
				return ConfiguredModel.builder()
					.modelFile(model)
					.rotationY(rotation)
					.build();
			}, WATERLOGGED))
		.onRegister(movementBehaviour(new SeatMovementBehaviour()))
		.onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
		.onRegisterAfter(ITEM_REGISTRY, v -> ItemDescription.useKey(v, "block.interiors.chair"))
		.item()
		.model(AssetLookup.customBlockItemModel("chair", "kelp_floor_chair_" + ChairBlock.ArmrestConfiguration.DEFAULT.getSerializedName()))
		.build()
		.register();

	public static final BlockEntry<DirectionalSeatBlock> KELP_SEAT = REGISTRATE.block("kelp_seat", p -> new DirectionalSeatBlock(p, DyeColor.BLACK))
		.initialProperties(SharedProperties::wooden)
		.properties(color(DyeColor.BLACK))
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
		.onRegister(assignDataBehaviour(new EntityNameDisplaySource(), "entity_name"))
		.onRegisterAfter(ITEM_REGISTRY, v -> ItemDescription.useKey(v, "block.create.seat"))
		.simpleItem()
		.register();

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

	private static ModelFile customChairModelFile(BlockStateProvider p, String parent, String name,
												  ResourceLocation top, ResourceLocation side, ResourceLocation sideTop, ResourceLocation sideFront) {
		return p.models()
			.withExistingParent(name,
				p.modLoc(parent))
			.texture("top", top)
			.texture("side_top", sideTop)
			.texture("side_front", sideFront)
			.texture("side", side);
	}

	private static NonNullUnaryOperator<Properties> color(DyeColor color) {
		#if MC <= "19.2"
		return p -> p.color(color.getMaterialColor());
		#else
		return p -> p.mapColor(color);
		#endif
	}

	private static ShapelessRecipeBuilder shapeless(Object category, Block block) {
		#if MC <= "19.2"
		return ShapelessRecipeBuilder.shapeless(block);
		#else
		return ShapelessRecipeBuilder.shapeless((net.minecraft.data.recipes.RecipeCategory) java.util.Objects.requireNonNull(category), block);
		#endif
	}
}
