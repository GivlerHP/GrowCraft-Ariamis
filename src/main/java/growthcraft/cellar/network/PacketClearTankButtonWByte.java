package growthcraft.cellar.network;

import growthcraft.cellar.common.inventory.ContainerBrewKettle;
import growthcraft.cellar.common.tileentity.TileEntityBrewKettle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class PacketClearTankButtonWByte extends AbstractPacketButton
{
	byte b;

	public PacketClearTankButtonWByte(){}

	public PacketClearTankButtonWByte(int x, int y, int z, byte byt)
	{
		super(x, y, z);
		this.b = byt;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		super.encodeInto(ctx, buffer);
		buffer.writeByte(b);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		super.decodeInto(ctx, buffer);
		this.b = buffer.readByte();
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{

	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		final TileEntityBrewKettle device =
			getOpenDevice(player, ContainerBrewKettle.class, TileEntityBrewKettle.class);
		if (device == null)
		{
			return;
		}

		if (b == 0 || b == 1)
		{
			device.clearTank(b);
			device.markDirtyAndUpdate();
		}
	}
}
