package growthcraft.cellar.network;

import growthcraft.cellar.common.inventory.CellarContainer;
import growthcraft.cellar.common.inventory.ContainerCultureJar;
import growthcraft.cellar.common.inventory.ContainerFermentBarrel;
import growthcraft.cellar.common.inventory.ContainerFruitPress;
import growthcraft.cellar.common.tileentity.TileEntityCellarDevice;
import growthcraft.cellar.common.tileentity.TileEntityCultureJar;
import growthcraft.cellar.common.tileentity.TileEntityFermentBarrel;
import growthcraft.cellar.common.tileentity.TileEntityFruitPress;

import net.minecraft.entity.player.EntityPlayer;

public class PacketClearTankButton extends AbstractPacketButton
{
	public PacketClearTankButton(){}

	public PacketClearTankButton(int x, int y, int z)
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
		final CellarContainer openContainer =
			player.openContainer instanceof CellarContainer ? (CellarContainer)player.openContainer : null;
		if (openContainer == null)
		{
			return;
		}

		final TileEntityCellarDevice device;
		if (openContainer instanceof ContainerFruitPress)
		{
			device = getOpenDevice(player, ContainerFruitPress.class, TileEntityFruitPress.class);
		}
		else if (openContainer instanceof ContainerFermentBarrel)
		{
			device = getOpenDevice(player, ContainerFermentBarrel.class, TileEntityFermentBarrel.class);
		}
		else if (openContainer instanceof ContainerCultureJar)
		{
			device = getOpenDevice(player, ContainerCultureJar.class, TileEntityCultureJar.class);
		}
		else
		{
			return;
		}

		if (device != null && device.getTankCount() > 0)
		{
			device.clearTank(0);
			device.markDirtyAndUpdate();
		}
	}
}
