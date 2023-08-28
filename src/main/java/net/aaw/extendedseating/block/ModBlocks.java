package net.aaw.extendedseating.block;

import static com.simibubi.create.AllInteractionBehaviours.interactionBehaviour;
import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;
import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.assignDataBehaviour;
import static com.simibubi.create.foundation.data.BlockStateGen.axisBlock;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOnly;
import static net.aaw.extendedseating.ExtendedSeating.REGISTRATE;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import com.simibubi.create.content.contraptions.actors.seat.SeatInteractionBehaviour;
import com.simibubi.create.content.contraptions.actors.seat.SeatMovementBehaviour;

import com.simibubi.create.content.redstone.displayLink.source.EntityNameDisplaySource;
import com.simibubi.create.foundation.block.DyedBlockList;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.utility.DyeHelper;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.aaw.extendedseating.ExtendedSeating;
import net.aaw.extendedseating.block.custom.*;
import net.aaw.extendedseating.block.util.ItemDescriptionsCreate;
import net.aaw.extendedseating.block.util.ModTags;
import net.aaw.extendedseating.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Supplier;

public class ModBlocks {


    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ExtendedSeating.MOD_ID);
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
        return REGISTRATE.block(colourName + "_chair", p -> new ChairBlockExtendsSeat(p, colour))
                .initialProperties(SharedProperties::wooden)
                .properties(p -> p.mapColor(colour))
                .transform(axeOnly())
                .onRegister(movementBehaviour(movementBehaviour))
                .onRegister(interactionBehaviour(interactionBehaviour))
                .onRegister(assignDataBehaviour(new EntityNameDisplaySource(), "entity_name"))
                .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.extendedseating.chair"))
                .tag(ModTags.Blocks.CHAIRS)
                .item()
                .tag(ModTags.Items.CHAIRS)
                .build()
                .register();
    });

    public static final RegistryEntry<ChairBlockExtendsSeat> KELP_CHAIR = REGISTRATE.block("kelp_chair", p -> new ChairBlockExtendsSeat(p, DyeColor.BLACK))
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(DyeColor.BLACK))
            .transform(axeOnly())
            .onRegister(movementBehaviour(new BigSeatMovementBehaviour()))
            .onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
            .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.extendedseating.chair"))
            .simpleItem()
            .register();

    public static final RegistryEntry<DirectionalSeatBlock> KELP_SEAT = REGISTRATE.block("kelp_seat", p -> new DirectionalSeatBlock(p, DyeColor.BLACK))
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(DyeColor.BLACK))
            .transform(axeOnly())
            .onRegister(movementBehaviour(new SeatMovementBehaviour()))
            .onRegister(interactionBehaviour(new SeatInteractionBehaviour()))
            .onRegister(assignDataBehaviour(new EntityNameDisplaySource(), "entity_name"))
            .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.create.seat"))
            .simpleItem()
            .register();

    public static final RegistryEntry<Block> SEATWOOD_PLANKS = REGISTRATE.block("seatwood_planks", p -> new Block(p))
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(DyeColor.ORANGE))
            .transform(axeOnly())
            .onRegisterAfter(Registries.ITEM, v -> ItemDescription.useKey(v, "block.extendedseating.seatwood_planks"))
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