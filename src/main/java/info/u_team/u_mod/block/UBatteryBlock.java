package info.u_team.u_mod.block;

import info.u_team.u_mod.UConstants;
import info.u_team.u_mod.init.UCreativeTabs;
import info.u_team.u_mod.tilentity.UBatteryTile;
import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class UBatteryBlock extends UBlockTileEntity{

	public UBatteryBlock(String name) {
		super(name, Material.IRON, UCreativeTabs.MACHINE, new UTileEntityProvider(new ResourceLocation(UConstants.MODID, "battery_tile"), true, UBatteryTile.class));
	}
	
}
