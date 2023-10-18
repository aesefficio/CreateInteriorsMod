package com.sudolev.interiors.item;

import com.simibubi.create.infrastructure.item.CreateCreativeModeTab;
import com.sudolev.interiors.block.ModBlocks;
import net.minecraft.world.item.ItemStack;

public class CreativeTabStuff extends CreateCreativeModeTab {

    public CreativeTabStuff(String id) {
        super("interiors_tab");
    }

    @Override
    public ItemStack makeIcon() {
        return ModBlocks.RED_CHAIR_ICON.get().asItem().getDefaultInstance();
    }
}
