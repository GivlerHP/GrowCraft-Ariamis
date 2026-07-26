package growthcraft.cellar.network;

import growthcraft.cellar.common.inventory.ContainerBrewKettle;
import growthcraft.cellar.common.tileentity.TileEntityBrewKettle;

import net.minecraft.entity.player.EntityPlayer;

public class PacketSwitchTankButton extends AbstractPacketButton
{
	public PacketSwitchTankButton() {}

	public PacketSwitchTankButton(int x, int y, int z)
	{
		super(x, y, z);
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
		if (device != null)
		{
			device.switchTanks();
			device.markDirtyAndUpdate();
		}
	}
}
