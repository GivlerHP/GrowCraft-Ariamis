package growthcraft.cellar.network;

import growthcraft.cellar.common.inventory.CellarContainer;
import growthcraft.cellar.common.tileentity.TileEntityCellarDevice;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractPacketButton extends AbstractPacket
{
	int xCoord;
	int yCoord;
	int zCoord;

	public AbstractPacketButton() {}

	public AbstractPacketButton(int x, int y, int z)
	{
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeInt(xCoord);
		buffer.writeInt(yCoord);
		buffer.writeInt(zCoord);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		this.xCoord = buffer.readInt();
		this.yCoord = buffer.readInt();
		this.zCoord = buffer.readInt();
	}

	/**
	 * Resolves a button target from the sender's currently open server
	 * container. Coordinates in the packet are only checked for consistency;
	 * they are never used to look up a tile entity.
	 */
	protected <T extends TileEntityCellarDevice> T getOpenDevice(
		EntityPlayer player, Class<? extends CellarContainer> containerType, Class<T> deviceType)
	{
		if (player == null || !containerType.isInstance(player.openContainer))
		{
			return null;
		}

		final CellarContainer container = (CellarContainer)player.openContainer;
		if (!container.canInteractWith(player))
		{
			return null;
		}

		final TileEntityCellarDevice device = container.getCellarDevice();
		if (!deviceType.isInstance(device)
			|| device.getWorldObj() != player.worldObj
			|| device.xCoord != xCoord
			|| device.yCoord != yCoord
			|| device.zCoord != zCoord)
		{
			return null;
		}

		return deviceType.cast(device);
	}
}
