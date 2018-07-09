package info.u_team.u_mod.init;

import static net.minecraftforge.oredict.OreDictionary.registerOre;

import org.apache.commons.lang3.StringUtils;

import info.u_team.u_mod.resource.*;
import net.minecraft.item.ItemStack;

public class UOreDirectory {
	
	public static void init() {
		// Resources
		for (EnumResources1 resources : EnumResources1.VALUES) {
			String name = StringUtils.capitalize(resources.getName());
			int metadata = resources.getMetadata();
			registerOre("ore" + name, new ItemStack(UBlocks.resource_ore1, 1, metadata));
			registerOre("block" + name, new ItemStack(UBlocks.resource_block1, 1, metadata));
			registerOre("dust" + name, new ItemStack(UItems.resource_dust1, 1, metadata));
			registerOre("nugget" + name, new ItemStack(UItems.resource_nugget1, 1, metadata));
			registerOre("ingot" + name, new ItemStack(UItems.resource_ingot1, 1, metadata));
		}
		for (EnumResources2 resources : EnumResources2.VALUES) {
			String name = StringUtils.capitalize(resources.getName());
			int metadata = resources.getMetadata();
			registerOre("ore" + name, new ItemStack(UBlocks.resource_ore2, 1, metadata));
			registerOre("block" + name, new ItemStack(UBlocks.resource_block2, 1, metadata));
			registerOre("dust" + name, new ItemStack(UItems.resource_dust2, 1, metadata));
			registerOre("nugget" + name, new ItemStack(UItems.resource_nugget2, 1, metadata));
			registerOre("ingot" + name, new ItemStack(UItems.resource_ingot2, 1, metadata));
		}
	}
}
