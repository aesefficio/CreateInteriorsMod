package systems.alexander.interiors.item;

import com.simibubi.create.infrastructure.item.CreateCreativeModeTab;
import net.minecraft.world.item.ItemStack;
import systems.alexander.interiors.block.ModBlocks;

public class CreativeTabStuff extends CreateCreativeModeTab {

    public CreativeTabStuff(String id) {
        super("interiors_tab");
    }

    @Override
    public ItemStack makeIcon() {
        return ModBlocks.RED_CHAIR_ICON.get().asItem().getDefaultInstance();
    }
}
