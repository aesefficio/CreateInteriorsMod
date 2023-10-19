package systems.alexander.interiors.datagen;

import systems.alexander.interiors.CreateInteriors;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CreateInteriors.ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        // this.tag(BlockTags.MINEABLE_WITH_AXE)
        //        .add(ModBlocks.SAPPHIRE_BLOCK.get(),
        //                ModBlocks.RAW_SAPPHIRE_BLOCK.get(),


    }
}