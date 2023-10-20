package systems.alexander.interiors.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.contraptions.actors.seat.SeatBlock;

public class BigSeatEntity extends Entity implements IEntityAdditionalSpawnData {

    public BigSeatEntity(EntityType<?> type, Level level) {
        super(type, level);
    }

    public BigSeatEntity(Level world, BlockPos pos) {
        this(AllEntityTypes.SEAT.get(), world);
        noPhysics = true;
    }

    public static EntityType.Builder<?> build(EntityType.Builder<?> builder) {
        @SuppressWarnings("unchecked")
        EntityType.Builder<com.simibubi.create.content.contraptions.actors.seat.SeatEntity> entityBuilder = (EntityType.Builder<com.simibubi.create.content.contraptions.actors.seat.SeatEntity>) builder;
        return entityBuilder.sized(0.25f, 0.85f);
    }

    @Override
    public void setPos(double x, double y, double z) {
        super.setPos(x, y, z);
        AABB bb = getBoundingBox();
        Vec3 diff = new Vec3(x, y, z).subtract(bb.getCenter());
        setBoundingBox(bb.move(diff));
    }

    @Override
    protected void positionRider(Entity entity, Entity.MoveFunction callback) {
        if (!this.hasPassenger(entity))
            return;
        double d0 = this.getY() + this.getPassengersRidingOffset() + entity.getMyRidingOffset();
        callback.accept(entity, this.getX(), d0 + getCustomEntitySeatOffset(entity), this.getZ());
    }

    public static double getCustomEntitySeatOffset(Entity entity) {
        if (entity instanceof Slime)
            return 0.25f;
        if (entity instanceof Parrot)
            return 1 / 16f;
        if (entity instanceof Skeleton)
            return 1 / 8f;
        if (entity instanceof Creeper)
            return 1 / 8f;
        if (entity instanceof Cat)
            return 1 / 8f;
        if (entity instanceof Wolf)
            return 1 / 16f;
        if (entity instanceof Frog)
            return 1 / 8f + 1 / 64f;
        return 0;
    }

    @Override
    public void setDeltaMovement(Vec3 deltaMovement) {}

    @Override
    public void tick() {
        if (level().isClientSide)
            return;
        boolean blockPresent = level().getBlockState(blockPosition())
                .getBlock() instanceof SeatBlock;
        if (isVehicle() && blockPresent)
            return;
        this.discard();
    }

    @Override
    protected boolean canRide(Entity entity) {
        // Fake Players (tested with deployers) have a BUNCH of weird issues, don't let
        // them ride seats
        return !(entity instanceof FakePlayer);
    }

    @Override
    protected void removePassenger(Entity entity) {
        super.removePassenger(entity);
        if (entity instanceof TamableAnimal ta)
            ta.setInSittingPose(false);
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        return super.getDismountLocationForPassenger(passenger).add(0, 0.5f, 0);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {}

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {}

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {}
}
