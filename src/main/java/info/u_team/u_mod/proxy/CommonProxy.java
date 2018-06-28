package info.u_team.u_mod.proxy;

import info.u_team.u_mod.init.*;
import info.u_team.u_mod.tilentity.UTileEntityPulverizer;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.*;

public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent event) {
		
		UBlocks.init();
		UItems.init();
		
		UCreativeTabs.init();
		
		UGuis.init();
		
		// Just testing
		UTileEntityPulverizer.addRecipe(new ItemStack(Blocks.IRON_ORE), new ItemStack(Items.IRON_INGOT, 2), null);
	}
	
	public void init(FMLInitializationEvent event) {
	}
	
	public void postinit(FMLPostInitializationEvent event) {
	}
	
}
