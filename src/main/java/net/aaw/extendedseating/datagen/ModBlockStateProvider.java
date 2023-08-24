package net.aaw.extendedseating.datagen;

import net.aaw.extendedseating.ExtendedSeating;
import net.aaw.extendedseating.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ExtendedSeating.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // blockWithItem(ModBlocks.SAPPHIRE_BLOCK);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}