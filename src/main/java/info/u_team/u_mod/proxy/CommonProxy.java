package info.u_team.u_mod.proxy;

import info.u_team.u_mod.api.TunnelHandler;
import info.u_team.u_mod.init.*;
import info.u_team.u_mod.recipe.InputStack;
import info.u_team.u_mod.tilentity.pulverizer.*;
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
		
	}
	
	public void init(FMLInitializationEvent event) {
		UCreativeTabs.init();
		UBiomes.init();
		UGeneration.init();
		UOreDirectory.init();
		URecipes.init();
		
		// Just testing
		RecipeManagerPulverizer.addRecipe(new InputStack("oreUranium"), new ItemStack(Items.IRON_INGOT, 2), null);
		RecipeManagerPulverizer.addRecipe(new InputStack(new ItemStack(Blocks.IRON_ORE)), new ItemStack(Items.IRON_INGOT, 2), null);
	}
	
	public void postinit(FMLPostInitializationEvent event) {
	}
	
}
