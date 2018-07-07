package info.u_team.u_mod.proxy;

import info.u_team.u_mod.api.TunnelHandler;
import info.u_team.u_mod.init.*;
import info.u_team.u_mod.tilentity.TileEntityPulverizer;
import info.u_team.u_team_core.registry.CommonRegistry;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.*;

public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent event) {
		UBlocks.preinit();
		UItems.preinit();
		UGuis.preinit();
		UBiomes.preinit();
		
		CommonRegistry.registerEventHandler(TunnelHandler.class);
		
		// Just testing
		TileEntityPulverizer.addRecipe(new ItemStack(Blocks.IRON_ORE), new ItemStack(Items.IRON_INGOT, 2), null);
	}
	
	public void init(FMLInitializationEvent event) {
		UCreativeTabs.init();
		UBiomes.init();
		UGeneration.init();
	}
	
	public void postinit(FMLPostInitializationEvent event) {
	}
	
}
