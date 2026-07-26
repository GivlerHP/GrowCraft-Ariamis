package growthcraft.cellar.common.inventory;

import growthcraft.cellar.common.tileentity.TileEntityCellarDevice;
import growthcraft.core.common.inventory.GrcContainer;

public class CellarContainer extends GrcContainer
{
	public CellarContainer(TileEntityCellarDevice te)
	{
		super(te);
	}

	/**
	 * Returns the server-side device this container was opened for.
	 *
	 * Packet handlers must use this reference instead of trusting coordinates
	 * supplied by the client.
	 */
	public TileEntityCellarDevice getCellarDevice()
	{
		return (TileEntityCellarDevice)tileEntity;
	}
}
