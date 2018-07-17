package info.u_team.u_mod.proxy;

import info.u_team.u_mod.api.TunnelHandler;
import info.u_team.u_mod.init.*;
import info.u_team.u_mod.recipe.*;
import info.u_team.u_mod.recipe.machine.RecipePulverizer;
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
		
		RecipeManager.registerPulverizer(new RecipePulverizer(new IngredientItemStack("oreUranium", 2), new OutputItemStack(new ItemStack(Items.IRON_INGOT, 4))));
		RecipeManager.registerPulverizer(new RecipePulverizer(new IngredientItemStack(new ItemStack(Blocks.STONE)), new OutputItemStack(new ItemStack(Items.IRON_INGOT, 16))));
	}
	
	public void postinit(FMLPostInitializationEvent event) {
	}
	
}
