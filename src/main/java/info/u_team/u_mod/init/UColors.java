package info.u_team.u_mod.init;

import info.u_team.u_mod.resource.*;
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
		blockcolors.registerBlockColorHandler((state, world, pos, index) -> {
			if (index == 1) {
				return EnumResources1.byMetadata(state.getBlock().getMetaFromState(state)).getColor();
			}
			return 0xFFFFFF; // Particle are now only stone textured. Dont know how to fix that without
								// custom particle
		}, UBlocks.ore1);
		
		blockcolors.registerBlockColorHandler((state, world, pos, index) -> {
			if (index == 1) {
				return EnumResources2.byMetadata(state.getBlock().getMetaFromState(state)).getColor();
			}
			return 0xFFFFFF;
		}, UBlocks.ore2);
	}
	
	private static void item() {
		itemcolors.registerItemColorHandler((stack, index) -> {
			if (index == 1) {
				return EnumResources1.byMetadata(stack.getMetadata()).getColor();
			}
			return 0xFFFFFF;
		}, UBlocks.ore1);
		
		itemcolors.registerItemColorHandler((stack, index) -> {
			if (index == 1) {
				return EnumResources2.byMetadata(stack.getMetadata()).getColor();
			}
			return 0xFFFFFF;
		}, UBlocks.ore2);
	}
	
}
