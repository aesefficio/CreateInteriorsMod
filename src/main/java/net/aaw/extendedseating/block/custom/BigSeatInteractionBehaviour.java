package net.aaw.extendedseating.block.custom;

import com.simibubi.create.AllPackets;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;
import com.simibubi.create.content.contraptions.actors.seat.SeatInteractionBehaviour;
import com.simibubi.create.content.contraptions.sync.ContraptionSeatMappingPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;
import net.minecraft.world.entity.Entity;

public class BigSeatInteractionBehaviour extends SeatInteractionBehaviour {

    @Override
    public void handleEntityCollision(Entity entity, BlockPos localPos, AbstractContraptionEntity contraptionEntity) {
        Contraption contraption = contraptionEntity.getContraption();
        int index = contraption.getSeats()
                .indexOf(localPos);
        if (index == -1)
            return;
        if (!ChairBlockExtendsSeat.canBePickedUp(entity))
            return;
        contraptionEntity.addSittingPassenger(entity, index);
    }
}
