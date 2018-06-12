package vswe.stevescarts.packet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import reborncore.common.network.ExtendedPacketBuffer;
import reborncore.common.network.INetworkPacket;
import vswe.stevescarts.containers.ContainerMinecart;
import vswe.stevescarts.entitys.EntityMinecartModular;

public class PacketReturnCart implements INetworkPacket<PacketReturnCart> {

    public PacketReturnCart() {}

    @Override
    public void writeData(ExtendedPacketBuffer extendedPacketBuffer) {}

    @Override
    public void readData(ExtendedPacketBuffer extendedPacketBuffer) {}

    @Override
    public void processData(PacketReturnCart packetReturnCart, MessageContext messageContext) {
        EntityPlayer player = messageContext.getServerHandler().player;

        Container container = player.openContainer;
        if (container instanceof ContainerMinecart) {
            EntityMinecartModular cart = ((ContainerMinecart) container).cart;
            cart.turnback();
        }

    }
}
