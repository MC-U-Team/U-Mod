package info.u_team.u_mod.proxy;

import info.u_team.u_mod.init.*;
import info.u_team.u_mod.tilentity.TileEntityPulverizer;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.*;

public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent event) {
		
		UBlocks.init();
		UItems.init();
		UGuis.init();
		
		// Just testing
		TileEntityPulverizer.addRecipe(new ItemStack(Blocks.IRON_ORE), new ItemStack(Items.IRON_INGOT, 2), null);
	}
	
	public void init(FMLInitializationEvent event) {
		UCreativeTabs.init();
	}
	
	public void postinit(FMLPostInitializationEvent event) {
	}
	
}
