package systems.alexander.interiors.block.util;

import systems.alexander.interiors.CreateInteriors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> CHAIRS =tag("chairs");
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(CreateInteriors.ID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> CHAIRS =tag("chairs");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(CreateInteriors.ID, name));
        }

    }
}
