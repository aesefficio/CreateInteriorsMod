package com.sudolev.interiors.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ConfiguredModel;

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
import com.sudolev.interiors.CreateInteriors;
import com.sudolev.interiors.block.seat.BigSeatMovementBehaviour;
import com.sudolev.interiors.block.seat.ChairBlock;
import com.sudolev.interiors.block.seat.DirectionalSeatBlock;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;

import static com.simibubi.create.AllInteractionBehaviours.interactionBehaviour;
import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;
import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.assignDataBehaviour;
import static com.simibubi.create.foundation.block.ProperWaterloggedBlock.WATERLOGGED;
import static com.simibubi.create.foundation.data.TagGen.axeOnly;
import static com.sudolev.interiors.CreateInteriors.REGISTRATE;

@SuppressWarnings("unused")
public class CIBlocks {

    static {
        REGISTRATE.setCreativeTab(CITab.TAB);
    }

    public static final BlockEntry<Block> SEATWOOD_PLANKS = REGISTRATE
            .block("seatwood_planks", Block::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(DyeColor.ORANGE))
            .transform(axeOnly())
            .simpleItem()
            .register();

    public static final DyedBlockList<ChairBlock> CHAIRS = new DyedBlockList<>(color -> {
        String colorName = color.getSerializedName();
        BigSeatMovementBehaviour movementBehaviour = new BigSeatMovementBehaviour();
        SeatInteractionBehaviour interactionBehaviour = new SeatInteractionBehaviour();
        return REGISTRATE.block(colorName + "_chair", p -> new ChairBlock(p, color))
                .initialProperties(SharedProperties::wooden)
                .properties(p -> p.mapColor(color))
                .transform(axeOnly())
                .blockstate(chairBlockstates(colorName))
                .recipe((c, p) -> {
                    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get())
                            .requires(ItemTags.WOODEN_SLABS)
                            .requires(ItemTags.WOODEN_SLABS)
                            .requires(DyeHelper.getWoolOfDye(color))
                            .unlockedBy("has_seat", RegistrateRecipeProvider.has(AllItemTags.SEATS.tag))
                            .save(p, CreateInteriors.asResource("crafting/chair/" + c.getName()));
                    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get())
                            .requires(ItemTags.WOODEN_SLABS)
                            .requires(AllBlocks.SEATS.get(color))
                            .unlockedBy("has_seat", RegistrateRecipeProvider.has(AllItemTags.SEATS.tag))
                            .save(p, CreateInteriors.asResource("crafting/chair/" + c.getName() + "_from_seat"));
                    ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get())
                            .requires(CITags.Items.CHAIRS)
                            .requires(color.getTag())
                            .unlockedBy("has_chair", RegistrateRecipeProvider.has(CITags.Items.CHAIRS))
                            .save(p, CreateInteriors.asResource("crafting/chair/" + c.getName() + "_from_other_chair"));
                })
                .onRegister(movementBehaviour(movementBehaviour))
                .onRegister(interactionBehaviour(interactionBehaviour))
                .onRegister(assignDataBehaviour(new EntityNameDisplaySource(), "entity_name"))
                .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.interiors.chair"))
                .tag(CITags.Blocks.CHAIRS)
                .item()
                .tag(CITags.Items.CHAIRS)
                .model(AssetLookup.customBlockItemModel("chair", colorName + "_chair_" + ChairBlock.ArmrestConfiguration.DEFAULT.getSerializedName()))
                .build()
                .register();
    });

    public static final BlockEntry<ChairBlock> KELP_CHAIR = REGISTRATE
            .block("kelp_chair", p -> new ChairBlock(p, DyeColor.BLACK))
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(DyeColor.BLACK))
            .transform(axeOnly())
            .blockstate(chairBlockstates("kelp"))
            .onRegister(movementBehaviour(new BigSeatMovementBehaviour()))
            .onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
            .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.interiors.chair"))
            .item()
            .model(AssetLookup.customBlockItemModel("chair", "kelp_chair_" + ChairBlock.ArmrestConfiguration.DEFAULT.getSerializedName()))
            .build()
            .register();

    public static final BlockEntry<DirectionalSeatBlock> KELP_SEAT = REGISTRATE
            .block("kelp_seat", p -> new DirectionalSeatBlock(p, DyeColor.BLACK))
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(DyeColor.BLACK))
            .transform(axeOnly())
            .blockstate((context, provider) -> provider.getVariantBuilder(context.get())
                    .forAllStatesExcept(state -> {
                        String facing = state.getValue(ChairBlock.FACING).getSerializedName();

                        int rotation = switch(state.getValue(ChairBlock.FACING)) {
                            case NORTH -> 0;
                            case EAST -> 90;
                            case SOUTH -> 180;
                            case WEST -> 270;
                            default -> 0;
                        };

                        return ConfiguredModel.builder()
                                              .modelFile(provider.models().getExistingFile(provider.modLoc("block/kelp_seat")))
                                              .rotationY(rotation)
                                .build();
                    }, WATERLOGGED))
            .onRegister(movementBehaviour(new SeatMovementBehaviour()))
            .onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
            .onRegister(assignDataBehaviour(new EntityNameDisplaySource(), "entity_name"))
            .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.create.seat"))
            .item()
            .build()
            .register();

    public static void register() {
        CreateInteriors.LOGGER.info("Registering blocks!");
    }

    private static NonNullBiConsumer<DataGenContext<Block, ChairBlock>, RegistrateBlockstateProvider> chairBlockstates(String color) {
        return (context, provider) ->
                provider.getVariantBuilder(context.get())
                        .forAllStatesExcept(state -> {
                            String armrest = state.getValue(ChairBlock.ARMRESTS).getSerializedName();
                            String facing = state.getValue(ChairBlock.FACING).getSerializedName();

                            int rotation = switch(state.getValue(ChairBlock.FACING)) {
                                case NORTH -> 0;
                                case EAST -> 90;
                                case SOUTH -> 180;
                                case WEST -> 270;
                                default -> 0;
                            };

                            ResourceLocation top;
                            ResourceLocation sideTop = provider.modLoc("block/chair/side_top/side_top_" + color);
                            ResourceLocation side;
                            ResourceLocation sideFront;
							if(color.equals("kelp")) {
                                top = Create.asResource("block/belt_offset");
                                side = provider.modLoc("block/chair/side/side_kelp_side");
                                sideFront = provider.modLoc("block/chair/side/side_kelp_front");
                            } else {
                                top = Create.asResource("block/seat/top_" + color);
                                side = Create.asResource("block/seat/side_" + color);
                                sideFront = side;
							}


							return ConfiguredModel.builder()
                                                  .modelFile(provider.models()
                                                                     .withExistingParent("block/chair/" + color + "_chair_" + armrest,
                                                                             provider.modLoc("block/chair/chair_" + armrest))
                                                                     .texture("top", top)
                                                                     .texture("side_top", sideTop)
                                                                     .texture("side", side)
                                                                     .texture("side_front", sideFront))
                                                  .rotationY(rotation)
                                                  .build();
                            }, WATERLOGGED);
    }
}