package systems.alexander.interiors.registry;

import systems.alexander.interiors.CreateInteriors;
import systems.alexander.interiors.block.seat.BigSeatMovementBehaviour;
import systems.alexander.interiors.block.seat.ChairBlock;
import systems.alexander.interiors.block.seat.DirectionalSeatBlock;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import com.simibubi.create.AllTags.AllBlockTags;
import com.simibubi.create.AllTags.AllItemTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.actors.seat.SeatInteractionBehaviour;
import com.simibubi.create.content.contraptions.actors.seat.SeatMovementBehaviour;
import com.simibubi.create.content.redstone.displayLink.source.EntityNameDisplaySource;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.item.ItemDescription;
import com.tterrag.registrate.util.entry.BlockEntry;

import static com.simibubi.create.AllInteractionBehaviours.interactionBehaviour;
import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;
import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.assignDataBehaviour;
import static com.simibubi.create.foundation.block.ProperWaterloggedBlock.WATERLOGGED;
import static com.simibubi.create.foundation.data.TagGen.axeOnly;
import static systems.alexander.interiors.CreateInteriors.REGISTRATE;

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
                .blockstate((context, provider) -> provider.getVariantBuilder(context.get())
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

                            //TODO add the correct keys for the textures
                            return ConfiguredModel.builder()
                                    .modelFile(provider.models()
                                            .withExistingParent("block/chair/" + colorName + "_chair_" + armrest + "_" + facing,
                                                    provider.modLoc("block/chair/chair_" + armrest))
                                            .texture("AAAAAAAA", Create.asResource("block/seat/top_" + colorName))
                                            .texture("AAAAAAAA", provider.modLoc("block/side_top/side_top_" + colorName))
                                            .texture("AAAAAAAA", Create.asResource("block/seat/side_" + colorName))
                                            .texture("AAAAAAAA", Create.asResource("block/seat/side_" + colorName)))
                                    .rotationY(rotation)
                                    .build();

                        }, WATERLOGGED))
                .onRegister(movementBehaviour(movementBehaviour))
                .onRegister(interactionBehaviour(interactionBehaviour))
                .onRegister(assignDataBehaviour(new EntityNameDisplaySource(), "entity_name"))
                .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.interiors.chair"))
                .tag(CITags.Blocks.CHAIRS)
                .item()
                .tag(CITags.Items.CHAIRS)
                .model(AssetLookup.customBlockItemModel("chair", colorName + "_chair_both_north"))
                .build()
                .register();
    });

    public static final BlockEntry<ChairBlock> KELP_CHAIR = REGISTRATE
            .block("kelp_chair", p -> new ChairBlock(p, DyeColor.BLACK))
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(DyeColor.BLACK))
            .transform(axeOnly())
            .blockstate((context, provider) -> provider.getVariantBuilder(context.get())
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

                        return ConfiguredModel.builder()
                                .modelFile(provider.models()
                                                   .withExistingParent("block/chair/kelp_chair_" + armrest + "_" + facing,
                                                           provider.modLoc("block/chair/chair_" + armrest)))
                                .rotationY(rotation)
                                .build();
                    }, WATERLOGGED))
            .onRegister(movementBehaviour(new BigSeatMovementBehaviour()))
            .onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
            .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.interiors.chair"))
            .tag(CITags.Blocks.CHAIRS)
            .item()
            .tag(CITags.Items.CHAIRS)
            .model(AssetLookup.customBlockItemModel("chair", "kelp_chair_both_north"))
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
                                              .modelFile(provider.models()
                                                                 .withExistingParent("kelp_seat_" + facing,
                                                                         provider.modLoc("block/kelp_seat")))
                                              .rotationY(rotation)
                                .build();
                    }, WATERLOGGED))
            .onRegister(movementBehaviour(new SeatMovementBehaviour()))
            .onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
            .onRegister(assignDataBehaviour(new EntityNameDisplaySource(), "entity_name"))
            .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.create.seat"))
            .tag(AllBlockTags.SEATS.tag)
            .item()
            .tag(AllItemTags.SEATS.tag)
            .build()
            .register();

    public static void register() {
        CreateInteriors.LOGGER.info("Registering blocks!");
    }
}