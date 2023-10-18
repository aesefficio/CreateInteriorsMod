package com.sudolev.interiors.item;

import com.sudolev.interiors.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {
    public static final CreativeModeTab INTERIORS_TAB = new CreativeModeTab("create_interiors_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.RED_CHAIR_ICON.get());
        }
    };
}