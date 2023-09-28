package systems.alexander.interiors.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import systems.alexander.interiors.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final CreativeModeTab INTERIORS_TAB = new CreativeModeTab("create_interiors_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.RED_CHAIR_ICON.get());
        }
    };
}