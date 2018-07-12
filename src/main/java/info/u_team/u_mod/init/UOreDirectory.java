package info.u_team.u_mod.init;

import static net.minecraftforge.oredict.OreDictionary.registerOre;

import info.u_team.u_mod.resource.ResourceUtil;

public class UOreDirectory {
	
	public static void init() {
		// Resources
		ResourceUtil.iterate(resource -> {
			String name = resource.getCapitalizedName();
			registerOre("ore" + name, resource.getOreItemStack());
			registerOre("block" + name, resource.getBlockItemStack());
			registerOre("dust" + name, resource.getDustItemStack());
			registerOre("nugget" + name, resource.getNuggetItemStack());
			registerOre("ingot" + name, resource.getIngotItemStack());
		});
	}
}
