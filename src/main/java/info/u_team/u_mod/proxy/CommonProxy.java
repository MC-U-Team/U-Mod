package info.u_team.u_mod.proxy;

import info.u_team.u_mod.api.TunnelHandler;
import info.u_team.u_mod.init.UBlocks;
import info.u_team.u_mod.init.UCreativeTabs;
import info.u_team.u_mod.init.UGuis;
import info.u_team.u_mod.init.UItems;
import info.u_team.u_mod.tilentity.TileEntityPulverizer;
import info.u_team.u_team_core.registry.CommonRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preinit(FMLPreInitializationEvent event) {
		UBlocks.init();
		UItems.init();
		UGuis.init();
		
		CommonRegistry.registerEventHandler(TunnelHandler.class);
		
		// Just testing
		TileEntityPulverizer.addRecipe(new ItemStack(Blocks.IRON_ORE), new ItemStack(Items.IRON_INGOT, 2), null);
	}
	
	public void init(FMLInitializationEvent event) {
		UCreativeTabs.init();
	}
	
	public void postinit(FMLPostInitializationEvent event) {
	}
	
}
