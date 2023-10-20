package systems.alexander.interiors.registry;

import systems.alexander.interiors.CreateInteriors;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class CITags {
    public static class Blocks {
        public static final TagKey<Block> CHAIRS = BlockTags.create(CreateInteriors.asResource("chairs"));
    }

    public static class Items {
        public static final TagKey<Item> CHAIRS = ItemTags.create(CreateInteriors.asResource("chairs"));
    }
}
