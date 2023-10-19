package com.sudolev.interiors.block;

import static com.simibubi.create.AllInteractionBehaviours.interactionBehaviour;
import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;
import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.assignDataBehaviour;
import static com.simibubi.create.foundation.data.BlockStateGen.axisBlock;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOnly;

import com.simibubi.create.content.contraptions.actors.seat.SeatInteractionBehaviour;
import com.simibubi.create.content.contraptions.actors.seat.SeatMovementBehaviour;

import com.simibubi.create.content.redstone.displayLink.source.EntityNameDisplaySource;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.item.ItemDescription;
import com.sudolev.interiors.Interiors;
import com.sudolev.interiors.block.custom.BigSeatMovementBehaviour;
import com.sudolev.interiors.block.custom.ChairBlockExtendsSeat;
import com.sudolev.interiors.block.custom.DirectionalSeatBlock;
import com.sudolev.interiors.block.custom.FloorChairBlockExtendsSeat;
import com.sudolev.interiors.block.util.ModTags;
import com.sudolev.interiors.item.ModItems;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {


    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Interiors.MOD_ID);
    // public static final RegistryObject<Block> KELP_SEAT = registerBlock("kelp_seat",
    //      () -> new DirectionalSeatBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), DyeColor.BLACK));

    // public static final RegistryObject<Block> KELP_CHAIR = registerBlock("kelp_chair",
    //        () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), DyeColor.BLACK));

    // public static final RegistryObject<Block> RED_CHAIR = registerBlock("red_chair",
    //                 () -> new ChairBlockTwo(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(),
    //                         DyeColor.RED));
    public static final DyedBlockList<ChairBlockExtendsSeat> CHAIRS = new DyedBlockList<>(colour -> {
        String colourName = colour.getSerializedName();
        BigSeatMovementBehaviour movementBehaviour = new BigSeatMovementBehaviour();
        SeatInteractionBehaviour interactionBehaviour = new SeatInteractionBehaviour();
        return Interiors.REGISTRATE.block(colourName + "_chair", p -> new ChairBlockExtendsSeat(p, colour))
                .initialProperties(SharedProperties::wooden)
                .properties(p -> p.mapColor(colour))
                .transform(axeOnly())
                .onRegister(movementBehaviour(movementBehaviour))
                .onRegister(interactionBehaviour(interactionBehaviour))
                .onRegister(assignDataBehaviour(new EntityNameDisplaySource(), "entity_name"))
                .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.interiors.chair"))
                .tag(ModTags.Blocks.CHAIRS)
                .item()
                .tag(ModTags.Items.CHAIRS)
                .build()
                .register();
    });

    public static final DyedBlockList<FloorChairBlockExtendsSeat> FLOOR_CHAIRS = new DyedBlockList<>(colour -> {
        String colourName = colour.getSerializedName();
        SeatMovementBehaviour movementBehaviour = new SeatMovementBehaviour();
        SeatInteractionBehaviour interactionBehaviour = new SeatInteractionBehaviour();
        return Interiors.REGISTRATE.block(colourName + "_floor_chair", p -> new FloorChairBlockExtendsSeat(p, colour))
                .initialProperties(SharedProperties::wooden)
                .properties(p -> p.mapColor(colour))
                .transform(axeOnly())
                .onRegister(movementBehaviour(movementBehaviour))
                .onRegister(interactionBehaviour(interactionBehaviour))
                .onRegister(assignDataBehaviour(new EntityNameDisplaySource(), "entity_name"))
                .tag(ModTags.Blocks.FLOOR_CHAIRS)
                .item()
                .tag(ModTags.Items.FLOOR_CHAIRS)
                .build()
                .loot((lt, block) -> lt.dropSelf(block))
                .register();
    });

    public static final RegistryEntry<ChairBlockExtendsSeat> KELP_CHAIR = Interiors.REGISTRATE.block("kelp_chair", p -> new ChairBlockExtendsSeat(p, DyeColor.BLACK))
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(DyeColor.BLACK))
            .transform(axeOnly())
            .onRegister(movementBehaviour(new BigSeatMovementBehaviour()))
            .onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
            .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.interiors.chair"))
            .simpleItem()
            .register();

    public static final RegistryEntry<FloorChairBlockExtendsSeat> KELP_FLOOR_CHAIR = Interiors.REGISTRATE.block("kelp_floor_chair", p -> new FloorChairBlockExtendsSeat(p, DyeColor.BLACK))
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(DyeColor.BLACK))
            .transform(axeOnly())
            .onRegister(movementBehaviour(new SeatMovementBehaviour()))
            .onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
            .simpleItem()
            .loot((lt, block) -> lt.dropSelf(block))
            .register();
    public static final RegistryEntry<DirectionalSeatBlock> KELP_SEAT = Interiors.REGISTRATE.block("kelp_seat", p -> new DirectionalSeatBlock(p, DyeColor.BLACK))
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(DyeColor.BLACK))
            .transform(axeOnly())
            .onRegister(movementBehaviour(new SeatMovementBehaviour()))
            .onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
            .onRegister(assignDataBehaviour(new EntityNameDisplaySource(), "entity_name"))
            .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.create.seat"))
            .simpleItem()
            .register();

    public static final RegistryEntry<Block> SEATWOOD_PLANKS = Interiors.REGISTRATE.block("seatwood_planks", p -> new Block(p))
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(DyeColor.ORANGE))
            .transform(axeOnly())
            .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.interiors.seatwood_planks"))
            .simpleItem()
            .register();
    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    };
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}