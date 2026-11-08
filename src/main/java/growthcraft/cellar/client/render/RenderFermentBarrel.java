package growthcraft.cellar.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

/** One standard cuboid; metadata still controls all six barrel orientations. */
public class RenderFermentBarrel implements ISimpleBlockRenderingHandler {
    public static int RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
    public int getRenderId() { return RENDER_ID; }
    public boolean shouldRender3DInInventory(int modelID) { return true; }
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        growthcraft.core.util.RenderUtils.renderInventoryBlockOverride(block, renderer,
            new net.minecraft.util.IIcon[] {block.getIcon(0,1),block.getIcon(1,1),block.getIcon(2,1),block.getIcon(3,1),block.getIcon(4,1),block.getIcon(5,1)},
            net.minecraft.client.renderer.Tessellator.instance);
    }
    public boolean renderWorldBlock(IBlockAccess world,int x,int y,int z,Block block,int modelId,RenderBlocks r) {
        if(modelId != RENDER_ID) return false;
        int e=r.uvRotateEast,w=r.uvRotateWest,n=r.uvRotateNorth,s=r.uvRotateSouth,t=r.uvRotateTop,b=r.uvRotateBottom;
        double x0=r.renderMinX,y0=r.renderMinY,z0=r.renderMinZ,x1=r.renderMaxX,y1=r.renderMaxY,z1=r.renderMaxZ;
        try {
            r.uvRotateEast=r.uvRotateWest=r.uvRotateNorth=r.uvRotateSouth=r.uvRotateTop=r.uvRotateBottom=0;
            int meta=world.getBlockMetadata(x,y,z);
            if(meta==2) { r.uvRotateNorth=3;r.uvRotateTop=2;r.uvRotateBottom=1; }
            else if(meta==3) { r.uvRotateSouth=3;r.uvRotateTop=1;r.uvRotateBottom=2; }
            else if(meta==4) { r.uvRotateWest=3;r.uvRotateTop=3;r.uvRotateBottom=3; }
            else if(meta==5) { r.uvRotateEast=3; }
            r.setRenderBounds(0,0,0,1,1,1);
            return r.renderStandardBlock(block,x,y,z);
        } finally {
            r.uvRotateEast=e;r.uvRotateWest=w;r.uvRotateNorth=n;r.uvRotateSouth=s;r.uvRotateTop=t;r.uvRotateBottom=b;
            r.setRenderBounds(x0,y0,z0,x1,y1,z1);
        }
    }
}
