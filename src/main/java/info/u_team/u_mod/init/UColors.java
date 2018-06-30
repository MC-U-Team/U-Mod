package info.u_team.u_mod.init;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.*;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class UColors {
	
	private static BlockColors blockcolors;
	private static ItemColors itemcolors;
	
	public static void init() {
		Minecraft minecraft = Minecraft.getMinecraft();
		blockcolors = minecraft.getBlockColors();
		itemcolors = minecraft.getItemColors();
		item();
		block();
	}
	
	private static void block() {
	}
	
	private static void item() {
		
	}
	
}
