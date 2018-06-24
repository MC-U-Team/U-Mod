package info.u_team.u_mod.blocks;

import info.u_team.u_mod.UConstants;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class UBlock extends Block {
	
	public UBlock(Material materialIn, String name) {
		super(materialIn);
		
		this.setRegistryName(UConstants.MODID, name);
		this.setUnlocalizedName(UConstants.MODID + ":" + name);
	}
	
}
